package com.atguigu.cloud.controller;

import com.atguigu.cloud.apis.Feign9002;
import com.atguigu.cloud.apis.PayFeignSentinelApi;
import com.atguigu.cloud.resp.ResultData;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerOrder {
    @Resource
    private RestTemplate restTemplate;

    @Value("${service-url.nacos-user-service}")
    private String serverURL;

    @Resource
    private Feign9002 feign9002;

    @Resource
    private PayFeignSentinelApi sentinelApi;


    @GetMapping("/consumer/pay/nacos/{id}")
    public String paymentInfo(@PathVariable("id") Integer id)
    {
        String result = restTemplate.getForObject(serverURL + "/pay/nacos/" + id, String.class);
        return result+"\t"+"    我是OrderNacosController83调用者。。。。。。";
    }
    @GetMapping("/consumer/pay/nacos/get/{orderNo}")
    public ResultData getOrderByXXX(@PathVariable("orderNo") String orderNo ){
        //getPayByOrderNo(orderNo)
        return sentinelApi.getPayByOrdeNo(orderNo);
    }
}
