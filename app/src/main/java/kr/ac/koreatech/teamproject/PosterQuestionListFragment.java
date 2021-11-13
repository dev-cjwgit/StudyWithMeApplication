package kr.ac.koreatech.teamproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.ac.koreatech.teamproject.databinding.FragmentPosterListBinding;
import kr.ac.koreatech.teamproject.databinding.FragmentPosterMainBinding;
import kr.ac.koreatech.teamproject.databinding.FragmentPosterQuestionListBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PosterQuestionListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PosterQuestionListFragment extends Fragment {
    private FragmentPosterQuestionListBinding binding;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PosterQuestionListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PosterQuestionList.
     */
    // TODO: Rename and change types and number of parameters
    public static PosterQuestionListFragment newInstance(String param1, String param2) {
        PosterQuestionListFragment fragment = new PosterQuestionListFragment();
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
        binding = FragmentPosterQuestionListBinding.inflate(getLayoutInflater());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}