package items;

import java.util.ArrayList;

public class ItemChild implements Item{
    String name = "";
    int xCoord,yCoord,length,width,height;
    double price, marketValue;

    public ItemChild() {
    }

    public ItemChild(String name, int xCoord, int yCoord, int length, int width, int height, double price, double marketValue) {
        this.name = name;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.length = length;
        this.width = width;
        this.height = height;
        this.price = price;
        this.marketValue = marketValue;
    }
    
    public void addChild(Item item) { }

    public void removeChild(Item item) { }

    public ArrayList<Item> getChildren() {
        return null;
    }

    public Item next() {
        return null;
    }

    public boolean hasNext() {
        return false;
    }

    public String toString() {
        return name;
    }
    
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setMarketValue(double marketValue) {
        this.marketValue = marketValue;
    }
    
    public double getMarketValue() {
        return marketValue;
    }
    
    public int getCoordX() {
        return xCoord;
    }

    public int getCoordY() {
        return yCoord;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setCoordinates(int xcoord, int ycoord) {
        this.xCoord = xcoord;
        this.yCoord = ycoord;
        
    }

    public void setSize(int length, int width, int height) {
        this.length = length;
        this.width = width;
        this.height = height;
        
    }

    @Override
    public double acceptMV(ItemVisitor visitor) {
        return visitor.marketVal(this);
    }

    @Override
    public double acceptPr(ItemVisitor visitor) {
        return visitor.purchasePrice(this);
    }    
    
}
