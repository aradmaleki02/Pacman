package com.pacman.model;

import java.awt.*;
import java.util.*;

public class MazeCreator {

    static void DFS(int x, int y, boolean[][] visited, char[][] maze) {
        visited[x][y] = true;
        ArrayList<Point> adjacent = getNeighbors(x, y);
        Collections.shuffle(adjacent, new Random());
        for (Point point : adjacent) {
            if (!visited[point.x][point.y]) {
                if (x == point.x && y < point.y)
                    maze[2 * x - 1][2 * y] = '0';
                if (x == point.x && y > point.y)
                    maze[2 * x - 1][2 * y - 2] = '0';
                if (y == point.y && x < point.x)
                    maze[2 * x][2 * y - 1] = '0';
                if (y == point.y && x > point.x)
                    maze[2 * x - 2][2 * y - 1] = '0';
                DFS(point.x, point.y, visited, maze);
            }
        }
    }

    private static ArrayList<Point> getNeighbors(int x, int y) {
        ArrayList<Point> adjacent = new ArrayList<>();
        Point left = new Point();
        Point right = new Point();
        Point up = new Point();
        Point down = new Point();
        left.x = x - 1;
        left.y = y;
        right.x = x + 1;
        right.y = y;
        up.x = x;
        up.y = y - 1;
        down.x = x;
        down.y = y + 1;
        adjacent.add(left);
        adjacent.add(right);
        adjacent.add(up);
        adjacent.add(down);
        return adjacent;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int t = scanner.nextInt();
        while (t-- > 0)
            makeNewValidMaze(n, m);
    }

    public static char[][] makeNewValidMaze(int n, int m) {
        boolean[][] visited = new boolean[n + 2][m + 2];
        for (boolean[] row : visited)
            Arrays.fill(row, false);
        char[][] maze = new char[2 * n + 1][2 * m + 1];
        for (int i = 0; i < 2 * n + 1; i++) {
            for (int j = 0; j < 2 * m + 1; j++) {
                maze[i][j] = '1';
            }
        }
        for (int i = 0; i <= m + 1; i++)
            visited[0][i] = visited[n + 1][i] = true;
        for (int i = 0; i <= n + 1; i++)
            visited[i][0] = visited[i][m + 1] = true;
        DFS(1, 1, visited, maze);
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                maze[2 * i + 1][2 * j + 1] = '*';
        return maze;
    }

}
