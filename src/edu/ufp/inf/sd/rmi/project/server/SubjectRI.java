package edu.ufp.inf.sd.rmi._05_observer.server;

import edu.ufp.inf.sd.rmi._05_observer.client.ObserverRI;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface SubjectRI extends Remote {
    public State getState() throws RemoteException;
    public void setState(State state) throws RemoteException;
    public void attach(ObserverRI observerRI) throws RemoteException;
    public void detach(ObserverRI observerRI) throws RemoteException;
}
