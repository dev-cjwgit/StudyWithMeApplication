package kr.ac.koreatech.teamproject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.contentcapture.ContentCaptureCondition;

import kr.ac.koreatech.teamproject.databinding.ActivityAnswerSharingMatDialogBinding;

public class AnswerSharingDialogActivity extends Activity {
    private ActivityAnswerSharingMatDialogBinding binding;

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
    }

    public void addButton_onClick(View view) {
        System.out.println("질문 등록\n" +
                "" + binding.answerSharingMatDialogBody.getText().toString());

        finish();
    }
}
