package com.go.bing.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.go.bing.model.FileMetaData;
import com.go.bing.model.GalleryPost;
import com.go.bing.model.ImageMetadata;
import com.go.bing.model.User;
import com.go.bing.repository.GalleryRepository;

@Controller
public class FileArchiveController {

	@Autowired
	private StorageService storageService;

	@Autowired
	private GalleryRepository galleryRepo;

	@ResponseBody
    @GetMapping("/getfiles")
    public List<FileMetaData> listUploadedFiles(Model model) throws IOException {
    	List<FileMetaData> files = storageService.loadAll("archive");
        return files;
    }

	@ResponseBody
    @GetMapping("/galleryposts")
    public List<GalleryPost> getGalleryPosts(Model model) throws IOException {
    	List<GalleryPost> posts = galleryRepo.findAll();
        return posts;
    }

	@ResponseBody
    @GetMapping("/files/{filename}/{oid}/{contentType1}/{contentType2}/{bucket}")
    public ResponseEntity<Resource> serveFile(@PathVariable("filename") String filename, @PathVariable("oid") String oid, @PathVariable("contentType1") String contentType1, 
    		@PathVariable("contentType2") String contentType2, @PathVariable("bucket") String bucket) {

        Resource file = storageService.loadAsResource(filename, oid, bucket);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\""+file.getFilename()+"\"")
                .header(HttpHeaders.CONTENT_TYPE, contentType1 + "/" + contentType2)
                .body(file);
    }

    @ResponseBody
    @RequestMapping(path="/uploadfile", method=RequestMethod.POST, produces="application/json")
    public List<FileMetaData> handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("desc") String description, @RequestParam("uploadedBy") User uploadedBy) {
        storageService.store(file, description, uploadedBy, "archive");
        List<FileMetaData> files = storageService.loadAll("archive");
        return files;
    }

    @ResponseBody
    @RequestMapping(path="/uploadPhotos", method=RequestMethod.POST, produces="application/json")
    public List<GalleryPost> handlePhotosUpload(@RequestParam("files") MultipartFile[] files, @RequestParam("userId") User user, @RequestParam("description") String description) {
    	GalleryPost post = new GalleryPost();
    	post.setDescription(description);
    	post.setUploader(user.getFirstName() + " " + user.getLastName());
    	post.setUploadDate(new Date());
    	
    	for(MultipartFile mpf : files) {
    		ImageMetadata imd = new ImageMetadata();
    		imd.setFileName(mpf.getOriginalFilename());
    		imd.setContentType(mpf.getContentType());
    		String oid = storageService.store(mpf, post.getDescription(), user, "gallery");
    		System.out.println(oid.toString());
    		imd.setOid(oid);
    		
    		post.getImages().add(imd);
    	}
    	
    	galleryRepo.save(post);
    	
        return galleryRepo.findAll();
    }
    
    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
