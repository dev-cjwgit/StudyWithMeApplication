package kr.ac.koreatech.teamproject;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import adapter.AnsbulletSharingMatAdapter;
import appcomponent.MyFragment;
import entity.AnssharingEntity;
import entity.BulletinSharingMaterialsEntity;
import kr.ac.koreatech.teamproject.databinding.FragmentAnsbulletSharingMatBinding;

public class fragment_ansbullet_sharing_mat extends Fragment {
    private FragmentAnsbulletSharingMatBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private AnsbulletSharingMatAdapter ansbulletSharingMatAdapter;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private BulletinSharingMaterialsEntity entity;

    private List<String> list = new ArrayList<>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String title;

    public fragment_ansbullet_sharing_mat(BulletinSharingMaterialsEntity entity, String title) {
        // Required empty public constructor
        this.entity = entity;
        this.title = title;
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
        binding.anssharingMainBody.setText(entity.getBody());
        SimpleDateFormat sDate4 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        binding.anssharingDateTextView.setText(sDate4.format(entity.getDate()));
        SimpleDateFormat sDate2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        binding.anssharingMainBody.setMovementMethod(new ScrollingMovementMethod());
        binding.anssharingAnsTextView.setOnClickListener(v -> {
            System.out.println("답변 달꺼지? 답변하기 클릭했나?");
            Intent intent = new Intent(getActivity(), AnswerSharingDialogActivity.class);
            intent.putExtra("poster_title", title);
            String temp;
            temp = entity.getTitle() + "|" + entity.getName() + "|" + sDate2.format(entity.getDate());
            intent.putExtra("qnainfo", temp);
            startActivity(intent);


        });


        ansbulletSharingMatAdapter = new AnsbulletSharingMatAdapter();

        binding.fragmentAnssharingListView.setAdapter(ansbulletSharingMatAdapter);
        //
    }

    @Override
    public void onResume() {
        super.onResume();
        ansbulletSharingMatAdapter.clear();
        SimpleDateFormat sDate2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        getPosterInfoAnswer(title, entity.getTitle() + "|" + entity.getName() + "|" + sDate2.format(entity.getDate()));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("정보 공유");
        actionBar.setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
        return binding.getRoot();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int curId = item.getItemId();
        switch (curId) {

            case android.R.id.home:
                MyFragment.prevFragment();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void getPosterInfoAnswer(String poster_title, String qnainfo) {
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        DocumentReference docRef = db.collection("Info" + poster_title).document(qnainfo);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    ArrayList<String> lists = (ArrayList<String>) document.getData().get("list");
                    for (String item : lists) {
                        try {
                            String[] info = item.replace("|", "@").split("@");
                            ansbulletSharingMatAdapter.append(new AnssharingEntity(info[0], 0, transFormat.parse(info[2]), info[1]));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
//                    ansbulletSharingMatAdapter.append(new AnssharingEntity());
                    Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                } else {
                    Log.d("TAG", "No such document");
                }
            } else {
                Log.d("TAG", "get failed with ", task.getException());
            }
        });
    }

}