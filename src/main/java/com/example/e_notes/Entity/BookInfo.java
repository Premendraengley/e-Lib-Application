package com.example.e_notes.Entity;


import com.mongodb.lang.NonNull;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data

@Document(collection = "Lib_Books")
public class BookInfo
{
    @Id
    private ObjectId bookId ;
    @NonNull
    private String bookName;
    @NonNull
    private int bookPrice;
    private LocalDateTime date;

//    public LocalDateTime getDate() {
//        return date;
//    }
//
//    public void setDate(LocalDateTime date) {
//        this.date = date;
//    }
//
//    private LocalDateTime date;
//
//    public ObjectId getBookId() {
//        return bookId;
//    }
//
//    public void setBookId(ObjectId bookId) {
//        this.bookId = bookId;
//    }
//
//    private String authorName ;
//
//    public String getBookName() {
//        return bookName;
//    }
//
//    public void setBookName(String bookName) {
//        this.bookName = bookName;
//    }
//
//    public int getBookPrice() {
//        return bookPrice;
//    }
//
//    public void setBookPrice(int bookPrice) {
//        this.bookPrice = bookPrice;
//    }
//
//    public String getAuthorName() {
//        return authorName;
//    }
//
//    public void setAuthorName(String authorName) {
//        this.authorName = authorName;
//    }
}
