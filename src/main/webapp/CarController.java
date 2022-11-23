package com.kgisl.demo1.Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
// import javax.servlet.ServletRequest;
// import javax.servlet.annotation.WebFilter;
// import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kgisl.demo1.Entity.Car;

// @WebFilter(urlPatterns = "/uppercase")
// public class EmptyParamFilter implements Filter {
//     @Override
//     public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
//       FilterChain filterChain) throws IOException, ServletException {
//         String inputString = servletRequest.getParameter("input");

//         if (inputString != null && inputString.matches("[A-Za-z0-9]+")) {
//             filterChain.doFilter(servletRequest, servletResponse);
//         } else {
//             servletResponse.getWriter().println("Missing input parameter");
//         }
//     }

//     // implementations for other methods
// }




// @WebServlet("/car")
public class CarController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //System.out.println("Car Controller doGet");
        String action = req.getServletPath();
        // System.out.println(action);

        try {
            switch (action) {
                case "/new":
                    showNewForm(req, resp);
                    break;
                case "/insert":
                    System.out.println("Insert Method");
                    insertCar(req, resp);
                    break;
                case "/delete":
                    deleteCar(req, resp);
                    break;
                case "/edit":
                    showEditForm(req, resp);
                    break;
                case "/update":
                    updateCar(req, resp);
                    break;
                default:
                    listCar(req, resp);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void listCar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       // System.out.println("List Car Called");
        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bikemanagementsystem?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                    "root", "");

            ArrayList<Car> crlist = new ArrayList<Car>();
            java.sql.Statement stmt = conn.createStatement();
            String strSelect = "select carId, brand, carName, price from car";
            ResultSet rset = stmt.executeQuery(strSelect);
            while (rset.next()) { // Repeatedly process each row
                int id = rset.getInt("carId");
                String brand = rset.getString("brand"); // retrieve a 'String'-cell in the row
                String carName = rset.getString("carName"); // retrieve a 'double'-cell in the row
                Float price = rset.getFloat("price"); // retrieve a 'int'-cell in the row
                // System.out.println(id + " ," + brand + ", " + carName + ", " + price);
                crlist.add(new Car(id, brand, carName, price));
            }
            // crlist.forEach(System.out::println);
            req.setAttribute("crlist", crlist);
            RequestDispatcher dis = req.getRequestDispatcher("carList.jsp");
            dis.forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void showNewForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("New Form Called");
        RequestDispatcher dis = req.getRequestDispatcher("carForm.jsp");
        dis.forward(req, resp);
    }

    private void insertCar(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("Insert Car Called");
        try {
            Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/bikemanagementsystem?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                        "root", "");{
                String brand = req.getParameter("brand");
                String carName = req.getParameter("carName");
                float price = Float.parseFloat(req.getParameter("price"));
                Car car = new Car(brand, carName, price);
                String sql = "INSERT INTO car (brand, carName, price) VALUES (?, ?, ?)";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, car.getBrand());
                statement.setString(2, car.getCarName());
                statement.setFloat(3, car.getPrice());
                statement.executeUpdate();
                statement.close();
                conn.close();
                resp.sendRedirect("/list");}
          } catch (Exception e) {
            e.printStackTrace();
          }
    }

    private void updateCar(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1/bikemanagementsystem?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "root", "")) {
            int id = Integer.parseInt(req.getParameter("carId"));
            String brand = req.getParameter("brand");
            String carName = req.getParameter("carName");
            float price = Float.parseFloat(req.getParameter("price"));
            Car cars = new Car(id, brand, carName, price);
            String sql = "UPDATE car SET brand = ?, carName = ?, price = ? WHERE carId = ?";
            // sql += " ";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, cars.getBrand());
            statement.setString(2, cars.getCarName());
            statement.setFloat(3, cars.getPrice());
            statement.setInt(4, cars.getCarId());
            statement.executeUpdate();
            statement.close();
            con.close();
            resp.sendRedirect("/list");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1/bikemanagementsystem?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "root", "")) {
            int id = Integer.parseInt(req.getParameter("carId"));
            Car cars = null;
            String sql = "SELECT * FROM car WHERE carId = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String brand = resultSet.getString("brand");
                String carName = resultSet.getString("carName");
                float price = resultSet.getFloat("price");
                cars = new Car(id, brand, carName, price);
            }
            resultSet.close();
            statement.close();
            con.close();
            RequestDispatcher dispatcher = req.getRequestDispatcher("carForm.jsp");
            req.setAttribute("cars", cars);
            dispatcher.forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteCar(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1/bikemanagementsystem?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "root", "")) {
            int carId = Integer.parseInt(req.getParameter("carId"));
            Car cars = new Car(carId, null, null, 0.0f);
            String sql = "DELETE FROM car where carId = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, cars.getCarId());
            statement.executeUpdate();
            statement.close();
            con.close();
            resp.sendRedirect("/list");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
