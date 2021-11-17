package kr.ac.koreatech.teamproject;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import kr.ac.koreatech.teamproject.databinding.ActivityAnswerDialogBinding;
import kr.ac.koreatech.teamproject.databinding.ActivityJoinStudygroupDialogBinding;
import kr.ac.koreatech.teamproject.databinding.ActivityLoginBinding;

public class JoinDialogActivity extends Activity {
    private ActivityJoinStudygroupDialogBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJoinStudygroupDialogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); //
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = (int) (display.getWidth() * 0.85); //Display 사이즈의 70%
        int height = (int) (display.getHeight() * 0.6);  //Display 사이즈의 90%

        getWindow().getAttributes().width = width;
        getWindow().getAttributes().height = height;


    }

    public void addButton_onClick(View view) {
        System.out.println("가입 신청\n" +
                "" + binding.joinDialogBody.getText().toString());
        finish();
    }
}