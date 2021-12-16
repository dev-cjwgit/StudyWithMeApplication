package kr.ac.koreatech.teamproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import adapter.PosterQuestionAnswerListViewAdapter;
import adapter.PosterQuestionListViewAdapter;
import appcomponent.MyFragment;
import entity.PosterQuestionAnswerEntity;
import entity.PosterQuestionEntity;
import kr.ac.koreatech.teamproject.databinding.FragmentPosterQuestionListBinding;
import kr.ac.koreatech.teamproject.databinding.FragmentPosterQuestionMainBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PosterQuestionMainFragment} factory method to
 * create an instance of this fragment.
 */
public class PosterQuestionMainFragment extends Fragment {
    private FragmentPosterQuestionMainBinding binding;
    private List<String> list = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private PosterQuestionAnswerListViewAdapter posterQuestionAnswerListViewAdapter;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private PosterQuestionEntity entity;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String title;

    public PosterQuestionMainFragment(PosterQuestionEntity entity, String title) {
        // Required empty public constructor
        this.entity = entity;
        this.title = title;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        binding = FragmentPosterQuestionMainBinding.inflate(getLayoutInflater());
        binding.questionMainTitleTextView.setText(entity.getTitle());
        binding.questionMainNameTextView.setText(entity.getName());
        binding.questionMainBodyTextView.setText(entity.getBody());
        SimpleDateFormat sDate2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        binding.questionMainDateTextView.setText(sDate2.format(entity.getDate()));
        binding.questionMainAnswerTextView.setText(entity.getAnswer().toString());

        binding.questionMainBodyTextView.setMovementMethod(new ScrollingMovementMethod());
        binding.answerTextView.setOnClickListener(v -> {
            System.out.println("답변 달꺼지?");
            Intent intent = new Intent(getActivity(), AnswerDialogActivity.class);
            intent.putExtra("poster_title", title);
            String temp;
            temp = entity.getTitle() + "|" + entity.getName() + "|" + sDate2.format(entity.getDate());
            intent.putExtra("qnaInfo", temp);
            startActivity(intent);
        });

        posterQuestionAnswerListViewAdapter = new PosterQuestionAnswerListViewAdapter();

        binding.framentPosterQuestionMainListView.setAdapter(posterQuestionAnswerListViewAdapter);


    }

    @Override
    public void onResume() {
        super.onResume();
        posterQuestionAnswerListViewAdapter.clear();
        SimpleDateFormat sDate2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        getPosterQnAAnswer(title, entity.getTitle() + "|" + entity.getName() + "|" + sDate2.format(entity.getDate()));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("질의응답 상세");
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
    private void getPosterQnAAnswer(String poster_title, String qnainfo) {
        DocumentReference docRef = db.collection("QnA" + poster_title).document(qnainfo);

        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    ArrayList<String> answerList = (ArrayList<String>) document.getData().get("list");
                    for (String answer : answerList) {
                        String[] info = answer.replace("|", "@").split("@");
                        try {
                            posterQuestionAnswerListViewAdapter.append(new PosterQuestionAnswerEntity(info[0],
                                    info[1],
                                    transFormat.parse(info[3]),
                                    info[2]
                            ));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        System.out.println("");
                    }
                } else {
                    Log.d("TAG", "No such document");
                }
            } else {
                Log.d("TAG", "get failed with ", task.getException());
            }
        });
    }


}