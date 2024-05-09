package edu.ufp.inf.sd.rmi.project.server;

import edu.ufp.inf.sd.rmi.project.client.ObserverRI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class GameSessionImpl extends UnicastRemoteObject implements GameSessionRI {

    String username;
    String token;
    GameFactoryImpl gameFactory;

    public GameSessionImpl(GameFactoryImpl gameFactoryImpl, String username) throws RemoteException {

        super();
        this.gameFactory = gameFactoryImpl;
        this.username = username;
    }

    @Override
    public String getUsername() {

        return username;
    }

    @Override
    public void setUsername(String username) {

        this.username = username;
    }

    @Override
    public String getToken() {

        return token;
    }

    @Override
    public void setToken(String token) {

        this.token = token;
    }

    /**
     * Requisito 2.a)
     * @return
     * @throws RemoteException
     */
    @Override
    public ArrayList<Game> listGames() throws RemoteException {

        return this.gameFactory.getDatabase().printGames();
    }

    /**
     * Requisito 2.c)
     * @return
     * @throws RemoteException
     */
    @Override
    public ArrayList<Game> listAvGames() throws RemoteException {

        return this.gameFactory.getDatabase().printAvGames();
    }

    /**
     * Requisito 2.b)
     * @return
     * @throws RemoteException
     */
    @Override
    public ArrayList<Game> listOnGames() throws RemoteException {

        return this.gameFactory.getDatabase().printOnGames();
    }

    public Game newGame(int max_players, String token, ObserverRI observerRI) throws RemoteException {

        if (Server.verifiedToken(token, this.username)) {

            SubjectRI subjectRI = new SubjectImpl();
            Game game = gameFactory.getDatabase().insert(max_players, subjectRI);

            subjectRI.setGame(game);
            game.getSubjectRI().attach(observerRI);

            System.out.println("Jogo criado com sucesso!");
            return game;
        } else {

            System.out.println("Token errado!");
            return null;
        }
    }





}
