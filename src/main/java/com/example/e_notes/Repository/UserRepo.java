package com.example.e_notes.Repository;

import com.example.e_notes.Entity.UserEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<UserEntity, ObjectId> {

    public UserEntity findByuserName(String userName);
}
