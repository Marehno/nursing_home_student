package model;

public class Caregiver {
    private String firstName;
    private String surname;
    private int phonenumber;
    private long pid;

    public Caregiver(String firstName, String surname, int phonenumber, long pid) {
        this.firstName = firstName;
        this.surname = surname;
        this.phonenumber = phonenumber;
        this.pid = pid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(int phonenumber) {
        this.phonenumber = phonenumber;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }
}
