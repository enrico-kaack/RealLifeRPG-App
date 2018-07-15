package de.realliferpg.app.objects;

public class CustomNetworkError {
    public Class requestReturnClass;
    public String msg;
    public int statusCode;

    @Override
    public String toString() {
        String typeName = requestReturnClass.getName();
        typeName = typeName.replaceAll("de.realliferpg.app.","");
        return "Error in Request of type " + typeName + " Status code " + statusCode;
    }
}