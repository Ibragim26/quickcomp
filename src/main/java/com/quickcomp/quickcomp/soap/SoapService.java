package com.quickcomp.quickcomp.soap;

import com.quickcomp.quickcomp.dto.OrderDTO;
import com.quickcomp.quickcomp.dto.ProductCategoryDTO;
import com.quickcomp.quickcomp.service.interfaces.OrderService;
import com.quickcomp.quickcomp.service.interfaces.ProductService;
import com.quickcomp.quickcomp.soap.entities.OrderSoap;
import com.quickcomp.quickcomp.soap.entities.ProductSoap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.GregorianCalendar;

@Service
public class SoapService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    public OrderSoap getOrder(long id) {
        OrderDTO order = orderService.getById(id);

        GregorianCalendar c = new GregorianCalendar();
        c.setTime(order.getDate());
        XMLGregorianCalendar date = null;
        try {
            date = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        OrderSoap soap = new OrderSoap(order.getId(), order.getAddress(), date);
        return soap;
    }



    @Transactional
    public ProductSoap getProduct(long id) {
        ProductCategoryDTO product = productService.getById(id);
        ProductSoap soap = new ProductSoap(product.getId(), product.getName(), product.getDescription(), product.getPrice());
        return soap;
    }

}
