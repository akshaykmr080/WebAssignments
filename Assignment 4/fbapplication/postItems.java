package com.example.akshaykumars.fbapplication;

/**
 * Created by Akshay Kumar S on 4/14/2017.
 */

public class postItems {
    String message;
    String story;
    String created_time;

    public postItems(String message, String story, String created_time) {
        this.message = message;
        this.story = story;
        this.created_time = created_time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }
}
