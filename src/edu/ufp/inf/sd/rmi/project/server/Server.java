package edu.ufp.inf.sd.rmi.project.server;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import edu.ufp.inf.sd.rmi.project.client.ClientManager;
import edu.ufp.inf.sd.rmi.util.rmisetup.SetupContextRMI;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.Properties;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
   public static PlayerData[] player = new PlayerData[Const.QTY_PLAYERS];
   public static Coordinate[][] map = new Coordinate[Const.LIN][Const.COL];

   /**
    * Context for running a RMI Servant on a SMTP_HOST_ADDR
    */
   private SetupContextRMI contextRMI;
   
   Server(int portNumber) {
      ServerSocket ss;

      setMap();
      setPlayerData();
      
      try {
         System.out.print("Abrindo a porta " + portNumber + "...");
         ss = new ServerSocket(portNumber); // socket escuta a porta
         System.out.print(" ok\n");

         for (int id = 0; !loggedIsFull(); id = (++id)%Const.QTY_PLAYERS)
            if (!player[id].logged) {
               Socket clientSocket = ss.accept();
               new ClientManager(clientSocket, id).start();
            }
         //nao encerra o servidor enquanto a thread dos clientes continuam executando
      } catch (IOException e) {
         System.out.println(" erro: " + e + "\n");
         System.exit(1);
      }
   }

   boolean loggedIsFull() {
      for (int i = 0; i < Const.QTY_PLAYERS; i++)
         if (player[i].logged == false)
            return false;
      return true;
   }
   
   void setMap() {
      for (int i = 0; i < Const.LIN; i++)
         for (int j = 0; j < Const.COL; j++)
            map[i][j] = new Coordinate(Const.SIZE_SPRITE_MAP * j, Const.SIZE_SPRITE_MAP * i, "block");

      // paredes fixas das bordas
      for (int j = 1; j < Const.COL - 1; j++) {
         map[0][j].img = "wall-center";
         map[Const.LIN - 1][j].img = "wall-center";
      }
      for (int i = 1; i < Const.LIN - 1; i++) {
         map[i][0].img = "wall-center";
         map[i][Const.COL - 1].img = "wall-center";
      }
      map[0][0].img = "wall-up-left";
      map[0][Const.COL - 1].img = "wall-up-right";
      map[Const.LIN - 1][0].img = "wall-down-left";
      map[Const.LIN - 1][Const.COL - 1].img = "wall-down-right";

      // paredes fixas centrais
      for (int i = 2; i < Const.LIN - 2; i++)
         for (int j = 2; j < Const.COL - 2; j++)
            if (i % 2 == 0 && j % 2 == 0)
               map[i][j].img = "wall-center";

      // arredores do spawn
      map[1][1].img = "floor-1";
      map[1][2].img = "floor-1";
      map[2][1].img = "floor-1";
      map[Const.LIN - 2][Const.COL - 2].img = "floor-1";
      map[Const.LIN - 3][Const.COL - 2].img = "floor-1";
      map[Const.LIN - 2][Const.COL - 3].img = "floor-1";
      map[Const.LIN - 2][1].img = "floor-1";
      map[Const.LIN - 3][1].img = "floor-1";
      map[Const.LIN - 2][2].img = "floor-1";
      map[1][Const.COL - 2].img = "floor-1";
      map[2][Const.COL - 2].img = "floor-1";
      map[1][Const.COL - 3].img = "floor-1";
   }
   
   void setPlayerData() {
      player[0] = new PlayerData(
         map[1][1].x - Const.VAR_X_SPRITES, 
         map[1][1].y - Const.VAR_Y_SPRITES
      );

      player[1] = new PlayerData(
         map[Const.LIN - 2][Const.COL - 2].x - Const.VAR_X_SPRITES,   
         map[Const.LIN - 2][Const.COL - 2].y - Const.VAR_Y_SPRITES
      );
      player[2] = new PlayerData(
         map[Const.LIN - 2][1].x - Const.VAR_X_SPRITES,   
         map[Const.LIN - 2][1].y - Const.VAR_Y_SPRITES
      );
      player[3] = new PlayerData(
         map[1][Const.COL - 2].x - Const.VAR_X_SPRITES,   
         map[1][Const.COL - 2].y - Const.VAR_Y_SPRITES
      );
   }

   public static void main(String[] args) {

      new Server(8383);
   }

   public static String giveToken(String username) {

      String token = new String("");

      try {

         Algorithm algorithm = Algorithm.HMAC256("secret");
         token = JWT.create().withIssuer(username).sign(algorithm);
         } catch (JWTCreationException exception) {
         exception.printStackTrace();
      }
      return token;
   }

   public static boolean verifiedToken(String token, String username) {

      DecodedJWT jwt = null;

      try {

         Algorithm algorithm = Algorithm.HMAC256("secret");
         JWTVerifier verifier = JWT.require(algorithm).withIssuer(username).build();
         jwt = verifier.verify(token);
         System.out.println(jwt.getToken());
      } catch (JWTVerificationException exception) {

      }
      assert jwt != null;
      if (jwt.getToken().equals(token)) {

         return true;
      }
      return false;
   }

}