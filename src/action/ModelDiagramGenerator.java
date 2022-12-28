package action;

import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.openapi.uml.ModelElementsManager;
import com.nomagic.magicdraw.openapi.uml.PresentationElementsManager;
import com.nomagic.magicdraw.openapi.uml.ReadOnlyElementException;
import com.nomagic.magicdraw.openapi.uml.SessionManager;
import com.nomagic.magicdraw.properties.ColorProperty;
import com.nomagic.magicdraw.properties.PropertyID;
import com.nomagic.magicdraw.properties.PropertyManager;
import com.nomagic.magicdraw.uml.DiagramTypeConstants;
import com.nomagic.magicdraw.uml.Finder;
import com.nomagic.magicdraw.uml.symbols.DiagramPresentationElement;
import com.nomagic.magicdraw.uml.symbols.shapes.ShapeElement;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package;

import java.awt.*;

public class ModelDiagramGenerator {

    private final Project project;

    private final ModelElementsManager manager;

    private final PresentationElementsManager presentationManager;

    public ModelDiagramGenerator() {
        this.project = Application.getInstance().getProject();
        this.manager = ModelElementsManager.getInstance();
        this.presentationManager = PresentationElementsManager.getInstance();
    }

    public void execute(Package parentPackage) {
        SessionManager.getInstance().createSession(project, "Creating diagram and presentation elements for lesson 4");

        try {
            createDiagram(parentPackage);
            SessionManager.getInstance().closeSession(project);
        } catch (Exception e) {
            SessionManager.getInstance().cancelSession(project);
        }
    }

    private void createDiagram(Package parentPackage) throws ReadOnlyElementException {

        var diagram = manager.createDiagram(DiagramTypeConstants.UML_CLASS_DIAGRAM, parentPackage);
        diagram.setName("My diagram");

        var diagramPresentation = project.getDiagram(diagram);

        diagramPresentation.open();

        OutMsg.disp("in createDiagram");

        createDiagramElements(parentPackage, diagramPresentation);
    }

    private void createDiagramElements(Package parentPackage, DiagramPresentationElement diagramPresentation) throws ReadOnlyElementException {
        var firstClass = Finder.byName().find(parentPackage,
                com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class.class,
                "First class");
        OutMsg.disp("OK");

        var firstClassShape = presentationManager.createShapeElement(firstClass, diagramPresentation);
        presentationManager.reshapeShapeElement(firstClassShape, new Rectangle(50, 50, 200, 300));
        setFillColorToRed(firstClassShape);

        var secondClass = Finder.byName().find(parentPackage,
                com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class.class,
                "Second class");

        var secondClassShape = presentationManager.createShapeElement(secondClass, diagramPresentation);
        presentationManager.reshapeShapeElement(secondClassShape, new Rectangle(350, 50, 200, 300));

        //var dependency = Finder.byName().find(parentPackage,
               // com.nomagic.uml2.ext.magicdraw.classes.mddependencies.Dependency.class,
               // "myDependency");

        //var dependencyPath = presentationManager.createPathElement(dependency, secondClassShape, firstClassShape);
    }

    private void setFillColorToRed(ShapeElement shapeElement) throws ReadOnlyElementException {
        var propertyManager = new PropertyManager();
        var colorProperty = (ColorProperty)shapeElement.getProperty(PropertyID.FILL_COLOR);
        var clonedColorProperty = colorProperty.clone();
        clonedColorProperty.setValue(new Color(255,0,0));
        propertyManager.addProperty(clonedColorProperty);
        presentationManager.setPresentationElementProperties(shapeElement, propertyManager);
    }


}
