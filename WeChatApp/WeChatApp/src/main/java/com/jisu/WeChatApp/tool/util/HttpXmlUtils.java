package com.jisu.WeChatApp.tool.util;

import com.jisu.WeChatApp.pojo.Transfers;
import com.thoughtworks.xstream.XStream;

import com.thoughtworks.xstream.core.util.QuickWriter;

import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import com.thoughtworks.xstream.io.naming.NoNameCoder;

import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;

import com.thoughtworks.xstream.io.xml.XppDriver;

import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.w3c.dom.Node;

import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;

import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.transform.OutputKeys;

import javax.xml.transform.Transformer;

import javax.xml.transform.TransformerFactory;

import javax.xml.transform.dom.DOMSource;

import javax.xml.transform.stream.StreamResult;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import java.io.Writer;

import java.util.*;

public class HttpXmlUtils {

	private static XStream xStream = new XStream(new XppDriver(new NoNameCoder()) {

		@Override

		public HierarchicalStreamWriter createWriter(Writer out) {

			return new PrettyPrintWriter(out) {

				// 对所有xml节点的转换都增加CDATA标记

				boolean cdata = true;

				@Override

				@SuppressWarnings("rawtypes")

				public void startNode(String name, Class clazz) {

					super.startNode(name, clazz);

				}

				@Override

				public String encodeNode(String name) {

					return name;

				}

				@Override

				protected void writeText(QuickWriter writer, String text) {

					if (cdata) {

						writer.write("<![CDATA[");

						writer.write(text);

						writer.write("]]>");

					} else {

						writer.write(text);

					}

				}

			};

		}

	});

	/**
	 * 
	 * 构造企业付款xml参数
	 * 
	 * @return
	 * 
	 */

	public static String transferXml(Transfers transfers) {
		xStream.autodetectAnnotations(true);
		xStream.alias("xml", Transfers.class);
		return xStream.toXML(transfers);
	}

	public static Map<String, String> xmlToMap(String strXML) throws Exception {

		Map<String, String> data = new HashMap<String, String>();

		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

		InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));

		org.w3c.dom.Document doc = documentBuilder.parse(stream);

		doc.getDocumentElement().normalize();

		NodeList nodeList = doc.getDocumentElement().getChildNodes();

		for (int idx = 0; idx < nodeList.getLength(); ++idx) {

			Node node = nodeList.item(idx);

			if (node.getNodeType() == Node.ELEMENT_NODE) {

				org.w3c.dom.Element element = (org.w3c.dom.Element) node;

				data.put(element.getNodeName(), element.getTextContent());

			}

		}

		try {

			stream.close();

		} catch (Exception ex) {

			// do nothing

		}

		return data;

	}

	public static String mapToXml(Map<String, String> data) throws Exception {

		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

		org.w3c.dom.Document document = documentBuilder.newDocument();

		org.w3c.dom.Element root = document.createElement("xml");

		document.appendChild(root);

		for (String key : data.keySet()) {

			String value = data.get(key);

			if (value == null) {

				value = "";

			}

			value = value.trim();

			org.w3c.dom.Element filed = document.createElement(key);

			filed.appendChild(document.createTextNode(value));

			root.appendChild(filed);

		}

		TransformerFactory tf = TransformerFactory.newInstance();

		Transformer transformer = tf.newTransformer();

		DOMSource source = new DOMSource(document);

		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

		transformer.setOutputProperty(OutputKeys.INDENT, "yes");

		StringWriter writer = new StringWriter();

		StreamResult result = new StreamResult(writer);

		transformer.transform(source, result);

		String output = writer.getBuffer().toString(); // .replaceAll("\n|\r", "");

		try {

			writer.close();

		}

		catch (Exception ignored) {

		}

		return output;

	}

	/*
	 * 
	 * 将SortedMap<Object,Object> 集合转化成 xml格式
	 * 
	 */

	public static String getRequestXml(SortedMap<Object, Object> parameters) {

		StringBuffer sb = new StringBuffer();

		sb.append("<xml>");

		Set es = parameters.entrySet();

		for (Object e : es) {

			Map.Entry entry = (Map.Entry) e;

			String k = (String) entry.getKey();

			String v = (String) entry.getValue();

			if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k) || "sign".equalsIgnoreCase(k)) {

				sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");

			} else {

				sb.append("<" + k + ">" + v + "</" + k + ">");

			}

		}

		sb.append("</xml>");

		return sb.toString();

	}

	/**
	 * 
	 * 解析申请退款之后微信返回的值并进行存库操作
	 * 
	 * @throws IOException
	 * 
	 * @throws JDOMException
	 * 
	 */

	public static Map<String, String> parseRefundXml(String refundXml) throws JDOMException, IOException {
		//ParseXMLUtils.jdomParseXml(refundXml);
		StringReader read = new StringReader(refundXml);
		// 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
		InputSource source = new InputSource(read);
		// 创建一个新的SAXBuilder
		SAXBuilder sb = new SAXBuilder();
		// 通过输入源构造一个Document
		org.jdom.Document doc;
		doc = (org.jdom.Document) sb.build(source);
		org.jdom.Element root = doc.getRootElement();// 指向根节点
		List<org.jdom.Element> list = root.getChildren();
		Map<String, String> refundOrderMap = new HashMap<String, String>();
		if (list != null && list.size() > 0) {
			for (org.jdom.Element element : list) {
				refundOrderMap.put(element.getName(), element.getText());
			}
			return refundOrderMap;
		}
		return null;
	}

}