/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.storegui;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Bassam Muhammad
 */
public class OnsiteSoldPanel extends JPanel {
    private final JTable table;
    public static final ArrayList<Item> SOLDITEMS = new ArrayList<>();
    private static double totalSale = 0;
       
    public OnsiteSoldPanel(){
        super(new BorderLayout());
        AddingOnsiteProductPanel.sortItems(SOLDITEMS);
        Object[][] data = new Object[SOLDITEMS.size()][5];
        
        //fill data with data from arrlist
        for(int i = 0; i < data.length; i++){
            for(int j = 0; j < data[i].length; j++){
                switch(j){
                    case 0:
                        data[i][j] = i+1;   //sr.no.
                        break;
                        
                    case 1:
                        data[i][j] = SOLDITEMS.get(i).getId();      //id
                        break;
                        
                    case 2:
                        data[i][j] = SOLDITEMS.get(i).getName();    //name
                        break;
                        
                    case 3:
                        data[i][j] = SOLDITEMS.get(i).getCost();    //cost
                        break;
                        
                    case 4:
                        data[i][j] = SOLDITEMS.get(i).getQuantity();    //quantity
                        break;
                }
                
            }
        }
        
            //set data into model for table
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
                
        JLabel totalLabel = new JLabel("Total Sale: " + totalSale);
        totalLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
        
        add(scrollPane, BorderLayout.CENTER);
        add(totalLabel , BorderLayout.SOUTH);
        add(back , BorderLayout.NORTH);
        
    }
    
      public static double getTotalSale() {
        return totalSale;
    }

    public static void addToTotalSale(double totalSale) {
        OnsiteSoldPanel.totalSale += totalSale;
    }
}
