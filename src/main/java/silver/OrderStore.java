package silver;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OrderStore {

    // use default CHM settings for simplicity
    ConcurrentHashMap<UUID,Order> localStore = new ConcurrentHashMap<UUID, Order>();

    public UUID add(Order order) {
        UUID orderNum = UUID.randomUUID();
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
}
