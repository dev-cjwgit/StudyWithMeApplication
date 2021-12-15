package kr.ac.koreatech.teamproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import appcomponent.MyFragment;
import kr.ac.koreatech.teamproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    public static MainActivity context;
    public ActivityMainBinding binding;
    private Map<String, Fragment> fragmentMap = new HashMap<>();
    private long backKeyPressedTime = 0;
    private Date startTime;
    private Toast toast;
    private Intent intent;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // 스터디 그룹 생성(스터디 그룹 이름, 닉네임, 자기소개, 현재인원)
    private void addStudyGroup(String title, String nickname, String introduce, Integer currPeople, String category) {
        Map<String, String> study_info = new HashMap<>();
        study_info.put("nickname", nickname);
        study_info.put("introduce", introduce);
        study_info.put("currPeople", currPeople.toString());
        study_info.put("category", category.toString());


        db.collection("server").document("data/studyGroupList/" + title)
                .set(study_info)
                .addOnSuccessListener(aVoid -> {
                    Log.d("TAG", "DocumentSnapshot successfully written!");
                })
                .addOnFailureListener(e -> {
                    Log.w("TAG", "Error writing document", e);
                });
    }

    private void addLecture(String title, String profName, String introduce, String profEmail, String phone, String assistEmail, String lecturePlan, String mainBook, String subBook, String category) {
        Map<String, String> lecture_info = new HashMap<>();
        lecture_info.put("profName", profName);
        lecture_info.put("introduce", introduce);
        lecture_info.put("profEmail", profEmail);
        lecture_info.put("phone", phone);
        lecture_info.put("assistEmail", assistEmail);
        lecture_info.put("lecturePlan", lecturePlan);
        lecture_info.put("mainBook", mainBook);
        lecture_info.put("subBook", subBook);
        lecture_info.put("category", category);

        db.collection("server").document("data/lectureList/" + title)
                .set(lecture_info)
                .addOnSuccessListener(aVoid -> {
                    Log.d("TAG", "DocumentSnapshot successfully written!");
                })
                .addOnFailureListener(e -> {
                    Log.w("TAG", "Error writing document", e);
                });

        System.out.println("강의게시판&정보생성하게?");
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
        binding.mainImageViewMain.setImageResource(R.drawable.icon_home);
        binding.mainImageViewPoster.setImageResource(R.drawable.icon_subject_gray);
        binding.mainImageViewStudy.setImageResource(R.drawable.icon_group_gray);
        binding.mainImageViewSetting.setImageResource(R.drawable.icon_more_gray);
        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // 상태바 없앰(전체화면)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED); // 양방향 가로모드 고정
        context = this;
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
                if (MyFragment.changeFragment(fragment, false)) {
                    binding.mainImageViewMain.setImageResource(R.drawable.icon_home);
                    binding.mainImageViewPoster.setImageResource(R.drawable.icon_subject_gray);
                    binding.mainImageViewStudy.setImageResource(R.drawable.icon_group_gray);
                    binding.mainImageViewSetting.setImageResource(R.drawable.icon_more_gray);
                    MyFragment.clearHistory();
                }
                break;
            }

            case R.id.main_imageView_poster: {
                Log.i("C_MainActivity/navigation_onClick", "게시판 프래그먼트");
                fragment = fragmentMap.get("poster");
                if (MyFragment.changeFragment(fragment, false)) {
                    binding.mainImageViewMain.setImageResource(R.drawable.icon_home_gray);
                    binding.mainImageViewPoster.setImageResource(R.drawable.icon_subject);
                    binding.mainImageViewStudy.setImageResource(R.drawable.icon_group_gray);
                    binding.mainImageViewSetting.setImageResource(R.drawable.icon_more_gray);
                    MyFragment.clearHistory();

                }
                break;
            }

            case R.id.main_imageView_study: {
                Log.i("C_MainActivity/navigation_onClick", "스터디 프래그먼트");
                fragment = fragmentMap.get("study");
                if (MyFragment.changeFragment(fragment, false)) {
                    binding.mainImageViewMain.setImageResource(R.drawable.icon_home_gray);
                    binding.mainImageViewPoster.setImageResource(R.drawable.icon_subject_gray);
                    binding.mainImageViewStudy.setImageResource(R.drawable.icon_group);
                    binding.mainImageViewSetting.setImageResource(R.drawable.icon_more_gray);
                    MyFragment.clearHistory();

                }
                break;
            }

            case R.id.main_imageView_setting: {
                Log.i("C_MainActivity/navigation_onClick", "설정 프래그먼트");
                fragment = fragmentMap.get("more");
                if (MyFragment.changeFragment(fragment, false)) {
                    binding.mainImageViewMain.setImageResource(R.drawable.icon_home_gray);
                    binding.mainImageViewPoster.setImageResource(R.drawable.icon_subject_gray);
                    binding.mainImageViewStudy.setImageResource(R.drawable.icon_group_gray);
                    binding.mainImageViewSetting.setImageResource(R.drawable.icon_more);
                    MyFragment.clearHistory();

                }
                break;
            }
            default: {
                Log.i("C_MainActivity/navigation_onClick", "기본 프래그먼트");
                fragment = fragmentMap.get("main");
                if (MyFragment.changeFragment(fragment, false)) {
                    binding.mainImageViewMain.setImageResource(R.drawable.icon_home);
                    binding.mainImageViewPoster.setImageResource(R.drawable.icon_subject_gray);
                    binding.mainImageViewStudy.setImageResource(R.drawable.icon_group_gray);
                    binding.mainImageViewSetting.setImageResource(R.drawable.icon_more_gray);
                    MyFragment.clearHistory();

                }
                break;
            }
        }
    }
}