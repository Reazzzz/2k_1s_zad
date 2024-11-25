package autosalona;
public class Car {
    private int id;
    private String type;
    private String model;
    private String brand;

    public Car(String type, String model, String brand) {
        this.type = type;
        this.model = model;
        this.brand = brand;
    }

    public Car(int id, String type, String model, String brand) {
        this.id = id;
        this.type = type;
        this.model = model;
        this.brand = brand;
    }
    public int getId() {
        return id;
    }
    public String getType() {
        return type;
    }
    public String getModel() {
        return model;
    }
    public String getBrand() {
        return brand;
    }
}
