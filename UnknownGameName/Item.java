import java.util.*;

public class Item {
   private String name;
   private String description;
   private double weight;
   private int price;
   public static Item[] storeCopies = new Item[23];
   
   public Item(){}
   
   public Item(String name, String description, double weight){
        this.name = name;
        this.description = description;
        this.weight = weight;
   }
   
   public Item(String name, String description, double weight, int price){
        this.name = name;
        this.description = description;
        this.weight = weight; 
        this.price = price;
   }

   public void setName(String name){
       this.name = name;
   }

   public void setDescription(String description){
       this.description = description;
   }
   
   public void setWeight(double weight){
       this.weight = weight;
   }

   public void setHeal(int heal){}
   
   public String getName(){
       return this.name;
   }

   public String getDescription(){
       return this.description;
   }

   public double getWeight(){
       return this.weight;
   }
   
   public int getAddPOW(){
       return 0;
   }
   
   public int getAddDEF(){
       return 0;
   }
   
   public String getText(){
       return "";
   }
   
   public int getHeal(){
       return 0;
   }
   
   public int getPrice(){
       return this.price;
   }
   
   public String getRoomUse(){
       return "";
   }
   
   public String getOpenDirect(){
       return "";
   }
   
   public String getComplement(){
       return "";
   }
   
   public String getUseMessage(){
       return "";
   }
   
   public String getNoComplement(){
       return "";
   }
}