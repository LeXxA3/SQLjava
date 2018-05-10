package main.model;

public class Components {
    int componentID;
    int companyID;
    String componentName;
    int cost;
    String type;

    public Components(int id, String dateTime, int companyID, int cost, String type) {
        this.componentID = id;
        this.componentName = dateTime;
        this.companyID = companyID;
        this.cost = cost;
        this.type = type;
    }

    public int getComponentID() {
        return componentID;
    }

    public void setComponentID(int componentID) {
        this.componentID = componentID;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
