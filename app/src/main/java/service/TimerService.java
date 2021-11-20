package service;


import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import appcomponent.MyFragment;
import kr.ac.koreatech.teamproject.MainActivity;
import kr.ac.koreatech.teamproject.MainFragment;

public class TimerService extends Service {
    public static Intent mainFragmentIntent;

    // https://betatester.tistory.com/16 참고
    private Date startTime;
    private Timer timer;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        startTime = new Date();
        System.out.println("서비스를 시작합니다.");
        Toast toast1 = Toast.makeText(getApplicationContext(), "공부시간을 측정합니다.", Toast.LENGTH_SHORT);
        toast1.show();
        timer = new Timer();
        mainFragmentIntent = new Intent("timeService"); // 새 intent 객체명 mainFragmentIntent
        timer.schedule(new TimerTask() {
            @Override
            public void run() { // schedule: 특정한 시간에 원하는 작업 수행, 이거 인자가 어떻게 되는 건가요?
                int sectime = (int) (new Date().getTime() - startTime.getTime()); // 가공되지않은 전체 초
                mainFragmentIntent.putExtra("time_sec", sectime); // intent로 액티비티 이동, 값을 넘기고 싶다면 intent안에 있는 putExtra()함수 사용
                sendBroadcast(mainFragmentIntent); // intent 넘김, context에 포함된 함수를 사용하여 인텐트 전달, putExtra()를 사용하여 여러 타입 형태로 전달 가
            }
        }, 1000, 1000); // long delay, long period, 지정한 시간부터 일정 간격(period)로 지정한 작업(tast)수

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    TextView watchTextView;

    @Override
    public void onDestroy() {
        super.onDestroy();
        int sectime = (int) (new Date().getTime() - startTime.getTime());
        int sec = (sectime / 1000) % 60;
        int min = (sectime / (1000 * 60)) % 60;
        int hour = (sectime / (1000 * 60 * 60)) % 24;

        ((MainFragment) MyFragment.getCurrFragment()).finishStudy(hour, min, sec);
        timer.cancel(); // 타이머 중지를 먼저
        Toast toast2 = Toast.makeText(getApplicationContext(), "공부시간이 측정되었습니다.", Toast.LENGTH_SHORT);
        toast2.show();


    }


}