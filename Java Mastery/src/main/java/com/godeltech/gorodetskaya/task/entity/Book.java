package com.godeltech.gorodetskaya.task.entity;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;

public class Book {

    public Book() {
    }

    private int id;

    @NotBlank(message = "Please, enter the title of the book")
    private String title;

    @Max(value = 2021, message = "Please, enter the right year of publication")
    private String yearOfPublication;

    private List<Author> authors;

    private String publisher;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(String yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }


    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id &&
                Objects.equals(title, book.title) &&
                Objects.equals(yearOfPublication, book.yearOfPublication) &&
                Objects.equals(publisher, book.publisher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, yearOfPublication, publisher);
    }
}