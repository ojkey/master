# NumberField7 Add-on component for Vaadin 7

## Online demo

Try the add-on demo at http://ojkey.jelastic.servint.net/demo/#!numberfield7

## Download release

## Building and running demo

git clone <url of the VaadinWidgets repository>
mvn clean install
cd demo
mvn jetty:run

To see the demo, navigate to http://localhost:8080/

## Development with Eclipse IDE

For further development of this add-on, the following tool-chain is recommended:
- Eclipse IDE
- m2e wtp plug-in (install it from Eclipse Marketplace)
- Vaadin Eclipse plug-in (install it from Eclipse Marketplace)
- JRebel Eclipse plug-in (install it from Eclipse Marketplace)
- Chrome browser

### Importing project

Choose File > Import... > Existing Maven Projects

Note that Eclipse may give "Plugin execution not covered by lifecycle configuration" errors for pom.xml. Use "Permanently mark goal resources in pom.xml as ignored in Eclipse build" quick-fix to mark these errors as permanently ignored in your project. Do not worry, the project still works fine. 

### Debugging server-side

If you have not already compiled the widgetset, do it now by running vaadin:install Maven target for numberfield-root project.

If you have a JRebel license, it makes on the fly code changes faster. Just add JRebel nature to your numberfield-demo project by clicking project with right mouse button and choosing JRebel > Add JRebel Nature

To debug project and make code modifications on the fly in the server-side, right-click the numberfield-demo project and choose Debug As > Debug on Server. Navigate to http://localhost:8080/numberfield-demo/ to see the application.

### Debugging client-side

The most common way of debugging and making changes to the client-side code is dev-mode. To create debug configuration for it, open numberfield-demo project properties and click "Create Development Mode Launch" button on the Vaadin tab. Right-click newly added "GWT development mode for numberfield-demo.launch" and choose Debug As > Debug Configurations... Open up Classpath tab for the development mode configuration and choose User Entries. Click Advanced... and select Add Folders. Choose Java and Resources under numberfield/src/main and click ok. Now you are ready to start debugging the client-side code by clicking debug. Click Launch Default Browser button in the GWT Development Mode in the launched application. Now you can modify and breakpoints to client-side classes and see changes by reloading the web page. 

Another way of debugging client-side is superdev mode. To enable it, uncomment devModeRedirectEnabled line from the end of DemoWidgetSet.gwt.xml located under numberfield-demo resources folder and compile the widgetset once by running vaadin:compile Maven target for numberfield-demo. Refresh numberfield-demo project resources by right clicking the project and choosing Refresh. Click "Create SuperDevMode Launch" button on the Vaadin tab of the numberfield-demo project properties panel to create superder mode code server launch configuration and modify the class path as instructed above. After starting the code server by running SuperDevMode launch as Java application, you can navigate to http://localhost:8080/numberfield-demo/?superdevmode. Now all code changes you do to your client side will get compiled as soon as you reload the web page. You can also access Java-sources and set breakpoints inside Chrome if you enable source maps from inspector settings. 

 
## Release notes

### Version 0.0.2
- Code-refactoring and simplification (deleted min, max values for validation and default converter) 
- Added NumberfFieldWidget class 
- Deleted demo project source code from add-on project 
- Bug fix with saving cursor position 
- Deleted click and focus handlers 

This component is developed as a hobby with no public roadmap or any guarantees of upcoming releases.

## Issue tracking

The issues for this add-on are tracked on its github.com page. All bug reports and feature requests are appreciated. 

## Contributions

Contributions are welcome, but there are no guarantees that they are accepted as such. Process for contributing is the following:
- Fork this project
- Create an issue to this project about the contribution (bug or feature) if there is no such issue about it already. Try to keep the scope minimal.
- Develop and test the fix or functionality carefully. Only include minimum amount of code needed to fix the issue.
- Refer to the fixed issue in commit
- Send a pull request for the original project
- Comment on the original issue that you have implemented a fix for it

## License & Author

Add-on is distributed under Apache License 2.0. For license terms, see LICENSE.txt.

NumberField7 is written by Kerim O.D.

# Developer Guide

## Getting started

```java
public class Numberfield7View extends VerticalLayout implements View {

    NumberField numberField = new NumberField("<span style=\"font-size: 2.4em;\">Demo NumberField</span>");
    TextField value = new TextField("Value");
    Label currentValue = new Label();
    CheckBox isSigned = new CheckBox("Is signed");
    CheckBox isUseGrouping = new CheckBox("Is Use Grouping");
    TextField groupingSeparator = new TextField("Grouping Separator");
    NumberField decimalLength = new NumberField("Decimalc Length");
    TextField decimalSeparator = new TextField("Decimal Separator");

    public Numberfield7View() {
        setSizeFull();
        HorizontalLayout content = new HorizontalLayout();
        content.setWidth(700, Sizeable.Unit.PIXELS);
        content.setMargin(true);
        content.setSpacing(true);
        addComponent(content);
        setComponentAlignment(content, Alignment.MIDDLE_CENTER);
        VerticalLayout form = new VerticalLayout();
        form.setSpacing(true);
        content.addComponent(form);
        numberField.setWidth(100, Sizeable.Unit.PERCENTAGE);
        numberField.setCaptionAsHtml(true);
        numberField.setNullRepresentation("");
        numberField.setImmediate(true);
        form.addComponent(numberField);
        form.addComponent(new Button("Get current Value:", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                currentValue.setValue(numberField.getValue());
            }
        }));
        form.addComponent(currentValue);
        form = new VerticalLayout();
        form.setCaption("<h3>Field Settings</h3>");
        form.setCaptionAsHtml(true);
        form.setSpacing(true);
        content.addComponent(form);
        value.setWidth(100, Sizeable.Unit.PERCENTAGE);
        isSigned.setValue(Boolean.TRUE);
        isUseGrouping.setValue(Boolean.TRUE);
        groupingSeparator.setMaxLength(1);
        groupingSeparator.setValue(" ");
        groupingSeparator.setWidth(100, Sizeable.Unit.PERCENTAGE);
        decimalLength.setValue("0");
        decimalLength.setMaxLength(1);
        decimalLength.setWidth(50, Sizeable.Unit.PERCENTAGE);
        decimalSeparator.setMaxLength(1);
        decimalSeparator.setValue(".");
        decimalSeparator.setWidth(100, Sizeable.Unit.PERCENTAGE);
        form.addComponent(value);
        form.addComponent(isSigned);
        form.addComponent(isUseGrouping);
        form.addComponent(groupingSeparator);
        form.addComponent(decimalLength);
        form.addComponent(decimalSeparator);
        Button submitButton = new Button("Reset settings", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                numberField.setSigned(
                        Boolean.TRUE.equals(isSigned.getValue()));
                numberField.setUseGrouping(
                        Boolean.TRUE.equals(isUseGrouping.getValue()));
                numberField.setGroupingSeparator(
                        getChar(groupingSeparator, ' '));
                numberField.setDecimalLength(getInt(decimalLength));
                numberField.setDecimalSeparator(
                        getChar(decimalSeparator, '.'));
                numberField.setValue(value.getValue());
            }

            char getChar(TextField field, char defaultChar) {
                String val = field.getValue();
                if (val == null || val.isEmpty()) {
                    return defaultChar;
                }
                return val.charAt(0);
            }

            int getInt(NumberField field) {
                String val = field.getValue();
                if (val == null) {
                    return 0;
                }
                return Integer.valueOf(field.getValue());
            }
        });
        submitButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
        form.addComponent(submitButton);
        form.setComponentAlignment(submitButton, Alignment.BOTTOM_RIGHT);
        submitButton.click();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Page.getCurrent().setTitle("NumberField7 Demo View");
    }

}

```

For a more comprehensive example, see vaadin-demo/src/main/java/tm/kod/widgets/demo/Numberfield7View.java

## Features

### Feature A

<...>

### Feature B

<...>

### Feature C

<...>

## API

VaadinWidgets JavaDoc is available online at <...>