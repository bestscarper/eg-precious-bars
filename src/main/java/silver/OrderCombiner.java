package silver;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class OrderCombiner {

    /**
     * Combined orders, removing dupes.
     *
     * @param sortedOrders List of pre-sorted orders of a single type
     * @return List of orders, with duplicate priced orders combined.
     */
    public List<Order> coalesce(Stream<Order> sortedOrders) {

        // we could use a functional approach but it might be more complex than imperative
        ArrayList<Order> orders = new ArrayList<>();
        sortedOrders.forEach( o -> {
            int lastElement = orders.size()-1;
            if (lastElement >= 0 && o.getPrice().equals(orders.get(lastElement).getPrice())) {
                orders.set(lastElement, o.mergeSamePrice(orders.get(lastElement)));
            } else {
                orders.add(o);
            }
        });

        return orders;
    }
}
