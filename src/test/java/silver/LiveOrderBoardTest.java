package silver;

import com.google.common.primitives.UnsignedLong;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class LiveOrderBoardTest {

    private static final String USER1 = "user1";

    @Test
    public void shouldAcceptBuyAndSellOrders() {

        LiveOrderBoard board = new LiveOrderBoard();

        OrderBuilder order = OrderBuilder.createOrderBuilder()
                .setUserId(USER1)
                .setQuantityGrammes(UnsignedLong.valueOf(3500L))
                .setPrice(UnsignedLong.valueOf(303))
                .setOrderType(OrderType.BUY);

        board.addOrder(order.createOrder());
        board.addOrder(order.setOrderType(OrderType.SELL).createOrder());

        assertEquals(2, board.orderCount());
    }

    @Test
    @Ignore
    public void shouldRemoveACancelledOrder() {
        assertTrue(true);
    }

    @Test
    @Ignore
    public void summarizeBoardState() {
        assertTrue(true);
    }
}