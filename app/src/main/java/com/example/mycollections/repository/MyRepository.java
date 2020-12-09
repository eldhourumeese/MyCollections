package com.example.mycollections.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.mycollections.model.DataResponseModel;

public interface MyRepository {

    MutableLiveData<DataResponseModel> getDataList( int pageIndex);
    MutableLiveData<String> getErrorMessage();
}
