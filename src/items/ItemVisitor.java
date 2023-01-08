package items;

public interface ItemVisitor {
    
    double marketVal(Item item);
    
    double purchasePrice(Item item);
    
}
