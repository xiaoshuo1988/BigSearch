package com.baidu.solr.service;

import java.sql.ResultSet;

import com.baidu.solr.model.Text;

public interface TextService {
	public void saveText(Text text);
	public void deleteTextAll(int[] id);
	public int updateText(Text text);
	public ResultSet findAllText();
	
	
	
}
