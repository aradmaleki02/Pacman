package com.pacman.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.pacman.model.User;

public class ChangePasswordScreen implements Screen {
    final Pacman game;
    User user;
    OrthographicCamera camera;
    Texture password;
    Texture back;
    Texture blankWhite;
    Texture blankGray;
    Texture changePassword;
    String enteredPassword = "";
    boolean isCursorOnPassword = false;
    boolean tried;
    boolean successful;

    public ChangePasswordScreen(Pacman game, User user) {
        this.game = game;
        this.user = user;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 800);
        password = new Texture(Gdx.files.internal("password.jpg"));
        blankWhite = new Texture(Gdx.files.internal("blankWhite.jpg"));
        blankGray = new Texture(Gdx.files.internal("blankGray.jpg"));
        back = new Texture(Gdx.files.internal("back.jpg"));
        changePassword = new Texture(Gdx.files.internal("change.jpg"));

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.YELLOW);
        camera.update();
        game.batch.setProjectionMatrix(camera.projection);
        game.batch.begin();
        game.batch.draw(password, -300, 150, 250, 100);
        game.batch.draw(back, -125, -300, 250, 100);
        if (!isCursorOnPassword)
            game.batch.draw(blankWhite, 50, 150, 250, 100);
        else
            game.batch.draw(blankGray, 50, 150, 250, 100);
        game.batch.draw(changePassword, -100, -100, 250, 100);
        handleClick();
    }

    private void handleClick() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3();
            int x = Gdx.input.getX();
            int y = Gdx.input.getY();
            touchPos.set(x, y, 0);
            camera.unproject(touchPos);

            if (x >= 450 && x <= 700 && y >= 250 && y <= 350) {
                //Username
                isCursorOnPassword = false;
            } else if (x >= 100 && x <= 350 && y >= 600 && y <= 700) {
                //Register
                process();
            } else if (x >= 450 && x <= 700 && y >= 600 && y <= 700) {
                game.setScreen(new MainMenu(game));
            }
        }
    }

    private void process() {
    }

    @Override
    public void resize(int width, int height) {

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

    }
}
