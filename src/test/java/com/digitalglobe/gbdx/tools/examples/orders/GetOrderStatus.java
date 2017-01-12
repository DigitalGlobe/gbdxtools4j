package com.digitalglobe.gbdx.tools.examples.orders;

import java.io.IOException;

import com.digitalglobe.gbdx.tools.orders.OrdersManager;
import com.digitalglobe.gbdx.tools.orders.model.OrderResult;


public class GetOrderStatus {
    public static void main(String[] argv) throws IOException {
        OrdersManager ordersManager = new OrdersManager();

        OrderResult result = ordersManager.getOrderStatus("d2bbddf7-4e2e-402a-b9aa-fe5c1ea36d0b");

        System.out.println( "result is " + result.toString() );
    }
}
