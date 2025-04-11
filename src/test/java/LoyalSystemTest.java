
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doAnswer;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class LoyalSystemTest {

    @Mock
    DiscountManager discountManagerMock;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBasic(){
        doAnswer(i -> 10).when(discountManagerMock).getDiscount(anyLong());
        LoyalSystem loyalSystem = new LoyalSystem(discountManagerMock);
        List<Purchase> purchaseList = Arrays.asList(
            new Purchase(1L, 100L),
            new Purchase(2L, 200L)
        );
        Basket basket = new Basket(purchaseList);

        Long buyerId = 10L;
        Basket resultBasket = loyalSystem.applyDiscounts(buyerId, basket);

        assertEquals(90L, resultBasket.getPurchases().get(0).getFinalPrice());
        assertEquals(180L, resultBasket.getPurchases().get(1).getFinalPrice());
    }

    @Test
    public void testCannotGetDiscount() {
        Mockito.doThrow(new RuntimeException("...")).when(discountManagerMock).getDiscount(anyLong());
        LoyalSystem loyalSystem = new LoyalSystem(discountManagerMock);
        List<Purchase> purchaseList = Arrays.asList(
            new Purchase(1L, 100L),
            new Purchase(2L, 200L)
        );
        Basket basket = new Basket(purchaseList);
        Long buyerId = 10L;

        try {
            Basket resultBasket = loyalSystem.applyDiscounts(buyerId, basket);
            fail("...");
        } catch (Exception e) {
            assertTrue(e instanceof RuntimeException);
            e.getMessage().equals("Cannot do at this time");
        }
    }

    @Test
    public void testEmptyBasket() {

    }

    @Test
    public void testNullBasket() {

    }


    @Test
    public void testWrongUserId() {

    }

    @Test
    public void testNullBuyerId() {

    }

    @Test
    public void testNegativeDiscount() {
        doAnswer(i -> -1).when(discountManagerMock).getDiscount(anyLong());
        LoyalSystem loyalSystem = new LoyalSystem(discountManagerMock);
        List<Purchase> purchaseList = Arrays.asList(
            new Purchase(1L, 100L),
            new Purchase(2L, 200L)
        );
        Basket basket = new Basket(purchaseList);

        Long buyerId = 10L;
        try {
            Basket resultBasket = loyalSystem.applyDiscounts(buyerId, basket);
            fail("Expected exception ...");
        } catch (Exception e) {
            assertTrue(e instanceof RuntimeException);
            e.getMessage().equals("Wrong discount value");
        }
    }

    @Test
    public void testOverHundredDiscount() {

    }

    @Test
    public void testZeroDiscount() {

    }

    @Test
    public void testHundredDiscount() {

    }

    @Test
    public void testNullDiscount() {
        doAnswer(i -> null).when(discountManagerMock).getDiscount(anyLong());
        LoyalSystem loyalSystem = new LoyalSystem(discountManagerMock);
        List<Purchase> purchaseList = Arrays.asList(
            new Purchase(1L, 100L),
            new Purchase(2L, 200L)
        );
        Basket basket = new Basket(purchaseList);

        Long buyerId = 10L;
        Basket resultBasket = loyalSystem.applyDiscounts(buyerId, basket);

        assertEquals(100L, resultBasket.getPurchases().get(0).getFinalPrice());
        assertEquals(200L, resultBasket.getPurchases().get(1).getFinalPrice());
    }

    @ParameterizedTest
    @CsvSource(value = {
        "49, 1",
        "50, 1",
        "51, 0",
        "null, 1"
    }, nullValues = {"null"})
    public void testFinalPrice(Integer initialPrice, int finalPrice) {
        doAnswer(i -> initialPrice).when(discountManagerMock).getDiscount(anyLong());
        LoyalSystem loyalSystem = new LoyalSystem(discountManagerMock);
        List<Purchase> purchaseList = Arrays.asList(
            new Purchase(1L, 1L)
        );
        Basket basket = new Basket(purchaseList);

        Long buyerId = 10L;
        Basket resultBasket = loyalSystem.applyDiscounts(buyerId, basket);

        assertEquals(finalPrice, resultBasket.getPurchases().get(0).getFinalPrice());
    }

}