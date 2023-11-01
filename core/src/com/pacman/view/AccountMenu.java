package com.pacman.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.pacman.model.User;

import java.io.File;

public class AccountMenu implements Screen {
    User user;
    final Pacman game;
    OrthographicCamera camera;
    Texture logout;
    Texture deleteAccount;
    Texture changePassword;
    Texture start;
    Texture plus;
    Texture minus;
    boolean hardMode;
    int lives = 2;

    public AccountMenu(User user, Pacman game) {
        this.user = user;
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 800);
        logout = new Texture(Gdx.files.internal("logout.jpg"));
        changePassword = new Texture(Gdx.files.internal("change.jpg"));
        deleteAccount = new Texture(Gdx.files.internal("delete.jpg"));
        logout = new Texture(Gdx.files.internal("logout.jpg"));
        start = new Texture(Gdx.files.internal("start.jpg"));
        plus = new Texture(Gdx.files.internal("plus.png"));
        minus = new Texture(Gdx.files.internal("minus.png"));
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
        if (!hardMode) {
            game.font.draw(game.batch, "Hard mode : Off", -125, 100);
        } else {
            game.font.draw(game.batch, "Hard mode : On", -125, 100);
        }
        game.font.draw(game.batch, "Welcome " + user.getUsername() + "!", -80, 360);
        game.font.draw(game.batch, "Lives : " + lives, -125, 50);
        game.batch.draw(plus, 25, 15, 50, 50);
        game.batch.draw(minus, 85, 10, 50, 50);
        game.batch.draw(start, -125, -100, 250, 100);
        game.batch.draw(changePassword, -300, 200, 250, 100);
        game.batch.draw(deleteAccount, 50, 200, 250, 100);
        game.batch.draw(logout, -125, -300, 250, 100);
        game.batch.end();
        handleLifeSelection();
        handleLogout();
        handleHardMode();
        handleDelete();
        handleChangePassword();
        handleStart();
    }

    private void handleChangePassword() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3();
            int x = Gdx.input.getX();
            int y = Gdx.input.getY();
            touchPos.set(x, y, 0);
            camera.unproject(touchPos);
            if (x >= 100 && x <= 350 && y >= 100 && y <= 200) {
                game.setScreen(new ChangePasswordScreen(game, user));
            }
        }
    }

    private void handleHardMode() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3();
            int x = Gdx.input.getX();
            int y = Gdx.input.getY();
            touchPos.set(x, y, 0);
            camera.unproject(touchPos);
            if (x >= 275 && x <= 500 && y >= 250 && y <= 350) {
                hardMode = !hardMode;
            }
        }
    }

    private void handleStart() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3();
            int x = Gdx.input.getX();
            int y = Gdx.input.getY();
            System.out.println(x + " " + y);
            touchPos.set(x, y, 0);
            camera.unproject(touchPos);
            if (x >= 275 && x <= 525 && y >= 400 && y <= 500) {
                if (!hardMode)
                    game.setScreen(new GameMenu(game, user, lives, 0));
                else
                    game.setScreen(new GameMenu(game, user, lives, 0, true));
            }
        }
    }

    private void handleDelete() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3();
            int x = Gdx.input.getX();
            int y = Gdx.input.getY();
            touchPos.set(x, y, 0);
            camera.unproject(touchPos);
            if (x >= 450 && x <= 700 && y >= 100 && y <= 200) {
                User.setLoggedInUser(null);
                User.deleteUser(user);
                game.setScreen(new MainMenu(game));
            }
        }
    }

    private void handleLogout() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3();
            int x = Gdx.input.getX();
            int y = Gdx.input.getY();
            touchPos.set(x, y, 0);
            camera.unproject(touchPos);
            if (x >= 275 && x <= 525 && y >= 600 && y <= 700) {
                game.setScreen(new LoginMenu(game));
            }
        }

    }

    private void handleLifeSelection() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3();
            int x = Gdx.input.getX();
            int y = Gdx.input.getY();
            touchPos.set(x, y, 0);
            camera.unproject(touchPos);
            if (x >= 425 && x <= 475 && y >= 325 && y <= 375) {
                if (lives < 5)
                    lives ++;
            }
            if (x >= 485 && x <= 535 && y >= 325 && y <= 375) {
                if (lives > 2)
                    lives --;
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
