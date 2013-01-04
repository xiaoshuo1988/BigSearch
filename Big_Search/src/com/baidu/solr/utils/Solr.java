package com.baidu.solr.utils;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse.Suggestion;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.baidu.solr.model.Text;

public class Solr {

	private final static String URL = "http://localhost:8080/solr";
	private int count = 0;

	private HttpSolrServer server = null;

	/**
	 * 添加索引
	 * 
	 * @param text
	 */
	public void addField(Text text) {
		init();
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", String.valueOf(text.getId()));
		doc.addField("s_title", text.getTitle());
		doc.addField("s_content", text.getContent());
		doc.addField("s_href", text.getHref());
		try {
			server.add(doc);
			server.commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除索引
	 * 
	 * @param command
	 *            删除索引的具体命令，比如command="*:*"，将删除所有索引
	 */
	public void delIndex(String command) {
		try {
			init();
			server.deleteByQuery(command);
			if (server != null) {
				server.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 此方法用于过滤文档，将格式不符合要求的文档过滤掉，目前允许以下文档通过："doc", "docx", "ppt", "pdf", "pps",
	 * "pptx","txt", "csv", "json", "xml"，这几种格式的文档通过.
	 * 
	 * @param file
	 *            待过滤的文件
	 * @return 如果文档符合格式要求则返回true，否则返回false.
	 */
	private boolean fomatFilter(File file) {
		String filename = file.getName();
		String[] filterList = { "doc", "docx", "ppt", "pdf", "pps", "pptx",
				"txt", "csv", "json", "xml", "html", "htm" };
		if (file.isHidden()) {
			return false;
		}
		for (String suffix : filterList) {
			if (filename.toLowerCase().endsWith(suffix)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 上传目录，将目录中的文档递归上传，内部条用了indexFile(File file, String solrId)
	 * 
	 * @param dir
	 *            文件的目录
	 * @throws IOException
	 *             IO异常
	 * @throws SolrServerException
	 *             Server异常
	 */
	private void indexFile(File dir) throws IOException, SolrServerException {
		if (dir.isDirectory()) {
			for (File file : dir.listFiles()) {
				if (file.isFile()) {
					String solrId = file.getName();
					indexFile(file, solrId);
				} else {
					indexFile(file);
				}
			}
		}
	}

	/**
	 * 上传文档
	 * 
	 * @param file
	 *            待上传的文件
	 * @param solrId
	 *            以文件名建立的id
	 * @throws IOException
	 *             IO异常
	 * @throws SolrServerException
	 *             Server异常
	 */
	private void indexFile(File file, String solrId) throws IOException,
			SolrServerException {
		// 过滤文档，不合格的文档不往下执行
		if (fomatFilter(file)) {
			ContentStreamUpdateRequest up = new ContentStreamUpdateRequest(
					"/update/extract");
			up.addFile(file, solrId);
			// 为document添加指定的field，fieldname为solrId(默认的是这篇文档的名称)
			up.setParam("literal.id", solrId);
			server.request(up);
			// 计数器记录上传了多少篇文档
			count++;
			System.out.println("文件 " + file.getName() + " 上传成功~");
		} else {
			System.out.println("文件" + file.getName() + " 格式有误~");
		}
	}

	public void init() {
		server = new HttpSolrServer(URL);
	}

	/**
	 * 上传整个目录中的所有文件内部调用了indexFile方法，并记录上传文档数目和上传时间,单位为毫秒
	 * 
	 * @param filePath
	 *            待上传的文件目录
	 */
	public void sendToSolrTK(String filePath) {
		init();
		File file = new File(filePath);
		Long startTime = System.currentTimeMillis();
		try {
			if (file.exists()) {
				if (file.isFile()) {
					String solrId = file.getName();
					indexFile(file, solrId);
				} else {
					indexFile(file);
				}
				Long endTime = System.currentTimeMillis();
				System.out.println("总上传时间为：" + (endTime - startTime) + " ms");
				System.out.println("总共上传了 " + count + " 篇文档~");
			} else {
				System.out.println("未找到指定文件夹" + file.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (server != null) {
				try {
					server.commit();
				} catch (SolrServerException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void solrQuery(String queryWord) {
		init();
		try {
			// 创建Solr查询
			SolrQuery query = new SolrQuery(queryWord);
			query.setHighlight(true).setHighlightSnippets(1);
			query.setParam("hl.fl", "chn_content");
			// query.setStart(0);
			// query.setRows(3);
			QueryResponse resp = server.query(query);
			// 把查询出的结果放进SolrDocumentList里面。
			SolrDocumentList sdl = resp.getResults();
			System.out.println(sdl.getNumFound());
			for (SolrDocument sd : sdl) {
				// System.out.println(sd.getFieldValue("title")+","+sd.getFieldValue("content"));
				String id = (String) sd.getFieldValue("id");
				System.out.println(id);
				System.out.println(resp.getHighlighting().get(id)
						.get("content"));
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		}

		// ModifiableSolrParams solrparams=new
		/*
		 * SolrQuery query=new SolrQuery(queryWord);
		 * query.setHighlight(true).setHighlightSnippets(1);
		 * query.setParam("hl.fl", "title","content","href");
		 * 
		 * QueryResponse resp=null; try {
		 * 
		 * resp=server.query(query);
		 * 
		 * } catch (SolrServerException e) { e.printStackTrace(); }
		 * SolrDocumentList sdl= resp.getResults(); Long
		 * totalNums=sdl.getNumFound(); List<Text> list=new ArrayList<Text>();
		 * for(SolrDocument sd:sdl) { Text t=new Text(); List<Integer> id_list =
		 * (ArrayList<Integer>)sd.getFieldValue("id"); List<String> title_list =
		 * (ArrayList<String>)sd.getFieldValue("title"); List<String>
		 * content_list = (ArrayList<String>)sd.getFieldValue("content");
		 * 
		 * 
		 * 
		 * String id = (String)sd.getFieldValue("id");
		 * //System.out.println("id de value="+id);
		 * System.out.println(resp.getHighlighting
		 * ().get("16").get("content")+" aaaaa"); }
		 */
		// return list;
	}

	/**
	 * 这个方法用于spellcheck功能，返回的结果包括suggestion和collate,大部分参数配置都隐藏在solrconfig.xml中.
	 * 
	 * @param term
	 *            待搜索的关键字
	 */
	public Point<String, List<String>> spellcheck(String term) {
		System.out.println("spellcheck test"+term);
		init();
		SolrQuery query = new SolrQuery();
		// 选择合适的handler
		query.set("qt", "/spell/leven");
		// 搜索什么关键字
		query.set("q", term);
		QueryResponse rsp = null;
		try {
			rsp = server.query(query);
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		// 拿到spellcheck的响应对象
		SpellCheckResponse spell = rsp.getSpellCheckResponse();
		Suggestion sug = spell.getSuggestion(term);
		List<String> list = sug.getAlternatives();
		/*
		 * System.out.println("suggestion: "); System.out.print("\t"); for
		 * (String suggest : list) { System.out.print(suggest + "  "); }
		 * System.out.println();
		 */
		String collate = spell.getCollatedResult();
		// System.out.println("collate: \n\t" + collate);
		return new Point<String, List<String>>(collate, list);
	}
	Document doc=null;

	
	public void createXMLFile(List<String> list){
	
		
		String word=null;
		Iterator<String> it=list.iterator();
		//创建Document
		 doc=DocumentHelper.createDocument();
		Element rootElement=doc.addElement("words");
		while(it.hasNext()){
			word=it.next();
			Element ele=rootElement.addElement("word");
			ele.setText(word);
		}
		
		
	      
	   
		
	}
	
	
	
	 /**
     * 创建xml文件
     * @param fileName
     */ 
    public void createXml(String fileName,List<String> list)  
    {    
    	createXMLFile(list);
        try {    
            //Writer fileWriter=new FileWriter(fileName);
        	
            OutputFormat outFormat = OutputFormat.createPrettyPrint();
            outFormat.setEncoding("UTF-8");
            outFormat.setTrimText(false);
            
            XMLWriter xmlWriter=new XMLWriter(new FileOutputStream(fileName),outFormat); 
            
            xmlWriter.write(doc);   //写入文件中 
            xmlWriter.close();   
            } catch (IOException e) {    
                System.out.println(e.getMessage());    
            }    
    }    
	
	
	
	
}
