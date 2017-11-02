
package com.example.android.flixt.service.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoResponse {

    @SerializedName("id")
    private int id;

    @SerializedName("results")
    private List<Video> videos = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public VideoResponse() {
    }

    /**
     * 
     * @param id movie id
     * @param videos list of videos
     */
    public VideoResponse(int id, List<Video> videos) {
        super();
        this.id = id;
        this.videos = videos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

}
