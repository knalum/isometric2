package no.knalum.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import no.knalum.map.MapCell;

import static no.knalum.map.IsometricMapRenderer.SZ_X;
import static no.knalum.map.IsometricMapRenderer.SZ_Y;

public class Slime extends IsometricActor {

    private static final Texture texture = new Texture("actors/slime.png");
    private static int id = 0;
    private int tileX;
    private int tileY;
    private int timer;
    private MapCell[][] map;

    public Slime(final int tileX, final int tileY, MapCell[][] map) {
        super(texture);
        this.map = map;
        setPosition((tileX + tileY) * SZ_X / 2.0f, (tileY - tileX) * SZ_Y / 2.0f);
        setName("slime_" + (++id));
        this.tileX = tileX;
        this.tileY = tileY;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        timer++;
        if (timer > 60) {
            timer = 0;
            moveRandom();
        }
    }

    private void moveRandom() {
        final int toTileX;
        final int toTileY;

        int dir = MathUtils.random(0, 8);
        Action action;


        if (dir == 0) {
            // NORTH
            toTileX = tileX - 1;
            toTileY = tileY + 1;
            if (isCollision(toTileX, toTileY)) return;
            action = Actions.moveBy(0, SZ_Y);
        }else if( dir == 1){
            // NORTH-EAST
            toTileX = tileX;
            toTileY = tileY +1 ;
            if (isCollision(toTileX, toTileY)) return;
            action = Actions.moveBy(SZ_X/2.f, SZ_Y/2.f);
        }else{
            // EAST
            toTileX = tileX+1;
            toTileY = tileY +1 ;
            if (isCollision(toTileX, toTileY)) return;
            action = Actions.moveBy(SZ_X, 0);
        }

        final int tileX = getTileX();
        final int tileY = getTileY();


        ((MoveByAction) action).setDuration(0.2f);
        Action finishAction = new Action() {
            @Override
            public boolean act(float delta) {
                if( map[tileY][tileX].getActor() == null ){
                    System.out.println("No actor at "+tileY+" "+tileX);
                }
                map[tileY][tileX].setActor(null);
                map[toTileY][toTileX].setActor(Slime.this);
                Slime.this.tileX = toTileX;
                Slime.this.tileY = toTileY;
                return true;
            }
        };
        SequenceAction seq = new SequenceAction(
                action,
                finishAction
        );

        addAction(seq);
    }

    private boolean isCollision(int toTileX, int toTileY) {

        MapCell mapCell = map[toTileY][toTileX];
        if (mapCell.getObjType() != null) {
            return true;
        } else if (mapCell.getActor() != null) {
            return true;
        }
        return false;
    }

    public int getTileX() {
        return tileX;
    }

    public int getTileY() {
        return tileY;
    }

    @Override
    public void setTileX(int tileX) {
        this.tileX = tileX;
    }

    @Override
    public void setTileY(int tileY) {
        this.tileY = tileY;
    }
}
