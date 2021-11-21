package kr.ac.koreatech.teamproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import appcomponent.MyFragment;
import kr.ac.koreatech.teamproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private Map<String, Fragment> fragmentMap = new HashMap<>();
    private long backKeyPressedTime = 0;
    private Date startTime;
    private Toast toast;
    private Intent intent;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private void addLecture(String title, String profName, String introduce, Integer currPeople) {
        Map<String, String> lecture_info = new HashMap<>();
        lecture_info.put("profName", profName);
        lecture_info.put("introduce", introduce);
        lecture_info.put("currPeople", currPeople.toString());
        db.collection("server").document("data/lectureList/" + title)
                .set(lecture_info)
                .addOnSuccessListener(aVoid -> {
                    Log.d("TAG", "DocumentSnapshot successfully written!");
                })
                .addOnFailureListener(e -> {
                    Log.w("TAG", "Error writing document", e);
                });
    }

    // 스터디 그룹 생성(스터디 그룹 이름, 닉네임, 자기소개, 현재인원)
    private void addStudyGroup(String title, String nickname, String introduce, Integer currPeople) {
        Map<String, String> study_info = new HashMap<>();
        study_info.put("nickname", nickname);
        study_info.put("introduce", introduce);
        study_info.put("currPeople", currPeople.toString());


        db.collection("server").document("data/studyGroupList/" + title)
                .set(study_info)
                .addOnSuccessListener(aVoid -> {
                    Log.d("TAG", "DocumentSnapshot successfully written!");
                })
                .addOnFailureListener(e -> {
                    Log.w("TAG", "Error writing document", e);
                });
    }

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


//    private Button mbtnPlay, mbtnStop;
//    private TextView mtimerTextView;
//    private Thread timeThread = null;
//    private Boolean isRunning = true;

    //region Override
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyFragment.setMainActivity(this);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.fragment1, new MainFragment());
        fragmentTransaction.commit();
        //
        fragmentMap.put("main", new MainFragment());
        fragmentMap.put("poster", new PosterListFragment());
        fragmentMap.put("study", new StudyListFragment());
        fragmentMap.put("more", new SettingFragment());
        //
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // 상태바 없앰(전체화면)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED); // 양방향 가로모드 고정
        //
        /*addStudyGroup("천체연구모임방", "카운팅스타", "천체를 관측하고 사랑하는 모임입니다.", 2); // 스터디그룹목록에 추가
        addStudyGroup("KafkaS", "backend", "백엔드의 카프카에 대해서 공부하는 모임입니다.", 2); // 스터디그룹목록에 추가
        addStudyGroup("아Do!이노", "인호", "아두이노에 대해서 공부하는 모임입니다.", 2); // 스터디그룹목록에 추가
        addStudyGroup("라즈베리PI", "3.14", "라즈베리파이에 대해서 공부하는 모임입니다.", 2); // 스터디그룹목록에 추가
        addStudyGroup("신박한 아이디어", "idea", "아이디어를 공유하는 모임입니다.", 2); // 스터디그룹목록에 추가
        addStudyGroup("파이썬공부방", "python", "파이썬 기초에 대해서 공부하는 모임입니다.", 2); // 스터디그룹목록에 추가
        addStudyGroup("일터학습개론", "일터", "일터학습개론에 대해서 공부하는 모임입니다.", 2); // 스터디그룹목록에 추가
        addStudyGroup("정처기아자", "knight", "정보처리기사에 대해서 공부하는 모임입니다.", 2); // 스터디그룹목록에 추가
        addStudyGroup("컴활1급따자", "컴활", "컴활1급에 대해서 공부하는 모임입니다.", 2); // 스터디그룹목록에 추가
        addStudyGroup("컴활2급따자", "컴활따야지?", "컴활2급에 대해서 공부하는 모임입니다.", 2); // 스터디그룹목록에 추가
        addStudyGroup("데이터베이스공부하자", "디비", "데이터베이스에 대해서 공부하는 모임입니다.", 2); // 스터디그룹목록에 추가
        addStudyGroup("한전가자", "kepco", "한전취업 스터디 모임입니다.", 2); // 스터디그룹목록에 추가
        addStudyGroup("모프공부방", "안드...", "강승우교수님 모바일프로그래밍 과목을 같이 공부하는 모임입니다.", 2); // 스터디그룹목록에 추가
        addStudyGroup("아좌좌~자바공부~", "앱등이", "자바를 공부하는 모임입니다.", 2); // 스터디그룹목록에 추가
        addStudyGroup("삭제할 방", "안드...", "강승우교수님 모바일프로그래밍 과목을 같이 공부하는 모임입니다.", 2); // 스터디그룹목록에 추가
        addStudyGroup("효민이와 같이 영청 공부할래?", "횸니", "영어청해 과목을 같이 공부하는 모임입니다.", 4); // 스터디그룹목록에 추가
        deleteStudyGroup("삭제할 방"); // 스터디 그룹 목록에서 삭제*/


/*        addJoinStudyGroup(firebaseAuth.getCurrentUser().getEmail(), "라즈베리PI"); // 유저가 스터디 그룹에 가입
        removeJoinStudyGroup(firebaseAuth.getCurrentUser().getEmail(), "라즈베리PI"); // 유저가 스터디 그룹에 탈퇴*/

    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2500) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "뒤로 가기 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간에 2.5초를 더해 현재 시간과 비교 후
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간이 2.5ncs초가 지나지 않았으면 종료
        if (System.currentTimeMillis() <= backKeyPressedTime + 2500) {
            toast.cancel();
            toast = Toast.makeText(this, "이용해 주셔서 감사합니다.", Toast.LENGTH_SHORT);
            toast.show();
            // 강제종료
            ActivityCompat.finishAffinity(this);
            System.exit(0);
        }
    }
    //endregion
    //주

    public void navigation_onClick(View view) {
        /*
            TODO: 프래그먼트에 대한 정적인 값으로 할지 고민 해야함.
            (기본적으로 프래그먼트 4개에 대한 값을 전부 다 만들어 놓을 것인가.)
         */
        Fragment fragment = null;

        switch (view.getId()) {
            case R.id.main_imageView_main: {
                Log.i("C_MainActivity/navigation_onClick", "메인 프래그먼트");
                fragment = fragmentMap.get("main");
                break;
            }

            case R.id.main_imageView_poster: {
                Log.i("C_MainActivity/navigation_onClick", "게시판 프래그먼트");
                fragment = fragmentMap.get("poster");
                break;
            }

            case R.id.main_imageView_study: {
                Log.i("C_MainActivity/navigation_onClick", "스터디 프래그먼트");
                fragment = fragmentMap.get("study");
                break;
            }

            case R.id.main_imageView_setting: {
                Log.i("C_MainActivity/navigation_onClick", "설정 프래그먼트");
                fragment = fragmentMap.get("more");
                break;
            }
            default: {
                Log.i("C_MainActivity/navigation_onClick", "기본 프래그먼트");
                fragment = fragmentMap.get("main");
            }
        }
        MyFragment.changeFragment(fragment);

    }

}