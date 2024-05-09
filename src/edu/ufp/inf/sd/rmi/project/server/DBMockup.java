package edu.ufp.inf.sd.rmi.project.server;

import java.lang.reflect.Array;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class simulates a DBMockup for managing users and books.
 *
 * @author rmoreira
 *
 */
public class DBMockup {

    private final ArrayList<Game> games; // = new ArrayList<>();
    private final ArrayList<User> users; // = new ArrayList<>();
    // private Map<String, GameSessionRI> sessions = new HashMap<>();

    /**
     * This constructor creates and inits the database with some books and users.
     */
    public DBMockup() {

        this.games = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    /**
     * Registers a new user.
     *
     * @param u username
     * @param p passwd
     */
    public void register(String u, String p) {

        if (!exists(u, p)) {
            users.add(new User(u, p));
            System.out.println(u + " registado com sucesso!");
        }
    }

    /**
     * Checks the credentials of an user.
     * 
     * @param u username
     * @param p passwd
     */
    public boolean exists(String u, String p) {

        // return this.users.stream().anyMatch((user -> user.getUname().compareTo(u) == 0 && user.getPword().compareTo(p) == 0));
        for (User usr : this.users) {
            if (usr.getUname().compareTo(u) == 0 && usr.getPword().compareTo(p) == 0) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Game> getGames() {

        return games;
    }

    public ArrayList<User> getUsers() {

        return users;
    }

    /**
     * Imprime jogos disponiveis
     * @return
     * @throws RemoteException
     */
    public ArrayList<Game> printAvGames() throws RemoteException {

        ArrayList<Game> avGames = new ArrayList<>();
        for (Game game : games) {

            if (game.getMax_players() != game.getNum_players()) {

                avGames.add(game);
            }
        }
        return avGames;
    }

    /**
     * Imprime jogos a decorrer
     * @return
     * @throws RemoteException
     */
    public ArrayList<Game> printOnGames() throws RemoteException {

        ArrayList<Game> onGames = new ArrayList<>();
        for (Game game : games) {

            if (game.getMax_players() == game.getNum_players()) {

                onGames.add(game);
            }
        }
        return onGames;
    }


    /**
     * Imprime todos os jogos
     * @return
     * @throws RemoteException
     */
    public ArrayList<Game> printGames() throws RemoteException {

        for (Game game : games) {

            System.out.println(game.getId());
        }
        return games;
    }

    /**
     * Seleciona um jogo
     * @param id
     * @return
     * @throws RemoteException
     */
    public Game select(int id) throws RemoteException {

        for (Game game : games) {

            if (game.getId() == id) {

                if (game.getMax_players() == game.getNum_players()) {

                    System.out.println("O jogo a decorrer já está cheio!");
                } else
                    return game;
            } else {
                System.out.println("Não existe um jogo com o id " + id);
            }
        }
        return null;
    }

    public Game insert(int max_players, SubjectRI subjectRI) throws RemoteException {

        Game game = new Game(max_players, subjectRI);
        if (games.size() < 1) {

            game.setId(1);
        } else {

            game.setId(games.size() + 1);
        }
        games.add(game);
        return game;
    }

}
