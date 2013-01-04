package com.baidu.solr.service.impl;

import java.sql.ResultSet;

import com.baidu.solr.dao.TextDao;
import com.baidu.solr.dao.impl.TextDaoImpl;
import com.baidu.solr.model.Text;
import com.baidu.solr.service.TextService;

public class TextServiceImpl implements TextService {
	TextDao textdao=new TextDaoImpl();

	public void saveText(Text text) {
		
		
	}

	public void deleteTextAll(int[] id) {
		// TODO Auto-generated method stub
		
	}

	public int updateText(Text text) {
		// TODO Auto-generated method stub
		return 0;
	}

	public ResultSet findAllText() {
		
		return textdao.findAllText();
	}

}
