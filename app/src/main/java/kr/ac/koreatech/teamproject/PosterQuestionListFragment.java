package kr.ac.koreatech.teamproject;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.Date;

import adapter.PosterListViewAdapter;
import adapter.PosterQuestionListViewAdapter;
import appcomponent.MyFragment;
import entity.PosterEntity;
import entity.QuestionListEntity;
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
    private PosterQuestionListViewAdapter posterQuestionListViewAdapter;
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
        posterQuestionListViewAdapter = new PosterQuestionListViewAdapter();

        binding.framentPosterQuestionListListView.setAdapter(posterQuestionListViewAdapter);
        posterQuestionListViewAdapter.append(new QuestionListEntity("질문있습니다", "최진우", 3, new Date()));
        posterQuestionListViewAdapter.append(new QuestionListEntity("액티비티란 무엇입니까?", "홍길동", 1, new Date()));
        posterQuestionListViewAdapter.append(new QuestionListEntity("객체지향 5대 원칙이 궁금합니다", "김철수", 5, new Date()));
        posterQuestionListViewAdapter.append(new QuestionListEntity("라즈베리파이 고장났는데 어케 하나요 ㅠ", "김영희", 7, new Date()));
        //리스트뷰의 아이템을 클릭시 해당 아이템의 문자열을 가져오기 위한 처리
        binding.framentPosterQuestionListListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a_parent, View a_view, int a_position, long a_id) {
                final PosterEntity item = (PosterEntity) posterQuestionListViewAdapter.getItem(a_position);
                MyFragment.changeFragment(new PosterMainFragment(item.getTitle(), true));

                //텍스트뷰에 출력
                System.out.println(item.getTitle() + " 에 클릭함?");
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}