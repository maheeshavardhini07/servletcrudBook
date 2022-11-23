package com.servlet.crud.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.servlet.crud.entity.Book;



@WebServlet("/book")
public class BookController extends HttpServlet {
    private Book book;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getServletPath();
        // System.out.println(action);

        try {
            switch (action) {
                case "/new":
                    showNewForm(req, resp);
                    break;
                case "/insert":
                System.out.println("Insert Method");
                    insertBook(req, resp);
                    break;
                case "/delete":
                    //deleteBook(req, resp);
                    break;
                case "/edit":
                   // showEditForm(req, resp);
                    break;
                case "/update":
                    //updateBook(req, resp);
                    break;
                default:
                    listBook(req, resp);
                    break;
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void listBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Book> listBook = new ArrayList<Book>();
        System.out.println("Book Controller");
        try{
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bookStore?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                    "root", "");

            java.sql.Statement stmt = conn.createStatement();
            String strSelect = "select * from book";
            ResultSet rset = stmt.executeQuery(strSelect);
            while (rset.next()) { // Repeatedly process each row
                int id = rset.getInt("book_id");
                String title = rset.getString("title"); // retrieve a 'String'-cell in the row
                String author = rset.getString("author"); // retrieve a 'double'-cell in the row
                Float price = rset.getFloat("price"); // retrieve a 'int'-cell in the row
                System.out.println(id + " ," + title + ", " + author + ", " + price);
                listBook.add(new Book(id, title, author, price));

            }
            // String s1="Maheesha";
            // req.setAttribute("s1", s1);
            req.setAttribute("listBook", listBook);
            RequestDispatcher dis = req.getRequestDispatcher("bookForm.jsp");
            dis.forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertBook(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
      try {
        Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bookStore?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                    "root", "");{
            String title = req.getParameter("title");
            String author = req.getParameter("author");
            float price = Float.parseFloat(req.getParameter("price"));
            book = new Book(title, author, price);
            String sql = "INSERT INTO book (title, author, price) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setFloat(3, book.getPrice());
            statement.executeUpdate();
            statement.close();
            conn.close();
            resp.sendRedirect("bookList");}
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    private void showNewForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher disp = req.getRequestDispatcher("bookList.jsp");
        disp.forward(req, resp);
    }
}

//     public void deleteBook(HttpServletRequest req, HttpServletResponse resp)
//             throws SQLException, IOException, ServletException {
//         try (Connection con = DriverManager.getConnection(
//                 "jdbc:mysql://127.0.0.1/bookStore?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
//                 "root", "")) {
//             int id = Integer.parseInt(req.getParameter("id"));
//             books = new Book(id);
//             String sql = "DELETE FROM book where book_id = ?";
//             PreparedStatement statement = con.prepareStatement(sql);
//             statement.setInt(1, books.getId());
//             statement.executeUpdate();
//             statement.close();
//             con.close();
//             resp.sendRedirect("/list");
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//     }

//     public void updateBook(HttpServletRequest req, HttpServletResponse resp)
//             throws SQLException, IOException, ServletException {
//         try (Connection con = DriverManager.getConnection(
//                 "jdbc:mysql://127.0.0.1/bookStore?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
//                 "root", "")) {
//             int id = Integer.parseInt(req.getParameter("id"));
//             String title = req.getParameter("title");
//             String author = req.getParameter("author");
//             float price = Float.parseFloat(req.getParameter("price"));
//             books = new Book(id, title, author, price);
//             String sql = "UPDATE book SET title = ?, author = ?, price = ?";
//             sql += " WHERE book_id = id";
//             PreparedStatement statement = con.prepareStatement(sql);
//             statement.setString(1, books.getTitle());
//             statement.setString(2, books.getAuthor());
//             statement.setFloat(3, books.getPrice());
//             statement.setInt(4, books.getId());
//             statement.executeUpdate();
//             statement.close();
//             con.close();
//             resp.sendRedirect("/list");
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//     }
//     public void showEditForm(HttpServletRequest request, HttpServletResponse response)
//             throws SQLException, ServletException, IOException {
//         try (Connection con = DriverManager.getConnection(
//                 "jdbc:mysql://127.0.0.1/bookstore?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
//                 "root", "")) {
//             int id = Integer.parseInt(request.getParameter("id"));
//             Book book = null;
//             String sql = "SELECT * FROM book WHERE book_id = ?";
//             PreparedStatement statement = con.prepareStatement(sql);
//             statement.setInt(1, id);
//             ResultSet resultSet = statement.executeQuery();
//             if (resultSet.next()) {
//                 String title = resultSet.getString("title");
//                 String author = resultSet.getString("author");
//                 float price = resultSet.getFloat("price");
//                 book = new Book(id, title, author, price);
//             }
//             resultSet.close();
//             statement.close();
//             con.close();
//             RequestDispatcher dispatcher = request.getRequestDispatcher("bookForm.jsp");
//             request.setAttribute("book", book);
//             dispatcher.forward(request, response);
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//     }
// }