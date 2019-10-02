package no.knalum.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import no.knalum.map.MapCell;

import static no.knalum.map.IsometricMapRenderer.SZ_X;
import static no.knalum.map.IsometricMapRenderer.SZ_Y;

public class Player extends IsometricActor  {

    private static Texture texture = new Texture("actors/playerDown.png");
    private final MapCell[][] map;
    private int tileX;
    private int tileY;
    private float duration = 0.2f;

    public Player(final int x, final int y, MapCell[][] map) {
        super(texture);
        this.map = map;
        setPosition((x + y) * SZ_X / 2.0f, (y - x) * SZ_Y / 2.0f);
        this.tileX = x;
        this.tileY = y;
    }

    public int getTileX() {
        return Math.round(getX() / SZ_X - getY() / SZ_Y);
    }

    public int getTileY() {
        return Math.round(getX() / SZ_X + getY() / SZ_Y);
    }


    public void move(float dx, float dy, final int tileY, final int tileX, final int toTileX, final int toTileY, final boolean firstAction) {
        if( !getActions().isEmpty() ){
            return;
        }
        if (map[toTileY][toTileX].getObjType() != null) {
            // Collides with object?
            return;
        }else if( map[toTileY][toTileX].getActor() != null ){
            // Collides with enemy
            System.out.println("Collides with "+map[toTileY][toTileX].getActor().getName());
            return;
        }

        MoveByAction moveAction = Actions.moveBy(dx, dy, duration);
        Action finishAction = new Action() {
            @Override
            public boolean act(float delta) {
                map[tileY][tileX].setActor(null);
                map[toTileY][toTileX].setActor(Player.this);
                return true;
            }
        };
        SequenceAction sequenceAction = new SequenceAction();
        if( firstAction ){
            sequenceAction.addAction(moveAction);
            sequenceAction.addAction(finishAction);
        }else{
            sequenceAction.addAction(finishAction);
            sequenceAction.addAction(moveAction);
        }
        this.tileX = toTileX;
        this.tileY = toTileY;

        addAction(sequenceAction);
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
