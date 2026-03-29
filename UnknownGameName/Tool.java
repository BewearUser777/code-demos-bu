import java.util.*;

/**
 * Write a description of class Tool here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Tool extends Item
{
    private String roomUse;
    private String openDirect;
    private String complement;
    private String useMessage;
    private String noComplement;
    
    public Tool(String name, String description, double weight, String roomUse, String openDirect, String complement, String useMessage, String noComplement){
        super(name,description,weight);
        this.roomUse = roomUse;
        this.openDirect = openDirect;
        this.complement = complement;
        this.useMessage = useMessage;
        this.noComplement = noComplement;
    }
    
    public String getRoomUse(){
        return this.roomUse;
    }
    
    public String getOpenDirect(){
        return this.openDirect;
    }
    
    public String getComplement(){
        return this.complement;
    }
    
    public String getUseMessage(){
        return this.useMessage;
    }
    
    public String getNoComplement(){
        return this.noComplement;
    }
}
