package silver;

/**
 * Created by ashley on 02/10/2015.
 */
public class OrderSnapshot {
    public static OrderSnapshot of(LiveOrderBoard liveOrderBoard) {
        return new OrderSnapshot();
    }

    @Override
    public String toString() {
        return "Live Order Board\n" +
                "BUY ORDERS\n" +
                "(NONE)\n" +
                "SELL ORDERS\n" +
                "(NONE)\n";
    }
}
