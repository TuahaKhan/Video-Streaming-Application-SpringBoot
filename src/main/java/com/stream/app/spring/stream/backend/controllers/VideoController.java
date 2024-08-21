package com.stream.app.spring.stream.backend.controllers;

import com.stream.app.spring.stream.backend.entities.Video;
import com.stream.app.spring.stream.backend.payload.CustomMessage;
import com.stream.app.spring.stream.backend.services.VideoService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/videos")
public class VideoController {

    private VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @PostMapping
    public ResponseEntity<CustomMessage> create(@RequestParam("file")MultipartFile file,
                                                @RequestParam("title") String title,
                                                @RequestParam("description") String description){

        //We are getting two things video file and metadata

        //Setting video meta data file with incoming video title, description and a id
        Video video=new Video();
        video.setTitle(title);
        video.setDescription(description);
        video.setVideoId(UUID.randomUUID().toString());

        //passing metadata and video to service class
        videoService.save(video,file);
        return null;
    }
}
