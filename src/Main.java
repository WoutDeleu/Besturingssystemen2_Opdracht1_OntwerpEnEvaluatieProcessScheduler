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
        //Read data
        List<Process> processes1 = new ArrayList<>(10000);
        List<Process> processes2 = new ArrayList<>(20000);
        List<Process> processes3 = new ArrayList<>(50000);
        inlezenXML(processes1, processes2, processes3);
        //Remark: we work only with the processes3 from now on. If you experience memory problems, you can use the smaller lists.

        //Groop in percentages
        List<Process> clusters1 = new ArrayList<>(100);
        List<Process> clusters2 = new ArrayList<>(100);
        List<Process> clusters3 = new ArrayList<>(100);

        makeClusters(clusters1, processes1);
        makeClusters(clusters2, processes2);
        makeClusters(clusters3, processes3);     //Opm: normally the cluster are right, but there is no way to check...
        //So if a fault is encountered, it is a possibility it is in here

        //SchedulingAlgorithms
        //1. FCFS


        //Plotten: JFreeChart
    }


    public static void makeClusters(List<Process> clusters, List<Process> processes) {
        int amountPerCluster = processes.size()/100;
        int procesCounter = 0;
        for(int i = 0; i<100; i++) {
            int temp = 0;
            long arrivaltime = 0;
            long servicetime = 0;
            while(temp<amountPerCluster) {
                arrivaltime = arrivaltime + processes.get(procesCounter).getArrivaltime();
                servicetime = servicetime + processes.get(procesCounter).getServicetime();
                temp++;
                procesCounter++;
            }
            clusters.add(i, new Process(arrivaltime/amountPerCluster, servicetime/amountPerCluster));
        }
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

            addProcesses(nodeList1, processes1);
            addProcesses(nodeList2, processes2);
            addProcesses(nodeList3, processes3);
        }
        catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    private static void addProcesses(NodeList nodeList, List<Process> processes) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                processes.add(Integer.parseInt(eElement.getElementsByTagName("pid").item(0).getTextContent()) - 1, new Process(
                        Long.parseLong(eElement.getElementsByTagName("arrivaltime").item(0).getTextContent()),
                        Long.parseLong(eElement.getElementsByTagName("servicetime").item(0).getTextContent())));
            }
        }
    }
}
