package com.baidu.solr.action;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.baidu.solr.service.TextService;
import com.baidu.solr.service.impl.TextServiceImpl;
import com.baidu.solr.utils.Solr;
import com.opensymphony.xwork2.ActionSupport;

public class Admin extends ActionSupport implements SessionAware,RequestAware {
	TextService textservice = new TextServiceImpl();

	private ResultSet rs;
	private Map<String, Object> session;
	private Map<String, Object> request;
	
	private String word;
	
	
	@Override
	public String execute() throws Exception {
		System.out.println("Hello");
		return null;
	}
	

	public String findAllText() {
		rs = textservice.findAllText();
		session.put("rs", rs);
		return SUCCESS;
	}
	
	public Admin() {
		super();
		System.out.println("alksdhfksdajh");
	}

	public String updateText() {
		
		return SUCCESS;
	}
	
	public String autoComplete(){
		//将字符串保存在request中
		System.out.println("hello");
		
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String a=request.getParameter("word");
		System.out.println(a);
		request.setAttribute("word", word);
		
		//request请求之前生成xml文件
		/*Solr solr=new Solr();
		solr.spellcheck("");*/
		
		
		
		//将请求转给视图层（注意Ajax中，这个所谓的视图层不返回页面，至返回数据，所以也称作一个数据层）
		try {
			request.getRequestDispatcher("word.xml").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	/*	HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/xml;charset=utf-8");
		response.setHeader("cache-control", "no-cache");
		try {
			PrintWriter out=response.getWriter();
			OutputFormat format=OutputFormat.createPrettyPrint();
			format.setEncoding("utf-8");
			XMLWriter writer=new XMLWriter(out, format);
			writer.write("absolute apple anything");
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		return null;
		
		
		
	}
	public Map<String, Object> getRequest() {
		return request;
	}

	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}

	
	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}
	
	

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public TextService getTextservice() {
		return textservice;
	}

	public void setTextservice(TextService textservice) {
		this.textservice = textservice;
	}

	public ResultSet getRs() {
		return rs;
	}

	public void setRs(ResultSet rs) {
		this.rs = rs;
	}

}
