package kr.ac.koreatech.teamproject;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TimerActivity extends AppCompatActivity {
    TextView timertextview;
    Button btnPlay, btnStop;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L;
    Handler handler;

    int Hours, Minutes, Seconds;
    ListView listView;

    String[] ListElements = new String[] { };
    List<String> ListElementsArrayList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timertextview = (TextView)findViewById(R.id.timertextview);
        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnStop = (Button) findViewById(R.id.btnStop);

        handler = new Handler();

        ListElementsArrayList = new ArrayList<String>(Arrays.asList(ListElements));

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ListElementsArrayList);

        listView.setAdapter(adapter);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);

            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimeBuff += MillisecondTime;
                handler.removeCallbacks(runnable);
            }
        });
    }
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            MillisecondTime = SystemClock.uptimeMillis() - StartTime;
            UpdateTime = TimeBuff + MillisecondTime;
            Seconds = (int) (UpdateTime / 1000);
            Minutes = Seconds / 60;
            Seconds = Seconds % 60;
            Hours = Minutes % 60;

            MillisecondTime = (int) (UpdateTime % 1000);

            timertextview.setText("" + Hours + ":"
                    + String.format("%02d", Minutes) + ":"
                    + String.format("%03d", Seconds));
            handler.postDelayed(this,0);
        }
    };
}
