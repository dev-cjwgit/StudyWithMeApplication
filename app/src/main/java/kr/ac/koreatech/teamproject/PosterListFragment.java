package kr.ac.koreatech.teamproject;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import adapter.PosterListViewAdapter;
import adapter.StudyListViewAdapter;
import appcomponent.MyFragment;
import entity.PosterEntity;
import entity.StudyEntity;
import kr.ac.koreatech.teamproject.databinding.FragmentPosterListBinding;

public class PosterListFragment extends Fragment {
    private FragmentPosterListBinding binding;
    private PosterListViewAdapter posterJoinListViewAdapter; // 가입중인
    private PosterListViewAdapter posterFullListViewAdapter; //전체 게시판 목록
    private PosterListViewAdapter posterListViewAdapter_2 = new PosterListViewAdapter(); // 강의
    private PosterListViewAdapter posterListViewAdapter_3 = new PosterListViewAdapter(); // 자격증
    private PosterListViewAdapter presentAdapter;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private boolean back_btn = false;

    private String mParam1;
    private String mParam2;

    public static Map<String, PosterEntity> list = new HashMap<>();

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private AdapterView.OnItemClickListener join_lecture_listener;
    private AdapterView.OnItemClickListener enter_lecture_listener;
    private boolean status = false;
    private Object MenuInflater;

    public PosterListFragment() {
        // Required empty public constructor
    }

    public PosterListFragment(boolean back_btn) {
        this.back_btn = back_btn;
    }

    public static String profName;
    public static String introduce;
    public static String profEmail;
    public static String phone;
    public static String assistEmail;
    public static String lecturePlan;
    public static String mainBook;
    public static String subBook;

    public static HashMap<String, PosterEntity> hashMap = new HashMap<>();

    private void getLectureList() {
        posterFullListViewAdapter.clear();
        posterListViewAdapter_2.clear();
        posterListViewAdapter_3.clear();
        db.collection("server/data/lectureList/")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("TAG", document.getId() + " => " + document.getData());
//                            posterListViewAdapter_2.append(new PosterEntity(BitmapFactory.decodeResource(getResources() R.drawable.default_image), document.getId(),document.getData().get("profName").toString(),document.getData().get("currPeople"),document.getData().get("introduce").toString(),document.getData().get("category").toString());
                            PosterEntity entity1 = new PosterEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image),
                                    document.getId(),
                                    document.getData().get("profName").toString(),
                                    document.getData().get("introduce").toString(),
                                    document.getData().get("profEmail").toString(),
                                    document.getData().get("phone").toString(),
                                    document.getData().get("assistEmail").toString(),
                                    document.getData().get("lecturePlan").toString(),
                                    document.getData().get("mainBook").toString(),
                                    document.getData().get("subBook").toString(),
                                    document.getData().get("category").toString());

                            hashMap.put(entity1.getTitle(), entity1);
//
//
//                                    // 해쉬 맵

//                                    profName = document.getData().get("profName").toString();
//                                    introduce = document.getData().get("introduce").toString();
//                                    profEmail = document.getData().get("profEmail").toString();
//                                    phone = document.getData().get("phone").toString();
//                                    assistEmail = document.getData().get("assistEmail").toString();
//                                    lecturePlan = document.getData().get("lecturePlan").toString();
//                                    mainBook = document.getData().get("mainBook").toString();
//                                    subBook = document.getData().get("subBook").toString();


                            posterFullListViewAdapter.append(entity1);
                            list.put(document.getId(), entity1);
                            if (document.getData().get("category").toString().equals("강의")) {
                                posterListViewAdapter_2.append(entity1);
                            }
                            if (document.getData().get("category").toString().equals("자격증")) {
                                posterListViewAdapter_3.append(entity1);
                            }
                        }
                    } else {
                        Log.d("TAG", "Error getting documents: ", task.getException());
                    }
                });
    }

    // 강의 참여(이메일, 참여 할 강의 이름)
    private void addJoinLecture(String user_email, String lecture_name) {
        user_email = user_email.replace(".", "-");
        DocumentReference washingtonRef = db.collection("server").document("user/" + user_email + "/joinLecture/");
        washingtonRef.update("title", FieldValue.arrayUnion(lecture_name))
                .addOnSuccessListener(aVoid -> {
                    Log.d("TAG", "DocumentSnapshot successfully update");
                })
                .addOnFailureListener(e -> {
                    Log.w("TAG", "Error update document", e);
                });
    }

    // 유저가 참여 중인 강의 목록(상세) 가져오기(이메일)
    public void getJoinLectureList(String user_email) {
        user_email = user_email.replace(".", "-");

        DocumentReference docRef = db.collection("server").document("user/" + user_email + "/joinLecture/");
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                    ArrayList<String> temp = (ArrayList<String>) document.get("title");
                    for (String item : temp) {
                        PosterEntity temp2 = list.get(item);
                        posterJoinListViewAdapter.append(temp2);
                    }

                } else {
                    Log.d("TAG", "No such document");
                }
            } else {
                Log.d("TAG", "get failed with ", task.getException());
            }
        });
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        binding = FragmentPosterListBinding.inflate(getLayoutInflater());
        posterJoinListViewAdapter = new PosterListViewAdapter();
        binding.framentPosterListListView.setAdapter(posterJoinListViewAdapter);
        status = false;

        posterFullListViewAdapter = new PosterListViewAdapter();
        binding.searchLayout.getLayoutParams().height = 0;
        ArrayList kind = new ArrayList();
        kind.add("전체");
        kind.add("강의");
        kind.add("자격증");

        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, kind);
        binding.fragmentPosterListSpiner1.setAdapter(adapter);
        getLectureList();
//        getJoinLectureList(firebaseAuth.getCurrentUser().getEmail());

        enter_lecture_listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final PosterEntity item = (PosterEntity) posterJoinListViewAdapter.getItem(position);
                MyFragment.changeFragment(new PosterMainFragment(item.getTitle(), true));

                //텍스트뷰에 출력
                System.out.println(item.getTitle() + " 에 접속함?");
            }
        };


        //리스트뷰의 아이템을 클릭시 해당 아이템의 문자열을 가져오기 위한 처리
        binding.framentPosterListListView.setOnItemClickListener(enter_lecture_listener);

        join_lecture_listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final PosterEntity item = (PosterEntity) presentAdapter.getItem(position);

                addJoinLecture(firebaseAuth.getCurrentUser().getEmail(), item.getTitle()); // 유저 강의 게시판 가입
                Toast.makeText(getActivity(), "강의 게시판에 가입", Toast.LENGTH_SHORT).show();

                System.out.println(item.getTitle() + "에 가입함?");

            }
        };

        binding.fragmentPosterListSpiner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (status) {
                    String text = binding.fragmentPosterListSpiner1.getSelectedItem().toString();
                    switch (text) {
                        case "전체":
                            binding.framentPosterListListView.setAdapter(posterFullListViewAdapter);
                            break;
                        case ("강의"):
                            binding.framentPosterListListView.setAdapter(posterListViewAdapter_2);
                            break;
                        case ("자격증"):
                            binding.framentPosterListListView.setAdapter(posterListViewAdapter_3);
                            break;
                        default:
                            break;
                    }
                } else {
                    status = true;
                    binding.fragmentPosterListSpiner1.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.fragmentPosterListSearchButton.setOnClickListener((v) -> {
            PosterListViewAdapter presentAdapter = null;
            if (binding.fragmentPosterListSpiner1.getSelectedItem().toString().equals("전체")) {
                presentAdapter = posterFullListViewAdapter;
            } else if (binding.fragmentPosterListSpiner1.getSelectedItem().toString().equals("강의")) {
                presentAdapter = posterListViewAdapter_2;
            } else if (binding.fragmentPosterListSpiner1.getSelectedItem().toString().equals("자격증")) {
                presentAdapter = posterListViewAdapter_3;
            }
            PosterListViewAdapter searchAdapter = new PosterListViewAdapter();
            for (PosterEntity item : presentAdapter.list) {
                if (item.getTitle().contains(binding.fragmentPosterListSearchTextView.getText().toString())) {
                    searchAdapter.append(item);
                }
            }
            presentAdapter = searchAdapter;
            binding.framentPosterListListView.setAdapter(searchAdapter);
        });
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() { // schedule: 특정한 시간에 원하는 작업 수행, 이거 인자가 어떻게 되는 건가요?
                getJoinLectureList(firebaseAuth.getCurrentUser().getEmail());
            }
        }, 1500); // long delay, long period, 지정한 시간부터 일정 간격(period)로 지정한 작업(tast)수
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.poster_search_menu, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("참여중인 강의 게시판");
        actionBar.setDisplayHomeAsUpEnabled(false);
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    //menu
    public boolean onOptionsItemSelected(MenuItem item) {

        int curId = item.getItemId();
        switch (curId) {
            case R.id.action_poster_search:
                ViewGroup.LayoutParams params = binding.searchLayout.getLayoutParams();

                if (params.height == 0) {
                    params.height = 150;
                    binding.framentPosterListListView.setAdapter(posterFullListViewAdapter);
                    presentAdapter = posterFullListViewAdapter;
                    ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
                    actionBar.setTitle("전체 강의 게시판 목록");
                    binding.searchLayout.setLayoutParams(params);
                    binding.framentPosterListListView.setOnItemClickListener(join_lecture_listener);

                } else {
                    params.height = 0;
                    posterJoinListViewAdapter.clear();
                    binding.framentPosterListListView.setAdapter(posterJoinListViewAdapter);
                    presentAdapter = posterJoinListViewAdapter;
                    getJoinLectureList(firebaseAuth.getCurrentUser().getEmail());
                    ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
                    actionBar.setTitle("참여중인 게시판 목록");
                    binding.searchLayout.setLayoutParams(params);
                    binding.framentPosterListListView.setOnItemClickListener(enter_lecture_listener);

                }


                return true;
            case android.R.id.home:
                MyFragment.prevFragment();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}