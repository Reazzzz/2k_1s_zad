package dao;
import autosalona.Customer;
import db.DbConnect;
import java.sql.*;
public class CustomerDao {
    public void addCustomer(Customer customer) throws SQLException {
        String query = "INSERT INTO customers (name, age, gender) VALUES (?, ?, ?)";
        try (Connection connection = DbConnect.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, customer.getName());
            statement.setInt(2, customer.getAge());
            statement.setString(3, customer.getGender());
            statement.executeUpdate();


            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    customer.id = generatedKeys.getInt(1);
                }
            }
        }
    }

    public void updateCustomer(Customer customer) throws SQLException {
        String query = "UPDATE customers SET name = ?, age = ?, gender = ? WHERE id = ?";
        try (Connection connection = DbConnect.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, customer.getName());
            statement.setInt(2, customer.getAge());
            statement.setString(3, customer.getGender());
            statement.setInt(4, customer.getId());
            statement.executeUpdate();
        }
    }
}
