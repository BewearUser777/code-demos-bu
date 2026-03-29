import java.util.*;

/**
 * Room Class
 * Each room represents a spot in the world
 * Room's have names, descriptions, inventories, and 
 * passages or lack there of to the N,S,E, and W
 */
public class Room {
    private boolean canGoNorth;
    private boolean canGoSouth;
    private boolean canGoEast;
    private boolean canGoWest;
    private boolean canEnter;
    private String description;
    private String dDescription;
    private int areaN;
    private String name;
    private ArrayList<Item> roomInventory;
    private ArrayList<Chara> roomCharas;
    private int fightValue = -1;
    private int fvub = 0;
    private int[] possibleCharas;
    private Room[] inRooms = null;
    private int money;

//MIGHT ADD MONEY INSTANCE VARIABLE

public Room(){}

//Inner room constructor
public Room(boolean canEnter, String name, String description, String dDescription, ArrayList<Item> roomInventory){
        this.canEnter = canEnter;
        this.name = name;
        this.description = description;
        this.dDescription = dDescription;
        this.roomInventory = roomInventory;
        this.roomCharas = new ArrayList<Chara>();
}

//Room constructor (no encounters)
public Room(boolean canGoNorth, boolean canGoSouth, boolean canGoEast, boolean canGoWest, String name, String description, 
String dDescription, ArrayList<Item> roomInventory, int money){
        this.canGoNorth = canGoNorth;
        this.canGoSouth = canGoSouth;
        this.canGoEast = canGoEast;
        this.canGoWest = canGoWest;
        this.name = name;
        this.description = description;
        this.dDescription = dDescription;
        this.roomInventory = roomInventory;
        this.money = money;
        this.roomCharas = new ArrayList<Chara>();
}

//Room constructor (with encounters)
public Room(boolean canGoNorth, boolean canGoSouth, boolean canGoEast, boolean canGoWest, int areaN, String name, 
String description, String dDescription, ArrayList<Item> roomInventory, int fightValue, int fvub, 
int[] possibleCharas, int money){
        this.canGoNorth = canGoNorth;
        this.canGoSouth = canGoSouth;
        this.canGoEast = canGoEast;
        this.canGoWest = canGoWest;
        this.areaN = areaN;
        this.name = name;
        this.description = description;
        this.dDescription = dDescription;
        this.roomInventory = roomInventory;
        this.fightValue = fightValue;
        this.fvub = fvub;
        this.possibleCharas = possibleCharas;
        this.money = money;
        this.roomCharas = new ArrayList<Chara>();
}

//Room constructor (with encounters & inner rooms); probably only PPD, Peony Apartments, & PCC 
public Room(boolean canGoNorth, boolean canGoSouth, boolean canGoEast, boolean canGoWest, int areaN, String name, 
String description, String dDescription, ArrayList<Item> roomInventory, int fightValue, 
int fvub, int[] possibleCharas, Room[] inRooms, int money){
        this.canGoNorth = canGoNorth;
        this.canGoSouth = canGoSouth;
        this.canGoEast = canGoEast;
        this.canGoWest = canGoWest;
        this.areaN = areaN;
        this.name = name;
        this.description = description;
        this.dDescription = dDescription;
        this.roomInventory = roomInventory;
        this.fightValue = fightValue;
        this.fvub = fvub;
        this.possibleCharas = possibleCharas;
        this.inRooms = inRooms;
        this.money = money;
        this.roomCharas = new ArrayList<Chara>();
}

    //Getter methods
    public String getDescription(){
        return this.description;
    }
    
    public String getDDescription(){
        return this.dDescription;
    }
    
    public String getName(){
        return this.name;
    }
    
    public int getFightValue(){
        return this.fightValue;
    }
    
    public int getFVUB(){
        return this.fvub;
    }
    
    public int[] getPossibleCharas(){
        return this.possibleCharas;
    }
    
    public boolean getCanGoNorth(){
        return this.canGoNorth;
    }

    public boolean getCanGoSouth(){
        return this.canGoSouth;
    }
    
    public boolean getCanGoEast(){
        return this.canGoEast;
    }
    
    public boolean getCanGoWest(){
        return this.canGoWest;
    }
    
    public boolean getCanEnter(){
        return this.canEnter;
    }

    public int getAreaN(){
        return this.areaN;
    }
    
    public ArrayList<Item> getRoomInventory(){
        return this.roomInventory;
    }
    
    public Room[] getInRooms(){
        return this.inRooms;
    }

    public ArrayList<Chara> getRoomCharas(){
        return this.roomCharas;
    }
    
    public int getMoney(){
        return this.money;
    }

    
    //Setter methods
    public void setName(String n){
        this.name = n;
    }
    
    public void setDescription(String d){
        this.description = d;
    }
    
    public void setFightValue(int f){
        this.fightValue = f;
    }
    
    public void setFVUB(int fv){
        this.fvub = fv;
    }
    
    public void setMoney(int m){
        this.money = m;
    }
    
    public void setCanGoNorth(boolean b){
        this.canGoNorth = b;
    }
    
    public void setCanGoSouth(boolean b){
        this.canGoSouth = b;
    }
    
    public void setCanGoEast(boolean b){
        this.canGoEast = b;
    }
    
    public void setCanGoWest(boolean b){
        this.canGoWest = b;
    }
    
    //Item methods
    public Item getObject(String item){
        for (int i = 0; i < roomInventory.size(); i++){
            if (this.roomInventory.get(i).getName().equalsIgnoreCase(item)){
                return this.roomInventory.get(i);
            }
        }
        return null;
    }

    public void addObject(Item thing){
        this.roomInventory.add(thing);
    }
   
    public Item removeObject(String thing){
        for (int i = 0; i < this.roomInventory.size(); i++){
            if (this.roomInventory.get(i).getName().equalsIgnoreCase(thing)){
                return this.roomInventory.remove(i);
            }
        }
        return null;
    }

    public String objectsInRoom(){
        if (this.roomInventory.size() == 0){
            return "There are no items in this room.";
        }
        String list = "Here is the list of items of the room: ";
        for (int i = 0; i < this.roomInventory.size(); i++){
            if (i == this.roomInventory.size()-1){
                list += this.roomInventory.get(i).getName();
            }
            else {
                list += this.roomInventory.get(i).getName() + ", ";
            }
        }
        return list;
    }

    public void unlock(String direction){
        if (direction.equals("north")){
            this.canGoNorth = true;
        }
        else if (direction.equals("south")){
            this.canGoSouth = true;
        }
        else if (direction.equals("east")){
            this.canGoEast = true;
        }
        else if (direction.equals("west")){
            this.canGoWest = true;
        }
        else {
            System.out.println("Direction unlock error message.");
        }
    }
/*   
/**
 * custom Room constructor with 9 parameters 
 * use to build an instance of a Room object 
 * the client populates all of the instance variables by
 * passing the following parameters in this order
 * 
 * @param canGoNorth
 * @param canGoSouth
 * @param canGoEast
 * @param canGoWest
 * @param name
 * @param description
 * @param item  -- this is type InventoryItem
 * 

public Room(boolean canGoNorth, boolean canGoSouth, boolean canGoEast, boolean canGoWest, String name, String description, InventoryItem item, Character[] roomCharas, int fightValue){
        this.canGoNorth = canGoNorth;
        this.canGoSouth = canGoSouth;
        this.canGoEast = canGoEast;
        this.canGoWest = canGoWest;
        this.name = name;
        this.description = description;
        this.roomInventory = new ArrayList<InventoryItem>();
        this.roomInventory.add(item);
        this.roomCharas = roomCharas;
        this.fightValue = fightValue;
}

/**
 * optional custom Room constructor with 7 parameters 
 * use to build an instance of a Room object 
 * the client populates all of the instance variables by
 * passing the following parameters in this order
 * 
 * @param canGoNorth
 * @param canGoSouth
 * @param canGoEast
 * @param canGoWest
 * @param name
 * @param description
 * @param item  -- this is an ArrayList of type Inventory


public Room(boolean canGoNorth, boolean canGoSouth, boolean canGoEast, boolean canGoWest, String name, String description, ArrayList<InventoryItem> items, Character[] roomCharas, int fightValue){
    this.canGoNorth = canGoNorth;
        this.canGoSouth = canGoSouth;
        this.canGoEast = canGoEast;
        this.canGoWest = canGoWest;
        this.name = name;
        this.description = description;
        this.roomInventory = items;
        this.roomCharas = roomCharas;
        this.fightValue = fightValue;
}
*/

}

