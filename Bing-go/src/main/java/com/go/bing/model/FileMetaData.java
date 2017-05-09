package com.go.bing.model;

import java.util.Date;

public class FileMetaData {

	private String objectId;
	private String fileName;
	private String description;
	private String uploader;
	private String uploadDateString;
	private Date uploadDate;
	private String contentType;

	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
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
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
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
	
}
