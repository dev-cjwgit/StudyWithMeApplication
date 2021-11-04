package kr.ac.koreatech.teamproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import kr.ac.koreatech.teamproject.databinding.FragmentMainBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    private FragmentMainBinding binding;

    private RecyclerAdapter m1Adapter;
    private RecyclerAdapter m2Adapter;
    private LinearLayoutManager m1LayoutManager;
    private LinearLayoutManager m2LayoutManager;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
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
        binding = FragmentMainBinding.inflate(getLayoutInflater());
        // RecyclerView binding
        //        mVerticalView = (RecyclerView) findViewById(R.id.vertical_list);

        // init Data
        ArrayList<FrontPoster> data1 = new ArrayList<>();

        data1.add(new FrontPoster(BitmapFactory.decodeResource(getResources(), R.drawable.icon_board), "모바일프로그래밍"));
        data1.add(new FrontPoster(BitmapFactory.decodeResource(getResources(), R.drawable.icon_board), "소프트웨어공학"));
        data1.add(new FrontPoster(BitmapFactory.decodeResource(getResources(), R.drawable.icon_board), "자바프로그래밍"));
        data1.add(new FrontPoster(BitmapFactory.decodeResource(getResources(), R.drawable.icon_board), "IoT개론및실습"));
        data1.add(new FrontPoster(BitmapFactory.decodeResource(getResources(), R.drawable.icon_board), "스크립트프로그래밍"));
        data1.add(new FrontPoster(BitmapFactory.decodeResource(getResources(), R.drawable.icon_board), "객체지향개발론및실습"));
        data1.add(new FrontPoster(BitmapFactory.decodeResource(getResources(), R.drawable.icon_board), "컴퓨터네트워크"));


        ArrayList<FrontPoster> data2 = new ArrayList<>();

        data2.add(new FrontPoster(BitmapFactory.decodeResource(getResources(), R.drawable.icon_book), "정처기합격"));
        data2.add(new FrontPoster(BitmapFactory.decodeResource(getResources(), R.drawable.icon_book), "CAD 스터디"));
        data2.add(new FrontPoster(BitmapFactory.decodeResource(getResources(), R.drawable.icon_book), "C언어 모각코"));
        data2.add(new FrontPoster(BitmapFactory.decodeResource(getResources(), R.drawable.icon_book), "과탑먹을사람들"));
        data2.add(new FrontPoster(BitmapFactory.decodeResource(getResources(), R.drawable.icon_book), "모각코"));
        data2.add(new FrontPoster(BitmapFactory.decodeResource(getResources(), R.drawable.icon_book), "공대생들어와"));
        data2.add(new FrontPoster(BitmapFactory.decodeResource(getResources(), R.drawable.icon_book), "중국어같이공부하자"));
        // init LayoutManager
        m1LayoutManager = new LinearLayoutManager(this.getActivity());
        m1LayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); // 기본값이 VERTICAL

        m2LayoutManager = new LinearLayoutManager(this.getActivity());
        m2LayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); // 기본값이 VERTICAL

        // setLayoutManager
        binding.fragmentMainPosterListView.setLayoutManager(m1LayoutManager);
        binding.fragmentMainStudyListView.setLayoutManager(m2LayoutManager);


        // init Adapter
        m1Adapter = new RecyclerAdapter();
        m2Adapter = new RecyclerAdapter();
        // set Data
        m1Adapter.setData(data1);
        m2Adapter.setData(data2);

        // set Adapter
        binding.fragmentMainPosterListView.setAdapter(m1Adapter);

        binding.fragmentMainStudyListView.setAdapter(m2Adapter);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("메인");
        actionBar.setDisplayHomeAsUpEnabled(true);
        return binding.getRoot();
    }
}

class FrontPoster {
    private Bitmap bitmap;
    private String title;

    public FrontPoster(Bitmap bitmap, String title) {
        this.title = title;
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getTitle() {
        return title;
    }
}

class RecyclerViewHolder extends RecyclerView.ViewHolder {

    public ImageView icon;
    public TextView description;

    public RecyclerViewHolder(View itemView, ArrayList<FrontPoster> items) {
        super(itemView);
        icon = (ImageView) itemView.findViewById(R.id.controll_recycler_imageView);
        description = (TextView) itemView.findViewById(R.id.controll_recycler_textView);

        itemView.setOnClickListener(v -> {
            // TODO : process click event.
            int pos = getAdapterPosition();
            if(pos != RecyclerView.NO_POSITION){
                System.out.println(items.get(pos).getTitle() + " 에 접속함?");
            }
        });
    }
}

class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private ArrayList<FrontPoster> datas;

    public void setData(ArrayList<FrontPoster> list) {
        datas = list;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


// 사용할 아이템의 뷰를 생성해준다.
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.controll_recyclerview_poster, parent, false);

        RecyclerViewHolder holder = new RecyclerViewHolder(view, datas);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        FrontPoster data = datas.get(position);

        holder.description.setText(data.getTitle());
        holder.icon.setImageBitmap(data.getBitmap());

    }


    @Override
    public int getItemCount() {
        return datas.size();
    }
}

//
//class MyListDecoration extends RecyclerView.ItemDecoration {
//
//    @Override
//    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//
//        if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
//            outRect.right = 30;
//        }
//    }
//}
//
//class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
//
//    private ArrayList<Bitmap> itemList;
//    private Context context;
//    private View.OnClickListener onClickItem;
//
//    public MyAdapter(Context context, ArrayList<Bitmap> itemList, View.OnClickListener onClickItem) {
//        this.context = context;
//        this.itemList = itemList;
//        this.onClickItem = onClickItem;
//    }
//
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        // context 와 parent.getContext() 는 같다.
//        View view = LayoutInflater.from(context)
//                .inflate(R.layout.controll_recyclerview_poster, parent, false);
//
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        Bitmap draw = itemList.get(position);
//
////        Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.login_logo);
//        holder.imageView.setImageBitmap(draw);
//    }
//
//    @Override
//    public int getItemCount() {
//        return itemList.size();
//    }
//
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        public ImageView imageView;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//
//            imageView = itemView.findViewById(R.id.controll_recycler_imageView1);
//        }
//    }
//}