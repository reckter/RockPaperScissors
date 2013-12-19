package me.reckter.Entity.Properties;

import me.reckter.Log;

import java.util.HashMap;
import java.util.MissingResourceException;
import java.util.Properties;

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

    /**
     * sets the chance to mutate paremeer and sets it to not mutate
     * @param chance
     */
    public void setChanceToMutate(float chance){
        Property chanceToMutate = new Property(0,1, chance);
        chanceToMutate.setDoesMutate(false);
        properties.put("chanceToMutate",chanceToMutate);
    }

    /**
     * sets the mutate percentage parameter and sets it to not mutate
     * @param chance
     */
    public void setMutatePercentage(float chance){
        Property mutatePercentage = new Property(0,1, chance);
        mutatePercentage.setDoesMutate(false);
        properties.put("mutatePercentage",mutatePercentage);
    }

    /**
     * mutates all the values. There has to be 2 properties set "chanceToMutate" and "mutatePercentage" both between 0 and 1
     * If there are not set then good luck! (setChanceToMutate and setMutatePercentage)
     */
    public void mutate() throws MissingResourceException {
        float chanceToMutate = 0;
        float mutatePercentage = 0;
        try{
            chanceToMutate = properties.get("chanceToMutate").getValue();
            mutatePercentage = properties.get("mutatePercentage").getValue();
        }
        catch(NullPointerException e){
            throw new MissingResourceException("chanceToMutate or mutatePercantage was not set", this.getClass().getName(),"");
        }
        for(Property property: properties.values()){
            property.mutate(chanceToMutate,mutatePercentage);
        }
    }

    /**
     * creates a String of all the variables that are DNA-important
     * @return the DNA String
     */
    public String getDNA(){

        String out = "";
        for(String key: properties.keySet()){
            Property prop = properties.get(key);
            out += key + "|" + prop.getMin() + "|" + prop.getMax() + "|" + prop.getValue() + ";";
        }

        return out;
    }

    /**
     * initialises the object with the string
     * @param dnaString the string to parse the dna out of
     * @return
     */
    public boolean setDNA(String dnaString){
        randomizeObjects();

        HashMap<String, Property> propertiesTMP = new HashMap<String, Property>();


        String[] propertiesString = dnaString.split(";");

        for(String prop: propertiesString){
            String[] input = prop.split("\\|");
            if(properties.get(input[0]) == null){
                Log.error("got a false property:" + input[0]);
                return false;
            }

            float min = Float.parseFloat(input[1]);
            float max = Float.parseFloat(input[2]);
            float value = Float.parseFloat(input[3]);
            propertiesTMP.put(input[0], new Property(min, max, value));
        }
        properties = propertiesTMP;
        return true;
    }

    /**
     * randomly initialises the object
     */
    public void randomizeObjects(){

        for(String key: properties.keySet()){
            Property prop = properties.get(key);
            properties.get(key).setValue((float) (Math.random() * prop.getMax()) + prop.getMin());
        }

    }


    public HashMap<String, Property> getProperties(){
        return properties;
    }
}
