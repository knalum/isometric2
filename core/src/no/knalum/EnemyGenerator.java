package no.knalum;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import no.knalum.actor.Slime;

class EnemyGenerator extends Actor {

    private int timer;
    private GameMap gameMap;

    public EnemyGenerator(GameMap gameMap) {

        this.gameMap = gameMap;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        timer++;
        if( timer>180 ){
            timer=0;
            spawnEnemies();
        }
    }

    private void spawnEnemies() {
        for(EnemySpawner spawner : gameMap.getEnemySpawners()){
            System.out.println("Spawn enemy at: "+spawner.getTileX()+" "+spawner.getTileY());
            gameMap.addActorToWorld(new Slime(
                    spawner.getTileX(),
                    spawner.getTileY(),
                    gameMap.getMap()
                    )
            );
        }
    }
}
