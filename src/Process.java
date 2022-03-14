public class Process {
    private long arrivaltime;
    private long servicetime;

    private long starttime;
    private long endtime;
    private long waittime;
    private long tat;
    private long genTat;



    public Process(long arrivaltime, long servicetime) {
        this.arrivaltime = arrivaltime;
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
}
