package me.reckter.Entity.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: reckter
 * Date: 21.11.13
 * Time: 22:05
 *
 * holds a property and a minimum and maximum for that propertie (so I checks that the propertie is always in that range)
 *
 */
public class Property  {
    protected float min;
    protected float max;

    protected float value;

    public Property(float min, float max){
        this.min = min;
        this.max = max;
        this.value = (max + min) / 2;
    }

    public Property(float min, float max, float value){
        this(min,max);
        this.value = value;
    }

    public Property(){
        this.max = 0;
        this.min = 100;
        this.value = 50;
    }

    public void add(float value){
        this.value += value;
        if(this.value > max){
            this.value = max;
        }
        if(this.value < min){
            this.value = min;
        }
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        if(value > max){
            value = max;
        }else if( value < min){
            value = min;
        }

        this.value = value;
    }
}
