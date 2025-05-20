import java.util.Scanner; // Import Scanner for reading user input

public class RoomAdventure { // Main class containing game logic

    // class variables
    private static Room currentRoom; // the room the player is currently in
    public static String[] inventory = {null, null, null}; //Player inventory slots
    public static String status; // Message to display after each action
    private static boolean dangerStatus = false;

    // constants
    final private static String DEFAULT_STATUS =
        "Sorry, I do not understand. Try [verb] [noun]. Valid verbs include: 'go', 'look', 'take', hide, drop, and 'eat'."; // default error message

    private static void handleGo(String noun) { // handles moving between rooms
        String[] exitDirections = currentRoom.getExitDirections(); // get available directions
        Room[] exitDestinations = currentRoom.getExitDestinations(); // get available destinations
        status = "I don't see that room."; // Default error message
        for (int i = 0; i < exitDirections.length; i++) { // loop through directions
            if (noun.equals(exitDirections[i])) { // If user direction matches
                currentRoom = exitDestinations[i]; // Change current room
                status = "Changed Room"; // Update Status    

                // Check if the new room is dangerous
                if (currentRoom.toString().contains("Room 3")) {
                    dangerStatus = true;
                    status = "DANGER! A man is lurking around the room. Find a hiding spot.";
                }
            }    
        }
    }

    private static void handleHide(String noun) {
        if (!currentRoom.toString().contains("Room 3")) {
            status = "There's no need to hide.";
            return;
        }

        switch (noun) {
            case "couch":
                status = "You hide behind the couch. The man doesn't see and leaves.";
                dangerStatus = false;
                break;
            case "table":
                status = "You crawled under the table and hit your head, causing the mug to fall and the man to catch you.";
                System.out.println(status);
                System.exit(0);
                break;
            case "TV":
                status = "You sneak toward the TV, but you tripped on a cable and the man caught you.";
                System.out.println(status);
                System.exit(0);
                break;
            default:
                status = "I can't hide there.";
        }        
    }
    
    public static void handleLook(String noun) { // handles inspecting items
        String[] items = currentRoom.getItems(); // visible items in current room
        String[] itemDescriptions = currentRoom.getItemDescriptions(); // Descriptions for each item
        status = "I don't see that item."; // Default message
        for (int i = 0; i < items.length; i++){ // loop through items
            if (noun.equals(items[i])) { // if user noun matches an item
                status = itemDescriptions[i]; // set status to item description
            }
        }   
    }

    public static void handleTake(String noun) { // Handles picking up items
        String[] grabbables = currentRoom.getGrabbables(); // Items that can be taken
        status = "I can't grab that."; // default message
        for (String item : grabbables) { // loop through grabbable items
            if (noun.equals(item)) { // if user noun matches a grabbable
                for (int j = 0; j < inventory.length; j++){ // find empty inventory slot
                    if (inventory[j] == null) { // if slot is empty
                        inventory[j] = noun; // add item to inventory
                        status = "Added it to the inventory.";
                        break; // exit the inventory look    
                    }
                }
            }
        }
    }

    public static void handleEat(String noun){ // Handles eating items
        status = "I can't eat that..."; // default error message
        for (String item : inventory){ // loop through inventory
            if (noun.equals(item)){
                if (item.equals("Bread") || item.equals("Coffee")){ // if item is a chosen food
                    status = "mmmmmmm tasty"; // message if item eaten is a food item
                    // removes eaten food from inventory
                    for (int k = 0; k < inventory.length; k++){
                        if (item.equals(inventory[k])){
                            inventory[k] = null; // set inventory space to null
                        } 
                    }                    
                }
            } 
        }
    }

    public static void handleDrop(String noun){ // handles dropping items
        status = "I can't do that"; // default error message
        for (String item : inventory) {
            if (noun.equals(item)){
                for (int k = 0; k < inventory.length; k++){
                    if (item.equals(inventory[k])){
                        inventory[k] = null; // set inventory space to null
                    } 
                } 
            }
        }
    }
    private static void setupGame() { // Initializes game world
        Room room1 = new Room("Room 1"); // create room 1
        Room room2 = new Room("Room 2"); // create room 2
        Room room3 = new Room("Room 3"); // create room 3
        Room room4 = new Room("Room 4"); // create room 4  
        Room room5 = new Room("Room 5"); // create room 5 aka Treasure room   

        // Room 1
        String[] room1ExitDirections = {"east", "south"}; 
        Room[] room1ExitDestinations = {room2, room3};
        String[] room1Items = {"chair", "desk"};
        String[] room1ItemDescriptions = {
            "It's worn and creaky",
            "It's covered in yellowed papers"
        };
        String[] room1Grabbables = {"key"};
        room1.setExitDirections(room1ExitDirections);
        room1.setExitDestinations(room1ExitDestinations);
        room1.setItems(room1Items);
        room1.setItemDescriptions(room1ItemDescriptions);
        room1.setGrabbables(room1Grabbables);


        // Room 2
        String[] room2ExitDirections = {"west", "south"}; 
        Room[] room2ExitDestinations = {room1, room4};
        String[] room2Items = {"stove", "refridgerator"};
        String[] room2ItemDescriptions = {
            "It's still hot.",
            "It kinda stinks."
        };
        String[] room2Grabbables = {"Kettle", "Bread"};
        room2.setExitDirections(room2ExitDirections);
        room2.setExitDestinations(room2ExitDestinations);
        room2.setItems(room2Items);
        room2.setItemDescriptions(room2ItemDescriptions);
        room2.setGrabbables(room2Grabbables);

        // Room 3
        String[] room3ExitDirections = {"east", "north"}; 
        Room[] room3ExitDestinations = {room4, room1};
        String[] room3Items = {"couch", "TV"};
        String[] room3ItemDescriptions = {
            "It's stained and stinky.",
            "The screen is covered in dust."
        };
        String[] room3Grabbables = {"Remote", "Coffee"};
        room3.setExitDirections(room3ExitDirections);
        room3.setExitDestinations(room3ExitDestinations);
        room3.setItems(room3Items);
        room3.setItemDescriptions(room3ItemDescriptions);
        room3.setGrabbables(room3Grabbables);

        // Room 4
        String[] room4ExitDirections = {"west", "north", "south"}; 
        Room[] room4ExitDestinations = {room3, room1, room5};
        String[] room4Items = {"Bed", "Bookshelf"};
        String[] room4ItemDescriptions = {
            "It is nicely made",
            "It is full of dusty encyclopedias."
        };
        String[] room4Grabbables = {"Book"};
        room4.setExitDirections(room4ExitDirections);
        room4.setExitDestinations(room4ExitDestinations);
        room4.setItems(room4Items);
        room4.setItemDescriptions(room4ItemDescriptions);
        room4.setGrabbables(room4Grabbables);

        //Room 5 
        String[] room5ExitDirections = {"north"};
        Room[] room5ExitDestinations = {room4};
        String[] room5Items = {"granade", "gem", "chest", "old radio", "ancient scroll", "key chain", "empty cage", "note"};
        String[] room5ItemDescriptions = { "this will explode!!!", "Wow! you found a gem. You can actually keep it with you and become rich haha!", "ohhhh chest!!! What could it be inside?", "Does this even play?", "looks really old. Can't even open it properly!", "does any of the room had locks? LOL", "just an empty cage", "you might want to check this note if you're willing to win the game"};

        String[] room5Grabbables = {"grenade", "gem", "old radio", "key chain", "ancient scroll"};

        room5.setExitDirections(room5ExitDirections);
        room5.setExitDestinations(room5ExitDestinations);
        room5.setItems(room5Items);
        room5.setItemDescriptions(room5ItemDescriptions);
        room5.setGrabbables(room5Grabbables);


        currentRoom = room1; // Start game in room 1
    }

    public static void main(String[] args) {
        setupGame(); // initialize rooms, items, and starting room

        while (true) { // Game loop, runs 
            System.out.print(currentRoom.toString()); // display current room descriptions
            System.out.print("Inventory: ");

            for (int i = 0; i < inventory.length; i++){
                System.out.print(inventory[i] + " ");
            }

            System.out.println("\nWhat would you like to do? "); // Prompt user

            Scanner s = new Scanner(System.in);
            String input = s.nextLine(); // Read entire line of input
            String[] words = input.split(" "); // Split input into words

            if (words.length != 2) { // Check for proper two-word command
                status = DEFAULT_STATUS; // set status to error message
                continue; // skip to next loop iteration    
            }

            String verb = words[0]; // make first word the verb
            String noun = words[1]; // make second word the noun

            if (dangerStatus && !verb.equals("hide")) {
                status = "I shouldn't do that right now. I need to 'hide'.";
                System.out.println(status);
                continue;
            }

            switch (verb) { // decide which action to take
                case "go":
                    handleGo(noun);
                    break;
                case "look":
                    handleLook(noun);
                    break;
                case "take":
                    handleTake(noun);
                    break;
                case "eat":
                    handleEat(noun);
                case "hide":
                    handleHide(noun);
                    break;
                case "drop":
                    handleDrop(noun);
                    break;
                default:
                    status = DEFAULT_STATUS;
            }

            System.out.println(status); // print the status message
        }
    }

}

// represents a game room
class Room {
    private String name; // Room name
    private String[] exitDirections; // Directions you can go
    private Room[] exitDestinations; // Rooms reached by each direction
    private String[] items; // Items visible in the room
    private String[] itemDescriptions; // Descriptions for those items
    private String[] grabbables; // Items you can grab

    public Room(String name) { // constructor
        this.name = name; // Set the room's name
    }

    public void setExitDirections(String[] exitDirections) { // setter for exits
        this.exitDirections = exitDirections;
    }

    public String[] getExitDirections() { // getter for exits
        return exitDirections;
    }

    public void setExitDestinations(Room[] exitDestinations) { // setter for destinations
        this.exitDestinations = exitDestinations;
    }

    public Room[] getExitDestinations() { // getter for destinations
        return exitDestinations;
    }

    public void setItems(String[] items){ // setter for items
        this.items = items;
    }

    public String[] getItems() { // getter for items
        return items;
    }

    public void setItemDescriptions(String[] itemDescriptions){ // set Item Descriptions
        this.itemDescriptions = itemDescriptions;
    }

    public String[] getItemDescriptions() { // getter for item descriptions
        return itemDescriptions;
    }

    public void setGrabbables(String[] grabbables) { // setter for grabbables
        this.grabbables = grabbables;
    }

    public String[] getGrabbables() { // getter for grabbables
        return grabbables;
    }

    @Override
    public String toString() { // Custon print for the room
        String result = "\nLocation: " + name; // Show room name
        result += "\nYou See: "; // list items
        for (String item : items){ // loop items
            result += item + " "; // append each item
        }
        result += "\nExits: "; // list exits
        for (String direction : exitDirections) { // loop exits
            result += direction + " "; // append each exit
        }
        return result + "\n"; // Return full description
    }

}
