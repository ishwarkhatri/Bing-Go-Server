package com.go.bing.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import org.springframework.data.annotation.Id;

@Entity
public class GalleryPost implements Serializable {

	private static final long serialVersionUID = 1L;
	private @Id @GeneratedValue String id;
	private String description;
	private String uploader;
	private String uploadDateString;
	private Date uploadDate;
	private List<ImageMetadata> images = new ArrayList<>();
	
	public String getFirstImageUrl() {
		ImageMetadata i = images.get(0);
		return "files/" + i.getFileName() + "/" + i.getOid() + "/" + i.getContentType() + "/gallery";
	}
	public List<String> getOtherImageUrls() {
		List<String> urls = new ArrayList<>();
		if(!images.isEmpty() && images.size() > 1) {
			for(int i = 1; i < images.size(); i++) {
				ImageMetadata imd = images.get(i);
				urls.add("files/" + imd.getFileName() + "/" + imd.getOid() + "/" + imd.getContentType() + "/gallery");
			}
		}
		
		return urls;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUploader() {
		return uploader;
	}
	public void setUploader(String uploader) {
		this.uploader = uploader;
	}
	public String getUploadDateString() {
		return uploadDateString;
	}
	public void setUploadDateString(String uploadDateString) {
		this.uploadDateString = uploadDateString;
	}
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	public List<ImageMetadata> getImages() {
		return images;
	}
	public void setImages(List<ImageMetadata> images) {
		this.images = images;
	}
	
}
