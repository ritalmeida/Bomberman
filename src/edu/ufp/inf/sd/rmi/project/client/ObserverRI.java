package edu.ufp.inf.sd.rmi.project.client;

import edu.ufp.inf.sd.rmi.project.server.State;
import edu.ufp.inf.sd.rmi.project.server.SubjectRI;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ObserverRI extends Remote {

    String getId();

    void setId(String id);

    SubjectRI getSubjectRI();

    void setSubjectRI(SubjectRI subjectRI);

    State getLastObserverState();

    void setLastObserverState(State lastObserverState);

    void update() throws RemoteException;
}
