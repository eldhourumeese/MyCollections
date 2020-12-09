package com.example.mycollections.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
/**
 * Awesome Pojo Generator
 * */
public class ContentItems{
  @SerializedName("content")
  @Expose
  private ArrayList<Content> content;
  public void setContent(ArrayList<Content> content){
   this.content=content;
  }
  public ArrayList<Content> getContent(){
   return content;
  }
}