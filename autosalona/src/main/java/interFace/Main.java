package interFace;
import autosalona.Car;
import autosalona.Customer;
import autosalona.Sale;
import dao.CarDao;
import dao.CustomerDao;
import dao.SaleDao;
import java.sql.SQLException;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CarDao carDao = new CarDao();
        CustomerDao customerDao = new CustomerDao();
        SaleDao saleDao = new SaleDao();
        while (true) {
            System.out.println("\nМеню:");
            System.out.println("1. Добавить автомобиль");
            System.out.println("2. Добавить покупателя");
            System.out.println("3. Добавить продажу");
            System.out.println("4. Вывести все продажи");
            System.out.println("5. Показать список автомобилей");
            System.out.println("6. Показать список покупателей");
            System.out.println("7. Очистить таблицу(не ворк)");
            System.out.println("8. Выход");
            System.out.print("Введите номер действия: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1:
                        //добавим тачечку
                        System.out.print("Введите тип автомобиля: ");
                        String type = scanner.nextLine();
                        System.out.print("Введите марку: ");
                        String brand = scanner.nextLine();
                        System.out.print("Введите модель: ");
                        String model = scanner.nextLine();

                        carDao.addCar(new Car(type, model, brand));
                        System.out.println("Автомобиль добавлен!");
                        break;

                    case 2:
                        //показ авто
                        for (Car car : carDao.getAllCars()) {
                            System.out.printf("ID: %d | Тип: %s | Марка: %s | Модель: %s\n",
                                    car.getId(), car.getType(), car.getBrand(), car.getModel());
                        }
                        break;

                    case 3:
                        //продаем бричку
                        System.out.print("Введите ID автомобиля: ");
                        int carId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Введите имя покупателя: ");
                        String name = scanner.nextLine();
                        System.out.print("Введите возраст покупателя: ");
                        int age = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Введите пол покупателя (М/Ж): ");
                        String gender = scanner.nextLine();

                        Customer customer = new Customer(name, age, gender);
                        customerDao.addCustomer(customer);

                        saleDao.sellCar(new Sale(carId, customer.getId()));
                        System.out.println("Автомобиль продан!");
                        break;

                    case 4:
                        //редактируем тачечку
                        System.out.print("Введите ID автомобиля: ");
                        int editCarId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Введите новый тип: ");
                        String newType = scanner.nextLine();
                        System.out.print("Введите новую марку: ");
                        String newBrand = scanner.nextLine();
                        System.out.print("Введите новую модель: ");
                        String newModel = scanner.nextLine();

                        carDao.updateCar(new Car(editCarId, newType, newModel, newBrand));
                        System.out.println("Информация об автомобиле обновлена!");
                        break;

                    case 5:
                        //переделка покупаетля
                        System.out.print("Введите ID покупателя: ");
                        int editCustomerId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Введите новое имя: ");
                        String newName = scanner.nextLine();
                        System.out.print("Введите новый возраст: ");
                        int newAge = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Введите новый пол: ");
                        String newGender = scanner.nextLine();

                        customerDao.updateCustomer(new Customer(editCustomerId, newName, newAge, newGender));
                        System.out.println("Информация о покупателе обновлена!");
                        break;

                    case 6:
                        //посмотреть выручку(продажи)
                        for (String sale : saleDao.getSoldCars()) {
                            System.out.println(sale);
                        }
                        break;

                    case 0:
                        System.out.println("Выход...");
                        return;
                    //ага, промахнулся? не бойся, я тебя не обижу
                    default:
                        System.out.println("Неверный выбор. Попробуйте снова.");
                }
            } catch (SQLException e) {
                System.err.println("Ошибка: " + e.getMessage());
            }
        }
    }
}
