package me.reckter.Robot.Properties;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: mediacenter
 * Date: 26.11.13
 * Time: 12:24
 * To change this template use File | Settings | File Templates.
 */
public class DNA {


    HashMap<String, Property> properties;

    public DNA(){
        properties = new HashMap<String, Property>();
    }

    public void setProperty(String name, Property property){
        properties.put(name, property);
    }

    public Property getProperty(String name){
        return properties.get(name);
    }

    public HashMap<String, Property> getProperties(){
        return properties;
    }
}
