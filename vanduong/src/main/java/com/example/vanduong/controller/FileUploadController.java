package com.example.vanduong.controller;

import com.example.vanduong.models.ResponseObject;
import com.example.vanduong.services.IStorageService;
import org.hibernate.sql.results.internal.ResolvedSqlSelection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

}
