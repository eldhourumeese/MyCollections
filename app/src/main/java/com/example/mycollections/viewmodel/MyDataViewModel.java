package com.example.mycollections.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mycollections.model.DataResponseModel;
import com.example.mycollections.repository.MyRepository;

public class MyDataViewModel extends ViewModel {

    public MutableLiveData<DataResponseModel> response;
    private MyRepository repository;
    public MutableLiveData<String> errorMessage;

    public void setRepository(MyRepository mRepository) {
        this.repository = mRepository;

    }

    public void getDataList( int index ) {
        response = repository.getDataList(index);
    }

    public void getErrorMsg(){
        errorMessage = repository.getErrorMessage();
    }
}
