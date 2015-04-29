package model;

import javafx.beans.property.SimpleStringProperty;

public class Elemek {

    private final SimpleStringProperty name;
    private final SimpleStringProperty type;

    public Elemek(String name, String type) {
        this.name = new SimpleStringProperty(name);
        this.type = new SimpleStringProperty(type);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String fName) {
        name.set(fName);
    }

    public String getType() {
        return type.get();
    }

    public void setType(String fName) {
    	type.set(fName);
    }
}

