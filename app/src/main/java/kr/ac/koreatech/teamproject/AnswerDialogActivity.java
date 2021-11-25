package kr.ac.koreatech.teamproject;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;

import kr.ac.koreatech.teamproject.databinding.ActivityAnswerDialogBinding;
import kr.ac.koreatech.teamproject.databinding.ActivityLoginBinding;

public class AnswerDialogActivity extends Activity {
    private ActivityAnswerDialogBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String poster_title, qnaInfo, title, body, date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAnswerDialogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); //
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = (int) (display.getWidth() * 0.85); //Display 사이즈의 70%
        int height = (int) (display.getHeight() * 0.6);  //Display 사이즈의 90%

        getWindow().getAttributes().width = width;
        getWindow().getAttributes().height = height;
        Intent intent = getIntent();
        poster_title = intent.getStringExtra("poster_title");
        qnaInfo = intent.getStringExtra("qnaInfo");


    }

    public void addButton_onClick(View view) {
        SimpleDateFormat sDate2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println("질문 등록\n" +
                "" + binding.answerActivityTitleEditText.getText().toString() + "\n" +
                "" + binding.answerActivityBodyEditText.getText().toString());
        addPosterQnAAnswer(poster_title, qnaInfo,
                binding.answerActivityTitleEditText.getText().toString(),
                SettingFragment.userName,
                binding.answerActivityBodyEditText.getText().toString(),
                sDate2.format(new Date())
        );
    }

    private void addPosterQnAAnswer(String poster_title, String qnaInfo, String title, String user_name, String body, String date) {
        DocumentReference washingtonRef = db.collection("QnA" + poster_title).document(qnaInfo);
        washingtonRef.update("list", FieldValue.arrayUnion(title + "|" + user_name + "|" + body + "|" + date))
                .addOnSuccessListener(aVoid -> {
                    Log.d("TAG", "DocumentSnapshot successfully update");
                    finish();
                })
                .addOnFailureListener(e -> {
                    Log.w("TAG", "Error update document", e);
                });
    }

}