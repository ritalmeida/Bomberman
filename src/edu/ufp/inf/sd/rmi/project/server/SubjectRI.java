package edu.ufp.inf.sd.rmi.project.server;

import edu.ufp.inf.sd.rmi.project.client.ObserverRI;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface SubjectRI extends Remote {

    Game getGame();

    void setGame(Game game);

    State getState();

    void setState(State state) throws RemoteException;

    void attach(ObserverRI observerRI);

    void detach(ObserverRI observerRI);
}
