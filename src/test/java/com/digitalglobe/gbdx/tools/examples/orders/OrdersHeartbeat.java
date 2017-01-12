package com.digitalglobe.gbdx.tools.examples.orders;

import java.io.IOException;

import com.digitalglobe.gbdx.tools.orders.OrdersManager;


public class OrdersHeartbeat {
    public static void main(String[] argv) throws IOException {
        OrdersManager ordersManager = new OrdersManager();

        if( ordersManager.isAlive() )
            System.out.println("orders system is up and running");
        else
            System.out.println("unable to get a heartbeat from the orders system");
    }
}
