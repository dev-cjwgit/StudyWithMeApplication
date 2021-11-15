package kr.ac.koreatech.teamproject;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

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

import adapter.FrontRecyclerViewAdapter;
import adapter.StudyRecyclerViewAdapter;
import entity.FrontPoster;
import entity.PosterEntity;
import entity.StudyEntity;
import kr.ac.koreatech.teamproject.databinding.FragmentStudyListBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudyListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudyListFragment extends Fragment {
    private FragmentStudyListBinding binding;

    private StudyRecyclerViewAdapter m1Adapter;
    private LinearLayoutManager m1LayoutManager;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private boolean back_btn = false;
    private String mParam1;
    private String mParam2;

    public StudyListFragment() {
        // Required empty public constructor
    }

    public StudyListFragment(boolean back_btn) {
        // Required empty public constructor
        this.back_btn = back_btn;
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

        /*//
        binding.studySearchLayout.getLayoutParams().height=0;
        ArrayList search_kind=new ArrayList();
        search_kind.add("강의");
        search_kind.add("자격증");
        search_kind.add("기타");
        ArrayAdapter adapter2=new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,search_kind);
        binding.fragmentStudyListSpinner.setAdapter(adapter2);
        //*/

        ArrayList<StudyEntity> data1 = new ArrayList<>();


        data1.add(new StudyEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "천체연구모임", "스타스타", 4, "밤하늘을 관측하는 스터디 모임입니다."));
        data1.add(new StudyEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "KafkaS", "백엔드", 7, "백엔드의 카프카에 대해서 공부하는 모임방입니다."));
        data1.add(new StudyEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "아Do!이노", "인호", 12, "아두이노에 대해 공부하는 스터디입니다~"));
        data1.add(new StudyEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "라즈베리PI", "3.14", 3, "라즈베리파이의에 대해서 연구합니다."));
        data1.add(new StudyEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "신박한아이디어", "노력노력", 6, "기가막히다고 생각되는 아이디어를 공유하는 모임"));
        data1.add(new StudyEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "한기대파이썬공부방", "helloPhyton", 23, "파이썬 기초에 대해서 모임하여 공부합니다."));
        data1.add(new StudyEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "일터학습개론", "김영은", 38, "일 또는 일 밖에서 일어나는 학습에 대해서 배웁니다."));
        data1.add(new StudyEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "정보처리기사가 갖고싶어요", "정처기", 17, "전박전인 정처기에 대해서 공부할거예용~!"));
        data1.add(new StudyEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "컴활1급 아자아자!", "컴활짱", 32, "요즘 시대에 도움이 되는 컴활 1급을 위한 스터디 모임입니다."));
        //리스트뷰의 아이템을 클릭시 해당 아이템의 문자열을 가져오기 위한 처리
        m1LayoutManager = new LinearLayoutManager(this.getActivity());
        m1LayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // 기본값이 VERTICAL


        // setLayoutManager
        binding.fragmentStudyListStudyRecyclerview.setLayoutManager(m1LayoutManager);


        // init Adapter
        m1Adapter = new StudyRecyclerViewAdapter();
        // set Data
        m1Adapter.setData(data1);
        // set Adapter
        binding.fragmentStudyListStudyRecyclerview.setAdapter(m1Adapter);



        binding.fragmentStudyListAddButton.setOnClickListener((v) -> {

            MainActivity main = ((MainActivity) getActivity());
            FragmentManager fm = main.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment1, new StudyJoinFragment(true));
            fragmentTransaction.commit();

        });
    }
    /*//새로 만듬
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.study_list_menu, menu);
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("참여중인 스터디 그룹");
        actionBar.setDisplayHomeAsUpEnabled(back_btn);
        return binding.getRoot();
    }

    //menu
    /*public boolean onOptionsItemSelected(MenuItem item){
        int curId=item.getItemId();
        switch (curId){
            case R.id.action_studyList_search:
                ViewGroup.LayoutParams params2=binding.studySearchLayout.getLayoutParams();
                if(params2.height==0){
                    params2.height=150;
                }else{
                    params2.height=0;
                }
                binding.studySearchLayout.setLayoutParams(params2);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }*/
}