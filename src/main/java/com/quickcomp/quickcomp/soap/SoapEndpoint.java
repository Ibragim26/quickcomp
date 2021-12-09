package com.quickcomp.quickcomp.soap;

import com.quickcomp.quickcomp.soap.entities.GetOrderRequest;
import com.quickcomp.quickcomp.soap.entities.GetOrderResponse;
import com.quickcomp.quickcomp.soap.entities.GetProductRequest;
import com.quickcomp.quickcomp.soap.entities.GetProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class SoapEndpoint {
    @Autowired
    private SoapService service;

    @PayloadRoot(namespace = "http://quickcomp.com/quickcomp/soap/entities/",
            localPart = "getProductRequest")
    @ResponsePayload
    public GetProductResponse getProductRequest(@RequestPayload GetProductRequest request) {
        GetProductResponse response = new GetProductResponse();
        response.setProduct(service.getProduct(request.getOrderId()));
        return response;
    }

    @PayloadRoot(namespace = "http://quickcomp.com/quickcomp/soap/entities/",
            localPart = "getOrderRequest")
    @ResponsePayload
    public GetOrderResponse getOrderRequest(@RequestPayload GetOrderRequest request) {
        GetOrderResponse response = new GetOrderResponse();
        response.setBooking(service.getOrder(request.getId()));
        return response;
    }
}
