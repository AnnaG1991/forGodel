package com.godeltech.gorodetskaya.task.entity;

import com.godeltech.gorodetskaya.task.entity.enums.Gender;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Objects;


public class Author {

    private int id;

    @NotBlank(message = "Please, enter name of the author")
    private String name;

    @NotBlank(message = "Please, enter surname of the author")
    private String surname;

    @Pattern(regexp = "\\d{2}\\.\\d{2}\\.\\d{4}", message = "Please enter the date of birth as dd.mm.yyyy")
    private String dateOfBirth;

    private Gender gender;

    public Author() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(name, author.name) &&
                Objects.equals(surname, author.surname) &&
                Objects.equals(dateOfBirth, author.dateOfBirth) &&
                gender == author.gender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, dateOfBirth, gender);
    }
}