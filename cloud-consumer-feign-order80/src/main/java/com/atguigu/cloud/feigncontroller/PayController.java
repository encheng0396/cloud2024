package com.atguigu.cloud.feigncontroller;

import cn.hutool.core.date.DateUtil;
import com.atguigu.cloud.apis.PayFeignApi;
import com.atguigu.cloud.entities.PayDTO;
import com.atguigu.cloud.resp.ResultData;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
public class PayController {

    @Resource
    private PayFeignApi payFeignApi;

    @PostMapping(value = "/feign/pay/add")
    public ResultData addOrder(@RequestBody PayDTO payDTO){
        ResultData resultData = payFeignApi.addPay(payDTO);
        return resultData;
    }
    @GetMapping(value = "/feign/pay/get/{id}")
    public ResultData getOrder(@PathVariable("id") Integer id){
        System.out.println("-------远程调用按照id查询订单支付流水信息----------");
        ResultData pay = null;
        try {
            System.out.println("调用开始--------："+ DateUtil.now());
            pay = payFeignApi.getPay(id);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("调用结束--------："+DateUtil.now());
        }

        return pay;
    }

    @GetMapping(value = "/feign/pay/get/info")
    public String getInfo(){
        String info = payFeignApi.getInfoByConsul();
        return info;
    }


    @GetMapping(value = "/pay/circuit/{id}")
    public String myCircuit(@PathVariable("id") Integer id){
        String str = payFeignApi.myCircuit(id);
        return str;
    }


}
