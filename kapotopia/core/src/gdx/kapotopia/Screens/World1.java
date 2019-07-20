package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import gdx.kapotopia.AssetsManager;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.ScreenType;
import gdx.kapotopia.StandardInputAdapter;
import gdx.kapotopia.Utils;

public class World1 implements Screen {

    private Kapotopia game;
    private Stage stage;

    private Sound clic;

    public World1(final Kapotopia game) {
        this.game = game;
        Image imgFond = new Image(AssetsManager.getInstance().getTextureByPath("FondNiveauBlanc2.png"));
        stage = new Stage(game.viewport);
        stage.addActor(imgFond);

        this.clic = AssetsManager.getInstance().getSoundByPath("sound/bruitage/kickhat__open-button-2.wav");

        TextButton.TextButtonStyle style = Utils.getStyleFont("SEASRN__.ttf");

        Button game1 = new TextButton("Game 1", style);
        Button game2 = new TextButton("Game 2", style);
        final float x = game.viewport.getWorldWidth() / 2.5f;
        float y = game.viewport.getWorldHeight() * 0.6f;
        game1.setPosition(x,y);
        y = game.viewport.getWorldHeight() * 0.3f;
        game2.setPosition(x,y);

        game1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                clic.play();
                game.changeScreen(ScreenType.MOCKUPG1);
            }
        });

        game2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                clic.play();
                game.changeScreen(ScreenType.GAME2);
            }
        });

        stage.addActor(imgFond);
        stage.addActor(game1);
        stage.addActor(game2);

        InputMultiplexer im = new InputMultiplexer();
        im.addProcessor(new StandardInputAdapter(this, game));

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
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
        stage.dispose();
    }
}
