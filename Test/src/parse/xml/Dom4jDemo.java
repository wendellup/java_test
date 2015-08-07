package parse.xml;

import java.io.File;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author Alexia
 * 
 * Dom4j 解析XML文档
 */
public class Dom4jDemo implements XmlDocument {

    public void parserXml(String fileName) {
        File inputXml = new File(fileName);
        SAXReader saxReader = new SAXReader();

        try {
            Document document = saxReader.read(inputXml);
            Element users = document.getRootElement();
            for (Iterator i = users.elementIterator(); i.hasNext();) {
                Element user = (Element) i.next();
                System.out.println(user.getName() + ":" + user.getText());
//                for (Iterator j = user.elementIterator(); j.hasNext();) {
//                    Element node = (Element) j.next();
//                    System.out.println(node.getName() + ":" + node.getText());
//                }
//                System.out.println();
            }
        } catch (DocumentException e) {
        	System.out.println(e);
        }
    }
    
    public static void main(String[] args) {
//		String xmlStrFile = new Dom4jDemo().getClass().getResource("/xmlTest.xml").getPath();;
    	String xmlStrFile = Dom4jDemo.class.getResource("/xmlTest.xml").getPath();;
		new Dom4jDemo().parserXml(xmlStrFile);
		
	}
    
    

}