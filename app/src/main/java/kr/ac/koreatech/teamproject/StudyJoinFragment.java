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
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private boolean back_btn = false;
    private String mParam1;
    private String mParam2;

    public StudyJoinFragment() {
        // Required empty public constructor
    }
    public StudyJoinFragment(boolean back_btn) {
        // Required empty public constructor
        this.back_btn = back_btn;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudyJoinFragment.
     */
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

        /*//
        binding.studyJoinSearchLayout.getLayoutParams().height=0;
        ArrayList search_kind2=new ArrayList();
        search_kind2.add("강의");
        search_kind2.add("자격증");
        search_kind2.add("기타");
        ArrayAdapter adapter3=new ArrayAdapter(getContext(),android.R.layout.simple_list_item_2,search_kind2);
        binding.fragmentStudyJoinSpinner.setAdapter(adapter3);
        //*/

        List<String> list = new ArrayList<>();
        list.add("(가입가능)01번 스터디 그룹");
        list.add("(가입가능)02번 스터디 그룹");
        list.add("(가입가능)03번 스터디 그룹");
        list.add("(가입가능)04번 스터디 그룹");
        list.add("(가입가능)05번 스터디 그룹");
        list.add("(가입가능)06번 스터디 그룹");
        list.add("(가입가능)07번 스터디 그룹");
        list.add("(가입가능)08번 스터디 그룹");
        list.add("(가입가능)09번 스터디 그룹");
        list.add("(가입가능)10번 스터디 그룹");
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
        actionBar.setDisplayHomeAsUpEnabled(back_btn);
        //actionBar.setDisplayHomeAsUpEnabled(false);
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.study_join_menu, menu);
        /*MenuItem searchItem = menu.findItem(R.id.action_search);

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
        });*/
    }

    //새로추가함
    public boolean onOptionsItemSelected(MenuItem item){
        int curId=item.getItemId();
        switch (curId){
            case R.id.action_search:
                ViewGroup.LayoutParams params2=binding.studyJoinSearchLayout.getLayoutParams();
                if(params2.height==0){
                    params2.height=150;
                }else{
                    params2.height=0;
                }
                binding.studyJoinSearchLayout.setLayoutParams(params2);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}