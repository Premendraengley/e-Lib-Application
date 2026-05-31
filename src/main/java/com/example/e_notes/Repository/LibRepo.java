package com.example.e_notes.Repository;

import com.example.e_notes.Entity.BookInfo;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LibRepo extends MongoRepository<BookInfo, ObjectId> {

}
