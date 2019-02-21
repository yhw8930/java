package xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.helpers.AttributesImpl;

public class CreateXMLDemo {

	public static void main(String[] args) throws Exception {
		// createByDOM();
//		createBySAX();
//		createByStAX();
		createByXSLT();
	}

	static void createByDOM() throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document d = builder.newDocument();

		Element root = d.createElement("emps");
		Element emp = d.createElement("emp");
		Text name = d.createTextNode("张三");

		d.appendChild(root);
		root.appendChild(emp);
		emp.appendChild(name);

		emp.setAttribute("id", "e001");

		Transformer t = TransformerFactory.newInstance().newTransformer();
		t.setOutputProperty(OutputKeys.INDENT, "yes");
		t.setOutputProperty(OutputKeys.METHOD, "xml");
		t.transform(new DOMSource(d), new StreamResult(new File("emps.xml")));

	}

	static void createBySAX() throws Exception {
		Result xml = new StreamResult(new FileOutputStream("emps2.xml"));
		SAXTransformerFactory sff = (SAXTransformerFactory) SAXTransformerFactory
				.newInstance();
		TransformerHandler th = sff.newTransformerHandler();
		th.setResult(xml);

		Transformer t = th.getTransformer();

		th.startDocument();
		AttributesImpl attrs = new AttributesImpl();
		attrs.addAttribute("", "", "id", "", "e001");
		th.startElement("", "", "person", null);
		th.startElement("", "", "emp", attrs);
		th.characters("张三".toCharArray(), 0, "张三".length());
		th.endElement("", "", "emp");
		th.endElement("", "", "person");
		th.endDocument();
	}

	static void createByStAX() throws Exception, XMLStreamException {
		XMLOutputFactory f=XMLOutputFactory.newFactory();
		XMLStreamWriter w=f.createXMLStreamWriter(new FileOutputStream("emps3.xml"));
		w.writeStartDocument("UTF-8", "1.0");
		w.writeStartElement("users");
		w.writeStartElement("user");
		w.writeAttribute("id", "u001");
		w.writeCharacters("Tom");
		w.writeEndElement();
		w.writeEndElement();
		w.writeEndDocument();
	}
	
	static void createByXSLT() throws Exception{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document d = builder.parse("myusers.xml");
		File file=new File("myusers.xsl");
		StreamSource ss=new StreamSource(file);
		Transformer t=TransformerFactory.newInstance().newTransformer(ss);
		t.transform(new DOMSource(d), new StreamResult(new File("emps4.html")));
	}

}
