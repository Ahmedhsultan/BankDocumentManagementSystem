package com.example.BankDocumentManagementSystem.controller;

import com.example.BankDocumentManagementSystem.dto.responce.DocumentDTOResp;
import com.example.BankDocumentManagementSystem.persistence.entity.Document;
import com.example.BankDocumentManagementSystem.persistence.repository.DocumentRepo;
import com.example.BankDocumentManagementSystem.service.DocumentService;
import com.example.BankDocumentManagementSystem.util.mapper.DocumentMapper;
import com.example.BankDocumentManagementSystem.util.records.DocumentParam;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@RequestMapping("document")
public class DocumentController extends BaseController<Integer, Document, DocumentRepo, DocumentDTOResp, DocumentMapper, DocumentService> {
    private DocumentService documentService;
    public DocumentController(DocumentService documentService){
        this.documentService = documentService;
    }

    @DeleteMapping("delete")
    public ResponseEntity<String> delete(@RequestParam("username") String userName,
                                         @RequestParam("filename") String fileName){
        /**
         * In case we use spring security we will get username from JWT token
         * But spring security isn't covered in this task
         * So we take username as parameter
         */

        //Delete resource from server
        var document = new DocumentParam(userName, fileName);
        documentService.delete(document);

        return ResponseEntity.ok("Success");
    }

    @PostMapping("upload")
    public ResponseEntity<String> upload(@RequestParam("file")MultipartFile multipartFile,
                @RequestParam("username") String userName){
        /**
         * In case we use spring security we will get username from JWT token
         * But spring security isn't covered in this task
         * So we take username as parameter
         */

        //Check if file is empty
        if (multipartFile.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body("Please select a file to upload.");

        //Get file name and inputstream and run upload method
        try {
            //Get file date
            String fileName = multipartFile.getOriginalFilename();
            InputStream inputStream = multipartFile.getInputStream();

            //Add file to system and database
            var document = new DocumentParam(userName, fileName);
            documentService.upload(document, inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body("Failed Operation!!");
        }

        return ResponseEntity.ok("Success");
    }

    @GetMapping("download")
    public ResponseEntity<Resource> download(@RequestParam("username") String userName,
                                           @RequestParam("filename") String fileName) {
        /**
         * In case we use spring security we will get username from JWT token
         * But spring security isn't covered in this task
         * So we take username as parameter
         */

        //Get resource from server
        var document = new DocumentParam(userName, fileName);
        Resource resource = documentService.download(document);

        // Set the response headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

        return ResponseEntity.ok().headers(headers).body(resource);
    }
}
