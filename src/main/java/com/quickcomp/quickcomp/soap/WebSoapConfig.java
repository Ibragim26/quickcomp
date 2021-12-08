package com.quickcomp.quickcomp.soap;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@Configuration
@EnableWs
public class WebSoapConfig {

        @Bean
        public ServletRegistrationBean messageDispatcherServlet(ApplicationContext context) {
            MessageDispatcherServlet servlet = new MessageDispatcherServlet();
            servlet.setApplicationContext(context);
            servlet.setTransformWsdlLocations(true);
            return new ServletRegistrationBean(servlet, "/soapWS/*");
        }

        @Bean
        public XsdSchema orderSchema() {
            return new SimpleXsdSchema(new ClassPathResource("schemas/orders.xsd"));
        }
        @Bean
        public XsdSchema productSchema() {
            return new SimpleXsdSchema(new ClassPathResource("schemas/products.xsd"));
        }


        @Bean
        public DefaultWsdl11Definition defaultWsdl11DefinitionOrder(XsdSchema orderSchema) {
            DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
            definition.setSchema(orderSchema);
            definition.setLocationUri("/soapWS/order");
            definition.setPortTypeName("OrderServicePort");
            definition.setTargetNamespace("http://quickcomp.com/quickcomp/soap/order/");
            return definition;
        }
        @Bean
        public DefaultWsdl11Definition defaultWsdl11DefinitionProduct(XsdSchema productSchema) {

            DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
            definition.setSchema(productSchema);
            definition.setLocationUri("/soapWS/product");
            definition.setPortTypeName("ProductServicePort");
            definition.setTargetNamespace("http://quickcomp.com/quickcomp/soap/product/");
            return definition;
        }

}
