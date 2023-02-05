package com.example.firebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CourseRVAdapter extends RecyclerView.Adapter<CourseRVAdapter.ViewHolder> {

    int lastPos = -1;
    private ArrayList<CourseRVModal> courseRVModalArrayList;
    private Context context;
    private CourseClickInterface courseClickInterface;

    public CourseRVAdapter(ArrayList<CourseRVModal> courseRVModalArrayList, Context context, CourseClickInterface courseClickInterface) {
        this.courseRVModalArrayList = courseRVModalArrayList;
        this.context = context;
        this.courseClickInterface = courseClickInterface;
    }

    @NonNull
    @Override
    public CourseRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.course_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseRVAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView  courseIV;
        private TextView courseNameTV,coursePriceTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            courseNameTV = itemView.findViewById(R.id.idTVCourseName);
            coursePriceTV = itemView.findViewById(R.id.idTVPrice);
            courseIV = itemView.findViewById(R.id.idIVCourse);
        }
    }
    public interface CourseClickInterface{
        void onCourseClick(int position);
    }
}
