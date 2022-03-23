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

        Process current = new Process(adjustableList.get(0));
        adjustableList.remove(0);


        //FOUT::: Bij bepaalde vult hij de endtijd niet in?? Starttime wordt wel ingevuld!!!!
        while(!adjustableList.isEmpty() || !waiting.isEmpty()) {
            if(waiting.isEmpty()) {
                if (isNextProcessFirst(adjustableList, current, timer)) {
                    //A new process is being added to the queue
                    current.setServicetime(current.getServicetime() - (adjustableList.get(0).getArrivaltime() - timer));
                    timer = adjustableList.get(0).getArrivaltime();

                    if (adjustableList.get(0).getServicetime() < current.getServicetime()) {
                        //New process has shorter remaining time as other process
                        replaceCurrent(current, waiting, adjustableList, timer);
                    }
                    else {
                        waiting.add(adjustableList.get(0));
                    }
                    adjustableList.remove(0);
                }
                else {
                    //current process is being finished
                    timer += current.getServicetime();
                    finishCurrent(processes, current, timer);
                    timer = adjustableList.get(0).getArrivaltime();
                    current = adjustableList.get(0);
                    current.setStarttime(timer);
                    adjustableList.remove(0);
                }
            }
            else if(adjustableList.isEmpty()) {
                timer += current.getServicetime();
                finishCurrent(processes, current, timer);
                current = findSRT(waiting);
                current.setStarttime(timer);

            }
            else {
                if (isNextProcessFirst(adjustableList, current, timer)) {
                    //A new process is being added to the queue
                    current.setServicetime(current.getServicetime() - (adjustableList.get(0).getArrivaltime() - timer));
                    timer = adjustableList.get(0).getArrivaltime();

                    if (adjustableList.get(0).getServicetime() < current.getServicetime()) {
                        //New process has shorter remaining time as other process
                        replaceCurrent(current, waiting, adjustableList, timer);
                    }
                    else {
                        waiting.add(adjustableList.get(0));
                    }
                    adjustableList.remove(0);
                }
                else {
                    //current process is being finished
                    timer += current.getServicetime();
                    finishCurrent(processes, current, timer);
                    current = findSRT(waiting);
                    current.setStarttime(timer);
                }
            }
        }
        //finishCurrent(processes, current, timer);
        findEndTimeNull(processes);
        calculateValues(processes);
        return processes;
    }

    private boolean isNextProcessFirst(List<Process> adjustableList, Process current, long timer) {
        return (adjustableList.get(0).getArrivaltime() - timer) <= current.getServicetime();
    }
    private void replaceCurrent(Process current, Queue<Process> waiting, List<Process> adjustableList, long timer) {
        waiting.add(new Process(current));
        current = adjustableList.get(0);
        current.setStarttime(timer);
    }

    private void finishCurrent(List<Process> processes, Process current, long timer) {
        processes.get(current.getId() - 1).setEndtime(timer);
        processes.get(current.getId() - 1).setStarttime(current.getStarttime());
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

    private void findEndTimeNull(List<Process> processes) {
        for(Process p : processes) {
            if(p.getEndtime() == 0) System.out.println(p.getId());
        }
    }
}
