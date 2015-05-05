# WebCamJS Add-on Project on Vaadin 7. 
This Add-on project based on JavaScript library project [WebCamJS](https://github.com/jhuckaby/webcamjs). Some information dublicated from [https://github.com/jhuckaby/webcamjs](https://github.com/jhuckaby/webcamjs)



## Online demo

Try the add-on demo at http://ojkey.jelastic.servint.net/demo/#!webcamjs

## Download release

## Building and running demo

git clone <url of the WebCamJS repository>
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
- Implemented WebCamJS in GWT and integrated with Vaadin client connector  
- Supported methods: freeze, unfreeze, snap, reset.
- Supported listeners: 
    1. ReadyListener - called when webcam is ready to capture.
    2. UploadCompleteListener - called after image uploaded
- Tested on Chrome(version 42), FireFox(version 37), Internet Explorer(version 9 and 11), Opera(version 29)

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

WebCamJS Vaadin Add-on is written by Kerim O.D.

# Developer Guide

## Getting started

###Freezing / Previewing The Image

Want to provide your users with the ability to "freeze" (i.e. preview) the image before actually saving a snapshot? Just call Webcam.freeze() to freeze the current camera image. Then call Webcam.snap() to save the frozen image, or call Webcam.unfreeze() to cancel and resume the live camera feed.

The idea here is to provide a photo-booth-like experience, where the user can take a snapshot, then choose to keep or discard it, before actually calling Webcam.snap() to save the image.

### Snapping a Picture

To snap a picture, just add WebCamJS.UploadCompleteListener and call the WebCamJS.snap(), passing in a callback function. The image data will be passed to your listener as base64 string, which you can get from WebCamJS.CompleteEvent#getBase64String(), which you can then display in your web page, or on a server. Example:

```java
    WebCamJS webCam = new WebCamJS("Webcam");
    webCam.addUploadCompleteListener(new WebCamJS.UploadCompleteListener() {

        @Override
        public void complete(final WebCamJS.CompleteEvent e) {
            StreamResource sr = new StreamResource(new StreamResource.StreamSource() {

                @Override
                public InputStream getStream() {
                    byte[] bytes = BaseEncoding.base64().decode(e.getBase64String());
                    return new ByteArrayInputStream(bytes);
                }
            }, System.currentTimeMillis() + ".jpeg");
            image.setSource(sr);
        }
    });
    ...
    webCam.snap();
```
For a more comprehensive example, see vaadin-demo/src/main/java/tm/kod/widgets/demo/WebCamJSView.java
```java
public class WebCamJSView extends VerticalLayout implements View {

    WebCamJS webCam = new WebCamJS("Webcam");
    Image image = new Image("Image");
    HorizontalLayout buttons = new HorizontalLayout();

    public WebCamJSView() {
        setSizeFull();
        VerticalLayout content = new VerticalLayout();
        content.setSpacing(true);
        content.setMargin(true);
        content.setSizeUndefined();
        addComponent(content);
        setComponentAlignment(content, Alignment.MIDDLE_CENTER);
        Label label = new Label("WebCamJSJS on Vaadin Demo");
        label.addStyleName("h1");
        content.addComponent(label);
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSpacing(true);
        content.addComponent(layout);
        image.setHeight(240, Sizeable.Unit.PIXELS);
        image.setWidth(320, Sizeable.Unit.PIXELS);
        webCam.addReadyListener(new WebCamJS.ReadyListener() {

            @Override
            public void ready(WebCamJS.ReadyEvent e) {
                buttons.setVisible(true);
            }
        });
        webCam.addUploadCompleteListener(new WebCamJS.UploadCompleteListener() {

            @Override
            public void complete(final WebCamJS.CompleteEvent e) {
                StreamResource sr = new StreamResource(new StreamResource.StreamSource() {

                    @Override
                    public InputStream getStream() {
                        byte[] bytes = BaseEncoding.base64().decode(e.getBase64String());
                        return new ByteArrayInputStream(bytes);
                    }
                }, System.currentTimeMillis() + ".jpeg");
                image.setSource(sr);
            }
        });
        layout.addComponent(webCam);
        layout.addComponent(image);
        // initing buttons
        buttons.setVisible(false); // visible when webcam is ready
        buttons.setSpacing(true);
        content.addComponent(buttons);
        // init freeze button
        Button button = new Button("Freeze", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                webCam.freeze();
            }
        });
        buttons.addComponent(button);
        // initing unfreeze button
        button = new Button("Unfreeze", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                webCam.unfreeze();
            }
        });
        buttons.addComponent(button);
        // initing snapshoot button
        button = new Button("Upload (Snap)", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                webCam.snap();
            }
        });
        button.addStyleName(ValoTheme.BUTTON_PRIMARY);
        buttons.addComponent(button);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Page.getCurrent().setTitle("WebCamJS Demo View");
    }

}
```

## Features

### Feature A

<...>

### Feature B

<...>

### Feature C

<...>

## API

WebCamJS JavaDoc is available online at <...>