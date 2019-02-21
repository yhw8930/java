package xml;

import java.io.File;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXDemo {

	public static void main(String[] args) throws Exception {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser p = factory.newSAXParser();
		p.parse(new File("videos.xml"), new DefaultHandler() {
			String tag;

			@Override
			public void startDocument() throws SAXException {
				System.out.println("开始解析...");
			}

			@Override
			public void endDocument() throws SAXException {
				System.out.println("结束解析");
			}

			@Override
			public void characters(char[] ch, int start, int length)
					throws SAXException {
				if ("title".equals(tag)) {
					String s = new String(ch, start, length);
					System.out.println(s);
				}
				tag = null;
			}

			@Override
			public void startElement(String uri, String localName,
					String qName, Attributes attributes) throws SAXException {
				if ("title".equals(qName)) {
					tag = "title";
				}
				if ("laotan:video".equals(qName)) {
					System.out.println(attributes.getValue("id"));
				}
			}
		});
	}
}
