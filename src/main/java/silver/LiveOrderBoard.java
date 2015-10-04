package silver;

import java.util.UUID;
import java.util.stream.Stream;

public class LiveOrderBoard {

    public LiveOrderBoard(OrderStore orderStore) {
        this.orderStore = orderStore;
    }

    private OrderStore orderStore;

    synchronized public UUID addOrder(Order order) {
        UUID orderId = orderStore.add(order);
        return orderId;
    }

    synchronized public int orderCount() {
        return orderStore.size();
    }

    synchronized public void cancelOrder(UUID orderId) throws OrderCancelledException {
        orderStore.remove(orderId);
    }

    synchronized public OrderSnapshot summarize() {
        return OrderSnapshot.of(this);
    }

    public void render(final StringBuffer snapshotText) {
        snapshotText.append("BUY ORDERS\n");
        Stream<Order> buySorted = orderStore.buyOrderSummary();
        buySorted.forEach(order -> snapshotText.append(order.toString()));

        snapshotText.append("SELL ORDERS\n");
        Stream<Order> sellSorted = orderStore.sellOrderSummary();
        sellSorted.forEach(order -> snapshotText.append(order.toString()));
    }
}
