<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <html>

        <head>
            <title>
                Car   
            </title>
        </head>

        <body>
            <center>
                <h1>Car Showroom Management</h1>
                <h2>
                    <a href="/new">Add New product</a>
                    &nbsp;&nbsp;&nbsp;
                    <a href="/list">List All products</a>
                </h2>
            </center>
            <div align="center">
                <table border="1" cellpadding="5">
                    <caption>
                        <h2>List of products</h2>
                    </caption>
                    <tr>
                        <th>ID</th>
                        <th>Brand</th>
                        <th>Model</th>
                        <th>Price</th>
                        <th>Actions</th>
                    </tr>
                    <c:forEach var="car" items="${crlist}">
                        <tr>
                            <td>
                                <c:out value="${car.carId}" />
                            </td>
                            <td>
                                <c:out value="${car.brand}" />
                            </td>
                            <td>
                                <c:out value="${car.carName}" />
                            </td>
                            <td>
                                <c:out value="${car.price}" />
                            </td>
                            <td>
                                <a href="/edit?carId=<c:out value='${car.carId}' />">Edit</a>
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                <a href="/delete?carId=<c:out value='${car.carId}' />">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </body>

        </html>