import java.util.*;

/**
 * Write a description of class Text here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Text extends Item
{
    private String text;
    
    public Text(String name, String description, double weight, String text){
        super(name,description,weight);
        this.text = text;
    }
    
    public String getText(){
        return this.text;
    }
}
