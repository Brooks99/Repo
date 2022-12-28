package action;

import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.openapi.uml.ModelElementsManager;
import com.nomagic.magicdraw.openapi.uml.PresentationElementsManager;
import com.nomagic.magicdraw.ui.browser.actions.DefaultBrowserAction;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package;

import java.awt.event.ActionEvent;

public class BrowserAction extends DefaultBrowserAction {
    private final Project project;

    private final ModelElementsManager manager;

    private final PresentationElementsManager presentationManager;

    public BrowserAction(String id, String name) {
        super(id, name, null, null);

        this.project = Application.getInstance().getProject();
        this.manager = ModelElementsManager.getInstance();
        this.presentationManager = PresentationElementsManager.getInstance();

    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        var tree = getTree();

        var selectedNode = tree.getSelectedNode();

        if (selectedNode != null) {
            if (selectedNode.getUserObject() instanceof Package) {
                var parentPackage = (Package) selectedNode.getUserObject();

                // Call execute on new class ModelStructureGenerator
                var modelStructureGenerator = new ModelStructureGenerator();
                modelStructureGenerator.execute(parentPackage);

                var modelDiagramGenerator = new ModelDiagramGenerator();
                modelDiagramGenerator.execute(parentPackage);
            }
        }

    }
}
