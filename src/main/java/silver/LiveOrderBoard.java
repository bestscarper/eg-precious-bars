package silver;

import java.util.UUID;

public class LiveOrderBoard {

    public LiveOrderBoard(OrderStore orderStore) {
        this.orderStore = orderStore;
    }

    private OrderStore orderStore;

    public UUID addOrder(Order order) {
        UUID orderId = orderStore.add(order);
        return orderId;
    }

    public int orderCount() {
        return orderStore.size();
    }
}
