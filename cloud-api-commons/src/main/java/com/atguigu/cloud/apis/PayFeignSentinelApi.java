package com.atguigu.cloud.apis;

import com.atguigu.cloud.resp.ResultData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//name = "nacos-payment-provider",url = "http://localhost:9001",
@FeignClient(value = "nacos-payment-provider3",fallback = PayFeignSentinelApiFallBack.class)
public interface PayFeignSentinelApi {

    @GetMapping("/pay/nacos/get/{orderNo}")
    public ResultData getPayByOrdeNo(@PathVariable("orderNo") String orderNp);
}
