import jakarta.annotation.Nonnull;
import java.util.Objects;

public class LoyaltyService {

    private IDiscountService discountService;

    public LoyaltyService(IDiscountService discountService) {
        this.discountService = discountService;
    }

    private Integer getDiscount(long buyerId) {
        try {
            Integer discount = discountService.getDiscount(buyerId); // TODO: retries
            if (discount == null) {
                return 0;
            }
            if (discount < 0 || discount > 100) {
                throw new LoyaltyServiceException("Wrong discount value");
                // TODO: notify discount team about wrong discount value for buyerId
            }
            return discount;
        } catch (Exception e) {
            throw new LoyaltyServiceException("Cannot do at this time");
        }
    }

    public Long getDiscount(long initialPrice, int discount) {
        long finalPrice = Math.round(initialPrice*(100-discount)/100.);
        if (finalPrice == 0) {
            // TODO: notify ...
        }
        return finalPrice;
    }

    public Basket applyDiscounts(long buyerId, @Nonnull Basket basket) {
        Objects.requireNonNull(basket, "Basket cannot be null");
        int discount = getDiscount(buyerId);
        for (Purchase purchase : basket.getPurchases()) {
            purchase.setFinalPrice(getDiscount(purchase.getPrice(), discount));
        }
        return basket;
    }

}
