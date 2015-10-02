package silver;

import com.google.common.primitives.UnsignedLong;

public class Order {

    public Order(String userId, UnsignedLong quantityGrammes, UnsignedLong price, OrderType orderType) {
        this.userId = userId;
        this.quantityGrammes = quantityGrammes;
        this.price = price;
        this.orderType = orderType;
    }

    private String userId;
    private UnsignedLong quantityGrammes;
    private UnsignedLong price;
    private OrderType orderType;

}
