package silver;

import com.google.common.primitives.UnsignedLong;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class AcceptanceTest {

    private static final String USER1 = "user1";
    private LiveOrderBoard board;
    private OrderBuilder order;
    private OrderSnapshotBuilder orderSnapshotBuilder;

    @Before
    public void setUp() {
        board = new LiveOrderBoard(new OrderStore());
        order = OrderBuilder.createOrderBuilder()
                .setUserId(USER1)
                .setQuantityGrammes(UnsignedLong.valueOf(3500L))
                .setPrice(UnsignedLong.valueOf(303L))
                .setOrderType(OrderType.BUY);
        OrderCombiner orderCombiner = new OrderCombiner();
        orderSnapshotBuilder = new OrderSnapshotBuilder(orderCombiner);
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
                "SELL ORDERS\n";
        OrderSnapshot snapshot = orderSnapshotBuilder.build((board));
        assertEquals(expectedSummary, snapshot.toString());
    }

    @Test
    public void ordersSortedBuyHighestFirst() {
        String expectedSummary = "Live Order Board\n" +
                "BUY ORDERS\n" +
                "3.5 kg for £350\n" +
                "3.5 kg for £300\n" +
                "3.5 kg for £250\n" +
                "SELL ORDERS\n";

        // commit orders in non-sequential order to emphasise sortedness
        board.addOrder(order.setPrice(UnsignedLong.valueOf(300L)).createOrder());
        board.addOrder(order.setPrice(UnsignedLong.valueOf(250L)).createOrder());
        board.addOrder(order.setPrice(UnsignedLong.valueOf(350L)).createOrder());

        OrderSnapshot snapshot = orderSnapshotBuilder.build(board);
        assertEquals(expectedSummary, snapshot.toString());
    }

    @Test
    public void ordersSortedSellLowestFirst() {
        String expectedSummary = "Live Order Board\n" +
                "BUY ORDERS\n" +
                "SELL ORDERS\n" +
                "3.5 kg for £250\n" +
                "3.5 kg for £300\n" +
                "3.5 kg for £350\n";

        // commit orders in non-sequential order to emphasise sortedness
        order.setOrderType(OrderType.SELL);
        board.addOrder(order.setPrice(UnsignedLong.valueOf(300L)).createOrder());
        board.addOrder(order.setPrice(UnsignedLong.valueOf(250L)).createOrder());
        board.addOrder(order.setPrice(UnsignedLong.valueOf(350L)).createOrder());

        OrderSnapshot snapshot = orderSnapshotBuilder.build(board);
        assertEquals(expectedSummary, snapshot.toString());
    }

    @Test
    public void combineOrdersAtSamePrice() {
        String expectedSummary = "Live Order Board\n" +
                "BUY ORDERS\n" +
                "3.5 kg for £300\n" +
                "SELL ORDERS\n" +
                "7.0 kg for £300\n";

        order.setOrderType(OrderType.SELL);
        board.addOrder(order.setPrice(UnsignedLong.valueOf(300L)).createOrder());
        board.addOrder(order.setPrice(UnsignedLong.valueOf(300L)).createOrder());
        board.addOrder(order.setOrderType(OrderType.BUY).setPrice(UnsignedLong.valueOf(300L)).createOrder());

        OrderSnapshot snapshot = orderSnapshotBuilder.build(board);
        assertEquals(expectedSummary, snapshot.toString());
    }
}