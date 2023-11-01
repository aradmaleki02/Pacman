package com.pacman.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.pacman.model.Finisher;
import com.pacman.model.Initializer;
import com.pacman.model.User;


public class MainMenu implements Screen {
    private static boolean initialized = false;
    final Pacman game;
    OrthographicCamera camera;
    Texture register;
    Texture login;
    Texture scoreboard;
    Texture welcome;
    Texture guest;
    Texture mute;
    Texture unmute;
    Texture exit;

    public MainMenu(Pacman game) {
        if (!initialized) {
            try {
                Initializer.addUsers();
            } catch (Exception e) {
                e.printStackTrace();
            }
            initialized = true;
        }
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 800);
        welcome = new Texture(Gdx.files.internal("welcome.jpg"));
        register = new Texture(Gdx.files.internal("register.jpg"));
        login = new Texture(Gdx.files.internal("login.jpg"));
        scoreboard = new Texture(Gdx.files.internal("scoreboard.jpg"));
        guest = new Texture(Gdx.files.internal("guest.jpg"));
        mute = new Texture(Gdx.files.internal("mute.jpg"));
        unmute = new Texture(Gdx.files.internal("unmute.jpg"));
        exit = new Texture(Gdx.files.internal("exit.png"));

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
        game.batch.draw(welcome, -300, 50, 600, 300);
        game.batch.draw(register, -300, -100, 250, 100);
        game.batch.draw(login, 50, -100, 250, 100);
        game.batch.draw(guest, -300, -250, 250, 100);
        game.batch.draw(scoreboard, 50, -250, 250, 100);
        game.batch.draw(exit, 330, 350, 100, 50);

        if (game.enabledSound)
            game.batch.draw(unmute, -400, 350, 50, 50);
        else
            game.batch.draw(mute, -400, 350, 50, 50);
        game.batch.end();

        if (game.enabledSound)
            game.sandstorm.play();
        else
            game.sandstorm.pause();


        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3();
            int x = Gdx.input.getX();
            int y = Gdx.input.getY();
            touchPos.set(x, y, 0);
            camera.unproject(touchPos);

            if (x >= 730 && x <= 800 && y >= 0 && y <= 50) {
                try {
                    Finisher.finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Gdx.app.exit();
            }

            if (x >= 0 && x <= 50 && y >= 0 && y <= 50) {
                game.enabledSound = !game.enabledSound;
            }
            else if (x >= 100 && x <= 350 && y >= 400 && y <= 500) {
                game.setScreen(new RegisterMenu(game));
            }
            else if (x >= 450 && x <= 700 && y >= 400 && y <= 500) {
                game.setScreen(new LoginMenu(game));
            }
            else if (x >= 100 && x <= 350 && y >= 550 && y <= 650) {
                User guest = new User("#guest", "", null, 0);
                game.setScreen(new GameMenu(game, guest, 3, 0));
            }
            else if (x >= 450 && x <= 700 && y >= 550 && y <= 650) {
                game.setScreen(new ScoreBoard(game));
            }
        }
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
