package silver;

import java.util.UUID;
import java.util.stream.Stream;

/**
 * A business API for the Live Order Board.
 *
 * This class allows for a thread-safe ways of handling orders
 * by assuming that the underlying OrderStore is thread-safe.
 */
public class LiveOrderBoard {

    public LiveOrderBoard(OrderStore orderStore) {
        this.orderStore = orderStore;
    }

    private OrderStore orderStore;

    /**
     * Add an order to the system.
     *
     * @param order - a new buy/sell Order
     * @return the unique id generated for the order in the system
     */
    synchronized public UUID addOrder(Order order) {
        UUID orderId = orderStore.add(order);
        return orderId;
    }

    /**
     * @return number of orders in the system
     */
    synchronized public int orderCount() {
        return orderStore.size();
    }

    /**
     * Allows an order to be cancelled given that we have the order id generated
     * when it was added.
     *
     * Attempts to cancel an order twice will lead to an OrderCancelledException.
     *
     * @param orderId
     * @throws OrderCancelledException
     */
    synchronized public void cancelOrder(UUID orderId) throws OrderCancelledException {
        orderStore.remove(orderId);
    }

    /**
     * Returns a stream of orders, filtered by the given orderType, and sorted
     * by the natural sort order of Order.
     *
     * @param orderType
     * @return stream of Order objects
     */
    public Stream<Order> getOrdersFilteredAndSorted(OrderType orderType) {
        return orderStore.getOrdersFilteredAndSorted(orderType);
    }
}
