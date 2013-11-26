package me.reckter.Robot.Properties;

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
    }

    public Property(float min, float max, float value){
        this(min,max);
        this.value = value;
    }

    public Property(){
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
