package kr.ac.koreatech.teamproject;

import androidx.appcompat.app.ActionBar;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.Date;

import adapter.BulletinSharingMaterialsAdapter;
import appcomponent.MyFragment;

import entity.BulletinSharingMaterialsEntity;
import kr.ac.koreatech.teamproject.databinding.FragmentBulletinSharingMaterialsBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_bulletin_sharing_materials#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_bulletin_sharing_materials extends Fragment {
    private FragmentBulletinSharingMaterialsBinding binding;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private BulletinSharingMaterialsAdapter bulletinSharingMaterialsAdapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_bulletin_sharing_materials() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static fragment_bulletin_sharing_materials newInstance(String param1, String param2) {
        fragment_bulletin_sharing_materials fragment = new fragment_bulletin_sharing_materials();
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
        binding = FragmentBulletinSharingMaterialsBinding.inflate(getLayoutInflater());
        bulletinSharingMaterialsAdapter = new BulletinSharingMaterialsAdapter();

        binding.framentsharingLecture.setAdapter(bulletinSharingMaterialsAdapter);
        bulletinSharingMaterialsAdapter.append(new BulletinSharingMaterialsEntity("오늘 모프", "실강임?","춘식이",3, new Date()));
        bulletinSharingMaterialsAdapter.append(new BulletinSharingMaterialsEntity("중간고사", "중간고사 점수 평균 올려주셨나요?","미쯔",2, new Date()));
        // click 이벤트 더 구현해야함

        binding.framentsharingLecture.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a_parent, View a_view, int a_postion, long a_id) {
                final BulletinSharingMaterialsEntity item = (BulletinSharingMaterialsEntity) bulletinSharingMaterialsAdapter.getItem(a_postion);
                MyFragment.changeFragment(new fragment_ansbullet_sharing_mat(item));
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
}