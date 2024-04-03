package com.atguigu.cloud.controller;

import com.atguigu.cloud.entities.PayDTO;
import com.atguigu.cloud.resp.ResultData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@Tag(name = "订单模块调用支付模块",description = "订单调用支付CRUD")
public class OrderController {

    //public static final String PaymentSer_URL = "http://localhost:8001";//先写死，硬编码
    public static final String PaymentSer_URL = "http://cloud-payment-service";//服务注册中心上微服务的名称

    @Resource
    private RestTemplate restTemplate;

    @GetMapping(value = "/consumer/pay/add")
    @Operation(summary = "新增",description = "订单调用支付新增支付流水方法，json串做参数")
    public ResultData addOrder(PayDTO payDTO){
        return restTemplate.postForObject(PaymentSer_URL + "/pay/add", payDTO, ResultData.class);
    }

    @DeleteMapping(value = "/consumer/pay/delete/{id}")
    @Operation(summary = "删除",description = "订单调用支付删除支付流水方法，id做参数")
    public void delOrder(@PathVariable("id") Integer id){
        System.out.println(id);
        restTemplate.delete(PaymentSer_URL+"/pay/delete/"+id,id);
    }


    @GetMapping(value = "/consumer/pay/get/{id}")
    @Operation(summary = "查询",description = "订单调用支付查询支付流水方法，id做参数")
    public ResultData getOrder(@PathVariable("id") Integer id){
        return restTemplate.getForObject(PaymentSer_URL + "/pay/get/"+id, ResultData.class, id);
    }

    @GetMapping(value = "/consumer/pay/get/info")
    private String getInfoByConsul()
    {
        return restTemplate.getForObject(PaymentSer_URL + "/pay/get/info", String.class);
    }

    @Resource
    private DiscoveryClient discoveryClient;
    @GetMapping("/consumer/discovery")
    public String discovery()
    {
        List<String> services = discoveryClient.getServices();
        for (String element : services) {
            System.out.println(element);
        }

        System.out.println("===================================");

        List<ServiceInstance> instances = discoveryClient.getInstances("cloud-payment-service");
        for (ServiceInstance element : instances) {
            System.out.println(element.getServiceId()+"\t"+element.getHost()+"\t"+element.getPort()+"\t"+element.getUri());
        }

        return instances.get(0).getServiceId()+":"+instances.get(0).getPort();
    }

}
