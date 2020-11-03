/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.storegui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class AddingOnsiteProductPanel extends JPanel {
    private JTable table;
    public static final ArrayList<Item> ITEMS = new ArrayList<>();
    private int initialItemsSize = ITEMS.size();        //how much items are in arrlist at start

    
    static{
        
        ITEMS.add(new Item("Youghurt", 120, 1, 100));
        ITEMS.add(new Item("Mango", 50, 2, 100));
        ITEMS.add(new Item("Milk", 100, 3, 100));    
    }
    public AddingOnsiteProductPanel(){
        
        super(new BorderLayout());
        
        // set the model for the table and add it
        DefaultTableModel model = new DefaultTableModel(new String[]{"Sr.No." , "Product ID", "Produt Name", "Price", "Qty"}, 0) {
            //make table not editable
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;             
            }
        };
        table = new JTable(model);
        
        //set sizes of column
        table.getColumnModel().getColumn(0).setMaxWidth(40);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setMaxWidth(40);
        
        // create JTextFields
        JTextField textId = new JTextField();
        JTextField textProductName = new JTextField();
        JTextField textPrice = new JTextField();
        JTextField textQuantity = new JTextField();
        
        // create JButtons
        JButton btnAdd = new JButton("Add");
        JButton btnDelete = new JButton("Delete");    
        JButton btnBack = new JButton("Back");
        
        Dimension prefferedSize = new Dimension(100, 20);
        //set size of textfields
        textId.setPreferredSize(prefferedSize);
        textProductName.setPreferredSize(prefferedSize);
        textPrice.setPreferredSize(prefferedSize);
        textQuantity.setPreferredSize(prefferedSize);

        
        // create a JScrollPane having Jtable and
        JScrollPane scrollPane = new JScrollPane(table);
        
        //add it to AddingOnsiteProductPanel
        add(scrollPane);
        
        
        JPanel subPanel1 = new JPanel();
        subPanel1.add(new JLabel("Product ID: "));
        subPanel1.add(textId);
        subPanel1.add(new JLabel("Product Name: "));
        subPanel1.add(textProductName);
        subPanel1.add(new JLabel("Price: "));
        subPanel1.add(textPrice);
        subPanel1.add(new JLabel("Quantity: "));
        subPanel1.add(textQuantity);
    
        
        JPanel subPanel2 = new JPanel();
        subPanel2.add(btnAdd);
        subPanel2.add(Box.createRigidArea(new Dimension(5, 0)));        //add horizontal space b/w buttons
        subPanel2.add(btnDelete);        
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(subPanel1);
        panel.add(subPanel2);
        
        add(panel, BorderLayout.SOUTH);
        add(btnBack, BorderLayout.NORTH);
        
        
                        //Event Handling
                        
        btnAdd.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
             
                // create an array of String to set the row data
                String[] row = new String[5];
                row[0] = (model.getRowCount() + 1) + "";
                row[1] = textId.getText();
                row[2] = textProductName.getText();
                row[3] = textPrice.getText();
                row[4] = textQuantity.getText();
                
                
                if(isFollowProtocol(row)){
                    if(!isOccuredBefore(row))
                        ITEMS.add(new Item(row[2], Double.parseDouble(row[3]), Long.parseLong(row[1]), Integer.parseInt(row[4])));
                   
                    // add row to the model(table)
                    model.addRow(row);                    
                }
            }

            private boolean isFollowProtocol(String[] row) {
                for(int i = 1; i < row.length; i++){
                    if(row[i].equals("")){
                        JOptionPane.showMessageDialog(null, "All fields should be filled", "ERROR", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                }
        
                //verify if id, price and quantity fall into logicelly acceptable range
                try{
                    long id = Long.parseLong(row[1]);
                    if(id <= 0)
                        throw new NumberFormatException();
                }catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "Invalid ID!!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                try{
                    double price = Double.parseDouble(row[3]);
                    if(price < 0)
                        throw new NumberFormatException();
                }catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "Invalid Price!!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                try{
                    int quantity = Integer.parseInt(row[4]);
                    if(quantity <= 0)
                        throw new NumberFormatException();
                }catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "Invalid Quantity!!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                return true;
            }

            private boolean isOccuredBefore(String[] row) {
                for(Item item : ITEMS){
                    if(Long.parseLong(row[1]) == item.getId()){         //if id is already present
                        if(!row[2].equals(item.getName())){             //if name is registered differently
                            JOptionPane.showMessageDialog(null, "Same id can't have different name\nOld value will be added"
                                    , "ERROR", JOptionPane.ERROR_MESSAGE);
                            row[2] = item.getName();
                            row[3] = "" + item.getCost();
                            ITEMS.add(new Item(row[2], Double.parseDouble(row[3]), Long.parseLong(row[1]), Integer.parseInt(row[4])));
                            return true;
                        }
                        else if(Double.parseDouble(row[3]) != item.getCost()){// if cost is registered differently
                            JOptionPane.showMessageDialog(null, "Same id can't have different price\nOld value will be added"
                                    , "ERROR", JOptionPane.ERROR_MESSAGE);
                            row[2] = item.getName();
                            row[3] = "" + item.getCost();
                            ITEMS.add(new Item(row[2], Double.parseDouble(row[3]), Long.parseLong(row[1]), Integer.parseInt(row[4])));
                            return true;
                        }
                    }
                    if(row[2].equals(item.getName())){                  //if name is already present
                        if(Long.parseLong(row[1]) != item.getId()){     //if id is registered differently
                            JOptionPane.showMessageDialog(null, "Different id can't have same name\nOld value will be added"
                                    , "ERROR", JOptionPane.ERROR_MESSAGE);
                            row[1] = "" + item.getId();
                            row[3] = "" + item.getCost();
                            ITEMS.add(new Item(row[2], Double.parseDouble(row[3]), Long.parseLong(row[1]), Integer.parseInt(row[4])));
                            return true;
                        }
                    }
                }
                return false;
            }

           
        });
        
        // button remove row
        btnDelete.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
            
                // i = the index of the selected row
                int i = table.getSelectedRow();
                
                if(i >= 0){       
                    
                    //remove from items(arraylist)
                    ITEMS.remove(initialItemsSize + i);
                    // remove the row from table
                    model.removeRow(i);
                    //update Sr. No.
                    for(int j = i; j < model.getRowCount(); j++){
                        model.setValueAt("" + (j+1), j, 0);
                    }
                    
                }
                else
                    JOptionPane.showMessageDialog(null, "Choose a row", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });
        
       
        
        btnBack.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                //sort Alphabetically
                sortItems(ITEMS);
                MainPanel.setSubContainer(new OnsiteOptionPanel());
            }
        });
            
                
    }
        //sort Alphabetically
    public static void sortItems(ArrayList<Item> items){
        Item[] temp = new Item[items.size()];
        
            //transfer from item arrlist to temp arrlist(except last) 
        for(int i = 0; i < temp.length - 1; i++){
            temp[i] = items.remove(0);
        }
        
        for(int i = 0; i < temp.length - 1; i++){ 
            boolean skip = false;
            for(int j = 0 ; j < items.size(); j++){
                if(items.get(j).getName().compareTo(temp[i].getName()) == 0){
                    items.get(j).setQuantity(items.get(j).getQuantity() + temp[i].getQuantity());
                    skip = true;
                    break;
                }

                else if(items.get(j).getName().compareTo(temp[i].getName()) > 0){
                    items.add(j, temp[i]);
                    skip = true;
                    break;
                }

            }
            if(!skip)   
                items.add(temp[i]);

        }
    }


}
