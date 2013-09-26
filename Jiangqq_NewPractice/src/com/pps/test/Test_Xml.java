package com.pps.test;



import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.pps.model.SubModel;
import com.pps.xmlparser.ListXMLContentHandler;

/**
 * 测试解析XML格式数据
 * @author jiangqq
 */
public class Test_Xml {
  public static void main(String[] args) throws Exception {
//	   File file=new File("d:\\abc.xml");
	   InputStream inputStream=Test_Xml.class.getResourceAsStream("141.xml");
//      byte b[]=new byte[1024];     //创建合适文件大小的数组  
//      inputStream.read(b);    //读取文件中的内容到b[]数组  
//      inputStream.close();  
//      System.out.println(new String(b));
	  List<SubModel> mList=new Test_Xml().readSAXXML(inputStream);
	 for (SubModel subModel : mList) {
		System.out.println(subModel.toString());
	}
	 System.out.println(mList.size());
  }
  
  /**
   * 返回解析成功的对象
   * @param inputStream
   * @return
   */
  public List<SubModel> readSAXXML(InputStream inputStream) {  
      try {  
          SAXParserFactory spf = SAXParserFactory.newInstance();  
          SAXParser parser = spf.newSAXParser();  
          ListXMLContentHandler handler = new ListXMLContentHandler();  
          parser.parse(inputStream, handler);  
          inputStream.close();  
          return handler.getmLists();
      } catch (Exception e) {  
        e.printStackTrace();
      }  
      return null;  
  }
}
