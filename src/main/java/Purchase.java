import java.util.Objects;
import jakarta.annotation.Nonnull;

public class Purchase {

    private Long id;
    private Long price; // price in kopeiki
    private Long finalPrice;

    public Purchase(Long id, @Nonnull Long price) {
        this.id = id;
        this.price = Objects.requireNonNull(price, "Price cannot be null");;
    }

    public void applyDiscount(Integer discount) {
        finalPrice = Math.round(price*(100-discount)/100.);
        if (finalPrice == 0) {
            // TODO: notify ...
        }
    }

    public long getFinalPrice() {
        return finalPrice;
    }
}
