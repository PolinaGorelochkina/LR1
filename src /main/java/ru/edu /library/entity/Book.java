package ru.edu.library.entity;
import jakarta.persistence.*;
@Entity @Table(name="books")
public class Book {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false, length=200) private String title;
 @Column(name="published_year") private Integer publishedYear;
 @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="author_id", nullable=false) private Author author;
 protected Book() {}
 public Book(String title,Integer publishedYear,Author author){this.title=title;this.publishedYear=publishedYear;this.author=author;}
 public Long getId(){return id;} public String getTitle(){return title;} public void setTitle(String title){this.title=title;}
 public Integer getPublishedYear(){return publishedYear;} public void setPublishedYear(Integer year){this.publishedYear=year;}
 public Author getAuthor(){return author;} public void setAuthor(Author author){this.author=author;}
}
