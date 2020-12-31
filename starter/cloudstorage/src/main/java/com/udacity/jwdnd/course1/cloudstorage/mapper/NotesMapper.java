package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotesMapper {

    @Select("Select * from Notes where userId=#{userId}")
    List<Notes> getAllNotes(Integer userId);

    @Insert("Insert into Notes(userId, noteTitle, noteDescription) Values(#{userId}, #{noteTitle}, #{noteDescription})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insertNotes(Notes notes);

    @Update("Update Notes Set noteTitle=#{noteTitle}, noteDescription=#{noteDescription} where noteId=#{noteId} AND userId=#{userId}")
    int updateNote(Notes notes);

    @Delete("Delete from Notes where noteId=#{noteId}")
    int deleteNote(Integer noteId);
}
