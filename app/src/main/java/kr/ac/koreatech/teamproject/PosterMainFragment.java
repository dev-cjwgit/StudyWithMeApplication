package kr.ac.koreatech.teamproject;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import appcomponent.MyFragment;
import kr.ac.koreatech.teamproject.databinding.FragmentPosterMainBinding;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PosterMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PosterMainFragment extends Fragment {
    private FragmentPosterMainBinding binding;

    private DrawerLayout drawerLayout;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private boolean back_btn = false;
    private String mParam1;
    private String mParam2;
    private String title;

    public PosterMainFragment(String title, boolean back_btn) {
        // Required empty public constructor
        this.title = title;
        this.back_btn = back_btn;
    }

    public PosterMainFragment(String title) {
        // Required empty public constructor
        this(title, false);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PosterFragment.
     */
    public static PosterMainFragment newInstance(String param1, String param2) {
        PosterMainFragment fragment = new PosterMainFragment("");
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
        binding = FragmentPosterMainBinding.inflate(getLayoutInflater());

        binding.questionAnswerMoveTextView.setOnClickListener(v -> {
            MyFragment.changeFragment(new PosterQuestionListFragment());
            System.out.println("질의 응답방에 접속하려고?");
        });
        binding.lectureInfo.setOnClickListener(v -> {
            MyFragment.changeFragment(new fragment_bulletin_lec_info());
            System.out.println("강의 정보에 접속하려고?");
        });
        binding.shareInfo.setOnClickListener(v -> {
            MyFragment.changeFragment(new fragment_bulletin_sharing_materials());
            System.out.println("정보 공유에 접속하려고?");
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        System.out.println("파괴됨..");
    }

    //menu 생성 부분
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.post_search_menu, menu);
        drawerLayout = getActivity().findViewById(R.id.drawerLayout_poster_main);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(title);
        actionBar.setDisplayHomeAsUpEnabled(back_btn);
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int curId = item.getItemId();
        switch (curId) {
            //기존 질의응답 등 오버플로우 메뉴에 구현한 부분은 네비게이션 드로우로 다시 구현해야합니다.
            /*case R.id.question:
                MyFragment.changeFragment(new PosterQuestionListFragment());
                System.out.println("질의 응답방 접속하려고?");
                return true;*/

            case R.id.post_category:
                drawerLayout.openDrawer(Gravity.RIGHT);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}