package edu.ufp.inf.sd.rmi.project.server;

public class Coordinate {
    public int x, y;
    public String img;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate(int x, int y, String img) {
        this.x = x;
        this.y = y;
        this.img = img;
    }
}
