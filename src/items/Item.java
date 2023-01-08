package items;

import java.util.ArrayList;

public interface Item {
    
    // name
    public void setName(String name);
    public String getName();
    
    // price
    public void setPrice(double price);
    public double getPrice();
    public void setMarketValue(double marketValue);
    public double getMarketValue();
    
    // coordinates and dimensions
    public void setCoordinates(int xcoord, int ycoord);
    public void setSize(int length, int width, int height);
    public int getCoordX();
    public int getCoordY();
    public int getLength();
    public int getWidth();
    public int getHeight();
    
    // array list and functions for children 
    public ArrayList<Item> getChildren();
    public void addChild(Item item);
    public void removeChild(Item item);
    
    // iterates the list
    public Item next();
    public boolean hasNext();
    
    public double acceptMV(ItemVisitor visitor);
    public double acceptPr(ItemVisitor visitor);
}
