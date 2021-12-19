package com.example.learnsb;

import lombok.*;

/**
 * @author liujd65
 * @date 2021/12/17 14:19
 **/
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    String id;
    String bookName;
    String bookAuthor;
}
