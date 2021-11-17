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
import kr.ac.koreatech.teamproject.databinding.FragmentStudyMakeBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudyJoinFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudyMakeFragment extends Fragment {
    private FragmentStudyMakeBinding binding;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private boolean back_btn = false;
    private String mParam1;
    private String mParam2;

    public StudyMakeFragment() {
        // Required empty public constructor
    }

    public StudyMakeFragment(boolean back_btn) {
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

        binding = FragmentStudyMakeBinding.inflate(getLayoutInflater());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("스터디 그룹 생성");
        actionBar.setDisplayHomeAsUpEnabled(back_btn);
        //actionBar.setDisplayHomeAsUpEnabled(false);
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    //새로추가함
    /*public boolean onOptionsItemSelected(MenuItem item){
        int curId=item.getItemId();
        switch (curId){
            case R.id.btn_studyGroupMake:
                *//*ViewGroup.LayoutParams params2=binding.studyJoinSearchLayout.getLayoutParams();
                if(params2.height==0){
                    params2.height=150;
                }else{
                    params2.height=0;
                }
                binding.studyJoinSearchLayout.setLayoutParams(params2);
                return true;
            default:
                break;*//*
        }
        return super.onOptionsItemSelected(item);
    }*/
}