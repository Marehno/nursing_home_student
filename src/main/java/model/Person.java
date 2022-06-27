package model;

public abstract class Person {
    private String firstName;
    private String surname;

    /**
     * constructs a person from the given params.
     * @param firstName
     * @param surname
     */

    public Person(String firstName, String surname) {
        this.firstName = firstName;
        this.surname = surname;
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
}
