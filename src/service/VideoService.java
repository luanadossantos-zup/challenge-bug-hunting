package service;

import model.Video;

import java.util.List;

public interface VideoService {
    void addVideo(Video video);
    List<Video> listVideos();
    void deleteVideo(Video video);
}