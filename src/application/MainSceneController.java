package application;

import java.io.IOException;
import java.util.ArrayList;


import items.Item;
import items.ItemChild;
import items.ItemContainer;
import items.ItemVisitor;
import items.VisitItem;
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.VLineTo;
import javafx.util.Duration;
import adapter.TelloDrone;
import main.java.surelyhuman.jdrone.Constants;


public class MainSceneController {
    
    private static MainSceneController INSTANCE;
    
    public static MainSceneController getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new MainSceneController();
        }
        return INSTANCE;
    }
    
    private ArrayList<Item> farmItems;
    

    @FXML
    private TextField HField;

    @FXML
    private TextField LField;

    @FXML
    private TextField WField;

    @FXML
    private Button buttonAdd;

    @FXML
    private Button buttonDel;

    @FXML
    private TreeView<Item> ItemTree;

    @FXML
    private TextField nameField;

    @FXML
    private SplitPane splitLeft;
    
    @FXML
    private AnchorPane viewAnchorPane;

    @FXML
    private TextField xField;

    @FXML
    private TextField yField;
    
    @FXML
    private TextField pField;
    
    @FXML
    private AnchorPane actionItemPane;
    
    @FXML
    private Button containerBut;
    
    @FXML
    private Button droneBut;
    
    @FXML
    private Button saveBut;
    
    @FXML
    private Button scanBut;
    
    @FXML
    private Button visitBut;
    
    @FXML
    private AnchorPane drawItemPane;
    
    @FXML
    private ImageView droneItemView;
    
    @FXML
    private Canvas drawCanvas;
    
    @FXML
    private Button goHomeBut;
    
    @FXML
    private TextField marketValue;
    
    @FXML 
    private Label marketValueLabel;
    
    @FXML
    private RadioButton simulatorRadio;
    
    @FXML
    private RadioButton droneRadio;
    
    @FXML
    private ToggleGroup launch;
    
    // initializes farm items array list 
    @FXML
    public void initialize() {
        farmItems = new ArrayList<Item>();
        
        ItemChild farm = new ItemChild("Farm", 0, 0, 0, 0, 0, 0, 0);
        
        TreeItem<Item> root = new TreeItem<Item>(farm);
        root.setExpanded(true);
        ItemTree.setRoot(root);
        
        
        
        // displays the selected items values, code inspired from "https://stackoverflow.com/questions/17388866/getting-selected-item-from-a-javafx-tableview"
        // & "https://stackoverflow.com/questions/37962910/javafx-addlistener-not-working"
        ItemTree.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) ->{
                    Item selected = getItemSelected();
                    
                    
                    if (selected == null) {
                        ItemTree.getSelectionModel().select(ItemTree.getRoot());
                        selected = getItemSelected();
                    }
                   
        //sets each text field to represent the values from whatever item was selected            
                    nameField.setText(selected.getName());
                    xField.setText(String.valueOf(selected.getCoordX()));
                    yField.setText(String.valueOf(selected.getCoordY()));
                    LField.setText(String.valueOf(selected.getLength()));
                    WField.setText(String.valueOf(selected.getWidth()));
                    HField.setText(String.valueOf(selected.getHeight()));
                    pField.setText(String.valueOf(selected.getPrice()));
                    marketValue.setText(String.valueOf(selected.getMarketValue()));
                    
                    calculateMarketValue(selected);
                    calculatePurchasePrice(selected);
                   
                });
        
        // initialize sample drone
        droneItemView.setVisible(true);
        
        ItemContainer droneContainer = new ItemContainer("Command Center",200,10,80,170,100,0, new ArrayList<Item>());
        ItemChild droneImg = new ItemChild("Drone", 255,30,0,0,0,0,500);
        droneContainer.getChildren().add(droneImg);
        
        farmItems.add(droneContainer);
        
        droneItemView.setX(droneImg.getCoordX());
        droneItemView.setY(droneImg.getCoordY());
        
        drawItem(droneContainer);
        
        // initialize container barn and cow item
        ItemContainer barn = new ItemContainer("Barn", 40,200,170,80,100,45, new ArrayList<Item>());
        ItemChild cow = new ItemChild("Cow", 45, 220, 50,50,50,10,500);
        ItemChild kettle = new ItemChild("Cattle", 45,300, 30, 30, 30, 10,200);
        barn.addChild(cow);
        barn.addChild(kettle);
        drawItem(cow);
        drawItem(kettle);
        
        farmItems.add(barn);
        
        drawItem(barn);
        updateTree();
        
                
    }
    
    // adds container
    @FXML
    private void addContainer(ActionEvent event) {
        ItemContainer itemContainer = new ItemContainer("NewContainer",0,0,0,0,0,0, new ArrayList<Item>());
        addFarmItem(itemContainer);
        drawContainer(itemContainer);
        
    }
    
    // adds item
    @FXML
    private void addItem(ActionEvent event) {
        Item item = new ItemChild("NewItem", 0, 0, 0, 0, 0, 0, 0);
        addFarmItem(item);
    }
    
    // adds command center as container and drone in it
    @FXML
    private void addDrone(ActionEvent event) {
        droneItemView.setVisible(true);
        
        ItemContainer droneContainer = new ItemContainer("Command Center",200,10,80,170,100,0, new ArrayList<Item>());
        ItemChild droneImg = new ItemChild("Drone", 255,30,0,0,0,0, 0);
        droneContainer.getChildren().add(droneImg);
        
        farmItems.add(droneContainer);
        
        droneItemView.setX(droneImg.getCoordX());
        droneItemView.setY(droneImg.getCoordY());
        
        drawItem(droneContainer);
      
      updateTree();
     

    }
    
    // adds item and container to the farm / tree view and enables selection functionality
    private void addFarmItem(Item newItem) {
        Item itemSelected = getItemSelected();
        
        if(itemSelected != null && itemSelected.getChildren() != null) {
            itemSelected.addChild(newItem);
        } else {
            farmItems.add(newItem);
        }
         
        updateTree();
      
        
    }
    
    // adds children and items in a hierarchical manner
    private void addChildrenToTree(Item item, TreeItem<Item> parent) {
        TreeItem<Item> newTree = new TreeItem<Item>(item);
        parent.getChildren().add(newTree);
        
        while (item.hasNext()) {
            addChildrenToTree(item.next(), newTree);
        }
    }
    
    
    
    // deletes an item from the itemViewTree, code inspired from "https://stackoverflow.com/questions/62837734/how-to-delete-a-specific-treeitem-from-the-treeview-in-javafx"
    @FXML
    private void deleteItem(ActionEvent event) {
        
        Item itemSel = getItemSelected();                           //gets the item selected by the user
        
        if(String.valueOf(itemSel) == "Farm" || itemSel == null) {      //if the item selected is null or if it is the farm item then return since neither can be deleted
            return;
        }
       
        
        TreeItem<Item> selected = ItemTree.getSelectionModel().getSelectedItem();   //selected item in the itemtree view
        TreeItem<Item> parent = selected.getParent();
        
        if(String.valueOf(parent.getValue()) == "Farm") {             //if the parent of the item selected is the farm, remove the item selected
            farmItems.remove(itemSel);
        }
     
        else {
            parent.getValue().removeChild(itemSel);                   //else, remove the child of the parent selected
        }
        
        // removes drone image
        if(itemSel.getName() == "Command Center" || itemSel.getName() == "Drone") {
            droneItemView.setVisible(false);
        }
        clearItem(itemSel);
        updateTree();

    }
    
    @FXML
    private void saveChanges(ActionEvent event) {
        
        Item selected = getItemSelected();
        clearItem(selected); //remove previous visualization
        if(selected.getName() == "Farm") return;
        selected.setName(nameField.getText());
        selected.setCoordinates(Integer.parseInt(xField.getText()), Integer.parseInt(yField.getText()));
        selected.setPrice(Double.parseDouble(pField.getText()));
        selected.setSize(Integer.parseInt(LField.getText()), Integer.parseInt(WField.getText()), Integer.parseInt(HField.getText()));
        selected.setMarketValue(Double.parseDouble(marketValue.getText()));
        
        

        clearItem(selected);//clear old visualization 
        drawItem(selected); //add new visualization
        
        ItemTree.refresh();
        updateTree();   //update the tree to visually show the updated name
        
      
    }
    
    // updates tree view for every item / container added
    private void updateTree() {
         ItemTree.getRoot().getChildren().clear();
         farmItems.forEach(item -> {
             addChildrenToTree(item, ItemTree.getRoot());
         });
     }
    
    
    // gets the value of selected item
    Item getItemSelected() {
        TreeItem<Item> itemTreeSelected = (TreeItem<Item>) ItemTree.getSelectionModel().getSelectedItem();
        
        if(itemTreeSelected == null) {
            return null;
        }
        return itemTreeSelected.getValue();
    }
    
  //calculates market value for item and total market value 
    private void calculateMarketValue(Item selected) {
        
        ItemVisitor visitor = new VisitItem();
        
        marketValue.setText(String.valueOf(selected.acceptMV(visitor)));
    } 
    
    private void calculatePurchasePrice(Item selected) {
        
        ItemVisitor visitor = new VisitItem();
        
        pField.setText(String.valueOf(selected.acceptPr(visitor)));
        
    }
    
    
    // add code here for drone animation to scan the farm
    @FXML
    private void scanFarm(ActionEvent event) throws InterruptedException, IOException {
        
        if(simulatorRadio.isSelected()) {
            double xPos = droneItemView.getX();
            double yPos = droneItemView.getY();
            
            Path flightPath = new Path();   
            
            flightPath.getElements().add(new MoveTo(280, 50)); 
            flightPath.getElements().add(new HLineTo(550)); 
            flightPath.getElements().add(new VLineTo(800)); 
            flightPath.getElements().add(new HLineTo(500)); 
            flightPath.getElements().add(new VLineTo(50)); 
            flightPath.getElements().add(new HLineTo(450)); 
            flightPath.getElements().add(new VLineTo(800)); 
            flightPath.getElements().add(new HLineTo(400)); 
            flightPath.getElements().add(new VLineTo(50)); 
            flightPath.getElements().add(new HLineTo(350)); 
            flightPath.getElements().add(new VLineTo(800)); 
            flightPath.getElements().add(new HLineTo(300)); 
            flightPath.getElements().add(new VLineTo(50)); 
            flightPath.getElements().add(new HLineTo(250)); 
            flightPath.getElements().add(new VLineTo(800)); 
            flightPath.getElements().add(new HLineTo(200)); 
            flightPath.getElements().add(new VLineTo(50)); 
            flightPath.getElements().add(new HLineTo(150)); 
            flightPath.getElements().add(new VLineTo(800)); 
            flightPath.getElements().add(new HLineTo(100)); 
            flightPath.getElements().add(new VLineTo(50)); 
            flightPath.getElements().add(new HLineTo(50)); 
            flightPath.getElements().add(new VLineTo(800)); 
            flightPath.getElements().add(new VLineTo(50)); 
            flightPath.getElements().add(new VLineTo(50));
            flightPath.getElements().add(new HLineTo(280));
            
            PathTransition scanPath = new PathTransition();
            scanPath.setNode(droneItemView);
            scanPath.setDuration(Duration.millis(15000));
            scanPath.setPath(flightPath); 
            scanPath.setCycleCount(1);
            scanPath.play();
        }
        
        else if(droneRadio.isSelected()) {
            droneScan();
        }
        
    }
    
    private void droneScan() throws InterruptedException, IOException {
        TelloDrone tello = new TelloDrone();
        tello.activateSDK();
        tello.hoverInPlace(10);
        tello.takeoff();
        tello.increaseAltitude(20);
        tello.turnCCW(90);
        tello.flyForward(10*30);
        tello.turnCW(90);
        tello.flyForward(32*30);
        tello.turnCW(90);
        tello.flyForward(2*30);
        tello.turnCW(90);
        tello.flyForward(32*30);
        tello.turnCCW(90);
        tello.flyForward(2*30);
        tello.turnCCW(90);
        tello.flyForward(32*30);
        tello.turnCW(90);
        tello.flyForward(2*30);
        tello.turnCW(90);
        tello.flyForward(32*30);
        tello.turnCCW(90);
        tello.flyForward(2*30);
        tello.turnCCW(90);
        tello.flyForward(32*30);
        tello.turnCW(90);
        tello.flyForward(2*30);
        tello.turnCW(90);
        tello.flyForward(32*30);
        tello.turnCW(90);
        tello.flyForward(8*30);
        tello.turnCW(90);
        tello.land();
        tello.end();
    }
    // visits whatever item is selected, code inspired from "https://docs.oracle.com/javase/8/javafx/api/javafx/scene/shape/Path.html"
    @FXML
    private void visitFarmItem(ActionEvent event) throws InterruptedException, IOException {
        
            if(simulatorRadio.isSelected()) {
                Item item = getItemSelected();
                
                //offset code to visit the middle of the item/container
                  double xOffset = item.getLength() / 2;
                  double yOffset = item.getWidth() / 2;
                  
                  //gets the coords of whatever item is selected
                  double xPath = item.getCoordX() + xOffset;
                  double yPath = item.getCoordY() + yOffset;
                 
                  //sets a return point for the drone
                  double xOrigin = droneItemView.getX();
                  double yOrigin = droneItemView.getY();
                  
                  //creates a new path for the drone to travel
                  Path travelPath = new Path();
                  travelPath.getElements().add(new MoveTo(xOrigin, yOrigin));
                  travelPath.getElements().add(new HLineTo(xPath));
                  travelPath.getElements().add(new VLineTo(yPath));
                  droneItemView.setX(xPath);
                  droneItemView.setY(yPath);
                  
                  //allows the path created to be seen and used by the image view
                  PathTransition visitItemPath = new PathTransition();
                  visitItemPath.setNode(droneItemView);
                  visitItemPath.setDuration(Duration.seconds(3));
                  visitItemPath.setPath(travelPath);
                  visitItemPath.setCycleCount(1);
                  visitItemPath.play();
            }
            
            else if(droneRadio.isSelected()) {
                droneVisit();
            }
        
    }
    
    private void droneVisit() throws InterruptedException, IOException {
        TelloDrone tello = new TelloDrone();
        
        Item item = getItemSelected();
        double x = item.getCoordX();
        double y = item.getCoordY();
        
        tello.activateSDK();
        tello.hoverInPlace(10);
        tello.takeoff();
        tello.increaseAltitude(20);
        
        if (x > 255) {
            tello.turnCCW(90);
            tello.flyForward(((int)x - 255)*30/25);
            tello.turnCW(90);
        } else {
            tello.turnCW(90);
            tello.flyForward((int)x*30/25);
            tello.turnCCW(90);
        }
        tello.flyForward(((int)y - 30)*30/25);
        tello.turnCCW(360);
        
        tello.turnCW(180);
        tello.flyForward(((int)y - 30)*30/25);
        
        if (x > 255) {
            tello.turnCCW(90);
            tello.flyForward(((int)x - 255)*30/25);
            tello.turnCW(90);
        } else {
            tello.turnCW(90);
            tello.flyForward((int)x*30/25);
            tello.turnCW(90);
        }
        
        tello.land();
        tello.end();
        
//        tello.activateSDK();
//        tello.streamOn();
//        tello.streamViewOn();
//        tello.hoverInPlace(10);
//        tello.takeoff();
//        tello.hoverInPlace(10);
//        tello.flyForward(100);
//        tello.turnCCW(180);
        
//        tello.flip("b");
//        tello.flyForward(100);
//        tello.flip("f");
//        tello.turnCW(180);
//        tello.land();
//        tello.streamOff();
//        tello.streamViewOff();
//        tello.end();
    }
    
    
    //add code for go home drone
    @FXML
    private void goHome(ActionEvent event) {
        
     // obtain current coordinates
        double xOrigin = droneItemView.getX();
        double yOrigin = droneItemView.getY();

        Path travelHome = new Path();
        travelHome.getElements().add(new MoveTo(xOrigin, yOrigin));
        travelHome.getElements().add(new HLineTo(280));
        travelHome.getElements().add(new VLineTo(50));
        droneItemView.setX(200);
        droneItemView.setY(50);


        PathTransition visitItemPath = new PathTransition();
        visitItemPath.setNode(droneItemView);
        visitItemPath.setDuration(Duration.seconds(3));
        visitItemPath.setPath(travelHome);
        visitItemPath.setCycleCount(1);
        visitItemPath.play();
        droneItemView.setVisible(true);
        
    }

    
    // add code here for drawing the items on the anchor pane / farm
    private void drawItem(Item item) {
        GraphicsContext gc = drawCanvas.getGraphicsContext2D();
        
        double x = Double.valueOf(item.getCoordX());
        double y = Double.valueOf(item.getCoordY());
        double w = Double.valueOf(item.getWidth());
        double l = Double.valueOf(item.getLength());
        
        gc.setStroke(Color.BLACK);
        gc.fillText(item.getName(), x,y+10);
        gc.strokeRect(x,y,w,l);
    }
    
    // add code here for drawing containers
    private void drawContainer(Item itemContainer) {
        
        GraphicsContext gc = drawCanvas.getGraphicsContext2D();
    }
    
    //clear previous visualizations to prevent duplicates
    private void clearItem(Item item) {
        GraphicsContext gc = drawCanvas.getGraphicsContext2D();
        
        double x = Double.valueOf(item.getCoordX());
        double y = Double.valueOf(item.getCoordY());
        double w = Double.valueOf(item.getWidth());
        double l = Double.valueOf(item.getLength());
        
        gc.clearRect(x-1,y-1,w+3,l+3); //workaround
    }
    
   
}











