package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public int insertFile(File file){
        return fileMapper.insertFile(file);
    }

    public List<File> getFiles(Integer userId){
        return fileMapper.getAllFiles(userId);
    }

    public File getFile(String fileName, Integer userId){
        return fileMapper.getFile(fileName, userId);
    }

    public void deleteFile(Integer fileId){
        fileMapper.deleteFile(fileId);
    }

    public File downloadFile(Integer fileId){
        return fileMapper.downloadFile(fileId);
    }
}
