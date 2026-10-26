package ru.edu.library.data;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import ru.edu.library.entity.*;
import java.util.List;
@Stateless
public class BookRepository {
 @PersistenceContext(unitName="libraryPU") private EntityManager em;
 public List<Book> findAll(){return em.createQuery("select b from Book b join fetch b.author order by b.id",Book.class).getResultList();}
 public List<Author> findAuthors(){return em.createQuery("select a from Author a order by a.name",Author.class).getResultList();}
 public Book find(Long id){return em.createQuery("select b from Book b join fetch b.author where b.id=:id",Book.class).setParameter("id",id).getResultStream().findFirst().orElse(null);}
 public void create(Book book){em.persist(book);}
 public void update(Book book){em.merge(book);}
 public void delete(Long id){Book b=em.find(Book.class,id);if(b!=null)em.remove(b);}
 public Author findAuthor(Long id){return em.find(Author.class,id);}
}
