package com.pps.xmlparser;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


import com.pps.model.Search;
import com.pps.model.SubModel;

/**
 * 进行使用Sax解析XML数据的工厂类
 * @author jiangqq
 * @time 2013/06/10
 */
public class ListXMLContentHandler extends DefaultHandler {
	private String preTag;
 	private List<SubModel> mLists;
	/**
	 * @return the mLists
	 */
	 public List<SubModel> getmLists() {
		return mLists;
	}

	private SubModel sub;
    private Search search;
	
	/* (non-Javadoc)
	 * 用来处理在XML文件中读到的内容，第一个参数为文件的字符串内容，
	 * 后面两个参数是读到的字符串在这个数组中的起始位置和长度，
	 * 使用new String(ch,start,length)就可以获取内容。
	 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
	 */
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		super.characters(ch, start, length);
		
		if("wl".equals(preTag))
		{
			if(sub!=null){
			sub.setWl(new String(ch, start, length));}
		}else if ("bl".equals(preTag)) {
			if(sub!=null){
			sub.setBl(new String(ch, start, length));}
		}else if("plat_wl".equals(preTag)) {
			if(sub!=null){
			sub.setPlat_wl(new String(ch, start, length));}
		}else if ("plat_bl".equals(preTag)) {
			if(sub!=null){
			sub.setPlat_bl(new String(ch, start, length));}
		}
		
	}

	/* (non-Javadoc)
	 * 当文档结束的时候，调用这个方法，做一些善后的工作。
	 * @see org.xml.sax.helpers.DefaultHandler#endDocument()
	 */
	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
		
		
	}

	/* (non-Javadoc)
	 * 在遇到结束标签的时候，调用这个方法。
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);
		preTag="";
		if("sub".equals(qName)||"Thirdsub".equals(qName))
		{
			mLists.add(sub);
			
		}
		
		
	}

	/* (non-Javadoc)
	 * 当遇到文档的开头的时候，调用这个方法，做一些预处理的工作。
	 * @see org.xml.sax.helpers.DefaultHandler#startDocument()
	 */
	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		
		mLists=new ArrayList<SubModel>();
		
	}

	/* (non-Javadoc)
	 * 当读到一个开始标签的时候，会触发这个方法。 namespaceURI就是命名空间，
	 * localName是不带命名空间前缀的标签名，qName是带命名空间前缀的标签名。
	 * 通过atts可以得到所有的属性名和相应的值。
	 * 要注意的是SAX中一个重要的特点就是它的流式处理，
	 * 当遇到一个标签的时候，它并不会纪录下以前所碰到的标签，
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		if("sub".equals(qName)||"Thirdsub".equals(qName))
		{
			sub=new SubModel();
			sub.setId(new Integer(attributes.getValue("id")));
			sub.setName(attributes.getValue("name"));
			sub.setBkid(new Integer(attributes.getValue("bkid")));
			sub.setVm(new Float(attributes.getValue("vm")));
			sub.setImg(attributes.getValue("img"));
			
			if(null==attributes.getValue("lt"))
			{
			   sub.setLt("Z");	
			}else {
			   sub.setLt(attributes.getValue("lt"));
			}
			
			
			sub.setNt(new Byte(attributes.getValue("nt")));
			sub.setMulti(new Byte(attributes.getValue("multi")));
		    if(null==attributes.getValue("sc"))
		    {
		    	sub.setSc(0);
		    }else {
				sub.setSc(new Integer(attributes.getValue("sc")));
			}
			
			if(null==attributes.getValue("on"))
			{
				sub.setOn(0);
			}else {
				sub.setOn(new Integer(attributes.getValue("on")));
			}
			if(null==attributes.getValue("vip"))
			{
				sub.setVip(0);
			}else {
				sub.setVip(new Integer(attributes.getValue("vip")));
			}
			
			if(null==attributes.getValue("vopt"))
			{
				sub.setVip(0);
			}else {
				sub.setVopt(new Byte(attributes.getValue("vopt")));
			}
		
			if(null==attributes.getValue("popt"))
			{
				sub.setPopt(0);
			}else {
				sub.setPopt(new Integer(attributes.getValue("popt")));
			}
			if(null==attributes.getValue("vlevel"))
			{
				sub.setVlevel(0);
			}else {
				sub.setVlevel(new Integer(attributes.getValue("vlevel")));
			}
			if(null==attributes.getValue("tm"))
			{
				sub.setTm(0);
			}else {
				sub.setTm(new Integer(attributes.getValue("tm")));
			}
			if(attributes.getValue("tp")==null)
			{
				sub.setTp("未知类型...");
			}else {
				sub.setTp(attributes.getValue("tp"));
			}
			
			
			//Log.v("jiangqq", "剧情的名字:"+sub.getName()+",筛选词为:"+sub.getTp());
			sub.setP(attributes.getValue("p"));
			//System.out.println("sub="+sub.toString());
		}else if("search".equals(qName))
	    {
	    	
	    	search=new Search();
	    	search.setPt(attributes.getValue("pt"));
	    	if(attributes.getValue("pt")==null)
	    	{
	    		search.setPt("无年份");
	    	}else {
				search.setPt(attributes.getValue("pt"));
			}
	    	search.setTp(attributes.getValue("tp"));
	    	sub.setSearch(search);
	    }	
	    preTag=qName;
	}
 }

