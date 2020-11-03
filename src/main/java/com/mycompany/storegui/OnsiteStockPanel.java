/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.storegui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Bassam Muhammad
 */
public class OnsiteStockPanel extends JPanel {
    private final JTable table;
    
    public OnsiteStockPanel(){
        super(new BorderLayout());
        AddingOnsiteProductPanel.sortItems(AddingOnsiteProductPanel.ITEMS);
        Object[][] data = new Object[AddingOnsiteProductPanel.ITEMS.size()][5];
        
        //fill data with data from item arrlist
        for(int i = 0; i < data.length; i++){
            for(int j = 0; j < data[i].length; j++){
                switch(j){
                    case 0:
                        data[i][j] = i+1;
                        break;
                        
                    case 1:
                        data[i][j] = AddingOnsiteProductPanel.ITEMS.get(i).getId();
                        break;
                        
                    case 2:
                        data[i][j] = AddingOnsiteProductPanel.ITEMS.get(i).getName();
                        break;
                        
                    case 3:
                        data[i][j] = AddingOnsiteProductPanel.ITEMS.get(i).getCost();
                        break;
                        
                    case 4:
                        data[i][j] = AddingOnsiteProductPanel.ITEMS.get(i).getQuantity();
                        break;
                }
                
            }
        }
        
            //enter data into model for table
            DefaultTableModel model = new DefaultTableModel(data, new String[]{"Sr.No." , "Product ID", "Produt Name", "Price", "Qty"}) {
        
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
        
        JScrollPane scrollPane = new JScrollPane(table);
        
        JButton back = new JButton("Back");
                
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainPanel.setSubContainer(new OnsiteOptionPanel());
            }
        });
        
        add(scrollPane, BorderLayout.CENTER);
        add(back , BorderLayout.SOUTH);
        
    }
}
