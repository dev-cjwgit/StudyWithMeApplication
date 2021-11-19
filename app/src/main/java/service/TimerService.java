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

import appcomponent.MyFragment;
import kr.ac.koreatech.teamproject.MainActivity;
import kr.ac.koreatech.teamproject.MainFragment;

public class TimerService extends Service {
    // https://betatester.tistory.com/16 참고
    private Date startTime;

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
        Toast toast2 = Toast.makeText(getApplicationContext(), "공부시간이 측정되었습니다.", Toast.LENGTH_SHORT);
        toast2.show();


    }


}