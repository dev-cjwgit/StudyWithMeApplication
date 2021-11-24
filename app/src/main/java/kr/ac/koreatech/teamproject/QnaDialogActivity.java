package kr.ac.koreatech.teamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import kr.ac.koreatech.teamproject.databinding.ActivityMainBinding;
import kr.ac.koreatech.teamproject.databinding.ActivityQnaDialogBinding;

public class QnaDialogActivity extends Activity {
    private ActivityQnaDialogBinding binding;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQnaDialogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = (int) (display.getWidth() * 0.85); //Display 사이즈의 70%
        int height = (int) (display.getHeight() * 0.6);  //Display 사이즈의 90%

        getWindow().getAttributes().width = width;
        getWindow().getAttributes().height = height;

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
    }

    public void addButton_onClick(View view) {
        SimpleDateFormat sDate2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        createPosterQnA(title,
                binding.qnaActivityTitleEditText.getText().toString(),
                SettingFragment.userName,
                binding.qnaActivityBodyEditText.getText().toString(),
                sDate2.format(new Date()));
    }

    private void createPosterQnA(String poster_title, String title, String user_name, String body, String date) {

        Map<String, ArrayList<String>> city = new HashMap<>();
        city.put("list", new ArrayList<>());

        db.collection("QnA" + poster_title).document(title + "|" + user_name + "|" + date)
                .set(city)
                .addOnSuccessListener(aVoid -> {

                    DocumentReference washingtonRef = db.collection("QnA" + poster_title).document(title + "|" + user_name + "|" + date);
                    washingtonRef.update("body", FieldValue.arrayUnion(body))
                            .addOnSuccessListener(bVoid -> {
                                Log.d("TAG", "DocumentSnapshot successfully update");
                                finish();
                            })
                            .addOnFailureListener(e -> {
                                Log.w("TAG", "Error update document", e);
                            });

                    Log.d("TAG", "DocumentSnapshot successfully written!");
                })
                .addOnFailureListener(e -> {
                    Log.w("TAG", "Error writing document", e);
                });


    }

}