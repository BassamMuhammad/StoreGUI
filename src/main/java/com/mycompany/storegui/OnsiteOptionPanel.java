/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.storegui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Bassam Muhammad
 */
public class OnsiteOptionPanel extends JPanel{
    private final JRadioButton[] radioButtons = new JRadioButton[4];
    private final ButtonGroup radioGroup = new ButtonGroup();
    private final JButton back = new JButton("Back");
    
    
    public OnsiteOptionPanel(){
        super(new BorderLayout());
        
        JPanel subPanel = new JPanel();
        subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));
        
        initialiseRadioButtons();
        
        for(JRadioButton radioButton : radioButtons){
            radioButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
        }        
        
        subPanel.add(Box.createRigidArea(new Dimension(150, 150))); // give space from top
        
        for(JRadioButton radioButton : radioButtons){
            subPanel.add(radioButton);
        } 
        
        add(subPanel, BorderLayout.CENTER);
        add(back, BorderLayout.SOUTH);
        
        for(JRadioButton radioButton : radioButtons){
            radioGroup.add(radioButton);
        } 

                //Event Handling
    
        radioButtons[0].addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                MainPanel.setSubContainer(new AddingOnsiteProductPanel());
            }
        });
        
        radioButtons[1].addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                MainPanel.setSubContainer(new BuyingOnsiteProductPanel());
            }
        });
        
        radioButtons[2].addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                MainPanel.setSubContainer(new OnsiteStockPanel());
            }
        });
        
        radioButtons[3].addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                MainPanel.setSubContainer(new OnsiteSoldPanel());
            }
        });
        
        back.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                MainPanel.setSubContainer(new MainMenuPanel());
            }
        });       
    }
        private void initialiseRadioButtons(){
            
            radioButtons[0] = new JRadioButton("Click to add products to system");
            radioButtons[1] = new JRadioButton("Click to buy products");
            radioButtons[2] = new JRadioButton("Click to check stock");
            radioButtons[3] = new JRadioButton("Click to check sold products");
    
        }
    
    
}
