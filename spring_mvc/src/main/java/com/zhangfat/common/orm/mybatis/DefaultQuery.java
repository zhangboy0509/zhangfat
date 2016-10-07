package com.zhangfat.common.orm.mybatis;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.executor.parameter.DefaultParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.SqlSessionUtils;
import org.springframework.core.annotation.Order;

public class DefaultQuery<T> implements Query<T> {
	
	private static Log log = LogFactory.getLog("com/zhangfat/common/orm/mybatis/DefaulQuery");
	private Map<String, Object> filterMap;
	@SuppressWarnings("rawtypes")
	private BaseDAO baseDao;
	private String listStatement;
	private StringBuffer sortColumns;
	private boolean hasSort;
	
	@SuppressWarnings("rawtypes")
	public DefaultQuery(BaseDAO baseDao, String statement) {
		hasSort = false;
		this.baseDao = baseDao;
		filterMap = new HashMap<String,Object>();
		listStatement = statement;
		sortColumns = new StringBuffer();
	}

	public int count() {
		return getCount(filterMap);
	}

	public Query<T> filterBean(Serializable entity) {
		try {
			Map<String,Object> filter = FactoryUtil.Bean2Map(entity);
			filterMap.putAll(filter);
		} catch (Exception e) {
			log.error(e);
		}
		return this;
	}

	public Query<T> filterMap(Map<String,Object> filterMap) {
		this.filterMap.putAll(filterMap);
		return this;
	}

	public Query<T> filterPro(String pro, Object value) {
		filterMap.put(pro, value);
		return this;
	}

	public List<T> list() {
		if (sortColumns.length() > 1){
			filterMap.put("sortColumns", sortColumns);
		}
		return baseDao.getSqlSession().selectList(listStatement, filterMap);
	}

	public PageResult<T> listPage(int page, int pagePreNum) {
		PageResult<T> res = new PageResult<T>();
		if (sortColumns.length() > 1){
			filterMap.put("sortColumns", sortColumns);
		}
		
		String databaseId = baseDao.getSqlSession().getConfiguration().
				getMappedStatement(listStatement).getConfiguration().getDatabaseId();
		
		List<T> list = null;
		if (databaseId.toUpperCase().equals("ORACLE")) {
			list = baseDao.getSqlSession().selectList(listStatement, filterMap, 
					new RowBounds(page*pagePreNum+1, pagePreNum));
		}else{
			list = baseDao.getSqlSession().selectList(listStatement, filterMap, 
					new RowBounds((page-1)*pagePreNum, pagePreNum));
		}
		res.setResult(list);
		res.setTotalNumber(getCount(filterMap));
		return res;
	}

	public Query<T> order(String col, Order order) {
		if(hasSort){
			sortColumns.append(",");
		}
		sortColumns.append(col).append("  ").append(order.toString()).toString();
		hasSort = true;
		return this;
	}

	public Serializable singleResult() {
		return (Serializable) baseDao.getSqlSession().selectOne(listStatement,
				filterMap);
	}

	private int getCount(Object parameter){
	    MappedStatement ms;
	    BoundSql boundSql;
	    DefaultParameterHandler dp;
	    PreparedStatement countStmt = null;
	    SqlSessionTemplate temp = null;
	    DefaultSqlSession defaultSession = null;
	    Connection connection = null;
	    int totalCount = 0;
	    
	    try{
		    ms = baseDao.getSqlSession().getConfiguration().getMappedStatement(listStatement);
		    boundSql = ms.getBoundSql(parameter);
		    dp = new DefaultParameterHandler(ms, parameter, boundSql);
		    temp = (SqlSessionTemplate)baseDao.getSqlSession();
		    defaultSession = (DefaultSqlSession)SqlSessionUtils.getSqlSession(temp.getSqlSessionFactory(), temp.getExecutorType(), temp.getPersistenceExceptionTranslator());
		    connection = defaultSession.getConnection();
		    
		    SqlPageSupport sqlPageSupport = new SqlPageSupport(ms.getConfiguration().getDatabaseId());
		    String coutSql = sqlPageSupport.getCountQuery(boundSql.getSql());
			countStmt = connection.prepareStatement(coutSql);
		    dp.setParameters(countStmt);
		    ResultSet rs = countStmt.executeQuery();
		    int result = 0;
		    if(rs.next()){
		        int tolalCount = rs.getInt(1);
		        result = tolalCount;
		    }
		    if(rs!=null){
		        rs.close();
		        rs = null;
		    }
		    if(!SqlSessionUtils.isSqlSessionTransactional(defaultSession, temp.getSqlSessionFactory())){
		        defaultSession.commit(true);
		    }
		    totalCount = result;

		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e);
		} finally {
		    try{
		        if( defaultSession!=null ){
		        	SqlSessionUtils.closeSqlSession(defaultSession, temp.getSqlSessionFactory());
		        }
		        if( countStmt!=null ){
		        	countStmt.close();
		        }
		    }catch(SQLException e){
		        log.error(e);
		    }
		}
	    
	    return totalCount;
	}


}