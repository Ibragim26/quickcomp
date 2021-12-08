package com.quickcomp.quickcomp.soap.service;

import com.quickcomp.quickcomp.dto.OrderDTO;
import com.quickcomp.quickcomp.service.interfaces.OrderService;
import com.quickcomp.quickcomp.soap.order.OrderSoap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.GregorianCalendar;

@Service
public class OrderSoapService {

    private OrderService service;

    @Autowired
    public OrderSoapService(OrderService orderService) {
        this.service = orderService;
    }

    public OrderSoap getOrder(long id) {

        OrderDTO order = service.getById(id);
        OrderSoap soap = new OrderSoap();

        GregorianCalendar c = new GregorianCalendar();

        c.setTime(order.getDate());
        XMLGregorianCalendar date = null;
        try {
            date = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }

        soap.setAddress(order.getAddress());
        soap.setDate(date);
        soap.setId(order.getId());
        return soap;
    }
}
