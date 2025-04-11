
public class LoyalSystem {

    private IDiscountService discountService;

    public LoyalSystem(IDiscountService discountService) {
        this.discountService = discountService;
    }

    private Integer getDiscount(Long buyerId) {
        try {
            Integer discount = discountService.getDiscount(buyerId); // TODO: retries
            if (discount == null) {
                return 0;
            }
            if (discount < 0 || discount > 100) {
                throw new RuntimeException("Wrong discount value");
                // TODO: notify discount team about wrong discount value for buyerId
            }
            return discount;
        } catch (Exception e) {
            throw new RuntimeException("Cannot do at this time");
        }
    }

    public Long getDiscount(Long initialPrice, Integer discount) {
        Long finalPrice = Math.round(initialPrice*(100-discount)/100.);
        if (finalPrice == 0) {
            // TODO: notify ...
        }
        return finalPrice;
    }

    // buyerId and basket should not be null
    public Basket applyDiscounts(Long buyerId, Basket basket) {
        Integer discount = getDiscount(buyerId);
        for (Purchase purchase : basket.getPurchases()) {
            purchase.setFinalPrice(getDiscount(purchase.getPrice(), discount));
        }
        return basket;
    }

}
