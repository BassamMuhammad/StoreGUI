/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.storegui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author Bassam Muhammad
 */
public class MainMenuPanel extends JPanel{
    JButton btnOnline;
    JButton btnOnsite;
    JLabel label;
    GridBagLayout layout;
    GridBagConstraints constraints; 
    
    public MainMenuPanel(){
        super();
        layout = new GridBagLayout();
        constraints = new GridBagConstraints();
        setLayout(new GridBagLayout());
    
        label = new JLabel("Select the version you want to use", SwingConstants.CENTER);
        label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        constraints.fill = GridBagConstraints.HORIZONTAL;
        addComponent(label, 0,0,3,1);
        
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 15);
        
        btnOnsite = new JButton("Onsite version");
        btnOnsite.setFont(font);
        constraints.fill = GridBagConstraints.NONE;
        constraints.insets = new Insets(10, 0, 0, 0);
        addComponent(btnOnsite, 0,2,1,1);
        
        btnOnline = new JButton("Online version"); 
        btnOnline.setFont(font);
        constraints.insets = new Insets(10, 10, 0, 0);
        addComponent(btnOnline, 2,2,1,1);
        
        ActionHandler handler = new ActionHandler();
        btnOnline.addActionListener(handler);
        btnOnsite.addActionListener(handler);
      
    
    }    
    
    private void addComponent(Component component,int gridx, int gridy, int gridwidth, int gridheight){
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.gridwidth = gridwidth;
        constraints.gridheight = gridheight;
        add(component, constraints);
    }
    
    private class ActionHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent event){
                if(event.getSource() == btnOnsite)
                    MainPanel.setSubContainer(new OnsiteOptionPanel());
                
                else{
                    MainPanel.setSubContainer(new OnlineSelectionScrollPane());
                }
                    
       }
    }
}
