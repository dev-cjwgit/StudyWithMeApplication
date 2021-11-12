package adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import entity.FrontPoster;
import entity.StudyEntity;
import kr.ac.koreatech.teamproject.R;

class StudyRecyclerViewHolder extends RecyclerView.ViewHolder {

    public ImageView bitmap;
    public TextView title;
    public TextView profName;
    public TextView currPeople;
    public TextView introduce;


    public StudyRecyclerViewHolder(View itemView, ArrayList<StudyEntity> items) {
        super(itemView);
        bitmap = itemView.findViewById(R.id.study_recyclerview_image);
        title = itemView.findViewById(R.id.study_recyclerview_title);
        profName = itemView.findViewById(R.id.study_recyclerview_profname);
        currPeople = itemView.findViewById(R.id.study_recyclerview_people);
        introduce = itemView.findViewById(R.id.study_recyclerview_introduce);

        itemView.setOnClickListener(v -> {
            // TODO : process click event.
            int pos = getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                System.out.println(items.get(pos).getTitle() + " 에 접속함?");
            }
        });
    }
}


public class StudyRecyclerViewAdapter extends RecyclerView.Adapter<StudyRecyclerViewHolder> {

    private ArrayList<StudyEntity> datas;

    public void setData(ArrayList<StudyEntity> list) {
        datas = list;
    }

    @Override
    public StudyRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


// 사용할 아이템의 뷰를 생성해준다.
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.controll_recyclerview_study, parent, false);

        StudyRecyclerViewHolder holder = new StudyRecyclerViewHolder(view, datas);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull StudyRecyclerViewHolder holder, int position) {
        StudyEntity data = datas.get(position);

        holder.title.setText(data.getTitle());
        holder.bitmap.setImageBitmap(data.getBitmap());
        holder.introduce.setText(data.getIntroduce());
        holder.currPeople.setText(data.getCurrPeople() + "명");
        holder.profName.setText(data.getProfName());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
