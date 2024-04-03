package com.atguigu.cloud.service.impl;

import com.atguigu.cloud.apis.PayFeignApi;
import com.atguigu.cloud.service.PayService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class PayServiceImpl implements PayService {

    @Resource
    private PayFeignApi payFeignApi;

    @Override
    @CircuitBreaker(name = "cloud-payment-service",fallbackMethod = "myCircuitFallback")
    public String getSircuitInfo(Integer id) {
        return payFeignApi.myCircuit(id);
    }


    //myCircuitFallback就是服务降级后的兜底处理方法
    public String myCircuitFallback(Integer id,Throwable t) {
        // 这里是容错处理逻辑，返回备用结果
        return "myCircuitFallback，系统繁忙，请稍后再试-----/(ㄒoㄒ)/~~";
    }
}
