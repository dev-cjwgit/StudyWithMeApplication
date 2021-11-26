package kr.ac.koreatech.teamproject;

import androidx.appcompat.app.ActionBar;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import adapter.BulletinSharingMaterialsAdapter;
import appcomponent.MyFragment;

import entity.BulletinSharingMaterialsEntity;
import kr.ac.koreatech.teamproject.databinding.FragmentBulletinSharingMaterialsBinding;


public class fragment_bulletin_sharing_materials extends Fragment {
    private FragmentBulletinSharingMaterialsBinding binding;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private BulletinSharingMaterialsAdapter bulletinSharingMaterialsAdapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String title;

    public fragment_bulletin_sharing_materials(String title) {
        // Required empty public constructor
        this.title = title;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        binding = FragmentBulletinSharingMaterialsBinding.inflate(getLayoutInflater());
        bulletinSharingMaterialsAdapter = new BulletinSharingMaterialsAdapter();

        binding.sharingAddButton.setOnClickListener(v -> {
            System.out.println("새 info_sharing post ?");

            Intent intent = new Intent(getActivity(), Lec_info_Post_Activity.class);
            intent.putExtra("poster_title", title);
            startActivity(intent);
        });


        binding.framentsharingLecture.setAdapter(bulletinSharingMaterialsAdapter);
//        bulletinSharingMaterialsAdapter.append(new BulletinSharingMaterialsEntity("오늘 모프", "실강임?","춘식이",3, new Date()));
//        bulletinSharingMaterialsAdapter.append(new BulletinSharingMaterialsEntity("중간고사", "중간고사 점수 평균 올려주셨나요?","미쯔",2, new Date()));
        // click 이벤트 더 구현해야함
        getPosterInfo(title);
        binding.framentsharingLecture.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a_parent, View a_view, int a_postion, long a_id) {
                final BulletinSharingMaterialsEntity item = (BulletinSharingMaterialsEntity) bulletinSharingMaterialsAdapter.getItem(a_postion);
                MyFragment.changeFragment(new fragment_ansbullet_sharing_mat(item, title));
                //텍스트뷰에 출력
                System.out.println(item.getTitle() + " 에 질문글에 접속함?");
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("정보 공유 목록");
        actionBar.setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    private void getPosterInfo(String poster_title) {
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        db.collection("Info" + poster_title)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            //bulletinSharingMaterialsAdapter.append(new BulletinSharingMaterialsEntity(
                            String[] info = document.getId().replace("|", "@").split("@");
                            try {
                                bulletinSharingMaterialsAdapter.append(new BulletinSharingMaterialsEntity(
                                        info[0],
                                        ((ArrayList<String>) document.getData().get("body")).get(0),
                                        info[1],
                                        0,
                                        transFormat.parse(info[2])

                                ));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Log.d("TAG", document.getId() + " => " + document.getData());
                        }
                    } else {
                        Log.d("TAG", "Error getting documents: ", task.getException());
                    }
                });
    }

}