package kr.ac.koreatech.teamproject;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.SearchView;
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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import appcomponent.MyFragment;
import entity.PosterEntity;
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

    public static Map<String, PosterEntity> list = new HashMap<>();

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // 강의 탈퇴(이메일, 탈퇴 할 강의 이름)
    private void removeJoinLecutre(String user_email, String lecture_name) {
        user_email = user_email.replace(".", "-");
        DocumentReference washingtonRef = db.collection("server").document("user/" + user_email + "/joinLecture/");
        washingtonRef.update("title", FieldValue.arrayRemove(lecture_name))
                .addOnSuccessListener(aVoid -> {
                    Log.d("TAG", "DocumentSnapshot successfully update");
                })
                .addOnFailureListener(e -> {
                    Log.w("TAG", "Error update document", e);
                });
    }

//
    public PosterMainFragment(String title, boolean back_btn) {
        // Required empty public constructor
        this.title = title;
        this.back_btn = back_btn;
    }

    public PosterMainFragment(String title) {
        // Required empty public constructor
        this(title, false);
    }

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
            MyFragment.changeFragment(new PosterQuestionListFragment(title));
            System.out.println("질의 응답방에 접속하려고?");
        });

        binding.shareInfo.setOnClickListener(v -> {
            MyFragment.changeFragment(new fragment_bulletin_sharing_materials(title));
            System.out.println("정보 공유에 접속하려고?");
        });

        // 드로우바 탈퇴 버튼 클릭 이벤트
        binding.signOutButton.setOnClickListener(v -> {
            System.out.println("탈퇴하려고?");
            removeJoinLecutre(firebaseAuth.getCurrentUser().getEmail(),title); // 유저가 강의 게시판 탈퇴
            Toast.makeText(getActivity(),"탈퇴되었습니다.", Toast.LENGTH_SHORT).show();
        });

        PosterEntity a = PosterListFragment.hashMap.get(title);
        binding.LecIDTextview2.setText(a.getTitle());
        binding.profIDTextview2.setText(a.getProfName());
        binding.profemailTextview2.setText(a.getProfEmail());
        binding.profdiaTextview2.setText(a.getPhone());
        binding.assiemailTextview2.setText(a.getAssistEmail());
        binding.lectureplan.setText(a.getLecturePlan());
        binding.maintbTextView2.setText(a.getMainBook());
        binding.subTbTextview2.setText(a.getSubBook());

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
            case R.id.post_category:
                System.out.println("네비바 보려고?");
                if (drawerLayout.isDrawerOpen(Gravity.RIGHT))
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                else
                    drawerLayout.openDrawer(Gravity.RIGHT);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}