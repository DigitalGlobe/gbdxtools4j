package com.digitalglobe.gbdx.tools.orders;

import java.io.IOException;
import java.util.List;

import com.digitalglobe.gbdx.tools.communication.CommunicationBase;
import com.digitalglobe.gbdx.tools.orders.model.OrderRequest;
import com.digitalglobe.gbdx.tools.orders.model.OrderResult;
import com.google.gson.Gson;


/**
 * Manages the interface with the workflow system.
 */
public class OrdersManager extends CommunicationBase {
    private static String baseUrl;

    public OrdersManager() {
        baseUrl = configurationManager.getBaseAPIUrl() + "/orders/v2";
    }

    /**
     * Calls /orders/v2/heartbeat to check if the orders system is alive
     *
     * @return true if we get an "ok", false otherwise.
     *
     * @throws IOException if there is an error communicating
     *
     */
    public boolean isAlive() throws IOException {
        String okString = getData(baseUrl + "/heartbeat", false);

        if (okString != null) {
            if ((okString.trim().toLowerCase().contains("ok")))
                return true;
        }

        return false;
    }

    /**
     * Calls /orders/v2/order to generate an order.
     *
     * @return an OrderResult object.
     *
     * @throws IOException if there is an error communicating
     *
     */
    public OrderResult placeOrder(List<String> acquisitions) throws IOException {
        String resultString = postData(baseUrl + "/order",
                new Gson().toJson(acquisitions.toArray(new String[0]), String[].class), true);

        return new Gson().fromJson(resultString, OrderResult.class);
    }

    /**
     * Calls /orders/v2/ordercb to generate an order with a callback.
     *
     * @return an OrderResult object.
     *
     * @throws IOException if there is an error communicating
     *
     */
    public OrderResult placeOrder(List<String> acquisitions, String callback) throws IOException {
        Gson gson = new Gson();

        OrderRequest orderRequest = new OrderRequest(acquisitions, callback);

        String resultString = postData(baseUrl + "/ordercb",
                gson.toJson(orderRequest, OrderRequest.class), true);

        return gson.fromJson(resultString, OrderResult.class);
    }

    /**
     * Calls /orders/v2/order to get the status of an order
     *
     * @return an OrderResult object.
     *
     * @throws IOException if there is an error communicating
     *
     */
    public OrderResult getOrderStatus(String orderId) throws IOException {
        String resultString = getData(baseUrl + "/order/" + orderId, true);

        return new Gson().fromJson(resultString, OrderResult.class);
    }
}
