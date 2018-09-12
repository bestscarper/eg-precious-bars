package silver;

import java.util.List;

/**
 * OrderSnapshot
 *
 * Contains a snapshot of live orders, in terms of "BUY" orders, and "SELL" orders.
 * Each list of orders is sorted and du-duped.
 *
 */
public class OrderSnapshot {
    private final List<Order> buyOrders;
    private final List<Order> sellOrders;

    /**
     * Return list of "BUY" orders.
     *
     * @return list of "BUY" orders, sorted, with dupes removed
     */
    public List<Order> getBuyOrders() {
        return buyOrders;
    }

    /**
     * Return list of "SELL" orders.
     *
     * @return list of "SELL" orders, sorted, with dupes removed
     */
    public List<Order> getSellOrders() {
        return sellOrders;
    }

    public OrderSnapshot(List<Order> buyOrders, List<Order> sellOrders) {
        this.buyOrders = buyOrders;
        this.sellOrders = sellOrders;
    }

    public void render(final StringBuffer snapshotText) {
        snapshotText.append("BUY ORDERS\n");
        buyOrders.forEach(order -> snapshotText.append(order.toString()));

        snapshotText.append("SELL ORDERS\n");
        sellOrders.forEach(order -> snapshotText.append(order.toString()));
    }

    /**
     * Convenience method, which does a simple job of formatting the snapshot
     * for display in text format.
     *
     * @param snapshotText
     */
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer("Live Order Board\n");
        render(buffer);
        return buffer.toString();
    }
}
