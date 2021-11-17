package service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.sql.Time;
import java.util.Date;

import appcomponent.MyFragment;
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

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 시간 게산 파이팅...
        System.out.println((new Date().getTime() - startTime.getTime()) + " 총 시간");
        ((MainFragment) MyFragment.getCurrFragment()).finishStudy();
    }
}