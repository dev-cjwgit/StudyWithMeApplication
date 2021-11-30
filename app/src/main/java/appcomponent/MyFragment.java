package appcomponent;

import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import kr.ac.koreatech.teamproject.MainActivity;
import kr.ac.koreatech.teamproject.R;

// test commit
public class MyFragment {
    private static MainActivity context;
    private static long backKeyPressedTime = 0;
    public static void setMainActivity(MainActivity main) {
        context = main;
    }

    public static Boolean changeFragment(Fragment frag) {
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간에 5초를 더해 현재 시간과 비교 후
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간이 5초가 지나지 않았으면 종료
        return changeFragment(frag, true);

    }

    public static Boolean changeFragment(Fragment frag, boolean flag) {
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간에 5초를 더해 현재 시간과 비교 후
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간이 5초가 지나지 않았으면 종료
        if (System.currentTimeMillis() >= backKeyPressedTime + 5000 || flag) {

            FragmentManager fm = context.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();

            fragmentTransaction.replace(R.id.fragment1, frag);
            fragmentTransaction.commit();
            backKeyPressedTime = System.currentTimeMillis();

            return true;
        }
        return false;
    }

    public static Fragment getCurrFragment() {
        for (Fragment fragment : context.getSupportFragmentManager().getFragments()) { // 여기 이해 안돼요..
            if (fragment.isVisible())
                return fragment;
        }
        return null;
    }
}
