package com.pacman.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.pacman.controller.InputHandler;
import com.pacman.model.Map;
import com.pacman.model.User;

public class RegisterMenu implements Screen {
    final Pacman game;
    OrthographicCamera camera;
    Texture username;
    Texture password;
    Texture register;
    Texture back;
    Texture mute;
    Texture unmute;
    Texture blankWhite;
    Texture blankGray;
    String enteredPassword = "";
    String enteredUsername = "";
    boolean isCursorOnUsername = false;
    boolean isCursorOnPassword = false;
    boolean tried;
    String message;

    public RegisterMenu(Pacman game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 800);
        username = new Texture(Gdx.files.internal("username.jpg"));
        password = new Texture(Gdx.files.internal("password.jpg"));
        register = new Texture(Gdx.files.internal("register.jpg"));
        blankWhite = new Texture(Gdx.files.internal("blankWhite.jpg"));
        blankGray = new Texture(Gdx.files.internal("blankGray.jpg"));
        mute = new Texture(Gdx.files.internal("mute.jpg"));
        unmute = new Texture(Gdx.files.internal("unmute.jpg"));
        back = new Texture(Gdx.files.internal("back.jpg"));
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
        game.batch.draw(username, -300, 50, 250, 100);
        game.batch.draw(password, -300, -150, 250, 100);

        if (!isCursorOnUsername)
            game.batch.draw(blankWhite, 50, 50, 250, 100);
        else
            game.batch.draw(blankGray, 50, 50, 250, 100);

        if (!isCursorOnPassword)
            game.batch.draw(blankWhite, 50, -150, 250, 100);
        else
            game.batch.draw(blankGray, 50, -150, 250, 100);

        game.font.draw(game.batch, enteredUsername, 50, 100);
        game.font.draw(game.batch, enteredPassword, 50, -100);

        if (game.enabledSound)
            game.batch.draw(unmute, -400, 350, 50, 50);
        else
            game.batch.draw(mute, -400, 350, 50, 50);
        game.batch.draw(register, -300, -300, 250 , 100);
        game.batch.draw(back, 50, -300, 250 , 100);

        if (tried)
            game.font.draw(game.batch, message, -(message.length()/2) * 15, 350);

        game.batch.end();


        if (game.enabledSound)
            game.sandstorm.play();
        else
            game.sandstorm.pause();


        handleClick();
        if (isCursorOnUsername) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)){
                if (!enteredUsername.isEmpty())
                    enteredUsername = enteredUsername.substring(0, enteredUsername.length() - 1);
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY))
                enteredUsername += InputHandler.keyToString();
        }
        if (isCursorOnPassword) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)){
                if (!enteredPassword.isEmpty())
                    enteredPassword = enteredPassword.substring(0, enteredPassword.length() - 1);
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY))
                enteredPassword += InputHandler.keyToString();
        }
    }

    private void handleClick() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3();
            int x = Gdx.input.getX();
            int y = Gdx.input.getY();
            touchPos.set(x, y, 0);
            camera.unproject(touchPos);

            if (x >= 0 && x <= 50 && y >= 0 && y <= 50) {
                game.enabledSound = !game.enabledSound;
            } else if (x >= 450 && x <= 700 && y >= 250 && y <= 350) {
                //Username
                isCursorOnUsername = true;
                isCursorOnPassword = false;
            } else if (x >= 450 && x <= 700 && y >= 450 && y <= 550) {
                //Password
                isCursorOnPassword = true;
                isCursorOnUsername = false;
            } else if (x >= 100 && x <= 350 && y >= 600 && y <= 700) {
                //Register
                process();
            } else if (x >= 450 && x <= 700 && y >= 600 && y <= 700) {
                game.setScreen(new MainMenu(game));
            }
        }
    }

    private void process() {
        tried = true;
        if (enteredUsername.isEmpty() || enteredPassword.isEmpty()) {
            message = "Empty Username Or Password!";
            return;
        }
        for (User user : User.getAllUsers()) {
            if (user.getUsername().equals(enteredUsername)) {
                message = "Username Exists!";
                return;
            }
        }
        message = "Successful!";
        new User(enteredUsername, enteredPassword, new Map(), 0);
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
