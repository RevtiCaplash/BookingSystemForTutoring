package com.bookingsystemfortutoring;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bookingsystemfortutoring.course_response.Data;
import com.bookingsystemfortutoring.interfaces.OnCourseClickListener;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.MyViewHolder> {

    private List<Data> mLstCourse;
    private boolean isFromJoined;
    private OnCourseClickListener onCourseClickListener;

    CourseAdapter(List<Data> mLstCourse, boolean isFromJoined, OnCourseClickListener onCourseClickListener) {
        this.mLstCourse = mLstCourse;
        this.isFromJoined = isFromJoined;
        this.onCourseClickListener = onCourseClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_course, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        myViewHolder.mTxtName.setText(mLstCourse.get(i).getName());
        String s1[] = mLstCourse.get(i).getDate().split("T");
        String s2[] = s1[1].split("\\+");
        String finalDate = s1[0] + " " + s2[0];
        myViewHolder.mTxtDate.setText(finalDate);
        myViewHolder.mTxtDetails.setText(mLstCourse.get(i).getDescription());
        if (isFromJoined) {
            myViewHolder.mTxtAdd.setVisibility(View.GONE);
        } else {
            myViewHolder.mTxtAdd.setVisibility(View.VISIBLE);
        }
        myViewHolder.mTxtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCourseClickListener.getId(mLstCourse.get(i).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mLstCourse.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTxtName;
        private TextView mTxtDate;
        private TextView mTxtDetails;
        private TextView mTxtAdd;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTxtName = itemView.findViewById(R.id.name);
            mTxtDate = itemView.findViewById(R.id.date);
            mTxtDetails = itemView.findViewById(R.id.details);
            mTxtAdd = itemView.findViewById(R.id.add);
        }
    }
}
