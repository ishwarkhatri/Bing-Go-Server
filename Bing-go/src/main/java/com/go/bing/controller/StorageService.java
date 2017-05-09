package com.go.bing.controller;

import java.nio.file.Path;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.go.bing.model.FileMetaData;
import com.go.bing.model.User;

public interface StorageService {

    void init();

    String store(MultipartFile file, String description, User uploadedBy, String bucket);

    List<FileMetaData> loadAll(String bucket);

    Path load(String filename);

    Resource loadAsResource(String filename, String oid, String bucket);

    void deleteAll();

}
