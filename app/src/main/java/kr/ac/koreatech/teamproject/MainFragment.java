package kr.ac.koreatech.teamproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.FrontRecyclerViewAdapter;
import entity.FrontPoster;
import entity.PosterEntity;
import entity.PosterQuestionEntity;
import entity.StudyEntity;
import kr.ac.koreatech.teamproject.databinding.FragmentMainBinding;
import service.TimerService;


public class MainFragment extends Fragment {
    class TimeRankModel {
        private TextView name;
        private TextView time;

        public TimeRankModel(TextView name, TextView time) {
            this.name = name;
            this.time = time;
        }

        public String getName() {
            return name.getText().toString();
        }

        public Integer getTime() {
            String temp = name.getText().toString();
            if (temp.equals(""))
                return null;
            String[] split = temp.split(":");
            int sec = Integer.parseInt(split[2]);
            int min = Integer.parseInt(split[1]) * 60;
            int hour = Integer.parseInt(split[0]) * 3600;
            return sec + min + hour;
        }

        public void setName(String name) {
            requireActivity().runOnUiThread(() -> {
                this.name.setText(name);
            });
        }

        public void setTime(Long time) {
            Long sec = (time / 1000) % 60;
            Long min = (time / (1000 * 60)) % 60;
            Long hour = (time / (1000 * 60 * 60)) % 24;
            this.time.setText(hour + ":" + min + ":" + sec);
        }

        public void setData(String name, Long time) {
            setName(name);
            setTime(time);
        }
    }

    class RankDTO {
        private String name;
        private Long time;

        public RankDTO(String name, Long time) {
            this.name = name;
            this.time = time;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Long getTime() {
            return time;
        }

        public void setTime(Long time) {
            this.time = time;
        }
    }

    class RankComparator implements Comparator<RankDTO> {
        @Override
        public int compare(RankDTO f1, RankDTO f2) {
            if (f1.getTime() > f2.getTime()) {
                return 1;
            } else if (f1.getTime() < f2.getTime()) {
                return -1;
            }
            return 0;
        }
    }

    ArrayList<RankDTO> list = new ArrayList<>();
    private FragmentMainBinding binding;
    private Date startTime = new Date();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private BroadcastReceiver studyTimeRecvier;
    private TimeRankModel rank1;
    private TimeRankModel rank2;
    private TimeRankModel rank3;
    private Long myTime = 0L;
    private FrontRecyclerViewAdapter m1Adapter;
    private FrontRecyclerViewAdapter m2Adapter;
    private LinearLayoutManager m1LayoutManager;
    private LinearLayoutManager m2LayoutManager;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    ArrayList<String> join_1 = new ArrayList<String>();

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onResume() {
        super.onResume();

        if (studyTimeRecvier == null) {
            studyTimeRecvier = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Integer total_sec = intent.getIntExtra("time_sec", 0);
                    if (total_sec == null)
                        return;
                    int sec = (total_sec / 1000) % 60;
                    int min = (total_sec / (1000 * 60)) % 60;
                    int hour = (total_sec / (1000 * 60 * 60)) % 24;
                    binding.timerTextView.setText(hour + ":" + min + ":" + sec);
                }
            };
        }

        requireActivity().registerReceiver(studyTimeRecvier, new IntentFilter("timeService"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (studyTimeRecvier != null) {
            requireActivity().unregisterReceiver(studyTimeRecvier);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        binding = FragmentMainBinding.inflate(getLayoutInflater());
        rank1 = new TimeRankModel(binding.rank1NameText, binding.rank1TimeText);
        rank2 = new TimeRankModel(binding.rank2NameText, binding.rank2TimeText);
        rank3 = new TimeRankModel(binding.rank3NameText, binding.rank3TimeText);
        // init Data
        ArrayList<FrontPoster> data1 = new ArrayList<>();
        binding.btnPlay.setOnClickListener(v -> {
            requireActivity().startService(new Intent(getActivity(), TimerService.class));
        });

        binding.btnStop.setOnClickListener(v -> {
            requireActivity().stopService(new Intent(getActivity(), TimerService.class));
            System.out.println("stop 버튼 누름?");
        });

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

//        data1.add(new FrontPoster(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "모바일프로그래밍"));
//        data1.add(new FrontPoster(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "중국어회화"));
//        data1.add(new FrontPoster(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "창의적글쓰기"));
//        data1.add(new FrontPoster(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "IoT개론및실습"));
//        data1.add(new FrontPoster(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "스크립트프로그래밍"));
//        data1.add(new FrontPoster(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "객체지향개발론및실습"));
//        data1.add(new FrontPoster(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "컴퓨터네트워크"));

/*
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /{document=**} {
      allow read, write: if
          request.time < timestamp.date(2022, 12, 18);
    }
  }
}
 */
        ArrayList<FrontPoster> data2 = new ArrayList<>();

//        data2.add(new FrontPoster(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "정처기합격"));
//        data2.add(new FrontPoster(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "CAD 스터디"));
//        data2.add(new FrontPoster(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "C언어 모각코"));
//        data2.add(new FrontPoster(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "과탑먹을사람들"));
//        data2.add(new FrontPoster(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "모각코"));
//        data2.add(new FrontPoster(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "공대생들어와"));
//        data2.add(new FrontPoster(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "중국어같이해"));
        // init LayoutManager
        m1LayoutManager = new LinearLayoutManager(this.getActivity());
        m1LayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); // 기본값이 VERTICAL

        m2LayoutManager = new LinearLayoutManager(this.getActivity());
        m2LayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); // 기본값이 VERTICAL

        // setLayoutManager
        binding.fragmentMainPosterListView.setLayoutManager(m1LayoutManager);
        binding.fragmentMainStudyListView.setLayoutManager(m2LayoutManager);


        // init Adapter
        m1Adapter = new FrontRecyclerViewAdapter();
        m2Adapter = new FrontRecyclerViewAdapter();

        //--------------------------------------------------------------
//        data2.add(new FrontPoster(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), );

        //--------------------------------------------------------------

        // set Data
        m1Adapter.setData(data1);
        m2Adapter.setData(data2);

        // set Adapter
        binding.fragmentMainPosterListView.setAdapter(m1Adapter);

        binding.fragmentMainStudyListView.setAdapter(m2Adapter);

        getUserInfo(firebaseAuth.getCurrentUser().getEmail());
//        getUserList();
        /*createRank("admin@email-com","최진우");
        createRank("test1@email-com","김채연");
        createRank("test2@email-com","이효민");
        createRank("test3@email-com","김선경");
        createRank("test4@email-com","테스트4");
        createRank("test5@email-com","테스트5");
        createRank("test6@email-com","테스트6");
        createRank("test7@email-com","테스트7");
        createRank("test8@email-com","테스트8");
        createRank("test9@email-com","테스트9");
        createRank("test10@email-com","테스트10");*/
        getRankingList();
        rank1.setData("로딩중", 0L);
        rank2.setData("로딩중", 0L);
        rank3.setData("로딩중", 0L);
    }

    private void createRank(String email, String name) {
        email = email.replace(".", "-");
        Map<String, Object> city = new HashMap<>();
        city.put("name", name);
        city.put("time", 0);

        db.collection("ranking").document(email)
                .set(city)
                .addOnSuccessListener(aVoid -> {
                    Log.d("TAG", "DocumentSnapshot successfully written!");
                });
    }

    private void updateRankTime(String email, Long value) {
        email = email.replace(".", "-");
        DocumentReference washingtonRef = db.collection("ranking").document(email);
        washingtonRef
                .update("time", value)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        getRankingList();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error updating document", e);
                    }
                });
    }

    private void getRankingList() {
        db.collection("ranking")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        list.clear();
                        String email = firebaseAuth.getCurrentUser().getEmail().replace(".", "-");
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            list.add(new RankDTO(document.getData().get("name").toString(), Long.parseLong(document.getData().get("time").toString())));
                            if (document.getId().equals(email)) {
                                myTime = Long.parseLong(document.getData().get("time").toString());
                            }
                        }
                        Collections.sort(list, new RankComparator().reversed());
                        rank1.setData(list.get(0).name, list.get(0).time);
                        rank2.setData(list.get(1).name, list.get(1).time);
                        rank3.setData(list.get(2).name, list.get(2).time);
                        Log.d("TAG", "good");

                    } else {
                        Log.d("TAG", "Error getting documents: ", task.getException());
                    }
                });

    }

    public void finishStudy(Long secTime) {
        myTime += secTime;
        updateRankTime(firebaseAuth.getCurrentUser().getEmail(), myTime);
        System.out.println(secTime + "공부시간이 측정되었습니다");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("Study With Me"); //  ·८·
        actionBar.setDisplayHomeAsUpEnabled(false);
        return binding.getRoot();
    }

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static String userName;


    private void getUserInfo(String user_email) {
        user_email = user_email.replace(".", "-");

        DocumentReference docRef = db.collection("server").document("user/" + user_email + "/info/");
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Log.d("TAG", "DocumentSnapshot data: " + document.getData());

                    userName = document.getData().get("name").toString();

                    //--------------------------------------------------------------------

                } else {
                    Log.d("TAG", "No such document");
                }
            } else {
                Log.d("TAG", "get failed with ", task.getException());
            }
        });
    }
}