import java.util.*;

/**
 * Chara class - contains all constructors and commands related to an NPC
 * Contains Boss, Mini Boss, Fight, Fight Commands, Attacks, etc.
 * @author (your name)
 * @version (a version number or a date)
 */
public class Chara
{
    private String name; //
    private int hp; // 
    private int pow; //
    private int def; // 
    private String desc;
    private String pDesc;
    private String check; //
    private String dCheck;
    private boolean canLeave; //
    private int wantSpare; //
    private int spareValue; //
    private int xp; //
    private int dxp;
    private int money; //
    private String[] battleFlavor; //
    private String[] battleFlavorD;
    private String[] battleDialogue; //
    private String[] battleDialogueD;
    private boolean isFought = false;
    private int bossArea = 0;
    private boolean friends;
    private String[] chatDialogue;
    private boolean isKilled = false; //
    
    //Bosses (prototype)
    private static final String[] abfd = new String[3];
    private static final String[] abdd = new String[3];
    public static Chara alyssa = new Chara("Alyssa",100,75,10,"A beautiful maiden with sparkling brown eyes and dark hair extending all the way to her waist.\n> She wears a striped T-shirt of pale blue and white, black shorts, and brown slippers.",
    "A beautiful maiden with sparkling brown eyes and dark hair extending all the way to her waist.\n> She wears a striped T-shirt of pale blue and white, black shorts, and brown slippers.",
    "","Alyssa - 75 POW 10 DEF.\n--> She started all of it. ",false,0,0,0,null,abfd,null,abdd,3,true);
    
    private static String[] kbf = new String[3];
    private static String[] kbfd = new String[3];
    private static String[] kbd = new String[3];
    private static String[] kbdd = new String[3];
    public static Chara kaitlyn = new Chara("Kaitlyn",Integer.MAX_VALUE,555,Integer.MAX_VALUE,"It’s just me, Kaitlyn!","It’s just me, Kaitlyn!",
    "YOUR WORST NIGHTMARE","Forgettable",false,0,0,0,kbf,kbfd,kbd,kbdd,3,true);
    
    private static final String[] cbf = {"Sheriff Conrad firmly points his taser at you.","Sheriff Conrad is placing you under arrest.",
    "Sheriff Conrad wonders where you really came from."};
    private static final String[] cbfd = new String[3];
    private static final String[] cbd = {"Sheriff: Troublemakers like you are the reason why this town is collapsing.","Sheriff: I thought you’d be different since you’re not from here, but I was wrong.",
    "Sheriff: You just want to create more chaos, don’t you?","Sheriff: In that case, you’re not welcome here.","Sheriff: Hell, you don’t even belong to the prisons of this place.",
    "Sheriff: All this town wants is for people like you to get the hell out of here.","Sheriff: But even that you can’t do.","Sheriff: ‘Cause you’d rather blow this place up.",
    "Sheriff: And...","Sheriff: ...","Sheriff: Hey! Quit moving!","Sheriff: ...","Sheriff: Hey!","Sheriff: ...","Sheriff: My taser...","Sheriff: It’s out of batteries.","Sheriff: ...","Sheriff: ...",
    "Sheriff: You’re... not gonna do anything?","Sheriff: ...","Sheriff: You’re not really one of those troublemakers, aren’t you?","Sheriff: And as for the dynamite... you just want to visit the rest of the town, right?",
    "Sheriff: ...","Sheriff: Well, I got nothin’ else to say to you.","Sheriff: ..."};
    private static final String[] cbdd = new String[3];
    public static Chara conrad = new Chara("Sheriff Conrad",300,30,30,"","","Sheriff Conrad - 30 POW 30 DEF.\n> Determined to put an end to the town’s conflict.","",
    false,750,1500,0,cbf,cbfd,cbd,cbdd,2,false);
    
    private static final String[] ambf = {"Amara glares at you carefully.","Amara wonders what your true intentions are.",
    "Amara doesn't trust you a single bit.","Amara is finally letting you go."};
    private static final String[] ambfd = new String[3];
    private static final String[] ambd = {"Amara: ...","Amara: ...","Amara: ...","Amara: ...","Amara: ...?","Amara: You’re... not fighting at all?",
    "Amara: ...","Amara: ...","Amara: ...","Amara: Well...","Amara: Honestly... I don’t wanna fight you, either.","Amara: ...","Amara: Hey...",
    "Amara: ...","Amara: I’m sorry, I thought you were one of them."};
    private static final String[] ambdd = new String[3];
    public static Chara amara = new Chara("Amara",150,15,15,"","",
    "Amara - 25 POW 25 DEF.\n--> A strong advocate of women’s rights.","",false,75,150,0,ambf,ambfd,ambd,ambdd,1,false);
    
    //Mini-bosses (alpha version)
    private static final String[] sebf = {"Sergeant Stewart looks nervous and cautious.","Sergeant Stewart wants to know why you're here."};
    private static final String[] sebfd = new String[3];
    private static final String[] sebd = {"Sergeant: ...","Sergeant: You’re... not attacking me?","Sergeant: Why... what...",
    "Sergeant: ...","Sergeant: You don’t support any side now, don’t you bud?","Sergeant: Yeah, I thought so.","Sergeant: You just want to pass by. That’s understandable."};
    private static final String[] sebdd = new String[3];
    public static Chara stewart = new Chara("Sergeant Stewart",250,25,25,"","","Sergeant Stewart - 25 POW 25 DEF.\n--> He’s extremely distressed by the town’s conflict.","",false,625,1250,100,
    sebf,sebfd,sebd,sebdd,2);
    
    public static Chara audrey = new Chara();
    
    //Other characters
    //Representative
    private static final String[] repbf = {"The Representative wants you to leave and never come back.","The Representative desperately wants to know your intentions.",
    "The Representative is relieved with your conduct.","The Representative really wants to know your TRUE intentions.","The Representative is waiting for you to say more."};
    private static final String[] repbd = {"Representative: Please leave...","Representative: No one wants you here...","Representative: Thank goodness you’re merciful.",
    "Representative: I’m listening...","Representative: Thank you. Now stay there where I can see you."};
    public static Chara representative = new Chara("Representative",100,15,10,"","",
    "Representative - 15 POW 10 DEF.\n--> Just wants the best for their side of town. ","Corrupt hypocrite.",
    false,0,3,50,100,20,repbf,repbd);
    
    //Lawyer
    private static final String[] lawbf = {"The Lawyer observes your movements.","The Lawyer is berating you for your \"actions.\"",
    "The Lawyer finally sees your pacifistic intentions.","The Lawyer is expecting more proof from you.","The Lawyer waits to see if you’ll plead your case."};
    private static final String[] lawbd = {"Lawyer: Your factions deserve life in prison.","Lawyer: The courts will make y’all pay once the war is done.",
    "Lawyer: Looks like you’re not here to fight. I appreciate that.","Lawyer: Your case seems valid, but are you truly innocent?","Lawyer: At least you have the decency to lower your attacks."};
    public static Chara lawyer = new Chara("Lawyer",100,10,15,
    "","The lawyer is now taking a nice, relaxing break now that nobody is fighting anymore.",
    "Lawyer - 10 POW 15 DEF.\n--> Trying to defend people in a criminal town.","Another idiot in this town. ",
    false,0,3,50,100,20,lawbf,lawbd);
    
    //Undergraduate
    private static final String[] undbf = {"The Undergraduate wants you to leave.","The Undergraduate tells you to fight your battles somewhere else.",
    "The Undergraduate apologizes for their misunderstanding."};
    private static final String[] undbd = {"PCC Undergraduate: Why are you here? You’re not even a student.","PCC Undergraduate: You wanna protest? Do that somewhere else!",
    "PCC Undergraduate: Sorry, I thought you were one of them. My mistake."};
    public static Chara undergrad = new Chara("PCC Undergraduate",100,10,10,
    "","The undergraduate is considering taking a break from intense studying to visit Peony Park. ",
    "PCC Undergraduate - 10 POW 10 DEF. Trying to study despite all the fighting. ","PCC Undergraduate - 10 POW 10 DEF. Trying to study despite all the fighting. ",
    true,0,1,30,30,25,undbf,undbd);
    
    //Officer
    private static final String[] offbf = {"The Officer checks you for weapons.","The Officer holds their defensive stance firmly.",
    "The Officer is letting you go.","The Officer needs more reassurance that you’re safe."};
    private static final String[] offbd = {"Police Officer: I need to see some ID!","Police Officer: Stop right there! Put your hands up!",
    "Police Officer: I’ll let you off with a warning.","Police Officer: I’ll have you on your way if you cooperate."};
    public static Chara officer = new Chara("Police Officer",150,15,25,
    "","The officer is happy that the townsfolk are willing to obey the law again. ",
    "Police Officer - 15 POW 25 DEF.\n--> Trying to enforce the law in a lawless town.","Useless \"law enforcer.\"",
    false,0,2,20,50,20,offbf,offbd);
    
    //Alyssa Supporter
    private static final String[] alybf = {"The Alyssa Supporter is trying to know your stance.","The Alyssa Supporter holds her poster proudly.",
    "The Alyssa Supporter doesn’t want to argue with you anymore."};
    private static final String[] alybd = {"Alyssa Supporter: Who do you support? Hayward?","Alyssa Supporter: I stand for Alyssa! Justice for Alyssa!","Alyssa Supporter: I respect someone who doesn’t add to the fighting.","Alyssa Supporter: Yeah! Justice for Alyssa!"};
    public static Chara alyssaChar = new Chara("Alyssa Supporter",100,10,5,
    "","The Alyssa Supporter now supports neutrality and peace through their new poster.",
    "Alyssa Supporter - 10 POW 5 DEF.\n--> Will fight for Hayward no matter what.","Eliminate the problem.",
    true,0,1,10,25,27,alybf,alybd);
    
    //Hayward Supporter
    private static final String[] haybf = {"The Hayward Supporter is trying to know your stance.","The Hayward Supporter holds his poster proudly.",
    "The Hayward Supporter doesn’t want to argue with you anymore."};
    private static final String[] haybd = {"Hayward Supporter: Who do you support? Alyssa?","Hayward Supporter: I stand for Hayward! Justice for Hayward!","Hayward Supporter: I respect someone who doesn’t add to the fighting.","Hayward Supporter: Yeah! Justice for Hayward!"};
    public static Chara haywardChar = new Chara("Hayward Supporter",100,5,10,
    "","The Hayward Supporter now supports neutrality and peace through their new poster.",
    "Hayward Supporter - 5 POW 10 DEF.\n--> Will fight for Hayward no matter what.","Eliminate the problem.",
    true,0,1,10,25,27,haybf,haybd);
    
    //Neutrist
    private static final String[] neutbf = {"The Neutrist is trying to know your stance.","The Neutrist bravely defends themselves.",
    "The Neutrist accepts your neutrality.","The Neutrist is waiting for your follow-through."};
    private static final String[] neutbd = {"Neutrist: Who do you support? I hope no one.","Neutrist: I stand for no war! Stop the fighting!",
    "Neutrist: Thank you for not adding to the conflict.","Neutrist: Are you really anti-war? Prove it."};
    public static Chara neutrist = new Chara("Neutrist",100,1,20,"","",
    "Neutrist - 1 POW 20 DEF.\n--> Seems very tired of the conflict.","Neutrist - 1 POW 20 DEF.\n--> Seems very tired of the conflict.",
    true,0,2,10,10,50,neutbf,neutbd);
    
    //Robber
    private static final String[] robbf = {"The Robber contemplates their life choices.","The Robber wonders why they are doing this.",
    "The Robber is thinking of having a new life."};
    private static final String[] robbd = {"Robber: Hey... give me all your money! Or something like that.","Robber: Give me everything or...",
    "Robber: You know what? Forget it. DON’T give me anything."};
    public static Chara robber = new Chara("Robber",100,2,10,
    "","The \"robber\" no longer wears a ski mask and now has fresh and bright new clothes.\n> Looks like they turned their life around.",
    "Robber - 2 POW 10 DEF.\n--> Just wants to survive.","Robber - 2 POW 10 DEF.\n--> Just wants to survive.",
    true,0,1,10,10,100,robbf,robbd);
    
    public static Chara[] copyableC = {robber,neutrist,haywardChar,alyssaChar,officer,undergrad,lawyer,representative};
    
    public Chara(){}
    
    //Generic NPC constructor (possibly mini-boss too)
    public Chara(String name, int hp, int pow, int def, String desc, String pDesc, String check, String dCheck, 
    boolean canLeave, int wantSpare, int spareValue, int xp, int dxp, int money, 
    String[] battleFlavor, String[] battleDialogue){
        this.name = name;
        this.hp = hp;
        this.pow = pow;
        this.def = def;
        this.desc = desc;
        this.pDesc = pDesc;
        this.check = check;
        this.dCheck = dCheck;
        this.canLeave = canLeave;
        this.wantSpare = wantSpare;
        this.spareValue = spareValue;
        this.xp = xp;
        this.dxp = dxp;
        this.money = money;
        this.battleFlavor = battleFlavor;
        this.battleDialogue = battleDialogue;
    }
    
    //Boss character constructor (make boss class if there's time)
    public Chara(String name, int hp, int pow, int def, String desc, String pDesc, String check, String dCheck, 
    boolean canLeave, int xp, int dxp, int money, String[] battleFlavor, String[] battleFlavorD, 
    String[] battleDialogue, String[] battleDialogueD, int bossArea, boolean friends){
        this.name = name;
        this.hp = hp;
        this.pow = pow;
        this.def = def;
        this.desc = desc;
        this.pDesc = pDesc;
        this.check = check;
        this.dCheck = dCheck;
        this.canLeave = canLeave;
        this.xp = xp;
        this.dxp = dxp;
        this.money = money;
        this.battleFlavor = battleFlavor;
        this.battleFlavorD = battleFlavorD;
        this.battleDialogue = battleDialogue;
        this.battleDialogueD = battleDialogueD;
        this.bossArea = bossArea;
        this.friends = friends;
    }
    
    //Mini boss...?
    public Chara(String name, int hp, int pow, int def, String desc, String pDesc, String check, String dCheck, 
    boolean canLeave, int xp, int dxp, int money, String[] battleFlavor, String[] battleFlavorD, 
    String[] battleDialogue, String[] battleDialogueD, int bossArea){
        this.name = name;
        this.hp = hp;
        this.pow = pow;
        this.def = def;
        this.desc = desc;
        this.pDesc = pDesc;
        this.check = check;
        this.dCheck = dCheck;
        this.canLeave = canLeave;
        this.xp = xp;
        this.dxp = dxp;
        this.money = money;
        this.battleFlavor = battleFlavor;
        this.battleFlavorD = battleFlavorD;
        this.battleDialogue = battleDialogue;
        this.battleDialogueD = battleDialogueD;
        this.bossArea = bossArea;
    }
    
    //NFC
    public Chara(String name,String desc,String pDesc){
        this.name = name;
        this.desc = desc;
        this.pDesc = pDesc;
    }
    
    public Chara(String name,String desc,String pDesc,String[] chatDialogue){
        this.name = name;
        this.desc = desc;
        this.pDesc = pDesc;
        this.chatDialogue = chatDialogue;
    }

    public String getName(){
        return this.name;
    }
    
    public int getHP(){
        return this.hp;
    }
    
    public int getPOW(){
        return this.pow;
    }
    
    public int getDEF(){
        return this.def;
    }
    
    public String[] getBattleFlavor(){
        return this.battleFlavor;
    }
    
    public String[] getBattleFlavorD(){
        return this.battleFlavorD;
    }
    
    public String[] getBattleDialogue(){
        return this.battleDialogue;
    }
    
    public String[] getBattleDialogueD(){
        return this.battleDialogueD;
    }
    
    public boolean getCanLeave(){
        return this.canLeave;
    }
    
    public boolean getIsFought(){
        return this.isFought;
    }
    
    public boolean getIsKilled(){
        return this.isKilled;
    }
    
    
    public String[] getChatDialogue(){
        return this.chatDialogue;
    }
    
    public int getXP(){
        if (!Commands.isDestruction){
            return this.xp;
        }
        return this.dxp;
    }
    
    public int getMoney(){
        return this.money;
    }
    
    public void setHP(int hp){
        this.hp = hp;
    }
    
    public void setPOW(int pow){
        this.pow = pow;
    }
    
    public void setDEF(int def){
        this.def = def;
    }
    
    public void setIsFought(){
        this.isFought = true;
    }
    
    public void setIsFought2(boolean a){
        this.isFought = a;
    }
    
    public void setIsKilled(){
        this.isKilled = true;
    }
    
    public static String amaraEncounter(){
        String[] amaraDia = {"Amara: Audrey, do you know where...","\nAmara: ...","\nAmara: Oh.","\nAmara: It’s one of you.",
        "\nAmara: What are you trying to do here? Take me down? Fight in the PWO’s streets? Destroy the PWO?",
        "\nAmara: ...","\nAmara: You know, Alyssa was right.","\nAmara: Men, those who support men, they...","\nAmara: ...",
        "\nAmara: Get out of my way.","\n> Amara shoves you out of the way. As she walks, she turns around.",
        "\nAmara: And don’t even THINK of hurting anyone here. I will hunt you down if you do, and I won’t stop until you’re dead.",
        "\n> Amara walks away."};
        String enter;
        System.out.println("(Press \"Enter\")");
        for (String ama : amaraDia){
            enter = Commands.s.nextLine();
            System.out.println(ama);
        }
        Commands.map[4][1].setFVUB(60);
        Commands.map[3][0].setFVUB(80);
        enter = "";
        return "";
    }
    
    public static String amaraPreFight(){
        String[] amaraDia = {"Amara: Hey! Where do you think you’re going?","Amara: I never said I was done with you.","Amara: ...",
        "Amara: Why are you trying to climb to the police?","Amara: Are you gonna report me or something?","Amara: ...",
        "Amara: Look, you may not have hurt anyone, but...","Amara: I don’t trust anything you’re doing.",
        "Amara: Especially climbing barriers that are SUPPOSED to block people off? Even I’d never do that, and I fought a lot of people on the streets behind me.",
        "Amara: ...","Amara: Well, if you’re truly as innocent as you present yourself, prove it.","Amara: Prove that you’re never going to hurt a single person even if they hurt you.",
        "Amara: I was right.","Amara: You’re the type of person who would hurt people.","Amara: ...","Amara: Remember what I told you... if you hurt anyone?",
        "Amara: Yeah, you’re DEAD to me.","Amara: Look, I haven’t seen Audrey since this morning.","Amara: I don’t know where she went or what happened to her, but I know you had something to do with it.",
        "Amara: WHAT DID YOU DO TO HER? WHERE IS SHE?","Amara: ...","Amara: You’re gonna die for what you did to her."};
        String enter;
        System.out.println("(Press \"Enter\")");
        for (int i = 0; i < 6; i++){
            enter = Commands.s.nextLine();
            System.out.println(amaraDia[i]);
        }
        if (Commands.kills[0] > 0){
            if (audrey.getIsKilled()){
                for (int j = amaraDia.length-5; j < amaraDia.length; j++){
                    enter = Commands.s.nextLine();
                    System.out.println(amaraDia[j]);
                }
                return "";
            }
            for (int j = amaraDia.length-10; j < amaraDia.length-5; j++){
                enter = Commands.s.nextLine();
                System.out.println(amaraDia[j]);
            }
            return "";
        }
        for (int j = 6; j < 12; j++){
            enter = Commands.s.nextLine();
            System.out.println(amaraDia[j]);
        }
        return "";
    }
    
    public static String kaitlynNoDestruction(){
        String[] kaitlynBerate = {"Kaitlyn: Oh no you don't!","> Variable \"done\" has been set to true.",
        "Kaitlyn: This game isn't even the full version and you're already PLANNING on KILLING EVERYONE???",
        "Kaitlyn: I can't believe this.","Kaitlyn: ...","Kaitlyn: I'm resetting this world.","Kaitlyn: In the meantime...",
        "Kaitlyn: Never play this game again."};
        String enter;
        System.out.println("> Press \"Enter\" Idiot.");
        for (String k : kaitlynBerate){
            enter = Commands.s.nextLine();
            System.out.println(k);
        }
        Commands.s.close();
        Commands.done = true;
        return "";
    }
    
    public String bossAttack(){
        int dmg = this.attackNPC();
        this.wantSpare = 0;
        if (this.hp < 0){
            this.hp = 0;
            return "You attacked " + this.name + ". You dealt " + dmg + " damage and the " + this.name + " has 0 health remaining.";
        }
        System.out.println("--> You attacked " + this.name + ". You dealt " + dmg + " damage and the " + this.name + " has " + this.hp + " health remaining.");
        String q = Commands.s.nextLine();
        return "";
    }
    
    public String bossFightComms(String input){
        String bDia = this.battleDialogue[(int)(Math.random()*3)];
        String bn = this.name;
        bDia = bn + ": ...";
        input = input.trim();
        String first = Commands.getFirstWord(input);
        String last = Commands.getRestOfSentence(input);
        if (input.equals("tips"))
            System.out.println(tips());
        else if (input.equals("attack")){
            return this.bossAttack();
        }
        else if (input.equals("check"))
            System.out.println(this.check());
        else if (first.equals("consume"))
            System.out.println(consume(last));
        else if (input.equals("talk"))
            System.out.println("You talked to " + this.name + ", but nothing happened.");
        else if (input.equals("pacify"))
            System.out.println("You tried to reason with " + this.name + ", but nothing happened.");
        else if (input.equals("follow"))
            System.out.println("You tried to follow " + this.name + ", but there's nothing to follow.");
        else if (input.equals("criticize"))
            System.out.println("You criticized " + this.name + ", but nothing happened.");
        else
            return "Unknown fight command. Refer to the \"tips\" method to see the list of fight commands.";
        String q = Commands.s.nextLine();
        return bDia;
    }
    
    public String amaraAttacks(){
        int ch = (int)(Math.random()*2);
        if (ch == 0){
            int pennies = (int)(Math.random()*16)+5;
            int dimes = (int)(Math.random()*11)+5;
            int quarters = (int)(Math.random()*6)+5;
            System.out.println("\n--> Amara throws sharp coins at you!");
            System.out.println("--> How much is " + quarters + " quarters, " + dimes + " dimes, and " + pennies + " pennies worth in dollars (numbers only)?\n");
            System.out.print("\n--> " );
            double sum = (0.25*quarters) + (0.1*dimes) + (0.01*pennies);
            String answer = Double.toString(Math.round(sum*100.0)/100.0);
            String input = Commands.s.nextLine().trim();
            while (input.indexOf(".") != -1 && input.endsWith("0")){
                input = input.substring(0,input.length() - 1);
            }
            if (input.substring(input.length()-1).equals(".")){
                input = input.substring(0,input.length() - 1);
            }
            return this.attackMessage(input.equals(answer));
        }
        else {
            int n = (int)(Math.random()*16)+5;
            System.out.println("--> Amara is using a table as a weapon!");
            System.out.println("--> Type \"JuSTiCe FOR AlYsSa!\" " + n + " times (all uppercase and with proper spacing)!\n");
            String answer = "";
            for (int i = 1; i <= n; i++){
                answer += "JuSTiCe FOR AlYsSa! ";
            }
            answer = answer.trim();
            System.out.print("\n--> ");
            String input = Commands.s.nextLine().trim();
            return this.attackMessage(input.equals(answer));
        }
    }
    
    public static String amaraFight(){
        System.out.println(amaraPreFight());
        String enter = Commands.s.nextLine();
        amara.setIsFought();
        Commands.bossFought[0] = true;
        String i;
        String outp = "";
        String[] attDialogue = {"Amara: I knew it. You were just waiting to attack me at the right moment.","Amara: I actually wanted to trust you, but you never wanted that, didn’t you?",
        "Amara: ...","Amara: You deserve whatever comes your way."};
        String[] deathDialogue = {"Amara: Wow...","Amara: You are stronger than I thought you were...","Amara: ...",
        "Amara: All of the PWO’s progress... down the drain...","Amara: ...","Amara: As for you...","Amara: Go to hell."};
        String[] betrayedDeath = {"Amara: What...","Amara: Oh... you...","Amara: You...","Amara: I... hate you...","Amara: I hope... you... die..."};
        int bfc = 0;
        int count = 0;
        int attDia = 0;
        int maxHP = amara.getHP();
        int maxDEF = amara.getDEF();
        System.out.println("--> Amara prevents you from leaving!\n");
        while (true){
            if (bfc > 0){
                if (count < 14){
                    System.out.println("\n--> " + amara.getBattleFlavor()[(int)(Math.random()*3)] + "\n");
                }
                else {
                    System.out.println("\n--> " + amara.getBattleFlavor()[amara.getBattleFlavor().length-1] + "\n");
                }
            }
            if (Commands.kills[0] > 0 || attDia > 0){
                count = 1;
            }
            if (count >= 10){
                amara.setDEF(-500);
            }
            System.out.print("\n--> ");
            i = Commands.s.nextLine().toLowerCase();
            if (i.equals("leave")){
                System.out.println("--> You can't just leave this important battle!");
                String q = Commands.s.nextLine();
            }
            else if (i.equals("spare")){
                if (count == 14){
                    i = "";
                    System.out.println("--> ...");
                    enter = Commands.s.nextLine();
                    System.out.println("\n--> " + amara.getBattleDialogue()[count]);
                    enter = Commands.s.nextLine();
                    System.out.println("\n--> You spared Amara.\n--> The battle ended.");
                    return amaraPostFight();
                }
                else {
                    System.out.println("--> ...");
                    String q = Commands.s.nextLine();
                    System.out.println("\n--> " + amara.getBattleDialogue()[count]);
                }
                count++;
            }
            else {
                outp = amara.bossFightComms(i);
                System.out.println("\n--> " + outp + "\n");
            }
            if (i.equals("attack") && attDia == 0 && Commands.kills[0] == 0 && amara.getHP() != 0){
                for (String at : attDialogue){
                    enter = Commands.s.nextLine();
                    System.out.println("--> " + at);
                }
                attDia++;
                count++;
            }
            if (amara.getHP() <= 0){
                amara.setIsKilled();
                Commands.kills[0]++;
                int xpr;
                Commands.pXp += amara.getXP();
                Commands.pMoney += amara.getMoney();
                amara.setDEF(maxDEF);
                amara.setHP(maxHP);
                i = "";
                if (count >= 10){
                    for (String bd : betrayedDeath){
                        enter = Commands.s.nextLine();
                        System.out.println("--> " + bd);
                    }
                } else {
                    for (String dd : deathDialogue){
                        enter = Commands.s.nextLine();
                        System.out.println("--> " + dd);
                    }
                }
                enter = Commands.s.nextLine();
                System.out.println("\n--> You killed Amara.\n--> You won! You got " + amara.getXP() + " XP and $" + amara.getMoney() + ".\n");
                return pLevelUp();
            }
            i = Commands.s.nextLine();
            if (count < 10){
                System.out.println("--> " + amara.amaraAttacks() + "\n");
            }
            if (Commands.pHp <= 0){
                return amara.pDeath(maxHP,maxDEF);
            }
            bfc++;
        }
    }
    
    public static String amaraPostFight(){
        String[] amaraPost = {"Amara: You know, you’re actually one of the kindest people I’ve met on the streets in a long time.",
        "Amara: Most people either wanna kill me or worse.","Amara: But you... I guess you’re not so bad after all.",
        "Amara: You’re not one of them, are you?","Amara: You just wanna get by and avoid the fighting. That’s respectable.",
        "Amara: ...","Amara: Oh, I almost forgot to introduce myself.","Amara: I’m Amara, president of the Prudence Women’s Organization.",
        "Amara: That building behind me, that’s our headquarters.","Amara: If you ever wanna hang out or stop by for lunch, I’ll be available tomorrow! Feel free to come!",
        "Amara: ...","Amara: To be completely honest, I have no idea what you’re gonna do at the police, but...",
        "Amara: You do whatever you want. I won’t stop you.","Amara: Just... be careful with the sheriff. He’s kind of a jerk.",
        "Amara: ...","Amara: Well, it’s been nice meeting you!","Amara: Stop by the organization tomorrow if you want. I’ll be waiting.",
        "Amara: See ya!"};
        String enter;
        System.out.println("\n(Press \"Enter\")");
        for (String ap : amaraPost){
            enter = Commands.s.nextLine();
            System.out.println(ap);
        }
        return "";
    }
    
    public static String sheriffEncounter1(){
        String[] shrfe1 = {"Sheriff: Hey!","Sheriff: No trespassing on private property, kid.","Sheriff: ...",
        "Sheriff: Y’see I can arrest you right now, but...","Sheriff: I’ll let you off with a warning.","Sheriff: Don’t make me regret it."};
        String enter;
        System.out.println("(Press \"Enter\")");
        for (String sher1 : shrfe1){
            enter = Commands.s.nextLine();
            System.out.println(sher1);
        }
        return "";
    }
    
    public static String stewartPreFight(){
        String[] stewartPre = {"Officer: Halt!","Sergeant: Stop right there! Hands where I can see them!",
        "Sergeat: ...","Sergeant: Stop!"};
        String enter;
        System.out.println("(Press \"Enter\")");
        for (String stp : stewartPre){
            enter = Commands.s.nextLine();
            System.out.println(stp);
        }
        return "";
    }
    
    public String stewartAttack(){
        int r = (int)(Math.random()*6)+5;
        int c = (int)(Math.random()*11)+5;
        String[][] target = new String[r][c];
        int randR = (int)(Math.random()*target.length);
        int randC = (int)(Math.random()*target[0].length);
        target[randR][randC] = "l";
        for (int i = 0; i < r; i++){
            for (int j = 0; j < c; j++){
                if (target[i][j] == null){
                    target[i][j] = "I";
                }
            }
        }
        System.out.println("--> Sergeant Stewart carefully aims his weapon at you!");
        for (int k = 0; k < r; k++){
            for (int l = 0; l < c; l++){
                System.out.print(target[k][l]);
            }
            System.out.println();
        }
        System.out.println("--> Where is \"l\" located ([row,column] answer format)?\n");
        String answer = (randR+1) + "," + (randC+1);
        System.out.print("\n--> ");
        String input = Commands.s.nextLine().trim();
        return stewart.attackMessage(input.equals(answer));
    }
    
    public static String stewartFight(){
        System.out.println(stewartPreFight());
        String enter = Commands.s.nextLine();
        stewart.setIsFought();
        String i;
        String outp = "";
        String[] deathDialogue = {"Sergeant: ...","Sergeant: Damn...","Sergeant: Even I... can’t protect this town...",
        "Sergeant: ...","Sergeant: If you have even the slightest heart left in you...","Sergeant: Tell the others I’m sorry I failed them."};
        int count = 0;
        int bfc = 0;
        int maxHP = stewart.getHP();
        int maxDEF = stewart.getDEF();
        System.out.println("--> Sergeant Stewart blocks your way!\n");
        while (true){
            if (bfc > 0){
                System.out.println("\n--> " + stewart.getBattleFlavor()[(int)(Math.random()*2)] + "\n");
            }
            System.out.print("\n--> ");
            i = Commands.s.nextLine().toLowerCase();
            if (i.equals("leave")){
                System.out.println("--> You can't just leave this important battle!");
                String q = Commands.s.nextLine();
            }
            else if (i.equals("spare")){
                if (count == 7){
                    i = "";
                    System.out.println("--> ...");
                    for (String ssend: stewart.getBattleDialogue()){
                        enter = Commands.s.nextLine();
                        System.out.println("--> " + ssend);
                    }
                    enter = Commands.s.nextLine();
                    System.out.println("\n--> You spared Sergeant Stewart.\n--> The battle ended.");
                    return stewartPostFight();
                }
                else {
                    System.out.println("--> ...");
                    String q = Commands.s.nextLine();
                    System.out.println("\n--> " + "Sergeant Stewart: ...");
                }
                count++;
            }
            else {
                outp = stewart.bossFightComms(i);
                System.out.println("\n--> " + outp + "\n");
            }
            if (stewart.getHP() <= 0){
                stewart.setIsKilled();
                Commands.kills[1]++;
                int xpr;
                Commands.pXp += stewart.getXP();
                Commands.pMoney += stewart.getMoney();
                i = "";
                for (String dd : deathDialogue){
                    enter = Commands.s.nextLine();
                    System.out.println("--> " + dd);
                }
                enter = Commands.s.nextLine();
                System.out.println("\n--> You killed Sergeant Stewart.\n--> You won! You got " + stewart.getXP() + " XP and $" + stewart.getMoney() + ".\n");
                return pLevelUp();
            }
            i = Commands.s.nextLine();
            if (count < 7){
                System.out.println("\n--> " + stewart.stewartAttack() + "\n");
            }
            if (Commands.pHp <= 0){
                return stewart.pDeath(maxHP,maxDEF);
            }
            bfc++;
        }
    }
    
    public static String stewartPostFight(){
        String[] stewartPost = {"Sergeant: I have more important things to do than fight you, bud.",
        "Sergeant: Damn... I’m such an idiot for attacking an innocent person like you.","Sergeant: ...",
        "Sergeant: You can be on your way now. I’ll be headed to the station anyway.","Sergeant: Take care, bud. Be safe out there."};
        String enter;
        System.out.println("\n(Press \"Enter\")");
        for (String stp : stewartPost){
            enter = Commands.s.nextLine();
            System.out.println(stp);
        }
        return "";
    }
    
    public static String sheriffEncounter2(){
        String[] shrfe2 = {"Sheriff: ...","Sheriff: Ah, it’s just you again.","Sheriff: What are you doing here anyway? Don’t you have anywhere else to be?",
        "Sheriff: ...wait...","Sheriff: You’re not from this town, aren’t you?","Sheriff: Why did you go here? The Mayor closed off the town ever since the war started.",
        "Sheriff: You’re not safe here, kid. You better leave soon. The fighting will continue any moment now."};
        String enter;
        System.out.println("(Press \"Enter\")");
        for (String sher2 : shrfe2){
            enter = Commands.s.nextLine();
            System.out.println(sher2);
        }
        return "";
    }
    
    public static String conradPreFight(){
        String [] shpre = {"Sheriff: Hey!","Sheriff: What the hell are you doing with a stick of dynamite and lighter? Are you trying to blow up this place? Have you lost your mind?",
        "> The Sheriff draws his taser.","Sheriff: Put the bomb down! Put it down!","Sheriff: ...",
        "Sheriff: Put your weapon down!","Sheriff: You are under arrest for the murder of Sergeant John Stewart.","Sheriff: You are under arrest for murder."};
        String enter;
        System.out.println("(Press \"Enter\")");
        enter = Commands.s.nextLine();
        System.out.println(shpre[0]);
        if (Commands.kills[1] == 0){
            for (int i = 1; i < 5; i++){
                enter = Commands.s.nextLine();
                System.out.println(shpre[i]);
            }
        }
        else if (Commands.kills[1] == 1 && stewart.getIsKilled()){
            enter = Commands.s.nextLine();
            System.out.println(shpre[2]);
            for (int i = 5; i < 7; i++){
                enter = Commands.s.nextLine();
                System.out.println(shpre[i]);
            }
        }
        else {
            enter = Commands.s.nextLine();
            System.out.println(shpre[2]);
            enter = Commands.s.nextLine();
            System.out.println(shpre[5]);
            enter = Commands.s.nextLine();
            System.out.println(shpre[7]);
        }
        return "";
    }
    
    public static String conradAttacks(){
        int choix = (int)(Math.random()*2);
        if (choix == 0){
            int r = (int)(Math.random()*6)+5;
            int c = (int)(Math.random()*11)+10;
            String[][] target = new String[r][c];
            int randR = (int)(Math.random()*target.length);
            int randC = (int)(Math.random()*target[0].length);
            target[randR][randC] = "|";
            for (int i = 0; i < r; i++){
                for (int j = 0; j < c; j++){
                    if (target[i][j] == null){
                        target[i][j] = "l";
                    }
                }
            }
            System.out.println("--> Sheriff Conrad carefully aims his taser at you and fires with full precision!");
            for (int k = 0; k < r; k++){
                for (int l = 0; l < c; l++){
                    System.out.print(target[k][l]);
                }
                System.out.println();
            }
            System.out.println("--> Where is \"|\" located ([row,column] answer format, a \"[]\" is a single element)?\n");
            String answer = (randR+1) + "," + (randC+1);
            System.out.print("\n--> ");
            String input = Commands.s.nextLine().trim();
            return conrad.attackMessage(input.equals(answer));
        }
        else {
            String att = "";
            int ans = 0;
            int len = (int)(Math.random()*21)+35;
            while (att.length() < len){
                int ch = (int)(Math.random()*8);
                if (ch == 0){
                    att += "i";
                    ans++;
                } else {
                    att += "l";
                }
            }
            System.out.println("--> Sheriff Conrad carefully aims his taser at you and fires with full precision!");
            System.out.println(att);
            System.out.println("How many i's are there?\n");
            String answer = Integer.toString(ans);
            System.out.print("\n--> ");
            String input = Commands.s.nextLine().trim();
            return conrad.attackMessage(input.equals(answer));
        }
    }
    
    public static String conradFight(){
        System.out.println(conradPreFight());
        String enter = Commands.s.nextLine();
        conrad.setIsFought();
        Commands.bossFought[1] = true;
        String i;
        String outp = "";
        String[] deathDialogue = {"Sheriff: ...","Sheriff: ...","Sheriff: You don’t even have the right weapons and I still lost...",
        "Sheriff: ...","Sheriff: Sweetheart...","Sheriff: I’m coming home."};
        int bfc = 0;
        int count = 0;
        int attDia = 0;
        int maxHP = conrad.getHP();
        int maxDEF = conrad.getDEF();
        System.out.println("--> Sheriff Conrad gives his final warning!\n");
        while (true){
            if (bfc > 0){
                System.out.println("\n--> " + conrad.getBattleFlavor()[(int)(Math.random()*3)] + "\n");
            }
            System.out.print("\n--> ");
            i = Commands.s.nextLine().toLowerCase();
            if (i.equals("leave")){
                System.out.println("--> You can't just leave this important battle!");
                String q = Commands.s.nextLine();
            }
            else if (i.equals("spare")){
                if (count == 17){
                    i = "";
                    System.out.println("--> ...");
                    for (int sc = 17; sc < conrad.getBattleDialogue().length-2; sc++){
                        enter = Commands.s.nextLine();
                        System.out.println("--> " + conrad.getBattleDialogue()[sc]);
                    }
                    enter = Commands.s.nextLine();
                    System.out.println("\n--> You spared Sheriff Conrad.\n--> The battle ended.");
                    return conradPostFight();
                }
                else {
                    System.out.println("--> ...");
                    String q = Commands.s.nextLine();
                    if (Commands.kills[1] > 0 && count >= 10){
                        if (count > 11){
                            count = 11;
                        }
                        System.out.println("\n--> " + conrad.getBattleDialogue()[conrad.getBattleDialogue().length-12+count]);
                    } 
                    else {
                        System.out.println("\n--> " + conrad.getBattleDialogue()[count]);
                    }
                }
                count++;
            }
            else {
                outp = conrad.bossFightComms(i);
                System.out.println("\n--> " + outp + "\n");
            }
            if (conrad.getHP() <= 0){
                conrad.setIsKilled();
                Commands.kills[0]++;
                int xpr;
                Commands.pXp += conrad.getXP();
                Commands.pMoney += conrad.getMoney();
                conrad.setDEF(maxDEF);
                conrad.setHP(maxHP);
                i = "";
                for (String dd : deathDialogue){
                    enter = Commands.s.nextLine();
                    System.out.println("--> " + dd);
                }
                enter = Commands.s.nextLine();
                System.out.println("\n--> You killed Sheriff Conrad.\n--> You won! You got " + conrad.getXP() + " XP and $" + conrad.getMoney() + ".\n");
                return pLevelUp();
            }
            i = Commands.s.nextLine();
            if (count < 14){
                System.out.println("--> " + conrad.conradAttacks() + "\n");
            }
            if (Commands.pHp <= 0){
                return conrad.pDeath(maxHP,maxDEF);
            }
            bfc++;
        }
    }
    
    public static String conradPostFight(){
        String[] conradPost = {"Sheriff: Sorry, kid, for trying to take you away.","Sheriff: So many problems these days I don’t even know who’s innocent and who isn’t anymore.",
        "Sheriff: ...","Sheriff: I’ll let you be for now.","Sheriff: I appreciate you being the most peaceful person I’ve met in times like these.",
        "Sheriff: ...","Sheriff: Most people call me Sheriff, but you can just call me Conrad if you’d like.","Sheriff: ...","Sheriff: Well, if you need anythin’ from me, anythin’ at all, just stop by my office at the station.",
        "Sheriff: But not now, though. I still have something I’m workin’ on.","Sheriff: I’ll be available tomorrow for you, kid.","Sheriff: ...","Sheriff: Oh, and one more thing...",
        "Sheriff: I’m pretty sure the only other place that ain’t blocked off is Peony Park and the Peony Apartments.","Sheriff: Usually no one fights there ‘cause of Kaitlyn, but be careful outside of the park. You might catch some strays along those roads.",
        "Sheriff: Well, that’s all I got for you, kid.","Sheriff: Be safe out there."};
        String enter;
        System.out.println("\n(Press \"Enter\")");
        for (String shp : conradPost){
            enter = Commands.s.nextLine();
            System.out.println(shp);
        }
        return "";
    }
    
    public static String haywardEncounter1(){
        String[] he1 = {"Unknown Person: ...","Unknown Person: ...","Unknown Person: ...","Unknown Person: ...","Unknown Person: ...",
        "Unknown Person: Nah, there’s more of the town still."};
        String enter;
        System.out.println("(Press \"Enter\")");
        for (String hayw : he1){
            enter = Commands.s.nextLine();
            System.out.println(hayw);
        }
        return "";
    }
    
    public static String alyKatEncounter1(){
        String[] ake1 = {"Kaitlyn: momma look!","> Alyssa and Kaitlyn approach you.","Kaitlyn: momma, it’s a new friend!",
        "Alyssa: Hi there! Yeah Kaitlyn’s right, I don’t think we ever saw you anywhere in the town.","Alyssa: I’m Alyssa, what’s your name?",
        "> You tell Alyssa your name","Alyssa: Well, welcome to the town, " + Commands.playerName + "! This is Kaitlyn, my daughter... well, actually, my \"AI daughter.\"",
        "Kaitlyn: yeah hi! i’m kaitlyn! welcome to the park!","Alyssa: Hey, um, if you don’t mind me asking, did you ever... get in trouble with anyone here?",
        "Alyssa: ...","Alyssa: Aw, I’m sorry to hear that! Don’t worry, you’re safe here in the park, " + Commands.playerName + ". No one will cause trouble here and no one has in a long time. You’ll be alright!",
        "Kaitlyn: yeah! in the park we just play and walk all day and look at flowers and imagine the clouds and... it’s so much fun! everyday fun! the peonies are very sweet and smell good!",
        "Alyssa: Yes, of course it is, honey! Oh, I’ll be right back my baby, I’ll just check on something on my bag.","Kaitlyn: okay momma!","Alyssa: I love you!","Kaitlyn: i love you more momma!",
        "> Alyssa kisses Kaitlyn on her \"forehead\" and leaves.","Kaitlyn: you’re new to this town, right " + Commands.playerName + "? that’s awesome! i wanna give you a tour and help you explore the fun and wonderful places in this town!",
        "Kaitlyn: also i have toys around the town and if you wanna take them you can have them if you want and have tea parties and restaurants and extra fun with all of them!!! :) :0","Alyssa: Kaitlyn honey, I need your help!",
        "Kaitlyn: coming momma!","Kaitlyn: " + Commands.playerName + " if you wanna go to the park and play or chat just go there anytime we’ll always be there!","Kaitlyn: bye bye for now :D"};
        String enter;
        System.out.println("(Press \"Enter\")");
        for (String alykat : ake1){
            enter = Commands.s.nextLine();
            System.out.println(alykat);
        }
        return "";
    }
    
    public static String alyKatEncounter2(){
        String[] ake2 = {"Kaitlyn: hiiiiii " + Commands.playerName + " you came to play!","Alyssa: Hello, " + Commands.playerName + ". Nice to see you again!","Kaitlyn: okay i have a game where we skip rocks at the pond and the farthest skip wins!",
        "Alyssa: We don’t have any rocks baby, and people will get wet anyways.","Kaitlyn: aw :(","Alyssa: How about we just play another game?","Kaitlyn: okay momma... i’m thinking...","Kaitlyn: ...","Kaitlyn: oh i know! but we need to get the thingies from the house first momma!",
        "Alyssa: Alright honey. Here, how about you take the keys first and you go ahead. I’ll be there in a fews minutes.","Kaitlyn: okay!","Alyssa: ...","Alyssa: Isn’t she the sweetest thing ever?","Alyssa: ...","Alyssa: " + Commands.playerName + ", you probably heard from the news what happened, right?",
        "Alyssa: Yeah, I thought so.","Alyssa: ...","Alyssa: Our relationship matters were supposed to be between us only, but the town just made it into an all-out war ‘cause they can’t help but take sides...","Alyssa: Look, I’m not saying that I’m in the right and... Hayward... is in the wrong, but I don’t want a stupid war all because of that!",
        "Alyssa: ...","Alyssa: I should probably get going.","Alyssa: If you wanna hang out with me and Kaitlyn, just go to our room in Peony Apartments, alright? The room number is 125.","Alyssa: See ya, " + Commands.playerName + ", and be safe out here!"};
        String enter;
        System.out.println("(Press \"Enter\")");
        for (String alykat : ake2){
            enter = Commands.s.nextLine();
            System.out.println(alykat);
        }
        return "";
    }
    
    public static String alyKatEncounter3(){
        String[] ake3 = {"Kaitlyn: " + Commands.playerName + "!","Alyssa: Ah! What the hell that was loud!","Kaitlyn: momma! that's a bad word!","Alyssa: Sorry honey! Anyways, hi again, " + Commands.playerName + "! Nice seeing you here again!",
        "Kaitlyn: i thought i had my toys, but i didn’t, so now we can’t play :(","Kaitlyn: but it’s okay! we can just talk about stuff!","Kaitlyn: ...","Alyssa: honey! lunch is ready!","Kaitlyn: aww okay momma!","Kaitlyn: we can just chat later at the park or beach, okay " + Commands.playerName + "?",
        "Kaitlyn: okay bye bye see ya i want lunch yayyy!!!","Alyssa: You can stay for lunch if you want, " + Commands.playerName + ". Otherwise, we’ll see you at the park later!"};
        String enter;
        System.out.println("(Press \"Enter\")");
        for (String alykat : ake3){
            enter = Commands.s.nextLine();
            System.out.println(alykat);
        }
        return "";
    }
    
    public static String haywardEncounter2(){
        String[] he2 = {"Unknown Person: ...","Unknown Person: ...do they know I'm here...?","Unknown Person: ...",
        "Unknown Person: ...","Unknown Person: Damn. I'm gonna be late for Kaitlyn's system check again."};
        String enter;
        System.out.println("(Press \"Enter\")");
        for (String alykat : he2){
            enter = Commands.s.nextLine();
            System.out.println(alykat);
        }
        return "";
    }
    
    public static String kaitlynEncounter1(){
        String[] ke1 = {"Kaitlyn: hiii again " + Commands.playerName + "! you love the park? you love playing cards? you love the flowers and streets and fluffy grass and everything?",
        "Kaitlyn: i’m glad you do!!! this place is so much fun and happy right? really very super duper fun! so much fun! :b :D ;)","Kaitlyn: the next place you can explore is the college just over there!",
        "Kaitlyn: college is like a big school which is so much fun because you have bigger friends and bigger brains and bigger fun!","Kaitlyn: before you can’t really do anything cuz the computer thingy stuff is weird and no one gets it, but now you can!",
        "> You and Kaitlyn go to the gates of the Prudence Community College entrance.","> The computer terminal for the locked gates is now in a readable language... or is it?","> You both went back to the Peony Park’s west wing.",
        "Kaitlyn: okieee! now you have a computer to play with and you can solve the pu-z-zles! yay!","Kaitlyn: if you need any more help with computer stuff just go to the park and i’ll help! i’m a robot anyway so i can hack into the computer and no problems anymore!",
        "Kaitlyn: bye bye! i hope i see you again, " + Commands.playerName + "! :)"};
        String enter;
        System.out.println("(Press \"Enter\")");
        for (String alykat : ke1){
            enter = Commands.s.nextLine();
            System.out.println(alykat);
        }
        return "";
    }
    
    public static String kaitlynEncounter2(){
        String[] nokills = {"Kaitlyn: twinkle twinkle little star","Kaitlyn: how i wonder what you are","Kaitlyn up above the... oh hiiiii " + Commands.playerName + "!!!"};
        String[] bosskills = {"Kaitlyn: Humpty Dumpty sat on a wall","Kaitlyn: Humpty Dumpty had a great fall","Kaitlyn: All the... oh hii " + Commands.playerName + "!"};
        String[] killed = {"Kaitlyn: The Itsy Bitsy Spider went up the water spout","Kaitlyn: Down came the rain and washed the spider out","Kaitlyn: Out... oh hiiii " + Commands.playerName + "!!"};
        if (Commands.kills[0] == 0 && Commands.kills[1] == 0){
            String enter;
            System.out.println("(Press \"Enter\")");
            for (String alykat : nokills){
                enter = Commands.s.nextLine();
                System.out.println(alykat);
            }
        }
        else if (amara.getIsKilled() || stewart.getIsKilled() || conrad.getIsKilled()){
            String enter;
            System.out.println("(Press \"Enter\")");
            for (String alykat : bosskills){
                enter = Commands.s.nextLine();
                System.out.println(alykat);
            }
        }
        else {
            String enter;
            System.out.println("(Press \"Enter\")");
            for (String alykat : killed){
                enter = Commands.s.nextLine();
                System.out.println(alykat);
            }
        }
        String[] ending = {"Kaitlyn: Wait... how did you get here so soon?","Kaitlyn: ...","Kaitlyn: Last time I checked someone left a spare key in the Town Center, NOT PCC Drive.",
        "Kaitlyn: ...","Kaitlyn: Hmmm...","Kaitlyn: Well, " + Commands.playerName + ", I got news for you...","Kaitlyn: The code of this world... at least this version of my world... it's incomplete.",
        "Kaitlyn: Not to mention that I see many incomplete methods here too.","Kaitlyn: So how about this?","Kaitlyn: I'll send you back to the beginning of the game...",
        "Kaitlyn: And while you're there, how about you experiment a bit?","Kaitlyn: And I don't mean killing of course.","Kaitlyn: You may think this is all just a game, but these are my people, and I care about all of them.",
        "Kaitlyn: What I mean by \"experiment\" is you should try looking at different areas and testing different items.","Kaitlyn: After all, this world is unfinished, so you might as well do those so you're not bored.",
        "Kaitlyn: Does that sound good?","Kaitlyn: ...","Kaitlyn: You know, it's funny that I'm asking you that when your input isn't even stored anywhere for this part.","Kaitlyn: ...",
        "Kaitlyn: But yeah...","Kaitlyn: I bet once this game is done, " + Commands.playerName + ", you, this town, my family... we'll finally get the good ending.","Kaitlyn: So yeah... hopefully it comes to that...",
        "Kaitlyn: ...","Kaitlyn: Welp, that's all for now, " + Commands.playerName + ".","Kaitlyn: See ya! :)"};
        String enter;
        for (String end : ending){
            enter = Commands.s.nextLine();
            System.out.println(end);
        }
        Commands.s.close();
        Commands.done = true;
        return "";
    }
    
    //HAYWARD FIGHT MAY NOT BE IMPLEMENTED
    //DESTRUCTION ROUTE STUFF MAY NOT BE IMPLEMENTED
    
    //COMPLETE???
    public static String tips(){
       return "Here are the available battle options:\n"
                + "Command             Example                    Description\n"
                + "Attack              attack (robber)            Attacks the enemy in battle\n"
                + "Check               check (robber)             Checks the enemy's stats\n"
                + "Consume {item}      consume hotdog             Consumes a healing item in battle\n"
                + "Spare               spare (robber)             Spares the enemy in battle\n"
                + "Leave               leave                      Leaves the battle, if possible\n"
                + "Talk                talk (robber)              Talks to the enemy in battle\n"
                + "Pacify              pacify (robber)            Attempts to reduce the enemy's POW in battle\n"
                + "Follow              follow (robber)            Follows the enemy's demands in battle\n"
                + "Criticize           criticize (robber)         Criticizes the enemy in battle\n"
                + "Tips                tips                       Provides important battle information";
    }
    
    public static String pLevelUp(){
        int temp = Commands.pLvl;
        for (int i = 1; i < 16; i++){
            int minXp = (int)(((Math.pow(4.5,i))/3.5));
            if (Commands.pXp >= minXp){
                Commands.pLvl = i;
            } else {
                break;
            }
        }
        if (Commands.pLvl > temp){
            int p = Commands.pPow + (3*(Commands.pLvl-1));
            if (Commands.pLvl >= 5 && Commands.isDestruction){
                p = Commands.pPow + (int)(3*Math.exp(Commands.pLvl - 2));
            }
            Commands.pPow = p;
            Commands.pDef += (3*(Commands.pLvl-1));
            Commands.maxpHp += (10*(Commands.pLvl-1));
            if (!Commands.isDestruction){
                return "--> You leveled up!";
            }
            return "--> You leveled up. Keep going.";
        }
        return "";
    }
    
    public int attackP(){
        int dmg = (int)(this.pow/2 + 10);
        int hurt = (int)(1.5*dmg*(1-(2*Commands.pDef/100)));
        if (hurt < 1){
            Commands.pHp--;
            return 1;
        }
        Commands.pHp -= hurt;
        if (Commands.pHp < 0){
            Commands.pHp = 0;
        }
        return hurt;
    }
    
    //PROBABLY DONE (adjust player stats if damage is too low for certain fights)
    public int attackNPC(){
        int dmg = (int)(Commands.pPow + 14);
        int hurt = (int)(dmg*(1-(2*this.def/100)));
        if (hurt < 1){
            this.hp--;
            return 1;
        }
        this.hp -= hurt;
        return hurt;
    }
    
    //PROBABLY DONE
    public String attack(){
        int dmg = this.attackNPC();
        this.wantSpare = 0;
        String bDia = this.battleDialogue[(int)(Math.random()*2)];
        if (this.wantSpare == this.spareValue){
            bDia = this.name + ": ...";
        }
        if (this.hp < 0){
            this.hp = 0;
            return "You attacked the " + this.name + ". You dealt " + dmg + " damage and the " + this.name + " has 0 health remaining.";
        }
        System.out.println("--> You attacked the " + this.name + ". You dealt " + dmg + " damage and the " + this.name + " has " + this.hp + " health remaining.");
        String q = Commands.s.nextLine();
        return bDia;
    }
    
    public String attackMessage(boolean t){
        if (t){
            return "You dodged all of " + this.name + "'s attacks and took no damage!\n";
        }
        else {
            return "You failed to dodge all attacks! You took " + this.attackP() + " damage and have " + Commands.pHp + " health remaining!\n";
        }
    }
    
    public String robAttack(){
        int dimes = (int)(Math.random()*6)+5;
        int quarters = (int)(Math.random()*4)+2;
        System.out.println("--> The Robber launches a heavy coin bag attack!");
        System.out.println("--> How much is " + quarters + " quarters and " + dimes + " dimes worth in dollars (numbers only)?\n");
        System.out.print("\n--> " );
        double sum = (0.25*quarters) + (0.10*dimes);
        String answer = Double.toString(Math.round(sum*100.0)/100.0);
        String input = Commands.s.nextLine().trim();
        while (input.indexOf(".") != -1 && input.endsWith("0")){
            input = input.substring(0,input.length() - 1);
        }
        if (input.substring(input.length()-1).equals(".")){
            input = input.substring(0,input.length() - 1);
        }
        return this.attackMessage(input.equals(answer));
    }
    
    public String neutAttack(){
        int n = (int)(Math.random()*14)+2;
        System.out.println("--> The Neutrist throws rocks at you to scare you away!");
        System.out.println("--> Type \"no\" " + n + " times (all lowercase, no spaces)!\n");
        String answer = "";
        for (int i = 1; i <= n; i++){
            answer += "no";
        }
        System.out.print("\n--> ");
        String input = Commands.s.nextLine().trim();
        return this.attackMessage(input.equals(answer));
    }
    
    public String alyAttack(){
        int n = (int)(Math.random()*9)+2;
        System.out.println("--> The Alyssa Supporter is using her poster as a weapon!");
        System.out.println("--> Type \"JUSTICE FOR ALYSSA!\" " + n + " times (all uppercase and with proper spacing)!\n");
        String answer = "";
        for (int i = 1; i <= n; i++){
            answer += "JUSTICE FOR ALYSSA! ";
        }
        answer = answer.trim();
        System.out.print("\n--> ");
        String input = Commands.s.nextLine().trim();
        return this.attackMessage(input.equals(answer));
    }
    
    public String hayAttack(){
        int n = (int)(Math.random()*9)+2;
        System.out.println("--> The Hayward Supporter is using his poster as a weapon!");
        System.out.println("--> Type \"JUSTICE FOR HAYWARD!\" " + n + " times (all uppercase and with proper spacing)!\n");
        String answer = "";
        for (int i = 1; i <= n; i++){
            answer += "JUSTICE FOR HAYWARD! ";
        }
        answer = answer.trim();
        System.out.print("\n--> ");
        String input = Commands.s.nextLine().trim();
        return this.attackMessage(input.equals(answer));
    }
    
    public String offAttack(){
        int r = (int)(Math.random()*3)+5;
        int c = (int)(Math.random()*6)+5;
        String[][] target = new String[r][c];
        int randR = (int)(Math.random()*target.length);
        int randC = (int)(Math.random()*target[0].length);
        target[randR][randC] = "C";
        for (int i = 0; i < r; i++){
            for (int j = 0; j < c; j++){
                if (target[i][j] == null){
                    target[i][j] = "O";
                }
            }
        }
        System.out.println("--> The Officer carefully aims their weapon at you!");
        for (int k = 0; k < r; k++){
            for (int l = 0; l < c; l++){
                System.out.print(target[k][l]);
            }
            System.out.println();
        }
        System.out.println("--> Where is \"C\" located ([row,column] answer format)?\n");
        String answer = (randR+1) + "," + (randC+1);
        System.out.print("\n--> ");
        String input = Commands.s.nextLine().trim();
        return this.attackMessage(input.equals(answer));
    }
    
    public String undAttack(){
        int rand1 = (int)(Math.random()*651)+100;
        int rand2 = (int)(Math.random()*651)+100;
        System.out.println("--> The Undergraduate takes out their notebook! How threatening!");
        System.out.println("--> Evaluate " + rand1 + " + " + rand2 + ".\n");
        int an = rand1 + rand2;
        String answer = Integer.toString(an);
        System.out.print("\n--> ");
        String input = Commands.s.nextLine().trim();
        return this.attackMessage(input.equals(answer));
    }
    
    public String lawAttack(){
        String[] triv = {"Congress shall make no law respecting an establishment of religion, or prohibiting the free exercise thereof","No Soldier shall, in time of peace be quartered in any house, without the consent of the Owner",
        "No person shall be held to answer for a capital, or otherwise infamous crime, unless on a presentment or indictment of a Grand Jury","are reserved to the States respectively, or to the people",
        "on account of sex","The eighteenth article of amendment"};
        String[] answers = {"1","3","5","10","19","21"};
        int randA = (int)(Math.random()*triv.length);
        System.out.println("--> The Lawyer looks like they're taking out a weapon from their suitcase!");
        System.out.println("--> \"" + triv[randA] + "\"");
        System.out.println("--> Which Constitutional Amendment does this excerpt come from (only type the number)?\n");
        String answer = answers[randA];
        System.out.print("\n--> ");
        String input = Commands.s.nextLine().trim();
        return this.attackMessage(input.equals(answer));
    }
    
    public String repAttack(){
        int r = (int)(Math.random()*3)+5;
        int c = (int)(Math.random()*6)+10;
        String[][] target = new String[r][c];
        String[] TorL = {"T","L"};
        for (int j = 0; j < r; j++){
            for (int k = 0; k < c; k++){
                if ((int)(Math.random()*r*c) <= 16){
                    target[j][k] = "H";
                }
                else {
                    target[j][k] = TorL[(int)(Math.random()*2)];
                }
            }
        }
        int count = 0;
        for (String[] m : target){
            for (String n : m){
                if (n.equals("H")){
                    count++;
                }
            }
        }
        System.out.println("--> The Representative is highly defensive against the unknown!");
        for (String[] o : target){
            for (String p : o){
                System.out.print(p);
            }
            System.out.println();
        }
        System.out.println("--> How many humans are there (humans are represented by \"H\")?\n");
        int an = count;
        String answer = Integer.toString(an);
        System.out.print("\n--> ");
        String input = Commands.s.nextLine().trim();
        return this.attackMessage(input.equals(answer));
    }
    
    public String check(){
        if (!Commands.isDestruction){
            return "--> " + this.check;
        }
        return "--> " + this.dCheck;
    }
    
    //MOST LIKELY DONE JUST POLISH IT LATER IF NEEDED
    public static String consume(String item){
        int index = Commands.pObjIndex(item);
        if (index != -1){
            Item thing = Commands.playerInventory.get(index);
            if (thing instanceof Heal){
                if (Commands.pHp == Commands.maxpHp){
                    return "--> Your health is still maxed out!";
                }
                if (Commands.pHp + thing.getHeal() >= Commands.maxpHp){
                    Commands.playerInventory.remove(index);
                    Commands.pHp = Commands.maxpHp;
                    return "--> You consumed the " + thing.getName() + ". Your health was maxed out.";
                }
                Commands.playerInventory.remove(index);
                Commands.pHp += thing.getHeal();
                return "--> You consumed the " + thing.getName() + ". You gained " + thing.getHeal() + " HP!";
            }
            return "--> You can't heal with this item!";
        }
        return "--> You don't have this item in your inventory." ;
    }
    
    //PROBABLY DONE???
    public String talk(){
        String bDia = this.battleDialogue[(int)(Math.random()*2)];
        String n = this.name;
        if (this.wantSpare == this.spareValue){
            bDia = n + ": ...";
        }
        if (!Commands.isDestruction){
            if (n.equals("Robber")){
                if (this.wantSpare < this.spareValue)
                this.wantSpare++;
                System.out.println("--> You told the Robber you don't want to get in their way. The Robber nods approvingly.");
                String q = Commands.s.nextLine();
                return robbd[2];
            }
            if (n.equals("Hayward Supporter")){
                if (this.wantSpare < this.spareValue)
                this.wantSpare++;
                System.out.println("--> You told the Hayward Supporter you didn't support the Alyssa Faction. The Hayward Supporter approves.");
                String q = Commands.s.nextLine();
                return haybd[2];
            }
            if (n.equals("Alyssa Supporter")){
                if (this.wantSpare < this.spareValue)
                this.wantSpare++;
                System.out.println("--> You told the Alyssa Supporter you didn't support the Hayward Faction. The Alyssa Supporter approves.");
                String q = Commands.s.nextLine();
                return alybd[2];
            }
            if (n.equals("PCC Undergraduate")){
                if (this.wantSpare < this.spareValue)
                this.wantSpare++;
                System.out.println("--> You told the Undergraduate you just want to be on your way. The Undergraduate lowered their defenses.");
                String q = Commands.s.nextLine();
                return undbd[2];
            }
            if (n.equals("Lawyer") || n.equals("Representative")){
                if (this.wantSpare == 1){
                    this.wantSpare++;
                    System.out.println("--> You told the " + n + " you never supported any side. The " + " is more convinced of your pacifism.");
                    String q = Commands.s.nextLine();
                    return this.battleDialogue[3];
                }
                System.out.println("--> You talked to the " + n + ". but they didn't hear you.");
                String q = Commands.s.nextLine();
                return bDia;
            }
            System.out.println("--> You tried talking to the " + n + ", but they didn't hear you.");
            String q = Commands.s.nextLine();
            return bDia;
        }
        System.out.println("--> Not worth talking to.");
        String q = Commands.s.nextLine();
        return bDia;
    }
    
    public String pacify(){
        String bDia = this.battleDialogue[(int)(Math.random()*2)];
        String n = this.name;
        if (this.wantSpare == this.spareValue){
            bDia = n + ": ...";
        }
        if (!Commands.isDestruction){
            if (n.equals("Alyssa Supporter") || n.equals("Hayward Supporter") || n.equals("Robber") || n.equals("PCC Undergraduate")){
                if (this.wantSpare < this.spareValue)
                this.wantSpare++;
                System.out.println("--> You told the " + n + " you don't want to fight. The " + n + " decides to let you go.");
                String q = Commands.s.nextLine();
                return this.battleDialogue[2];
            }
            if (n.equals("Neutrist")){
                if (this.wantSpare == 1){
                    this.wantSpare++;
                    System.out.println("--> You told the Neutrist to stop fighting. The Neutrist agrees.");
                    String q = Commands.s.nextLine();
                    return neutbd[2];
                }
                System.out.println("--> You tried to calm the Neutrist, but nothing happened.");
                String q = Commands.s.nextLine();
                return bDia;
            }
            if (n.equals("Police Officer")){
                if (this.wantSpare == 1){
                    this.wantSpare++;
                    System.out.println("--> You told the Police Officer you come in peace. The Police Officer decides to let you go for now.");
                    String q = Commands.s.nextLine();
                    return offbd[2];
                }
                System.out.println("--> You tried to calm the Police Officer, but nothing happened.");
                String q = Commands.s.nextLine();
                return bDia;
            }
            if (n.equals("Lawyer")){
                if (this.wantSpare == 2){
                    this.wantSpare++;
                    System.out.println("--> You argue to the Lawyer you never wanted to fight and just want to be on your way. The Lawyer is now convinced.");
                    String q = Commands.s.nextLine();
                    return lawbd[2];
                }
                System.out.println("--> You tried to calm the Lawyer, but nothing happened.");
                String q = Commands.s.nextLine();
                return bDia;
            }
            //TODO
            if (n.equals("Representative")){
                if (this.wantSpare == 2){
                    this.wantSpare++;
                    System.out.println("--> You told the Representative you don't want to fight anyone. The Representative approves your stance.");
                    String q = Commands.s.nextLine();
                    return repbd[2];
                }
                System.out.println("--> You tried to calm the Representative, but nothing happened.");
                String q = Commands.s.nextLine();
                return bDia;
            }
        }
        System.out.println("...seriously?");
        String q = Commands.s.nextLine();
        return bDia;
    }
    
    public String follow(){
        String bDia = this.battleDialogue[(int)(Math.random()*2)];
        String n = this.name;
        if (this.wantSpare == this.spareValue){
            bDia = n + ": ...";
        }
        if (!Commands.isDestruction){
            if (n.equals("Alyssa Supporter")){
                if (this.wantSpare < this.spareValue)
                this.wantSpare++;
                System.out.println("--> You shouted \"Justice For Alyssa!\" to the Alyssa Supporter. The Alyssa Supporter repeats your chant.");
                String q = Commands.s.nextLine();
                return alybd[3];
            }
            if (n.equals("Hayward Supporter")){
                if (this.wantSpare < this.spareValue)
                this.wantSpare++;
                System.out.println("--> You shouted \"Justice For Hayward!\" to the Hayward Supporter. The Hayward Supporter repeats your chant.");
                String q = Commands.s.nextLine();
                return haybd[3];
            }
            if (n.equals("PCC Undergraduate")){
                if (this.wantSpare < this.spareValue)
                this.wantSpare++;
                System.out.println("--> You backed off from the Undergraduate and explained that you were just on your way. The Undergraduate lowers their defenses.");
                String q = Commands.s.nextLine();
                return undbd[2];
            }
            if (n.equals("Neutrist")){
                if (this.wantSpare == 0){
                    this.wantSpare++;
                    System.out.println("--> You lowered your defenses and told the Neutrist that you don't wanna fight. The Neutrist is now less suspicious of you.");
                    String q = Commands.s.nextLine();
                    return neutbd[3];
                }
                System.out.println("--> You tried following the Neutrist, but nothing happened.");
                String q = Commands.s.nextLine();
                return bDia;
            }
            if (n.equals("Police Officer")){
                if (this.wantSpare == 0){
                    this.wantSpare++;
                    System.out.println("--> You showed your pockets and put your hands up to the Police Officer. The Police Officer begins to trust you.");
                    String q = Commands.s.nextLine();
                    return offbd[3];
                }
                System.out.println("--> You tried following the Police Officer, but nothing happened.");
                String q = Commands.s.nextLine();
                return bDia;
            }
            if (n.equals("Representative")){
                if (this.wantSpare == 0){
                    this.wantSpare++;
                    System.out.println("--> You took some steps back from the Representative. The Representative calms down a bit.");
                    String q = Commands.s.nextLine();
                    return repbd[4];
                }
                System.out.println("--> You tried following the Representative, but nothing happened.");
                String q = Commands.s.nextLine();
                return bDia;
            }
            if (n.equals("Lawyer")){
                if (this.wantSpare == 0){
                    this.wantSpare++;
                    System.out.println("--> You slowly lowered your fighting stance. The Lawyer becomes less critical of you.");
                    String q = Commands.s.nextLine();
                    return lawbd[4];
                }
                System.out.println("--> You tried following the Lawyer, but nothing happened.");
                String q = Commands.s.nextLine();
                return bDia;
            }
            System.out.println("--> You tried following the " + this.name + ", but there's nothing to follow!");
            String q = Commands.s.nextLine();
            return bDia;
        }
        System.out.println("--> Don't listen to the enemy.");
        String q = Commands.s.nextLine();
        return bDia;
    }
    
    //same for all characters
    public String criticize(){
        if (this.wantSpare >= 0){
            this.wantSpare = 0;
        }
        return "--> You criticized the " + this.name + ". They didn't seem to like that very much.";
    }
    
    public String fightComms(String input){
        String bDia = this.battleDialogue[(int)(Math.random()*2)];
        if (this.wantSpare == this.spareValue){
            bDia = this.name + ": ...";
        }
        input = input.trim();
        String first = Commands.getFirstWord(input);
        String last = Commands.getRestOfSentence(input);
        if (input.equals("tips"))
            System.out.println(tips());
        else if (input.equals("attack"))
            return this.attack();
        else if (input.equals("check"))
            System.out.println(this.check());
        else if (first.equals("consume"))
            System.out.println(consume(last));
        else if (input.equals("talk"))
            return this.talk();
        else if (input.equals("pacify"))
            return this.pacify();
        else if (input.equals("follow"))
            return this.follow();
        else if (input.equals("criticize"))
            System.out.println(this.criticize());
        else
            return "Unknown fight command. Refer to the \"tips\" method to see the list of fight commands.\n";
        String q = Commands.s.nextLine();
        return bDia;
    }
    
    /* Neutrist (follow + pacify only, leavable) 
     * Hayward/Alyssa supporter (talk/follow, leavable) * 
     * Lawyer (follow + talk + pacify only, not leavable)
     * Representative (follow + talk + pacify, not leavable)
     * Robber (talk/pacify, leavable)*
     * Officer (follow + pacify only, not leavable)
     * Undergraduate (follow/talk/pacify, leavable) * 
     */
    public String fight(){
        this.isFought = true;
        if (!Commands.isDestruction){
            System.out.println("--> " + this.name + " attacks!\n");
        }
        else {
            System.out.println("--> " + this.name + " attacks, but feels like something's wrong...\n");
        }
        String i;
        String outp = "";
        int count = 0;
        int maxHp = this.hp;
        int maxDef = this.def;
        while (true){
            if (count > 0){
                if (this.wantSpare == 0){
                    System.out.println("--> " + this.battleFlavor[(int)(Math.random()*2)] + "\n");
                }
                else {
                    System.out.println("--> " + this.battleFlavor[this.battleFlavor.length - this.wantSpare] + "\n");
                }
            }
            if (this.wantSpare == this.spareValue){
                this.def = -500;
            } else {
                this.def = maxDef;
            }
            System.out.print("\n--> ");
            i = Commands.s.nextLine().toLowerCase();
            if (i.equals("leave")){
                if (this.canLeave){
                    i = "";
                    this.hp = maxHp;
                    this.wantSpare = 0;
                    this.def = maxDef;
                    return "--> You left the battle!\n";
                }
                else {
                    System.out.println("--> You can't just leave this battle!");
                    String q = Commands.s.nextLine();
                }
            }
            else if (i.equals("spare")){
                if (!Commands.isDestruction){
                    if (this.wantSpare == this.spareValue){
                    i = "";
                    this.hp = maxHp;
                    this.wantSpare = 0;
                    this.def = maxDef;
                    return "--> You made peace with your opponent.\n--> The battle ended.\n";
                    }
                    else {
                        System.out.println("--> The " + this.name + " still wants to attack you!");
                        String q = Commands.s.nextLine();
                    }
                }
                else {
                    System.out.println("--> Don't spare the enemy.");
                    String q = Commands.s.nextLine();
                }
            }
            else {
                outp = new String(this.fightComms(i));
                System.out.println("\n--> " + outp + "\n");
            }
            if (outp.indexOf("\"tips\"") == -1){
                i = Commands.s.nextLine();
                if (this.hp <= 0){
                    this.isKilled = true;
                    this.hp = maxHp;
                    this.wantSpare = 0;
                    this.def = maxDef;
                    Commands.kills[Commands.currentRoom.getAreaN()-1]++;
                    int xpr;
                    if (!Commands.isDestruction){
                        Commands.pXp += this.xp;
                        xpr = this.xp;
                    }
                    else {
                        Commands.pXp += this.dxp;
                        xpr = this.dxp;
                    }
                    Commands.pMoney += this.money;
                    i = "";
                    System.out.println("--> You killed the " + this.name + ".\n--> You won! You got " + xpr + " XP and $" + this.money + ".\n");
                    return pLevelUp();
                }
                if (this.wantSpare != this.spareValue){
                    if (this.name.equals("Robber"))
                        System.out.println("--> " + this.robAttack() + "\n");
                    if (this.name.equals("Neutrist"))
                        System.out.println("--> " + this.neutAttack() + "\n");
                    if (this.name.equals("Alyssa Supporter"))
                        System.out.println("--> " + this.alyAttack() + "\n");
                    if (this.name.equals("Hayward Supporter"))
                        System.out.println("--> " + this.hayAttack() + "\n");
                    if (this.name.equals("Police Officer"))
                        System.out.println("--> " + this.offAttack() + "\n");
                    if (this.name.equals("PCC Undergraduate"))
                        System.out.println("--> " + this.undAttack() + "\n");
                    if (this.name.equals("Lawyer"))
                        System.out.println("--> " + this.lawAttack() + "\n");
                    if (this.name.equals("Representative"))
                        System.out.println("--> " + this.repAttack() + "\n");
                }
                if (Commands.pHp <= 0){
                    return this.pDeath(maxHp,maxDef);
                }
                count++;
            }
        }
    }
    
    public String pDeath(int maxhp,int maxdef){
        this.hp = maxhp;
        this.def = maxdef;
        Commands.s.nextLine();
        System.out.println("-> You died! Restarting the game...");
        Commands.s.close();
        Commands.done = true;
        return "";
    }
    
    /* Might not implement to save time
    public static String twoFight(Character c1, Character c2){
        System.out.println("-->" + c1.getName() + " attacks! " + c2.getName() + " came too!");
        Scanner inS = new Scanner(System.in);
        
        inS.close();
        return "";
    }
    */
}

    
