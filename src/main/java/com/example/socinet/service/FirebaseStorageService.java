package com.example.socinet.service;

import com.google.cloud.storage.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.StorageClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FirebaseStorageService {
    private final FirebaseApp firebaseApp;

    public String upload(String folderName, MultipartFile file) throws IOException {
        String linkStorage = "socinet-6cfdd.appspot.com";
        Storage storage = StorageClient.getInstance(firebaseApp).bucket().getStorage();
        String fileName =  System.currentTimeMillis() + "-" + file.getOriginalFilename();
        Bucket bucket = storage.get(linkStorage);
        Blob blob = bucket.create(folderName + "/" + fileName, file.getInputStream(), file.getContentType());
        return blob.getMediaLink();
    }
}