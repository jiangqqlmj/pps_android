package com.pps.xmlparser;


import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.pps.model.Channel;
import com.pps.model.DetailModel;

public class DetailXMLContentHandler extends DefaultHandler {

	private String preTag;
	private List<DetailModel> mLists;
	private List<Channel> channels;
	private DetailModel detailModel;
	private Channel mChannel;
	
	public List<DetailModel> getmLists() {
		return mLists;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		System.out.println("文档解析开始...");
		mLists=new ArrayList<DetailModel>();
		channels=new ArrayList<Channel>();
		
	}

	@Override
	public void endDocument() throws SAXException {
		
		super.endDocument();
		System.out.println("文件解析结束");
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {	
		super.startElement(uri, localName, qName, attributes);
		System.out.println("开始解析元素....");
		System.out.println("localName = "+localName+",qName = "+qName);
		if("Sub".equals(qName))
		{
			detailModel=new DetailModel();
			detailModel.setTm(new Integer(attributes.getValue("tm")));
		}else if("Channel".equals(qName)){
			mChannel=new Channel();
			mChannel.setId(new Integer(attributes.getValue("id")));
			mChannel.setUrl(attributes.getValue("url"));
			mChannel.setGid(attributes.getValue("gid"));
			mChannel.setFotm(attributes.getValue("fotm"));
			mChannel.setLang(attributes.getValue("lang"));
			mChannel.setFsize(new Integer(attributes.getValue("fsize")));
			mChannel.setDl(new Byte(attributes.getValue("dl")));
			mChannel.setTm(new Integer(attributes.getValue("tm")));
			mChannel.setCt(new Integer(attributes.getValue("ct")));
			mChannel.setFmt(attributes.getValue("fmt"));
			mChannel.setDef(attributes.getValue("def"));
			mChannel.setTag(attributes.getValue("tag"));
			mChannel.setFn(attributes.getValue("fn"));
			mChannel.setPfv2mp4(new Byte(attributes.getValue("pfv2mp4")));
		}else if("Channels".equals(qName))
		{
			detailModel.setTotal(new Integer(attributes.getValue("Total")));
		}
		preTag=qName;
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		
		super.endElement(uri, localName, qName);
		preTag="";
		if("Sub".equals(qName))
		{
			mLists.add(detailModel);
			System.out.println(detailModel.toString());
		}else if ("Channels".equals(qName)) {
			detailModel.setChannels(channels);
		}
		System.out.println("一个元素解析完成");
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		super.characters(ch, start, length);
		System.out.println("字符串解析");
		    if("id".equals(preTag))
	     	{
		        detailModel.setId(new Integer(new String(ch, start, length)));
			}else if("inton".equals(preTag)) {
				detailModel.setInton(new String(ch, start, length));
			}else if ("type".equals(preTag)) {
				detailModel.setType(new String(ch, start, length));
			}else if ("region".equals(preTag)) {
				detailModel.setRegion(new String(ch, start, length));
			}else if ("dirt".equals(preTag)) {
				detailModel.setDirt(new String(ch, start, length));
			}else if ("actor".equals(preTag)) {
				detailModel.setActor(new String(ch, start, length));
			}else if ("vote_count".equals(preTag)) {
				detailModel.setVote_count(new Integer(new String(ch, start, length)));
			}else if ("block".equals(preTag)) {
				detailModel.setBlock(new String(ch, start, length));
			}else if ("wlock".equals(preTag)) {
				detailModel.setWlock(new String(ch, start, length));
			}else if ("name".equals(preTag)) {
				detailModel.setName(new String(ch, start, length));
			}else if ("vote".equals(preTag)) {
				detailModel.setVote(Float.valueOf(new String(ch, start, length)));
			}else if ("bkid".equals(preTag)) {
				detailModel.setBkid(new Integer(new String(ch, start, length)));
			}else if ("multi".equals(preTag)) {
				detailModel.setMulti(new Byte(new String(ch, start, length)));
			}else if ("vip".equals(preTag)) {
				detailModel.setVip(new Integer(new String(ch, start, length)));
			}else if ("vopt".equals(preTag)) {
				detailModel.setVopt(new Integer(new String(ch, start, length)));
			}else if ("popt".equals(preTag)) {
				detailModel.setPopt(new Byte(new String(ch, start, length)));
			}else if ("vlevel".equals(preTag)) {
				detailModel.setVlevel(new Integer(new String(ch, start, length)));
			}else if ("ct".equals(preTag)) {
				detailModel.setCt(new Integer(new String(ch, start, length)));
			}else if ("img".equals(preTag)) {
				detailModel.setImg(new String(ch, start, length));
			}else if ("followable".equals(preTag)) {
				detailModel.setFollowable(new Byte(new String(ch, start, length)));
			}else if ("fn".equals(preTag)) {
				detailModel.setFn(new Byte(new String(ch, start, length)));
			}else if ("Channel".equals(preTag)) {
				mChannel.setStroy(new String(ch, start, length));
				channels.add(mChannel);
			}
		}
}
