package silver;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LiveOrderBoardTest {

    private static final String USER1 = "user1";
    private static final UUID ORDER_ID = UUID.randomUUID();
    private LiveOrderBoard board;
    @Mock
    private OrderStore orderStore;

    @Before
    public void setUp() {
        board = new LiveOrderBoard(orderStore);
    }

    @Test
    public void givenOrderStore_whenAddingOrders_orderIsStored() {
        Order mockOrder = mock(Order.class);
        when(orderStore.add(mockOrder)).thenReturn(ORDER_ID);
        UUID newOrderId = board.addOrder(mockOrder);
        verify(orderStore).add(mockOrder);
        assertEquals(newOrderId, ORDER_ID);
    }

    // remove
    @Test
    public void givenOrderStore_whenCancellingOrder_orderIsRemovedFromStore() throws OrderCancelledException {
        board.cancelOrder(ORDER_ID);
        verify(orderStore).remove(ORDER_ID);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void givenOrderStore_whenCancellingInvalid_fails() throws OrderCancelledException {
        doThrow(OrderCancelledException.class).when(orderStore).remove(ORDER_ID);
        thrown.expect(OrderCancelledException.class);
        board.cancelOrder(ORDER_ID);
    }
}