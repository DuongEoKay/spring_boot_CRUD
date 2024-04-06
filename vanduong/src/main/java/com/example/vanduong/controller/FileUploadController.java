package com.example.vanduong.controller;

import com.example.vanduong.models.ResponseObject;
import com.example.vanduong.services.IStorageService;
import org.hibernate.sql.results.internal.ResolvedSqlSelection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/api/v1/fileupload")
public class FileUploadController {
    @Autowired
    private IStorageService StorageService;
    @PostMapping("")
    public ResponseEntity<ResponseObject>uploadFile(@RequestParam("file")MultipartFile file)
    {
        try{
            String generatedFileName=StorageService.storeFile(file);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "upload file successfully !!!", generatedFileName)
            );
        }
        catch (Exception exception)
        {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "cant upload file !!!","")
            );
        }
    }




    @GetMapping("/file/{fileName:.+}")
    public ResponseEntity<byte[]> readDetailFile(@PathVariable String fileName)
    {
        try {
            byte[] bytes=StorageService.readFileContent(fileName);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);

        }catch (Exception exception)
        {
            return ResponseEntity.noContent().build();
        }
    }


    @GetMapping("")
    public ResponseEntity<ResponseObject> getAllUploadedFile()
    {
        try {
            List<String> urls=StorageService.loadAll().map(path -> {
                String urlPath= MvcUriComponentsBuilder.fromMethodName(
                                FileUploadController.class,
                                "readDetailFile",
                                path.getFileName().toString()).build().toUriString();
                return urlPath;
            }).collect(Collectors.toList());
            return ResponseEntity.ok(
                    new ResponseObject("ok", "List all file successfully !!!", urls)
            );
        }catch (Exception exception)
        {
            return ResponseEntity.ok(new ResponseObject("failed","cant list all files !!!", ""));
        }
    }

}
