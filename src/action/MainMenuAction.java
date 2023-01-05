package action;

import com.nomagic.magicdraw.actions.MDAction;

import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;

public class MainMenuAction extends MDAction {

    public MainMenuAction(String id, String name) {
        super(id, name, null, null);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        OutMsg.disp("Trying to connection to the CSEP Parts Database.");

        PartConnection dbCon = new PartConnection();

        Connection con = dbCon.connect();

        PartQuery partQuery = new PartQuery();

        try {
            
            partQuery.viewResult(con);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
