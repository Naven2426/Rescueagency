package com.example.rescueagency.admin.HomeFragment;

public class AdminCategoryList {

    private String categoryimage;
    private String categoryname;
    private String categoryinfo;

    public AdminCategoryList(String categoryimage, String categoryname, String categoryinfo) {
        this.categoryimage = categoryimage;
        this.categoryname = categoryname;
        this.categoryinfo = categoryinfo;
    }

    public String getCategoryimage() {
        return categoryimage;
    }

    public void setCategoryimage(String categoryimage) {
        this.categoryimage = categoryimage;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getCategoryinfo() {
        return categoryinfo;
    }

    public void setCategoryinfo(String categoryinfo) {
        this.categoryinfo = categoryinfo;
    }
}
