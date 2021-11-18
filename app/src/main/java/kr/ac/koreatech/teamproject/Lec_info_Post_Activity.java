package kr.ac.koreatech.teamproject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import kr.ac.koreatech.teamproject.databinding.FragmentLecInfoBinding;

public class Lec_info_Post_Activity extends Activity {
    private FragmentLecInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentLecInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Display display = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = (int) (display.getWidth() * 0.85);
        int height = (int) (display.getHeight() * 0.6);

        getWindow().getAttributes().width = width;
        getWindow().getAttributes().height = height;

    }

    public void addButton_onClick(View view) {
        System.out.println("질문 등록\n" +
                "" + binding.lecInfoTitleEditText.getText().toString() + "\n" +
                "" + binding.lecInfoBodyEditText.getText().toString());

        finish();
    }
}
