package com.pps.xmlparser;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.pps.model.Channel;
import com.pps.model.DetailModel;
import com.pps.model.SubModel;

public class DetailXmLSax extends DefaultHandler {

	private List<DetailModel> detailLists=null;
	public List<DetailModel> getDetailLists() {
		return detailLists;
	}
	public List<SubModel> getSubLists() {
		return subLists;
	}
	private List<SubModel> subLists=null;
	private List<Channel> channels;
	//private Search mSearch;
	private Channel mChannel;
	private DetailModel detailModel;
	//private SubModel subModel;
	private String preTag_sub="";
	private String preTag_Subs="";
	private String preTag_Third="";
	
	
	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		detailLists=new ArrayList<DetailModel>();
		subLists=new ArrayList<SubModel>();
		channels=new ArrayList<Channel>();	
	}
	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		preTag_sub=qName;
		if("Subs".equals(qName))
		{
			preTag_Subs="Subs";
		}
		if("ThirdPart".equals(qName))
		{
			preTag_Third="ThirdPart";
		}
		if("Sub".equals(qName)&&(!"Subs".equals(preTag_Subs))&&(!"ThirdPart".equals(preTag_Third)))
		{
			
			//说明读取详细数据的sub标签
			detailModel=new DetailModel();
			detailModel.setTm(new Integer(attributes.getValue("tm")));
		}else if("Channel".equals(qName)&&(!"ThirdPart".equals(preTag_Third))){
			//解析节目的集合
			mChannel=new Channel();
			mChannel.setId(new Integer(attributes.getValue("id")));
			mChannel.setUrl(attributes.getValue("url"));
			mChannel.setGid(attributes.getValue("gid"));
			mChannel.setFotm(attributes.getValue("fotm"));
			mChannel.setLang(attributes.getValue("lang"));
			if(attributes.getValue("fsize")!=null){
			mChannel.setFsize(new Integer(attributes.getValue("fsize")));
			}else {
			mChannel.setFsize(0);	
			}
			if(attributes.getValue("dl")!=null){
			mChannel.setDl(new Byte(attributes.getValue("dl")));
			}else {
			mChannel.setDl(Byte.parseByte("1"));	
			}
			if(attributes.getValue("tm")!=null){
			mChannel.setTm(new Integer(attributes.getValue("tm")));
			}else {
			mChannel.setTm(0);	
			}
			if(attributes.getValue("ct")!=null){
			mChannel.setCt(new Integer(attributes.getValue("ct")));
			}else {
			mChannel.setCt(0);	
			}
			mChannel.setFmt(attributes.getValue("fmt"));
			mChannel.setDef(attributes.getValue("def"));
			if(attributes.getValue("bitrate")!=null){
			mChannel.setBitrate(new Integer(attributes.getValue("bitrate")));
			}else {
			mChannel.setBitrate(0);	
			}
			if(attributes.getValue("type")!=null)
			{
			mChannel.setType(attributes.getValue("type"));	
			}else {
			mChannel.setType(String.valueOf(0));	
			}
			mChannel.setTag(attributes.getValue("tag"));
			mChannel.setUrl_key(attributes.getValue("url_key"));
			if(attributes.getValue("vod")!=null){
			mChannel.setVid(new Integer(attributes.getValue("vid")));
			}
			else {
			mChannel.setVid(0);	
			}
			mChannel.setWebURL(attributes.getValue("webURL"));
			if(attributes.getValue("pfv2mp4")!=null)
			{
			mChannel.setPfv2mp4(new Byte(attributes.getValue("pfv2mp4")));
			}else {
			mChannel.setPfv2mp4((byte)1);	
			}
		}else if ("Channels".equals(qName)&&(!"ThirdPart".equals(preTag_Third))) {
			//解析该节目的一共总集数
			if(attributes.getValue("Total")!=null){
			detailModel.setTotal(new Integer(attributes.getValue("Total")));
			}else {
		    detailModel.setTotal(0);
			}
		}else if("Sub".equals(qName)&&"Subs".equals(preTag_Subs)) {
			//解析相关的节目//暂时不解析
		
		}else if("Sub".equals(qName)&&"Subs".equals(preTag_Subs)&&"ThirdPart".equals(preTag_Third)){
			//地方平台节目，暂时不解析
		}		
		
	}
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {	
		super.endElement(uri, localName, qName);
		preTag_sub="";
		if("Sub".equals(qName)&&(!"Subs".equals(preTag_Subs)))
		{
			detailLists.add(detailModel);
		}else if ("Channels".equals(qName)) {
			detailModel.setChannels(channels);
			preTag_Subs="";
		}else if ("Sub".equals(qName)&&"Subs".equals(preTag_Subs)) {
		    //相关节目暂时不解析
		}else if ("Subs".equals(qName)) {
			preTag_Subs="";
		}else if ("ThirdPart".equals(qName)) {
		    preTag_Third="";	
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		super.characters(ch, start, length);
		if(!"Subs".equals(preTag_Subs)&&!"ThirdPart".equals(preTag_Third))
		{
			//表示不在下面相关节目，
			if("id".equals(preTag_sub))
			{
				detailModel.setId(new Integer(new String(ch, start, length)));
			}else if ("inton".equals(preTag_sub)) {
				detailModel.setInton(new String(ch, start, length));
			}else if ("type".equals(preTag_sub)) {
				detailModel.setType(new String(ch, start, length));
			}else if ("region".equals(preTag_sub)) {
				detailModel.setRegion(new String(ch, start, length));
			}else if ("dirt".equals(preTag_sub)) {
				detailModel.setDirt(new String(ch, start, length));
			}else if ("actor".equals(preTag_sub)) {
				detailModel.setActor(new String(ch, start, length));
			}else if ("vote_count".equals(preTag_sub)) {
				detailModel.setVote_count(new Integer(new String(ch, start, length)));
			}else if ("block".equals(preTag_sub)) {
				detailModel.setBlock(new String(ch, start, length));
			}else if ("wlock".equals(preTag_sub)) {
				detailModel.setWlock(new String(ch, start, length));
			}else if ("name".equals(preTag_sub)) {
				detailModel.setName(new String(ch, start, length));
			}else if ("vote".equals(preTag_sub)) {
				detailModel.setVote(Float.valueOf(new String(ch, start, length)));
			}else if ("bkid".equals(preTag_sub)) {
				detailModel.setBkid(new Integer(new String(ch, start, length)));
			}else if ("multi".equals(preTag_sub)) {
				detailModel.setMulti(new Byte(new String(ch, start, length)));
			}else if ("vip".equals(preTag_sub)) {
				detailModel.setVip(new Integer(new String(ch, start, length)));
			}else if ("vopt".equals(preTag_sub)) {
				detailModel.setVopt(new Integer(new String(ch, start, length)));
			}else if ("popt".equals(preTag_sub)) {
				detailModel.setPopt(new Byte(new String(ch, start, length)));
			}else if ("vlevel".equals(preTag_sub)) {
				detailModel.setVlevel(new Integer(new String(ch, start, length)));
			}else if ("ct".equals(preTag_sub)) {
				detailModel.setCt(new Integer(new String(ch, start, length)));
			}else if ("img".equals(preTag_sub)) {
				detailModel.setImg(new String(ch, start, length));
			}else if ("followable".equals(preTag_sub)) {
				detailModel.setFollowable(new Byte(new String(ch, start, length)));
			}else if ("fn".equals(preTag_sub)) {
				detailModel.setFn(new Byte(new String(ch, start, length)));
			}else if ("Channel".equals(preTag_sub)) {
				mChannel.setStroy(new String(ch, start, length));
				channels.add(mChannel);
			}		
		}else {
			//相关节目。。。。
		}
	}
  
}
