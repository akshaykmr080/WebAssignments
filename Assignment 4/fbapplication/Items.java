package com.example.akshaykumars.fbapplication;

/**
 * Created by Akshay Kumar S on 4/13/2017.
 */

public class Items {
    public String id;
    public String name;
    public String url;

    public Items(String id, String name, String url){
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
