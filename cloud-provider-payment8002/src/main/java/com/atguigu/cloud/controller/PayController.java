package com.atguigu.cloud.controller;

import com.atguigu.cloud.entities.Pay;
import com.atguigu.cloud.entities.PayDTO;
import com.atguigu.cloud.resp.ResultData;
import com.atguigu.cloud.service.PayService;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@Tag(name = "支付微服务模块",description = "支付CRUD")
public class PayController {
    @Resource
    private PayService payService;

    @PostMapping(value = "/pay/add")
    @Operation(summary = "新增",description = "新增支付流水方法，json串做参数")
    public ResultData addPay(@RequestBody Pay pay){
        int i = payService.add(pay);
        return ResultData.success("成功插入记录，返回值: "+i);
    }

    @DeleteMapping(value = "/pay/delete/{id}")
    @Operation(summary = "删除",description = "删除支付流水方法")
    public ResultData deletePay(@PathVariable("id") Integer id){
        return ResultData.success(payService.delete(id)) ;
    }

    @PutMapping(value = "/pay/update")
    @Operation(summary = "修改",description = "修改支付流水方法")
    public ResultData updatePay(@RequestBody PayDTO payDTO){

        Pay pay = new Pay();
        BeanUtils.copyProperties(payDTO,pay);

        int update = payService.update(pay);
        return ResultData.success("成功修改记录，返回值: "+update);
    }
    @GetMapping(value = "/pay/get/{id}")
    @Operation(summary = "查询",description = "查询支付流水方法")
    public ResultData getById(@PathVariable("id") Integer id){
        if(id == -4) throw new RuntimeException("id不能为负数");
        return ResultData.success(payService.getById(id));
    }

    @GetMapping(value = "/pay/getAll")
    @Operation(summary = "查询全部",description = "查询全部支付流水方法")
    public ResultData getAll(){
        return ResultData.success(payService.getAll());
    }

    @PostMapping(value = "/pay/getList")
    @Operation(summary = "查询payList",description = "查询全部支付流水List方法")
    public ResultData getAll(@RequestBody Pay pay){
        System.out.println(Json.pretty(pay));
        return ResultData.success(payService.getList(pay));
    }

    @Value("${server.port}")
    private String port;
    @Operation(summary = "获取consul信息",description = "获取consul信息")
    @GetMapping("/pay/get/info")
    public String getConsulInfo(@Value("${atguigu.info}") String info){
        return "atguiguInfo:"+info  +"\t"+"port:  "+port;
    }

}
