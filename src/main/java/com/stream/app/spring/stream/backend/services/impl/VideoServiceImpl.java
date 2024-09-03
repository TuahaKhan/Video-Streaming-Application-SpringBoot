//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.stream.app.spring.stream.backend.services.impl;

import com.stream.app.spring.stream.backend.entities.Video;
import com.stream.app.spring.stream.backend.repository.VideoRepository;
import com.stream.app.spring.stream.backend.services.VideoService;
import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class VideoServiceImpl implements VideoService {
    @Value("${files.video}")
    String DIR;
    private VideoRepository videoRepository;

    public VideoServiceImpl(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    @PostConstruct//This will run in beginning of bean lifecycle
    public void init() {
        File file = new File(this.DIR);
        if (!file.exists()) {
            file.mkdir();
            System.out.println("Folder Created");
        } else {
            System.out.println("Folder Exist");
        }

    }

    public Video save(Video video, MultipartFile file) {
        try {
            String filename = file.getOriginalFilename();
            String contentType = file.getContentType();
            InputStream inputStream = file.getInputStream();

            //getting cleaned filename
            String cleanFileName = StringUtils.cleanPath(filename);
            //getting clean file path
            String cleanFolder = StringUtils.cleanPath(this.DIR);
//            merging file name and path to create the final video saving location
            Path path = Paths.get(cleanFolder, cleanFileName);
            //Saving video in path
            Files.copy(inputStream, path, new CopyOption[]{StandardCopyOption.REPLACE_EXISTING});

//            Setting remaing meta data and saving meta data in mysql db
            video.setContentType(contentType);
            video.setFilePath(path.toString());
            return videoRepository.save(video);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Video get(String videoId) {


        Video video = videoRepository.findById(videoId).orElseThrow(() -> new RuntimeException("Video Not Found"));
        return video;
    }

    public Video getByTitle(String title) {
        return null;
    }

    public List<Video> getAll() {
        return videoRepository.findAll();
    }
}