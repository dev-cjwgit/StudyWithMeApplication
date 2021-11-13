package kr.ac.koreatech.teamproject;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;

import entity.PosterQuestionEntity;
import kr.ac.koreatech.teamproject.databinding.FragmentPosterQuestionListBinding;
import kr.ac.koreatech.teamproject.databinding.FragmentPosterQuestionMainBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PosterQuestionMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PosterQuestionMainFragment extends Fragment {
    private FragmentPosterQuestionMainBinding binding;
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