package kr.ac.koreatech.teamproject;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import kr.ac.koreatech.teamproject.databinding.FragmentPosterListBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PosterListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PosterListFragment extends Fragment {
    private FragmentPosterListBinding binding;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PosterListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PosterFragment.
     */
    // TODO: Rename and change types and number of parameters
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

        List<String> list = new ArrayList<>();
        list.add("모바일프로그래밍");
        list.add("소프트웨어공학");
        list.add("자바프로그래밍");
        list.add("IoT개론및실습");
        list.add("스크립트프로그래밍");
        list.add("객체지향개발론및실습");
        list.add("공학설계");
        list.add("창의적공학설계");
        list.add("c프로그래밍1");
        list.add("빅데이터개론");
        list.add("컴퓨터그래픽스");
        list.add("기초전기전자실습");
        list.add("c프로그래밍2");
        list.add("경영학개론");
        list.add("경제학원론");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, list);
        binding.framentPosterListListView.setAdapter(adapter);

        //리스트뷰의 아이템을 클릭시 해당 아이템의 문자열을 가져오기 위한 처리
        binding.framentPosterListListView.setOnItemClickListener((adapterView, view, position, id) -> {

            //클릭한 아이템의 문자열을 가져옴
            String selected_item = (String) adapterView.getItemAtPosition(position);
            MainActivity main = ((MainActivity) getActivity());
            FragmentManager fm = main.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment1, new PosterFragment(selected_item));
            fragmentTransaction.commit();

            //텍스트뷰에 출력
            System.out.println(selected_item + " 에 접속함?");
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("게시판");
        actionBar.setDisplayHomeAsUpEnabled(true);
        return binding.getRoot();
    }
}