package silver;

import com.google.common.primitives.UnsignedLong;

public class OrderBuilder {
    private String userId;
    private UnsignedLong quantityGrammes;
    private UnsignedLong price;
    private OrderType orderType;

    private OrderBuilder() {
    }

    public static OrderBuilder createOrderBuilder() {
        return new OrderBuilder();
    }

    public OrderBuilder setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public OrderBuilder setQuantityGrammes(UnsignedLong quantityGrammes) {
        this.quantityGrammes = quantityGrammes;
        return this;
    }

    public OrderBuilder setPrice(UnsignedLong price) {
        this.price = price;
        return this;
    }

    public OrderBuilder setOrderType(OrderType orderType) {
        this.orderType = orderType;
        return this;
    }

    public Order createOrder() {
        return new Order(userId, quantityGrammes, price, orderType);
    }
}