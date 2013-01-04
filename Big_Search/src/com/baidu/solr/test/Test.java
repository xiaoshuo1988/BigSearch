package com.baidu.solr.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.baidu.solr.action.Admin;
import com.baidu.solr.dao.TextDao;
import com.baidu.solr.dao.impl.TextDaoImpl;
import com.baidu.solr.model.Text;
import com.baidu.solr.utils.Point;
import com.baidu.solr.utils.Solr;

public class Test {

	@org.junit.Test
	public void save() {
		TextDao t = new TextDaoImpl();
		Text t2 = new Text();
		t2.setTitle("�й�ʮ�˴�");
		t2.setContent("�й�������ٿ�ʮ�˴󣡣���");
		t2.setHref("http://www.bisu.edu.cn/Item/38258.aspx");
		int result = t.saveText(t2);
		// System.out.println(result);

	}

	@org.junit.Test
	public void delete() {
		TextDao t = new TextDaoImpl();
		Text t2 = new Text();
		t2.setId(21);
		/*
		 * t2.setTitle("�й�ʮ�˴�"); t2.setContent("�й�������ٿ�ʮ�˴󣡣���");
		 * t2.setHref("http://www.bisu.edu.cn/Item/38258.aspx");
		 */
		int result = t.deleteText(t2);
		// System.out.println(result);

	}

	@org.junit.Test
	public void update() {
		TextDao t = new TextDaoImpl();
		Text t2 = new Text();
		t2.setId(2);
		t2.setTitle("�й�ʮ�˴�");
		t2.setContent("�й�������ٿ�ʮ�˴󣡣���");
		t2.setHref("http://www.bisu.edu.cn/Item/38258.aspx");
		int result = t.updateText(t2);
		System.out.println(result);

	}

	@org.junit.Test
	public void select() {
		TextDao t = new TextDaoImpl();
		Text t2 = new Text();
		t2 = t.findTextById(2);
	}

	@org.junit.Test
	public void selectAll() {
		TextDao t = new TextDaoImpl();
		ResultSet rs = t.findAllText();
		try {

			while (rs.next()) {
				System.out.println(rs.getString("id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@org.junit.Test
	public void addField() {
		Text t = new Text();
		t.setId(1);
		t.setTitle("Apache Tomcat - Welcome!");
		t.setContent("Apache Tomcat, Tomcat, Apache, the Apache feather, and the Apache Tomcat project logo are trademarks of the Apache Software Foundation. Tomcat 7.0.33");
		t.setHref("www.tomcat.apache.org");
		// t.setfenlei("wenku");
		Solr s = new Solr();
		s.addField(t);

		Text t2 = new Text();
		t2.setId(2);
		t2.setTitle("tomcat_百度百科");
		t2.setContent("Tomcat是Apache 软件基金会（Apache Software Foundation）的Jakarta 项目中的一个核心项目，由Apache、Sun 和其他一些公司及个人共同开发而成。");
		t2.setHref("www.baike.baidu.com/view/10166.htm");
		s.addField(t2);
		
		Text t3 = new Text();
		t3.setId(3);
		t3.setTitle("windows tomcat配置大全[详细]_win服务器_脚本之家");
		t3.setContent("希望朋友在经过N次失败配置后.看到我这一篇文章应该会感动的.... 经常看到jsp的初学者问tomcat下如何配置jsp、servlet和bean的问题,于是总结了一下...1.到Tomcat的");
		t3.setHref("www.jb51.net/article/164...htm");
		s.addField(t3);
		
		Text t4 = new Text();
		t4.setId(4);
		t4.setTitle("tomcat 配置_百度知道");
		t4.setContent("问题描述: 我圆圆想在服务器上配置多个TOMCAT环境变量应该如何设置,请高手指教.最佳答案: 经常看到许多人问如何配置JDK和JSP,现在我把方法总结下,希望对大家有所帮助。 第一步:下载jdk和tomcat 第二步:安装和配置你的jdk和tomcat:执行jdk");
		t4.setHref("www.zhidao.baidu.com/question/265847...html");
		s.addField(t4);
	}

	@org.junit.Test
	public void fieldQuery() {
		Solr solr = new Solr();
		solr.solrQuery("oracle");
	}

	@org.junit.Test
	public void select_All() {
		Admin a = new Admin();
		a.findAllText();
	}
	@org.junit.Test
	public void sendToTK() {
		//String filePath = "E:\\linux 共享\\Solr+Nutch+Lucene相关资料\\原材料";
		 String filePath = "C:\\Users\\lenovo\\Desktop\\原材料\\郭春光.docx";
		Solr solr = new Solr();
		solr.sendToSolrTK(filePath);
	}

	@org.junit.Test
	public void delIndex() {
		Solr solr = new Solr();
		solr.delIndex("*:*");
	}

	@org.junit.Test
	public void spellcheck() {
		String term = "基";
		Solr solr = new Solr();
		Point<String, List<String>> p = null;
		p = solr.spellcheck(term);
		String x = p.getX();
		List<String> list = p.getY();
		 solr.createXml("WebRoot\\word.xml",list);
		/*System.out.println(x);
		System.out.println(list.size());*/
	}
	
	
	

}
