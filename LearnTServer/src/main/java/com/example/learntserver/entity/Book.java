package com.example.learntserver.entity;

import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author liujd65
 * @date 2021/12/17 13:57
 **/
@Data
public class Book {
    String id;
    String bookName;
    String bookAuthor;

    public static Book getInstance(){
        Book book = new Book();
        book.setId(RandomStringUtils.randomAlphabetic(8));
        book.setBookName(RandomStringUtils.randomAlphabetic(8));
        book.setBookAuthor(RandomStringUtils.randomAlphabetic(8));
        return book;
    }
}
