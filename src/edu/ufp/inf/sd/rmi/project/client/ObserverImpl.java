package edu.ufp.inf.sd.rmi.project.client;

import edu.ufp.inf.sd.rmi.project.server.Game;
import edu.ufp.inf.sd.rmi.project.server.State;
import edu.ufp.inf.sd.rmi.project.server.SubjectRI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ObserverImpl extends UnicastRemoteObject implements ObserverRI {

    private String id;
    private SubjectRI subjectRI;
    private State lastObserverState;
    private Game g;

    public ObserverImpl(String username) throws RemoteException {

        super();
        this.id = username;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public SubjectRI getSubjectRI() {
        return subjectRI;
    }

    @Override
    public void setSubjectRI(SubjectRI subjectRI) {
        this.subjectRI = subjectRI;
    }

    @Override
    public State getLastObserverState() {
        return lastObserverState;
    }

    @Override
    public void setLastObserverState(State lastObserverState) {
        this.lastObserverState = lastObserverState;
    }

    @Override
    public void update() throws RemoteException {

        try {
            lastObserverState = subjectRI.getState();
            if (lastObserverState.getInfo().compareTo("START") == 0) {

                System.out.println("STARTING GAME...");

            }
        }

    }
}
