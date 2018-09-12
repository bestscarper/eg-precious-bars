package silver;

import com.google.common.collect.ImmutableList;
import com.google.common.primitives.UnsignedLong;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class OrderCombinerTest {

    private OrderCombiner subject;
    private OrderBuilder order;
    private static final String USER1 = "user1";

    @Before
    public void init() {
        subject = new OrderCombiner();
        order = OrderBuilder.createOrderBuilder()
                .setUserId(USER1)
                .setQuantityGrammes(UnsignedLong.valueOf(3500L))
                .setPrice(UnsignedLong.valueOf(303L))
                .setOrderType(OrderType.BUY);
    }

    @Test
    public void shouldCombineOrdersWithSamePrice() {
        List<Order> orders = ImmutableList.of(
                order.setPrice(UnsignedLong.valueOf(250)).createOrder(),
                order.setPrice(UnsignedLong.valueOf(250)).createOrder(),
                order.setPrice(UnsignedLong.valueOf(350)).createOrder()
        );

        List<Order> flattenedOrders = subject.coalesce(orders.stream());

        assertEquals(2, flattenedOrders.size());
        Order found = flattenedOrders.get(0);
        assertEquals(UnsignedLong.valueOf(7000L), found.getQuantityGrammes());
    }

    @Test
    public void shouldHandleEmptyList()  {
        List<Order> orders = ImmutableList.of();

        List<Order> flattenedOrders = subject.coalesce(orders.stream());

        assertEquals(0, flattenedOrders.size());
    }

    @Test
    public void shouldHandleSingleOrder()  {
        List<Order> orders = ImmutableList.of(order.createOrder());

        List<Order> flattenedOrders = subject.coalesce(orders.stream());

        assertEquals(1, flattenedOrders.size());
    }
}