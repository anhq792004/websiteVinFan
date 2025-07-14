package com.example.datn.controller;

import com.example.datn.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequiredArgsConstructor
@RequestMapping("/uploads")
public class FileController {
    
    private final FileUploadService fileUploadService;
    
    /**
     * Serve file từ thư mục uploads bên ngoài
     * @param filename Tên file cần lấy
     * @return ResponseEntity chứa file
     */
    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            // Lấy đường dẫn file
            Path filePath = Paths.get(fileUploadService.getUploadDirectory(), filename);
            File file = filePath.toFile();
            
            // Kiểm tra file có tồn tại không
            if (!file.exists() || !file.isFile()) {
                return ResponseEntity.notFound().build();
            }
            
            // Tạo resource
            Resource resource = new FileSystemResource(file);
            
            // Xác định content type
            String contentType = determineContentType(filename);
            
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                    .body(resource);
                    
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Xác định content type dựa trên extension file
     */
    private String determineContentType(String filename) {
        String extension = filename.toLowerCase().substring(filename.lastIndexOf(".") + 1);
        
        switch (extension) {
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            case "gif":
                return "image/gif";
            case "bmp":
                return "image/bmp";
            case "webp":
                return "image/webp";
            default:
                return "application/octet-stream";
        }
    }
} 