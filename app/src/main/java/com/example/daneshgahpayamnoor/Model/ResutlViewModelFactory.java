package com.example.daneshgahpayamnoor.Model;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ResutlViewModelFactory implements ViewModelProvider.Factory {
    private MainRepository repository;
    private String studentId;
    public ResutlViewModelFactory(MainRepository repository,String studentId) {
        this.repository=repository;
        this.studentId=studentId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ResultViewModel(repository,studentId);
    }
}
