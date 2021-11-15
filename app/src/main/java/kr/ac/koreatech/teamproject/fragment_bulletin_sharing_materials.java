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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentBulletinSharingMaterialsBinding binding;
    private BulletinSharingMaterialsAdapter bulletinSharingMaterialsAdapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_bulletin_sharing_materials() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_bulletin_sharing_materials.
     */
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
        bulletinSharingMaterialsAdapter.append(new BulletinSharingMaterialsEntity("오늘 모프 강의 실강인가요?", "모바일 프로그래밍 1분반 실강인지 궁금해요!! \n같은 분반이신분 알려주세요 ㅠㅠ\n 저번 수업에 못 들어가서 모르네요.. ", "채니",new Date()));
        bulletinSharingMaterialsAdapter.append(new BulletinSharingMaterialsEntity("중간고사", "다들 중간고사 몇점맞음? \n본인은 87점", "춘식이는냥냥",new Date()));
        bulletinSharingMaterialsAdapter.append(new BulletinSharingMaterialsEntity("안드로이드 스튜디오 질문", "깃 연동하는데 에러나는데 도와주실분 계신가요? \n기프티콘 드릴게요!", "초보개발자",new Date()));
        bulletinSharingMaterialsAdapter.append(new BulletinSharingMaterialsEntity("맥북좋나요?", "컴공 맥북 쓸만한가요?\n그냥 궁금해서요..", "갓생살자",new Date()));
        bulletinSharingMaterialsAdapter.append(new BulletinSharingMaterialsEntity("강승우 교수님 수업스타일", "강승우 교수님 수업 잘하신다고 하는데 학점도 잘 주시나요?\n강승우 교수님 존경합니다..", "한기대두기대",new Date()));
        // click 이벤트 더 구현해야함
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
}