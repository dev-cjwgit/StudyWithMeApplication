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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adapter.StudyListViewAdapter;
import appcomponent.MyFragment;
import entity.StudyEntity;
import kr.ac.koreatech.teamproject.databinding.FragmentStudyListBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudyListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudyListFragment extends Fragment {
    private FragmentStudyListBinding binding;
    private StudyListViewAdapter studyListViewAdapter;
    private StudyListViewAdapter studyListViewAdapter_2;
    public static Map<String, StudyEntity> list = new HashMap<>();

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // 모든 스터디 그룹에 대한 상세 정보 목록 가져오기
    private void getStudyGroupList() {
        db.collection("server/data/studyGroupList/")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("TAG", document.getId() + " => " + document.getData());
                            StudyEntity temp = new StudyEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), document.getId(), document.getData().get("nickname").toString(), Integer.parseInt(document.getData().get("currPeople").toString()), document.getData().get("introduce").toString());
                            studyListViewAdapter_2.append(temp);
                            list.put(document.getId(), temp);
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

    //->기존 코
    /*//private StudyRecyclerViewAdapter m1Adapter;
    private LinearLayoutManager m1LayoutManager;*/

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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudyFragment.
     */
    public static StudyListFragment newInstance(String param1, String param2) {
        StudyListFragment fragment = new StudyListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        binding.fragmentStudyListListView.setAdapter(studyListViewAdapter);
        studyListViewAdapter_2 = new StudyListViewAdapter();

        binding.studySearchLayout.getLayoutParams().height = 0;
        ArrayList search_kind = new ArrayList();
        search_kind.add("강의");
        search_kind.add("자격증");
        search_kind.add("기타");
        ArrayAdapter adapter2 = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, search_kind);
        binding.fragmentStudyListSpinner.setAdapter(adapter2);
        getStudyGroupList();
        getJoinStudyGroupList(firebaseAuth.getCurrentUser().getEmail());


//        studyListViewAdapter.append(new StudyEntity(BitmapFactory.decodeResource(getResources(),R.drawable.default_image),"천체연구모임","스타스타",4,"밤하늘을 관측하는 스터디 모임입니다."));
//        studyListViewAdapter.append(new StudyEntity(BitmapFactory.decodeResource(getResources(),R.drawable.default_image),"KafkaS", "백엔드", 7, "백엔드의 카프카에 대해서 공부하는 모임방입니다."));
//        studyListViewAdapter.append(new StudyEntity(BitmapFactory.decodeResource(getResources(),R.drawable.default_image),"아Do!이노", "인호", 12, "아두이노에 대해 공부하는 스터디입니다~"));
//        studyListViewAdapter.append(new StudyEntity(BitmapFactory.decodeResource(getResources(),R.drawable.default_image),"라즈베리PI", "3.14", 3, "라즈베리파이의에 대해서 연구합니다."));
//        studyListViewAdapter.append(new StudyEntity(BitmapFactory.decodeResource(getResources(),R.drawable.default_image),"신박한아이디어", "노력노력", 6, "기가막히다고 생각되는 아이디어를 공유하는 모임"));
//        studyListViewAdapter.append(new StudyEntity(BitmapFactory.decodeResource(getResources(),R.drawable.default_image),"한기대파이썬공부방", "helloPhyton", 23, "파이썬 기초에 대해서 모임하여 공부합니다."));
//        studyListViewAdapter.append(new StudyEntity(BitmapFactory.decodeResource(getResources(),R.drawable.default_image),"일터학습개론", "김영은", 38, "일 또는 일 밖에서 일어나는 학습에 대해서 배웁니다."));
//        studyListViewAdapter.append(new StudyEntity(BitmapFactory.decodeResource(getResources(),R.drawable.default_image),"정보처리기사가 갖고싶어요", "정처기", 17, "전전인 정처기에 대해서 공부할거예용~!"));
//        studyListViewAdapter.append(new StudyEntity(BitmapFactory.decodeResource(getResources(),R.drawable.default_image),"컴활1급 아자아자!", "컴활짱", 32, "요즘 시대에 도움이 되는 컴활 1급을 위한 스터디 모임입니다."));
        //리스트뷰의 아이템을 클릭시 해당 아이템의 문자열을 가져오기 위한 처리
        binding.fragmentStudyListListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a_parent, View a_view, int a_position, long a_id) {
                final StudyEntity item = (StudyEntity) studyListViewAdapter.getItem(a_position);
                MyFragment.changeFragment(new StudyMainFragment(item.getTitle(), true));

                //텍스트뷰에 출력
                System.out.println(item.getTitle() + " 에 접속함?");
            }
        });
        /*binding.fragmentStudyListListView.setOnClickListener(v->{
            Intent intent=new Intent(getActivity(),joinDialogActivity.class);
            startActivity(intent);
        });*/
        //


        binding.fragmentStudyListAddButton.setOnClickListener((v) -> {
            MainActivity main = ((MainActivity) getActivity());
            FragmentManager fm = main.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment1, new StudyMakeFragment(true));
            fragmentTransaction.commit();
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
                    params2.height = 150;
                } else {
                    params2.height = 0;
                }
                ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
                actionBar.setTitle("전체 스터디 그룹 목록");
                binding.studySearchLayout.setLayoutParams(params2);

                binding.fragmentStudyListListView.setAdapter(studyListViewAdapter_2);
                getStudyGroupList();


//                studyListViewAdapter_2.append(new StudyEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "스터디1", "사람1", 38, "참여 가능 스터디 테스트1"));
//                studyListViewAdapter_2.append(new StudyEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "스터디2", "사람2", 38, "참여 가능 스터디 테스트2"));
//                studyListViewAdapter_2.append(new StudyEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "스터디3", "사람3", 38, "참여 가능 스터디 테스트3"));
//                studyListViewAdapter_2.append(new StudyEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "스터디4", "사람4", 38, "참여 가능 스터디 테스트4"));
//                studyListViewAdapter_2.append(new StudyEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "스터디5", "사람5", 38, "참여 가능 스터디 테스트5"));
//                studyListViewAdapter_2.append(new StudyEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "스터디6", "사람6", 38, "참여 가능 스터디 테스트6"));
//                studyListViewAdapter_2.append(new StudyEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "스터디7", "사람7", 38, "참여 가능 스터디 테스트7"));

                //전체 게시판에서 스터디 그룹 가입하는 부분
                binding.fragmentStudyListListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> a_parent, View a_view, int a_position, long a_id) {
                        final StudyEntity item = (StudyEntity) studyListViewAdapter_2.getItem(a_position);
                        //MyFragment.changeFragment(new StudyMainFragment(item.getTitle(), true));

                        addJoinStudyGroup(firebaseAuth.getCurrentUser().getEmail(), item.getTitle()); // 유저가 스터디 그룹에 가입
                        //Intent intent = new Intent(getActivity(), JoinDialogActivity.class);
                        //startActivity(intent);

                        //텍스트뷰에 출력
                        System.out.println(item.getTitle() + " 에 가입함?");
                    }
                });

                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}