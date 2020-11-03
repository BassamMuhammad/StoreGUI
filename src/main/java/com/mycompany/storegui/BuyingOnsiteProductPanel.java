/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.storegui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
/**
 *
 * @author Bassam Muhammad
 */
public class BuyingOnsiteProductPanel extends JPanel {
    private final JTable table;
    DefaultTableModel model;
    private final ArrayList<Item> cart = new ArrayList<>();
    

    public BuyingOnsiteProductPanel(){
        super(new BorderLayout());
        
        model = new DefaultTableModel(new String[]{"Sr.No." , "Product ID", "Produt Name", "Price", "Qty", "Total"}, 0) {
        
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;             
            }
        }; 

        table = new JTable(model);
        table.getColumnModel().getColumn(0).setMaxWidth(40);
        table.getColumnModel().getColumn(1).setMaxWidth(150);
        table.getColumnModel().getColumn(3).setMaxWidth(100);
        table.getColumnModel().getColumn(4).setMaxWidth(40);
        table.getColumnModel().getColumn(5).setMaxWidth(100);
        
        JScrollPane scrollPane = new JScrollPane(table);
        
        add(scrollPane, BorderLayout.CENTER);
        add(new SouthPanel(), BorderLayout.SOUTH);
        add(new WestPanel(), BorderLayout.WEST);
        
        table.getModel().addTableModelListener(new TableModelListener(){
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                
                //when id is entered check items list and autofill product and cost column 
                if(column == 1){
                    if(model.getValueAt(row, column) != null){  //if id column is not null
                        boolean skip = false;
                        for(Item item : AddingOnsiteProductPanel.ITEMS){
                            try{

                                if(Long.parseLong((String)model.getValueAt(row, column)) == item.getId()){
                                    model.setValueAt(item.getName(), row, 2);
                                    model.setValueAt(item.getName(), row, 3);
                                    skip = true;
                                    break;
                                }

                            }catch(NumberFormatException ex){
                                JOptionPane.showMessageDialog(null, "Id not found", "ERROR", JOptionPane.ERROR_MESSAGE);
                                model.setValueAt(null, row, column);
                                break;
                            }
                        }
                        if(!skip){
                            JOptionPane.showMessageDialog(null, "Id not found", "ERROR", JOptionPane.ERROR_MESSAGE);
                            model.setValueAt(null, row, column);
                        }
                    }
                }
                
                //whem quantity is entered autofill total column by cost x quantity
                if(column == 4){
                    try{
                        
                        if(model.getValueAt(row, column) != null){  //if quantity column is not null
                            if(model.getValueAt(row, 1) == null)    // if id is not entered throw exception
                                throw new IllegalAccessException();
                        int quantity;
                        quantity = Integer.parseInt((String)model.getValueAt(row, column));
                        if(quantity <= 0)
                            throw new NumberFormatException();
                        
                        //multiply price and quantity and set it to total
                        model.setValueAt("" +(Double.parseDouble((String)model.getValueAt(row, 3)) *
                                Integer.parseInt((String)(model.getValueAt(row, 4)))), row, 5);
                        }
                    }catch(NumberFormatException ex){
                        JOptionPane.showMessageDialog(null, "Invalid quantity", "ERROR", JOptionPane.ERROR_MESSAGE);
                        model.setValueAt(null, row, column);
                    } catch (IllegalAccessException ex) {
                        JOptionPane.showMessageDialog(null, "ID not entered", "ERROR", JOptionPane.ERROR_MESSAGE);
                        model.setValueAt(null, row, column);
                    }
                }
            }
            
        });
        
    }
    
    private class SouthPanel extends JPanel{
        GridBagConstraints gbc;
        
        private SouthPanel(){
            super(new GridBagLayout());
            
            gbc = new GridBagConstraints();
            
            JComboBox<String> paymentMode = new JComboBox<>(new String[]{"Cash", "Card"});
            gbc.insets = new Insets(0, 0, 0, 300);
            addComponent(paymentMode, 0, 0, 1, 1);  //add to SouthPanel
            
            JButton btnTotal = new JButton("Get Total");
            addComponent(btnTotal, 0, 2, 1, 1);
            
            JLabel totalLabel = new JLabel("Total");
            gbc.insets = new Insets(0, 0, 0, 0);
            addComponent(totalLabel, 1, 0, 1, 1);
            
            JLabel paidLabel = new JLabel("Paid");
            gbc.insets = new Insets(0, 0, 0, 0);
            addComponent(paidLabel, 1, 1, 1, 1);
            
            JLabel changeLabel = new JLabel("Change");
            addComponent(changeLabel, 1, 2, 1, 1);
            
            Dimension preferredSize = new Dimension(100, 30);
            
            JTextField totalField = new JTextField("0.00");
            totalField.setPreferredSize(preferredSize);
            totalField.setEditable(false);
            totalField.setBackground(Color.WHITE);
            addComponent(totalField, 2, 0, 3, 1);
            
            JTextField paidField = new JTextField("0.00");
            paidField.setPreferredSize(preferredSize);
            paidField.setBackground(Color.WHITE);
            addComponent(paidField, 2, 1, 3, 1);
            
            JTextField changeField = new JTextField("0.00");
            changeField.setPreferredSize(preferredSize);
            changeField.setEditable(false);
            changeField.setBackground(Color.WHITE);
            addComponent(changeField, 2, 2, 3, 1);
            
            JButton btnNext = new JButton("Next Transaction");
            gbc.insets = new Insets(10, 0, 0, 5);
            addComponent(btnNext, 0, 3, 1, 1);
            
            JButton btnCancelTrans = new JButton("Cancel Transaction");
            addComponent(btnCancelTrans, 1, 3, 1, 1);
            
            btnTotal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double sum = 0;
                for(int i = 0; i < model.getRowCount(); i++){
                    sum += Double.parseDouble((String)table.getValueAt(i, 5));
                }
                totalField.setText("" + sum);
            }
        });
        
            paidField.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try{
                        double total = Double.parseDouble(totalField.getText());
                        double paid = Double.parseDouble(paidField.getText());
                        changeField.setText("" + (paid - total));
                        if(paid - total < 0)
                            throw new IllegalArgumentException();
                    }catch(NumberFormatException ex){
                        JOptionPane.showMessageDialog(null, "Enter valid price", "ERROR", JOptionPane.ERROR_MESSAGE);
                        paidField.setText("");
                    }catch(IllegalArgumentException ex){
                        JOptionPane.showMessageDialog(null, "Amount paid can't be less than total", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            
            btnNext.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {
                    try{
                        Double.parseDouble(paidField.getText());
                    }catch(NumberFormatException ex){
                        JOptionPane.showMessageDialog(null, "Invalid amount paid", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                    if(Double.parseDouble(totalField.getText()) == 0)
                        JOptionPane.showMessageDialog(null, "Complete this transaction", "ERROR", JOptionPane.ERROR_MESSAGE);
                    else if(Double.parseDouble(paidField.getText()) < Double.parseDouble(totalField.getText()))
                        JOptionPane.showMessageDialog(null, "Amount paid can't be less than total", "ERROR", JOptionPane.ERROR_MESSAGE);
                    else{
                        for(Item item : getCart())
                            OnsiteSoldPanel.SOLDITEMS.add(item);
                        
                        OnsiteSoldPanel.addToTotalSale(Double.parseDouble(totalField.getText()));
                        getCart().clear();
                        
                        int rowCount = model.getRowCount();
                        for(int i = 0; i < rowCount; i++)
                            model.removeRow(0);
                        
                        totalField.setText("0.00");
                        paidField.setText("0.00");
                        changeField.setText("0.00");
                    }
                }
            });
            
            btnCancelTrans.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {
                    int choice = JOptionPane.showConfirmDialog(null, "Cancel transaction");
                    if(choice == JOptionPane.YES_OPTION){
                        int itemsCount = getCart().size();
                        
                        for(int i = 0; i < itemsCount; i++){
                            AddingOnsiteProductPanel.ITEMS.add(getCart().remove(0));
                            model.removeRow(0);
                        }
                        totalField.setText("0.00");
                        paidField.setText("0.00");
                        changeField.setText("0.00");
                        AddingOnsiteProductPanel.sortItems(AddingOnsiteProductPanel.ITEMS);
                    }
                }
            });
            
        }
        
        
        private void addComponent(Component component,int gridx, int gridy, int gridwidth, int gridheight){
            gbc.gridx = gridx;
            gbc.gridy = gridy;
            gbc.gridwidth = gridwidth;
            gbc.gridheight = gridheight;
            add(component, gbc);
        }
    }
    
    private class WestPanel extends JPanel{
        
        public WestPanel(){
            super();
            
        JTextField textId = new JTextField();
        JTextField textQuantity = new JTextField();    
        
        Dimension preferedSize = new Dimension(100, 20);
        textId.setPreferredSize(preferedSize);
        textQuantity.setPreferredSize(preferedSize);

        JPanel subPanel1 = new JPanel();
        subPanel1.add(new JLabel("ID:"));
        subPanel1.add(textId);
        subPanel1.add(new JLabel("Quantity:"));
        subPanel1.add(textQuantity);
    
        JButton btnAdd = new JButton("Add");
        JButton btnDelete = new JButton("Delete");
        
        JPanel subPanel2 = new JPanel();
        subPanel2.add(btnAdd);
        subPanel2.add(Box.createRigidArea(new Dimension(10, 0)));
        subPanel2.add(btnDelete);        
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JButton btnBack = new JButton("Back");
        
        panel.add(subPanel1);
        panel.add(subPanel2);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(btnBack);
        
        add(panel);
        
        
        btnAdd.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {

                String[] row = new String[2];
                row[0] = textId.getText();
                row[1] = textQuantity.getText();

                if(isFollowProtocol(row)){
                    if(isAvailable(row)){
                        for(Item item : getCart()){
                            if(Long.parseLong(row[0]) == item.getId()){
                                String[] data = {
                                    "" + (model.getRowCount()+1),
                                    row[0],
                                    item.getName(),
                                    "" + item.getCost(),
                                    row[1],
                                    "" + (item.getCost() * Integer.parseInt(row[1]))

                                };

                                model.addRow(data);
                                break;
                            }
                        }
                    }
                    else
                        JOptionPane.showMessageDialog(null, "ID not found!!", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }

            private boolean isFollowProtocol(String[] row) {
                for(int i = 1; i < row.length; i++){
                    if(row[i].equals("")){
                        JOptionPane.showMessageDialog(null, "All fields should be filled", "ERROR", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                }

                try{
                    long id = Long.parseLong(row[0]);
                    if(id <= 0)
                        throw new NumberFormatException();
                }catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "Invalid ID!!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                try{
                    int quantity = Integer.parseInt(row[1]);
                    if(quantity <= 0)
                        throw new NumberFormatException();
                }catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "Invalid Quantity!!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                return true;
            }

            private boolean isAvailable(String[] row){
                for(Item item : AddingOnsiteProductPanel.ITEMS){
                    if(Long.parseLong(row[0]) == item.getId()){
                        if(Integer.parseInt(row[1]) < item.getQuantity()){  //if quantity required is less than available quantity
                            item.setQuantity(item.getQuantity() - Integer.parseInt(row[1]));//set new quantity in stock
                            getCart().add(new Item(item.getName(), item.getCost(), item.getId(), Integer.parseInt(row[1])));
                            return true;
                        }

                        else if(Integer.parseInt(row[1]) == item.getQuantity()){ // if quantity req is equal to availble quantity
                            AddingOnsiteProductPanel.ITEMS.remove(item);// remove from stock
                            getCart().add(item);
                            return true;
                        }
                        else{   // if quantity req is greater than availble quantity
                            int choice = JOptionPane.showConfirmDialog(null, "Quantity greater than max available\n"
                                    + "Set to max available?", "ERROR", JOptionPane.YES_NO_OPTION);
                            if(choice == JOptionPane.YES_OPTION){
                                row[1] = "" + item.getQuantity();
                                AddingOnsiteProductPanel.ITEMS.remove(item);
                                getCart().add(item);
                                return true; 
                            }
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
                    AddingOnsiteProductPanel.ITEMS.add(getCart().remove(i));
                    AddingOnsiteProductPanel.sortItems(AddingOnsiteProductPanel.ITEMS);
                    // remove a row from jtable
                    model.removeRow(i);
                    //update Sr. No.
                    for(int j = i; j < model.getRowCount(); j++){
                        model.setValueAt("" + (j+1), j, 0);
                    }
                    AddingOnsiteProductPanel.sortItems(AddingOnsiteProductPanel.ITEMS);
                }
                else
                    JOptionPane.showMessageDialog(null, "Choose a row", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(model.getRowCount() > 0){
                    int choice = JOptionPane.showConfirmDialog(null, "This transaction will be cancelled!!! Exit?");
                    if(choice == JOptionPane.YES_OPTION){
                        for(int i = 0; i < getCart().size(); i++)
                            AddingOnsiteProductPanel.ITEMS.add(getCart().remove(0));
                        
                        MainPanel.setSubContainer(new OnsiteOptionPanel());
                    }
                }
                else
                    MainPanel.setSubContainer(new OnsiteOptionPanel());
                    
                
                
            }
        });
        }
    }
    
    private ArrayList<Item> getCart(){
        return cart;
    }
}
