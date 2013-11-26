package me.reckter;

import me.reckter.Field.BaseField;
import org.newdawn.slick.*;

/**
 * Created with IntelliJ IDEA.
 * User: reckter
 * Date: 17.11.13
 * Time: 22:04
 * To change this template use File | Settings | File Templates.
 */
public class Gameloop extends BasicGame {
    protected BaseField baseField;

    protected int logicTime;
    protected final int MAX_LOGICTIME = 5;

    public Gameloop(String title) {
        super(title);
        baseField = new BaseField();
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {

        baseField.populate();
        baseField.init();
    }

    @Override
    public void update(GameContainer gameContainer, int delta) throws SlickException {
        Input input = gameContainer.getInput();

        if(input.isKeyDown(Input.KEY_R)){
            baseField = new BaseField();
            baseField.populate();
            baseField.init();
        }
        logicTime += delta;
        if(logicTime >= MAX_LOGICTIME){
            logicTime = 0;
            baseField.logic(delta);
        }
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        graphics.setBackground(Color.black);
        baseField.render(graphics);
    }
}
