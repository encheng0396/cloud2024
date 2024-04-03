package com.atguigu.cloud.service.Impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.atguigu.cloud.entities.Pay;
import com.atguigu.cloud.mapper.PayMapper;
import com.atguigu.cloud.service.PayService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PayServiceImpl implements PayService {

    @Resource
    private PayMapper payMapper;

    @Override
    public int add(Pay pay) {
        return payMapper.insertSelective(pay);
    }

    @Override
    public int delete(Integer id) {
        return payMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(Pay pay) {
        return payMapper.updateByPrimaryKeySelective(pay);
    }

    @Override
    public Pay getById(Integer id) {
        return payMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Pay> getAll() {
        List<Pay> pays = payMapper.selectAll();
        return pays;
    }

    @Override
    public Page<Pay> getList(Pay pay) {
        PageHelper.startPage(1, 1);
        List<Pay> getlist = payMapper.getlist(pay);
        Page<Pay> page = (Page<Pay>) getlist;
        System.out.println("-------------");
        System.out.println(page);
        System.out.println(page.getTotal());
        System.out.println(page.getResult());
        System.out.println("-------------");
        return page;
    }
}
