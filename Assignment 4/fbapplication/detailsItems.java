package com.example.akshaykumars.fbapplication;

import java.util.ArrayList;

/**
 * Created by Akshay Kumar S on 4/14/2017.
 */

public class detailsItems {

    private String albumName;
    private ArrayList<String> urlData = new ArrayList<String>();
    private ArrayList<String> idData = new ArrayList<String>();

    public detailsItems(String albuName, ArrayList<String> urlData, ArrayList<String> idData){
        this.albumName = albuName;
        this.urlData = urlData;
        this.idData = idData;
    }

    public String getAlbumName() {
        return albumName;
    }

    public ArrayList<String> getUrlData() {
        return urlData;
    }

    public void setUrlData(ArrayList<String> urlData) {
        this.urlData = urlData;
    }

    public ArrayList<String> getIdData() {
        return idData;
    }

    public void setIdData(ArrayList<String> idData) {
        this.idData = idData;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

}
