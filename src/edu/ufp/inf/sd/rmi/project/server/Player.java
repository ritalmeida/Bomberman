package edu.ufp.inf.sd.rmi.project.server;

import java.awt.Graphics;
import javax.swing.JPanel;

//tanto para you quanto para enemy
public class Player {
   int x, y;
   String status, color;
   JPanel panel;
   boolean alive;

   StatusChanger sc;

   Player(int id, JPanel panel) throws InterruptedException {
      this.x = Client.spawn[id].x;
      this.y = Client.spawn[id].y;
      this.color = Sprite.personColors[id];
      this.panel = panel;
      this.alive = Client.alive[id];

      (sc = new StatusChanger(this, "wait")).start();
   }

   public void draw(Graphics g) {
      if (alive)
         g.drawImage(Sprite.ht.get(color + "/" + status), x, y, Const.WIDTH_SPRITE_PLAYER, Const.HEIGHT_SPRITE_PLAYER, null);
   }
}

