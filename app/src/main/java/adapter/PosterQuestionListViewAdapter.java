package adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import entity.PosterEntity;
import entity.QuestionListEntity;
import kr.ac.koreatech.teamproject.R;

public class PosterQuestionListViewAdapter extends BaseAdapter implements Serializable, Cloneable {
    public ArrayList<QuestionListEntity> list;

    public PosterQuestionListViewAdapter() {
        list = new ArrayList<>();
    }


    public Object clone() {
        PosterListViewAdapter vo = null;
        try {
            vo = (PosterListViewAdapter) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return vo;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Context context = viewGroup.getContext();
        QuestionListEntity listItem = list.get(i);

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.controll_questionlist_listview, viewGroup, false);
        }

        TextView title = view.findViewById(R.id.question_title_textView);
        TextView name = view.findViewById(R.id.question_name_textView);
        TextView answer = view.findViewById(R.id.question_answer_textView);
        TextView date = view.findViewById(R.id.question_date_textView);


        title.setText(listItem.getTitle());
        name.setText(listItem.getName());
        answer.setText(listItem.getAnswer() + "개");
        date.setText(listItem.getDate().toString());

        return view;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public ArrayList<QuestionListEntity> getList() {
        return this.list;
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    public void append(QuestionListEntity obj) {
        list.add(obj);
        notifyDataSetChanged();
    }

}