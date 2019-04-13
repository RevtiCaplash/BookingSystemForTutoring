package com.bookingsystemfortutoring.interfaces;

import com.bookingsystemfortutoring.add_course.CourseRequest;
import com.bookingsystemfortutoring.course_response.CoursesModel;
import com.bookingsystemfortutoring.response.LoginRequest;
import com.bookingsystemfortutoring.response.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by debut_android on 12/1/2015.
 */
public interface ApiInterface {

    @POST("api/login.json")
    Call<LoginResponse> hitLogin(@Body LoginRequest loginRequest);

    @GET("api/courses.json")
    Call<CoursesModel> getCourses();

    @GET("api/users_courses.json")
    Call<CoursesModel> getSelectedCourses(@Query("user_id") int user_id);

    @POST("api/users_courses.json")
    Call<LoginResponse> addCourses(@Body CourseRequest courseRequest);
}