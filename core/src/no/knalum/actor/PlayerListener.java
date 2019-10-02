package no.knalum.actor;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

import no.knalum.map.MapCell;

import static no.knalum.map.IsometricMapRenderer.SZ_X;
import static no.knalum.map.IsometricMapRenderer.SZ_Y;

public class PlayerListener extends InputAdapter {
    private Player player;
    private MapCell[][] map;
    private Integer keydown;


    public PlayerListener(Player player, MapCell[][] map) {
        this.player = player;
        this.map = map;
    }

    public boolean keyDown(int keycode) {

        this.keydown = keycode;
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        this.keydown = null;
        return super.keyUp(keycode);
    }

    public void handleMovement() {
        if (this.keydown == null) return;
        int keycode = this.keydown;
        int tileY = player.getTileY();
        int tileX = player.getTileX();


        if (keycode == Input.Keys.D) {
            player.move(SZ_X, 0, tileY, tileX, tileX + 1, tileY + 1, true);
        } else if (keycode == Input.Keys.A) {
            player.move(-SZ_X, 0, tileY, tileX, tileX - 1, tileY - 1, false);
        } else if (keycode == Input.Keys.C) {
            player.move(SZ_X / 2f, -SZ_Y / 2f, tileY, tileX, tileX + 1, tileY, false);
        } else if (keycode == Input.Keys.X) {
            player.move(0, -SZ_Y, tileY, tileX, tileX + 1, tileY - 1, false);
        } else if (keycode == Input.Keys.Z) {
            player.move(-SZ_X / 2f, -SZ_Y / 2f, tileY, tileX, tileX, tileY - 1, false);
        } else if (keycode == Input.Keys.Q) {
            player.move(-SZ_X / 2f, SZ_Y / 2f, tileY, tileX, tileX - 1, tileY, true);
        } else if (keycode == Input.Keys.W) {
            player.move(0, SZ_Y, tileY, tileX, tileX - 1, tileY + 1, true);
        } else if (keycode == Input.Keys.E) {
            player.move(SZ_X / 2f, SZ_Y / 2f, tileY, tileX, tileX, tileY + 1, true);
        }
    }
}
