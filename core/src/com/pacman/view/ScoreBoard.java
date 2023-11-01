package com.pacman.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.pacman.model.User;

import java.util.ArrayList;

public class ScoreBoard implements Screen {
    final Pacman game;
    OrthographicCamera camera;
    Texture back;
    Texture mute;
    Texture unmute;
    ArrayList<User> topTen;

    public ScoreBoard(Pacman game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 800);
        mute = new Texture(Gdx.files.internal("mute.jpg"));
        unmute = new Texture(Gdx.files.internal("unmute.jpg"));
        back = new Texture(Gdx.files.internal("back.jpg"));
        topTen = getAllUsersSorted();
    }

    private ArrayList<User> getAllUsersSorted() {
        ArrayList<User> arrayList = User.getAllUsers();
        arrayList.sort(User.comparator);
        return arrayList;
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
        for (int i = 0; i < 10; i++) {
            game.font.draw(game.batch, (i + 1) + " : ", -200, 380 - (i * 50));
            if (i < topTen.size()){
                String username = topTen.get(i).getUsername();
                int score = topTen.get(i).getMaxScore();
                game.font.draw(game.batch, username + "    ---->    " + score, 0, 380 - (i * 50));
            }
        }
        if (game.enabledSound)
            game.batch.draw(unmute, -400, 350, 50, 50);
        else
            game.batch.draw(mute, -400, 350, 50, 50);
        game.batch.draw(back, -125, -300, 250 , 100);

        game.batch.end();


        if (game.enabledSound)
            game.sandstorm.play();
        else
            game.sandstorm.pause();
        handleClick();
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
            } else if (x >= 275 && x <= 525 && y >= 600 && y <= 700) {
                game.setScreen(new MainMenu(game));
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
