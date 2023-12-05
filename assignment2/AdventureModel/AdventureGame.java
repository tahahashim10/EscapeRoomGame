package AdventureModel;

import java.io.*;
import java.util.*;

/**
 * Class AdventureGame.  Handles all the necessary tasks to run the Adventure game.
 */
public class AdventureGame implements Serializable {
    private final String directoryName; //An attribute to store the Introductory text of the game.
    private String helpText; //A variable to store the Help text of the game. This text is displayed when the user types "HELP" command.
    public final HashMap<Integer, Room> rooms; //A list of all the rooms in the game.
    private HashMap<String,String> synonyms = new HashMap<>(); //A HashMap to store synonyms of commands.
    public final String[] actionVerbs = {"QUIT","INVENTORY", "VIEW", "PASSWORD"}; //List of action verbs (other than motions) that exist in all games. Motion vary depending on the room and game.
    public Player player; //The Player of the game.
    private String hintText; //A variable to store the hint text of the game. This text is displayed when the user types "HINT" command.
    private int totalClues; //A variable to store total number of clues

    public static AdventureGame game; // Singleton instance of the AdventureGame.

    AdventureGameController gameController; // The controller for managing game interactions.

    /**
     * Adventure Game Constructor
     * __________________________
     * Initializes attributes
     *
     * @param name the name of the adventure
     */
    public AdventureGame(String name){
        this.synonyms = new HashMap<>();
        this.rooms = new HashMap<>();
        this.directoryName = "Games/" + name; //all games files are in the Games directory!
        this.totalClues = 0;
        gameController = new AdventureGameController();
        try {
            setUpGame();
        } catch (IOException e) {
            throw new RuntimeException("An Error Occurred: " + e.getMessage());
        }


    }

    /**
     * Get Game Singleton Instance
     * __________________________
     *
     * Retrieves the singleton instance of the AdventureGame. If the instance does not exist,
     * a new AdventureGame instance with the specified adventure name is created.
     *
     * @return the singleton instance of AdventureGame.
     */
    public static AdventureGame getGame() {
        // Check if the game instance does not exist
        if (AdventureGame.game == null) {
            // Create a new AdventureGame instance with the specified adventure name
            game = new AdventureGame("TinyEscapeRoomGame");
        }

        // Return the singleton instance of AdventureGame
        return game;
    }

    /**
     * Save the current state of the game to a file
     * 
     * @param file pointer to file to write to
     */
    public void saveModel(File file) {
        try {
            FileOutputStream outfile = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(outfile);
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * setUpGame
     * __________________________
     *
     * @throws IOException in the case of a file I/O error
     */
    public void setUpGame() throws IOException {

        String directoryName = this.directoryName;
        AdventureLoader loader = new AdventureLoader(this, directoryName);
        loader.loadGame();

        // set up the player's current location
        this.player = new Player(this.rooms.get(1));
    }

    /**
     * tokenize
     * __________________________
     *
     * @param input string from the command line
     * @return a string array of tokens that represents the command.
     */
    public String[] tokenize(String input){

        //input = input.toUpperCase();
        String[] commandArray = input.split(" ");

        if (commandArray.length > 0) {
            commandArray[0] = commandArray[0].toUpperCase();
        }

        int i = 0;
        while (i < commandArray.length) {
            if(this.synonyms.containsKey(commandArray[i])){
                commandArray[i] = this.synonyms.get(commandArray[i]);
            }
            i++;
        }
        return commandArray;

    }

    /**
     * movePlayer
     * __________________________
     * Moves the player in the given direction, if possible.
     * Return false if the player wins or dies as a result of the move.
     *
     * @param direction the move command
     * @return false, if move results in death or a win (and game is over).  Else, true.
     */
    public boolean movePlayer(String direction) {

        direction = direction.toUpperCase();
        PassageTable motionTable = this.player.getCurrentRoom().getMotionTable(); //where can we move?
        if (!motionTable.optionExists(direction)) return true; //no move

        ArrayList<Passage> possibilities = new ArrayList<>();
        for (Passage entry : motionTable.getDirection()) {
            if (entry.getDirection().equals(direction)) { //this is the right direction
                possibilities.add(entry); // are there possibilities?
            }
        }

        //try the blocked passages first
        Passage chosen = null;
        for (Passage entry : possibilities) {
            System.out.println(entry.getIsBlocked());
            System.out.println(entry.getKeyName());

            if (chosen == null && entry.getIsBlocked()) {
                if (this.player.getInventory().contains(entry.getKeyName())) {
                    chosen = entry; //we can make it through, given our stuff
                    break;
                }
            } else { chosen = entry; } //the passage is unlocked
        }

        if (chosen == null) return true; //doh, we just can't move.

        int roomNumber = chosen.getDestinationRoom();
        Room room = this.rooms.get(roomNumber);
        this.player.setCurrentRoom(room);

        return !this.player.getCurrentRoom().getMotionTable().getDirection().get(0).getDirection().equals("FORCED");
    }


    /**
     * Restarts the game
     * __________________________
     *
     * This method restarts the game by placing the player in the first room
     * and resetting all rooms.
     *
     * */
    public void restart() {
        //restart the game by initiating a new player in room 1
        this.player = new Player(this.rooms.get(1));
        //clear the rooms
        for (Room room : this.rooms.values()) {
            room.reset();
        }

    }

    /**
     * interpretAction
     * interpret the user's action.
     *
     * @param command String representation of the command.
     */
    public String interpretAction(String command){

        return gameController.interpretActionController(command, this);
    }

    /**
     * getDirectoryName
     * __________________________
     * Getter method for directory 
     * @return directoryName
     */
    public String getDirectoryName() {
        return this.directoryName;
    }

    /**
     * getInstructions
     * __________________________
     * Getter method for instructions
     * @return helpText
     */
    public String getInstructions() {
        return helpText;
    }

    /**
     * getHint
     * __________________________
     * Getter method for hint
     * @return hintText
     */
    public String getHint() {
        return hintText;
    }

    /**
     * getPlayer
     * __________________________
     * Getter method for Player
     * @return player
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * getRooms
     * __________________________
     * Getter method for rooms
     * @return map of key value pairs (integer to room)
     */
    public HashMap<Integer, Room> getRooms() {
        return this.rooms;
    }

    /**
     * getSynonyms
     * __________________________
     * Getter method for synonyms 
     * @return map of key value pairs (synonym to command)
     */
    public HashMap<String, String> getSynonyms() {
        return this.synonyms;
    }

    /**
     * getTotalClues
     * __________________________
     * Getter method for totalClues
     * @return int value of totalClues
     */
    public int getTotalClues() {
        return this.totalClues;
    }

    /**
     * setHelpText
     * __________________________
     * Setter method for helpText
     * @param help which is text to set
     */
    public void setHelpText(String help) {
        this.helpText = help;
    }

    /**
     * setHintText
     * __________________________
     * Setter method for hintText
     * @param hint which is text to set
     */
    public void setHintText(String hint) {
        this.hintText = hint;
    }

    /**
     * setTotalClues
     * __________________________
     * Setter method for totalClues
     * @param clues which is text to set
     */
    public void setTotalClues(int clues) {
        this.totalClues = clues;
    }


}
