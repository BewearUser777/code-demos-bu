import java.util.*;

/**
 * Write a description of class Armor here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Armor extends Item
{
    private int addDEF;
    public Armor(String name, String description, double weight, int addDEF){
        super(name,description,weight);
        this.addDEF = addDEF;
    }
    
    public int getAddDEF(){
        return this.addDEF;
    }
}

