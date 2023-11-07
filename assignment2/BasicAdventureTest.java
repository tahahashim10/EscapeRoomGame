
import java.io.IOException;

import AdventureModel.AdventureGame;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BasicAdventureTest {
    @Test
    void getCommandsTest() throws IOException {
        AdventureGame game = new AdventureGame("TinyGame");
        String commands = game.player.getCurrentRoom().getCommands();
        //assertEquals("DOWN,NORTH,IN,WEST,UP,SOUTH", commands);
        String [] arr = commands.split(", ");
        assertTrue(stringContains(arr, "DOWN"));
        assertTrue(stringContains(arr, "NORTH"));
        assertTrue(stringContains(arr, "IN"));
        assertTrue(stringContains(arr, "WEST"));
        assertTrue(stringContains(arr, "UP"));
        assertTrue(stringContains(arr, "SOUTH"));
    }

    @Test
    void getObjectString() throws IOException {
        AdventureGame game = new AdventureGame("TinyGame");
        String objects = game.player.getCurrentRoom().getObjectString();
        assertEquals("a water bird", objects);
    }

    private boolean stringContains(String [] arr, String command) {
        for(int i = 0; i < arr.length; i ++){
            if(arr[i].equals(command)){
                return true;
            }
        }
        return false;
    }



}
