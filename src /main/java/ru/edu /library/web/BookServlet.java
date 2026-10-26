package ru.edu.library.web;
import jakarta.ejb.EJB;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import ru.edu.library.entity.*;
import ru.edu.library.service.BookService;
@WebServlet("/books")
public class BookServlet extends HttpServlet {
 @EJB private BookService service;
 protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws IOException{
  resp.setContentType("text/html;charset=UTF-8");resp.setCharacterEncoding("UTF-8");
  try(PrintWriter out=resp.getWriter()){
   out.println("<!doctype html><html lang='ru'><head><meta charset='UTF-8'><title>Каталог книг</title><style>body{font:16px Arial;max-width:1000px;margin:32px auto;padding:0 16px}table{border-collapse:collapse;width:100%;margin:20px 0}td,th{border:1px solid #bbb;padding:8px}form.inline{display:inline}input,select,button{padding:7px;margin:3px}</style></head><body><h1>Каталог книг</h1>");
   String msg=req.getParameter("message");if(msg!=null)out.println("<p><b>"+esc(msg)+"</b></p>");
   out.println("<h2>Добавить книгу</h2><form method='post' action='books'><input type='hidden' name='action' value='save'><label>Название <input name='title' required maxlength='200'></label><label>Год <input name='year' type='number' min='0' max='2100'></label><label>Автор <select name='authorId' required>");
   for(Author a:service.authors())out.println("<option value='"+a.getId()+"'>"+esc(a.getName())+"</option>");
   out.println("</select></label><button>Создать</button></form><h2>Книги</h2><table><tr><th>ID</th><th>Название</th><th>Год</th><th>Автор</th><th>Действия</th></tr>");
   for(Book b:service.list()){
    out.println("<tr><td>"+b.getId()+"</td><td>"+esc(b.getTitle())+"</td><td>"+(b.getPublishedYear()==null?"—":b.getPublishedYear())+"</td><td>"+esc(b.getAuthor().getName())+"</td><td>");
    out.println("<form class='inline' method='post'><input type='hidden' name='action' value='save'><input type='hidden' name='id' value='"+b.getId()+"'><input name='title' value='"+esc(b.getTitle())+"' required><input name='year' type='number' value='"+(b.getPublishedYear()==null?"":b.getPublishedYear())+"'><select name='authorId'>");
    for(Author a:service.authors())out.println("<option value='"+a.getId()+"' "+(a.getId().equals(b.getAuthor().getId())?"selected":"")+">"+esc(a.getName())+"</option>");
    out.println("</select><button>Сохранить</button></form><form class='inline' method='post' onsubmit=\"return confirm('Удалить книгу?')\"><input type='hidden' name='action' value='delete'><input type='hidden' name='id' value='"+b.getId()+"'><button>Удалить</button></form></td></tr>");
   }
   out.println("</table></body></html>");
  }catch(Exception e){resp.sendError(500,"Ошибка при чтении каталога: "+e.getMessage());}
 }
 protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws IOException{
  req.setCharacterEncoding(StandardCharsets.UTF_8.name());
  try{String action=req.getParameter("action");Long id=parseLong(req.getParameter("id"));
   if("delete".equals(action))service.delete(id);else if("save".equals(action)){String ys=req.getParameter("year");Integer year=ys==null||ys.isBlank()?null:Integer.valueOf(ys);service.save(id,req.getParameter("title"),year,parseLong(req.getParameter("authorId")));}else throw new IllegalArgumentException("Неизвестное действие.");
   resp.sendRedirect("books?message="+java.net.URLEncoder.encode("Операция выполнена",StandardCharsets.UTF_8));
  }catch(Exception e){resp.sendError(400,"Не удалось выполнить операцию: "+e.getMessage());}
 }
 private static Long parseLong(String s){return s==null||s.isBlank()?null:Long.valueOf(s);}
 private static String esc(String s){if(s==null)return "";return s.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;").replace("\"","&quot;").replace("'","&#39;");}
}
