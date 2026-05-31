package com.example.e_notes.Controller;

import com.example.e_notes.Entity.BookInfo;
import com.example.e_notes.Entity.UserEntity;
import com.example.e_notes.Service.BookEntryService;
import com.example.e_notes.Service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookListController {


    @Autowired
    private BookEntryService bookEntryService;

    @Autowired
    private UserService userService;


    @GetMapping("/allBooks")
    public ResponseEntity<?> showAllBooks() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity user = userService.findByUserName(username);

        List<BookInfo> all = user.getBookInfoList();
        if (!all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }


    @PostMapping("/newBook")
    public ResponseEntity<BookInfo> newBook(@RequestBody BookInfo bookInfo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            bookInfo.setDate(LocalDateTime.now());
            bookEntryService.saveNewBook(bookInfo, username);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/findbyid/{id}")
    public ResponseEntity findById(@PathVariable("id") ObjectId id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity user = userService.findByUserName(username);

        Optional<BookInfo> bookInfo = bookEntryService.findById(id);
        if (bookInfo.isPresent() && user.getBookInfoList().contains(bookInfo.get())) {
            return new ResponseEntity<BookInfo>(bookInfo.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BookInfo> deleteBook(@PathVariable("id") ObjectId id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity user = userService.findByUserName(username);

        try {
            bookEntryService.deleteById(id, username);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateById(
            @RequestBody BookInfo newEntry,
            @PathVariable("id") ObjectId id) {

        try {

            Authentication authentication =
                    SecurityContextHolder.getContext()
                            .getAuthentication();

            String username = authentication.getName();

            Optional<BookInfo> updatedBook =
                    bookEntryService.updateById(
                            id,
                            newEntry,
                            username
                    );

            if (updatedBook.isPresent()) {

                return new ResponseEntity<>(
                        updatedBook.get(),
                        HttpStatus.OK
                );
            }

            return new ResponseEntity<>(
                    "Book Not Found",
                    HttpStatus.NOT_FOUND
            );

        } catch (Exception e) {

            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

}
