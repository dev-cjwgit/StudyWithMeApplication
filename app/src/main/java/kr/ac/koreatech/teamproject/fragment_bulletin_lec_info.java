package kr.ac.koreatech.teamproject;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.ArrayList;

import adapter.PosterListViewAdapter;
import entity.PosterEntity;
import kr.ac.koreatech.teamproject.databinding.FragmentPosterMainBinding;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_bulletin_lec_info#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_bulletin_lec_info extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentPosterMainBinding binding;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_bulletin_lec_info() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static fragment_bulletin_lec_info newInstance(String param1, String param2) {
        fragment_bulletin_lec_info fragment = new fragment_bulletin_lec_info();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        binding = FragmentPosterMainBinding.inflate(getLayoutInflater());
        // binding.LecIDTextview2.setText(getTitle());
        // TODO: 채요니 여기 겟 해결해야함 ㅋ
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("강의 정보");
        actionBar.setDisplayHomeAsUpEnabled(true);
        return binding.getRoot();
    }

    public ArrayList<PosterEntity> list;

//    public View getView(int i, View view, ViewGroup viewGroup) {
//        final Context context = viewGroup.getContext();
//        PosterEntity listItem = list.get(i);
//
//        if(view == null) {
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            view = inflater.inflate(R.layout.fragment_poster_main, viewGroup,false);
//
//        }
//        TextView title = view.findViewById(R.id.LecIDTextview2);
//        TextView profName = view.findViewById(R.id.profIDTextview2);
//        TextView profEmail = view.findViewById(R.id.profemailTextview2);
//        TextView phone = view.findViewById(R.id.profdiaTextview2);
//        TextView assistEmail = view.findViewById(R.id.assi_email_Textview2);
//        TextView lecturePlan = view.findViewById(R.id.lectureplan);
//        TextView mainBook = view.findViewById(R.id.main_tb_textview2);
//        TextView subBook = view.findViewById(R.id.sub_tb_textview2);
//
//        title.setText(listItem.getTitle());
//        profName.setText(listItem.getProfName());
//        profEmail.setText(listItem.getProfEmail());
//        phone.setText(listItem.getPhone());
//        assistEmail.setText(listItem.getAssistEmail());
//        lecturePlan.setText(listItem.getLecturePlan());
//        mainBook.setText(listItem.getMainBook());
//        subBook.setText(listItem.getSubBook());
//
//        return view;
//    }

}