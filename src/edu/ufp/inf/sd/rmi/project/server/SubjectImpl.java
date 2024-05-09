package edu.ufp.inf.sd.rmi._05_observer.server;

import edu.ufp.inf.sd.rmi._01_helloworld.server.HelloWorldRI;
import edu.ufp.inf.sd.rmi._05_observer.client.ObserverRI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SubjectImpl extends UnicastRemoteObject implements SubjectRI {

    private State state;

    private ArrayList<ObserverRI> observers = new ArrayList<>();

    public SubjectImpl() throws RemoteException {

        super();
    }

    public SubjectImpl(State subjectState) throws RemoteException {

        super();
        this.state = subjectState;
    }

    @Override
    public State getState() {

        return this.state;
    }

    @Override
    public synchronized void setState(State state) throws RemoteException {

        System.out.println(state);
        this.state = state;
        this.notifyAllObservers();
    }

    public void notifyAllObservers() {

        for (ObserverRI observer : observers) {

            try {
                observer.update();
            } catch (RemoteException ex) {

                System.out.println(ex.toString());
            }
        }
    }

    @Override
    public void attach(ObserverRI observerRI) {

        /*if (!this.observers.contains(observerRI))
            this.observers.add(observerRI);*/
        this.observers.add(observerRI);
        System.out.println("Observer attached");
        System.out.println("Observer list size: " + this.observers.size());
    }

    @Override
    public void detach(ObserverRI observerRI) {

        //this.observers.remove(observerRI);
        this.observers.remove(observerRI);
        System.out.println("Observer detatched");
        System.out.println("Observer list size: " + this.observers.size());
    }

}
