package kr.ac.koreatech.teamproject;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.content.Intent;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import adapter.StudyListViewAdapter;
import appcomponent.MyFragment;
import entity.StudyEntity;
import kr.ac.koreatech.teamproject.databinding.FragmentStudyListBinding;

public class StudyListFragment extends Fragment {
    private FragmentStudyListBinding binding;
    private StudyListViewAdapter studyListViewAdapter;
    private StudyListViewAdapter studyListViewAdapter_2=new StudyListViewAdapter();
    //강의 카테고리 스터디 그룹
    private StudyListViewAdapter studyListViewAdapter_3=new StudyListViewAdapter();
    //게시판 카테고리 스터디 그룹
    private StudyListViewAdapter study_FullListViewAdapter; //전체 스터디 그룹 목록
    public static Map<String, StudyEntity> list = new HashMap<>();

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private AdapterView.OnItemClickListener joinListener;
    private AdapterView.OnItemClickListener enter_Listener;


    // 모든 스터디 그룹에 대한 상세 정보 목록 가져오기
    private void getStudyGroupList() {
        db.collection("server/data/studyGroupList/")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("TAG", document.getId() + " => " + document.getData());
                            StudyEntity temp = new StudyEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), document.getId(), document.getData().get("nickname").toString(), Integer.parseInt(document.getData().get("currPeople").toString()), document.getData().get("introduce").toString());
                            study_FullListViewAdapter.append(temp);
                            list.put(document.getId(), temp);
                            if(document.getData().get("category").toString().equals("강의")){
                                studyListViewAdapter_2.append(temp);
                            }
                            if(document.getData().get("category").toString().equals("자격증")){
                                studyListViewAdapter_3.append(temp);
                            }
                        }
                    } else {
                        Log.d("TAG", "Error getting documents: ", task.getException());
                    }
                });
    }

    // 스터디 그룹에 가입(이메일, 가입 할 스터디 그룹 이름)
    private void addJoinStudyGroup(String user_email, String study_group_name) {
        user_email = user_email.replace(".", "-");
        DocumentReference washingtonRef = db.collection("server").document("user/" + user_email + "/joinStudyGroup/");
        washingtonRef.update("title", FieldValue.arrayUnion(study_group_name))
                .addOnSuccessListener(aVoid -> {
                    Log.d("TAG", "DocumentSnapshot successfully update");
                })
                .addOnFailureListener(e -> {
                    Log.w("TAG", "Error update document", e);
                });

//        System.out.println(db.document());
    }

    // 유저가 가입 중인 스터디 그룹 목록(상세) 가져오기(이메일)
    private void getJoinStudyGroupList(String user_email) {
        user_email = user_email.replace(".", "-");

        DocumentReference docRef = db.collection("server").document("user/" + user_email + "/joinStudyGroup/");
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                    ArrayList<String> temp = (ArrayList<String>) document.get("title");
                    for (String item : temp) {
                        StudyEntity temp2 = list.get(item);
                        studyListViewAdapter.append(temp2);
                    }
                    //studyListViewAdapter.append(new StudyEntity(BitmapFactory.decodeResource(getResources(),R.drawable.default_image),"천체연구모임","스타스타",4,"밤하늘을 관측하는 스터디 모임입니다."));
                } else {
                    Log.d("TAG", "No such document");
                }
            } else {
                Log.d("TAG", "get failed with ", task.getException());
            }
        });
    }

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private boolean back_btn = false;
    private String mParam1;
    private String mParam2;

    public StudyListFragment() {
        // Required empty public constructor
    }

    public StudyListFragment(boolean back_btn) {
        // Required empty public constructor
        this.back_btn = back_btn;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        binding = FragmentStudyListBinding.inflate(getLayoutInflater());
        studyListViewAdapter = new StudyListViewAdapter();
        study_FullListViewAdapter = new StudyListViewAdapter();
        getStudyGroupList();

        binding.studySearchLayout.getLayoutParams().height = 0;
        ArrayList search_kind = new ArrayList();
        search_kind.add("강의");
        search_kind.add("자격증");
        //search_kind.add("기타");
        ArrayAdapter adapter2 = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, search_kind);
        binding.fragmentStudyListSpinner.setAdapter(adapter2);

        enter_Listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a_parent, View a_view, int a_position, long a_id) {
                final StudyEntity item = (StudyEntity) studyListViewAdapter.getItem(a_position);
                MyFragment.changeFragment(new StudyMainFragment(item.getTitle(), true));
                //텍스트뷰에 출력
                System.out.println(item.getTitle() + " 에 접속함?");
            }
        };

        //리스트뷰의 아이템을 클릭시 스터디 그룹에 들어감(enter)
        binding.fragmentStudyListListView.setOnItemClickListener(enter_Listener);

        //리스트뷰 선택시 -> 가입(가입 리스너 설정)
        joinListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a_parent, View a_view, int a_position, long a_id) {
                final StudyEntity item = (StudyEntity) study_FullListViewAdapter.getItem(a_position);

                addJoinStudyGroup(firebaseAuth.getCurrentUser().getEmail(), item.getTitle()); // 유저가 스터디 그룹에 가입
                Toast.makeText(getActivity(), "스터디 그룹에 가입합니다.", Toast.LENGTH_SHORT).show();
                //텍스트뷰에 출력
                System.out.println(item.getTitle() + " 에 가입함?");
            }
        };

        //스터디 그룹 생성 버튼 누르면 발생하는 이벤트 처리
        binding.fragmentStudyListAddButton.setOnClickListener((v) -> {
            MainActivity main = ((MainActivity) getActivity());
            FragmentManager fm = main.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment1, new StudyMakeFragment(true));
            fragmentTransaction.commit();
        });
        binding.fragmentStudyListListView.setAdapter(studyListViewAdapter);
//        getJoinStudyGroupList(firebaseAuth.getCurrentUser().getEmail());

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() { // schedule: 특정한 시간에 원하는 작업 수행, 이거 인자가 어떻게 되는 건가요?
                getJoinStudyGroupList(firebaseAuth.getCurrentUser().getEmail());

            }
        }, 1500); // long delay, long period, 지정한 시간부터 일정 간격(period)로 지정한 작업(tast)수

        //카테고리 누르면 카테고리 별로 리스트 나뉨
        binding.fragmentStudyListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text=binding.fragmentStudyListSpinner.getSelectedItem().toString();
                switch(text){
                    case("강의"):
                        binding.fragmentStudyListListView.setAdapter(studyListViewAdapter_2);
                    case("자격증"):
                        binding.fragmentStudyListListView.setAdapter(studyListViewAdapter_3);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.study_list_menu, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("참여중인 스터디 그룹");
        actionBar.setDisplayHomeAsUpEnabled(false);
        //actionBar.setDisplayHomeAsUpEnabled(back_btn);
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    //menu
    public boolean onOptionsItemSelected(MenuItem item) {
        int curId = item.getItemId();
        switch (curId) {
            case R.id.action_studyList_search:
                ViewGroup.LayoutParams params2 = binding.studySearchLayout.getLayoutParams();
                if (params2.height == 0) {
                    params2.height = 150; //검색창 열림
                    binding.fragmentStudyListListView.setAdapter(study_FullListViewAdapter);
                    ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
                    actionBar.setTitle("전체 스터디 그룹 목록");
                    binding.studySearchLayout.setLayoutParams(params2);
                    binding.fragmentStudyListListView.setOnItemClickListener(joinListener);

                } else {
                    params2.height = 0; //검색창 닫힘
                    studyListViewAdapter.list.clear();
                    binding.fragmentStudyListListView.setAdapter(studyListViewAdapter);
                    getJoinStudyGroupList(firebaseAuth.getCurrentUser().getEmail());
                    ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
                    actionBar.setTitle("참여중인 스터디 그룹");
                    binding.studySearchLayout.setLayoutParams(params2);
                    binding.fragmentStudyListListView.setOnItemClickListener(enter_Listener);
                }

                //전체 게시판에서 스터디 그룹 가입하는 부분
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}