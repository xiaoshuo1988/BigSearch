package com.baidu.solr.dao;

import java.sql.ResultSet;

import com.baidu.solr.model.Text;
import com.baidu.solr.utils.BaseDao;

public interface TextDao {
	BaseDao baseDao=new BaseDao();
	public int saveText(Text text);
	public ResultSet findAllText();
	public int deleteText(Text text);
	public int updateText(Text text);
	public Text findTextById(int id);
	
}
