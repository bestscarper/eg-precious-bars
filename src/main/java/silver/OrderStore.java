package silver;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OrderStore {

    Map<UUID,Order> localStore = new ConcurrentHashMap<UUID, Order>();

    public UUID add(Order order) {
        UUID orderNum = UUID.randomUUID();
        localStore.put(orderNum,order);
        return orderNum;
    }

    public int size() {
        return localStore.size();
    }

    public void remove(UUID orderId) {
        localStore.remove(orderId);
    }
}
