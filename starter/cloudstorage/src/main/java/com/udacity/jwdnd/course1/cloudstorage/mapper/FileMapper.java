package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE noteTitle = #{noteTitle}")
    File select(String filename);

    @Insert("INSERT INTO FILES (filename, noteDescription, userId) VALUES(#{noteTitle} #{noteDescription} #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insert(File file);

    @Delete("DELETE * FROM FILES WHERE fileid = #{fileId}")
    void delete(Integer fileId);

    @Update("UPDATE NOTE SET (fileTitle, noteDescription) VALUES(#{noteTitle} #{noteDescription}) WHERE noteid = #{noteId}")
    void update(Integer fileId);

    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    List<File> getFiles(Integer userId);
}
