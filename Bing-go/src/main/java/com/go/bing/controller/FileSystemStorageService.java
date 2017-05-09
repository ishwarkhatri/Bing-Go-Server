package com.go.bing.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.bson.types.ObjectId;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.go.bing.common.Utility;
import com.go.bing.model.FileMetaData;
import com.go.bing.model.User;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;
    private final DB mongoDB;
    private Utility utility = new Utility();

    public FileSystemStorageService() throws Exception {
    	File file = new File("upload-dir");
    	file.mkdir();
    	
    	this.rootLocation = Paths.get("upload-dir");
    	this.mongoDB = createDBConnection();
    }


	private DB createDBConnection() {
		String uname = utility.getPropertyValue("spring.data.mongodb.username");
		String database = utility.getPropertyValue("spring.data.mongodb.database");
		String pswd = utility.getPropertyValue("spring.data.mongodb.password");
		String host = utility.getPropertyValue("spring.data.mongodb.host");
		int port = Integer.parseInt(utility.getPropertyValue("spring.data.mongodb.port"));

		MongoCredential credential = MongoCredential.createCredential(uname, database, pswd.toCharArray());
		MongoClient mongoClient = new MongoClient(new ServerAddress(host, port), Arrays.asList(credential));

		return mongoClient.getDB(database);
	}

	@Override
    public String store(MultipartFile file, String description, User uploadedBy, String bucket) {
		String oid = "";
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }
            //Save file to local directory temporarily
            Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
            
            //Get locally saved file
            File fileHandle = this.rootLocation.resolve(file.getOriginalFilename()).toFile();
            
            //Save into monogdb
            try {
            	String contentType = file.getContentType();
            	oid = saveFileToMongoDB(fileHandle, description, uploadedBy, contentType, bucket);
            } catch(Exception e) {
            	throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
            }
            
            finally {
            	//Delete locally saved file
            	Files.deleteIfExists(this.rootLocation.resolve(file.getOriginalFilename()));
            }
            
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
        
        return oid;
    }

    private String saveFileToMongoDB(File fileToSave, String description, User uploadedBy, String contentType, String bucket) throws Exception {
    	
		String newFileName = fileToSave.getName();

		// create a namespace
		GridFS gfsPhoto = new GridFS(this.mongoDB, bucket);

		// get file from local drive
		GridFSInputFile gfsFile = gfsPhoto.createFile(fileToSave);

		// set a new filename and other properties
		gfsFile.setFilename(newFileName);
		gfsFile.put("description", description);
		gfsFile.put("uploadedBy", uploadedBy.getFirstName() + " " + uploadedBy.getLastName());
		gfsFile.setContentType(contentType);
		// save the file into mongoDB
		gfsFile.save();
		
		return gfsFile.getId().toString();
	}

	@Override
	public List<FileMetaData> loadAll(String bucket) {
		List<FileMetaData> files = new ArrayList<>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		try {
            // create a namespace
    		GridFS gfsArchive = new GridFS(this.mongoDB, bucket);
    		
        	// get all files
    		DBCursor cursor = gfsArchive.getFileList();
    		while(cursor.hasNext()) {
    			DBObject current = cursor.next();
    			
    			FileMetaData meta = new FileMetaData();
    			meta.setFileName(current.get("filename").toString());
    			meta.setDescription(current.get("description").toString());
    			meta.setObjectId(current.get("_id").toString());
    			meta.setUploader(current.get("uploadedBy").toString());
    			meta.setUploadDate((Date) current.get("uploadDate"));
    			meta.setUploadDateString(dateFormat.format(meta.getUploadDate()));
    			meta.setContentType(current.get("contentType").toString());

    			files.add(meta);
    		}
        } catch (Exception e) {
            throw new StorageException("Failed to read stored files", e);
        }

		return files;
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename, String objectId, String bucket) {
        try {
        	File file = new File(filename);
        	if(file.exists())
        		file.delete();
        	
        	// create a namespace
    		GridFS gfsArchive = new GridFS(this.mongoDB, bucket);
    		
        	// get file by it's filename
    		ObjectId oid = new ObjectId(objectId);
    		GridFSDBFile gridFile = gfsArchive.findOne(oid);

    		// save it into a new local file
    		gridFile.writeTo(file);

            Resource resource = new UrlResource(file.toURI());

            if(resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);

            }
        } catch (Exception e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
