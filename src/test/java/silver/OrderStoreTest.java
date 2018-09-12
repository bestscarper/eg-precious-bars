package silver;

import com.google.common.primitives.UnsignedLong;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.internal.matchers.Or;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class OrderStoreTest {

    private OrderStore subject;
    private OrderBuilder orderBuilder;

    @Before
    public void setUp()  {
        subject = new OrderStore();
        orderBuilder = OrderBuilder.createOrderBuilder()
                .setUserId("user")
                .setQuantityGrammes(UnsignedLong.valueOf(3500L))
                .setPrice(UnsignedLong.valueOf(303L))
                .setOrderType(OrderType.BUY);
    }

    @Test
    public void givenEmptyStore_sizeIsZero() {
        assertEquals(subject.size(),0);
    }

    @Test
    public void givenEmptyStore_whenWeAddOrders_sizeIsNonZero() {
        subject.add(orderBuilder.createOrder());
        subject.add(orderBuilder.createOrder());

        assertEquals(subject.size(),2);
    }

    @Test
    public void givenOrderAdded_whenRemoved_sizeIsReduced() throws OrderCancelledException {
        UUID orderId = subject.add(orderBuilder.createOrder());
        subject.remove(orderId);
        assertEquals(subject.size(), 0);
    }

    @Test
    public void givenNonEmptyStore_ReturnsSortedListOfOrders() {
        subject.add(orderBuilder.createOrder());
        subject.add(orderBuilder.setPrice(UnsignedLong.valueOf(450L)).createOrder());

        List<Order> sortedList = subject.getOrdersFilteredAndSorted(OrderType.BUY).collect(Collectors.toList());
        assertEquals(sortedList.size(),2);
        assertEquals(sortedList.get(0).getPrice(),UnsignedLong.valueOf(450L));
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void givenNonEmptyStore_whenCancelledTwice_throwsException() throws OrderCancelledException {
        thrown.expect(OrderCancelledException.class);
        UUID orderId = subject.add(orderBuilder.createOrder());
        subject.remove(orderId);
        subject.remove(orderId);
    }

    @Test
    public void givenNonEmptyStore_whenInvalidOrderIdRemoved_throwsException() throws OrderCancelledException {
        thrown.expect(OrderCancelledException.class);
        UUID orderId = subject.add(orderBuilder.createOrder());
        subject.remove(UUID.randomUUID());
    }
}