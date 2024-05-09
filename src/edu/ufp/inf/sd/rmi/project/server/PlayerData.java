package edu.ufp.inf.sd.rmi.project.server;

public class PlayerData {
    public boolean logged;
    public boolean alive;
    public int x;
    public int y; //coordenada atual
    public int numberOfBombs;

    PlayerData(int x, int y) {
        this.x = x;
        this.y = y;
        this.logged = false;
        this.alive = false;
        this.numberOfBombs = 1; // para 2 bombas, é preciso tratar cada bomba em uma thread diferente
    }
}
