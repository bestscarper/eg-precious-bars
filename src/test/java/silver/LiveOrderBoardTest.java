package silver;

import com.google.common.primitives.UnsignedLong;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class LiveOrderBoardTest {

    private static final String USER1 = "user1";
    private LiveOrderBoard board;
    private OrderBuilder order;

    @Before
    public void setUp() throws Exception {
        board = new LiveOrderBoard(new OrderStore());
        order = OrderBuilder.createOrderBuilder()
                .setUserId(USER1)
                .setQuantityGrammes(UnsignedLong.valueOf(3500L))
                .setPrice(UnsignedLong.valueOf(303L))
                .setOrderType(OrderType.BUY);
    }

    @Test
    public void shouldAcceptBuyAndSellOrders() {

        board.addOrder(order.createOrder());
        board.addOrder(order.setOrderType(OrderType.SELL).createOrder());

        assertEquals(2, board.orderCount());
    }

    @Test
    public void shouldRemoveACancelledOrder() {

        UUID orderId = board.addOrder(order.createOrder());
        board.addOrder(order.setPrice(UnsignedLong.valueOf(300L)).createOrder());

        board.cancelOrder(orderId);

        assertEquals(1, board.orderCount());
    }

    @Test
    @Ignore
    public void summarizeBoardState() {
        assertTrue(true);
    }
}