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
import java.util.*;

public class Main {
    public static void main(String[] args) {
        //Read data
        List<Process> processes1 = new ArrayList<>(10000);
        List<Process> processes2 = new ArrayList<>(20000);
        List<Process> processes3 = new ArrayList<>(50000);
        inlezenXML(processes1, processes2, processes3);
        Collections.sort(processes1, new ServiceTimeComparator());
        Collections.sort(processes2, new ServiceTimeComparator());
        Collections.sort(processes3, new ServiceTimeComparator());



             //Opm: normally the cluster are right, but there is no way to check...
        //So if a fault is encountered, it is a possibility it is in here


        //SchedulingAlgorithms
        //1. FCFS
        List<Process> fcfs = processes3;
        fCFS(fcfs);
        //2.
        List<Process> rr_2 = processes3;
        List<Process> rr_4 = processes3;
        List<Process> rr_8 = processes3;
        rr(rr_2,2);
        rr(rr_4,4);
        rr(rr_8,8);


        //Groop in percentages
        List<Process> clusters1 = new ArrayList<>(100);
        List<Process> clusters2 = new ArrayList<>(100);
        List<Process> clusters3 = new ArrayList<>(100);

        makeClusters(clusters1, processes1);
        makeClusters(clusters2, processes2);
        makeClusters(clusters3, processes3);

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

    private static void fCFS(List<Process> processes) {
        int c = 0;
        long currentEndTime = 0;
        for(Process p : processes){
            if(c == 0) {
                p.setWaittime(0);
            }
            else {
                if (currentEndTime - p.getArrivaltime() < 0) p.setWaittime(0);
                else p.setWaittime(currentEndTime - p.getArrivaltime());
            }
            currentEndTime = p.getArrivaltime()+p.getServicetime()+p.getWaittime();
            p.setEndtime(currentEndTime);
            p.setStarttime(p.getArrivaltime()+p.getWaittime());
            p.setTat(p.getServicetime()+p.getWaittime());
            p.setGenTat(p.getTat()/p.getServicetime());
            c++;
        }
    }

    private static void rr(List<Process> rr, int q) {
        List<Process> queue = new LinkedList<>();
        for(int i = 0; i<rr.size(); i++) {

        }
    }
}
