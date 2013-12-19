package me.reckter.Entity;

import me.reckter.Entity.Animal;
import me.reckter.Field.BaseField;
import me.reckter.Entity.Properties.Property;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: reckter
 * Date: 26.11.13
 * Time: 18:17
 * To change this template use File | Settings | File Templates.
 */
public class Grass extends BaseEntity {
    public final float GROWTH_RATE = 10f;

    public Grass(float x, float y, BaseField field) {
        super(x, y, field);
    }

    @Override
    public void init(){
        dna.setProperty("sizeToDublicate", new Property(0,100));

        dna.setProperty("dublicatesNumbers", new Property(0,3));
        dna.setProperty("dublicateRange", new Property(0,100));
        dna.setProperty("dublicateSize", new Property(5,20));


        dna.setProperty("midTerms", new Property(30,100));

        size = 50;
    }

    @Override
    public void logic(int delta) {

        if(size <= 0){
            isAlive = false;
        }

        age += delta;
        size += ((float) delta / 1000) * GROWTH_RATE * (1f - ((float)age / (dna.getProperty("midTerms").getValue() * 1000f)));

        if(age >= dna.getProperty("midTerms").getValue() * 100 && size >= dna.getProperty("sizeToDublicate").getValue()){

            for(int i = 0; i < (int) dna.getProperty("dublicatesNumbers").getValue(); i++){
                Grass born = new Grass( (float) (this.x + (Math.random() * dna.getProperty("dublicateRange").getValue()) - (dna.getProperty("dublicateRange").getValue() / 2)),
                                        (float) (this.y + (Math.random() * dna.getProperty("dublicateRange").getValue()) - (dna.getProperty("dublicateRange").getValue() / 2)), field);
                born.init();
                born.dna = dna;
                born.size = dna.getProperty("dublicateSize").getValue();
                size -= 3 * dna.getProperty("dublicateSize").getValue(); //for every point the mother looses 3


                field.add(born);
            }
        }
    }

    @Override
    public Shape getHitbox(){
        return new Rectangle(x - size / 2, y - size / 2, size, size);
    }

    @Override
    public void render(Graphics g) {
        float displaySize = size / 5;
        g.setColor(Color.green);
        g.fill(new Rectangle(x - displaySize, y - displaySize, displaySize, displaySize));
    }
}
