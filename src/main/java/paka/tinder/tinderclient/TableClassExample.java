package paka.tinder.tinderclient;

/**
 * Class representing table in database
 * Very similar exists in server, but with some annotations
 */
public class TableClassExample {
    private int id;
    private String name;
    private String surname;

    //some getters and setters

    public int getId() {
        return id;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}

