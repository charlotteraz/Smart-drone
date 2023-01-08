package items;

public class VisitItem implements ItemVisitor {

    @Override
    public double marketVal(Item item) {
        
        double totalMarketValue = 0.0;
        
        if (item.getChildren() != null) {
            while (item.hasNext() && item.getChildren().size() >= 1) {
                totalMarketValue += item.next().getMarketValue();
            }
            
            return totalMarketValue;
        } else {
            return item.getMarketValue();
        }
    }

    @Override
    public double purchasePrice(Item item) {
        
        double totalPurchasePrice = item.getPrice();
        
        if (item.getChildren() != null) {
            while(item.hasNext() && item.getChildren().size() >= 1) {
                totalPurchasePrice += item.next().getPrice();
            }
            
            return totalPurchasePrice;
        } else {
            return item.getPrice();
        }
        
      
    }
    
}
