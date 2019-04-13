package com.bookingsystemfortutoring;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bookingsystemfortutoring.course_response.CoursesModel;
import com.bookingsystemfortutoring.course_response.Data;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinedActiity extends BaseActivity implements Callback<CoursesModel> {

    private RecyclerView mLstCoursesList;
    private List<Data> mLstCourse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joined);

        mLstCoursesList = findViewById(R.id.courseList);

        getJoinedCOurses();


    }

    private void getJoinedCOurses() {
        showProgressDialog();
        RetrofitAdapter retrofitAdapter = new RetrofitAdapter();
        Call<CoursesModel> call = retrofitAdapter.getRetrofitApiService().getSelectedCourses(getIntent().getExtras().getInt("user_id"));
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<CoursesModel> call, Response<CoursesModel> response) {
        dismissProgressDialog();
        if (response.body() != null) {
            mLstCourse = response.body().getResponse().getData();

            CourseAdapter courseAdapter = new CourseAdapter(mLstCourse, true, null);
            LinearLayoutManager lm = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false) {
                @Override
                public boolean canScrollVertically() {
                    return true;
                }
            };
            mLstCoursesList.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 0));
            mLstCoursesList.setLayoutManager(lm);
            mLstCoursesList.setItemAnimator(new DefaultItemAnimator());
            mLstCoursesList.setAdapter(courseAdapter);
        }
    }

    @Override
    public void onFailure(Call<CoursesModel> call, Throwable t) {
        dismissProgressDialog();
    }
}
