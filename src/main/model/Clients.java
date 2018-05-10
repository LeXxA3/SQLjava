package main.model;

public class Clients {
    int clientID;
    String clientName;
    int age;
    String Email;

    public Clients(int clientID, String clientName, int age, String Email) {
        this.clientID = clientID;
        this.clientName = clientName;
        this.age = age;
        this.Email = Email;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) { this.Email = Email; }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
