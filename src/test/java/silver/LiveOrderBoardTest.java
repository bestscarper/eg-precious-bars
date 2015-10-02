package silver;

import com.google.common.primitives.UnsignedLong;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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
    public void shouldRemoveACancelledOrder() throws OrderCancelledException {

        UUID orderId = board.addOrder(order.createOrder());
        board.addOrder(order.setPrice(UnsignedLong.valueOf(300L)).createOrder());

        board.cancelOrder(orderId);

        assertEquals(1, board.orderCount());
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldFailIfOrderCancelledTwice() throws OrderCancelledException {
        thrown.expect(OrderCancelledException.class);
        UUID orderId = board.addOrder(order.createOrder());
        board.cancelOrder(orderId);
        board.cancelOrder(orderId);
    }

    @Test
    public void summarizeEmptyBoardState() {
        String expectedSummary = "Live Order Board\n" +
                "BUY ORDERS\n" +
                "(NONE)\n" +
                "SELL ORDERS\n" +
                "(NONE)\n";

        OrderSnapshot snapshot = board.summarize();
        assertEquals(expectedSummary, snapshot.toString());
    }
}