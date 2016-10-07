package com.zhangfat.common.orm.mybatis;

public class SqlPageSupport {
	
	private String dataSourceId;
	
	public SqlPageSupport(String dataSourceId) {
		this.dataSourceId = dataSourceId;
	}

	public String getPagedQuery(String sql, int startNum, int pagePreNum) {
		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
		if (dataSourceId.toUpperCase().equals("ORACLE")) {
			pagingSelect
					.append("SELECT * FROM (SELECT INNER.*, ROWNUM as zhangfat FROM (");
			pagingSelect.append(sql);
			pagingSelect.append((new StringBuilder(") INNER WHERE ROWNUM <= "))
					.append(startNum + pagePreNum)
					.append(") WRAPPED WHERE WRAPPED.zhangfat >=   ")
					.append(startNum).toString());
		} else if (dataSourceId.toUpperCase().equals("MYSQL")) {
			pagingSelect.append("SELECT TEMP.*,1 zhangfat FROM (");
			pagingSelect.append(sql);
			pagingSelect.append((new StringBuilder(" ) TEMP LIMIT "))
					.append(startNum).append(" , ")
					.append(startNum + pagePreNum).toString());
		} else {
			throw new NullPointerException("dbType is not supported");
		}
		return pagingSelect.toString();
	}

	public String getCountQuery(String sql) {
		StringBuffer buffer = new StringBuffer(sql.length() + 100);
		if (dataSourceId.toUpperCase().equals("ORACLE"))
			return buffer.append("SELECT count(*) FROM (").append(sql)
					.append(") counttable ").toString();
		else
			return buffer.append("SELECT count(*) FROM (").append(sql)
					.append(") as counttable ").toString();
	}

	public static boolean isSupportPage(String dataSourceId) {
		if (dataSourceId.toUpperCase().equals("ORACLE"))
			return true;
		return dataSourceId.toUpperCase().equals("MYSQL");
	}

}
