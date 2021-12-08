package com.quickcomp.quickcomp.soap.endpoint;

import com.quickcomp.quickcomp.soap.order.GetOrderRequest;
import com.quickcomp.quickcomp.soap.order.GetOrderResponse;
import com.quickcomp.quickcomp.soap.service.OrderSoapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class OrderSoapEndpoint {

    @Autowired
    private OrderSoapService orderService;

    @PayloadRoot(namespace = "http://quickcomp.com/quickcomp/soap/order/",
            localPart = "getOrderRequest")
    @ResponsePayload
    public GetOrderResponse getOrderRequest(@RequestPayload GetOrderRequest request) {
        GetOrderResponse response = new GetOrderResponse();
        response.setBooking(orderService.getOrder(request.getId()));
        return response;
    }
}
