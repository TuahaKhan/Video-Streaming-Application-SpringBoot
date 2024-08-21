package com.stream.app.spring.stream.backend.services.impl;

import ch.qos.logback.core.util.StringUtil;
import com.stream.app.spring.stream.backend.entities.Video;
import com.stream.app.spring.stream.backend.repository.VideoRepository;
import com.stream.app.spring.stream.backend.services.VideoService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service

public class VideoServiceImpl implements VideoService {

    @Value("${files.video}")
    String DIR;

    private VideoRepository videoRepository;

    public VideoServiceImpl(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    //We need to check if the folder where video will be saved exists or not if not create
    @PostConstruct//As soon as bean is created this method will be initialized (Beginning Of Bean LifeCycle)
    public void init(){

        File file = new File(DIR);

        if(!file.exists()){
            file.mkdir();
            System.out.println("Folder Created");
        }else{
            System.out.println("Folder Exist");
        }
    }

    @Override
    public Video save(Video video, MultipartFile file) {

        try {
            //fetches file name if sample.mp3 it will fetch sample
            String filename = file.getOriginalFilename();
            String contentType = file.getContentType();
            InputStream inputStream = file.getInputStream();

            //file path
            String cleanFileName=StringUtils.cleanPath(filename);
            //folder path
            String cleanFolder=StringUtils.cleanPath(DIR);
            //merging video to be saved path with video file name (appending video name in path to complete path)
           Path path= Paths.get(cleanFolder,cleanFileName);
           //Saving video
            Files.copy(inputStream,path, StandardCopyOption.REPLACE_EXISTING);

            //saving metadata of video
            video.setContentType(contentType);
            video.setFilePath(path.toString());

            //saving metadata
            return videoRepository.save(video);

        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Video get(String videoId) {
        return null;
    }

    @Override
    public Video getByTitle(String title) {
        return null;
    }

    @Override
    public List<Video> getAll() {
        return null;
    }
}
