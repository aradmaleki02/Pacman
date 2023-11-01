package com.pacman.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.pacman.controller.GifDecoder;
import com.pacman.model.Map;
import com.pacman.model.User;

import java.util.Random;

public class GameMenu implements Screen {
    final Pacman game;
    OrthographicCamera camera;
    User user;
    Animation<TextureRegion> kashimanImage;
    Animation<TextureRegion> redImage;
    Animation<TextureRegion> blueImage;
    Animation<TextureRegion> yellowImage;
    Animation<TextureRegion> greenImage;
    Rectangle[][] cells = new Rectangle[20][20];
    boolean[][] isWall = new boolean[20][20];
    boolean[][] hasEaten = new boolean[20][20];
    boolean[][] hasEatenRB = new boolean[20][20];
    boolean[][] containsRB = new boolean[20][20];
    Rectangle kashiman;
    Rectangle red;
    Rectangle blue;
    Rectangle yellow;
    Rectangle green;
    Rectangle[][] dots = new Rectangle[20][20];
    Rectangle[][] redBulls = new Rectangle[20][20];
    Texture dotImage;
    Texture redBull;
    Texture gray;
    Texture back;
    Texture pause;
    Texture square;
    Texture filledSquare;
    Texture heart;
    Map map = Map.createRandomMap();
    float elapsed;
    boolean isPaused;
    boolean hard;
    boolean power;
    int pacx = 10 * 800 / 19 - 400 + 2;
    int pacy = 400 - 11 * 35 + 2;
    int greenx = 400 - 800 / 19;
    int greeny = -265;
    int bluex = -400;
    int bluey = -265;
    int yellowx = 400 - 800 / 19;
    int yellowy = 365;
    int redx = -400;
    int redy = 365;
    float width = 800f/19;
    float height = 35;
    int lives;
    int score;
    long timePassed;
    long timePassedFromPowerUp;
    Sound eat;
    Sound lose;

    public GameMenu(Pacman game, User user, int lives, int score) {
        this.lives = lives;
        this.score = score;
        this.user = user;
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 800);
        kashimanImage = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("kashiman.gif").read());
        redImage = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("red.gif").read());
        yellowImage = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("yellow.gif").read());
        blueImage = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("blue.gif").read());
        greenImage = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("green.gif").read());
        dotImage = new Texture(Gdx.files.internal("dot.png"));
        redBull = new Texture(Gdx.files.internal("redbull.png"));
        for (int i = 1; i < 20; i++) {
            for (int j = 1; j < 20; j++) {
                dots[i][j] = new Rectangle((i - 1) * 800f / 19 - 400, 400 - j * 35, 10, 10);
                redBulls[i][j] = new Rectangle((i - 1) * 800f / 19 - 400, 400 - j * 35, 10, 10);
                Random rn = new Random();
                int number = rn.nextInt( 100);
                if (number == 0)
                    containsRB[i][j] = true;
            }
        }
        kashiman = new Rectangle(pacx, pacy, width - 5, height - 5);
        red = new Rectangle(redx, redy, width - 5, height - 5);
        blue = new Rectangle(bluex, bluey, width - 5, height - 5);
        yellow = new Rectangle(yellowx, yellowy, width - 5, height - 5);
        green = new Rectangle(greenx, greeny, width - 5, height - 5);
        filledSquare = new Texture(Gdx.files.internal("filledsquare.png"));
        square = new Texture(Gdx.files.internal("square.png"));
        gray = new Texture(Gdx.files.internal("gray.jpg"));
        back = new Texture(Gdx.files.internal("back.jpg"));
        heart = new Texture(Gdx.files.internal("heart.jpg"));
        pause = new Texture(Gdx.files.internal("pause.png"));
        eat = Gdx.audio.newSound(Gdx.files.internal("eat.mp3"));
        lose = Gdx.audio.newSound(Gdx.files.internal("lose.mp3"));
    }

    public GameMenu(Pacman game, User user, int lives, int score, boolean hard) {
        this.hard = hard;
        this.lives = lives;
        this.score = score;
        this.user = user;
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 800);
        kashimanImage = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("kashiman.gif").read());
        redImage = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("red.gif").read());
        yellowImage = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("yellow.gif").read());
        blueImage = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("blue.gif").read());
        greenImage = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("green.gif").read());
        dotImage = new Texture(Gdx.files.internal("dot.png"));
        redBull = new Texture(Gdx.files.internal("redbull.png"));
        for (int i = 1; i < 20; i++) {
            for (int j = 1; j < 20; j++) {
                dots[i][j] = new Rectangle((i - 1) * 800f / 19 - 400, 400 - j * 35, 10, 10);
                redBulls[i][j] = new Rectangle((i - 1) * 800f / 19 - 400, 400 - j * 35, 10, 10);
                Random rn = new Random();
                int number = rn.nextInt( 100);
                if (number == 0)
                    containsRB[i][j] = true;
            }
        }
        kashiman = new Rectangle(pacx, pacy, width - 5, height - 5);
        red = new Rectangle(redx, redy, width - 5, height - 5);
        blue = new Rectangle(bluex, bluey, width - 5, height - 5);
        yellow = new Rectangle(yellowx, yellowy, width - 5, height - 5);
        green = new Rectangle(greenx, greeny, width - 5, height - 5);
        filledSquare = new Texture(Gdx.files.internal("filledsquare.png"));
        square = new Texture(Gdx.files.internal("square.png"));
        gray = new Texture(Gdx.files.internal("gray.jpg"));
        back = new Texture(Gdx.files.internal("back.jpg"));
        heart = new Texture(Gdx.files.internal("heart.jpg"));
        pause = new Texture(Gdx.files.internal("pause.png"));
        eat = Gdx.audio.newSound(Gdx.files.internal("eat.mp3"));
        lose = Gdx.audio.newSound(Gdx.files.internal("lose.mp3"));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        elapsed += Gdx.graphics.getDeltaTime();
        ScreenUtils.clear(Color.LIGHT_GRAY);
        camera.update();
        game.batch.setProjectionMatrix(camera.projection);
        showMap();
        game.batch.begin();
        game.batch.draw(kashimanImage.getKeyFrame(elapsed), kashiman.x, kashiman.y, width - 5, height - 5);
        if (!power) {
            game.batch.draw(redImage.getKeyFrame(elapsed), red.x, red.y, width - 5, height - 5);
            game.batch.draw(blueImage.getKeyFrame(elapsed), blue.x, blue.y, width - 5, height - 5);
            game.batch.draw(greenImage.getKeyFrame(elapsed), green.x, green.y, width - 5, height - 5);
            game.batch.draw(yellowImage.getKeyFrame(elapsed), yellow.x, yellow.y, width - 5, height - 5);
        } else {
            game.batch.draw(gray, red.x, red.y, width - 5, height - 5);
            game.batch.draw(gray, blue.x, blue.y, width - 5, height - 5);
            game.batch.draw(gray, green.x, green.y, width - 5, height - 5);
            game.batch.draw(gray, yellow.x, yellow.y, width - 5, height - 5);
            if (System.nanoTime() - timePassedFromPowerUp > 5000000000L)
                power = false;
        }
        handlePauseAndBack();
        handleMapReset();
        if (!isPaused) {
            handleInput();
            handleDotsEaten();
            handleRedBullsEaten();
            if (System.nanoTime() - timePassed > 100000000) {
                handleGhostMoves();
                timePassed = System.nanoTime();
            }
        }
        setInScreen(kashiman);
        setInScreen(red);
        setInScreen(blue);
        setInScreen(yellow);
        setInScreen(green);
        handleDeath();
        game.batch.draw(pause, -400, -400, 100, 100);
        game.batch.draw(back, 200, -400, 200, 100);

        if (lives >= 1)
            game.batch.draw(heart, -250, -400, 50, 50);
        if (lives >= 2)
            game.batch.draw(heart, -200, -400, 50, 50);
        if (lives >= 3)
            game.batch.draw(heart, -150, -400, 50, 50);
        if (lives >= 4)
            game.batch.draw(heart, -100, -400, 50, 50);
        if (lives >= 5)
            game.batch.draw(heart, -50, -400, 50, 50);

        game.font.draw(game.batch, "SCORE : " + score, -250, -300);
        game.batch.end();
    }

    private void handleRedBullsEaten() {
        for (int i = 1; i < 20; i++) {
            for (int j = 1; j < 20; j++) {
                if (kashiman.overlaps(redBulls[i][j]) && !hasEatenRB[i][j] && containsRB[i][j]) {
                    hasEatenRB[i][j] = true;
                    score += 20;
                    power = true;
                    timePassedFromPowerUp = System.nanoTime();
                    if (game.enabledSound)
                        eat.play();
                }
            }
        }
    }

    private void handleMapReset() {
        boolean isDotRemaining = false;
        for (int i = 1; i < 20; i++) {
            for (int j = 1; j < 20; j++) {
                if (!hasEaten[i][j])
                    isDotRemaining = true;
            }
        }
        if (!isDotRemaining) {
            game.setScreen(new GameMenu(game, user, lives, score));
        }
    }

    private void handleDeath() {
        if (kashiman.overlaps(red) || kashiman.overlaps(blue) || kashiman.overlaps(green) || kashiman.overlaps(yellow)) {
            lives--;
            if (game.enabledSound)
                lose.play();
            if (lives > 0) {
                game.setScreen(new GameMenu(game, user, lives, score));
            } else {
                user.setMaxScore(Math.max(user.getMaxScore(), score));
                game.setScreen(new AccountMenu(user, game));
            }
        }
    }

    private void handlePauseAndBack() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3();
            int x = Gdx.input.getX();
            int y = Gdx.input.getY();
            touchPos.set(x, y, 0);
            camera.unproject(touchPos);
            if (x >= 0 && x <= 100 && y >= 700 && y <= 800) {
                isPaused = !isPaused;
            }
            if (x >= 550 && x <= 800 && y >= 700 && y <= 800) {
                User user = User.getLoggedInUser();
                if (user != null)
                    game.setScreen(new AccountMenu(user, game));
                else
                    game.setScreen(new MainMenu(game));
            }
        }
    }

    private void handleGhostMoves() {
        handleAGhost(red);
        handleAGhost(blue);
        handleAGhost(yellow);
        handleAGhost(green);
    }

    private void handleAGhost(Rectangle rectangle) {
        Random rn = new Random();
        int a;
        int speed = 60;
        if (hard)
            speed = 160;
        a = rn.nextInt(2);
        if (a == 0) {
            rectangle.x -= speed * Gdx.graphics.getDeltaTime();
            for (int i = 1; i < 20; i++) {
                for (int j = 1; j < 20; j++) {
                    if (rectangle.overlaps(cells[i][j]) && isWall[i][j]) {
                        rectangle.x += speed * Gdx.graphics.getDeltaTime();
                        break;
                    }
                }
            }
        }
        if (a == 1) {
            rectangle.x += speed * Gdx.graphics.getDeltaTime();
            for (int i = 1; i < 20; i++) {
                for (int j = 1; j < 20; j++) {
                    if (rectangle.overlaps(cells[i][j]) && isWall[i][j]) {
                        rectangle.x -= speed * Gdx.graphics.getDeltaTime();
                        break;
                    }
                }
            }
        }
        a = rn.nextInt(2);
        if (a == 0) {
            rectangle.y -= speed * Gdx.graphics.getDeltaTime();
            for (int i = 1; i < 20; i++) {
                for (int j = 1; j < 20; j++) {
                    if (rectangle.overlaps(cells[i][j]) && isWall[i][j]) {
                        rectangle.y += speed * Gdx.graphics.getDeltaTime();
                        break;
                    }
                }
            }
        }
        if (a == 1) {
            rectangle.y += speed * Gdx.graphics.getDeltaTime();
            for (int i = 1; i < 20; i++) {
                for (int j = 1; j < 20; j++) {
                    if (rectangle.overlaps(cells[i][j]) && isWall[i][j]) {
                        rectangle.y -= speed * Gdx.graphics.getDeltaTime();
                        break;
                    }
                }
            }
        }
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            kashiman.x -= 80 * Gdx.graphics.getDeltaTime();
            for (int i = 1; i < 20; i++) {
                for (int j = 1; j < 20; j++) {
                    if (kashiman.overlaps(cells[i][j]) && isWall[i][j]) {
                        kashiman.x += 80 * Gdx.graphics.getDeltaTime();
                        break;
                    }
                }
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            kashiman.x += 80 * Gdx.graphics.getDeltaTime();
            for (int i = 1; i < 20; i++) {
                for (int j = 1; j < 20; j++) {
                    if (kashiman.overlaps(cells[i][j]) && isWall[i][j]) {
                        kashiman.x -= 80 * Gdx.graphics.getDeltaTime();
                        break;
                    }
                }
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            kashiman.y += 80 * Gdx.graphics.getDeltaTime();
            for (int i = 1; i < 20; i++) {
                for (int j = 1; j < 20; j++) {
                    if (kashiman.overlaps(cells[i][j]) && isWall[i][j]) {
                        kashiman.y -= 80 * Gdx.graphics.getDeltaTime();
                        break;
                    }
                }
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            kashiman.y -= 80 * Gdx.graphics.getDeltaTime();
            for (int i = 1; i < 20; i++) {
                for (int j = 1; j < 20; j++) {
                    if (kashiman.overlaps(cells[i][j]) && isWall[i][j]) {
                        kashiman.y += 80 * Gdx.graphics.getDeltaTime();
                        break;
                    }
                }
            }
        }
    }

    private void setInScreen(Rectangle rectangle) {
        if (rectangle.x < -400)
            rectangle.x = -400;
        if (rectangle.x > 360)
            rectangle.x = 360;
        if (rectangle.y < -265)
            rectangle.y = -265;
        if (rectangle.y > 365)
            rectangle.y = 365;
    }

    private void handleDotsEaten() {
        for (int i = 1; i < 20; i++) {
            for (int j = 1; j < 20; j++) {
                if (kashiman.overlaps(dots[i][j]) && !hasEaten[i][j]) {
                    hasEaten[i][j] = true;
                    score += 5;
                    if (game.enabledSound)
                        eat.play();
                }
            }
        }
    }

    private void showMap() {
        char[][] maze = map.getMaze();
        game.batch.begin();
        for (int i = 1; i < 20; i++) {
            for (int j = 1; j < 20; j++) {
                cells[i][j] = new Rectangle((i - 1) * 800f / 19 - 400, 400 - j * 35, 800f / 19, 35);
                if (maze[i][j] == '1') {
                    isWall[i][j] = true;
                    game.batch.draw(filledSquare, (i - 1) * 800f / 19 - 400, 400 - j * 35, 800f / 19, 35);
                }
                else {
                    isWall[i][j] = false;
                    game.batch.draw(square, (i - 1) * 800f / 19 - 400, 400 - j * 35, 800f / 19, 35);
                    if (!hasEaten[i][j]) {
                        game.batch.draw(dotImage, (i - 1) * 800f / 19 - 400 + 15, 410 - j * 35, 10, 10);
                    }
                    if (containsRB[i][j] && !hasEatenRB[i][j]) {
                        game.batch.draw(redBull, (i - 1) * 800f / 19 - 400, 400 - j * 35, 35, 30);
                    }
                }
            }
        }
        game.batch.end();
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
