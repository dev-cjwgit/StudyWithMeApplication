package appcomponent;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import kr.ac.koreatech.teamproject.MainActivity;
import kr.ac.koreatech.teamproject.R;

public class MyFragment {
    private static MainActivity context;

    public static void setMainActivity(MainActivity main) {
        context = main;
    }

    public static void changeFragment(Fragment frag) {
        FragmentManager fm = context.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        fragmentTransaction.replace(R.id.fragment1, frag);
        fragmentTransaction.commit();

    }
}
