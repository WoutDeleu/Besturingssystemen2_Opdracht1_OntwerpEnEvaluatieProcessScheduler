package Schedulers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import Basics.Process;

public class RR {
    private long duur;
    public RR(long tq) {
        duur = tq*50;
    }
    public List<Process> schedule(List<Process> Proc){
        Queue<Process> Q = new LinkedList<>();
        List<Process> AdjList = new ArrayList<>(Proc);
        long timer = AdjList.get(0).getArrivaltime();

        Process Temp = new Process(AdjList.get(0));
        AdjList.remove(0);
        Q.add(Temp);

        while(!AdjList.isEmpty() || !Q.isEmpty()){
            if(!Q.isEmpty()){
                Temp=Q.peek();
                Q.remove();
                if((Temp.getBursttime()-duur)>0){
                    Temp.setBursttime(Temp.getBursttime()-duur);
                    Q.add(new Process(Temp));
                    timer+=duur;
                }
                else{
                    timer+=Temp.getBursttime();
                    Temp.setBursttime(0);
                    Temp.setEndtime(timer);
                }

            }
            if(Q.isEmpty() && !AdjList.isEmpty()) {
                timer = AdjList.get(0).getArrivaltime();
            }

            while(!AdjList.isEmpty() && AdjList.get(0).getArrivaltime()<=timer){
                AdjList.get(0).setStarttime(timer);
                Q.add(AdjList.get(0));
                AdjList.remove(0);
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
