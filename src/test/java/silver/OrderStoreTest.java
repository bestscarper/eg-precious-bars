package silver;

import com.google.common.collect.ImmutableList;
import com.google.common.primitives.UnsignedLong;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by ashley on 03/10/2015.
 */
public class OrderStoreTest {

    private OrderBuilder order;

    @Before
    public void setUp() throws Exception {
        order = OrderBuilder.createOrderBuilder()
                .setUserId("user")
                .setQuantityGrammes(UnsignedLong.valueOf(3500L))
                .setPrice(UnsignedLong.valueOf(303L))
                .setOrderType(OrderType.BUY);

    }

    @Test
    public void shouldCombineOrdersWithSamePrice() throws Exception {

        List<Order> orders = ImmutableList.of(
                order.setPrice(UnsignedLong.valueOf(250)).createOrder(),
                order.setPrice(UnsignedLong.valueOf(250)).createOrder(),
                order.setPrice(UnsignedLong.valueOf(350)).createOrder()
        );

        List<Order> flattenedOrders = OrderStore.coalesce(orders.stream());

        assertEquals(2, flattenedOrders.size());
        Order found = flattenedOrders.get(0);
        assertEquals(UnsignedLong.valueOf(7000L), found.quantityGrammes);
    }

    @Test
    public void shouldHandleEmptyList() throws Exception {
        List<Order> orders = ImmutableList.of();

        List<Order> flattenedOrders = OrderStore.coalesce(orders.stream());

        assertEquals(0, flattenedOrders.size());
    }

    @Test
    public void shouldHandleSingleOrder() throws Exception {
        List<Order> orders = ImmutableList.of(order.createOrder());

        List<Order> flattenedOrders = OrderStore.coalesce(orders.stream());

        assertEquals(1, flattenedOrders.size());
    }

}