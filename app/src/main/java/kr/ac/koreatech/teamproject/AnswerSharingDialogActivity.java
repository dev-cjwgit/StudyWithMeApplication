package kr.ac.koreatech.teamproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.contentcapture.ContentCaptureCondition;
import android.widget.ArrayAdapter;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import adapter.AnsbulletSharingMatAdapter;
import entity.AnssharingEntity;

import kr.ac.koreatech.teamproject.databinding.ActivityAnswerSharingMatDialogBinding;
import kr.ac.koreatech.teamproject.databinding.FragmentAnsbulletSharingMatBinding;

public class AnswerSharingDialogActivity extends Activity {
    private ActivityAnswerSharingMatDialogBinding binding;
    private AnsbulletSharingMatAdapter ansbulletSharingMatAdapter;
    private List<String> list = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String poster_title, qnainfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAnswerSharingMatDialogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = (int) (display.getWidth() * 0.85); //Display 사이즈의 70%
        int height = (int) (display.getHeight() * 0.6);  //Display 사이즈의 90%

        getWindow().getAttributes().width = width;
        getWindow().getAttributes().height = height;
        Intent intent = getIntent();
        poster_title = intent.getStringExtra("poster_title");
        qnainfo = intent.getStringExtra("qnainfo");

    }

    public void Button_onClick(View view) {
//        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat sDate2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        System.out.println("질문 등록\n" +
                "" + binding.answerSharingMatDialogBody.getText().toString());
        try {
            addPosterInfoAnswer(poster_title,
                    qnainfo,
                    SettingFragment.userName,
                    binding.answerSharingMatDialogBody.getText().toString(),
                    sDate2.format(new Date()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addPosterInfoAnswer(String poster_title, String qnaInfo, String user_name, String body, String date) {
        DocumentReference washingtonRef = db.collection("Info" + poster_title).document(qnaInfo);
        washingtonRef.update("list", FieldValue.arrayUnion(user_name + "|" + body + "|" + date))
                .addOnSuccessListener(aVoid -> {
                    Log.d("TAG", "DocumentSnapshot successfully update");
                    finish();
                })
                .addOnFailureListener(e -> {
                    Log.w("TAG", "Error update document", e);
                });
    }

}
