package com.example.e_notes.Entity;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection="users")
public class UserEntity
{
    @Id
    private ObjectId id;
    @Indexed(unique = true)
    private String userName;

    private String userPassword;

    @DBRef
    private List<BookInfo> bookInfoList = new ArrayList<>();

    private List<String> roles;
}
