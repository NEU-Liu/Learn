package com.example.learntserver.controller;

import com.example.learntserver.entity.Book;
import com.example.learntserver.entity.BookList;
import org.checkerframework.checker.units.qual.A;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liujd65
 * @date 2021/12/17 13:52
 **/
@RestController
public class HelloController {

    @RequestMapping(value = "hi")
    public String hello() {
        return "Hello";
    }

    @RequestMapping(value = "arrayResponse", method = RequestMethod.GET)
    public List<Book> arrayResponse() {
        List<Book> list = new ArrayList<>();
        list.add(Book.getInstance());
        list.add(Book.getInstance());
        list.add(Book.getInstance());
        return list;
    }

    @RequestMapping(value = "bookListResponse", method = RequestMethod.GET)
    public BookList bookListResponse() {
        BookList bookList = new BookList();
        List<Book> list = new ArrayList<>();
        list.add(Book.getInstance());
        list.add(Book.getInstance());
        list.add(Book.getInstance());
        bookList.setBook(list);
        return bookList;
    }
}
