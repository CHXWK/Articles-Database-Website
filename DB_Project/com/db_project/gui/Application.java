package com.db_project.gui;

import java.sql.SQLException;
import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.db_project.backend.Program;
import com.db_project.gui.Panels.*;

public class Application extends JFrame
{
    public static void main(String[] args)
    {
        new Application();
    }

    // Constants
    public final static int LOGIN_PANEL = 0;
    public final static int MAIN_MENU_PANEL = 1;
    public final static int TABLE_VIEW = 2;
    private final static String[] panelNames= {"login","main_menu","table"};
    private final static int WIDTH = 800;
    private final static int HEIGHT = 800;

    // Variables
    private Program program;
    private JPanel[] panels;
    private CardLayout cd;

    /**
     * Setup JFrame UI and Setup Program
     */
    public Application ()
    {
        initComponents();
    }

    /**
     * Transitions the frame's panel from the fromPanel to the toPanel
     * @param fromPanel EX: Application.LOGIN_PANEL
     * @param toPanel EX: Application.MAIN_MENU_PANEL
     */
    public void transition (int toPanel)
    {
        cd.show(this.getContentPane(), panelNames[toPanel]);
    }
    
    /**
     * Transitions the frame's panel from the fromPanel to the toPanel
     * @param fromPanel EX: Application.LOGIN_PANEL
     * @param toPanel EX: Application.MAIN_MENU_PANEL
     * @param carryQuery query to be carrried over to toFrame
     */
    public void transition (int toPanel, String sql, String table)
    {
        cd.show(this.getContentPane(), panelNames[toPanel]);
        TableView tv = (TableView) panels[toPanel];
        tv.receiveQuery(sql, table);
    }

    private void initComponents ()
    {
        try
        {
            program = new Program();
        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null, "Unable to connect to server. Closing program.\n" + e.getMessage(),
                "Connection Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        panels = new JPanel[3];
        panels[0] = new LoginPanel(program);
        panels[1] = new MainMenuPanel();
        if (!program.hasAdminPrivilage())
        {
            MainMenuPanel mmp = (MainMenuPanel) panels[1];
            mmp.removeWebAdmin();
        }
        panels[2] = new TableView(program);

        cd = new CardLayout();
        this.getContentPane().setLayout(cd);
        for (int i = 0; i < panels.length; i++)
        {
            this.getContentPane().add(panels[i], panelNames[i]);
        }

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WIDTH, HEIGHT);
        this.setResizable(false);
        this.setVisible(true);
    }
}
