package kr.ac.koreatech.teamproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import adapter.PosterQuestionAnswerListViewAdapter;
import adapter.PosterQuestionListViewAdapter;
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
    private PosterQuestionAnswerListViewAdapter posterQuestionAnswerListViewAdapter;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private PosterQuestionEntity entity;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PosterQuestionMainFragment(PosterQuestionEntity entity) {
        // Required empty public constructor
        this.entity = entity;
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
            startActivity(intent);
        });



        posterQuestionAnswerListViewAdapter = new PosterQuestionAnswerListViewAdapter();

        binding.framentPosterQuestionMainListView.setAdapter(posterQuestionAnswerListViewAdapter);
        for (int i = 0; i < entity.getAnswer(); i++) {
            posterQuestionAnswerListViewAdapter.append(new PosterQuestionAnswerEntity("라즈베리파이 이미저를 돌려주세요", "PIPI", new Date(),
                    "라즈베리파이 내부의 sd를 카드를 빼시고 새로운 카드를 구입하셔서 이미져 해주시면됩니다,\n" +
                            "그냥 sd card만 손상이 된 것 같아서...\n" +
                            "안되면 다시 질문 주시면 자세하게 알아보겠습니다.\n" +
                            "조금 만 더 자세하게 설명글을 올려주세요!\n" +
                            "1. 이게 안되면 ~~ 로 해보시구요\n" +
                            "2. 또 안되면 홈페이지를 접속하셔서 확인해보세요\n" +
                            "마지막으로도 안되면 라즈베리파이를 재 구매하시는 최악의 방법까지 생각해보셔야 할 것 같아요 ㅠ"));

        }
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
}