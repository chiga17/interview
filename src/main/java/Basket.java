import jakarta.annotation.Nonnull;
import java.util.List;
import lombok.Getter;

@Getter
public class Basket {

    private final List<Purchase> purchases;

    public Basket(@Nonnull List<Purchase> purchases) {
        this.purchases = purchases;
    }
}
