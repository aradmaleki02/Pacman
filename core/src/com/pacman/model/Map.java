package com.pacman.model;

import java.util.Random;

public class Map {
    private char[][] maze;

    public Map() {
        maze = new char[41][41];
    }

    public static Map createRandomMap() {
        Map map = new Map(MazeCreator.makeNewValidMaze(10, 10));
        for (int i = 0; i < 21; i++) {
            for (int j = 0; j < 21; j++) {
                if (map.maze[i][j] == '1') {
                    Random rn = new Random();
                    int answer = rn.nextInt(10);
                    if (answer > 6)
                        map.maze[i][j] = '0';
                }
            }
        }
        map.maze[1][1] = 'r';
        map.maze[1][19] = 'b';
        map.maze[19][1] = 'g';
        map.maze[19][19] = 'y';
        map.maze[11][11] = 'p';
        return map;
    }

    public Map(char[][] maze) {
        this.maze = maze;
    }

    public char[][] getMaze() {
        return maze;
    }
}
