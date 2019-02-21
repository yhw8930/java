package xml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

public class StAXDemo {

	public static void main(String[] args) throws Exception {
		XMLInputFactory factory=XMLInputFactory.newFactory();
		InputStream is=new FileInputStream("videos.xml");
		XMLStreamReader r=factory.createXMLStreamReader(is);
		
		int e=r.getEventType();
		while(true){
			//int e=r.next();
			if(e==XMLStreamConstants.START_DOCUMENT){
				System.out.println("开始解析");
			}else if(e==XMLStreamConstants.START_ELEMENT){
				if(r.getLocalName().equals("title")){
					System.out.println(r.getElementText());
				}
				if(r.getLocalName().equals("video")){
					System.out.println(r.getAttributeValue(0));
				}
			}
			
			if(!r.hasNext()){
				break;
			}
			e=r.next();
			
		}
	}

}
