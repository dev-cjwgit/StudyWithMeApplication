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

import java.util.ArrayList;
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


    private void addLecture(String title, String profName, String introduce, Integer currPeople, String category) {
        Map<String, String> lecture_info = new HashMap<>();
        lecture_info.put("profName", profName);
        lecture_info.put("introduce", introduce);
        lecture_info.put("currPeople", currPeople.toString());
        lecture_info.put("category", category);

        db.collection("server").document("data/lectureList/" + title)
                .set(lecture_info)
                .addOnSuccessListener(aVoid -> {
                    Log.d("TAG", "DocumentSnapshot successfully written!");
                })
                .addOnFailureListener(e -> {
                    Log.w("TAG", "Error writing document", e);
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
        binding.mainImageViewMain.setImageResource(R.drawable.icon_home);
        binding.mainImageViewPoster.setImageResource(R.drawable.icon_subject_gray);
        binding.mainImageViewStudy.setImageResource(R.drawable.icon_group_gray);
        binding.mainImageViewSetting.setImageResource(R.drawable.icon_more_gray);
        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // 상태바 없앰(전체화면)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED); // 양방향 가로모드 고정
        context = this;
//
//        addStudyGroup("천체연구모임방", "카운팅스타", "천체를 관측하고 사랑하는 모임입니다.", 2); // 스터디그룹목록에 추가
//        addStudyGroup("모프공부방", "노예", "모바일프로그래밍 강승우 교수님 공부하는 공부방", 3); // 스터디그룹목록에 추가
//        addStudyGroup("컴활1급 공부하자", "대한건아", "컴퓨터활용능력1급을 공부하는 모임입니다.", 4); // 스터디그룹목록에 추가
//        addStudyGroup("컴활2급 공부하자", "대한", "컴퓨터활용능력2급을 공부하는 모임입니다.", 4); // 스터디그룹목록에 추가
//        addStudyGroup("영청공부하자", "홉스", "영어청해를 공부하는 모임입니다.", 2); // 스터디그룹목록에 추가
//        addStudyGroup("효민이랑 영청 공부할래?", "횸니", "영어청해를 효민이와 함께 모임입니다.", 6); // 스터디그룹목록에 추가
//        addStudyGroup("객체지향공부하자!", "나는야 교수님의 노예..", "김상진교수님 객체지향프로그래밍을 공부하는 모임입니다.", 4); // 스터디그룹목록에 추가
//        addStudyGroup("오픽공부방", "OPIC", "오픽 IH를 목표로 하는 공부방입니다.", 20); // 스터디그룹목록에 추가
//        addStudyGroup("선경이랑 같이 그래픽스할래..?", "덩경이", "컴퓨터그래픽스를 공부하는 모임입니다.", 2); // 스터디그룹목록에 추가
//        addStudyGroup("자바공부하ja", "자바스크립트", "자바스크립트를 공부하는 모임입니다.", 4); // 스터디그룹목록에 추가
//        addStudyGroup("채연이와 아이들", "채요니", "채연이와 같이 시스템프로그래밍을 공부하는 모임입니다.", 4); // 스터디그룹목록에 추가
//        addStudyGroup("직능훈 공부방", "진구교수님", "이진구 교수님 직업능력개발훈련평가 과목을 공부하는 모임입니다.", 1); // 스터디그룹목록에 추가
//        addStudyGroup("경영학개론 공부하자", "산경천재", "경영학개론을 공부하는 모임입니다.", 5); // 스터디그룹목록에 추가
//        addStudyGroup("Information process Knight", "Knight..", "정처기를 공부하는 모임입니다.", 4); // 스터디그룹목록에 추가
//        addStudyGroup("정보보안기사 공부하자", "보안기사 어려워", "정보보안기사를 모임입니다.", 4); // 스터디그룹목록에 추가
//        addStudyGroup("SQLD 공부방", "sqld최공", "SQLD를 공부하는 모임입니다.", 15); // 스터디그룹목록에 추가
        //
//        addLecture("객체지향개발론및실습", "김상진", "GoF 패턴을 학습합니다.", 37,"강의");
//        addLecture("모바일프로그래밍", "강승우", "모바일 애플리케이션을 디자인하고 구현하는데 필요한 기본 능력과 방법을 습득하는 것을 목표로 한다.", 40,"강의");
//        addLecture("시스템프로그래밍", "김덕수", "유닉스/리눅스의 기본적인 사용법을 익히고, 시스템 호출과 라이브러리 제작 및 이를 사용한 응용 프로그램과 개발 기법을 학습한다.", 39,"강의");
//        addLecture("컴퓨터네트워크", "박승철", "TCP/IP 인터넷을 중심으로 컴퓨터 네트워크에 대한 전반적인 기술을 공부한다.", 40,"강의");
//        addLecture("데이터베이스시스템", "무하마드", "This course is intended to gice students a solid background in databases, with a focus on relational database systems.", 40,"강의");
//        addLecture("학습자이해와상담", "이지", "본 교과에서는 학습자의 특성 및 상담의 기초를 이해하고 이를 토대로 한 주요 학생지도 및 상담이론에 대해 학습한다.", 25,"강의");
//        addLecture("C프로그래밍2","조재수","C언어를 이용한 고수준의 프로그램 작성 능력을 배양한다.",30,"강의");
//        addLecture("컴퓨터활용능력","-","컴퓨터뢀용능력을 함양한다.",70,"자격증");
//        addLecture("한국사능력검정시험","-","한국사능력을 함양한다.",50,"자격증");
//        addLecture("e-learning개론","김원섭","본 교과목은 이러닝 코스 설계를 위한 주요 이론 및 원리를 공부하며, 양직의 학습콘텐츠를 설계해 볼 것입니다.", 50,"강의");
//        addLecture("영어회화","제임스","This course builds learner's knowledge and ability, enabling them to accomplish to things.",30,"강의");
//        addLecture("창의적사고와글쓰기","정재영","의사소통의 기본 개념과 기법을 익히고 이를 바탕으로 창의적인 문제해결의 전 과정을 학습함으로 대학생활과 직업 생활에 필요한 기초 의사소통능력을 기른다.", 30,"강의");
//        addLecture("공학수학2","박원우","공학기초 역량과 문제해결 역량을 함양하고자 한다.",35,"강의");
//        addLecture("정보처리기사","-","정보처리능력을 함양한다.",70,"자격증");
//        addLecture("보안기사","-","보안관련능력을 함양한다.",40,"자격증");
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
                binding.mainImageViewMain.setImageResource(R.drawable.icon_home);
                binding.mainImageViewPoster.setImageResource(R.drawable.icon_subject_gray);
                binding.mainImageViewStudy.setImageResource(R.drawable.icon_group_gray);
                binding.mainImageViewSetting.setImageResource(R.drawable.icon_more_gray);
                break;
            }

            case R.id.main_imageView_poster: {
                Log.i("C_MainActivity/navigation_onClick", "게시판 프래그먼트");
                fragment = fragmentMap.get("poster");
                binding.mainImageViewMain.setImageResource(R.drawable.icon_home_gray);
                binding.mainImageViewPoster.setImageResource(R.drawable.icon_subject);
                binding.mainImageViewStudy.setImageResource(R.drawable.icon_group_gray);
                binding.mainImageViewSetting.setImageResource(R.drawable.icon_more_gray);
                break;
            }

            case R.id.main_imageView_study: {
                Log.i("C_MainActivity/navigation_onClick", "스터디 프래그먼트");
                fragment = fragmentMap.get("study");
                binding.mainImageViewMain.setImageResource(R.drawable.icon_home_gray);
                binding.mainImageViewPoster.setImageResource(R.drawable.icon_subject_gray);
                binding.mainImageViewStudy.setImageResource(R.drawable.icon_group);
                binding.mainImageViewSetting.setImageResource(R.drawable.icon_more_gray);
                break;
            }

            case R.id.main_imageView_setting: {
                Log.i("C_MainActivity/navigation_onClick", "설정 프래그먼트");
                fragment = fragmentMap.get("more");
                binding.mainImageViewMain.setImageResource(R.drawable.icon_home_gray);
                binding.mainImageViewPoster.setImageResource(R.drawable.icon_subject_gray);
                binding.mainImageViewStudy.setImageResource(R.drawable.icon_group_gray);
                binding.mainImageViewSetting.setImageResource(R.drawable.icon_more);
                break;
            }
            default: {
                Log.i("C_MainActivity/navigation_onClick", "기본 프래그먼트");
                binding.mainImageViewMain.setImageResource(R.drawable.icon_home);
                binding.mainImageViewPoster.setImageResource(R.drawable.icon_subject_gray);
                binding.mainImageViewStudy.setImageResource(R.drawable.icon_group_gray);
                binding.mainImageViewSetting.setImageResource(R.drawable.icon_more_gray);
                fragment = fragmentMap.get("main");
            }
        }
        MyFragment.changeFragment(fragment);

    }


}