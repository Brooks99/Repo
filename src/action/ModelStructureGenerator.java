package action;

import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.openapi.uml.ModelElementsManager;
import com.nomagic.magicdraw.openapi.uml.ReadOnlyElementException;
import com.nomagic.magicdraw.openapi.uml.SessionManager;
import com.nomagic.magicdraw.uml.Finder;
import com.nomagic.uml2.ext.jmi.helpers.CoreHelper;
import com.nomagic.uml2.ext.jmi.helpers.ModelHelper;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.*;
import com.nomagic.uml2.impl.ElementsFactory;


public class ModelStructureGenerator {

    private final Project project;
    private final ElementsFactory factory;
    private final ModelElementsManager manager;


    public ModelStructureGenerator() {
        this.project = Application.getInstance().getProject();
        this.factory = project.getElementsFactory();
        this.manager = ModelElementsManager.getInstance();
    }

    public void execute(Package parentPackage) {
        try {
            SessionManager.getInstance().createSession(project, "Creating model elements");

            //createModelElements(parentPackage);
            createInstanceSpecification(parentPackage);

            SessionManager.getInstance().closeSession(project);

        } catch (Exception e) {
            e.printStackTrace();
            Application.getInstance().getGUILog().showMessage("Exception occured: " + e.getMessage());
            SessionManager.getInstance().cancelSession(project);
        }
    }

    private void createModelElements(Package parentPackage) throws ReadOnlyElementException {

        var firstClass = createClass(parentPackage, "First class");

        addProperty(firstClass);

        addOperation(firstClass);

        var enumeration = createEnumeration(parentPackage);

        var secondClass = createClass(parentPackage, "Second class");

        addStereotype(secondClass);

        addEnumeration(secondClass, enumeration);

        createRelation(parentPackage, firstClass, secondClass);
    }

    private InstanceSpecification createInstanceSpecification(Package parentPackage) throws ReadOnlyElementException {

        InstanceSpecification mdInstanceSpecification = null;

        for (SqlRecord sr : SqlRecords.getRecords()) {

            // Create an instance of a class
            mdInstanceSpecification = factory.createInstanceSpecificationInstance();

            mdInstanceSpecification.setVisibility(VisibilityKindEnum.getByName("public"));

            mdInstanceSpecification.setOwner(parentPackage);

            // Add the name for the part from the db.
            mdInstanceSpecification.setName(sr.getpName());

            OutMsg.disp(mdInstanceSpecification.getHumanName() + " Test ");

            // Add the Type property
            addInstanceProperty(mdInstanceSpecification);

            // set slot values
            setSlotValue(mdInstanceSpecification, sr.getpID(), sr.getMax1(), sr.getMin1());

            manager.addElement(mdInstanceSpecification, parentPackage);
        }
        return mdInstanceSpecification;
    }


    private void addInstanceProperty(InstanceSpecification mdIs) throws ReadOnlyElementException {


        var ele1 = (Classifier) Finder.byQualifiedName()
                .find(project, "08. Work Area::Part Class::Non Volatile Memory Part Class");

        mdIs.getClassifier().add(ele1);
        //ModelHelper.setSlotValue();
        ModelHelper.createSlotsForDefaultValues(mdIs, ele1, true);
    }


    private void setSlotValue(InstanceSpecification mdIs, String sID, String sMax, String sMin) {

        var literalReal = factory.createLiteralRealInstance();

        var stringType = (Type) Finder.byQualifiedName()
                .find(project, "UML Standard Profile::UML2 Metamodel::PrimitiveTypes::String");


        if (!mdIs.getSlot().isEmpty()) {
            var slotName = "";
            var cnt = 0;
            for (Slot specSlot : mdIs.getSlot()) {
                slotName = specSlot.getDefiningFeature().getName();
                OutMsg.disp("Setting value for slot " + slotName);
                switch (cnt) {

                    case 0:
                        ModelHelper.setSlotValue(specSlot, sID);
                        break;
                    case 1:
                        ModelHelper.setSlotValue(specSlot, sMax);
                        break;
                    case 2:
                        ModelHelper.setSlotValue(specSlot, sMin);
                        break;
                    case 3:
                        OutMsg.disp("The slot cannot be assigned a value.(setSlotValue)");
                }
                ++cnt;
            }
        }
//        StructuralFeature attribute;
//
//        slot.setDefiningFeature(attribute);
//        ValueSpecification valueSpecification = ModelHelper.createValueSpecification(
//                Project.getProject(slot),
//                stringType,  //null
//                value,
//                Collections.emptySet());  //slot.getValue().add(value_spec);

//        if (valueSpecification != null)
//        {
//            OutMsg.disp("hey");
//            slot.getValue().add(valueSpecification);
//        }
    }


    private Class createClass(Package parentPackage, String name) throws ReadOnlyElementException {

        var mdClass = factory.createClassInstance();

        mdClass.setName(name);

        manager.addElement(mdClass, parentPackage);

        return mdClass;
    }


    private void addProperty(Class mdClass) throws ReadOnlyElementException {

        var stringType = (Type) Finder.byQualifiedName()
                .find(project, "UML Standard Profile::UML2 Metamodel::PrimitiveTypes::String");

        var r = Finder.byQualifiedName()
                .find(project, "SysMl::Libraries::PrimitiveValueTypes::Real");

        var property = factory.createPropertyInstance();

        property.setName("myProperty");

        property.setType(stringType);

        CoreHelper.setMultiplicity(0, 1, property);

        manager.addElement(property, mdClass);

    }

    private void addOperation(Class mdClass) throws ReadOnlyElementException {
        var operation = factory.createOperationInstance();
        operation.setName("myOperation");
        manager.addElement(operation, mdClass);
    }

    private void addStereotype(Class mdClass) throws ReadOnlyElementException {

        var profile = StereotypesHelper.getProfile(project, "My profile");
        var stereotype = StereotypesHelper.getStereotype(project, "myStereotype", profile);

        StereotypesHelper.addStereotype(mdClass, stereotype);
        StereotypesHelper.setStereotypePropertyValue(mdClass, stereotype, "name", "hello");
    }

    private void createRelation(Package parentPackage, Class firstClass, Class secondClass) throws ReadOnlyElementException {
        var dependency = factory.createDependencyInstance();
        dependency.setName("myDependency");
        CoreHelper.setSupplierElement(dependency, firstClass);
        CoreHelper.setClientElement(dependency, secondClass);
        manager.addElement(dependency, parentPackage);
    }

    private Enumeration createEnumeration(Package parentPackage) throws ReadOnlyElementException {

        var myEnum = factory.createEnumerationInstance();

        myEnum.setName("My enumeration");

        var values = new String[]{"a", "b", "c"};

        for (var value : values) {


            var literal = factory.createEnumerationLiteralInstance();

            var valueSpecification = factory.createLiteralStringInstance();

            valueSpecification.setValue(value);

            literal.setSpecification(valueSpecification);

            literal.setName(value);

            literal.setEnumeration(myEnum);
        }

        manager.addElement(myEnum, parentPackage);

        return myEnum;
    }

    private void addEnumeration(Class mdClass, Enumeration enumeration) throws ReadOnlyElementException {
        var property = factory.createPropertyInstance();
        property.setName("myEnumeration");
        property.setType(enumeration);
        CoreHelper.setMultiplicity(1, 1, property);

        manager.addElement(property, mdClass);
    }

}
