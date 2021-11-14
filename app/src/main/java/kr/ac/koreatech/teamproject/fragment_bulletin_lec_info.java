package kr.ac.koreatech.teamproject;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.ac.koreatech.teamproject.databinding.FragmentBulletinLecInfoBinding;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_bulletin_lec_info#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_bulletin_lec_info extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentBulletinLecInfoBinding binding;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_bulletin_lec_info() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_bulletin_lec_info.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_bulletin_lec_info newInstance(String param1, String param2) {
        fragment_bulletin_lec_info fragment = new fragment_bulletin_lec_info();
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
        binding = FragmentBulletinLecInfoBinding.inflate(getLayoutInflater());
        binding.LecIDTextview2.setText("모바일프로그래밍");
        binding.profIDTextview2.setText("강승우");
        binding.profemailTextview2.setText("swkang@koretech.ac.kr");
        binding.profdiaTextview2.setText("044-123-1122");
        binding.assiEmailTextview2.setText("cy0804bamboo@naver.com");
        binding.lectureplan.setText("1주차 | 과목 소개, 안드로이드 앱 개발 개요\n2주차 | 사용자 인터페이스 기초: UI 개요, 기본 뷰, 레이아웃\n3주차 | 사용자 인터페이스 기초: 이벤트 처리\n4주차 | 이벤트 처리\n5주차 | 메뉴와 대화상자\n6주차 | 액티비티와 인텐트\n7주차 | 액티비티 생명주기\n8주차 | 파일 처리\n9주차 | 어댑터 뷰 / 서비스\n10주차 | 브로드캐스트 리시버/Firebase\n");
        binding.mainTbTextview2.setText("그림으로 쉽게 설명하는 안드로이드 프로그래밍, 생능출판, 천인국 지음\n" +
                "이것이 안드로이드다. 박성근의 안드로이드 앱 프로그래밍, 한빛미디어, 박성근 지음");
        binding.subTbTextview2.setText("Eclipse를 활용한 안드로이드 프로그래밍, 한빛아카데미, 우재남/이복기 지음");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("강의 정보");
        actionBar.setDisplayHomeAsUpEnabled(true);
        return binding.getRoot();
    }
}