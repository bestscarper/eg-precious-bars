package silver;

import java.util.List;
import java.util.stream.Stream;

public class OrderSnapshotBuilder {

    private final OrderCombiner orderCombiner;

    public OrderSnapshotBuilder(OrderCombiner orderCombiner) {
        this.orderCombiner = orderCombiner;
    }

    public OrderSnapshot build(LiveOrderBoard liveOrderBoard) {
        return new OrderSnapshot(buyOrderSummary(liveOrderBoard), sellOrderSummary(liveOrderBoard));
    }

    private List<Order> buyOrderSummary(LiveOrderBoard liveOrderBoard) {
        Stream<Order> sorted = liveOrderBoard.getOrdersFilteredAndSorted(OrderType.BUY);

        return orderCombiner.coalesce(sorted);
    }

    private List<Order> sellOrderSummary(LiveOrderBoard liveOrderBoard) {
        Stream<Order> sorted = liveOrderBoard.getOrdersFilteredAndSorted(OrderType.SELL);

        return orderCombiner.coalesce(sorted);
    }


}
