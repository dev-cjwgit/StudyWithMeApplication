package kr.ac.koreatech.teamproject;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import adapter.PosterListViewAdapter;
import appcomponent.MyFragment;
import entity.PosterEntity;
import kr.ac.koreatech.teamproject.databinding.FragmentPosterListBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PosterListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PosterListFragment extends Fragment {
    private FragmentPosterListBinding binding;
    private PosterListViewAdapter posterListViewAdapter;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private Object MenuInflater;

    public PosterListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PosterFragment.
     */
    public static PosterListFragment newInstance(String param1, String param2) {
        PosterListFragment fragment = new PosterListFragment();
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
        binding = FragmentPosterListBinding.inflate(getLayoutInflater());
        posterListViewAdapter = new PosterListViewAdapter();
        binding.framentPosterListListView.setAdapter(posterListViewAdapter);
        posterListViewAdapter.append(new PosterEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "모바일프로그래밍", "강승우", 38, "안드로이드 스튜디오를 이용하여 앱을 만듭니다."));
        posterListViewAdapter.append(new PosterEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "객체지향개발론및실습", "김상진", 42, "객체지향의 5대 원칙 등을 배웁니다."));
        posterListViewAdapter.append(new PosterEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "컴퓨터네트워크", "박승철", 45, "컴퓨터의 OSI 7계층에 대해서 "));
        posterListViewAdapter.append(new PosterEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "IoT개론및실습", "강승우", 23, "라즈베리파이의 GPIO에 대해서 학습합니다."));
        posterListViewAdapter.append(new PosterEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "공학설계", "조태훈", 6, "졸업설계를 위한 아이디어를 구상합니다."));
        posterListViewAdapter.append(new PosterEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "스크립트프로그래밍", "한연희", 40, "파이썬의 기초 문법을 배웁니다."));
        posterListViewAdapter.append(new PosterEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "일터학습개론", "김영은", 38, "일 또는 일 밖에서 일어나는 학습에 대해서 배웁니다."));
        posterListViewAdapter.append(new PosterEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "소프트웨어공학", "김승희", 17, "설계전략, 모델링등에 대해서 배웁니다."));
        posterListViewAdapter.append(new PosterEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image), "자바프로그래밍", "김상진", 32, "자바 기초 문법에 대해 학습합니다."));
        //리스트뷰의 아이템을 클릭시 해당 아이템의 문자열을 가져오기 위한 처리
        binding.framentPosterListListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a_parent, View a_view, int a_position, long a_id) {
                final PosterEntity item = (PosterEntity) posterListViewAdapter.getItem(a_position);
                MyFragment.changeFragment(new PosterMainFragment(item.getTitle(), true));

                //텍스트뷰에 출력
                System.out.println(item.getTitle() + " 에 접속함?");
            }
        });
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.poster_search_menu, menu);
        //MenuItem searchItem = menu.findItem(R.id.action_poster_search);

        //SearchView searchView = (SearchView) searchItem.getActionView();
        //searchView.setQueryHint("plz in search");
        //searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            /*@Override
            public boolean onQueryTextSubmit(String s) {
                System.out.println(s + " 검색하려고?");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }*/
        /*});*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("참여중인 게시판");
        actionBar.setDisplayHomeAsUpEnabled(false);
        setHasOptionsMenu(true);
        return binding.getRoot();
    }
}