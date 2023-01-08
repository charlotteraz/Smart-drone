package items;

import java.util.ArrayList;

public class ItemContainer extends ItemChild {
	
    ArrayList<Item> children;
    private int index = 0;

    public ItemContainer(String name, int xCoord, int yCoord, int length, int width, int height, double price, ArrayList<Item> children) {
        this.name = name;
        this.children = children;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.length = length;
        this.width = width;
        this.height = height;
        this.price = price;
    }

    public void addChild(Item item) {
        children.add(item);
    };
    
    public void removeChild(Item item) {
        children.remove(item);
    }

    public ArrayList<Item> getChildren() {
        return children;
    }

    public boolean hasNext() {
        if (index == children.size()) {
            index = 0;
            return false;
        }
        return index < children.size();
    }

    public Item next() {
        Item nextItem = this.children.get(index);
        index++;
        return nextItem;
    }
}
