package Schedulers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import Basics.Process;

public class RR {
    private long duur;
    private Queue<Process> Q = new LinkedList<>();
    public RR(long tq) {
        duur = tq;
    }

    public List<Process> schedule(List<Process> Proc){
        List<Process> AdjList = new ArrayList<>(Proc);
        long timer = AdjList.get(0).getArrivaltime();

        Process Temp = new Process(AdjList.get(0));
        AdjList.remove(0);

        Q.add(Temp);

        while(!AdjList.isEmpty()){
            if(AdjList.get(0).getArrivaltime()>=timer){
                Q.add(AdjList.get(0));
                AdjList.remove(0);
            }

            if(!Q.isEmpty()){
                if((Temp.getServicetime()-duur)>0){
                    Temp.setServicetime(Temp.getServicetime()-duur);
                    Q.add(new Process(Temp));
                    timer+=duur;
                }
                else{
                    timer+=Temp.getServicetime();
                    Temp.setServicetime(0);
                }
                Temp=Q.peek();
                if(Temp.getTimesScheduled()==0) Temp.setStarttime(timer);
                Q.remove();
            }


        }


        return
    }

    public void addToQueue(){

    }

}
