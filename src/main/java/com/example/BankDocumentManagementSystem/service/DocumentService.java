package com.example.BankDocumentManagementSystem.service;

import com.example.BankDocumentManagementSystem.dto.responce.DocumentDTOResp;
import com.example.BankDocumentManagementSystem.exception.custom_exception.DocumentFailedException;
import com.example.BankDocumentManagementSystem.persistence.entity.Document;
import com.example.BankDocumentManagementSystem.persistence.entity.User;
import com.example.BankDocumentManagementSystem.persistence.repository.DocumentRepo;
import com.example.BankDocumentManagementSystem.persistence.repository.UserRepo;
import com.example.BankDocumentManagementSystem.util.constant.Constant;
import com.example.BankDocumentManagementSystem.util.mapper.DocumentMapper;
import com.example.BankDocumentManagementSystem.util.records.DocumentParam;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class DocumentService extends BaseService<Document, DocumentRepo, Integer, DocumentDTOResp, DocumentMapper> {

    private String uploadPath = Constant.UPLOAD_FILE_PATH;
    private DocumentRepo documentRepo;
    private UserRepo userRepo;
    public DocumentService(DocumentRepo documentRepo, UserRepo userRepo){
        this.documentRepo = documentRepo;
        this.userRepo = userRepo;
    }


    public void delete(DocumentParam documentParam){
        String userName = documentParam.userName();
        String fileName = documentParam.fileName();

        //URL which file locate in
        String url = uploadPath + "/" + userName + "/" + fileName;
        Path path = Paths.get(url);

        if(!Files.exists(path))
            throw new DocumentFailedException("File not exist!!");

        try {
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
            throw new DocumentFailedException("Server Failed to delete");
        }
    }
    public Resource download(DocumentParam documentParam){
        String userName = documentParam.userName();
        String fileName = documentParam.fileName();
        //Get file from server
        Path filePath = Paths.get(uploadPath, userName, fileName);
        Resource resource = new FileSystemResource(filePath.toFile());

        //Check if file exist
        if(!resource.exists())
            throw new DocumentFailedException("File not exist!!");

        return resource;
    }
    public void upload(DocumentParam documentParam, InputStream inputStream){
        String userName = documentParam.userName();
        String fileName = documentParam.fileName();

        //URL which file locate in
        String url = uploadPath + "/" + userName + "/" + fileName;
        //Create folder for this user
        createNewFolder(userName);
        //Save document in this folder
        saveDocumentInServer(inputStream, url);
        //Save document name and url and user in database
        Optional<User> userOptional = userRepo.findByUserName(userName);
        if (!userOptional.isPresent())
            throw new DocumentFailedException("Server Failed to upload");
        User user = userOptional.get();
        saveDocumentInDatabase(fileName, user, url);
    }


    /**
     * helpful methods
     * @param folderName
     */
    private void createNewFolder(String folderName){
        File folder = new File(uploadPath + "/" + folderName);

        if (!folder.exists()) {
            boolean created = folder.mkdir();
            if (!created) {
                throw new DocumentFailedException("Server Failed to upload");
            }
        }
    }
    private void saveDocumentInServer(InputStream inputStream, String url){
        //Get the folder path in server
        Path outputPath = Path.of(url);

        // Using OutputStream to save the file
        try (OutputStream outputStream = new FileOutputStream(outputPath.toFile())) {
            // Copy the input stream to the output stream
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new DocumentFailedException("Server Failed to upload");
        }
    }
    private void saveDocumentInDatabase(String fileName, User user, String url){
        Document document = Document.builder()
                .url(url)
                .fileName(fileName)
                .user(user)
                .build();

        documentRepo.save(document);
    }
}
