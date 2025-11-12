package com.example.apiconnectionapp;

// Ovdje smo uveli ovo SerializedName stuff da mozemo stvari iz JSON-a prebacivati U POJO (Plain Old Java Object)
import com.google.gson.annotations.SerializedName;

// Kreira Klasu Post (da bi mogli instancirati objekte te klase)
public class Post {

    // Kreira varijablu (private String title;) @SerializedName("title") kaze da u JSON-u koji ce da dobije nađe key "title" i spremi ga u varijablu ispod
    @SerializedName("title")
    private String title;


    @SerializedName("body")
    private String body;



    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    // Ovo sam dodao cisto da vidim kad logam objekat (bez ovog printa samo adresu)
    @Override
    public String toString() {
        return "Post{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
