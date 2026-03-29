import java.util.*;

/**
 * Write a description of class Weapon here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Weapon extends Item
{
    private int addPOW;
    public Weapon(String name, String description, double weight, int addPOW){
        super(name,description,weight);
        this.addPOW = addPOW;
    }
    
    public int getAddPOW(){
        return this.addPOW;
    }
}
