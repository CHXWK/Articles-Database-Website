package com.db_project.gui.Panels;

public interface SQLpage {
    
    /**
     * receive query accepts a query string from the transition method when this page is transitioned to.
     * This method will run query and display the results by default. 
     * @param sql The query to be ran
     */
    public void receiveQuery (String sql, String table);

    /**
     * This method is called when the current page is going to transition to another page.
     * It accepts an sql query and gives it to Application.transition(args).
     * It accepts the toPanel that will designate the panel to be transitioned to.
     * @param sql Sql Query
     */
    public void transition (int toPanel, String sql, String table);
}
