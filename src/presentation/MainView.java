package presentation;

import domain.*;
import persistence.EdgeSerializer;
import persistence.LabelSerializer;
import persistence.NodeSerializer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

import static domain.NodeType.*;

/**
 * Created by Magi on 10/05/2016.
 */
public class MainView {

    public int Clikeds;

    private JFrame viewFrame = new JFrame("MainFrame");
    private JPanel mainPanel = new JPanel();
    private JButton labelButton = new JButton("Label");
    private JButton termButton = new JButton("Term");
    private JButton conferenceButton = new JButton("Conference");
    private JButton authorButton = new JButton("Author");
    private JButton paperButton = new JButton("Paper");

    private JButton addEleButton = new JButton("Add Element");
    //^^Afergir button al panel de buttons, pero no es sempre visible
    private JPanel buttonsPanel = new JPanel();

    private JTextField busqueda = new JTextField("Type to search");
    private JButton searchButton = new JButton("Search");
    //^^ Fer visible nomes quan les busquedes siguin possibles.

    private JTextField relationalSearchOrigin = new JTextField("From");
    private JTextField relationalSearchDestination = new JTextField("To");

    private JMenuBar menubar = new JMenuBar();
    private JMenu menuFile = new JMenu("File");
    private JMenuItem New = new JMenuItem("New Graph");
    private JMenuItem Export = new JMenuItem("Export database");
    private JMenuItem Import = new JMenuItem("Import database");

    public MainView(){
        initialize_components();
    }

    private void initialize_components() {
        initialize_mainPanel();
        initialize_viewFrame();
        initialize_Buttons();
        initialize_menuView();
        listeners_plz();

    }

    private void one_clicked(boolean active){
        if(active){
            addEleButton.setVisible(true);
            searchButton.setVisible(true);
            busqueda.setVisible(true);
        }
        else{
            addEleButton.setVisible(false);
            searchButton.setVisible(false);
            busqueda.setVisible(false);
        }
    }



    private void listeners_plz() {

        authorButton.addActionListener(new ActionListener() {
            boolean clicked = false;
            public void actionPerformed(ActionEvent e) {
                //DESCLIKEAR
                if(clicked == true) {
                    authorButton.setBackground(Color.cyan);
                    clicked = false;
                    --Clikeds;
                    if(Clikeds <= 1){
                        more_clicked(false);
                    }
                    if(Clikeds == 0) one_clicked(false);
                    if(Clikeds == 1) one_clicked(true);
                //CLIKEAR
                } else {
                    clicked = true;
                    authorButton.setBackground(Color.blue);

                    //Búsqueda relacional
                    if(Clikeds >= 1){
                        one_clicked(false);
                        more_clicked(true);
                    }
                    //Búsqueda simple
                    else {
                        one_clicked(true);
                    }
                    ++Clikeds;
                }
            }
        });

        labelButton.addActionListener(new ActionListener() {
            boolean clicked = false;
            public void actionPerformed(ActionEvent e) {
                //DESCLIKEAR
                if(clicked == true) {
                    labelButton.setBackground(Color.cyan);
                    clicked = false;
                    --Clikeds;
                    if(Clikeds <= 1){
                        more_clicked(false);
                    }
                    if(Clikeds == 0) one_clicked(false);
                    if(Clikeds == 1) one_clicked(true);
                    //CLIKEAR
                } else {
                    clicked = true;
                    labelButton.setBackground(Color.blue);

                    //Búsqueda relacional
                    if(Clikeds >= 1){
                        one_clicked(false);
                        more_clicked(true);
                    }
                    //Búsqueda simple
                    else {
                        one_clicked(true);
                    }
                    ++Clikeds;
                }
            }
        });

        paperButton.addActionListener(new ActionListener() {
            boolean clicked = false;
            public void actionPerformed(ActionEvent e) {
                //DESCLIKEAR
                if(clicked == true) {
                    paperButton.setBackground(Color.cyan);
                    clicked = false;
                    --Clikeds;
                    if(Clikeds <= 1){
                        more_clicked(false);
                    }
                    if(Clikeds == 0) one_clicked(false);
                    if(Clikeds == 1) one_clicked(true);
                    //CLIKEAR
                } else {
                    clicked = true;
                    paperButton.setBackground(Color.blue);

                    //Búsqueda relacional
                    if(Clikeds >= 1){
                        one_clicked(false);
                        more_clicked(true);
                    }
                    //Búsqueda simple
                    else {
                        one_clicked(true);
                    }
                    ++Clikeds;
                }
            }
        });

        conferenceButton.addActionListener(new ActionListener() {
            boolean clicked = false;
            public void actionPerformed(ActionEvent e) {
                //DESCLIKEAR
                if(clicked == true) {
                    conferenceButton.setBackground(Color.cyan);
                    clicked = false;
                    --Clikeds;
                    if(Clikeds <= 1){
                        more_clicked(false);
                    }
                    if(Clikeds == 0) one_clicked(false);
                    if(Clikeds == 1) one_clicked(true);
                    //CLIKEAR
                } else {
                    clicked = true;
                    conferenceButton.setBackground(Color.blue);

                    //Búsqueda relacional
                    if(Clikeds >= 1){
                        one_clicked(false);
                        more_clicked(true);
                    }
                    //Búsqueda simple
                    else {
                        one_clicked(true);
                    }
                    ++Clikeds;
                }
            }
        });

        termButton.addActionListener(new ActionListener() {
            boolean clicked = false;
            public void actionPerformed(ActionEvent e) {
                //DESCLIKEAR
                if(clicked == true) {
                    termButton.setBackground(Color.cyan);
                    clicked = false;
                    --Clikeds;
                    if(Clikeds <= 1){
                        more_clicked(false);
                    }
                    if(Clikeds == 0) one_clicked(false);
                    if(Clikeds == 1) one_clicked(true);
                    //CLIKEAR
                } else {
                    clicked = true;
                    termButton.setBackground(Color.blue);

                    //Búsqueda relacional
                    if(Clikeds >= 1){
                        one_clicked(false);
                        more_clicked(true);
                    }
                    //Búsqueda simple
                    else {
                        one_clicked(true);
                    }
                    ++Clikeds;
                }
            }
        });



    }

    private void more_clicked(boolean b) {
        if(b){
            relationalSearchDestination.setVisible(true);
            relationalSearchOrigin.setVisible(true);
            searchButton.setVisible(true);
        } else {
            relationalSearchDestination.setVisible(false);
            relationalSearchOrigin.setVisible(false);
            searchButton.setVisible(false);
        }
    }

    private void initialize_menuView() {
        menuFile.add(New);
        menuFile.add(Export);
        menuFile.add(Import);
        menubar.add(menuFile);
        viewFrame.setJMenuBar(menubar);
    }

    private void initialize_mainPanel() {
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(buttonsPanel);
    }

    private void initialize_Buttons() {
        buttonsPanel.setLayout(null);
        buttonsPanel.add(paperButton);
        paperButton.setBounds(300,335, 100,30);
        paperButton.setBackground(Color.cyan);
        buttonsPanel.add(conferenceButton, BorderLayout.WEST);
        conferenceButton.setBounds(475, 335, 100, 30);
        conferenceButton.setBackground(Color.cyan);
        buttonsPanel.add(termButton, BorderLayout.SOUTH);
        termButton.setBounds(300, 510, 100, 30);
        termButton.setBackground(Color.cyan);
        buttonsPanel.add(authorButton, BorderLayout.EAST);
        authorButton.setBounds(125, 335, 100, 30);
        authorButton.setBackground(Color.cyan);
        buttonsPanel.add(labelButton, BorderLayout.NORTH);
        labelButton.setBounds(300, 125, 100, 30);
        labelButton.setBackground(Color.cyan);
        buttonsPanel.add(addEleButton);
        addEleButton.setBounds(525, 175, 125, 30);
        addEleButton.setVisible(false);
        buttonsPanel.add(busqueda);
        busqueda.setBounds(125, 590, 375, 30);
        busqueda.setVisible(false);
        buttonsPanel.add(searchButton);
        searchButton.setBounds(560, 590, 100, 30);
        searchButton.setVisible(false);
        buttonsPanel.add(relationalSearchOrigin);
        relationalSearchOrigin.setBounds(125, 590, 172, 30);
        relationalSearchOrigin.setVisible(false);
        buttonsPanel.add(relationalSearchDestination);
        relationalSearchDestination.setBounds(327, 590, 170, 30);
        relationalSearchDestination.setVisible(false);
    }

    private void initialize_viewFrame(){
        viewFrame.setMinimumSize(new Dimension(700,700));
        viewFrame.setPreferredSize(viewFrame.getMinimumSize());
        viewFrame.setResizable(false);
        viewFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel contentPane = (JPanel) viewFrame.getContentPane();
        contentPane.add(mainPanel);
    }


    public void setEnable() {
        viewFrame.pack();
        viewFrame.setVisible(true);
    }
}
