package no.knalum;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class EnemySpawner extends Actor {


    private final int tileX;
    private final int tileY;

    public EnemySpawner(int tileX, int tileY) {

        this.tileX = tileX;
        this.tileY = tileY;
    }

    public int getTileX() {
        return tileX;
    }

    public int getTileY() {
        return tileY;
    }
}
