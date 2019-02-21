package xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XPathDemo {

	public static void main(String[] args) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		// factory.setValidating(true);
		//factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse("videos.xml");

		XPathFactory pathFactory = XPathFactory.newInstance();
		XPath path = pathFactory.newXPath();

		NodeList list = (NodeList) path.evaluate("videos/video/title/text()",
				doc, XPathConstants.NODESET);
		for(int i=0;i<list.getLength();i++){
			Node node=list.item(i);
			System.out.println(node.getNodeValue());
		}
	}

}
