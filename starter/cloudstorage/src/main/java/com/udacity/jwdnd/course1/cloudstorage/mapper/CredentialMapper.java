package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("Select * from Credentials where userId=#{userId}")
    List<Credential> getAllCredential(Integer userId);

    @Insert("Insert into Credentials(userId, url, username, password, key) Values(#{userId}, #{url}, #{username}, #{password}, #{key})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insertCredential(Credential credential);

    @Update("Update Credentials Set url=#{url}, username=#{username}, password=#{password}, key=#{key} where credentialId=#{credentialId}")
    int updateCredential(Credential credential);

    @Delete("Delete from Credentials where credentialId=#{credentialId}")
    int deleteCredential(Integer credentialId);
}
