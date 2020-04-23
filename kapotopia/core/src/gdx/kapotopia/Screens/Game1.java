package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.HashSet;
import java.util.List;

import gdx.kapotopia.AssetsManaging.AssetDescriptors;
import gdx.kapotopia.AssetsManaging.AssetsManager;
import gdx.kapotopia.Game1.GameController;
import gdx.kapotopia.Game1.MireilleListener;
import gdx.kapotopia.Game1.RenderController;
import gdx.kapotopia.Game1.SoundController;
import gdx.kapotopia.Game1.VirusContainer;
import gdx.kapotopia.Kapotopia;

public class Game1 implements Screen, MireilleListener {

    // General Variables
    private Kapotopia game;
    private Stage stage;

    private float stateTime;

    // Constants

    private final String TAG = this.getClass().getSimpleName();

    private GameController gameController;
    private RenderController renderController;
    private SoundController soundController;

    /* *******************************************************
     *                      M E T H O D S                   *
     ******************************************************* */

    /**
     * Constructeur
     * @param game
     */
    public Game1(final Kapotopia game) {
        // Initialize global variables
        this.game = game;
        this.stage = new Stage(game.viewport);

        this.gameController = new GameController(game, this);

        /* We need to load asset after calling creating the gamecontroller because
         * It's in that method where we load the texture paths of the viruses.
         * Therefore, the init method must be called after loadAssets since it's set up Virus textures
         */
        loadAssets(this.gameController.getIst(), this.gameController.getFake(), this.gameController.getMaybeIst());

        this.gameController.init();

        this.renderController = new RenderController(game, this, stage);
        this.soundController = new SoundController(game,this);

        /* Setting up the stage */
        stateTime = 0f;

        AssetsManager.getInstance().addStage(stage, TAG);
    }

    private void loadAssets(List<VirusContainer> ist, List<VirusContainer> fake, List<VirusContainer> maybeist) {
        // Graphics
        game.ass.load(AssetDescriptors.MI_HAPPY);
        game.ass.load(AssetDescriptors.MI_UNI);
        for (VirusContainer v : ist) {
            game.ass.load(v.getTexture());
        }
        for (VirusContainer v : fake) {
            game.ass.load(v.getTexture());
        }
        for (VirusContainer v : maybeist) {
            game.ass.load(v.getTexture());
        }
        // Musics
        game.ass.load(AssetDescriptors.MUSIC_GAME1);
        game.ass.load(AssetDescriptors.MUSIC_J);
        // Sounds
        game.ass.load(AssetDescriptors.SOUND_FAIL);
        game.ass.load(AssetDescriptors.SOUND_JUMP_V1);
        game.ass.load(AssetDescriptors.SOUND_JUMP_V2);
        game.ass.load(AssetDescriptors.SOUND_PUNCH);
        game.ass.load(AssetDescriptors.SOUND_FAIL);
        game.ass.load(AssetDescriptors.SOUND_COIN);
        game.ass.load(AssetDescriptors.SOUND_SUCCESS);

        game.ass.finishLoading();
        Gdx.app.log(TAG, game.ass.getDiagnostics());
    }

    /* *******************************************************
     *              S C R E E N   M E T H O D S              *
     ******************************************************* */

    @Override
    public void show() {
        boolean isRenderControllerNull = false;
        if (renderController == null)
            isRenderControllerNull = true;
        Gdx.app.debug(TAG, "renderController is null " + isRenderControllerNull);
        gameController.updateOnShow(stage);
        soundController.playAtShow();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stateTime += delta;

        renderController.update(stateTime);
        gameController.update(delta, stage);

        stage.draw();

        if(!gameController.isLetsGoAppeared()) {
            renderController.renderFirstAnimation(stateTime);
        }

        gameController.makeJojoAppear(delta);
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override
    public void pause() {
        renderController.updateAtPause();
        gameController.updateOnPause();
        soundController.playAtPause();
        Gdx.app.debug(TAG, "game paused - isPaused is true");
    }

    @Override
    public void resume() {
        gameController.updateOnResume();
        Gdx.app.debug(TAG, "game resumed - isPaused is false");
    }

    public void resumeFromPause() {
        renderController.updateWhenResumeFromPause();
        gameController.resumeFromPause();
        soundController.playAtResumeFromPause();
    }

    @Override
    public void hide() {
        gameController.updateOnHide();
        soundController.playAtHide();
    }

    @Override
    public void dispose() {
        AssetsManager.getInstance().disposeStage(TAG);
        renderController.dispose();
    }

    // Labels

    public void setNewEnnemiLabelPosition(float x, float y){
        renderController.setNewEnnemiLabelPosition(x,y);
    }
    public void changeEnnemiLabel(String newName) {
        renderController.changeEnnemiLabel(newName);
    }

    // Game mechanics

    public HashSet<VirusContainer> getMissedIST() {
        return gameController.getMissedIsts();
    }


    /**
     * Method used by lifeListener
     * @param life the new life value given to MireilleLife
     */
    @Override
    public void lifeChanged(byte life) {
        soundController.playWhenLifeChange();
        gameController.lifeChanged(life);
    }
    @Override
    public void scoreChanged(int score) {
        gameController.scoreChanged(score);
    }

    /* ********************************************
     *       G E T   C O N T R O L L E R S        *
     ******************************************** */

    public GameController getGameController() {
        return gameController;
    }

    public RenderController getRenderController() {
        return renderController;
    }

    public SoundController getSoundController() {
        return soundController;
    }

    public Kapotopia getGame() {
        return game;
    }
}
