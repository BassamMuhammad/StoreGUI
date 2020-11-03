/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.storegui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;
/**
 *
 * @author Bassam Muhammad
 */
public final class OnlineSelectionScrollPane extends JScrollPane{
    //category and products panels will go on it
    private static final JPanel MAIN_PANEL = new JPanel();     //static because scroll pane "add" method not working and so to pass it to its constructor 
    private static final HashMap<String, JPanel> CATEGORIES = new HashMap<>();  //keep record of categories
    public static final ArrayList<Item> CART = new ArrayList<>();                   //keep record of items included in cart
    private static final HashMap<JPanel, JLabel> UNDER_EDITING_PANELS = new HashMap<>();
    static{
        
        //make 4 categories
        addCategory("Fruits and Vegetables", "fruitVeg-icon.png");
        addCategory("Electronics", "lightning-icon.png");
        addCategory("Kitchen Items", "cutlery-icon.png");
        addCategory("Sports Items", "Sport-icon.png");
        
        //add products to first category
        JPanel panel = new JPanel();
        panel.add(addProduct("Banana", "banana-icon.png", 100.0, "dozen"));
        panel.add(addProduct("Cherry", "cherry-icon.png", 250.0, "kg"));
        panel.add(addProduct("Coconut", "coconut-icon.png", 200.0, "kg"));
        panel.add(addProduct("Melon", "melon-icon.png", 150.0, "kg"));
        panel.add(addProduct("Strawberry", "strawberry-icon.png", 175.0, "kg"));
        
        CATEGORIES.get("Fruits and Vegetables").add(panel);
       
        //add products to secoond category
        panel = new JPanel();
        panel.add(addProduct("Computer", "computer-icon.png", 40000.0, "piece"));
        panel.add(addProduct("Television", "television-icon.png", 35000.0, "piece"));
        panel.add(addProduct("Kettle", "kettle-icon.png", 500.0, "piece"));
        panel.add(addProduct("Fridge", "fridge-icon.png", 30000.0, "piece"));
        panel.add(addProduct("Printer", "printer-icon.png", 2000.0, "piece"));
        
        CATEGORIES.get("Electronics").add(panel);
        
        //add products to third category
        panel = new JPanel();
        panel.add(addProduct("Spoon set(12 pieces)", "spoon-icon.png", 1000.0, "set"));
        panel.add(addProduct("Knife set(12 pieces)", "knife-icon.png", 3000.0, "set"));
        panel.add(addProduct("Pan(non-stick)", "Pan-icon.png", 2000.0, "piece"));
        panel.add(addProduct("Plate set(12 pieces)", "plate-icon.png", 1500.0, "set"));
        panel.add(addProduct("Rolling Pin", "Roling-pin-icon.png", 700.0, "piece"));
        panel.add(addProduct("Hand Blender", "Handblender-icon.png", 1500.0, "piece"));
        
        CATEGORIES.get("Kitchen Items").add(panel);
        
        //add products to fourth category
        panel = new JPanel();
        panel.add(addProduct("Football", "football-icon.png", 2000.0, "set"));
        panel.add(addProduct("American Football", "american-football-icon.png", 1000.0, "set"));
        panel.add(addProduct("Basketball", "basketball-icon.png", 1500.0, "piece"));
        panel.add(addProduct("Baseball", "baseball-icon.png", 500.0, "set"));
        panel.add(addProduct("Cricket bat with ball", "cricket-icon.png", 1200.0, "piece"));
        
        CATEGORIES.get("Sports Items").add(panel);
       
    }
    
    public OnlineSelectionScrollPane(){
        super(MAIN_PANEL);
        
        //remove any previous component (dealing with static nonsense)
        MAIN_PANEL.removeAll();
        MAIN_PANEL.validate();
        MAIN_PANEL.repaint();
        
        BoxLayout layout = new BoxLayout(MAIN_PANEL, BoxLayout.Y_AXIS);
        MAIN_PANEL.setLayout(layout);
        
        JButton btnBack = new JButton("Back");
        JButton btnShowCart = new JButton("Show Cart");
        JButton btnBuyCart = new JButton("Buy products in cart");
        
        JPanel subPanel = new JPanel();
        subPanel.add(btnBack);
        subPanel.add(Box.createRigidArea(new Dimension(20, 0)));    //horizontal space
        subPanel.add(btnShowCart);
        subPanel.add(Box.createRigidArea(new Dimension(20, 0)));    //horizontal space
        subPanel.add(btnBuyCart);
        
        
        if(OnlineLoginPanel.isAdminAccess()){       //if have admin access give these extra things
            JButton btnAddCategory = new JButton("Add Category");
            subPanel.add(Box.createRigidArea(new Dimension(30, 0)));
            subPanel.add(btnAddCategory);

            btnAddCategory.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e){
                    String name = JOptionPane.showInputDialog(null, "Enter category name");
                    String imageAddress = JOptionPane.showInputDialog(null, "Enter image address");
                    addCategory(name, imageAddress);
                    MAIN_PANEL.add(CATEGORIES.get(name));
                    MAIN_PANEL.validate();
                    MAIN_PANEL.repaint();
                }

            });
        }
        //if logined give log out button
        if(OnlineRegistrationPanel.isLogin()){
            JButton logOut = new JButton("Log Out");
            subPanel.add(Box.createRigidArea(new Dimension(50, 0)));
            subPanel.add(logOut);
            
            logOut.addActionListener(new ActionListener(){
            
                @Override
                public void actionPerformed(ActionEvent e){
                    OnlineRegistrationPanel.setLogin(false);
                    OnlineLoginPanel.setAdminAccess(false);
                    
                    for(JLabel label : UNDER_EDITING_PANELS.values()){
                        JPanel panel = CATEGORIES.get(label.getText());
                        panel.remove(0);
                        panel.add(label, 0);
                        panel.revalidate();
                        panel.repaint();
                    }
                    MainPanel.setSubContainer(new OnlineSelectionScrollPane());
                }
                   
            });

        }
        
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainPanel.setSubContainer(new MainMenuPanel());
            }
        });
        
        btnShowCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainPanel.setSubContainer(new ShowOnlineCartPanel());
            }
        });
        
        btnBuyCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(OnlineRegistrationPanel.isLogin())
                    MainPanel.setSubContainer(new OnlineBuyCartPanel());
                else
                    MainPanel.setSubContainer(new OnlineLoginPanel());
            }
        });
        

        MAIN_PANEL.add(subPanel);
        
        //add categories to main panel
        for(JPanel panel : CATEGORIES.values())
            MAIN_PANEL.add(panel);
        
    }
    
    public static void addCategory(String categoryName, String imageAddress){
        if(!CATEGORIES.containsKey(categoryName)){      //if category name is not already used
            final JPanel panel = new JPanel();
            BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
            panel.setLayout(layout);

            JLabel tempLabel;
           try{
                URL url = OnlineSelectionScrollPane.class.getClassLoader().getResource(imageAddress);
                tempLabel = new JLabel(categoryName, new ImageIcon(url), SwingConstants.LEADING);
           }catch(Exception ex){
                JOptionPane.showMessageDialog(null, "Image not found", "ERROR", JOptionPane.ERROR_MESSAGE);
                URL url = OnlineSelectionScrollPane.class.getClassLoader().getResource("close-icon.png");
                tempLabel = new JLabel(categoryName, new ImageIcon(url), JLabel.CENTER);
            }

            JLabel label = tempLabel;           //will use it later in anonymous class 
            label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));

            panel.addMouseListener(new MouseAdapter() {
                private boolean show = true;    //show extra buttons in admin access
                JPanel subPanel = new JPanel();
                @Override
                public void mouseClicked(MouseEvent e){
                    
                    if(OnlineLoginPanel.isAdminAccess()){
                        if(show){
                            show = false;       //so if clicked again hide these
                            UNDER_EDITING_PANELS.put(panel, label);
                            JButton btnAddProduct = new JButton("Add product");
                            JButton btnEditCategory = new JButton("Edit Category");
                            JButton btnDelete = new JButton("Delete Category");

                            subPanel.setBackground(Color.WHITE);

                            subPanel.add(label);
                            Dimension dimension = new Dimension(5, 0);
                            subPanel.add(Box.createRigidArea(dimension));
                            subPanel.add(btnAddProduct);
                            subPanel.add(Box.createRigidArea(dimension));
                            subPanel.add(btnEditCategory);
                            subPanel.add(Box.createRigidArea(dimension));
                            subPanel.add(btnDelete);

                            panel.remove(label);
                            panel.add(subPanel, 0);
                            panel.revalidate();
                            panel.repaint();
                            
                            btnAddProduct.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    try{
                                        String productName = JOptionPane.showInputDialog(null, "Enter product name");
                                        String imageAddress = JOptionPane.showInputDialog(null, "Enter image address");
                                        double cost = Double.parseDouble((String)JOptionPane.showInputDialog(null, "Enter Price"));
                                        String quantity = JOptionPane.showInputDialog(null, "Enter quantity(it will be displayed as cost per quantity)");
                                        
                                        if(productName == null || imageAddress == null || quantity == null)
                                            throw new NullPointerException();
                                        
                                        try{
                                            JPanel subPanel = (JPanel)panel.getComponent(1);
                                            JPanel subSubPanel = addProduct(productName, imageAddress, cost, quantity);
                                            subPanel.add(subSubPanel);
                                            subPanel.validate();
                                            subPanel.repaint();
                                        }catch(ArrayIndexOutOfBoundsException ex){
                                            JPanel subPanel = new JPanel();
                                            JPanel subSubPanel = addProduct(productName, imageAddress, cost, quantity);
                                            subPanel.add(subSubPanel);
                                            subPanel.validate();
                                            subPanel.repaint();
                                            panel.add(subPanel);
                                            panel.validate();
                                            panel.repaint();
                                        }
                                    }catch(NumberFormatException ex){
                                        JOptionPane.showMessageDialog(null, "Invalid Cost", "ERROR", JOptionPane.ERROR_MESSAGE);
                                    } catch(NullPointerException ex){
                                        JOptionPane.showMessageDialog(null, "Operation Cancelled");
                                    }

                                }
                            });

                            btnEditCategory.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    int choice = JOptionPane.showConfirmDialog(null, "Change Category");
                                    if(choice == JOptionPane.YES_OPTION){
                                        try{
                                            String name = JOptionPane.showInputDialog(null, "Enter Category Name");
                                            if(name == null)
                                                throw new NullPointerException();
                                        
                                            String imageAddress = JOptionPane.showInputDialog(null, "Enter Category's Image Address");
                                            if(imageAddress == null)
                                                throw new NullPointerException();

                                            CATEGORIES.remove(label.getText());
                                            label.setText(name);
                                            CATEGORIES.put(name, panel);
                                    
                                            URL url = OnlineSelectionScrollPane.class.getClassLoader().getResource(imageAddress);
                                            label.setIcon(new ImageIcon(ImageIO.read(url)));
                                        }catch(IOException ex){
                                            JOptionPane.showMessageDialog(null, "Image not found", "ERROR", JOptionPane.ERROR_MESSAGE);
                                        }catch(NullPointerException ex){
                                            JOptionPane.showMessageDialog(null, "Operation Cancelled");
                                        }catch(Exception ex){
                                            JOptionPane.showMessageDialog(null, "Image not found", "ERROR", JOptionPane.ERROR_MESSAGE);
                                        }
                                    }
                                    
                                }
                            });

                            btnDelete.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    int choice = JOptionPane.showConfirmDialog(null, "Delete Permanently?", "Confirmation", JOptionPane.YES_NO_OPTION);
                                    if(choice == JOptionPane.YES_OPTION){
                                        CATEGORIES.remove(label.getText(), panel);
                                        JPanel parentPanel = (JPanel)panel.getParent();
                                        parentPanel.remove(panel);
                                        parentPanel.validate();
                                        parentPanel.repaint();
                                    }  
                                }
                            });

                        }
                        else{
                            subPanel.removeAll();   //remove everything(those extra buttons in admin access) from extra panel
                            panel.remove(subPanel); //remove that panel too
                            panel.setBackground(Color.WHITE);
                            panel.add(label, 0);
                            panel.revalidate();
                            panel.repaint();
                            show = true;        //if clicked again show those buttons
                            UNDER_EDITING_PANELS.remove(panel);
                        }

                    }
                }
                
                @Override
                public void mouseEntered(MouseEvent e){
                    
                    if(OnlineLoginPanel.isAdminAccess())
                        panel.setToolTipText("Click to Edit");
                    
                    else
                        panel.setToolTipText(null);
                }
                
            });

            if(!OnlineLoginPanel.isAdminAccess() || !CATEGORIES.containsKey(categoryName)){
                panel.setBackground(Color.WHITE);
                panel.add(label);
            }
            

            CATEGORIES.put(categoryName, panel);        //put in categories(hash map)
        }
        else
            JOptionPane.showMessageDialog(null, "Category already present", "ERROR", JOptionPane.ERROR_MESSAGE);
    }
    
    
    
    private static JPanel addProduct(String productName, String imageAddress, Double cost, String quantity){
        JPanel panel = new JPanel();
        BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(layout);
        JLabel tempLabel;
        try{
            URL url = OnlineSelectionScrollPane.class.getClassLoader().getResource(imageAddress);
            tempLabel = new JLabel(productName, new ImageIcon(ImageIO.read(url)), JLabel.CENTER);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Image not found", "ERROR", JOptionPane.ERROR_MESSAGE);
            URL url = OnlineSelectionScrollPane.class.getClassLoader().getResource("close-icon.png");
            tempLabel = new JLabel(productName, new ImageIcon(url), JLabel.CENTER);
        }
        
            JLabel imageNameLabel = tempLabel;
            imageNameLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
            imageNameLabel.setHorizontalTextPosition(SwingConstants.CENTER);
            imageNameLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
            panel.add(imageNameLabel);
        
            String costText = "Cost: " + cost;
            JLabel costLabel = new JLabel(costText);
            costLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));

            String quantityText = "per " + quantity;
            JLabel quantityLabel = new JLabel(quantityText);

            panel.add(costLabel);
            panel.add(quantityLabel);
            panel.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
            panel.validate();
            panel.repaint();
        
        panel.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                    if(OnlineLoginPanel.isAdminAccess()){
                        if(!e.isMetaDown()){//if not right click
                            int choice = JOptionPane.showConfirmDialog(null, "Delete Permanently?", "Confirmation", JOptionPane.YES_NO_OPTION);
                            if(choice == JOptionPane.YES_OPTION){
                                JPanel parentPanel = (JPanel)panel.getParent();
                                parentPanel.remove(panel);
                                parentPanel.validate();
                                parentPanel.repaint();
                            }  
                        }
                        
                        else{   //if right click
                            int choice = JOptionPane.showConfirmDialog(null, "Edit Product?", "Confirmation", JOptionPane.YES_NO_OPTION);
                            if(choice == JOptionPane.YES_OPTION){
                                try{
                                    String productName = JOptionPane.showInputDialog(null, "Enter product name");
                                    if(productName == null)
                                        throw new NullPointerException();
                                    
                                    String imageAddress = JOptionPane.showInputDialog(null, "Enter image address");
                                    if(imageAddress == null)
                                        throw new NullPointerException();
                                    
                                    double cost = Double.parseDouble((String)JOptionPane.showInputDialog(null, "Enter Price"));
                                    String quantity = JOptionPane.showInputDialog(null, "Enter quantity(it will be displayed as cost per quantity)");
                                    if(quantity == null)
                                        throw new NullPointerException();
                                    
                                    URL url = OnlineSelectionScrollPane.class.getClassLoader().getResource(imageAddress);File file = new File(imageAddress);
                                    imageNameLabel.setText(productName);
                                    imageNameLabel.setIcon(new ImageIcon(ImageIO.read(url)));
                                    
                                    costLabel.setText("" + cost);
                                    quantityLabel.setText(quantity);
                                }catch(NumberFormatException ex){
                                    JOptionPane.showMessageDialog(null, "Invalid Cost", "ERROR", JOptionPane.ERROR_MESSAGE);
                                }catch(IOException ex){
                                    JOptionPane.showMessageDialog(null, "Image not found", "ERROR", JOptionPane.ERROR_MESSAGE);
                                }catch(NullPointerException ex){
                                    JOptionPane.showMessageDialog(null, "Operation Cancelled");
                                }catch(Exception ex){
                                    JOptionPane.showMessageDialog(null, "Image not found", "ERROR", JOptionPane.ERROR_MESSAGE);
            
                                }
                            }            

        
                        }
                    
                    }                   
                    else{    //if not admin access
                        int choice = JOptionPane.showConfirmDialog(null, "Add to cart?", "Confirmation", JOptionPane.YES_NO_OPTION);
                        if(choice == JOptionPane.YES_OPTION){
                        CART.add(new Item(productName, imageAddress, cost)); 
                    }
                }
            }
            
            @Override
            public void mouseEntered(MouseEvent e){
                panel.setBackground(Color.blue);
                panel.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.red));
                
                if(OnlineLoginPanel.isAdminAccess())
                    panel.setToolTipText("Click to Delete. Right Click to Edit");
                    
                else
                    panel.setToolTipText("Click to buy");
            }
            @Override
            public void mouseExited(MouseEvent e){
                panel.setBackground(null);
                panel.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.black));
            }
            
        });
        return panel;
            
    }
}
