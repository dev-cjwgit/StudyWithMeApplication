package kr.ac.koreatech.teamproject;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import kr.ac.koreatech.teamproject.databinding.FragmentStudyMainBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudyMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudyMainFragment extends Fragment {
    private FragmentStudyMainBinding binding;
    private List<String> list = new ArrayList<>();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String title;

    public StudyMainFragment(String title) {
        this.title = title;
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudyFagment.
     */
    // TODO: Rename and change types and number of parameters
    public static StudyMainFragment newInstance(String param1, String param2) {
        StudyMainFragment fragment = new StudyMainFragment("");
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
        binding = FragmentStudyMainBinding.inflate(getLayoutInflater());


        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, list);
        binding.framentStudyListView.setAdapter(adapter);

        binding.fragmentStudyButtonSend.setOnClickListener((v) -> {
            list.add(binding.fragmentStudyEditTextSend.getText().toString());
            binding.fragmentStudyEditTextSend.setText("");
            adapter.notifyDataSetChanged();
        });
    }

    // Hi
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(title);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.studymenu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int curId = item.getItemId();
        switch (curId) {
            case R.id.action_online:
                System.out.println("현활 보려고??");
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}