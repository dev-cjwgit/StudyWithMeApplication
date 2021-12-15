package kr.ac.koreatech.teamproject;

import android.content.ClipData;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

    public class ChatDTO{
        private String UserName;
        private String message;

        public ChatDTO(){}
        public ChatDTO(String UserName,String message){
            this.UserName=UserName;
            this.message=message;
        }
        public void setUserName(String UserName){this.UserName=UserName;}
        public void setMessage(String message){this.message=message;}
        public String getUserName(){return UserName;}
        public String getMessage(){return message;}
    }


    private FragmentStudyMainBinding binding;
    private DrawerLayout drawerLayout_study;
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
    //
    private FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference=firebaseDatabase.getReference();
    //

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

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, list);
        binding.framentStudyListView.setAdapter(adapter);

        //openChat(title);

        binding.fragmentStudyButtonSend.setOnClickListener((v) -> {
            list.add(MainFragment.userName+" : "+binding.fragmentStudyEditTextSend.getText().toString());
            //System.out.println(MainFragment.userName); //채팅 보낸 userName
            ChatDTO chat=new ChatDTO(MainFragment.userName,binding.fragmentStudyEditTextSend.getText().toString());
            databaseReference.child("chat").child(title).push().setValue(chat);
            binding.fragmentStudyEditTextSend.setText("");
            adapter.notifyDataSetChanged();
        });

        //studyGroupMain navigation listview
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

    private void addMessage(DataSnapshot dataSnapshot,ArrayAdapter<String>adapter){
        ChatDTO chatDTO=dataSnapshot.getValue(ChatDTO.class);
        adapter.add(chatDTO.getUserName()+" : "+chatDTO.getMessage());
        binding.framentStudyListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
    }
    private void removeMessage(DataSnapshot dataSnapshot,ArrayAdapter<String>adapter){
        ChatDTO chatDTO=dataSnapshot.getValue(ChatDTO.class);
        adapter.remove(chatDTO.getUserName()+" : "+chatDTO.getMessage());
        binding.framentStudyListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
    }
    private void openChat(String title){
        ArrayAdapter<String> chat_adapter=new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,android.R.id.text1);
        binding.framentStudyListView.setAdapter(chat_adapter);
        binding.framentStudyListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        databaseReference.child("chat").child(title).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                addMessage(dataSnapshot,chat_adapter);
                Log.e("LOG","s:"+s);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                removeMessage(dataSnapshot,chat_adapter);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.studymenu, menu);
        //dwLayout=getActivity().findViewById(R.id.drawerLayout_study_main);
        drawerLayout_study=getActivity().findViewById(R.id.drawerLayout_study_main);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int curId = item.getItemId();
        switch (curId) {
            case R.id.action_online:
                System.out.println("현활 보려고??");
                if (drawerLayout_study.isDrawerOpen(Gravity.RIGHT))
                    drawerLayout_study.closeDrawer(Gravity.RIGHT);
                else
                    drawerLayout_study.openDrawer(Gravity.RIGHT);
                return true;
            case android.R.id.home:
                MyFragment.prevFragment();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}