package dao;
import autosalona.Car;
import db.DbConnect;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDao {
    public void addCar(Car car) throws SQLException {
        String query = "INSERT INTO cars (type, model, brand) VALUES (?, ?, ?)";
        try (Connection connection = DbConnect.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, car.getType());
            statement.setString(2, car.getModel());
            statement.setString(3, car.getBrand());
            statement.executeUpdate();
        }
    }

    public List<Car> getAllCars() throws SQLException {
        List<Car> cars = new ArrayList<>();
        String query = "SELECT * FROM cars";
        try (Connection connection = DbConnect.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                cars.add(new Car(
                        resultSet.getInt("id"),
                        resultSet.getString("type"),
                        resultSet.getString("model"),
                        resultSet.getString("brand")));
            }
        }
        return cars;
    }


    public void updateCar(Car car) throws SQLException {
        String query = "UPDATE cars SET type = ?, model = ?, brand = ? WHERE id = ?";
        try (Connection connection = DbConnect.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, car.getType());
            statement.setString(2, car.getModel());
            statement.setString(3, car.getBrand());
            statement.setInt(4, car.getId());
            statement.executeUpdate();
        }
    }
}
