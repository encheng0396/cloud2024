package com.atguigu.cloud.controller;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.cloud.entities.PayDTO;
import com.atguigu.cloud.resp.ResultData;
import com.atguigu.cloud.resp.ReturnCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Slf4j
@RestController
@RefreshScope //在控制器类加入@RefreshScope注解使当前类下的配置支持Nacos的动态刷新功能。
public class PayAlibabaController {
    @Value("${server.port}")
    private String configInfo;

    @GetMapping("/pay/config/port")
    public String getConfigPort() {
        return configInfo;
    }

    @GetMapping("/pay/nacos/{id}")
    public String getConfigInfo(@PathVariable("id") Integer id) {
        return "nacos registry,serverPort:"+configInfo+"\t id"+id;
    }

    //openfeign+sentinel进行服务降级和流量监控的整合处理

    @GetMapping("/pay/nacos/get/{orderNo}")
    @SentinelResource(value = "getPayByOrdeNo",blockHandler = "handlerBlockHandler")
    public ResultData getPayByOrdeNo(@PathVariable("orderNo") String orderNp){
        PayDTO payDTO = new PayDTO();
        payDTO.setId(1024);
        payDTO.setOrderNo(orderNp);
        payDTO.setAmount(BigDecimal.valueOf(9.9));
        payDTO.setUserId(11);
        return ResultData.success(payDTO);
    }
    public ResultData handlerBlockHandler(String orderNo, BlockException e){
        return ResultData.faild(ReturnCodeEnum.RC500.getCode(), "getPaybyOrdeNo服务不可用，"+
                "触发sentinel流控配置规则\t"+"o(π_π)o");
    }
}
