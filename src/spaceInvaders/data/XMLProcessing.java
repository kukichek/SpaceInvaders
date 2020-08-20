package spaceInvaders.data;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class XMLProcessing {
    public static List<PersonalizedScore> readXMLFile(String xmlPath) {
        List<PersonalizedScore> scoreTable = new LinkedList<>();

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {
                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    if (qName.equals("namedScore")) {
                        String name = attributes.getValue("name");
                        String score = attributes.getValue("score");

                        scoreTable.add(new PersonalizedScore(name, Integer.valueOf(score)));
                    }
                }
            };

            parser.parse(xmlPath, handler);
        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }

        return scoreTable;
    }

    public static void writeInXMLFile(String xmlPath, List<PersonalizedScore> scoreTable) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element root = document.createElement("scoreTable");
            document.appendChild(root);

            scoreTable.forEach((namedScore) -> {
                Element namedScoreElement = document.createElement("namedScore");

                namedScoreElement.setAttribute("name", namedScore.getName());
                namedScoreElement.setAttribute("score", Integer.toString(namedScore.getScore()));

                root.appendChild(namedScoreElement);
            });

            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = transFactory.newTransformer();

            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(xmlPath));
            transformer.transform(domSource, streamResult);

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }
}
