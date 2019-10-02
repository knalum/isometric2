package no.knalum;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import java.util.Map;

import no.knalum.actor.IsometricActor;
import no.knalum.actor.Slime;
import no.knalum.map.MapCell;

import static no.knalum.map.IsometricMapRenderer.SZ_X;
import static no.knalum.map.IsometricMapRenderer.SZ_Y;

public class GameMap {
    private MapCell[][] map;
    private Map<Integer, TextureRegion> textures;
    private Array<EnemySpawner> enemySpawners;
    private Stage stage;

    public GameMap(MapCell[][] map, Map<Integer, TextureRegion> textures) {

        this.map = map;
        this.textures = textures;
        this.enemySpawners = new Array<>();
    }

    public MapCell[][] getMap() {
        return map;
    }

    public  Map<Integer, TextureRegion> getTextures() {
        return textures;
    }

    public Array<EnemySpawner> getEnemySpawners() {
        return enemySpawners;
    }

    public void setEnemySpawners(Array<EnemySpawner> enemySpawners) {
        this.enemySpawners = enemySpawners;
    }

    public void addActorToWorld(Actor actor){
        int tileX = ((IsometricActor)actor).getTileX();
        int tileY = ((IsometricActor)actor).getTileY();
        map[tileY][tileX].setActor(actor);
        stage.addActor(actor);

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }
}
