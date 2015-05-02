package tm.kod.widgets.webcam.client;

import com.vaadin.shared.ui.JavaScriptComponentState;

public class WebCamState extends JavaScriptComponentState {

    /**
     * Generated serial version UID
     */
    private static final long serialVersionUID = -2800170770211846223L;
    /**
     * Is webcam flash object ready to work
     */
    public boolean ready;
    /**
     * Image capture quality [1-100]. Default value = 90 %
     */
    public int quality = 90;
    /**
     * Image width. Default value = 320
     */
    public int imageWidth = 320;
    /**
     * Image height. Default value = 240
     */
    public int imageHeight = 240;

}
