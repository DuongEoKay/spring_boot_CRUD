package com.example.vanduong.services;


import java.util.stream.Stream;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;


public interface IStorageService {
    public String storeFile(MultipartFile file);
    public Stream<Path> loadAll();
    public byte[] readFileContent(String filename);
    public void deleteAllFile();

    
}
