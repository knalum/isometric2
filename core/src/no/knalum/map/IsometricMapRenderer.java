package no.knalum.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

import no.knalum.GameMap;

public class IsometricMapRenderer {
    private final int MAP_Y;
    private final int MAP_X;
    public final static int SZ_X = 64;
    public final static int SZ_Y = 32;
    private final GameMap gameMap;

    private Vector3 screenMiddle = new Vector3(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0);
    private MapCell[][] map;
    private OrthographicCamera camera;

    public IsometricMapRenderer(GameMap gameMap, OrthographicCamera camera) {

        this.gameMap = gameMap;
        this.map = gameMap.getMap();
        this.camera = camera;
        this.MAP_Y = map.length;
        this.MAP_X = map[0].length;
    }

    public void render(SpriteBatch batch) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_ONE, GL20.GL_ZERO);

        batch.setProjectionMatrix(camera.combined);
        camera.update();

        screenMiddle.x = Gdx.graphics.getWidth() / 2f;
        screenMiddle.y = Gdx.graphics.getHeight() / 2f;
        Vector3 unproject = camera.unproject(screenMiddle);
        int offset = 20;
        float x = unproject.x;
        float y = -unproject.y;

        int startX = Math.max(0, Math.round(x / SZ_X + y / SZ_Y) - offset);
        int startY = Math.max(0, Math.round(x / SZ_X - y / SZ_Y) - offset);
        int endX = Math.min(MAP_X, startX + 30);
        int endY = Math.min(MAP_Y, startY + 30);


        for (int i = endY - 1; i >= startY; i--) {
            for (int j = startX; j < endX - 1; j++) {
                short posX = (short) ((i + j) * SZ_X / 2);
                short posY = (short) ((i - j) * SZ_Y / 2);
                if (posX >= (camera.position.x - camera.zoom * camera.viewportWidth / 2 - 100) &&
                        posX <= (camera.position.x + camera.zoom * camera.viewportWidth / 2) &&
                        posY >= (camera.position.y - camera.zoom * camera.viewportHeight / 2 - 100) &&
                        posY <= (camera.position.y + camera.zoom * camera.viewportHeight / 2)) {

                    if (map[i][j].groundType != null) {

                        batch.draw(getTexture(map[i][j].groundType), posX, posY);
                        if (map[i][j].objType != null) {
                            batch.draw(getTexture(map[i][j].objType), posX, posY);
                        }


                        if (map[i][j].actor != null) {
                            map[i][j].actor.draw(batch, 1);

                        }


                    }
                }
            }
        }
    }

    private TextureRegion getTexture(Integer objType) {
        if( !gameMap.getTextures().containsKey(objType) ){
            System.out.println("Textures doesnt contain "+objType);
            return gameMap.getTextures().get(0);
        }
        return gameMap.getTextures().get(objType);
    }
}
