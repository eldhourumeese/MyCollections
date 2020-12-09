package com.example.mycollections.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class DataResponseModel{
  @SerializedName("page")
  @Expose
  private Page page;
  public void setPage(Page page){
   this.page=page;
  }
  public Page getPage(){
   return page;
  }
}