package Basics;

import Schedulers.*;

public class Process {
    private int id;
    private long arrivaltime;
    private long servicetime;

    private long starttime;
    private long endtime;
    private long waittime;
    private long tat;
    private long genTat;
    private int timesScheduled;



    public Process(int id, long arrivaltime, long servicetime) {
        this.id = id;
        this.arrivaltime = arrivaltime;
        this.servicetime = servicetime;
    }

    public Process(long arrivaltime, long servicetime, long waittime, long tat, long genTat) {
        this.arrivaltime = arrivaltime;
        this.servicetime = servicetime;
        this.waittime = waittime;
        this.tat = tat;
        this.genTat = genTat;
    }

    public Process(Process p) {
        id = p.id;
        arrivaltime = p.arrivaltime;
        servicetime = p.servicetime;
    }

    public void reset() {
        this.starttime = 0;
        this.endtime = 0;
        this.waittime = 0;
        this.tat = 0;
        this.genTat = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setArrivaltime(long arrivaltime) {
        this.arrivaltime = arrivaltime;
    }

    public void setServicetime(long servicetime) {
        this.servicetime = servicetime;
    }

    public long getArrivaltime() {
        return arrivaltime;
    }

    public long getServicetime() {
        return servicetime;
    }

    public long getWaittime() {
        return waittime;
    }

    public long getTat() {
        return tat;
    }

    public long getGenTat() {
        return genTat;
    }

    public void setWaittime(long waittime){
        this.waittime = waittime;
    }

    public void setEndtime(long endtime) {
        this.endtime = endtime;
    }

    public void setTat(long tat) {
        this.tat = tat;
    }

    public void setGenTat(long genTat) {
        this.genTat = genTat;
    }

    public void setStarttime(long starttime) {
        this.starttime = starttime;
    }

    public long getStarttime() {
        return starttime;
    }

    public long getEndtime() {
        return endtime;
    }

    public void setTimesScheduled(int timesScheduled){this.timesScheduled = timesScheduled;}

    public int getTimesScheduled() {return this.timesScheduled;}
}
