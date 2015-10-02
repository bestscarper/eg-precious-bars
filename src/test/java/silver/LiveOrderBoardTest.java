package silver;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class LiveOrderBoardTest {

    @Test
    public void shouldAcceptBuyAndSellOrders() {

        LiveOrderBoard board = new LiveOrderBoard();

        Order order = Order.Builder.newInstance()
                .setUser(USER1)
                .setQuantity(3500)
                .setPrice(303)
                .setOrderType(BUY);

        String orderRef = board.addOrder(order.build());

        board.addOrder(order.setPrice().build());
        board.addOrder(order.setOrderType(SELL).build());

        assertEquals(3, board.orderCount());
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