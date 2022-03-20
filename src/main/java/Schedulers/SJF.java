package Schedulers;

import Basics.Process;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SJF {
    public SJF() {

    }
    public List<Process> schedule(List<Process> processes) {
        Queue<Process> queue = new LinkedList<>();
        List<Process> adjustableList = processes;
        long timer = adjustableList.get(0).getArrivaltime();
        addToQue(adjustableList, adjustableList.get(0));

        Process currentProcess = findShortestServiceTime(adjustableList, timer);
        while(queue.size() != 0 && !processes.isEmpty()) {

        }
    }

    private void addToQue(Process process) {
    }

    private Process findShortestServiceTime(List<Process> adjustableList, long timer) {
        int currentBestServicetime_index = 0;
        Process ret;
        for(int i = 0; i< adjustableList.size(); i++) {
            if( adjustableList.get(i).getServicetime() < adjustableList.get(currentBestServicetime_index).getServicetime() && adjustableList.get(i).getArrivaltime() < timer ) {
                currentBestServicetime_index = i;
            }
        }
        ret = adjustableList.get(currentBestServicetime_index);
        adjustableList.remove(currentBestServicetime_index);
        return ret;
    }
}
