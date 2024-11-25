package dao;
import autosalona.Sale;
import db.DbConnect;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SaleDao {
    public void sellCar(Sale sale) throws SQLException {
        String query = "INSERT INTO sales (car_id, customer_id) VALUES (?, ?)";
        String deleteCarQuery = "DELETE FROM cars WHERE id = ?";
        try (Connection connection = DbConnect.getConnection();
             PreparedStatement saleStatement = connection.prepareStatement(query);
             PreparedStatement deleteCarStatement = connection.prepareStatement(deleteCarQuery)) {
            saleStatement.setInt(1, sale.getCarId());
            saleStatement.setInt(2, sale.getCustomerId());
            saleStatement.executeUpdate();

            deleteCarStatement.setInt(1, sale.getCarId());
            deleteCarStatement.executeUpdate();
        }
    }

    public List<String> getSoldCars() throws SQLException {
        String query = "SELECT c.brand, c.model, cu.name FROM sales s " +
                "JOIN cars c ON s.car_id = c.id " +
                "JOIN customers cu ON s.customer_id = cu.id";


        List<String> sales = new ArrayList<>();
        try (Connection connection = DbConnect.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                sales.add(String.format("Марка: %s, Модель: %s, Покупатель: %s",
                        resultSet.getString("brand"),
                        resultSet.getString("model"),
                        resultSet.getString("name")));
            }
        }
        return sales;
    }
}
