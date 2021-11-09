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

import kr.ac.koreatech.teamproject.databinding.FragmentStudyListBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudyListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudyListFragment extends Fragment {
    private FragmentStudyListBinding binding;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public StudyListFragment() {
        // Required empty public constructor
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

        List<String> list = new ArrayList<>();
        list.add("정처기합격");
        list.add("CAD 스터디");
        list.add("C언어 모각코");
        list.add("과탑먹을사람들");
        list.add("모각코");
        list.add("공대생들어와");
        list.add("중국어같이공부");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, list);
        binding.framentStudyListListView.setAdapter(adapter);

        //리스트뷰의 아이템을 클릭시 해당 아이템의 문자열을 가져오기 위한 처리
        binding.framentStudyListListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {

                //클릭한 아이템의 문자열을 가져옴
                String selected_item = (String) adapterView.getItemAtPosition(position);
                MainActivity main = ((MainActivity) getActivity());
                FragmentManager fm = main.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment1, new StudyMainFragment(selected_item));
                fragmentTransaction.commit();
                //텍스트뷰에 출력
                System.out.println(selected_item + " 에 접속함?");
            }
        });

        binding.fragmentStudyListAddButton.setOnClickListener((v) -> {

            MainActivity main = ((MainActivity) getActivity());
            FragmentManager fm = main.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment1, new StudyJoinFragment());
            fragmentTransaction.commit();

        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("스터디 그룹 리스트");
        actionBar.setDisplayHomeAsUpEnabled(true);
        return binding.getRoot();
    }
}