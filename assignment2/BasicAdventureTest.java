
import java.io.IOException;
import java.util.ArrayList;

import AdventureModel.AdventureGame;
import AdventureModel.AdventureObject;
import AdventureModel.Player;
import AdventureModel.Room;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BasicAdventureTest {
    @Test
    void getCommandsTest() throws IOException {
        AdventureGame game = new AdventureGame("TinyEscapeRoomGame");
        String commands = game.player.getCurrentRoom().getCommands();
        //assertEquals("DOWN,NORTH,IN,WEST,UP,SOUTH", commands);
        String [] arr = commands.split(", ");
        assertTrue(stringContains(arr, "NONE"));
    }

    private boolean stringContains(String [] arr, String command) {
        for(int i = 0; i < arr.length; i ++){
            if(arr[i].equals(command)){
                return true;
            }
        }
        return false;
    }

    @Test
    void getObjectString() throws IOException {
        AdventureGame game = new AdventureGame("TinyEscapeRoomGame");
        String objects = game.player.getCurrentRoom().getObjectString();
        assertEquals("Which man was the most famous murderer in United States History?, True or false. The " +
                "fingerprint on the left matches the right., Decode code the word uzsnxmrjsy (Hint: it is a " +
                "crime related word)", objects);
    }

    @Test
    void getObjectString2() throws IOException {
        AdventureGame game = new AdventureGame("TinyEscapeRoomGame");
        game.player = new Player(game.rooms.get(game.player.getCurrentRoom().getRoomNumber() + 1));
        String objects = game.player.getCurrentRoom().getObjectString();
        assertEquals("What legal term is used to describe the temporary release of a prisoner before the " +
                "completion of their sentence, often for good behavior?, What is the most famous Prisoner in History?" +
                ", Decode this room with this punishment: ptwypzvutlua (using caesar cipher 7)", objects);
    }

    @Test
    void getObjectString3() throws IOException {
        AdventureGame game = new AdventureGame("TinyEscapeRoomGame");
        game.player = new Player(game.rooms.get(game.player.getCurrentRoom().getRoomNumber() + 1 + 1));
        String objects = game.player.getCurrentRoom().getObjectString();
        assertEquals("Besides Earth, which is the only world in our solar system known to have liquid lakes" +
                " and seas on the surface?, Who was the first human in space? We will provide the answer (Yuri " +
                "Gagarin) with unordered letters and the user will have to re-order the letters to give the right " +
                "answer with ordered letters., NASA's Perseverance Mars rover captured \"Bettys Rock\" on June 20, " +
                "2022. The rock is named after Bettys Rock in what national park?", objects);
    }

    @Test
    void getObjectString4() throws IOException {
        AdventureGame game = new AdventureGame("TinyEscapeRoomGame");
        game.player = new Player(game.rooms.get(game.player.getCurrentRoom().getRoomNumber() + 1 + 1 + 1));
        String objects = game.player.getCurrentRoom().getObjectString();
        assertEquals("If the zombies move at a speed of 2.5 feet per second, and the survivors need to cross" +
                " a room that is 13 feet wide, how many seconds do they have to safely cross before the zombies catch " +
                "up?, What is the name of the mushroom that causes the epidemy in the show and game The Last of Us?, " +
                "Decode the name of this popular zombie game (Resident Evil)", objects);
    }

    @Test
    void getInventoryTest() throws IOException {
        AdventureGame game = new AdventureGame("TinyEscapeRoomGame");
        ArrayList<String> inventory = game.player.getInventory();
        assertEquals(inventory.size(), 0);
    }

    @Test
    void objectsInRoomTest() throws IOException {
        AdventureGame game = new AdventureGame("TinyEscapeRoomGame");
        ArrayList<AdventureObject> objects = game.player.getCurrentRoom().objectsInRoom;
        assertEquals(objects.get(0).getName(), "CrimeObject1");
        assertEquals(objects.get(1).getName(), "CrimeObject2");
        assertEquals(objects.get(2).getName(), "CrimeObject3");
    }

    @Test
    void objectsInRoomTest2() throws IOException {
        AdventureGame game = new AdventureGame("TinyEscapeRoomGame");
        game.player = new Player(game.rooms.get(game.player.getCurrentRoom().getRoomNumber() + 1));
        ArrayList<AdventureObject> objects = game.player.getCurrentRoom().objectsInRoom;
        assertEquals(objects.get(0).getName(), "PrisonObject1");
        assertEquals(objects.get(1).getName(), "PrisonObject2");
        assertEquals(objects.get(2).getName(), "PrisonObject3");
    }

    @Test
    void objectsInRoomTest3() throws IOException {
        AdventureGame game = new AdventureGame("TinyEscapeRoomGame");
        game.player = new Player(game.rooms.get(game.player.getCurrentRoom().getRoomNumber() + 1 + 1));
        ArrayList<AdventureObject> objects = game.player.getCurrentRoom().objectsInRoom;
        assertEquals(objects.get(0).getName(), "FutureObject1");
        assertEquals(objects.get(1).getName(), "FutureObject2");
        assertEquals(objects.get(2).getName(), "FutureObject3");
    }

    @Test
    void objectsInRoomTest4() throws IOException {
        AdventureGame game = new AdventureGame("TinyEscapeRoomGame");
        game.player = new Player(game.rooms.get(game.player.getCurrentRoom().getRoomNumber() + 1 + 1 + 1));
        ArrayList<AdventureObject> objects = game.player.getCurrentRoom().objectsInRoom;
        assertEquals(objects.get(0).getName(), "ZombieObject1");
        assertEquals(objects.get(1).getName(), "ZombieObject2");
        assertEquals(objects.get(2).getName(), "ZombieObject3");
    }

    @Test
    void roomNumberTest() throws IOException {
        AdventureGame game = new AdventureGame("TinyEscapeRoomGame");
        int num = game.player.getCurrentRoom().getRoomNumber();
        assertEquals(num, 1);
    }

    @Test
    void roomNumberTest2() throws IOException {
        AdventureGame game = new AdventureGame("TinyEscapeRoomGame");
        game.player = new Player(game.rooms.get(game.player.getCurrentRoom().getRoomNumber() + 1));
        int num = game.player.getCurrentRoom().getRoomNumber();
        assertEquals(num, 2);
    }

    @Test
    void roomNumberTest3() throws IOException {
        AdventureGame game = new AdventureGame("TinyEscapeRoomGame");
        game.player = new Player(game.rooms.get(game.player.getCurrentRoom().getRoomNumber() + 1 + 1));
        int num = game.player.getCurrentRoom().getRoomNumber();
        assertEquals(num, 3);
    }

    @Test
    void roomNumberTest4() throws IOException {
        AdventureGame game = new AdventureGame("TinyEscapeRoomGame");
        game.player = new Player(game.rooms.get(game.player.getCurrentRoom().getRoomNumber() + 1 + 1 + 1));
        int num = game.player.getCurrentRoom().getRoomNumber();
        assertEquals(num, 4);
    }

    @Test
    void getRoomPasswordTest() throws IOException {
        AdventureGame game = new AdventureGame("TinyEscapeRoomGame");
        String password = game.player.getCurrentRoom().getRoomPassword();
        assertEquals(password, "gtp");
    }

    @Test
    void getRoomPasswordTest1() throws IOException {
        AdventureGame game = new AdventureGame("TinyEscapeRoomGame");
        game.player = new Player(game.rooms.get(game.player.getCurrentRoom().getRoomNumber() + 1));
        String password = game.player.getCurrentRoom().getRoomPassword();
        assertEquals(password, "pri");
    }

    @Test
    void getRoomPasswordTest2() throws IOException {
        AdventureGame game = new AdventureGame("TinyEscapeRoomGame");
        game.player = new Player(game.rooms.get(game.player.getCurrentRoom().getRoomNumber() + 1 + 1));
        String password = game.player.getCurrentRoom().getRoomPassword();
        assertEquals(password, "tis");
    }

    @Test
    void getRoomPasswordTest3() throws IOException {
        AdventureGame game = new AdventureGame("TinyEscapeRoomGame");
        game.player = new Player(game.rooms.get(game.player.getCurrentRoom().getRoomNumber() + 1 + 1 + 1));
        String password = game.player.getCurrentRoom().getRoomPassword();
        assertEquals(password, "5c0");
    }


}
