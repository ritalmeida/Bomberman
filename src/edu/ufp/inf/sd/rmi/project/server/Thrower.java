package edu.ufp.inf.sd.rmi.project.server;

//thread auxiliar
public class Thrower extends Thread {
    String keyWord, index[];
    int l, c;
    int delay;

    Thrower(String keyWord, String index[], int delay, int l, int c) {
        this.keyWord = keyWord;
        this.index = index;
        this.delay = delay;
        this.l = l;
        this.c = c;
    }

    public void run() {
        for (String i : index) {
            MapUpdatesThrower.changeMap(keyWord + "-" + i, l, c);
            try {
                sleep(delay);
            } catch (InterruptedException e) {
            }
        }
        //situação pós-explosão
        MapUpdatesThrower.changeMap("floor-1", l, c);
    }
}
