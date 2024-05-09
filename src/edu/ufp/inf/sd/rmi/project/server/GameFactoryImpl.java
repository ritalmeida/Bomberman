package edu.ufp.inf.sd.rmi.project.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class GameFactoryImpl extends UnicastRemoteObject implements GameFactoryRI {

    private DBMockup database;
    private HashMap<String, GameSessionRI> sessions;

    public GameFactoryImpl() throws RemoteException {

        super();
        this.database = new DBMockup();
        this.sessions = new HashMap<>();
    }

    public DBMockup getDatabase() {

        return database;
    }

    @Override
    public boolean register(String username, String password) throws RemoteException {

        if (database.exists(username, password))
            return false;

        database.register(username, password);
        return true;
    }

    @Override
    public GameSessionRI login(String username, String password) throws RemoteException {

        if (database.exists(username, password)) {

            if (sessions.containsKey(username)) {

                System.out.println("Jogador efetuou o Login com sucesso!");
                return sessions.get(username);
            } else {

                GameSessionRI gameSessionRI = new GameSessionImpl(this, username);
                sessions.put(username, gameSessionRI);
                System.out.println("Sessao criada com sucesso!");
                return gameSessionRI;
            }
        }
        return null;
    }

    @Override
    public void logout(String username, GameSessionImpl gameSession) throws RemoteException {

        sessions.remove(username, gameSession);
    }

    /*private GameSessionRI getSession(User user) throws RemoteException {

        GameSessionRI sessionRI = this.database.session(user.getUname()).orElse(new DigLibSessionImpl(database, user,
                LocalDateTime.now().plusSeconds(SessionTimeInSeconds)));

        Thread thread = new Thread((Runnable)sessionRI);
        thread.start();

        return sessionRI;
    }*/

}
