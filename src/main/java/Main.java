import Basics.Process;
import Basics.ServiceTimeComparator;
import Basics.XYLineChart_AWT;
import Schedulers.FCFS;
import Schedulers.SJF;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import org.jfree.ui.RefineryUtilities;

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

        List<Process> cluster = new ArrayList<>(100);


        //SchedulingAlgorithms

        //1. Schedulers

        // FCFS

        FCFS fcfs = new FCFS();
        List<Process> fcfs_res = new ArrayList<>(fcfs.schedule(processes3));

        Collections.sort(fcfs_res, new ServiceTimeComparator());
        makeClusters(cluster, fcfs_res);

        long[] glob_par = calculate_averages(cluster);
        System.out.println("1. FCFS");
        printResult(glob_par);

        plot(cluster, "FCFS");


        for(Process p : processes3) p.reset();

        //2.SJN (=SPN)
        SJF sjf = new SJF();
        List<Process> sjf_res = new ArrayList<>(sjf.schedule(processes3));

        Collections.sort(sjf_res, new ServiceTimeComparator());
        makeClusters(cluster, sjf_res);

        glob_par = calculate_averages(cluster);
        System.out.println("2. SJN");
        printResult(glob_par);

        plot(cluster, "SJN/SPN");



    }


    public static void inlezenXML(List<Process> processes1, List<Process> processes2, List<Process> processes3) {
        try {

            File file1 = new File("src/main/java/Data/processen10000.xml");
            File file2 = new File("src/main/java/Data/processen20000.xml");
            File file3 = new File("src/main/java/Data/processen50000.xml");

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

    public static void makeClusters(List<Process> clusters, List<Process> processes) {
        int amountPerCluster = processes.size()/100;
        int procesCounter = 0;
        for(int i = 0; i<100; i++) {
            int temp = 0;
            long arrivaltime = 0;
            long servicetime = 0;
            long waittime = 0;
            long tat = 0;
            long genTat = 0;
            while(temp<amountPerCluster) {
                arrivaltime += processes.get(procesCounter).getArrivaltime();
                servicetime += processes.get(procesCounter).getServicetime();
                waittime += processes.get(procesCounter).getWaittime();
                tat += processes.get(procesCounter).getTat();
                genTat += processes.get(procesCounter).getGenTat();
                temp++;
                procesCounter++;
            }
            clusters.add(i, new Process(arrivaltime/amountPerCluster, servicetime/amountPerCluster, waittime/amountPerCluster, tat/amountPerCluster, genTat/amountPerCluster));
        }
    }

    private static long[] calculate_averages(List<Process> cluster) {
        long gem_omlooptijd=0, gem_gen_omlooptijd=0, gem_wachttijd = 0;
        for(int i =0; i<100; i++) {
            Process current = cluster.get(i);
            gem_omlooptijd = current.getTat() + gem_omlooptijd;
            gem_gen_omlooptijd = current.getGenTat() + gem_gen_omlooptijd;
            gem_wachttijd = current.getWaittime() + gem_wachttijd;
        }
        gem_omlooptijd = gem_omlooptijd/100;
        gem_gen_omlooptijd = gem_gen_omlooptijd/100;
        gem_wachttijd = gem_wachttijd/100;
        long[] ret = { gem_omlooptijd, gem_gen_omlooptijd, gem_wachttijd };
        return ret;
    }

    //Print Global Variables
    //Glob_par: { gem_omlooptijd, gem_gen_omlooptijd, gem_wachttijd }
    private static void printResult(long[] glob_par) {
        System.out.println("De gemiddelde omlooptijd is " + glob_par[0] + " JIFFIY's");
        System.out.println("De gemiddelde genormaliseerde omlooptijd is " + glob_par[1] + " JIFFIY's");
        System.out.println("De gemiddelde wachttijd is " + glob_par[2] + " JIFFIY's");
        System.out.println();
        System.out.println();
    }

    private static void plot(List<Process> cluster, String titel) {
        XYLineChart_AWT chart = new XYLineChart_AWT(titel, titel, cluster);
        chart.pack( );
        RefineryUtilities.centerFrameOnScreen( chart );
        chart.setVisible( true );
    }

}