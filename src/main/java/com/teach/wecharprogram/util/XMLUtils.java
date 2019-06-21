/**
 * Copyright 2009-2019 Gwenhaël Pasquiers
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.teach.wecharprogram.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.teach.wecharprogram.entity.other.ElementNameConverter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Helper class that can be used for JSON -> XML and XML -> JSON transformation.
 * <p>
 * It is mostly useful to go back to JSON after using JsonXmlReader and
 * eventually modifying the DOM using XPath. This class needs the type attribute
 * so it can only convert back XML created from JSON with addTypeAttributes
 * true.
 *
 * <pre>
 *  Node node = convertToDom(myJSONString, "", true, "root");
 *  // ...
 *  String json = convertToJson(node);
 * </pre>
 */
public class XMLUtils {

    public static DocumentBuilder newDocumentBuilder() throws ParserConfigurationException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        documentBuilderFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        documentBuilderFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        documentBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        documentBuilderFactory.setXIncludeAware(false);
        documentBuilderFactory.setExpandEntityReferences(false);

        return documentBuilderFactory.newDocumentBuilder();
    }

    public static Document newDocument() throws ParserConfigurationException {
        return newDocumentBuilder().newDocument();
    }

    public enum TYPE {
        STRING, INT, FLOAT, BOOLEAN, NULL, ARRAY, OBJECT
    }

    /**
     * Helper method to convert JSON string to XML DOM
     *
     * @param json               String containing the json document
     * @param namespace          Namespace that will contain the generated dom nodes
     * @param addTypeAttributes  Set to true to generate type attributes
     * @param artificialRootName Name of the artificial root element node
     * @return Document DOM node.
     * @throws TransformerConfigurationException
     */
    public static Node convertToDom(final String json, final String namespace, final boolean addTypeAttributes, final String artificialRootName) throws TransformerConfigurationException, TransformerException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        InputSource source = new InputSource(new StringReader(json));
        DOMResult result = new DOMResult();
        transformer.transform(new SAXSource(new JsonXmlReader(namespace, addTypeAttributes, artificialRootName), source), result);
        return result.getNode();
    }

    /**
     * Simpler helper method to convert DOM node back to JSON. The node MUST
     * have the "type" attributes (generated with addTypeAttributes flag set as
     * true).
     *
     * @param node The DOM Node
     * @return The JSON string
     * @throws IOException
     */
    public static String convertToJson(Node node) throws IOException {
        try (StringWriter writer = new StringWriter(); JsonGenerator generator = new JsonFactory().createGenerator(writer)) {
            convertToJson(node, generator, name -> name);
            return writer.toString();
        }
    }

    /**
     * More complete helper method to convert DOM node back to JSON.The node
     * MUST have the "type" attributes (generated with addTypeAttributes flag
     * set as true).This method allows to customize the JsonGenerator.
     *
     * @param node      The DOM Node
     * @param generator A configured JsonGenerator
     * @param converter Converter to convert elements names from XML to JSON
     * @throws IOException
     */
    public static void convertToJson(Node node, JsonGenerator generator, ElementNameConverter converter) throws IOException {
        Element element;
        if (node instanceof Document) {
            element = ((Document) node).getDocumentElement();
        } else if (node instanceof Element) {
            element = (Element) node;
        } else {
            throw new IllegalArgumentException("Node must be either a Document or an Element");
        }

        TYPE type = toTYPE(element.getAttribute("type"));
        switch (type) {
            case OBJECT:
            case ARRAY:
                convertElement(generator, element, true, converter);
                break;
            default:
                throw new RuntimeException("invalid root type [" + type + "]");
        }
        generator.close();
    }

    /**
     * Convert a DOM element to Json, with special handling for arrays since arrays don't exist in XML.
     *
     * @param generator
     * @param element
     * @param isArrayItem
     * @throws IOException
     */
    private static void convertElement(JsonGenerator generator, Element element, boolean isArrayItem, ElementNameConverter converter) throws IOException {
        TYPE type = toTYPE(element.getAttribute("type"));
        String name = element.getTagName();

        if (!isArrayItem) {
            generator.writeFieldName(converter.convertName(name));
        }

        switch (type) {
            case OBJECT:
                generator.writeStartObject();
                convertChildren(generator, element, false, converter);
                generator.writeEndObject();
                break;
            case ARRAY:
                generator.writeStartArray();
                convertChildren(generator, element, true, converter);
                generator.writeEndArray();
                break;
            case STRING:
                generator.writeString(element.getTextContent());
                break;
            case INT:
            case FLOAT:
                generator.writeNumber(new BigDecimal(element.getTextContent()));
                break;
            case BOOLEAN:
                generator.writeBoolean(Boolean.parseBoolean(element.getTextContent()));
                break;
            case NULL:
                generator.writeNull();
                break;
        }
    }

    /**
     * Method to recurse within children elements and convert them to JSON too.
     *
     * @param generator
     * @param element
     * @param isArray
     * @throws IOException
     */
    private static void convertChildren(JsonGenerator generator, Element element, boolean isArray, ElementNameConverter converter) throws IOException {
        NodeList list = element.getChildNodes();
        int len = list.getLength();
        for (int i = 0; i < len; i++) {
            Node node = list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                convertElement(generator, (Element) node, isArray, converter);
            }
        }
    }

    /**
     * Convert type attribute value to TYPE enum (no attribute = OBJECT)
     *
     * @param type The type as a string
     * @return
     */
    private static TYPE toTYPE(String type) {
        if (null == type || type.trim().isEmpty()) {
            return TYPE.OBJECT;
        } else {
            return TYPE.valueOf(type.toUpperCase());
        }
    }

    public static String converXmlToString(Node data) {
        DOMSource source = new DOMSource(data);
        StringWriter writer = new StringWriter();
        Result result = new StreamResult(writer);
        try {
            Transformer transformer = TransformerFactory.newInstance()
                    .newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.CDATA_SECTION_ELEMENTS,
                    "yes");
            transformer.setOutputProperty(
                    "{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (writer.getBuffer().toString());
    }

    /**
     * @param json
     * @param rootTag
     * @return Json转xml字符串
     */
    public static String convertJsonToXMLString(String json, String rootTag) {
        try {
            Node xml = convertToDom(json, "", false, rootTag);
            String xmlToString = converXmlToString(xml);
            return xmlToString;
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param data xml字符串
     * @return
     * @brief 转换字符串为xml对象
     */
    public static Node convertStringToXml(String data) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        try {
            db = dbf.newDocumentBuilder();
            Document document = db
                    .parse(new InputSource(new StringReader(data)));
            Node recordNode = document.getFirstChild();
            return recordNode.cloneNode(true);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * XML格式字符串转换为Map
     *
     * @param strXML XML字符串
     * @return XML数据转换后的Map
     * @throws Exception
     */
    public static Map<String, String> xmlToMap(String strXML) {
        try {
            Map<String, String> data = new HashMap<String, String>();
            DocumentBuilder documentBuilder = newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));
            Document doc = documentBuilder.parse(stream);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
                Node node = nodeList.item(idx);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    data.put(element.getNodeName(), element.getTextContent());
                }
            }
            try {
                stream.close();
            } catch (Exception ex) {
                // do nothing
            }
            return data;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 将Map转换为XML格式的字符串
     *
     * @param data Map类型数据
     * @return XML格式的字符串
     * @throws Exception
     */
    public static String mapToXml(Map<String, String> data) throws Exception {
        Document document = newDocument();
        Element root = document.createElement("xml");
        document.appendChild(root);
        for (String key : data.keySet()) {
            String value = data.get(key);
            if (value == null) {
                value = "";
            }
            value = value.trim();
            Element filed = document.createElement(key);
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
        String output = writer.getBuffer().toString(); //.replaceAll("\n|\r", "");
        try {
            writer.close();
        } catch (Exception ex) {
        }
        return output;
    }


}
