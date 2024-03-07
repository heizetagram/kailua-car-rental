package mysqlconnection.tables;

/**
 * @author Freddy
 */

public class CarModel {
    private int modelId;
    private String brand;
    private String model;

    public CarModel(int modelId, String brand, String model) {
        this.modelId = modelId;
        this.brand = brand;
        this.model = model;
    }

    public CarModel(String brand, String model) {
        this.brand = brand;
        this.model = model;
    }

    public int getModelId() {
        return modelId;
    }

    public String getModel() {
        return model;
    }

    public String getBrand() {
        return brand;
    }

}
