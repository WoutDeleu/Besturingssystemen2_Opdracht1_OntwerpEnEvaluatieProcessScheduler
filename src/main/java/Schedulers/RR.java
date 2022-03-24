package Schedulers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import Basics.Process;

public class RR {
    private long duur;
    public RR(long tq) {
        duur = tq;
    }
    public List<Process> schedule(List<Process> Proc){
        Queue<Process> q = new LinkedList<>();
        List<Process> adjList = new ArrayList<>(Proc);
        long timer = adjList.get(0).getArrivaltime();

        Process Temp;
        adjList.remove(0);
        q.add(adjList.get(0));

        while(!adjList.isEmpty() || !q.isEmpty()){
            if(!q.isEmpty()){
                Temp= q.peek();
                q.remove();
                if((Temp.getBursttime()-duur)>0){
                    Temp.setBursttime(Temp.getBursttime()-duur);
                    q.add(Temp);
                    timer=timer+duur;
                }
                else{
                    timer=timer+Temp.getBursttime();
                    Temp.setBursttime(0);
                    Temp.setEndtime(timer);
                }
            }
            if(q.isEmpty() && !adjList.isEmpty()) {
                timer = adjList.get(0).getArrivaltime();
            }

            while(!adjList.isEmpty() && adjList.get(0).getArrivaltime()<=timer){
                adjList.get(0).setStarttime(timer);
                q.add(adjList.get(0));
                adjList.remove(0);
            }
        }

        calculateValues(Proc);
        return Proc;
    }

    private void calculateValues(List<Process> processes) {
        for(Process p : processes) {
            p.setWaittime(p.getEndtime() - p.getServicetime() - p.getArrivaltime());
            p.setTat(p.getWaittime() + p.getServicetime());
            p.setGenTat(p.getTat()/p.getServicetime());
        }
    }

}
