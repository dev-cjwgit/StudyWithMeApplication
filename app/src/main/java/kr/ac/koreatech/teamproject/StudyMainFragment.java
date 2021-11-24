package kr.ac.koreatech.teamproject;

import android.content.ClipData;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import adapter.CurrentMemberAdapter;
import appcomponent.MyFragment;
import entity.CurrentMemberEntity;
import entity.StudyEntity;
import kr.ac.koreatech.teamproject.databinding.FragmentStudyMainBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudyMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudyMainFragment extends Fragment {
    private FragmentStudyMainBinding binding;
    private List<String> list = new ArrayList<>();
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private boolean back_btn = false;
    private String mParam1;
    private String mParam2;
    private String title;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    // 스터디 그룹 탈퇴(이메일, 탈퇴 할 스터디 그룹 이름)
    private void removeJoinStudyGroup(String user_email, String study_group_name) {
        user_email = user_email.replace(".", "-");
        DocumentReference washingtonRef = db.collection("server").document("user/" + user_email + "/joinStudyGroup/");
        washingtonRef.update("title", FieldValue.arrayRemove(study_group_name))
                .addOnSuccessListener(aVoid -> {
                    Log.d("TAG", "DocumentSnapshot successfully update");
                })
                .addOnFailureListener(e -> {
                    Log.w("TAG", "Error update document", e);
                });
    }

    private CurrentMemberAdapter currentMemberAdapter;

    // Make sure to be using androidx.appcompat.app.ActionBarDrawerToggle version.
    private ActionBarDrawerToggle drawerToggle;
//    private Object CurrentMemberAdapter;

    public StudyMainFragment(String title) {
        this(title, false);
        // Required empty public constructor
    }

    public StudyMainFragment(String title, boolean back_btn) {
        // Required empty public constructor
        this.title = title;
        this.back_btn = back_btn;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudyFagment.
     */
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

//        binding.navView.setEnabled(false);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, list);
        binding.framentStudyListView.setAdapter(adapter);

        binding.fragmentStudyButtonSend.setOnClickListener((v) -> {
            list.add(binding.fragmentStudyEditTextSend.getText().toString());
            binding.fragmentStudyEditTextSend.setText("");
            adapter.notifyDataSetChanged();
        });

        //studygroupmain navigation listview
        currentMemberAdapter=new CurrentMemberAdapter();
        binding.fragmentCurmemlist.setAdapter(currentMemberAdapter);

        currentMemberAdapter.append(new CurrentMemberEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image),"죠르디",BitmapFactory.decodeResource(getResources(), R.drawable.studying)));
        currentMemberAdapter.append(new CurrentMemberEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image),"춘식이",BitmapFactory.decodeResource(getResources(), R.drawable.studying)));
        currentMemberAdapter.append(new CurrentMemberEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image),"라이언",BitmapFactory.decodeResource(getResources(), R.drawable.not_studying)));
        currentMemberAdapter.append(new CurrentMemberEntity(BitmapFactory.decodeResource(getResources(), R.drawable.default_image),"어피치",BitmapFactory.decodeResource(getResources(), R.drawable.not_studying)));

        //네비 드로우의 탈퇴 버튼 클릭 이벤트
        binding.signOutButton.setOnClickListener(v -> {
            //MyFragment.changeFragment(new PosterQuestionListFragment());
            System.out.println("탈퇴하려고?");
            removeJoinStudyGroup(firebaseAuth.getCurrentUser().getEmail(),title); // 유저가 스터디 그룹에 탈퇴
            Toast.makeText(getActivity(),"탈퇴되었습니다.",Toast.LENGTH_SHORT).show();
            MyFragment.changeFragment(new StudyListFragment());
        });

    }

    // Hi
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

                DrawerLayout drawerLayout=getActivity().findViewById(R.id.drawerLayout);
                View btn_setting=getActivity().findViewById(curId);

                btn_setting.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        drawerLayout.openDrawer(Gravity.RIGHT);
                    }
                });

                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}