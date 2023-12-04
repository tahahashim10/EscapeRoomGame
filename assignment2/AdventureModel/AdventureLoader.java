package AdventureModel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class AdventureLoader. Loads an adventure from files.
 */
public class AdventureLoader {

    private AdventureGame game; //the game to return
    private String adventureName; //the name of the adventure
    private int clueNumbers = 3;

    /**
     * Adventure Loader Constructor
     * __________________________
     * Initializes attributes
     * @param game the game that is loaded
     * @param directoryName the directory in which game files live
     */
    public AdventureLoader(AdventureGame game, String directoryName) {
        this.game = game;
        this.adventureName = directoryName;
    }

     /**
      * Load game from directory
      * @throws IOException if there's an issue loading the game
     */
    public void loadGame() throws IOException {
        parseRooms();
        parseObjects();
        parseSynonyms();
        this.game.setHelpText(parseOtherFile("help"));
        this.game.setHintText(parseOtherFile("hint"));
        this.game.setTotalClues(clueNumbers);
    }

     /**
      * Parse Rooms File
      * @throws IOException if there's an issue reading or parsing the rooms file
     */
    private void parseRooms() throws IOException {

        int roomNumber;

        String roomFileName = this.adventureName + "/rooms.txt";
        BufferedReader buff = new BufferedReader(new FileReader(roomFileName));

        while (buff.ready()) {

            String currRoom = buff.readLine(); // first line is the number of a room

            roomNumber = Integer.parseInt(currRoom); //current room number

            // now need to get room name
            String roomName = buff.readLine();

            // now we need to get the description
            String roomDescription = "";
            String line = buff.readLine();
            while (!line.equals("-----")) {
                roomDescription += line + "\n";
                line = buff.readLine();
            }
            roomDescription += "\n";
            String roomPass = buff.readLine();

            // now we make the room object
            Room room = new Room(roomName, roomNumber, roomDescription, adventureName, roomPass);

            // now we make the motion table
            line = buff.readLine(); // reads the line after "-----"
            while (line != null && !line.equals("")) {
                String[] part = line.split(" \s+"); // have to use regex \\s+ as we don't know how many spaces are between the direction and the room number
                String direction = part[0];
                String dest = part[1];
                if (dest.contains("/")) {
                    String[] blockedPath = dest.split("/");
                    String dest_part = blockedPath[0];
                    String object = blockedPath[1];
                    Passage entry = new Passage(direction, dest_part, object);
                    room.getMotionTable().addDirection(entry);
                } else {
                    Passage entry = new Passage(direction, dest);
                    room.getMotionTable().addDirection(entry);
                }
                line = buff.readLine();
            }
            this.game.getRooms().put(room.getRoomNumber(), room);
        }

    }

    /**
     * Parse Objects File
     *
     * @throws IOException if there's an issue reading or parsing the objects file
     */
    public void parseObjects() throws IOException {

        // Construct the file path for objects.txt based on the adventure name
        String objectFileName = this.adventureName + "/objects.txt";

        // Create a BufferedReader to read from the objects.txt file
        BufferedReader buff = new BufferedReader(new FileReader(objectFileName));

        // Read and process each set of object information from the file
        while (buff.ready()) {
            // Read object details: name, description, answer, location, and separator
            String objectName = buff.readLine();
            String objectDescription = buff.readLine();
            String objectAnswer = buff.readLine();
            String objectLocation = buff.readLine();
            String separator = buff.readLine();

            // Check for formatting errors in the file
            if (separator != null && !separator.isEmpty())
                System.out.println("Formatting Error!");

            // Convert the object's location to an integer and retrieve the corresponding room
            int i = Integer.parseInt(objectLocation);
            Room location = this.game.getRooms().get(i);

            // Create an AdventureObject with the parsed information and add it to the room
            AdventureObject object = new AdventureObject(objectName, objectDescription, location, objectAnswer);
            location.addGameObject(object);
        }
    }

    /**
     * Parse Synonyms File
     *
     * @throws IOException if there's an issue reading or parsing the synonyms file
     */
    public void parseSynonyms() throws IOException {
        // Construct the file path for synonyms.txt based on the adventure name
        String synonymsFileName = this.adventureName + "/synonyms.txt";

        // Create a BufferedReader to read from the synonyms.txt file
        BufferedReader buff = new BufferedReader(new FileReader(synonymsFileName));

        // Read and process each line in the file containing command synonyms
        String line = buff.readLine();
        while(line != null){
            // Split the line into command and synonym using "=" as a delimiter
            String[] commandAndSynonym = line.split("=");
            String command1 = commandAndSynonym[0];
            String command2 = commandAndSynonym[1];

            // Add the synonym pair to the game's synonym mapping
            this.game.getSynonyms().put(command1, command2);

            // Move to the next line in the file
            line = buff.readLine();
        }
    }

    /**
     * Parse Files other than Rooms, Objects, and Synonyms
     *
     * @param fileName the file to parse
     * @return the content of the parsed file as a string
     * @throws IOException if there's an issue reading or parsing the specified file
     */
    public String parseOtherFile(String fileName) throws IOException {
        // Initialize an empty string to store the content of the parsed file
        String text = "";

        // Construct the file path based on the adventure name and the provided file name
        fileName = this.adventureName + "/" + fileName + ".txt";
        // Create a BufferedReader to read from the specified file
        BufferedReader buff = new BufferedReader(new FileReader(fileName));

        // Read and concatenate each line in the file to the text string
        String line = buff.readLine();
        while (line != null) { // while not EOF
            text += line + "\n";
            line = buff.readLine();
        }
        // Return the concatenated text representing the content of the parsed file
        return text;
    }


}
