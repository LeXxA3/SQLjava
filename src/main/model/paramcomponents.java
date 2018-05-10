package main.model;

public class paramcomponents {
    int paramID;
    int componentID;
    int value;

    public paramcomponents(int paramID, int componentID, int value) {
        this.paramID = paramID;
        this.componentID = componentID;
        this.value = value;
    }

    public int getParamID() {
        return paramID;
    }

    public void setParamID(int paramID) {
        this.paramID = paramID;
    }

    public int getComponentID() {
        return componentID;
    }

    public void setComponentID(int componentID) {
        this.componentID = componentID;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
