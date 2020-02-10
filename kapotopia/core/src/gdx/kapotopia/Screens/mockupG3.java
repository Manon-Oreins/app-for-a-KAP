package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.I18NBundle;

import gdx.kapotopia.Helpers.Builders.ImageBuilder;
import gdx.kapotopia.Helpers.Builders.LabelBuilder;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.Localisation;
import gdx.kapotopia.ScreenType;

public class mockupG3 extends CinematicScreen {

    public mockupG3(final Kapotopia game) {
        super(game, new Stage(game.viewport), "mockupG3");

        Localisation loc = Localisation.getInstance();

        final Label[][] labels = new Label[][] {
                {

                },
                {
                    new LabelBuilder(loc.getString("game3_instr"))
                            .withPosition(Kapotopia.ONE_CHARACTER_STD_WIDTH, (game.viewport.getWorldHeight() / 2))
                            .withWidth(game.viewport.getWorldWidth() - (2 * Kapotopia.ONE_CHARACTER_STD_WIDTH))
                            .withHeight(Kapotopia.ONE_CHARACTER_STD_HEIGHT * 10).isWrapped(true)
                            .build()
                }
        };

        final Image[][] images = new Image[][] {
                {
                    new ImageBuilder().withTexture("game3/Monde2Ecran2.png").build()
                },
                {
                    new ImageBuilder().withTexture("game3/Monde2Ecran3.png").build()
                }
        };

        this.applyBundle(new ParameterBundleBuilder(ScreenType.GAME3)
        .withImages(images).withLabels(labels).withFinishBtn(false));
    }

    @Override
    public void show() {
        setUpInputProcessor();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
}
