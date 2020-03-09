package materials;

public interface Item {
    int getFinalPrice();
    int getLevel();
    void setBasePrice(int basePrice);
    String getName();
    void setName(String name);
    String toString();
}
