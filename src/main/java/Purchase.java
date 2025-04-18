import lombok.Getter;
import lombok.Setter;

public class Purchase {

    private Long id;
    @Getter
    private Long price; // price in kopeiki
    @Setter @Getter
    private Long finalPrice;

    public Purchase(long id, long price) {
        this.id = id;
        this.price = price;
    }

}
