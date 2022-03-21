package Schedulers;

import Basics.Process;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SRT {
    public SRT() { }

    public List<Process> schedule(List<Process> processes) {
        List<Process> adjustableList = new ArrayList<>(processes);
        Queue<Process> waiting = new LinkedList<>();
        //int currentIndexTimer = 0;
        long timer = adjustableList.get(0).getArrivaltime();

        Process temp = new Process(adjustableList.get(0));
        adjustableList.remove(0);


        //FOUT: wanneer adjustableList isEmpty => cannot : adjustableList.get(0) (lijn 25) lukt niet....
        while(!adjustableList.isEmpty() || !waiting.isEmpty()) {
                if ((adjustableList.get(0).getArrivaltime() - timer) <= temp.getServicetime()) {
                    //A new process is being added to the queue
                    waiting.add(adjustableList.get(0));
                    temp.setServicetime(temp.getServicetime() - (adjustableList.get(0).getArrivaltime() - timer));
                    timer = adjustableList.get(0).getArrivaltime();

                    if (adjustableList.get(0).getServicetime() < temp.getServicetime()) {
                        //New process has shorter remaining time as other process
                        waiting.add(new Process(temp));
                        temp = adjustableList.get(0);
                        temp.setStarttime(timer);
                    }

                    adjustableList.remove(0);
                }

                else {
                    //current process is being finished
                    timer += temp.getServicetime();
                    processes.get(temp.getId() - 1).setEndtime(timer);
                    processes.get(temp.getId() - 1).setStarttime(temp.getStarttime());
                    if (!waiting.isEmpty()) {
                        temp = findSRT(waiting);
                        temp.setStarttime(timer);
                    }
                    else {
                        timer += adjustableList.get(0).getServicetime();
                        adjustableList.remove(0);
                        temp = adjustableList.get(0);
                        temp.setStarttime(timer);
                    }
                }

        }
        calculateValues(processes);
        return processes;
    }

    private Process findSRT(Queue<Process> waiting) {
        Process shortest = waiting.peek();
        for(Process p : waiting) {
           if(p.getServicetime() <= shortest.getServicetime()) shortest = p;
        }
        waiting.remove(shortest);
        return shortest;
    }

    private void calculateValues(List<Process> processes) {
        for(Process p : processes) {
            p.setWaittime(p.getEndtime() - p.getServicetime() - p.getArrivaltime());
            p.setTat(p.getWaittime() + p.getServicetime());
            p.setGenTat(p.getTat()/p.getServicetime());
        }
    }
}
