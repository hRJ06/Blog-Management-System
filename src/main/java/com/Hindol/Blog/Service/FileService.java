package com.Hindol.Blog.Service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface FileService {
    public String uploadImage(MultipartFile file);
}
