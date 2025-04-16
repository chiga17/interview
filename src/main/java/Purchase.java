import java.util.Objects;
import jakarta.annotation.Nonnull;
import lombok.Getter;
import lombok.Setter;

public class Purchase {

    private Long id;
    @Getter
    private Long price; // price in kopeiki
    @Setter @Getter
    private Long finalPrice;

    public Purchase(Long id, @Nonnull Long price) {
        this.id = id;
        this.price = Objects.requireNonNull(price, "Price cannot be null");;
    }

}
