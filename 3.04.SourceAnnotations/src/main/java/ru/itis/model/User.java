package ru.itis.model;

import ru.itis.annotations.FormInput;
import ru.itis.annotations.HtmlForm;

@HtmlForm(method = "post",action = "/user")
public class User {
    @FormInput(name = "name", placeholder = "name")
    private String name;

    @FormInput(name = "password",type = "password",placeholder = "password")
    private String password;

    public User() {
    }
}
