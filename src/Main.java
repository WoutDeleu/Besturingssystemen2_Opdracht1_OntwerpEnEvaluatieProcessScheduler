import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        List<Process> processes1 = new ArrayList<>(10000);
        List<Process> processes2 = new ArrayList<>(20000);
        List<Process> processes3 = new ArrayList<>(50000);

        inlezenXML(processes1, processes2, processes3);
    }

    public static void inlezenXML(List<Process> processes1, List<Process> processes2, List<Process> processes3) {
        try {

            File file1 = new File("src/processen10000.xml");
            File file2 = new File("src/processen20000.xml");
            File file3 = new File("src/processen50000.xml");

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc1 = db.parse(file1);
            Document doc2 = db.parse(file2);
            Document doc3 = db.parse(file3);

            doc1.getDocumentElement().normalize();
            doc2.getDocumentElement().normalize();
            doc3.getDocumentElement().normalize();
            //System.out.println("Root element: " + doc1.getDocumentElement().getNodeName());

            NodeList nodeList1 = doc1.getElementsByTagName("process");
            NodeList nodeList2 = doc2.getElementsByTagName("process");
            NodeList nodeList3 = doc3.getElementsByTagName("process");

            for(int i=0; i<nodeList1.getLength(); i++) {
                Node node = nodeList1.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;
                    processes1.add(Integer.parseInt(eElement.getElementsByTagName("pid").item(0).getTextContent())-1, new Process(
                            Integer.parseInt(eElement.getElementsByTagName("pid").item(0).getTextContent()),
                            Integer.parseInt(eElement.getElementsByTagName("arrivaltime").item(0).getTextContent()),
                            Integer.parseInt(eElement.getElementsByTagName("servicetime").item(0).getTextContent())));
                }
            }

            for(int i=0; i<nodeList2.getLength(); i++) {
                Node node = nodeList2.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;
                    processes2.add(Integer.parseInt(eElement.getElementsByTagName("pid").item(0).getTextContent())-1, new Process(
                            Integer.parseInt(eElement.getElementsByTagName("pid").item(0).getTextContent()),
                            Integer.parseInt(eElement.getElementsByTagName("arrivaltime").item(0).getTextContent()),
                            Integer.parseInt(eElement.getElementsByTagName("servicetime").item(0).getTextContent())));
                }
            }

            for(int i=0; i<nodeList3.getLength(); i++) {
                Node node = nodeList3.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;
                    processes3.add(Integer.parseInt(eElement.getElementsByTagName("pid").item(0).getTextContent())-1, new Process(
                            Integer.parseInt(eElement.getElementsByTagName("pid").item(0).getTextContent()),
                            Integer.parseInt(eElement.getElementsByTagName("arrivaltime").item(0).getTextContent()),
                            Integer.parseInt(eElement.getElementsByTagName("servicetime").item(0).getTextContent())));
                }
            }
        }
        catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }
}
