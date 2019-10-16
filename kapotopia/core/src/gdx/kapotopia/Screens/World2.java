package gdx.kapotopia.Screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.Timer;

import gdx.kapotopia.*;

public class World2 implements Screen {

    private Kapotopia game;
    private Stage stage;

    private Sound gameStart;

    public World2(final Kapotopia game) {

        this.game = game;
        Image imgFond = new Image(AssetsManager.getInstance().getTextureByPath("FondNiveauBlanc2.png"));
        stage = new Stage(game.viewport);

        stage.addActor(imgFond);

        this.gameStart = AssetsManager.getInstance().getSoundByPath("sound/bruitage/plasterbrain_game-start.ogg");

        TextButton.TextButtonStyle style = Utils.getStyleFont("SEASRN__.ttf");

        I18NBundle languageStrings = I18NBundle.createBundle(Gdx.files.internal("strings/strings"));
        String instr_string = languageStrings.get("game3_instr");

        Label instr = new Label(instr_string, new Label.LabelStyle(style.font, Color.BLACK));
        float x = game.viewport.getWorldWidth() / 12f;
        float y = game.viewport.getWorldHeight()*3 / 4;
        instr.setPosition(x,y);

        final Button play = new TextButton("Play", style);
        x = game.viewport.getWorldWidth() / 2.5f;
        y = game.viewport.getWorldHeight() / 4;
        play.setPosition(x,y);
        play.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameStart.play();
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        game.changeScreen(ScreenType.GAME3);
                    }
                },2f);
            }
        });

        stage.addActor(instr);
        stage.addActor(play);

        InputMultiplexer iM = new InputMultiplexer();
        iM.addProcessor(new StandardInputAdapter(this,game));
        iM.addProcessor(stage);

        Gdx.input.setInputProcessor(iM);

        AssetsManager.getInstance().addStage(stage, "world2");
    }

    @Override
    public void show() {
        //In case there are problems to restart the game where it was left after going to another screen and returning, it could maybe be solved by setting the Input Processor (Gdx.input.setInputProcessor(iM);) here and not when the game is first created
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        AssetsManager.getInstance().disposeStage("world2");
    }
}
