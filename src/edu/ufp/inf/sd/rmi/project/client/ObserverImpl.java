package edu.ufp.inf.sd.rmi.project.client;

import edu.ufp.inf.sd.rmi._01_helloworld.server.HelloWorldRI;
import edu.ufp.inf.sd.rmi._05_observer.server.State;
import edu.ufp.inf.sd.rmi._05_observer.server.SubjectRI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ObserverImpl extends UnicastRemoteObject implements ObserverRI {

    private String username;
    private SubjectRI subjectRI;
    private ObserverGuiClient observerGuiClient;

    public ObserverImpl() throws RemoteException {

        super();
    }

    public ObserverImpl(String username, ObserverGuiClient observerGuiClient, SubjectRI subjectRI) throws RemoteException {

        super();
        this.username = username;
        this.observerGuiClient = observerGuiClient;
        this.subjectRI = subjectRI;
        this.subjectRI.attach(this);
    }

    @Override
    public void update() throws RemoteException {

        //observerState = subjectRI.getState();
        try {
            if (this.subjectRI.getState().getId().compareTo(this.username) == 0) {      // current user message goes to the right
                this.observerGuiClient.doc.insertString(this.observerGuiClient.doc.getLength(), this.subjectRI.getState().toString() + "\n", this.observerGuiClient.rightAlign);
            }
            else {
                this.observerGuiClient.doc.insertString(this.observerGuiClient.doc.getLength(), this.subjectRI.getState().toString() + "\n", this.observerGuiClient.leftAlign);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SubjectRI getSubjectRI() {
        return this.subjectRI;
    }

    public  State getObserverState() throws RemoteException {

        return this.subjectRI.getState();
    }
}
