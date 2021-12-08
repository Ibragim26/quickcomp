package com.quickcomp.quickcomp.soap.endpoint;

import com.quickcomp.quickcomp.soap.product.GetProductRequest;
import com.quickcomp.quickcomp.soap.product.GetProductResponse;
import com.quickcomp.quickcomp.soap.service.ProductSoapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class ProductSoapEndpoint {

    @Autowired
    private ProductSoapService productService;

    @PayloadRoot(namespace = "http://quickcomp.com/quickcomp/soap/product/",
            localPart = "getProductRequest")
    @ResponsePayload
    public GetProductResponse getProductRequest(@RequestPayload GetProductRequest request) {
        GetProductResponse response = new GetProductResponse();
        response.setProduct(productService.getProduct(request.getOrderId()));
        return response;
    }
}
