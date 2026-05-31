package com.example.e_notes.Service;

import com.example.e_notes.Entity.BookInfo;
import com.example.e_notes.Entity.UserEntity;
import com.example.e_notes.Repository.LibRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Component
public class BookEntryService {

    @Autowired
    private LibRepo librepo;
    @Autowired
    private UserService userService;

    @Transactional
    public void saveNewBook(BookInfo bookInfo, String userName) {
        try
        {
            UserEntity user = userService.findByUserName(userName);
            BookInfo saved=  librepo.save(bookInfo);
            user.getBookInfoList().add(saved);
            userService.saveEntry(user);
        }catch(Exception e)
        {
            System.out.println( e.getMessage() );
            throw new RuntimeException("An error occured while saving new book");
        }
    }

    public void saveNewBook(BookInfo bookInfo) {
        librepo.save(bookInfo);
    }

    public List<BookInfo> showAllBooks() {
        return librepo.findAll();
    }


    public Optional<BookInfo> findById(ObjectId bookId) {
        return librepo.findById(bookId);
    }

    @Transactional
    public void deleteById(ObjectId bookId, String userName) {

        UserEntity user = userService.findByUserName(userName);
        boolean removed = user.getBookInfoList().removeIf(x -> x.getBookId().equals(bookId));
        if (removed)
        {
            userService.saveEntry(user);
            librepo.deleteById(bookId);
        }
    }

    @Transactional
    public Optional<BookInfo> updateById(
            ObjectId bookId,
            BookInfo newValues,
            String userName) {

        UserEntity user =
                userService.findByUserName(userName);

        boolean bookExists = user.getBookInfoList()
                .stream()
                .anyMatch(book ->
                        book.getBookId().equals(bookId));

        if (!bookExists) {
            return Optional.empty();
        }

        Optional<BookInfo> oldOptional =
                librepo.findById(bookId);

        if (oldOptional.isPresent()) {

            BookInfo old = oldOptional.get();

            old.setBookName(
                    newValues.getBookName() != null
                            && !newValues.getBookName().isEmpty()
                            ? newValues.getBookName()
                            : old.getBookName()
            );

            old.setBookPrice(
                    newValues.getBookPrice() != 0
                            ? newValues.getBookPrice()
                            : old.getBookPrice()
            );

            librepo.save(old);

            return Optional.of(old);
        }

        return Optional.empty();
    }
}