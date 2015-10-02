package silver;

/**
 * Created by ashley on 02/10/2015.
 */
public class OrderSnapshot {

    StringBuffer snapshotText;

    public OrderSnapshot(LiveOrderBoard liveOrderBoard) {
        snapshotText = new StringBuffer("Live Order Board\n");
        liveOrderBoard.render(snapshotText);
    }

    public static OrderSnapshot of(LiveOrderBoard liveOrderBoard) {
        return new OrderSnapshot(liveOrderBoard);
    }

    @Override
    public String toString() {
        return snapshotText.toString();
    }
}
