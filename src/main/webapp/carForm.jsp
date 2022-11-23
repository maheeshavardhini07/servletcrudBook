<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Form</title>
</head>
<body style="color: rgb(134, 6, 66);">
    <center>
        <h1>Car Showroom Management</h1>
        <h2>
            <a href="/new">Add Car Details</a>
            &nbsp;&nbsp;&nbsp;
            <a href="/list">Car Details</a>
        </h2>
    </center>
    <div align="center">
        <c:if test="${car != null}">
            <form action="update" method="post">
        </c:if>
        <c:if test="${car == null}">
            <form action="insert" method="post">
        </c:if>
        <table border="1" cellpadding="5">
            <caption>
                <h2>
                    <c:if test="${car != null}">
                        Update 
                    </c:if>
                    <c:if test="${car == null}">
                        Add 
                    </c:if>
                </h2>
            </caption>
                <c:if test="${car != null}">
                    <input type="hidden" name="id" value="<c:out value='${car.carId}' />" />
                </c:if>           
            <tr>
                <th>Brand: </th>
                <td>
                    <input type="text" name="brand" size="45"
                            value="<c:out value='${car.brand}' />"
                        />
                </td>
            </tr>
            <tr>
                <th>Model: </th>
                <td>
                    <input type="text" name="carName" size="45"
                            value="<c:out value='${car.carName}' />"
                    />
                </td>
            </tr>
            <tr>
                <th>Price: </th>
                <td>
                    <input type="text" name="price" size="5"
                            value="<c:out value='${car.price}' />"
                    />
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="Save" />
                </td>
            </tr>
        </table>
        </form>
    </div>   
</body>
</html>