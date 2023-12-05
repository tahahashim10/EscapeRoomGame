package AdventureModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains the information about a
 * room in the Adventure Game.
 */
public class Room implements Serializable {

    /**
     * The adventure name.
     */
    private final String adventureName;
    /**
     * The number of the room.
     */
    private int roomNumber;

    /**
     * The name of the room.
     */
    private String roomName;

    /**
     * The description of the room.
     */
    private String roomDescription;

    /**
     * Object counter to keep track of the first object to add in order to reset
     * */
    private int objCounter;

    /**
     * The passage table for the room.
     */
    private PassageTable motionTable = new PassageTable();

    /**
     * The list of objects in the room.
     */
    public ArrayList<AdventureObject> objectsInRoom = new ArrayList<AdventureObject>();

    /**
     * The list of the initial objects in the room.
     */
    public ArrayList<AdventureObject> initObjects = new ArrayList<AdventureObject>();

    /**
     * A boolean to store if the room has been visited or not
     */
    private boolean isVisited;
    /**
     * The number of the room.
     */
    private String roomPassword;

    /**
     * AdvGameRoom constructor.
     *
     * @param roomName: The name of the room.
     * @param roomNumber: The number of the room.
     * @param roomDescription: The description of the room.
     * @param adventureName: The name of the adventure game.
     */
    public Room(String roomName, int roomNumber, String roomDescription, String adventureName, String roomPassword){
        this.roomName = roomName;
        this.roomNumber = roomNumber;
        this.roomDescription = roomDescription;
        this.adventureName = adventureName;
        this.isVisited = false;
        this.roomPassword = roomPassword;
    }


    /**
     * Returns a comma delimited list of every
     * object's description that is in the given room,
     * e.g. "a can of tuna, a beagle, a lamp".
     *
     * @return delimited string of object descriptions
     */
    public String getObjectString() {
        //test comment
        StringBuilder delimited = new StringBuilder();
        for (int i = 0; i < objectsInRoom.size(); i++){
            if(delimited.isEmpty()){
                delimited.append(objectsInRoom.get(i).getDescription());
            } else{
                delimited.append(", ").append(objectsInRoom.get(i).getDescription());
            }
        }
        return delimited.toString();
    }

    /**
     * Returns a comma delimited list of every
     * move that is possible from the given room,
     * e.g. "DOWN, UP, NORTH, SOUTH".
     *
     * @return delimited string of possible moves
     */
    public String getCommands() {
        StringBuilder delimited = new StringBuilder();
        List<Passage> passages = getMotionTable().getDirection();
        for (int i = 0; i < passages.size(); i++){
            if(delimited.isEmpty()){
                delimited.append(passages.get(i).getDirection());
            } else{
                delimited.append(", ").append(passages.get(i).getDirection());
            }
        }
        return delimited.toString();
    }

    /**
     * This method adds a game object to the room.
     *
     * @param object to be added to the room.
     */
    public void addGameObject(AdventureObject object){
        this.objectsInRoom.add(object);
        if (objCounter <3) {
            // adds the three clues in the rooms, checks that counter is less
            // than 3 to avoid doubles when resetting the room
            if (!initObjects.contains(object)) {
                this.initObjects.add(object);
            }
        }
        objCounter++;
    }

    /**
     * This method removes a game object from the room.
     *
     * @param object to be removed from the room.
     */
    public void removeGameObject(AdventureObject object){
        this.objectsInRoom.remove(object);
    }

    /**
     * This method checks if an object is in the room.
     *
     * @param objectName Name of the object to be checked.
     * @return true if the object is present in the room, false otherwise.
     */
    public boolean checkIfObjectInRoom(String objectName){
        for(int i = 0; i<objectsInRoom.size();i++){
            if(this.objectsInRoom.get(i).getName().equals(objectName)) return true;
        }
        return false;
    }

    /**
     * Getter for returning an AdventureObject with a given name
     *
     * @param objectName: Object name to find in the room
     * @return the AdventureObject corresponding to the given name
     */
    public AdventureObject getObject(String objectName){
        for(int i = 0; i<objectsInRoom.size();i++){
            if(this.objectsInRoom.get(i).getName().equals(objectName)) return this.objectsInRoom.get(i);
        }
        return null;
    }
    /**
     * Resets the room
     * __________________________
     *
     * This method clears the objects in the room and sets the visited attribute to false.
     *
     * */
    public void reset() {
        // set the room as not visited
        this.isVisited = false;
        // clear the objects in the room
        objectsInRoom.clear();
        // add the initial three clues to the objects
        objectsInRoom.addAll(initObjects);
    }

    /**
     * Delete Object
     * __________________________
     *
     * Removes an object from the list of objects in the room based on the object's name.
     *
     * @param objectName the name of the object to be deleted.
     */
    public void deleteObject(String objectName) {
        // Iterate through the list of objects in the room
        for (int i = 0; i < objectsInRoom.size(); i++) {
            // Check if the current object's name matches the specified objectName
            if (this.objectsInRoom.get(i).getName().equals(objectName)) {
                // Remove the matching object from the list
                this.objectsInRoom.remove(this.objectsInRoom.get(i));
            }
        }
    }

    /**
     * Getter method for the number attribute.
     *
     * @return number of the room
     */
    public int getRoomNumber(){
        return this.roomNumber;
    }

    /**
     * Getter method for the description attribute.
     *
     * @return description of the room
     */
    public String getRoomDescription(){
        return this.roomDescription.replace("\n", " ");
    }


    /**
     * Getter method for the name attribute.
     *
     * @return name of the room
     */
    public String getRoomName(){
        return this.roomName;
    }


    /**
     * Getter method for the visit attribute.
     *
     * @return visit status of the room
     */
    public boolean getVisited(){
        return this.isVisited;
    }


    /**
     * Getter method for the motionTable attribute.
     *
     * @return motion table of the room
     */
    public PassageTable getMotionTable(){
        return this.motionTable;
    }

    /**
     * Getter method for the room password.
     *
     * @return room password
     */
    public String getRoomPassword(){
        return this.roomPassword;
    }


}
