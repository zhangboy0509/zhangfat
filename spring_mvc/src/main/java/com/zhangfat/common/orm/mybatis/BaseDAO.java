package com.zhangfat.common.orm.mybatis;

import java.io.Serializable;
import org.mybatis.spring.support.SqlSessionDaoSupport;

public abstract class BaseDAO<T extends Serializable,id> extends SqlSessionDaoSupport{
	
	private static String maperNameSpace;
	
	@SuppressWarnings("rawtypes")
	public BaseDAO(Class m){
        maperNameSpace = m.getName();
    }
	
    private String getPrimaryKeyStatement(){
        return (new StringBuffer(String.valueOf(maperNameSpace))).append(".getById").toString();
    }

    private String getInsertStatement(){
        return (new StringBuffer(String.valueOf(maperNameSpace))).append(".insert").toString();
    }

    private String getUpdateStatement(){
        return (new StringBuffer(String.valueOf(maperNameSpace))).append(".update").toString();
    }

    private String getUpdateNotNullStatement(){
        return (new StringBuilder(String.valueOf(maperNameSpace))).append(".updateNotNull").toString();
    }

    private String getDeleteStatement(){
        return (new StringBuilder(String.valueOf(maperNameSpace))).append(".delete").toString();
    }

    private String getDeleteIdsStatement(){
        return (new StringBuilder(String.valueOf(maperNameSpace))).append(".deleteIds").toString();
    }

    public String getListStatement(){
        return (new StringBuilder(String.valueOf(maperNameSpace))).append(".list").toString();
    }

    public T getById(Serializable id){
        T object = getSqlSession().selectOne(getPrimaryKeyStatement(), id);
        return object;
    }

    public int deleteById(Serializable id){
        return getSqlSession().delete(getDeleteStatement(), id);
    }

    public int deleteByIds(Serializable ids[]){
        if(ids != null && ids.length != 0)
            return getSqlSession().delete(getDeleteIdsStatement(), ids);
        else
            return 0;
    }

    public int save(Serializable entity){
        return getSqlSession().insert(getInsertStatement(), entity);
    }

    public int update(Serializable entity){
        return getSqlSession().update(getUpdateStatement(), entity);
    }

    public int updateNotNull(Serializable entity){
        return getSqlSession().update(getUpdateNotNullStatement(), entity);
    }
    
    public Query<T> createQuery()
    {
        return new DefaultQuery<T>(this, getListStatement());
    }

    public Query<T> createQuery(String statement)
    {
        return new DefaultQuery<T>(this, statement);
    }
}