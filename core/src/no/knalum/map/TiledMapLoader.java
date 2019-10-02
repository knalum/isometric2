package no.knalum.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

import no.knalum.EnemySpawner;
import no.knalum.GameMap;

import static no.knalum.map.IsometricMapRenderer.SZ_X;
import static no.knalum.map.IsometricMapRenderer.SZ_Y;

public class TiledMapLoader {


    private Stage stage;

    public TiledMapLoader(Stage stage) {

        this.stage = stage;
    }

    public GameMap load(String path) {

        Map<Integer, TextureRegion> textures = new HashMap<>();

        TiledMap tiledMap = new TmxMapLoader().load(path);
        TiledMapTileLayer groundLayer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        TiledMapTileLayer objectLayer = (TiledMapTileLayer) tiledMap.getLayers().get(1);
        TiledMapTileLayer enemySpawnLayer = (TiledMapTileLayer) tiledMap.getLayers().get(2);

        TiledMapTileSets tileSets = tiledMap.getTileSets();
        for (TiledMapTileSet tiledMapTileSet : tileSets) {
            for (TiledMapTile next : tiledMapTileSet) {
                TextureRegion textureRegion = next.getTextureRegion();
                textures.put(next.getId(), textureRegion);
            }
        }



        int szy = groundLayer.getHeight();
        int szx = groundLayer.getWidth();

        MapCell[][] map = new MapCell[szy][szx];
        Array<EnemySpawner> enemySpawners = new Array<>();

        for (int i = 0; i < szy; i++) {
            for (int j = 0; j < szx; j++) {
                TiledMapTileLayer.Cell groundCell = groundLayer.getCell(j, i);
                TiledMapTileLayer.Cell objCell = objectLayer.getCell(j, i);
                map[i][j] = new MapCell(groundCell, objCell);

                TiledMapTileLayer.Cell enemySpawnCell = enemySpawnLayer.getCell(j, i);
                if( enemySpawnCell != null && enemySpawnCell.getTile() != null ){
                    enemySpawners.add(new EnemySpawner(j,i));
                }
            }
        }

        GameMap gameMap = new GameMap(map, textures);
        gameMap.setEnemySpawners(enemySpawners);
        gameMap.setStage(stage);
        return gameMap;
    }
}
