package com.zhangfat.common.orm.mybatis;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.core.annotation.Order;

public interface Query<T>{

    public abstract Query<T> filterBean(Serializable serializable);
    public abstract Query<T> filterPro(String s, Object obj);
    public abstract Query<T> filterMap(Map<String, Object> map);
    public abstract int count();
    public abstract Serializable singleResult();
    public abstract List<T> list();
    public abstract PageResult<T> listPage(int i, int j);
    public abstract Query<T> order(String s, Order order1);
}
