package com.example.sundari.accidentinfo;

public class Upload {

    private String ImageUrl , VideoUrl;
    private long VideoName;

    public Upload() {
        //empty constructor needed
    }

    public Upload(String ImageUrl) {
        this.ImageUrl = ImageUrl;
    }

    public Upload(String videoUrl, long videoName) {
        VideoUrl = videoUrl;
        VideoName = videoName;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getVideoUrl() {
        return VideoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        VideoUrl = videoUrl;
    }

}
