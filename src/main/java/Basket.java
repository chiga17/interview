import java.util.List;

public class Basket {

    private List<Purchase> purchases;

    public Basket(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }
}
