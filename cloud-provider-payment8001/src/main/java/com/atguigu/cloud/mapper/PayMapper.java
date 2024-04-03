package com.atguigu.cloud.mapper;

import com.atguigu.cloud.entities.Pay;
import com.github.pagehelper.Page;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PayMapper extends Mapper<Pay> {
    Page<Pay> getlist(Pay pay);
}