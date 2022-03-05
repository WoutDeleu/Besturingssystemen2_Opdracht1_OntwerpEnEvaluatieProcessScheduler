public class Process {
    private long arrivaltime;
    private long servicetime;

    private long endtime;
    private long tat;
    private long waittime;

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
}
