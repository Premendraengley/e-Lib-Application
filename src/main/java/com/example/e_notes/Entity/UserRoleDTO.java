package com.example.e_notes.Entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection="users")
public class UserRoleDTO
{
    @Id
    private ObjectId id;
    @Indexed(unique = true)
    private String userName;
    private List<String> roles;
}