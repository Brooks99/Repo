package action;

import com.nomagic.magicdraw.core.Application;


// This class write a message to the notification window.
public class OutMsg {

    public static void disp(String s) {

        String msg = "STS msg. ++++ " + s + " ++++";
        //NotificationManager.getInstance().showNotification(new Notification("notificationID", msg, null));
        Application.getInstance().getGUILog().log(msg);

    }
}
