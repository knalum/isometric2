package no.knalum.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;


public abstract class IsometricActor extends Image {
    public IsometricActor(Texture texture) {
        super(texture);
    }

    public abstract int getTileX();
    public abstract int getTileY();

    public abstract void setTileX(final int tileX);
    public abstract void setTileY(final int tileY);


}
