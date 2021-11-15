package kr.ac.koreatech.teamproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.Date;

import adapter.AnsbulletSharingMatAdapter;
import entity.AnssharingEntity;
import entity.BulletinSharingMaterialsEntity;
import kr.ac.koreatech.teamproject.databinding.FragmentAnsbulletSharingMatBinding;

public class fragment_ansbullet_sharing_mat extends Fragment {
    private FragmentAnsbulletSharingMatBinding binding;

    private AnsbulletSharingMatAdapter ansbulletSharingMatAdapter;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private BulletinSharingMaterialsEntity entity;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_ansbullet_sharing_mat(BulletinSharingMaterialsEntity entity) {
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

        binding = FragmentAnsbulletSharingMatBinding.inflate(getLayoutInflater());
        binding.anssharingTitleTextView.setText(entity.getTitle());
        binding.anssharingNameTextView.setText(entity.getName());
        binding.anssharingAnsTextView.setText(entity.getBody());
        SimpleDateFormat sDate4 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        binding.anssharingDateTextView.setText(sDate4.format(entity.getDate()));

        binding.anssharingMainBody.setMovementMethod(new ScrollingMovementMethod());
        binding.anssharingAnsTextView.setOnClickListener(v -> {
            System.out.println("답변 달꺼지?");
            Intent intent = new Intent(getActivity(), AnswerDialogActivity.class);
            startActivity(intent);

        });

        ansbulletSharingMatAdapter = new AnsbulletSharingMatAdapter();

        binding.fragmentAnssharingListView.setAdapter(ansbulletSharingMatAdapter);
        for (int i = 0; i < entity.getAnswer(); i++) {
            ansbulletSharingMatAdapter.append(new AnssharingEntity("실강","곰돌", 3, new Date(),"실강없어요."));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("정보 공유 답변");
        actionBar.setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
        return binding.getRoot();
    }
}