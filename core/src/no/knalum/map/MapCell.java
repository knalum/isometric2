package no.knalum.map;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.Iterator;

public class MapCell {
    Integer groundType;
    Integer objType;
    Actor actor;

    public MapCell(Integer groundType,Integer objType){
        this.groundType = groundType;
        this.objType = objType;
    }

    public MapCell(TiledMapTileLayer.Cell groundCell, TiledMapTileLayer.Cell objCell) {
        if( groundCell == null || groundCell.getTile() == null ){
            groundType = 1;
        }else{
            groundType = groundCell.getTile().getId();
        }

        if( objCell == null ||objCell.getTile() == null ){
            objType = null;
        }else{
            MapProperties properties = objCell.getTile().getProperties();
            objType = objCell.getTile().getId();
        }
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Actor getActor() {
        return actor;
    }

    public Integer getGroundType() {
        return groundType;
    }

    public Integer getObjType() {
        return objType;
    }

    public void setObjType(Integer objType) {
        this.objType = objType;
    }
}
