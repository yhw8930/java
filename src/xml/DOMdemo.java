package xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class DOMdemo {

	public static void main(String[] args) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		//factory.setValidating(true);
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		builder.setErrorHandler(new ErrorHandler() {
			
			@Override
			public void warning(SAXParseException exception) throws SAXException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void fatalError(SAXParseException exception) throws SAXException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void error(SAXParseException exception) throws SAXException {
				System.out.println(exception.getMessage());
				
			}
		});
		Document doc = builder.parse("videos.xml");
		System.out.println(doc.getChildNodes().getLength());
		Element root = doc.getDocumentElement();
		System.out.println(root.getNamespaceURI());
		System.out.println(root.getChildNodes().getLength());
		NodeList list = root.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			if (node instanceof Element) {
				System.out.println(node.getNodeName() + ","
						+ ((Element) node).getAttribute("id"));
				NamedNodeMap map = node.getAttributes();
				for (int j = 0; j < map.getLength(); j++) {
					System.out.println(map.item(j).getNodeName() + ","
							+ map.item(j).getNodeValue());
				}
			}
		}

		NodeList titles = doc.getElementsByTagName("title");
		for (int i = 0; i < titles.getLength(); i++) {
			Node node = titles.item(i);
			System.out.println(node.getFirstChild().getNodeValue());
		}

	}

}
