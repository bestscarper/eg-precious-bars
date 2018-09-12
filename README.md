#### Precious Metal Bars
I was sent this as a coding exercise for a contract application in Sept 2015.

I wasn't paid for the work, and received no feedback on the solution or the application, so I've made the solution public.

I'm not claiming this is a stellar example of Java code, but it was written in about 2 hours.

The "client" wishes to have a Live Order Board for buying and selling of precious metals.

Requirement 1: Add an order. This should have the following properties:
  * user id
  * quantity (grams)
  * price per kg (£)
  * BUY/SELL

Requirement 2: Ability to remove an order after placement.

Requirement 3: Display current buy/sell orders
  * 3.1 - "buy" orders displayed in decreasing order of price
  * 3.2 - "sell" orders displayed in increasing order of price
  * 3.3 - orders of the same type, for the same price, are merged

The LiveOrderBoard is the main API used to handle incoming orders (requirements 1/2).

The OrderSnapshotBuilder is an API used to generate a set of orders for the "UI team".

