import java.util.*;

/**
 * Write a description of class Heal here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Heal extends Item
{
    private int heal;
    public Heal(String name, String description, double weight, int heal){
        super(name,description,weight);
        this.heal = heal;
    }
    
    public Heal(String name, String description, double weight, int heal, int price){
        super(name,description,weight,price);
        this.heal = heal;
    }
    
    public int getHeal(){
        return this.heal;
    }
    
    public void setHeal(int h){
        this.heal = h;
    }
}

