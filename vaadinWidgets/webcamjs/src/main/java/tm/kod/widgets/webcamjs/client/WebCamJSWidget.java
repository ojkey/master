/*
 * Copyright 2015 Kerim O.D.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tm.kod.widgets.webcamjs.client;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 *
 * @author Kerim O.D.
 */
public class WebCamJSWidget extends SimplePanel {

    /**
     * Ready listener interface
     */
    public interface ReadyListener {

        public void ready();
    }

    /**
     * Error listener interface
     */
    public interface ErrorListener {

        /**
         * Called on error
         * @param message 
         */
        public void error(String message);
    }

    /**
     * Image snap listener interface
     */
    public interface SnapListener {

        /**
         * Called on image snapped
         * @param base64String 
         */
        public void snap(String base64String);
    }
    
    public WebCamJSWidget() {
    }
    
    public void init() {
        attach(getElement());
    }
        
    private native void attach(Element el)/*-{
        $wnd.Webcam.attach(el);
     }-*/;
        
    public native void setQuality(int quality)/*-{
        $wnd.Webcam.set({'quality':quality});
     }-*/;
        
    public native void setCamWidth(int width)/*-{
        $wnd.Webcam.set({'width':width});
     }-*/;
        
    public native void setCamHeight(int height)/*-{
        $wnd.Webcam.set({'height':height});
     }-*/;

    public native void setReadyListener(ReadyListener l)/*-{
    $wnd.Webcam.on( 'load', function() {
     l.@tm.kod.widgets.webcamjs.client.WebCamJSWidget.ReadyListener::ready()();
    } );
     }-*/;

    public native void setErrorListener(ErrorListener l)/*-{
    $wnd.Webcam.on( 'error', function(err) {
     l.@tm.kod.widgets.webcamjs.client.WebCamJSWidget.ErrorListener::error(Ljava/lang/String;)(err);
    } );
     }-*/;

    public native void freeze()/*-{
     $wnd.Webcam.freeze();
     }-*/;

    public native void unfreeze()/*-{
     $wnd.Webcam.unfreeze();
     }-*/;

    public native void reset()/*-{
     $wnd.Webcam.reset();
     }-*/;

    public native void snap(SnapListener l)/*-{
     $wnd.Webcam.snap(function (data_uri) {
     var raw_image_data = data_uri.replace(/^data\:image\/\w+\;base64\,/, '');
     l.@tm.kod.widgets.webcamjs.client.WebCamJSWidget.SnapListener::snap(Ljava/lang/String;)(raw_image_data);
     });
     }-*/;
}
