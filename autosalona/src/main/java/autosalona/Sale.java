package autosalona;
public class Sale {
    private int carId;
    private int customerId;

    public Sale(int carId, int customerId) {
        this.carId = carId;
        this.customerId = customerId;
    }

    public int getCarId() {
        return carId;
    }

    public int getCustomerId() {
        return customerId;
    }
}
