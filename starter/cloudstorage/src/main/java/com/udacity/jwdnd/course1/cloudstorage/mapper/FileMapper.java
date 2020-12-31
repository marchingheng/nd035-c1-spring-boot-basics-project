package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("Select * from Files where userId=#{userId}")
    public List<File> getAllFiles(Integer userId);

    @Select("Select * from Files where fileName=#{fileName} AND userId=#{userId}")
    public File getFile(String fileName, Integer userId);

    @Insert("Insert into Files(fileId, filename, contentType, fileSize, userId, fileData) VALUES (#{fileId}, #{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    public int insertFile(File file);

    @Delete("Delete from Files where fileId= #{fileId}")
    public void deleteFile(Integer fileId);

    @Select("Select * from Files where fileId= #{fileId}")
    public File downloadFile(Integer fileId);
}
