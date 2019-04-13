package com.bookingsystemfortutoring;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.bookingsystemfortutoring.add_course.CourseRequest;
import com.bookingsystemfortutoring.course_response.CoursesModel;
import com.bookingsystemfortutoring.course_response.Data;
import com.bookingsystemfortutoring.interfaces.OnCourseClickListener;
import com.bookingsystemfortutoring.response.LoginResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WelcomeActivity extends BaseActivity implements View.OnClickListener, Callback<CoursesModel>, OnCourseClickListener {

    private RecyclerView mLstCoursesList;
    private List<Data> mLstCourse;
    private CourseAdapter courseAdapter;

    private Button mBtnJoinedCourse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mLstCoursesList = findViewById(R.id.courseList);
        mBtnJoinedCourse = findViewById(R.id.joined_courses);

        mBtnJoinedCourse.setOnClickListener(this);

        getCourses();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.joined_courses:
                Intent intent = new Intent(getApplicationContext(), JoinedActiity.class);
                intent.putExtra("user_id", getIntent().getExtras().getInt("user_id"));
                startActivity(intent);
                break;
        }
    }

    private void showMessage(String message, View view) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

    private void getCourses() {
        showProgressDialog();
        RetrofitAdapter retrofitAdapter = new RetrofitAdapter();
        Call<CoursesModel> coursesCall = retrofitAdapter.getRetrofitApiService().getCourses();
        coursesCall.enqueue(this);
    }

    @Override
    public void onResponse(Call<CoursesModel> call, Response<CoursesModel> response) {
        dismissProgressDialog();
        if (response.isSuccessful() && response.body() != null) {
            mLstCourse = response.body().getResponse().getData();
            Log.e("size", "::" + mLstCourse.size());
            setAdapter();
        }
    }

    @Override
    public void onFailure(Call<CoursesModel> call, Throwable t) {
        dismissProgressDialog();
        showMessage("Something went wrong", getWindow().getDecorView());
    }

    private void setAdapter() {
        courseAdapter = new CourseAdapter(mLstCourse, false, this);
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

    @Override
    public void getId(int courseId) {
        showProgressDialog();
        RetrofitAdapter retrofitAdapter = new RetrofitAdapter();
        CourseRequest courseRequest = new CourseRequest();
        courseRequest.setCourse_id(courseId);
        courseRequest.setUser_id(getIntent().getExtras().getInt("user_id"));
        Call<LoginResponse> call = retrofitAdapter.getRetrofitApiService().addCourses(courseRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                dismissProgressDialog();
                showMessage("Course joined successfully", getWindow().getDecorView());
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                dismissProgressDialog();
            }
        });
    }
}