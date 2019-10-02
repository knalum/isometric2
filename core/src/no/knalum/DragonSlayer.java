package no.knalum;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

import no.knalum.actor.Player;
import no.knalum.actor.PlayerListener;
import no.knalum.actor.Slime;
import no.knalum.map.CameraInputProcessor;
import no.knalum.map.IsometricMapRenderer;
import no.knalum.map.TiledMapLoader;

public class DragonSlayer extends ApplicationAdapter {

    private SpriteBatch batch;
    private IsometricMapRenderer mapRenderer;
    private OrthographicCamera camera;
    private Stage stage;
    private SpriteBatch hudBatch;
    private BitmapFont debugText;
    private PlayerListener playerListener;
    private GameMap map;
    private EnemySpawner enemySpawner;
    private EnemyGenerator enemyGenerator;


    @Override
    public void create() {
        batch = new SpriteBatch();
        hudBatch = new SpriteBatch();
        debugText = new BitmapFont();
        this.stage = new Stage();

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        this.map = new TiledMapLoader(stage).load("map/map.tmx");
        this.mapRenderer = new IsometricMapRenderer(map, camera);

        Player player = new Player(1, 1, map.getMap());
        map.addActorToWorld(player);

        this.playerListener = new PlayerListener(player, map.getMap());
        Gdx.input.setInputProcessor(new InputMultiplexer(
                playerListener,
                new CameraInputProcessor(camera)
        ));
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        playerListener.handleMovement();
        stage.act();

        //camera.position.x = stage.getActors().get(0).getX();
        //camera.position.y = stage.getActors().get(0).getY();


        batch.begin();
        mapRenderer.render(batch);
        batch.end();

        hudBatch.begin();
        debugText.draw(hudBatch,
                "x=" + CameraInputProcessor.tileX + " y= " + CameraInputProcessor.tileY + " draw=N/A" + " zoom=" + camera.zoom
                , 10, 40);
        hudBatch.end();
    }
}
