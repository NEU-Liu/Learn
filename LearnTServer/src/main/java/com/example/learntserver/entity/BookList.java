package com.example.learntserver.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import sun.swing.BakedArrayList;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liujd65
 * @date 2021/12/17 14:36
 **/
@Setter
@Getter
@AllArgsConstructor
public class BookList {
    List<Book> book;

    public BookList(){
        book = new ArrayList<>();
    }
}
