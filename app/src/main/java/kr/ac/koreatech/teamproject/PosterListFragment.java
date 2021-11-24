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
import appcomponent.MyFragment;
import entity.PosterEntity;
import kr.ac.koreatech.teamproject.databinding.FragmentPosterListBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PosterListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PosterListFragment extends Fragment {
    private FragmentPosterListBinding binding;
    private PosterListViewAdapter posterListViewAdapter;
    private PosterListViewAdapter posterFullListViewAdapter; //전체 게시판 목록

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

    private Object MenuInflater;

    public PosterListFragment() {
        // Required empty public constructor
    }

    public PosterListFragment(boolean back_btn) {
        this.back_btn = back_btn;
    }


    private void getLectureList() {
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

                            posterFullListViewAdapter.append(entity1);
                            list.put(document.getId(),entity1);

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
    private void getJoinLectureList(String user_email) {
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
                        posterListViewAdapter.append(temp2);
                    }

                } else {
                    Log.d("TAG", "No such document");
                }
            } else {
                Log.d("TAG", "get failed with ", task.getException());
            }
        });
    }


    public static PosterListFragment newInstance(String param1, String param2) {
        PosterListFragment fragment = new PosterListFragment();
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
        binding = FragmentPosterListBinding.inflate(getLayoutInflater());
        posterListViewAdapter = new PosterListViewAdapter();
        binding.framentPosterListListView.setAdapter(posterListViewAdapter);
        posterFullListViewAdapter = new PosterListViewAdapter();


        binding.searchLayout.getLayoutParams().height = 0;
        ArrayList kind = new ArrayList();
        kind.add("강의");
        kind.add("자격증");

        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, kind);
        binding.fragmentPosterListSpiner1.setAdapter(adapter);
        getLectureList();
        getJoinLectureList(firebaseAuth.getCurrentUser().getEmail());

        enter_lecture_listener=new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final PosterEntity item = (PosterEntity) posterListViewAdapter.getItem(position);
                MyFragment.changeFragment(new PosterMainFragment(item.getTitle(), true));

                //텍스트뷰에 출력
                System.out.println(item.getTitle() + " 에 접속함?");
            }
        };



/*        posterListViewAdapter.append(new PosterEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "모바일프로그래밍", "강승우", 38, "안드로이드 스튜디오를 이용하여 앱을 만듭니다."));
        posterListViewAdapter.append(new PosterEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "객체지향개발론및실습", "김상진", 42, "객체지향의 5대 원칙 등을 배웁니다."));
        posterListViewAdapter.append(new PosterEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "컴퓨터네트워크", "박승철", 45, "컴퓨터의 OSI 7계층에 대해서 "));
        posterListViewAdapter.append(new PosterEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "IoT개론및실습", "강승우", 23, "라즈베리파이의 GPIO에 대해서 학습합니다."));
        posterListViewAdapter.append(new PosterEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "공학설계", "조태훈", 6, "졸업설계를 위한 아이디어를 구상합니다."));
        posterListViewAdapter.append(new PosterEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "스크립트프로그래밍", "한연희", 40, "파이썬의 기초 문법을 배웁니다."));
        posterListViewAdapter.append(new PosterEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "일터학습개론", "김영은", 38, "일 또는 일 밖에서 일어나는 학습에 대해서 배웁니다."));
        posterListViewAdapter.append(new PosterEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "소프트웨어공학", "김승희", 17, "설계전략, 모델링등에 대해서 배웁니다."));
        posterListViewAdapter.append(new PosterEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "자바프로그래밍", "김상진", 32, "자바 기초 문법에 대해 학습합니다."));*/

        //리스트뷰의 아이템을 클릭시 해당 아이템의 문자열을 가져오기 위한 처리
        binding.framentPosterListListView.setOnItemClickListener(enter_lecture_listener);

        join_lecture_listener=new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final PosterEntity item = (PosterEntity) posterFullListViewAdapter.getItem(position);

                addJoinLecture(firebaseAuth.getCurrentUser().getEmail(), item.getTitle()); // 유저 강의 게시판 가입
                Toast.makeText(getActivity(), "강의 게시판에 가입", Toast.LENGTH_SHORT).show();

                System.out.println(item.getTitle() + "에 가입함?");

            }
        };
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
                    ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
                    actionBar.setTitle("전체 강의 게시판 목록");
                    binding.searchLayout.setLayoutParams(params);
                    binding.framentPosterListListView.setOnItemClickListener(join_lecture_listener);

                } else {
                    params.height = 0;
                    posterListViewAdapter.list.clear();
                    binding.framentPosterListListView.setAdapter(posterListViewAdapter);
                    getJoinLectureList(firebaseAuth.getCurrentUser().getEmail());
                    ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
                    actionBar.setTitle("참여중인 게시판 목록");
                    binding.searchLayout.setLayoutParams(params);
                    binding.framentPosterListListView.setOnItemClickListener(enter_lecture_listener);

                }
                /*ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
                actionBar.setTitle("전체 강의 게시판 목록");
                binding.searchLayout.setLayoutParams(params);*/

/*                posterListViewAdapter_2.append(new PosterEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "과목1", "교수님1", 38, "참여 가능 게시판 테스트1"));
                posterListViewAdapter_2.append(new PosterEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "과목2", "교수님2", 38, "참여 가능 게시판 테스트2"));
                posterListViewAdapter_2.append(new PosterEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "과목3", "교수님3", 38, "참여 가능 게시판 테스트3"));
                posterListViewAdapter_2.append(new PosterEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "과목4", "교수님4", 38, "참여 가능 게시판 테스트4"));
                posterListViewAdapter_2.append(new PosterEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "과목5", "교수님5", 38, "참여 가능 게시판 테스트5"));
                posterListViewAdapter_2.append(new PosterEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "과목6", "교수님6", 38, "참여 가능 게시판 테스트6"));
                posterListViewAdapter_2.append(new PosterEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "과목7", "교수님7", 38, "참여 가능 게시판 테스트7"));
                posterListViewAdapter_2.append(new PosterEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "과목8", "교수님8", 38, "참여 가능 게시판 테스트8"));
                posterListViewAdapter_2.append(new PosterEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "과목9", "교수님9", 38, "참여 가능 게시판 테스트9"));
                posterListViewAdapter_2.append(new PosterEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "과목10", "교수님10", 38, "참여 가능 게시판 테스트10"));*/


                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}