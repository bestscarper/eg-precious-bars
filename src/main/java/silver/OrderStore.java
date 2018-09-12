package silver;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * Persistance layer for Orders.
 * Implemented in memory for simplicities sake.
 *
 * The class should be thread-safe by design, using ConcurrentHashMap.
 */
public class OrderStore {

    // use default CHM settings for simplicity
    ConcurrentHashMap<UUID,Order> localStore = new ConcurrentHashMap<UUID, Order>();

    public UUID add(Order order) {
        UUID orderNum = UUID.randomUUID(); // unique-ish
        localStore.putIfAbsent(orderNum, order);
        return orderNum;
    }

    public int size() {
        return localStore.size();
    }

    synchronized public void remove(UUID orderId) throws OrderCancelledException {
        if (localStore.containsKey(orderId)) {
            localStore.remove(orderId);
        }
        else {
            throw new OrderCancelledException();
        }
    }

    public Stream<Order> getOrdersFilteredAndSorted(OrderType requiredType) {
        return localStore
                .values()
                .stream()
                .filter(order -> order.isOfType(requiredType))
                .sorted();
    }
}
