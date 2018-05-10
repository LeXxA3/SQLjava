package main.model;

public class Parameters {
    int paramID;
    String parameterName;
    int ed;

    public Parameters(int paramID, String parameterName, int ed) {
        this.paramID = paramID;
        this.parameterName = parameterName;
        this.ed = ed;
    }

    public int getParamID() {
        return paramID;
    }

    public void setParamID(int paramID) {
        this.paramID = paramID;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public int getEd() {
        return ed;
    }

    public void setEd(int ed) {
        this.ed = ed;
    }
}
