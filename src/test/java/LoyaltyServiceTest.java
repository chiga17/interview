
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class LoyaltyServiceTest {

    @Mock
    IDiscountService discountServiceMock;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBasic(){
        doAnswer(i -> 10).when(discountServiceMock).getDiscount(anyLong());
        LoyaltyService loyaltyService = new LoyaltyService(discountServiceMock);
        List<Purchase> purchaseList = Arrays.asList(
            new Purchase(1L, 100L),
            new Purchase(2L, 200L)
        );
        Basket basket = new Basket(purchaseList);

        long buyerId = 10L;
        Basket resultBasket = loyaltyService.applyDiscounts(buyerId, basket);

        verify(discountServiceMock, times(1)).getDiscount(buyerId);

        assertEquals(90L, resultBasket.getPurchases().get(0).getFinalPrice());
        assertEquals(180L, resultBasket.getPurchases().get(1).getFinalPrice());
    }

    @Test
    public void testCannotGetDiscount() {
        Mockito.doThrow(new RuntimeException("...")).when(discountServiceMock).getDiscount(anyLong());
        LoyaltyService loyaltyService = new LoyaltyService(discountServiceMock);
        List<Purchase> purchaseList = Arrays.asList(
            new Purchase(1L, 100L),
            new Purchase(2L, 200L)
        );
        Basket basket = new Basket(purchaseList);
        long buyerId = 10L;

        try {
            Basket resultBasket = loyaltyService.applyDiscounts(buyerId, basket);
            fail("...");
        } catch (Exception e) {
            verify(discountServiceMock, times(1)).getDiscount(buyerId);
            assertInstanceOf(LoyaltyServiceException.class, e);
            assertEquals("Cannot do at this time", e.getMessage());
        }
    }

    @Test
    public void testEmptyBasket() {

    }

    @Test
    public void testNullBasket() {
        LoyaltyService loyaltyService = new LoyaltyService(discountServiceMock);
        long buyerId = 10L;
        try {
            Basket resultBasket = loyaltyService.applyDiscounts(buyerId, null);
            fail("Expected exception ...");
        } catch (Exception e) {
            assertInstanceOf(NullPointerException.class, e);
        }

    }


    @Test
    public void testWrongUserId() {

    }

    @Test
    public void testNullBuyerId() {

    }

    @Test
    public void testNegativeDiscount() {
        doAnswer(i -> -1).when(discountServiceMock).getDiscount(anyLong());
        LoyaltyService loyaltyService = new LoyaltyService(discountServiceMock);
        List<Purchase> purchaseList = Arrays.asList(
            new Purchase(1L, 100L),
            new Purchase(2L, 200L)
        );
        Basket basket = new Basket(purchaseList);

        long buyerId = 10L;
        try {
            Basket resultBasket = loyaltyService.applyDiscounts(buyerId, basket);
            fail("Expected exception ...");
        } catch (Exception e) {
            verify(discountServiceMock, times(1)).getDiscount(buyerId);
            assertInstanceOf(LoyaltyServiceException.class, e);
            assertEquals("Cannot do at this time", e.getMessage());
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
        doAnswer(i -> null).when(discountServiceMock).getDiscount(anyLong());
        LoyaltyService loyaltyService = new LoyaltyService(discountServiceMock);
        List<Purchase> purchaseList = Arrays.asList(
            new Purchase(1L, 100L),
            new Purchase(2L, 200L)
        );
        Basket basket = new Basket(purchaseList);

        long buyerId = 10L;
        Basket resultBasket = loyaltyService.applyDiscounts(buyerId, basket);

        assertEquals(100L, resultBasket.getPurchases().get(0).getFinalPrice());
        assertEquals(200L, resultBasket.getPurchases().get(1).getFinalPrice());
        verify(discountServiceMock, times(1)).getDiscount(buyerId);
    }

    @ParameterizedTest
    @CsvSource(value = {
        "49, 1",
        "50, 1",
        "51, 0",
        "null, 1"
    }, nullValues = {"null"})
    public void testFinalPrice(Integer initialPrice, int finalPrice) {
        doAnswer(i -> initialPrice).when(discountServiceMock).getDiscount(anyLong());
        LoyaltyService loyaltyService = new LoyaltyService(discountServiceMock);
        List<Purchase> purchaseList = Arrays.asList(
            new Purchase(1L, 1L)
        );
        Basket basket = new Basket(purchaseList);

        long buyerId = 10L;
        Basket resultBasket = loyaltyService.applyDiscounts(buyerId, basket);

        assertEquals(finalPrice, resultBasket.getPurchases().get(0).getFinalPrice());
        verify(discountServiceMock, times(1)).getDiscount(buyerId);
    }

}