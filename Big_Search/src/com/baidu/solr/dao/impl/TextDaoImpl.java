package com.baidu.solr.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.baidu.solr.dao.TextDao;
import com.baidu.solr.model.Text;



public class TextDaoImpl implements TextDao {
	
	
	
	public int saveText(Text text) {
		String sql="insert into table_text (id,title,content,href) values(text_seq.nextval,?,?,?)";
//		String params[]=new String[]{text.getTitle(),Long.toString(text.getContent()),text.getHref()};
		
	/*	List arrayl=new ArrayList();
		arrayl.add(text.getTitle());
		arrayl.add(text.getContent());
		arrayl.add(text.getHref());*/
		int saveResult=baseDao.executeUpdate(sql, text.getTitle(),text.getContent(),text.getHref());
		return saveResult;
	}
	
	public int deleteText(Text text) {
		String sql="delete from table_text where id= ?";
		
		/*List params=new ArrayList();
		params.add(text.getId());*/
		int deleteResult=baseDao.executeUpdate(sql, text.getId());
		
		return deleteResult;
	}
	
	public int updateText(Text text) {
		String sql="update table_text set title= ?,content= ?,href= ? where id= ?";
		
		/*List params=new ArrayList();
		params.add(text.getTitle());
		params.add(text.getContent());
		params.add(text.getHref());
		params.add(text.getId());*/
		int updateResult=baseDao.executeUpdate(sql, text.getTitle(),text.getContent(),text.getHref(),text.getId());
		
		return updateResult;
	}
	
	public ResultSet findAllText() {
		String sql="select * from table_text order by id asc";
		ResultSet rs=baseDao.getResultSet(sql, null);
			
		return rs;
	}
	
	public Text findTextById(int id) {
		String sql="select * from table_text where id =?";
		/*List params=new ArrayList();
		params.add(id);*/
		ResultSet rs=baseDao.getResultSet(sql, id);
		Text t=null;
		/*try {
			System.out.println(rs.getRow());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		try {
			while(rs.next()){
				t=new Text();
				t.setId(rs.getInt("id"));
				t.setTitle(rs.getString("title"));
				t.setContent(rs.getString("content"));
				t.setHref(rs.getString("href"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
		
		return t;
	}
}
