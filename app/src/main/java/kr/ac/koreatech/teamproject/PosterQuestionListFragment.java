package kr.ac.koreatech.teamproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import adapter.PosterQuestionListViewAdapter;
import appcomponent.MyFragment;
import entity.PosterQuestionEntity;
import kr.ac.koreatech.teamproject.databinding.FragmentPosterQuestionListBinding;

public class PosterQuestionListFragment extends Fragment {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FragmentPosterQuestionListBinding binding;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private PosterQuestionListViewAdapter posterQuestionListViewAdapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String title;

    public PosterQuestionListFragment() {
        // Required empty public constructor
    }

    public PosterQuestionListFragment(String title) {
        this.title = title;
    }

    // poster_title의 모든 QnA 불러오기
    private void getPosterQnA(String poster_title) {
        db.collection("QnA" + poster_title)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String[] info = document.getId().replace("|", "@").split("@");
                            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                            try {
                                posterQuestionListViewAdapter.append(new PosterQuestionEntity(info[0],
                                        ((ArrayList<String>) document.getData().get("body")).get(0),
                                        info[1],
                                        0,
                                        transFormat.parse(info[2])));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Log.d("TAG", document.getId() + " => " + document.getData());
                        }
                    } else {
                        Log.d("TAG", "Error getting documents: ", task.getException());
                    }
                });
    }

    // TODO: Rename and change types and number of parameters
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        binding = FragmentPosterQuestionListBinding.inflate(getLayoutInflater());
        posterQuestionListViewAdapter = new PosterQuestionListViewAdapter();
        binding.createQnAButton.setOnClickListener(v -> {
            // TODO: 글쓰는 인텐트
            Intent intent = new Intent(getActivity(), QnaDialogActivity.class);
            intent.putExtra("title", title);
            startActivity(intent);

        });
        binding.framentPosterQuestionListListView.setAdapter(posterQuestionListViewAdapter);

        //리스트뷰의 아이템을 클릭시 해당 아이템의 문자열을 가져오기 위한 처리
        binding.framentPosterQuestionListListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a_parent, View a_view, int a_position, long a_id) {
                final PosterQuestionEntity item = (PosterQuestionEntity) posterQuestionListViewAdapter.getItem(a_position);
                MyFragment.changeFragment(new PosterQuestionMainFragment(item, title));
                //텍스트뷰에 출력
                System.out.println(item.getTitle() + " 에 질문글에 접속함?");
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        posterQuestionListViewAdapter.clear();
        getPosterQnA(title);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("질의응답 목록");
        actionBar.setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int curId = item.getItemId();
        switch (curId) {

            case android.R.id.home:
                MyFragment.prevFragment();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}