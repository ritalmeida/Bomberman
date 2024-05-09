package edu.ufp.inf.sd.rmi.project.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;


public interface GameSessionRI extends Remote {

    String getUsername() throws RemoteException;

    void setUsername(String username);

    String getToken();

    void setToken(String token);

    ArrayList<Game> listGames() throws RemoteException;

    ArrayList<Game> listAvGames() throws RemoteException;

    ArrayList<Game> listOnGames() throws RemoteException;
}
