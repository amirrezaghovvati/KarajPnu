package com.example.daneshgahpayamnoor.Model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.daneshgahpayamnoor.Result.ErrorData;
import com.example.daneshgahpayamnoor.Result.Student;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class ResultViewModel extends ViewModel {
    private MainRepository repository;
    private MutableLiveData<List<Student>> studentInfoList=new MutableLiveData<>();
    private MutableLiveData<String> userError=new MutableLiveData<>();
    private CompositeDisposable compositeDisposable=new CompositeDisposable();
    public ResultViewModel(MainRepository repository,String studentId){
        this.repository=repository;
        this.repository.getStudentInfo(studentId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<Student>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<Student> students) {
                            studentInfoList.postValue(students);
                    }

                    @Override
                    public void onError(Throwable e) {
                        repository.getError(studentId)
                                .subscribeOn(Schedulers.io())
                                .subscribe(new SingleObserver<ErrorData>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {
                                        compositeDisposable.add(d);
                                    }

                                    @Override
                                    public void onSuccess(ErrorData errorData) {
                                        userError.postValue(errorData.getData());
                                    }


                                    @Override
                                    public void onError(Throwable e) {

                                    }
                                });
                    }
                });
    }

    public MutableLiveData<List<Student>> getStudentInfoList() {
        return studentInfoList;
    }

    public MutableLiveData<String> getUserError() {
        return userError;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
