package ru.edu.library.entity;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity @Table(name="authors")
public class Author {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false, length=120) private String name;
 @OneToMany(mappedBy="author") private List<Book> books=new ArrayList<>();
 protected Author() {}
 public Author(String name){this.name=name;}
 public Long getId(){return id;} public String getName(){return name;} public void setName(String name){this.name=name;}
 public List<Book> getBooks(){return books;}
}
