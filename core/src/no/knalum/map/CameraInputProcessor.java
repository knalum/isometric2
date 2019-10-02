package no.knalum.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

import static no.knalum.map.IsometricMapRenderer.SZ_X;
import static no.knalum.map.IsometricMapRenderer.SZ_Y;


public class CameraInputProcessor extends InputAdapter {

    private final Vector3 vec;
    private OrthographicCamera camera;

    public static int tileY;
    public static int tileX;
    private boolean isDragging;

    public CameraInputProcessor(final OrthographicCamera camera) {
        this.camera = camera;
        this.vec = new Vector3();
    }


    @Override
    public boolean mouseMoved(int screenX, int screenY) {

        vec.x = screenX;
        vec.y = screenY;
        vec.z = 0;
        Vector3 unproject = camera.unproject(vec);
        float x = unproject.x;
        float y = -unproject.y;

        tileX = Math.round(x / SZ_X + y / SZ_Y);
        tileY = Math.round(x / SZ_X - y / SZ_Y) - 1;
        return super.mouseMoved(screenX, screenY);
    }


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (Gdx.input.isButtonPressed(0)) {
            float x = Gdx.input.getDeltaX();
            float y = Gdx.input.getDeltaY();

            float zoom = camera.zoom;
            camera.translate(-x * zoom, y * zoom);
            isDragging = true;
            return super.touchDragged(screenX, screenY, pointer);
        }
        return false;
    }


    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        isDragging = false;
        return super.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean scrolled(int amount) {
        float newZoom = (float) (camera.zoom + amount * 0.1 * camera.zoom);
        if (newZoom < 1) {
            camera.zoom = 1;
        } else {
            camera.zoom = newZoom;
        }
        return super.scrolled(amount);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == 1) {
            //Isometric1.player.moveTo2(tileX,tileY);
        } else if (button == 0) {
            //MapUtils.cellClickedOn(tileX,tileY);
        }
        return super.touchDown(screenX, screenY, pointer, button);
    }


}
