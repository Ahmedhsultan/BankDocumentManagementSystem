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
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.Set;

@Service
public class DocumentService extends BaseService<DocumentRepo, Integer, DocumentDTOResp, DocumentMapper> {

    private String uploadPath = Constant.UPLOAD_FILE_PATH;
    private DocumentRepo documentRepo;
    private UserRepo userRepo;
    private MessageDigest messageDigest;
    private Class<?> fileSystemClass;
    public DocumentService(DocumentRepo documentRepo, UserRepo userRepo){
        this.documentRepo = documentRepo;
        this.userRepo = userRepo;
        try {
            this.messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        fileSystemClass = FileSystemResource.class;
    }

    @Transactional
    public void update(DocumentParam documentParam, InputStream inputStream){
        delete(documentParam);
        upload(documentParam, inputStream);
    }
    public void delete(DocumentParam documentParam){
        //Find hash from db
        String fileHash = getFileHash(documentParam);

        //URL which file locate in
        Path path = Paths.get(uploadPath, fileHash);
        if(!Files.exists(path))
            throw new DocumentFailedException("File not exist!!");

        //Delete document from table of documents
        User user = getUser(documentParam.userName());
        deleteDocumentFromTable(user, fileHash);

        //Check if no user use this file delete it from server
        if(!isFileUsedFromAnotherUser(fileHash)){
            try {
                Files.delete(path);
            } catch (IOException e) {
                e.printStackTrace();
                throw new DocumentFailedException("Server Failed to delete");
            }
        }
    }
    public Resource download(DocumentParam documentParam){
        //Find hash from db
        String fileHash = getFileHash(documentParam);
        //Get file from server
        Path filePath = Paths.get(uploadPath, fileHash);
        Resource resource = getFileSystemResource(filePath.toFile());
        //Check if file exist
        if(!resource.exists())
            throw new DocumentFailedException("File not exist!!");

        return resource;
    }
    public void upload(DocumentParam documentParam, InputStream inputStream){
        String userName = documentParam.userName();
        String fileName = documentParam.fileName();

        //Hash file to make it unique file per server
        String fileHash;
        try {
            fileHash = hashFile(inputStream.readAllBytes());
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new DocumentFailedException("Server Failed to upload!!");
        }
        //URL which file locate in
        String url = uploadPath + "/" + fileHash;
        //Save document in this folder
        if (!isFileSaved(url))
            saveDocumentInServer(inputStream, url);
        //Save document name and url and user in database
        User user = getUser(userName);
        saveDocumentInDatabase(fileHash, fileName, user, url);
    }


    /**
     * helpful methods
     */
    private void deleteDocumentFromTable(User user, String fileHash){
        Document document = user.getDocuments().stream()
                .filter(x -> x.getFileHash().equals(fileHash))
                .findFirst().get();

        documentRepo.delete(document);
    }
    private boolean isFileUsedFromAnotherUser(String fileHash){
        long count = documentRepo.findDocumentByFileHash(fileHash).stream().count();
        if (count>0)
            return true;
        return false;
    }
    private String getFileHash(DocumentParam documentParam){
        User user = getUser(documentParam.userName());
        Set<Document> documents = user.getDocuments();
        Optional<Document> optionalDocument = documents.stream()
                .filter(x -> x.getOriginalFileName().equals(documentParam.fileName()))
                .findFirst();

        if (!optionalDocument.isPresent())
            throw new DocumentFailedException("Document not exist!!");

        return optionalDocument.get().getFileHash();
    }
    private User getUser(String userName){
        Optional<User> userOptional = userRepo.findByUserName(userName);
        if (!userOptional.isPresent())
            throw new DocumentFailedException("Unavailable user!!");

        return userOptional.get();
    }
    private String hashFile(byte[] fileBytes) throws NoSuchAlgorithmException {
        byte[] hash = getMessageDigest().digest(fileBytes);
        String checksum = new BigInteger(1, hash).toString(16);

        return checksum;
    }
    private boolean isFileSaved(String url){
        Path filePath = Paths.get(url);
        if(Files.exists(filePath))
            return true;

        return false;
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
    private void saveDocumentInDatabase(String fileHash, String fileName, User user, String url){
        Document document = Document.builder()
                .url(url)
                .fileHash(fileHash)
                .originalFileName(fileName)
                .build();

//        Document document1 = documentRepo.save(document);
        user.getDocuments().add(document);
        userRepo.save(user);
//        documentRepo.save(document);
    }
    private MessageDigest getMessageDigest(){
        return messageDigest;
    }
    public void setMessageDigest(MessageDigest messageDigest){
        this.messageDigest = messageDigest;
    }
    public Resource getFileSystemResource(File file){
        try {
            return (Resource) fileSystemClass.getConstructor(File.class).newInstance(file);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    public void setFileSystemResourceClass(Class<?> fileSystemClass){
        this.fileSystemClass = fileSystemClass;
    }
}
