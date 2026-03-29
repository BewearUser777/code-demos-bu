import java.util.*;


/**
 * Creates the class Commands, 
 * There will only be one instance used by the game
 * therefore all of its methods are static to prevent multiple
 */

// Project last modified 01/23/2026

public class Commands {

public static final Scanner s = new Scanner(System.in);
public static boolean done = false;

public static Room[][] map = new Room[5][5];
public static int row = 4;
public static int column = 0;
public static Room currentRoom = map[row][column];
public static Room currentInRoom = null;
public static ArrayList<Item> playerInventory = new ArrayList<Item>();

//Player stats (+ route)
public static String playerName = "";
public static int pLvl = 1;
public static int pHp = 100;
public static int maxpHp = 100;
public static int pPow = 5;
public static int pDef = 5;
//area1,area2,area3,area4,area5,bosses/minibosses
public static int[] kills = {0,0,0,0,0,0};
public static int pXp = 0;
public static int pMoney = 0;
public static Weapon currentWeapon;
public static Armor currentArmor;
public static boolean isPacifist = false;
public static boolean isDestruction = false;
public static boolean isFlawedD = false;

//Encounter and area logic
//20 + 2 [mini]bosses for Destruction Route
//10 + 2 [mini]bosses for Destruction Route
//50 (Kaitlyn will never count as a kill)
//20 + 2 bosses for Destruction Route
//20 + 1 miniboss for Destruction Route
public static int[] areaFights = {20,10,50,20,20};
//area1,area2,area3,area4,area5
public static int[] encountered = {0,0,0,0,0};
public static boolean[] bossFought = {false,false,false,false,false};

//Game progress
public static int progress = 0;

//public static String[][] showMapArray = new String[0][0];

private Commands(){}

/**
 * runGame method
 * method responsible for running a console version of the game
 * this method returns nothing and has no parameters
 * it contains the while loop that is used to run the game
 */
    
    public static void runGame(){

        String userInput = "";
        String command = "";
        String item = "";
        
        //Room Inventories (Generic Item: name,description,weight)
        ArrayList<Item> i01 = new ArrayList<Item>();
        ArrayList<Item> i02 = new ArrayList<Item>();
        ArrayList<Item> i03 = new ArrayList<Item>();
        
        ArrayList<Item> i04 = new ArrayList<Item>();
        i04.add(new Text("Hayward's Journal","Nothing of value, but still readable.",2,
        "\"Alyssa looks so pretty, cute, and beautiful wherever she goes. She's such a sweetheart and I always kiss or hug her"
        + "\nwhenever she passes by me...\""
        + "\n> You almost died of cringe from the section you just read"));
        i04.add(new Item("Wedding Ring","A grim reminder of what started the town’s war...",0.1));
        
        ArrayList<Item> i10 = new ArrayList<Item>();
        
        ArrayList<Item> i11 = new ArrayList<Item>();
        i11.add(new Item("Bouquet of Peonies","They smell fresh and wonderful!",2));
        i11.add(new Item("Peony Hair Tie","Looks beautiful and thematic!",0.005));
        i11.add(new Item("Bob the Pebble","you can have Bob the Pebble if you want he likes exploring the world :D",0.1));
        
        ArrayList<Item> i12 = new ArrayList<Item>();
        
        ArrayList<Item> i20 = new ArrayList<Item>();
        i20.add(new Tool("Damaged Ladder","Tall enough to climb a small barrier, not sturdy enough to climb a large barrier.",
        20.0,"Washington Intersection","east","Ladder Support","You placed the ladder and the ladder support. You can now climb to Washington Drive.",
        "You also need the Ladder Support to use this item!"));
        
        //IF TIME MIGHT ADD A PURPOSE TO KAITLYN'S TOYS/STUFFED ANIMALS
        ArrayList<Item> i21 = new ArrayList<Item>();
        i21.add(new Item("Brown Teddy Bear","you can have Mr. Teddy B. Snuggles if you want just don’t hurt him!",1));
        
        ArrayList<Item> i22 = new ArrayList<Item>();
        
        //Add military uniform Destruction Route
        ArrayList<Item> i24 = new ArrayList<Item>();
        i24.add(new Tool("Spare Key","Unlocks the gate leading to Kaitlyn Beach.\n> Too bad someone left behind their key to the purest beach in central California!",
        0.1,"Main Street K","east","","You used the spare key and unlocked the gate to Kaitlyn Beach to the east.",""));
        
        ArrayList<Item> i30 = new ArrayList<Item>();
        i30.add(new Weapon("Long Stick","Increases POW by 7.\n> It’s always a good defense to have.",2.5,7));
        i30.add(new Armor("\"JUSTICE FOR ALYSSA!\" Poster", "Increases DEF by 4.\n> Some people might like this.\n> Others... not so much.",
        6,4));
        i30.add(new Heal("Hot Dog","Heals 10 HP.\n> A delicious untouched and uncontaminated snack.",1.3,10));
        
        ArrayList<Item> i31 = new ArrayList<Item>();
        i31.add(new Armor("Raincoat","Increases DEF by 5.\n> More durable than most people think.",2.5,5));
        i31.add(new Text("PWO Handbook","Provides valuable info about the goals of the Prudence Women’s Organization.",3,
        "\"Mission Statement:"
        + "\n> To empower women by providing equal rights and opportunities through"
        + "\neducational outreach programs and active communication with town officials."
        + "\n> Vision Statement:"
        + "\n> To advance a future where all women can achieve their dreams, empowering them to inspire change and shape the world.\""
        + "\nYou decided to stop reading as there are too many pages to read at once."));
        i31.add(new Heal("Orange Candy","Heals 5 HP.\n> Has a weird aftertaste.",0.02,5));
        
        ArrayList<Item> i32 = new ArrayList<Item>();
        
        ArrayList<Item> i33 = new ArrayList<Item>();
        i33.add(new Heal("Brown Coffee","Heals 15 HP.\n> Better than dark coffee.",1.5,15));
        i33.add(new Text("Notepad Notes","Looks like the Sheriff’s \"TO DO\" list.",0.3,"TO DO:"
        + "\n- Fix water leak"
        + "\n- Buy more coffee"
        + "\n- Scout Main Street"
        + "\n- More stuff"));
        
        ArrayList<Item> i34 = new ArrayList<Item>();
        i34.add(new Armor("Bulletproof Vest","Increases DEF by 10.\n> It’s strange how no one picked this up in times like these.",7,10));
        
        ArrayList<Item> i40 = new ArrayList<Item>();
        i40.add(new Weapon("Crowbar","Increases POW by 5.\n> It’s so rusted and bent that it looks like someone used it in a fight before.",
        5,5));
        i40.add(new Armor("Garbage Can Lid","Increases DEF by 3.\n> The easiest shield anyone can ever get.",
        5,3));
        
        ArrayList<Item> i41 = new ArrayList<Item>();
        i41.add(new Armor("\"JUSTICE FOR HAYWARD!\" Poster", "Increases DEF by 4.\n> Some people might like this.\n> Others... not so much.",
        6,4));
        i41.add(new Heal("Unopened Burrito","Heals 20 HP.\n> Does NOT come with salsa.",1.5,20));
        i41.add(new Tool("Ladder Support","Can support a ladder that’s too damaged to support itself.",0.01,"Washington Intersection","east",
        "Damaged Ladder","You placed the ladder and the ladder support. You can now climb to Washington Drive.",
        "You also need the Damaged Ladder to use this item!"));
        
        ArrayList<Item> i42 = new ArrayList<Item>();
        
        ArrayList<Item> i43 = new ArrayList<Item>();
        i43.add(new Tool("Dynamite","Can break any barrier, but needs to be ignited first.",1.1,"Washington Intersection","north",
        "Lighter","You ignite the dynamite with the lighter. You throw it to the barrier blocking the rest of Main Street." 
        + "\nThe barrier is now destroyed and you can now access the entirety of Main Street.",
        "You also need the Lighter to ignite this item!"));
        
        ArrayList<Item> i44 = new ArrayList<Item>();
        i44.add(new Tool("Discarded ID Card","Too old and damaged to read the card holder’s information, but the magnetic stripe is still intact to swipe.",
        0.01,"Washington Drive N","west","","You wore the ID card. You are now authorized to enter the station.",
        ""));
        
        //Inner rooms & their room inventories
        
        ArrayList<Item> ir01alys = new ArrayList<Item>();
        ir01alys.add(new Item("Diamond Wedding Ring","A reminder of what had once been...",0.1));
        ir01alys.add(new Text("Alyssa's Journal","Contains VERY personal information about Alyssa and Hayward's lives",2,
        "\"December 14 2018" + "\n Hayward and I got into another fight. I got mad at him for not telling me that he'd been home"
        + "\n the entire time and didn't call me even though he explained that he didn't know I was awake and expected me to text him first."
        + "\n I got mad at him and ended the call on him and he began angrily explaining why he didn't call me. I didn't wanna hear it."
        + "\n I called him again, expecting him to apologize or say \"I love you,\" but he just angrily ignored me. I've never seen him so mad at me before."
        + "\n After I fake apologized to him while laughing too, he got even more mad, and I ended the call later."
        + "\n I then texted him that we should break up, hoping that he'd finally say something and fight for me, but no."
        + "\n IN HIS OWN WORDS, he said \"do it, I don't care anymore.\" I didn't know he was even capable of doing that in the first place."
        + "\n So I told him I was kidding...\""
        + "\n> You couldn't read the rest of the passage without feeling invasive."));
        ir01alys.add(new Item("Barney Plush","you can have Barney I have 10 of him and I only play with 5 of them :)))))",1));
        ir01alys.add(new Heal("Sour Candy","Heals 7 HP. The aftertaste is the highlight of this candy.",0.02,7));
        
        ArrayList<Item> ir01mayors = new ArrayList<Item>();
        
        Room alys = new Room(true,"Room 125","Inside Alyssa’s room, you see a fancy kitchen, a comfortable living room with a flatscreen TV, one dining room, one bathroom, and one bedroom."
        + "\n> There are toys scattered around the entire room, but they’re mostly plush toys and plastic."
        + "\n> You can also make out a bunch of cat scratches and cat food on the carpets.","She started it. All of it.",ir01alys);
        
        Room mayors = new Room(false,"Room 94","Inside the Mayor’s room, you see well-made furniture, a decent living room with a small TV, one small dining room, one bathroom, and one bedroom."
        + "\n> The room looks completely spotless. On a desk near the entrance, there are photos of the Mayor with some of the previous mayors of Prudence as well as a large selfie of the Mayor with all of the town's people.",
        "",ir01mayors);
        
        Room[] ir01 = {alys,mayors};
        
        /*
         * ROOMS
         */
        
        //TEST A LOT SINCE MIGHT BE BUGGY
        map[0][1] = new Room(false,true,true,false,3,"Peony Apartments",
        "Inside the apartment complex lobby, you see large pots of peonies at the corners, luxurious furniture in the living area,"
        + "\n> a fine carpet, a fast elevator, beautiful wooden walls, smooth lighting, and a fancy front desk."
        + "\n> The lobby looks so fancy that even the stairs look fancy from a distance.","Nothing of use here.",
        i01,0,-1,null,ir01,50);
        
        //TESTING TOO + Serious Note
        map[0][2] = new Room(false,true,false,true,3,"Main Street K","This road looks spectacularly clean and has peonies growing on the west sidewalk and two tropical trees standing on the east sidewalk."
        + "\n> To the west is the garage entrance to the Peony Apartments."
        + "\n> To the east is a gate leading to Kaitlyn Beach."
        + "\n> One sign says “Peony Apartments Entrance” pointing west, another sign says “Main Street” pointing south, and a third sign says “Kaitlyn Beach” pointing east.",
        "A pointless road in a hopeless town.",i02,0,60,new int[]{0,1,2,3},0);
        
        map[0][3] = new Room(false,false,true,true,"Kaitlyn Beach","hi this is my beach i have my name for the beach yay!!!!!!! :D anyways the sand is really cool and nice and white and fluffy"
        + "\nand squishy and the trees and sun balance the heat shade thingy and the waters are so smooth and the skies are so clear"
        + "\nand i have Bob’s pebble friend thingies here and sandcastles and seashells and squishy fishies and, and, and, so FUN!!!!!!"
        + "\ni love this beach hehehehehe :) :D :U <- that guy is talking sideways btw by the way :))))))","Waste of robot parts.",
        i03,0);
        
        //Set west true in Destruction Route
        map[0][4] = new Room(false,false,false,false,"Hayward's Hut","","He started it. All of it.",i04,0);
        
        //Encounters come after solving puzzle and entering college
        //MIGHT BREAK BARRIERS IN PACIFIST
        map[1][0] = new Room(false,false,true,false,4,"PCC Drive","The PCC Drive has a powerful security system that prevents anyone with weapons or no PCC ID from entering the college."
        + "\n> There’s a gate that’s unclimbable and unbreakable that has a computer terminal that scans oncomers and checks for their ID."
        + "\n> In case the oncomer forgets their ID, the computer also offers two-step verification.",
        "Some security this is...",i10,0,-1,new int[]{1,5},0);
        
        //Has $10 in Destruction Route & also Alyssa's Room Key
        map[1][1] = new Room(true,true,true,true,"Peony Park",
        "The park is the largest structure in the entire town, being larger than the PWO’s headquarters and the PPD building combined."
        + "\n> As its name implies, the park is filled with peonies at every inch and every corner excluding the walkway."
        + "\n> There are apple trees, smooth wooden benches, picnic tables, pebbles, ponds of water, and children’s toys scattered throughout the park."
        + "\n> People occasionally come and go to the park, the most people that can be seen at any area of the town."
        + "\n> At the east entrance of the park you see a guy selling ice cream.","The flowers are stupid.",i11,0);
        
        map[1][2] = new Room(true,true,false,true,3,"Main Street N","This road looks spectacularly clean and has peonies growing at each sidewalk."
        + "\n> The cement is really smooth and the view of Town Center looks absolutely beautiful."
        + "\n> To the west is the entrance of Peony Park. Also, there’s a street vendor here spelling their special fish balls!",
        "A pointless road in a hopeless town.",i12,0,60,new int[]{0,1,2,3},0);
        
        //MIGHT BREAK BARRIERS IN PACIFIST
        map[2][0] = new Room(false,true,false,false,"Stanton Intersection",
        "This intersection looks like it went through war a thousand times."
        + "\n> Trash and weapons are everywhere, most of them not being usable at all."
        + "\n> There are a lot of broken pro-Alyssa and pro-Hayward posters and items in this place."
        + "\n> The cement is so brittle cars can’t even drive here anymore."
        + "\n> You can barely make out a street sign that says \"PCC Drive\" pointing north and two other signs that say “Stanton Road” pointing east and south." 
        + "\n> However, all those roads are currently blocked off.",
        "A pointless intersection in a hopeless town.",i20,0);
        
        //MIGHT BREAK BARRIERS IN PACIFIST
        map[2][1] = new Room(true,false,true,false,3,"Stanton Road E",
        "This road looks spectacularly clean and has peonies growing at each sidewalk."
        + "\n> The cement is really smooth and the wall of the PWO’s headquarters has graffiti of an orange cat with a peony on its head."
        + "\n> To the north is the entrance of Peony Park. The west is blocked off.","A pointless road in a hopeless town.",
        i21,0,60,new int[]{0,1,2,3},0);
        
        //CHANGE FOR TESTING ALSO CHANGE DESC IF canGoEast = true
        //CURRENTLY CHANGING EAST VALUE
        map[2][2] = new Room(true,false,true,true,"Main Intersection",
        "Surprisingly, the intersection is really clean and the signs are clearly readable."
        + "\n> Two signs say “Main Street” pointing north and south, another sign says “Stanton Road” pointing west, and a third sign says “Town Center” pointing east."
        + "\n> A large barrier is blocking Town Center; no dynamite is strong enough to break that.",
        "A pointless intersection in a hopeless town.",i22,0);
        
        //Might set fightCounter to 0 to make sure Amara is the only one encountered
        //TEMPORARILY CHANGED 
        map[3][0] = new Room(true,true,true,false,1,"Stanton Road S",
        "This road looks like it went through war a hundred times."
        + "\n> Trash and weapons are everywhere, most of them not being usable at all."
        + "\n> There are a lot of pro-Alyssa posters and items in this place."
        + "\n> The cement is so brittle cars can’t even drive here anymore."
        + "\n> To the east is the headquarters of the Prudence Women’s Organization."
        + "\n> You can barely make out a street sign that says \"Stanton Road\" pointing north and another sign that says \"Main Street\" pointing south.",
        "A pointless road in a hopeless town.",i30,0,0,new int[]{0,1,2,3},30);
        
        map[3][1] = new Room(false,true,false,true,"Prudence Women’s Organization Headquarters",
        "Inside the organization’s headquarters,"
        + "\nyou see tables filled with women working on pamphlets, websites, charities, and more."
        + "\n> You also see some sort of speech room with a podium and several plastic chairs." 
        + "\n> The front desk is right in front of you; the two women at the front desk are working on something on their computers."
        + "\n> There’s a coat tree right next to the front desk with a single raincoat on it."
        + "\n> Finally, you see three restrooms and notice a door that has a sign that says \"AUTHORIZED PERSONNEL ONLY.\"","The headquarters of an evil organization.",
        i31,5);
        
        //CHANGE THE DESCRIPTION IN PACIFIST MAYBE
        map[3][2] = new Room(true,true,false,false,"Main Street S",
        "This road looks like a billion tanks rolled on it in one day."
        + "\n> There’s trash, broken tools, and paper everywhere."
        + "\n> A pile of pro-Alyssa and pro-Hayward posters can be seen directly at the center of this part of the street."
        + "\n> Money is scattered everywhere, most of them not usable."
        + "\n> There’s vulgar and violent graffiti on the backs of both the PWO’s headquarters and the PPD’s building.",
        "A pointless road in a hopeless town.",
        i32,10);
        
        //south is a one-way door (exit)
        map[3][3] = new Room(false,true,true,false,"Prudence Police Department",
        "Inside the department building, you see a living room with a couch, table, and a coffee machine."
        + "\n> The front desk is right in front of you; the guy at the front desk is carefully watching you and the building’s entrance."
        + "\n> There’s a hallway leading to the Sheriff’s Office and other rooms authorized for police officers only."
        + "\n> Finally, you see a single restroom right next to the other entrance of the building.",
        "The department of injustice and corruption.",i33,0);
        String[] fdcChat = {"Front Desk Clerk: ...","Front Desk Clerk: Hmm...","Front Desk Clerk: ...",
        "Front Desk Clerk: ...","Front Desk Clerk: What do you want?","Front Desk Clerk: A lighter?","Front Desk Clerk: Weird request, but alright...",
        "You got the Lighter.","Front Desk Clerk: Okay you have your lighter now can you please leave?","Front Desk Clerk: My shift's over. Get out of here.",
        "Front Desk Clerk: So why did you come here then? Get out of here.","The Front Desk clerk leaves.","Lighter | Nothing"};
        map[3][3].getRoomCharas().add(new Chara("Front Desk Clerk","","",fdcChat));
        
        //CHANGE canGoWest to true if player has ID card; also change direction values for testing
        map[3][4] = new Room(false,true,false,false,2,"Washington Drive N",
        "This road looks cleaner than the other war-torn areas, but it’s still quite messy and reminiscent of the town’s conflict." 
        + "\n> For some reason, there are a lot of trash cans, trampled plants, and scattered papers on this part of the road." 
        + "\n> There are two police cars parked at the corners. The cement is somehow still neat and intact."
        + "\n> To the east is the Prudence Police Department building.","A pointless road in a hopeless town.",
        i34,0,50,new int[]{4},0);
        
        map[4][0] = new Room(true,false,true,false,"Town Entrance",
        "You see a poorly maintained sign that you can barely make out as “WELCOME TO PRUDENCE."
        + "\n> It looks like no one has stepped foot on this part of the town in a long time." 
        + "\n> There are two poorly maintained street signs you can barely make out: one says “Stanton Road” pointing north and the other says “Main Street” pointing east."
        + "\n> To the west is California State Route 1, but you just came from there. ",
        "A pointless place in a hopeless town.",i40,0);
        
        //Might set fightCounter to 0 to make sure Amara is the only one encountered
        map[4][1] = new Room(true,false,true,true,1,"Main Street SW",
        "This road looks like it went through war a hundred times."
        + "\n> Trash and weapons are everywhere, most of them not being usable at all." 
        + "\n> There are a lot of pro-Alyssa and pro-Hayward posters and items in this place." 
        + "\n> The cement is so brittle cars can’t even drive here anymore."
        + "\n> To the north is the headquarters of the Prudence Women’s Organization.",
        "A pointless road in a hopeless town.",i41,0,0,new int[]{0,1,2,3},0);
        
        //Change as needed to test rooms & also change description
        //TEMPORARILY CHANGING FIRST VALUE AS OF NOW
        map[4][2] = new Room(false,false,false,true,"Washington Intersection",
        "This intersection looks like it went through war a thousand times."
        + "\n> Trash and weapons are everywhere, most of them not being usable at all."
        + "\n> There are a lot of broken pro-Alyssa and pro-Hayward posters and items in this place."
        + "\n> The cement is so brittle cars can’t even drive here anymore."
        + "\n> To the north is the rest of Main Street. To the east is the road next to Peony Park."
        + "\n> You can barely make out two street signs that say “Main Street” pointing north and west and another sign that says “Washington Drive” pointing east."
        + "\n> However, all those roads are currently blocked off, and there’s a HUGE barrier blocking the rest of Main Street.",
        "A pointless intersection in a hopeless town.",i42,0);
        
        map[4][3] = new Room(false,false,true,true,2,"Washington Drive W",
        "This road looks cleaner than the other war-torn areas, but it’s still reminiscent of the town’s conflict."
        + "\n> There’s absolutely nothing on this road but some trash cans and trampled plants."
        + "\n> The cement is somehow still neat and intact. There’s currently a barrier separating this road from the Washington Intersection."
        + "\n> However, the barrier can be exited from this road. To the north is the Prudence Police Department building.",
        "A pointless road in a hopeless town.",i43,0,70,new int[]{4},25);
        
        //CHANGE FVUB TO 70 ONCE SERGEANT STEWART FIGHT IS DONE
        //ADD SERGEANT STEWART ID
        map[4][4] = new Room(true,false,false,true,2,"Washington Drive",
        "This road looks cleaner than the other war-torn areas, but it’s still quite messy and reminiscent of the town’s conflict."
        + "\n> For some reason, there are a lot of trash cans, trampled plants, and scattered papers on this part of the road." 
        + "\n> The cement is somehow still neat and intact.","A pointless road in a hopeless town.",i44,0,60,new int[]{4},0);
        
//TODO: When you have made your game work in the console
//change done to true to play your game in the JFrame        
        String output = "";
        String output0 = "";
        if(!done){
            updateRoom();
            System.out.println(gameIntro());
            System.out.println(selectName());
        }
        
        while(!done){
            System.out.print("\n\n>> ");      
            userInput = s.nextLine().toLowerCase();
            command = getFirstWord(userInput);
            item = getRestOfSentence(userInput);
            output = executeCommand(command, item);
            Room temp = currentRoom;
            updateRoom();
            System.out.println(output +"\n ");
            int iTemp = progress;
            gameProgression(command,item);
            if(command.equals("quit")){
                s.close();
                done = true;
            }
            if (!done && !temp.getName().equals(currentRoom.getName()) && currentRoom.getAreaN() > 0 && iTemp == progress){
                System.out.print(checkFight());
            }
            checkRoute();
          }
          s.close();    
    }
    
    public static String gameProgression(String param1, String param2){
        if (progress == 0 && (Commands.currentRoom == Commands.map[4][1] || Commands.currentRoom == Commands.map[3][0])){
            progress++;
            return Chara.amaraEncounter();
        }
        if (progress == 1 && Commands.currentRoom == Commands.map[4][2] && param1.equals("use") && pObjIndex(param2) == -1){
            progress++;
            return Chara.amaraFight();
        }
        if (progress == 2 && Commands.currentRoom == Commands.map[4][3]){
            progress++;
            return Chara.sheriffEncounter1();
        }
        if (progress == 3 && Commands.currentRoom == Commands.map[4][4]){
            progress++;
            return Chara.stewartFight();
        }
        if (progress == 4 && Commands.currentRoom == Commands.map[3][4]){
            progress++;
            return Chara.sheriffEncounter2();
        }
        if (progress == 5 && Commands.currentRoom == Commands.map[4][2] && pObjIndex("Dynamite") != -1 &&
        pObjIndex(playerInventory.get(pObjIndex("Dynamite")).getComplement()) != -1){
            progress++;
            return Chara.conradFight();
        }
        if (progress == 6 && Commands.currentRoom == Commands.map[2][2]){
            progress++;
            return Chara.haywardEncounter1();
        }
        if (progress == 7 && (Commands.currentRoom == Commands.map[2][1] || Commands.currentRoom == Commands.map[1][2])){
            progress++;
            return Chara.alyKatEncounter1();
        }
        if (progress == 8 && Commands.currentRoom == Commands.map[1][1]){
            progress++;
            return Chara.alyKatEncounter2();
        }
        if (progress == 9 && Commands.currentInRoom == Commands.map[0][1].getInRooms()[0]){
            progress++;
            return Chara.alyKatEncounter3();
        }
        if (progress == 10 && Commands.currentRoom == Commands.map[0][2]){
            progress++;
            return Chara.haywardEncounter2();
        }
        if (progress == 11 && Commands.currentRoom == Commands.map[1][1]){
            progress++;
            Commands.map[1][0].getRoomInventory().add(new Tool("Spare Key","Unlocks the gate leading to Kaitlyn Beach. Too bad someone left behind their key to the purest beach in central California!",
            0.1,"Main Street K","east","","You used the Spare Key. You opened the east gate leading to Kaitlyn Beach.",""));
            return Chara.kaitlynEncounter1();
        }
        if (progress == 12 && Commands.currentRoom == Commands.map[0][3]){
            progress++;
            return Chara.kaitlynEncounter2();
        }
        return "";
    }
    
    //INCOMPLETE
    public static void checkRoute(){
        if (kills[0] > 0 || kills[1] > 0 || kills[2] > 0 || kills[3] > 0 || kills[4] > 0){
            isPacifist = false;
        }
        if (kills[0] == areaFights[0]){
            isDestruction = true;
            System.out.println(Chara.kaitlynNoDestruction());
        }
    }
    
    public static String gameIntro(){
        String[] intros = {"News Reporter: Today on NBC News - a small town near the coasts of central California, Prudence, has fallen into a full-fledged faction war\nafter a popular married couple, Alyssa and Hayward Mila, recently filed for divorce...", 
            "News Reporter: Here's Candice Nguyen with Mayor Reina Valeska.",
            "Candice: Ms. Valeska your town is in a state of war against itself after the Milas' divorce how do you plan on tackling this conflict and finding a peaceful resolution?", 
            "Mayor: Well it's not easy to handle a town of 10,233 people fighting amongst themselves... so our first step is to try to migitate this conflict, uhm, understand both sides...", 
            "Candice: Ms. Valeska you mentioned appeasing both sides at the same time how do you intend to resolve the conflict through that strategy?", 
            "Mayor: *smiles* We're out of time here Candice thank you for", 
            "*explosion*", 
            "Alyssa Protesters: We stand for Alyssa! We stand-", 
            "Hayward Protesters: Justice for Hayward! Justice for Hayward...",
            "..."};
        String enter;
        System.out.println("Press \"Enter\" to continue.");
        for (String intr : intros){
            enter = s.nextLine();
            System.out.println(intr);
        }
        enter = "";
        return "";
    }
    
    public static String selectName(){
        boolean inL = true;
        String n;
        String nn;
        String yn;
        while (inL){
            System.out.print("\nType the player's name to continue:\n\n>> ");
            n = s.nextLine();
            boolean next = false;
            nn = n.trim().toLowerCase();
            if (nn.equals("alyssa")){
                System.out.println("\nSorry, that's my name!");
            }
            else if (nn.equals("hayward")){
                System.out.println("\n...");
            }
            else if (nn.equals("kaitlyn")){
                System.out.println("\nSorry, that's my name and only name!!! :)");
            }
            else if (nn.equals("reina")){
                System.out.println("\nWhat an interesting name you have!");
                next = true;
            }
            else if (nn.equals("valeska")){
                System.out.println("\nOh I'm sorry young one, but I can't allow you to have that name.");
            }
            else if (nn.equals("quinn")){
                System.out.println("\nPlease select different credentials to prevent name conflicts.");
            }
            else if (nn.equals("conrad")){
                System.out.println("\nThis town isn't big enough for that name of yours.");
            }
            else if (nn.equals("amara")){
                System.out.println("\nGet your own name!");
            }
            else if (nn.equals("grayson")){
                System.out.println("\nReasonable name.");
                next = true;
            }
            else if (nn.equals("ramiro")){
                System.out.println("\nThe system does not appreciate your naming choice."); 
            }
            else if (nn.equals("constance")){
                System.out.println("\nPlease select a different name to avoid confusion!");
            }
            else if (nn.equals("stewart")){
                System.out.println("\nI already have that name, bud.");
            }
            else if (nn.equals("audrey")){
                System.out.println("\nChoose another name please. Thank you!");
            }
            else if (nn.length() == 0){
                System.out.println("\nYou didn't type anything for a name!");
            }
            else {
                next = true;
            }
            if (next == true){
                System.out.print("\nYou chose the name \"" + n + "\". Is that correct?\n\n>> ");
                yn = s.nextLine();
                yn = yn.trim();
                if (yn.equalsIgnoreCase("yes")){
                    playerName = n;
                    inL = false;
                }
                else if (!yn.equalsIgnoreCase("no")){
                    System.out.println("\nPlease select only \"yes\" or \"no.\"");
                }
            }
        }
        n = "";
        return "\n\nYou find yourself on an empty and desolate street.";
    }
    
    public static String getFirstWord(String input){
        int spaceLocation = input.indexOf(" ");
        if(spaceLocation > 0)
            return input.substring(0,spaceLocation);
        if(input.length() >0)
            return input;
        return "";
    }

    public static String getRestOfSentence(String input){
        int spaceLocation = input.indexOf(" ");
        if(spaceLocation >= 0)
            return input.substring(spaceLocation+1);
        return "";
    }    

    public static boolean onlyHasSpaces(String input){
        for (int i = 0; i < input.length()-1; i++){
            String checker = input.substring(i,i+1);
            if (!input.equals(null) || !checker.equals(" ")){
                return false;
            }
        }
        return true;
    }
    
    //MOST LIKELY DONE JUST POLISH IT LATER IF NEEDED
    public static String north(){
        if (currentInRoom != null){
            return "You're still inside a room! Use the \"exit\" command instead.";
        }
        if (currentRoom.getCanGoNorth()){
            row--;
            return "You moved north.";
        }
        return "You can't go north from this area.";
    }

    //MOST LIKELY DONE JUST POLISH IT LATER IF NEEDED
    public static String south(){
        if (currentInRoom != null){
            return "You're still inside a room! Use the \"exit\" command instead.";
        }
        if (currentRoom.getCanGoSouth()){
            row++;
            return "You moved south.";
        }
        return "You can't go south from this area.";
    }

    //MOST LIKELY DONE JUST POLISH IT LATER IF NEEDED
    public static String east(){
        if (currentInRoom != null){
            return "You're still inside a room! Use the \"exit\" command instead.";
        }
        if (currentRoom.getCanGoEast()){
            column++;
            return "You moved east.";
        }
        return "You can't go east from this area.";
    }
    
    //MOST LIKELY DONE JUST POLISH IT LATER IF NEEDED
    public static String west(){
        if (currentInRoom != null){
            return "You're still inside a room! Use the \"exit\" command instead.";
        }
        if (currentRoom.getCanGoWest()){
            column--;
            return "You moved west.";
        }
        return "You can't go west from this area.";
    }
    
    //MIGHT BE BUGGY BUT JUST POLISH LATER
    public static String enter(String room){
        if (currentInRoom != null){
            return "You're still inside a room!";
        }
        Room[] rooms = currentRoom.getInRooms();
        if (rooms != null){
            for (Room r : rooms){
                if (r.getName().equalsIgnoreCase(room)){
                    if (r.getCanEnter()){
                        currentInRoom = r;
                        return "You entered the " + currentInRoom.getName();
                    }
                    return "You can't enter this room.";
                }
            }
            return "You couldn't find this room.";
        }
        return "This place has no rooms!";
    }
    
    //MIGHT BE BUGGY BUT JUST POLISH LATER
    public static String exit(){
        if (currentInRoom == null){
            return "You're not inside any room!";
        }
        Room current = currentInRoom;
        currentInRoom = null;
        return "You left " + current.getName() + ".";
    }
    
    /*MIGHT NOT IMPLEMENT
    public static String store(){
        if (currentInRoom != null){
            return "There are no stores inside rooms!";
        }
        if (currentRoom.getStores() == null){
            return "This area has no stores.";
        }
        if (currentRoom.getStores().length > 1){
            boolean input = true;
            while (input){
                System.out.println("Which store do you want to enter?");
                System.out.println(Store.storeNames());
                System.out.print("\n>>");
                String storing = s.nextLine();
                for (int i = 0; i < currentRoom.getStores().length; i++){
                    if (storing.equalsIgnoreCase(currentRoom.getStores()[i].getName())){
                        return currentRoom.getStores()[i].inStore();
                    }
                }
                System.out.println("No such store exists in this area!");
            }
        }
        return currentRoom.getStores()[0].inStore();
    }
    */
   
    //MOST LIKELY DONE JUST POLISH IT LATER IF NEEDED
    public static String take(String item){
        if (item.equals("money")){
            if (currentRoom.getMoney() > 0){
                int rm = currentRoom.getMoney();
                pMoney += rm;
                currentRoom.setMoney(0);
                return "You took $" + rm + " from this room!";
            }
            return "There's no money in this room.";
        }
        if (playerInventory.size() == 6){
            return "You have too many items in your inventory.";
        }
        double sum = 0.0;
        for (Item i : playerInventory){
            sum += i.getWeight();
        }
        if (sum > 50.0){
            return "Your inventory is too heavy!";
        }
        if (currentInRoom != null){
            if (currentInRoom.getObject(item) != null){
                Item newObj = currentInRoom.removeObject(item);
                playerInventory.add(newObj);
                return "You took the " + newObj.getName() + ".";
            }
            return "You couldn't find this object in this room.";
        }
        if (currentRoom.getObject(item) != null){
            Item newObj = currentRoom.removeObject(item);
            playerInventory.add(newObj);
            return "You took the " + newObj.getName() + ".";
        }
        return "You couldn't find this object in this area.";
    }
    
    //Helper method
    public static int pObjIndex(String item){
        for (int i = 0; i < playerInventory.size(); i++){
            if (playerInventory.get(i).getName().equalsIgnoreCase(item)){
                return i;
            }
        }
        return -1;
    }
    
    //MOST LIKELY DONE JUST POLISH IT LATER IF NEEDED
    public static String drop(String item){
        if (item.length() > 0){
            if (currentWeapon != null){
                if (currentWeapon.getName().equalsIgnoreCase(item)){
                    if (currentInRoom != null){
                        currentInRoom.getRoomInventory().add(currentWeapon);
                    }
                    else {
                        currentRoom.getRoomInventory().add(currentWeapon);
                    }
                    pPow -= currentWeapon.getAddPOW();
                    currentWeapon = null;
                    return "You dropped your current weapon.";
                }
            }
            if (currentArmor != null){
                if (currentArmor.getName().equalsIgnoreCase(item)){
                    if (currentInRoom != null){
                        currentInRoom.getRoomInventory().add(currentArmor);
                    }
                    else {
                        currentRoom.getRoomInventory().add(currentArmor);
                    }
                    pDef -= currentArmor.getAddDEF();
                    currentArmor = null;
                    return "You dropped your current armor.";
                }
            }
        }
        int index = pObjIndex(item);
        if (index != -1){
            Item removed = playerInventory.get(index);
            if (currentInRoom != null){
                currentInRoom.getRoomInventory().add(removed);
            }
            else {
                currentRoom.getRoomInventory().add(removed);
            }
            return "You dropped the " + removed.getName() + ".";
        }
        return "You don't have this item in your inventory.";
    }
    
    //MOST LIKELY DONE JUST POLISH IT LATER IF NEEDED
    public static String look(){
        if (!isDestruction){
            if (currentInRoom != null){
                return "> " + currentInRoom.getName() + " - " + currentInRoom.getDescription();
            }
            return "> " + currentRoom.getName() + " - " + currentRoom.getDescription();
        }
        if (currentInRoom != null){
            return "> " + currentInRoom.getName() + " - " + currentInRoom.getDDescription();
        }
        return "> " + currentRoom.getName() + " - " + currentRoom.getDDescription();
    }

    //MOST LIKELY DONE JUST POLISH IT LATER IF NEEDED
    public static String items(){
        if (currentInRoom != null){
            return currentInRoom.objectsInRoom();
        }
        return currentRoom.objectsInRoom();
    }
    
    //MOST LIKELY DONE JUST POLISH IT LATER IF NEEDED
    public static String inventory(){
        if (playerInventory.size() == 0){
            return "You currently don't have anything in your inventory.";
        }
        String items = "Your inventory: ";
        for (Item i : playerInventory){
            items += i.getName() + ", ";
        }
        return items.substring(0,items.length()-2);
    }    
    
    //MOST LIKELY DONE JUST POLISH IT LATER IF NEEDED
    public static String examine(String item){
        int index = pObjIndex(item);
        if (index != -1){
            return "> " + playerInventory.get(index).getName() + " - " + playerInventory.get(index).getDescription();
        }
        return "You don't have this item in your inventory.";
    }   
    
    /*{"Front Desk Clerk: ...","Front Desk Clerk: Hmm...","Front Desk Clerk: ...",
        "Front Desk Clerk: ...","Front Desk Clerk: What do you want?","Front Desk Clerk: A lighter?","Front Desk Clerk: Weird request, but alright...",
        "You got the Lighter.","Front Desk Clerk: Okay you have your lighter now can you please leave?","Front Desk Clerk: My shift's over. Get out of here.",
        "Front Desk Clerk: So why did you come here then? Get out of here.","The Front Desk clerk leaves.","Lighter | Nothing"};*/
    public static String chat(String chara){
        String enter;
        if (currentRoom == map[3][3] && chara.equalsIgnoreCase("Front Desk Clerk") && currentRoom.getRoomCharas().size() > 0){
            System.out.println(map[3][3].getRoomCharas().get(0).getChatDialogue()[0]);
            for (int i = 1; i < 5; i++){
                enter = s.nextLine();
                System.out.println(map[3][3].getRoomCharas().get(0).getChatDialogue()[i]);
            }
            System.out.println(map[3][3].getRoomCharas().get(0).getChatDialogue()[
            map[3][3].getRoomCharas().get(0).getChatDialogue().length-1]);
            System.out.print("\n>> ");
            String input = s.nextLine().trim().toLowerCase();
            if (input.equals("lighter")){
                System.out.println("\n" + map[3][3].getRoomCharas().get(0).getChatDialogue()[5]);
                for (int j = 6; j < 10; j++){
                    enter = s.nextLine();
                    System.out.println(map[3][3].getRoomCharas().get(0).getChatDialogue()[j]);
                }
                playerInventory.add(new Tool("Lighter","Can’t cook anything, but can maybe detonate something...?",0.07,"Washington Drive","north","Dynamite","You ignite the dynamite with the lighter. You throw it to the barrier blocking the rest of Main Street." 
                + "\nThe barrier is now destroyed and you can now access the entirety of Main Street.","You also need the Lighter to ignite this item!"));
                currentRoom.getRoomCharas().remove(0);
            }
            else if (input.equals("nothing")){
                System.out.println(map[3][3].getRoomCharas().get(0).getChatDialogue()[10]);
            }
            else {
                System.out.println("Front Desk Clerk: ...");
            }
            return "";
        }
        if (chara.equalsIgnoreCase("Alyssa") || chara.equalsIgnoreCase("Kaitlyn")){
            return "Kaitlyn: hiii! oops! wrong nextLine() stream!!! :( :b >:)";
        }
        if (chara.equalsIgnoreCase("Hayward")){
            return "Kaitlyn: don't talk to dad he doesn't like talking :( ...wait, which nextLine() is this???";
        }
        return "This person isn't in this area.";
    }
    
    //PROBABLY DONE...? (needs testing)
    public static String read(String item){
        int index = pObjIndex(item);
        if (index != -1 && playerInventory.get(index) instanceof Text){
            return "-> " + playerInventory.get(index).getName() + ":\n" + "> " + playerInventory.get(index).getText();
        }
        if (!(playerInventory.get(index) instanceof Text)){
            return "You can't read this item!";
        }
        return "You don't have this item in your inventory.";
    }
    
    //PROBABLY DONE??? (MIGHT BE VERY BUGGY)
    public static String use(String item){
        int index = pObjIndex(item);
        if (index != -1){
            Item thing = playerInventory.get(index);
            if (thing instanceof Heal){
                if (pHp == maxpHp){
                    return "Your health is still maxed out!";
                }
                if (pHp + thing.getHeal() >= maxpHp){
                    playerInventory.remove(index);
                    pHp = maxpHp;
                    return "You consumed the " + thing.getName() + ". Your health was maxed out.";
                }
                playerInventory.remove(index);
                pHp += thing.getHeal();
                return "You consumed the " + thing.getName() + ". You gained " + thing.getHeal() + " hp!";
            }
            if (thing instanceof Weapon){
                if (currentWeapon != null){
                    return "You still have the " + currentWeapon.getName() + " equipped!";
                }
                playerInventory.remove(index);
                currentWeapon = (Weapon) thing;
                pPow += thing.getAddPOW();
                return "You equipped the " + thing.getName() + ".";
            }
            if (thing instanceof Armor){
                if (currentArmor != null){
                    return "You still have the " + currentArmor.getName() + " equipped!";
                }
                playerInventory.remove(index);
                currentArmor = (Armor) thing;
                pDef += thing.getAddDEF();
                return "You equipped the " + thing.getName() + ".";
            }
            if (thing instanceof Tool){ 
                thing = (Tool) thing;
                if (thing.getComplement().equals("")){
                    if (thing.getRoomUse().equals(currentRoom.getName())){
                        currentRoom.unlock(thing.getOpenDirect());
                        if (!(thing.getName().substring(thing.getName().length()-3).equals("Key"))){
                            playerInventory.remove(index);
                        }
                        return thing.getUseMessage();
                    }
                    return "You can't use this item in this area!";
                }
                if (pObjIndex(thing.getComplement()) != -1){
                    if (thing.getRoomUse().equals(currentRoom.getName())){
                        currentRoom.unlock(thing.getOpenDirect());
                        playerInventory.remove(index);
                        playerInventory.remove(pObjIndex(thing.getComplement()));
                        return thing.getUseMessage();
                    }
                    return "You can't use these items in this area!";
                }
                return thing.getNoComplement();
            }
            return "You tried using the " + thing.getName() + ". Nothing happened.";
        }
        return "You don't have this item in your inventory.";
    }    
    
    //COMPLETE???
    public static String stats(){
        String weaponName;
        String armorName;
        if (currentWeapon == null){
            weaponName = "None";
        }
        else {
            weaponName = currentWeapon.getName();
        }
        if (currentArmor == null){
            armorName = "None";
        }
        else {
            armorName = currentArmor.getName();
        }
        int totalKills = 0;
        for (int i : kills){
            totalKills += i;
        }
        return "-> " + playerName + " (LVL " + pLvl + ")\n"
        + "> HP: " + pHp + "/" + maxpHp + "\n"
        + "> POW: " + pPow + "\n"
        + "> DEF: " + pDef + "\n"
        + "> Weapon: " + weaponName + "\n"
        + "> Armor: " + armorName + "\n"
        + "> Kills: " + totalKills + "\n"
        + "> XP: " + pXp;
    }
    
    //COMPLETE???
    public static String checkFight(){
        if (encountered[currentRoom.getAreaN()-1] == areaFights[currentRoom.getAreaN()-1] || bossFought[currentRoom.getAreaN()-1]){
            return "";
        }
        currentRoom.setFightValue((int) (Math.random() * 100) + 1);
        if (currentRoom.getFightValue() <= currentRoom.getFVUB()){
            int rand = currentRoom.getPossibleCharas()[(int)(Math.random() * currentRoom.getPossibleCharas().length)];
            encountered[currentRoom.getAreaN()-1]++;
            return Chara.copyableC[rand].fight();
        }
        return "";
    }

    //COMPLETE...?
    public static String help(){
        return "The following commands are used to play the game\n"
                + "Command             Example                       Description\n"
                + "North               north                         Moves the player north\n"
                + "South               south                         Moves the player south\n"
                + "East                east                          Moves the player east\n"
                + "West                west                          Moves the player west\n"
                + "Enter               enter Room          Enters a specific room in an area, if available\n"
                + "Exit                exit                          Exits the current room in an area, if inside"
                + "Look                look                          Provides a detailed description of the area\n"
                + "Items               items                         Lists all the available items in the area\n"
                + "Take                take crowbar                  Adds an item from an area to your inventory\n"
                + "Drop                drop crowbar                  Removes an item from your inventory and leaves it in the area\n"
                + "Inventory           inventory                     Displays the names of items in your inventory\n"
                + "Examine             examine crowbar               Provides a detailed description of the inventory item\n"
                + "Describe            describe Audrey               Provides a description of the specified character in the area\n"
                + "Chat                chat Audrey                   Talks with character in the area\n"
                + "Read                read PWO Handbook             Displays the text of an excerpt\n"
                + "Use                 use crowbar                   Uses an item based on its type\n"
                + "Stats               stats                         Displays the player's stats\n"
                + "Save                save                          Saves the player's game, if possible (HIGHLY EXPERIMENTAL AND MIGHT NOT WORK)\n"
                + "Help                help                          Displays the help menu\n"
                + "Quit                quit                          Quits the game instantly\n";
    }
    
    //COMPLETE
    public static String quit(){
        return "You have decided to quit the game.";
    }

    //COMPLETE
    public static String invalid(){
        return "Unknown command. Refer to the \"help\" method to see the list of commands.";
    }

    public static String executeCommand(String command, String specified){
        if(command.equals("north")) //Complete
            return north();
        if(command.equals("south")) //Complete
            return south();
        if(command.equals("east")) //Complete
            return east();
        if(command.equals("west")) //Complete
            return west();
        if(command.equals("enter")) //Complete
            return enter(specified);
        if(command.equals("exit")) //Complete
            return exit();
        if(command.equals("inventory")) //Complete
            return inventory();
        if(command.equals("look")) //Complete???
            return look();
        if(command.equals("items")) //Complete
            return items();
        if(command.equals("take")) //Complete???
            return take(specified);
        if(command.equals("drop")) //Complete???
            return drop(specified);
        if(command.equals("examine")) //Complete
            return examine(specified);
        if(command.equals("chat"))
            return chat(specified);
        if(command.equals("use")) //Complete
            return use(specified);
        if(command.equals("read")) //Complete
            return read(specified);
        if(command.equals("stats")) 
            return stats();
        if(command.equals("help")) //Complete
            return help();
        if(command.equals("quit")) //Complete
            return quit();
        return invalid();//Complete
    }
    
    public static void updateRoom(){
        currentRoom = map[row][column];
    }

    public static Room getCurrentRoom(){
        updateRoom();
        return currentRoom;
    }

    public static void main(String[] args){
        runGame();
    }
}


