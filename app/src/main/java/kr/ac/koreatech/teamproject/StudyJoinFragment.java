package kr.ac.koreatech.teamproject;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import kr.ac.koreatech.teamproject.databinding.FragmentStudyJoinBinding;
import kr.ac.koreatech.teamproject.databinding.FragmentStudyListBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudyJoinFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudyJoinFragment extends Fragment {
    private FragmentStudyJoinBinding binding;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StudyJoinFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudyJoinFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StudyJoinFragment newInstance(String param1, String param2) {
        StudyJoinFragment fragment = new StudyJoinFragment();
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

        binding = FragmentStudyJoinBinding.inflate(getLayoutInflater());
        List<String> list = new ArrayList<>();
        list.add("집으로 가즈앙");
        list.add("기분 좋은 아침");
        list.add("안드로이드");
        list.add("아이폰은 전설이다");
        list.add("공부가 싫어");
        list.add("과제도 싫엉");
        list.add("공부 멈춰!!!!");
        list.add("안스 어렵다...");
        list.add("m1맥은 쓰레기야.");
        list.add("안드로이드 스튜디오 멍청이");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, list);
        binding.framentStudyJoinListView.setAdapter(adapter);

        //리스트뷰의 아이템을 클릭시 해당 아이템의 문자열을 가져오기 위한 처리
        binding.framentStudyJoinListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {

                //클릭한 아이템의 문자열을 가져옴
                String selected_item = (String) adapterView.getItemAtPosition(position);

                //텍스트뷰에 출력
                System.out.println(selected_item + " 에 가입하려고?");
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("스터디 그룹 모집");
        actionBar.setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.study_join_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("plz in search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String s) {
                System.out.println(s + " 검색하려고?");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }
}