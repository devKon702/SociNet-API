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

    public String upload(String folderName, MultipartFile file) throws IOException {
        // Tạo file name
        String fileName = System.currentTimeMillis() + "-" + file.getOriginalFilename();
        // Khởi tạo Blob
        BlobId blobId = BlobId.of("socinet-6cfdd.appspot.com", fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
        Storage storage = StorageOptions.getDefaultInstance().getService();
        // Lưu file
        Blob blob = storage.create(blobInfo, file.getBytes());
        return blob.getMediaLink();
    }
}