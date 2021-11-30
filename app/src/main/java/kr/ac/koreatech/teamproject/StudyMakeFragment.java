package kr.ac.koreatech.teamproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import appcomponent.MyFragment;
import kr.ac.koreatech.teamproject.databinding.FragmentStudyJoinBinding;
import kr.ac.koreatech.teamproject.databinding.FragmentStudyListBinding;
import kr.ac.koreatech.teamproject.databinding.FragmentStudyMakeBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudyMakeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudyMakeFragment extends Fragment {
    private FragmentStudyMakeBinding binding;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private boolean back_btn = false;
    private String mParam1;
    private String mParam2;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // 스터디 그룹 삭제(스터디 그룹 이름)
    private void deleteStudyGroup(String title) {
        db.collection("server").document("data/studyGroupList/" + title)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Log.d("TAG", "DocumentSnapshot successfully deleted!");
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error deleting document", e);
                    }
                });
    }

    // 스터디 그룹 생성(스터디 그룹 이름, 닉네임, 자기소개, 현재인원)
    private void addStudyGroup(String title, String nickname, String introduce, Integer currPeople,String category) {
        Map<String, String> study_info = new HashMap<>();
        study_info.put("nickname", nickname);
        study_info.put("introduce", introduce);
        study_info.put("currPeople", currPeople.toString());
        study_info.put("category",category.toString());


        db.collection("server").document("data/studyGroupList/" + title)
                .set(study_info)
                .addOnSuccessListener(aVoid -> {
                    Log.d("TAG", "DocumentSnapshot successfully written!");
                })
                .addOnFailureListener(e -> {
                    Log.w("TAG", "Error writing document", e);
                });
    }

    public StudyMakeFragment() {
        // Required empty public constructor
    }

    public StudyMakeFragment(boolean back_btn) {
        // Required empty public constructor
        this.back_btn = back_btn;
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudyJoinFragment.
     */
    public static StudyMakeFragment newInstance(String param1, String param2) {
        StudyMakeFragment fragment = new StudyMakeFragment();
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

        binding = FragmentStudyMakeBinding.inflate(getLayoutInflater());

        //생성버튼 클릭 이벤트
        binding.btnStudyGroupMake.setOnClickListener(v->{
            System.out.println("생성하려고?");
            if(binding.radioLecture.isChecked()==true){
                addStudyGroup(binding.studyGroupEdittext.getText().toString(),MainFragment.userName,binding.groupIntroduce.getText().toString(), 1,"강의"); // 스터디그룹목록에 추가
            }else if(binding.radioLicense.isChecked()==true){
                addStudyGroup(binding.studyGroupEdittext.getText().toString(),MainFragment.userName,binding.groupIntroduce.getText().toString(),1,"자격증");
            }else if(binding.radioEtc.isChecked()==true){
                addStudyGroup(binding.studyGroupEdittext.getText().toString(),MainFragment.userName,binding.groupIntroduce.getText().toString(),1,"기타");
            }
            Toast.makeText(getActivity(),"생성되었습니다.",Toast.LENGTH_SHORT).show();
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("스터디 그룹 생성");
        actionBar.setDisplayHomeAsUpEnabled(back_btn);
        //actionBar.setDisplayHomeAsUpEnabled(false);
        setHasOptionsMenu(true);
        return binding.getRoot();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int curId = item.getItemId();
        switch (curId) {

            case android.R.id.home:
                MyFragment.prevFragment();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private static String userName;
    private void getUserInfo(String user_email) {
        user_email = user_email.replace(".", "-");

        DocumentReference docRef = db.collection("server").document("user/" + user_email + "/info/");
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                    userName=document.getData().get("name").toString();
                } else {
                    Log.d("TAG", "No such document");
                }
            } else {
                Log.d("TAG", "get failed with ", task.getException());
            }
        });
    }
}