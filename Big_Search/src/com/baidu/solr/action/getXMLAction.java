package com.baidu.solr.action;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.baidu.solr.utils.Point;
import com.baidu.solr.utils.Solr;
import com.opensymphony.xwork2.ActionSupport;

public class getXMLAction extends ActionSupport {
	private String word;
	private String recommend;
	
	
	public String getRecommend() {
		return recommend;
	}

	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}
	HttpServletResponse response = ServletActionContext.getResponse();
	HttpServletRequest request = ServletActionContext.getRequest();
	Map<String, Object> session = ServletActionContext.getContext().getSession();
	

	@Override
	public String execute() throws Exception {

		Solr solr = new Solr();
		Point<String, List<String>> p = null;
		p = solr.spellcheck(word);

		String x = p.getX();// 搜索索引返回推荐值
		session.put("recommend", x);
		List<String> list = p.getY();

		solr
				.createXml(
						"E:\\GuoChunguang\\workspace\\BaiDu\\Search\\WebRoot\\word.xml",
						list);

		// 将request转向xml文件
		try {
			request.getRequestDispatcher("word.xml").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	public String recommendValue(){
		System.out.println("lllllllllllllllll"+session.get("recommend"));
		return "recommond";
	}
	

	

	
}
