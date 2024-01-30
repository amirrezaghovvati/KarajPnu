package com.example.daneshgahpayamnoor.Model;

import com.example.daneshgahpayamnoor.Result.ApiService;
import com.example.daneshgahpayamnoor.Result.ErrorData;
import com.example.daneshgahpayamnoor.Result.Student;

import java.util.List;

import io.reactivex.Single;

public class MainRepository {
    private ApiService apiService;
    public MainRepository(ApiService apiService){this.apiService=apiService;}
    public Single<List<Student>> getStudentInfo(String studentId){
        return apiService.getStudent(studentId);
    }
    public Single<ErrorData> getError(String studentId){
        return apiService.getError(studentId);
    }
}
