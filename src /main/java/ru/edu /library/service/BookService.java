package ru.edu.library.service;
import jakarta.ejb.Stateless;
import jakarta.ejb.EJB;
import ru.edu.library.data.BookRepository;
import ru.edu.library.entity.*;
import java.util.List;
@Stateless
public class BookService {
 @EJB private BookRepository repository;
 public List<Book> list(){return repository.findAll();}
 public List<Author> authors(){return repository.findAuthors();}
 public void save(Long id,String title,Integer year,Long authorId){
  if(title==null||title.isBlank())throw new IllegalArgumentException("Название книги обязательно.");
  Author author=repository.findAuthor(authorId);if(author==null)throw new IllegalArgumentException("Выберите существующего автора.");
  Book book=id==null?new Book(title.trim(),year,author):repository.find(id);
  if(book==null)throw new IllegalArgumentException("Книга не найдена.");
  book.setTitle(title.trim());book.setPublishedYear(year);book.setAuthor(author);
  if(id==null)repository.create(book);else repository.update(book);
 }
 public void delete(Long id){if(id==null)throw new IllegalArgumentException("Некорректный идентификатор.");repository.delete(id);}
}
