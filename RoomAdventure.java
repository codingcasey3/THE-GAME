import java.util.Scanner; // Import Scanner for reading user input

public class RoomAdventure { // Main class containing game logic

    // class variables
    private static Room currentRoom; // the room the player is currently in
    public static String[] inventory = {null, null, null, null, null}; //Player inventory slots
    public static String status; // Message to display after each action

    // constants
    final private static String DEFAULT_STATUS =
        "Sorry, I do not understand. Try [verb] [noun]. Valid verbs include: 'go', 'look', and 'take'."; // default error message

    private static void handleGo(String noun) { // handles moving between rooms
        String[] exitDirections = currentRoom.getExitDirections(); // get available directions
        Room[] exitDestinations = currentRoom.getExitDestinations(); // get available destinations
        status = "I don't see that room."; // Default error message
        for (int i = 0; i < exitDirections.length; i++) { // loop through directions
            if (noun.equals(exitDirections[i])) { // If user direction matches
                currentRoom = exitDestinations[i]; // Change current room
                status = "Changed Room"; // Update Status    
            }    
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

    private static void setupGame() { // Initializes game world
        Room room1 = new Room("Room 1"); // create room 1
        Room room2 = new Room("Room 2"); // create room 2
        Room room3 = new Room("Room 3"); // create room 3
        Room room4 = new Room("Room 4"); // create room 4    

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
        String[] room4ExitDirections = {"west", "north"}; 
        Room[] room4ExitDestinations = {room3, room1};
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