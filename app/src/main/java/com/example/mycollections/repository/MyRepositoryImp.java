package com.example.mycollections.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.mycollections.model.DataResponseModel;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

public class MyRepositoryImp implements MyRepository {

    private MutableLiveData<DataResponseModel> response;
    private MutableLiveData<String> errorMessage;
    private Gson gson;
    private Context context;

    public MyRepositoryImp(Context context) {
        this.response = new MutableLiveData<>();
        this.errorMessage = new MutableLiveData<>();
        this.context = context;
        this.gson = new Gson();
    }


    @Override
    public MutableLiveData<DataResponseModel> getDataList( int pageIndex) {
        String menus = getAssetJsonData(context, "CONTENTLISTINGPAGE-PAGE" + pageIndex + ".json");
        Log.e("TAG", "home_menu : " + menus);
        Gson gson = new Gson();
        DataResponseModel dataResponseModel = gson.fromJson(menus, DataResponseModel.class);
        response.setValue(dataResponseModel);
        return response;
    }


    @Override
    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    //to get json file from asset folder
    public static String getAssetJsonData(Context context, String name) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(name);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        Log.e("data", json);
        return json;

    }
}
