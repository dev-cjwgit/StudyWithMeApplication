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

        addLecture("모바일프로그래밍", "강승우", "과제 수행을 통한 안드로이드 앱 개발 역량을 기른다","cy0804bamboo@naver.com",
                "01098678883","cu0000@naver.com","1주차: 과목소개, 안드로이드 앱 개발 개요\n2주차: 사용자 인터페이스 기초\n3주차: 사용자 인터페이스 기초\n4주차: 이벤트 처리\n5주차: 메뉴와 대화상자\n6주차: 액티비티아 인텐트\n7주차: 액티비티 생명주기\n8주차: 파일 처리\n9주차: 어댑터뷰\n10주차: 브로드캐스트 리시버\n11주차: 프로젝트 중간발표",
                "그림으로 쉽게 설명하는 안드로이드 프로그래밍", "Eclipse를 활용한 안드로이드 프로그래밍","강의"); // 강의목록에 추가
        addLecture("데이터베이스시스템","무하마드","This course is intened to gice students a solid background in databases.",
                "v12@naver.com","01012345999","mumu@naver.com","1주차: Introduction to Database Management Systems\n2주차: The Relational Model\n3주차: SQL Server\n4주차: TSQL Functions\n5주차: Stored Procedures\n6주차: TSQL Triggers\n7주차: TSQL Cursors\n8주차: Mid Exam\n9주차: Data Security\n10주차: Transaction Processing and Management\n11주차: Query Processing" +
                        "\n12주차: JDBC\n13주차: Java Application Development1\n14주차: Java Application Development2\n15주차: Report Building\n16주차: Final Exam","Database Systems","Practical Database Programming with JAVA","강의");
        addLecture("빅데이터활용","이호","빅데이터관련 기본적인 지식 습득과 활용을 목표로 한다.","hoho@naver.com","010188887777",
                "hohoassist@naver.com","1주차: 강의소개\n2주차: 4차산업혁과 빅데이터\n3주차: 빅데이터 생성 및 수집\n4주차: 특징 및 사례\n5주차: 분석 및 시각화\n6주차:" +
                        "빅데이터와 인공지능\n7주차: 선형회귀분석\n8주차: 사례 연구 토론 및 질의응답\n9주차: 의사결정트리\n10주차: 중간고사\n11주차: 비정형데이터 분석 개념 학습 및 실습\n12주차: 멘토링세션\n13주차: 멘토링세션\n14주차: 기말고사\n15주차: 기말고사\n16주차: 기말고사",
                "자체교재","자체교재","강의");
        addLecture("한국사능력검정시험","-","한국사능력을 함양합니다.","-","-","-","-","이기적한국사능력검정시험","-","자격증");
        //addLecture("컴퓨터활용능력검정시험","-","");
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