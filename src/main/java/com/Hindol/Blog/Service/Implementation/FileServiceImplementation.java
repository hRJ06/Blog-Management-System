package com.Hindol.Blog.Service.Implementation;

import com.Hindol.Blog.Service.FileService;
import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class FileServiceImplementation implements FileService {
    @Autowired
    private Cloudinary cloudinary;
    @Override
    public String uploadImage(MultipartFile file) {
        try {
            Map data = this.cloudinary.uploader().upload(file.getBytes(),Map.of());
            String uploadedLink = (String) data.get("secure_url");
            return uploadedLink;
        }
        catch (IOException e) {
            throw new RuntimeException("Image Uploading Failed");
        }
    }
}
