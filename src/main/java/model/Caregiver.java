package model;

public class Caregiver extends Person {
    private long cid;
    private String phonenumber;

    /**
     * constructs a patient from the given params.
     * @param firstname
     * @param surname
     * @param phonenumber
     */
   public Caregiver(String firstname, String surname, String phonenumber) {
        super(firstname, surname);
        this.phonenumber = phonenumber;
    }

    /**
     * constructs a patient from the given params.
     * @param firstname
     * @param surname
     * @param phonenumber
     * @param cid
     *
     */
    public Caregiver(String firstname, String surname, String phonenumber, long cid) {
        super(firstname, surname);
        this.phonenumber = phonenumber;
        this.cid = cid;
    }

    public String getPhonenumber() {
        return phonenumber;
    }
    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
    public long getCid() {
       return cid;
   }
}
