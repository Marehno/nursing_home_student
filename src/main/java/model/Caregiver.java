package model;

public class Caregiver extends Person {
    private long cid;
    private long phonenumber;

    public Caregiver(String firstname, String surname, long phonenumber) {
        super(firstname, surname);
        this.phonenumber = phonenumber;
    }

    public Caregiver(String firstname, String surname, int phonenumber, long cid) {
        super(firstname, surname);
        this.phonenumber = phonenumber;
        this.cid = cid;
    }

    public long getPhonenumber() {
        return phonenumber;
    }
    public long getCid() {
        return cid;
    }

    public void setPhonenumber(int phonenumber) {
        this.phonenumber = phonenumber;
    }
}
