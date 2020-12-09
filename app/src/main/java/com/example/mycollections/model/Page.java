package com.example.mycollections.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class Page{
  @SerializedName("page-num")
  @Expose
  private Integer page_num;
  @SerializedName("page-size")
  @Expose
  private Integer page_size;
  @SerializedName("content-items")
  @Expose
  private ContentItems content_items;
  @SerializedName("total-content-items")
  @Expose
  private Integer total_content_items;
  @SerializedName("title")
  @Expose
  private String title;

    public Integer getPage_num() {
        return page_num;
    }

    public void setPage_num(Integer page_num) {
        this.page_num = page_num;
    }

    public Integer getPage_size() {
        return page_size;
    }

    public void setPage_size(Integer page_size) {
        this.page_size = page_size;
    }

    public ContentItems getContent_items() {
        return content_items;
    }

    public void setContent_items(ContentItems content_items) {
        this.content_items = content_items;
    }

    public Integer getTotal_content_items() {
        return total_content_items;
    }

    public void setTotal_content_items(Integer total_content_items) {
        this.total_content_items = total_content_items;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}