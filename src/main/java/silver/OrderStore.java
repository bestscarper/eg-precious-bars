package silver;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.primitives.UnsignedLong;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

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

    public Stream<Order> buyOrderSummary() {
        Stream<Order> sorted = localStore
                .values()
                .stream()
                .filter(order -> order.isOfType(OrderType.BUY))
                .sorted();

        List<Order> combined = coalesce(sorted);
        return combined.stream();
    }

    public Stream<Order> sellOrderSummary() {
        Stream<Order> sorted = localStore
                .values()
                .stream()
                .filter(order -> order.isOfType(OrderType.SELL))
                .sorted();

        List<Order> combined = coalesce(sorted);
        return combined.stream();
    }

    @VisibleForTesting
    protected static List<Order> coalesce(Stream<Order> sorted) {
        // coalesce/combine orders of same price
        // assumes stream if of single order type, and pre-sorted
        // use imperative form because my brain hurts trying to build a reduction :(
        ArrayList<Order> orders = new ArrayList<>();
        UnsignedLong lastWeight = UnsignedLong.valueOf(0L);
        sorted.forEach( o -> {
            int lastElement = orders.size()-1;
            if (lastElement >= 0 && o.price.equals(orders.get(lastElement).price)) {
                orders.set(lastElement, o.mergeSamePrice(orders.get(lastElement)));
            } else {
                orders.add(o);
            }
        });

        return orders;
    }

}
