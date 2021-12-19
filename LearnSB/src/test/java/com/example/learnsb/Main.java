package com.example.learnsb;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author liujd65
 * @date 2021/12/17 11:11
 **/
public class Main {

    //https://www.baeldung.com/spring-rest-template-list

    @Test
    public void runRestTemplateArray() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/arrayResponse";
        ResponseEntity<Book[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, Book[].class);
        Book[] books = responseEntity.getBody();
        for (Book book : books) {
            System.out.println(book);
        }
    }

    @Test
    public void runRestTemplateList() {

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/bookListResponse";
        ResponseEntity<BookList> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, BookList.class);
        BookList bookList = responseEntity.getBody();
        for (Book book:bookList.getBook()){
             System.out.println(book);
        }

    }
}
