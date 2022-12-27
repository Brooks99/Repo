package action;

import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.ui.actions.DefaultDiagramAction;
import com.nomagic.ui.ScalableImageIcon;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.impl.ElementsFactory;

import java.awt.event.ActionEvent;
import java.util.Iterator;

public class DiagramAction extends DefaultDiagramAction {

    private ElementsFactory factory;


    public DiagramAction(String id, String name) {
        super(id, name, null, null);

        var url = getClass().getResource("/icon.png");
        setSmallIcon(new ScalableImageIcon(url));

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        var presentationElements = getSelected();

        if (presentationElements != null && presentationElements.size() > 0) {

            // This gets the first item in the selection of diagram objects.
            var presentationElement = presentationElements.get(0);

            var name = presentationElement.getElement().getHumanName();

            Application.getInstance().getGUILog().showMessage("Selected element: \n"
                    + name);

            Element el = presentationElement.getElement();
           var hname  ="";
            if (el.hasOwnedElement()) {
                for (Iterator it = el.getOwnedElement().iterator(); it.hasNext();) {
                    Element ownedElement = (Element) it.next();
                    hname = ownedElement.getHumanName();
                    OutMsg.disp(hname + " --- hname ");

                    var taggedValueClassValue = factory.getElementTaggedValueClass();

                }
            }
        }
    }
}
