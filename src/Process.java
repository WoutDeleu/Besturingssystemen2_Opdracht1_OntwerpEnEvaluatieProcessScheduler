public class Process {
    private int pid;
    private int arrivaltime;
    private int servicetime;

    private int endtime;
    private int tat;
    private int waittime;

    public Process(int pid, int arrivaltime, int servicetime) {
        this.pid = pid;
        this.arrivaltime = arrivaltime;
        this.servicetime = servicetime;
    }

    public int getPid() {
        return pid;
    }

    public int getArrivaltime() {
        return arrivaltime;
    }

    public int getServicetime() {
        return servicetime;
    }
}
