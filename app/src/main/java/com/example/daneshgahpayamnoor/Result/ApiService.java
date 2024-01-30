package com.example.daneshgahpayamnoor.Result;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET(value = "examitem_list/{student_id}")
    Single<List<Student>> getStudent(@Path("student_id") String student_id);

    @GET(value = "examitem_list/{student_id}")
    Single<ErrorData> getError(@Path("student_id") String student_id);
}
