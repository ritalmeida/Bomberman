package edu.ufp.inf.sd.rmi.project.client;

import edu.ufp.inf.sd.rmi.project.server.*;
import edu.ufp.inf.sd.rmi.util.rmisetup.SetupContextRMI;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Client {
   private Socket socket = null;
   static PrintStream out = null;
   static Scanner in = null;

   public static int id;
   private GameFactoryRI gameFactory;
   private SetupContextRMI contextRMI;

   final static int rateStatusUpdate = 115;
   public static Coordinate[][] map = new Coordinate[Const.LIN][Const.COL];

   public static Coordinate[] spawn = new Coordinate[Const.QTY_PLAYERS];
   public static boolean[] alive = new boolean[Const.QTY_PLAYERS];

   Client(String host, int porta) {
      try {
         System.out.print("Estabelecendo conexão com o servidor...");
         this.socket = new Socket(host, porta);
         out = new PrintStream(socket.getOutputStream(), true);  //para enviar ao servidor
         in = new Scanner(socket.getInputStream()); //para receber do servidor
      } 
      catch (UnknownHostException e) {
         System.out.println(" erro: " + e + "\n");
         System.exit(1);
      } 
      catch (IOException e) {
         System.out.println(" erro: " + e + "\n");
         System.exit(1);
      }
      System.out.print(" ok\n");
      
      receiveInitialSettings();
      //new Receiver().start();
   }

   void receiveInitialSettings() {
      id = in.nextInt();

      //mapa
      for (int i = 0; i < Const.LIN; i++)
         for (int j = 0; j < Const.COL; j++)
            map[i][j] = new Coordinate(Const.SIZE_SPRITE_MAP * j, Const.SIZE_SPRITE_MAP * i, in.next());
      
      //situação (vivo ou morto) inicial de todos os jogadores
      for (int i = 0; i < Const.QTY_PLAYERS; i++)
         Client.alive[i] = in.nextBoolean();

      //coordenadas inicias de todos os jogadores
      for (int i = 0; i < Const.QTY_PLAYERS; i++)
         Client.spawn[i] = new Coordinate(in.nextInt(), in.nextInt());
   }
   
   public static void main(String[] args) {
      //new Client("127.0.0.1", 8383);
      //new Window();
      if (args != null && args.length < 2) {
         System.err.println("usage: java [options] edu.ufp.sd.inf.rmi._04_diglib.client.DigLibClient <rmi_registry_ip> <rmi_registry_port> <service_name>");
         System.exit(-1);
      } else {
         //1. ============ Setup client RMI context ============
         Client clt = new Client(args);
         //2. ============ Lookup service ============
         clt.lookupService();
         //3. ============ Play with service ============
         clt.playService();
      }
   }

   public Client(String args[]) {
      try {
         //List ans set args
         SetupContextRMI.printArgs(this.getClass().getName(), args);
         String registryIP = args[0];
         String registryPort = args[1];
         String serviceName = args[2];
         //Create a context for RMI setup
         contextRMI = new SetupContextRMI(this.getClass(), registryIP, registryPort, new String[]{serviceName});
      } catch (RemoteException e) {
         Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, e);
      }
   }

   private void lookupService() {

      //Get proxy MAIL_TO_ADDR rmiregistry
      Registry registry = contextRMI.getRegistry();
      //Lookup service on rmiregistry and wait for calls
      if (registry != null) {
         //Get service url (including servicename)
         //String serviceUrl = contextRMI.getServicesUrl(0);
         System.out.println("Registry is null");
      }
      String serviceUrl = contextRMI.getServicesUrl(0);
      try {
         gameFactory = (GameFactoryRI) registry.lookup(serviceUrl);
      } catch (RemoteException | NotBoundException ex) {
         ex.printStackTrace();
      }
   }

   private void playService() {

      //TODO ARRANJAR DO OBSERVER
   }
}

