package adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import entity.FrontPoster;
import kr.ac.koreatech.teamproject.R;

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
            if (pos != RecyclerView.NO_POSITION) {
                System.out.println(items.get(pos).getTitle() + " 에 접속함?");
            }
        });
    }
}


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

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
