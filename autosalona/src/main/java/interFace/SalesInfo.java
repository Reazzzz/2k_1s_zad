package interFace;
import java.sql.*;
import java.util.Scanner;
public class SalesInfo {

    private static final String URL = "jdbc:mysql://localhost:3306/autosalon?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "123456789";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\nМеню:");
                System.out.println("1. Добавить автомобиль");
                System.out.println("2. Добавить покупателя");
                System.out.println("3. Добавить продажу");
                System.out.println("4. Вывести все продажи");
                System.out.println("5. Показать список автомобилей");
                System.out.println("6. Показать список покупателей");
                System.out.println("7. Очистить таблицу");
                System.out.println("8. Выход");
                System.out.print("Введите номер действия: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        addCar(conn, scanner);
                        break;
                    case 2:
                        addCustomer(conn, scanner);
                        break;
                    case 3:
                        addSale(conn, scanner);
                        break;
                    case 4:
                        displaySales(conn);
                        break;
                    case 5:
                        displayCars(conn);
                        break;
                    case 6:
                        displayCustomers(conn);
                        break;
                    case 7:
                        clearTable(conn, scanner);
                        break;
                    case 8:
                        System.out.println("Выход из программы.");
                        return;
                    default:
                        System.out.println("Некорректный выбор. Пожалуйста, выберите действие снова.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void displayCars(Connection conn) throws SQLException {
        String query = "SELECT id, type, model, brand FROM cars";

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("+----+-----------------+--------------------+-----------------+");
            System.out.println("| ID | Тип автомобиля  | Модель автомобиля  | Марка автомобиля |");
            System.out.println("+----+-----------------+--------------------+-----------------+");

            while (rs.next()) {
                int carId = rs.getInt("id");
                String carType = rs.getString("type");
                String carModel = rs.getString("model");
                String carBrand = rs.getString("brand");


                System.out.printf("| %-2d | %-15s | %-18s | %-15s |\n", carId, carType, carModel, carBrand);
            }

            System.out.println("+----+-----------------+--------------------+-----------------+");
        }
    }


    private static void addCar(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Введите тип автомобиля (легковой/грузовой): ");
        String type = scanner.nextLine();

        System.out.print("Введите модель автомобиля: ");
        String model = scanner.nextLine();

        System.out.print("Введите марку автомобиля: ");
        String brand = scanner.nextLine();

        String query = "INSERT INTO cars (type, model, brand) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, type);
            stmt.setString(2, model);
            stmt.setString(3, brand);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Автомобиль успешно добавлен!");
            }
        }
    }


    private static void addCustomer(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Введите имя покупателя: ");
        String name = scanner.nextLine();

        System.out.print("Введите возраст покупателя: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Введите пол покупателя (M/F): ");
        String gender = scanner.nextLine();

        String query = "INSERT INTO customers (name, age, gender) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setInt(2, age);
            stmt.setString(3, gender);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Покупатель успешно добавлен!");
            }
        }
    }


    private static void addSale(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Введите ID автомобиля для продажи: ");
        int carId = scanner.nextInt();

        System.out.print("Введите ID покупателя: ");
        int customerId = scanner.nextInt();
        scanner.nextLine();


        String checkCarQuery = "SELECT id FROM cars WHERE id = ?";
        try (PreparedStatement checkCarStmt = conn.prepareStatement(checkCarQuery)) {
            checkCarStmt.setInt(1, carId);
            ResultSet carResult = checkCarStmt.executeQuery();
            if (!carResult.next()) {
                System.out.println("Ошибка: автомобиль с таким ID не найден!");
                return;
            }
        }


        String checkCustomerQuery = "SELECT id FROM customers WHERE id = ?";
        try (PreparedStatement checkCustomerStmt = conn.prepareStatement(checkCustomerQuery)) {
            checkCustomerStmt.setInt(1, customerId);
            ResultSet customerResult = checkCustomerStmt.executeQuery();
            if (!customerResult.next()) {
                System.out.println("Ошибка: покупатель с таким ID не найден!");
                return;
            }
        }


        String query = "INSERT INTO sales (car_id, customer_id) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, carId);
            stmt.setInt(2, customerId);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Продажа успешно добавлена!");
            } else {
                System.out.println("Не удалось добавить продажу.");
            }
        }
    }


    private static void displaySales(Connection conn) throws SQLException {
        String query = "SELECT s.id AS sale_id, c.model AS car_model, c.brand AS car_brand, " +
                "cu.name AS customer_name, cu.age AS customer_age, cu.gender AS customer_gender " +
                "FROM sales s " +
                "JOIN cars c ON s.car_id = c.id " +
                "JOIN customers cu ON s.customer_id = cu.id";

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("+------------+--------------------+-----------------+----------------------+--------+-------+");
            System.out.println("| ID продажи | Модель автомобиля  | Марка автомобиля| Имя покупателя        | Возраст| Пол   |");
            System.out.println("+------------+--------------------+-----------------+----------------------+--------+-------+");

            while (rs.next()) {
                int saleId = rs.getInt("sale_id");
                String carModel = rs.getString("car_model");
                String carBrand = rs.getString("car_brand");
                String customerName = rs.getString("customer_name");
                int customerAge = rs.getInt("customer_age");
                String customerGender = rs.getString("customer_gender");

                // Вывод информации о продаже
                System.out.printf("| %-10d | %-18s | %-15s | %-20s | %-6d | %-6s |\n", saleId, carModel, carBrand, customerName, customerAge, customerGender);
            }

            System.out.println("+------------+--------------------+-----------------+----------------------+--------+-------+");
        }
    }

    
    private static void displayCustomers(Connection conn) throws SQLException {
        String query = "SELECT id, name, age, gender FROM customers";

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("+----+----------------------+--------+-------+");
            System.out.println("| ID | Имя покупателя       | Возраст| Пол   |");
            System.out.println("+----+----------------------+--------+-------+");

            while (rs.next()) {
                int customerId = rs.getInt("id");
                String customerName = rs.getString("name");
                int customerAge = rs.getInt("age");
                String customerGender = rs.getString("gender");


                System.out.printf("| %-2d | %-20s | %-6d | %-6s |\n", customerId, customerName, customerAge, customerGender);
            }

            System.out.println("+----+----------------------+--------+-------+");
        }
    }
    private static void clearTable(Connection conn, Scanner scanner) throws SQLException {
        System.out.println("Доступные таблицы: cars, customers, sales");
        System.out.print("Введите название таблицы для очистки: ");
        String tableName = scanner.nextLine().toLowerCase();

        if (!tableName.equals("cars") && !tableName.equals("customers") && !tableName.equals("sales")) {
            System.out.println("Ошибка: неизвестное название таблицы!");
            return;
        }

        System.out.print("Вы уверены, что хотите очистить таблицу '" + tableName + "'? (y/n): ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("y")) {
            String query = "DELETE FROM " + tableName;
            try (Statement stmt = conn.createStatement()) {
                int rowsDeleted = stmt.executeUpdate(query);
                System.out.println("Таблица '" + tableName + "' успешно очищена. Удалено строк: " + rowsDeleted);
            }
        } else {
            System.out.println("Очистка таблицы отменена.");
        }
    }
}
