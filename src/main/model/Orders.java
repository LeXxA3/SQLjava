package main.model;

public class Orders {
    int orderID;
    int clientID;
    int componentID;
    String orderDate;
    String completeDate;

    public Orders(int orderID, String orderDate, int payment) {
        this.orderID = orderID;
        this.clientID = clientID;
        this.componentID = componentID;
        this.orderDate = orderDate;
        this.completeDate = completeDate;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getPayment() {
        return completeDate;
    }

    public void setPayment(int payment) {
        this.completeDate = completeDate;
    }
}
