package com.example.learnsb;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class BookList {
    private List<Book> book;

    public BookList() { book = new ArrayList<>();
    }


}