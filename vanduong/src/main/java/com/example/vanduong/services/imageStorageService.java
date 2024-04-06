package com.example.vanduong.services;

import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Stream;



@Service
public class imageStorageService  implements IStorageService{
    private final Path storageFolder= Paths.get("upload");

    public static final float FILESIZE=5.0f;

    private boolean isImageFile(MultipartFile file)
    {
        String fileExtension= FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.asList(new String[]{"png", "jpg", "jpeg", "bmp"}).contains(fileExtension.trim().toLowerCase());
    }

    private boolean checkFileSize(MultipartFile file)
    {
        float fileSize=file.getSize()/1000000.0f;
        if(fileSize<FILESIZE)
        {
            return true;
        }
        else {
            return false;
        }
    }


    @Override
    public String storeFile(MultipartFile file) {
        try {
            System.out.println("start storing file");
            if (file.isEmpty()) {
                throw new RuntimeException("cant store empty file");
            }
            if (!isImageFile(file)) {
                throw new RuntimeException("cant store only image type file");
            }

            if(!checkFileSize(file))
            {
                throw new RuntimeException("file size must be smaller than 5mb");
            }

            String fileExtension =FilenameUtils.getExtension(file.getOriginalFilename());
            String generatedFileName= UUID.randomUUID().toString().replace("-","");
            generatedFileName=generatedFileName+"."+fileExtension;
            Path destinationFilePath=this.storageFolder.resolve(
                    Paths.get(generatedFileName)).normalize().toAbsolutePath();
            if (!destinationFilePath.getParent().equals(this.storageFolder.toAbsolutePath()))
            {
                throw new RuntimeException("cannot store outside current directory");
            }
            try (InputStream inputStream=file.getInputStream()){
                Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);

            }

            return generatedFileName;
        }

        catch (IOException exception) {
            throw new RuntimeException("khong the luu file", exception);
        }

    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.storageFolder, 1)
                    .filter(path -> !path.equals(this.storageFolder))
                    .map(this.storageFolder::relativize);
        }catch (IOException exception)
        {
            throw new RuntimeException("failed to load stored files !!!",exception);
        }
    }

    @Override
    public byte[] readFileContent(String fileName) {
        try {
            Path file =storageFolder.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists()||resource.isReadable())
            {
                byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
                return bytes;
            }
            else{
                throw new RuntimeException("could not read file"+fileName);
            }
        }
        catch (IOException exception)
        {
            throw new RuntimeException("could not read file: "+fileName,exception);
        }
    }

    @Override
    public void deleteAllFile() {
    }
}
