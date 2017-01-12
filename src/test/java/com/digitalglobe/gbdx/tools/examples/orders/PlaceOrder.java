package com.digitalglobe.gbdx.tools.examples.orders;

import java.io.IOException;
import java.util.Arrays;

import com.digitalglobe.gbdx.tools.orders.OrdersManager;
import com.digitalglobe.gbdx.tools.orders.model.OrderResult;


public class PlaceOrder {
    public static void main(String[] argv) throws IOException {
        OrdersManager ordersManager = new OrdersManager();

      /*  OrderResult result = ordersManager.placeOrder(Arrays.asList("104001000506EE00",
                                                           "1010010005231400"));

        System.out.println( "result is " + result.toString() ); */

        OrderResult result = ordersManager.placeOrder(Arrays.asList("104001000506EE00",
                "1010010005231400"), "http://requestb.in/1c0iufw1");

        System.out.println( "result is " + result.toString() );
    }
}
