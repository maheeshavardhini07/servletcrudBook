<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <html>

        <head>
            <title>Books Store Application</title>
        </head>

        <body>
            <center>
                <h1>Book Store Details</h1>
                <form action="insert" method="post">
                    <input type="text" name="title" id="title" placeholder="Title">
                    <input type="text" name="author" id="author" placeholder="Author">
                    <input type="text" name="price" id="price" placeholder="Price">
                    <input type="submit" value="Submit">
                </form>
            </center>
        </body>