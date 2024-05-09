package edu.ufp.inf.sd.rmi.project.server;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface GameFactoryRI extends Remote {

    boolean register(String username, String password) throws RemoteException;

    public GameSessionRI login(String username, String password) throws RemoteException;

    void logout(String username, GameSessionImpl gameSession) throws RemoteException;
}
