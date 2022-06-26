package model;

public class Caregiver extends Person {
    private long cid;
    private int phonenumber;

    public Caregiver(String firstname, String surname, int phonenumber) {
        super(firstname, surname);
        this.phonenumber = phonenumber;
    }

    public Caregiver(String firstname, String surname, int phonenumber, long cid) {
        super(firstname, surname);
        this.phonenumber = phonenumber;
        this.cid = cid;
    }

    public int getPhonenumber() {
        return phonenumber;
    }
    public void setPhonenumber(int phonenumber) {
        this.phonenumber = phonenumber;
    }
    public long getCid() {
        return cid;
    }
}
