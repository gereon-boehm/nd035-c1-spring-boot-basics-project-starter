package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE url = #{url}")
    Note select(String url);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userId) VALUES(#{url} #{username} #{key} #{password} #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int insert(Credential credential);

    @Delete("DELETE * CREDENTIALS WHERE credentialid = #{credentialId}")
    void delete(Integer credentialId);

    @Update("UPDATE CREDENTIALS SET (url, username, password) VALUES(#{url} #{username}, #{password}) WHERE credentialid = #{credentialId}")
    void update(Integer credentialId);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    List<Credential> getAllCredentials(Integer userId);
}
