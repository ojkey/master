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
package tm.kod.widgets.webcamjs;

import com.vaadin.annotations.JavaScript;
import com.vaadin.server.UserError;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Component;
import com.vaadin.util.ReflectTools;
import java.io.Serializable;
import java.lang.reflect.Method;
import tm.kod.widgets.webcamjs.client.WebCamJSClientRpc;
import tm.kod.widgets.webcamjs.client.WebCamJSServerRpc;
import tm.kod.widgets.webcamjs.client.WebCamJSState;

/**
 * Simple Javascript WebCam component based on
 * https://github.com/jhuckaby/webcamjs WARNING: To correct working you have to
 * use only one instance in UI.
 *
 * @author Kerim O.D.
 */
@JavaScript("vaadin://webcam/webcam.js")
public class WebCamJS extends AbstractComponent {

    /**
     * Server PRC implementation
     */
    private final WebCamJSServerRpc rpc = new WebCamJSServerRpc() {

        @Override
        public void ready() {
            fireEvent(new ReadyEvent(WebCamJS.this));
        }

        @Override
        public void error(String message) {
            setComponentError(new UserError(message));
        }

        @Override
        public void upload(String base64String) {
            fireEvent(new ReceivedEvent(WebCamJS.this, base64String));
        }

    };
    /**
     * Generated serial version UID
     */
    private static final long serialVersionUID = -3703246954643612374L;

    /**
     * WebCamJS component is ready to capture event class
     *
     */
    public class ReadyEvent extends Event {

        /**
         * Generated serial version UID
         */
        private static final long serialVersionUID = -5139326042688663341L;

        public ReadyEvent(WebCamJS source) {
            super(source);
        }

        @Override
        public WebCamJS getSource() {
            return (WebCamJS) super.getSource();
        }
    }

    /**
     * WebCamJS component is ready listener
     *
     */
    public interface ReadyListener extends Serializable {

        static Method METHOD = ReflectTools.findMethod(ReadyListener.class,
                "ready", ReadyEvent.class);

        /**
         *
         * @param e event object
         */
        void ready(ReadyEvent e);
    }

    /**
     * Snap data received event
     *
     */
    public class ReceivedEvent extends Event {

        /**
         * Generated serial version UID
         */
        private static final long serialVersionUID = -6630357998893872551L;
        private final String base64String;

        public ReceivedEvent(Component source, String base64String) {
            super(source);
            this.base64String = base64String;
        }

        public String getBase64String() {
            return base64String;
        }

    }

    /**
     * Snap data received listener
     *
     */
    public interface SnapReceivedListener extends Serializable {

        static Method METHOD = ReflectTools.findMethod(SnapReceivedListener.class, "received", ReceivedEvent.class);

        /**
         *
         * @param e event object
         */
        void received(ReceivedEvent e);
    }

    public WebCamJS() {
        setHeight(240, Unit.PIXELS);
        setWidth(320, Unit.PIXELS);
        // To receive events from the client, we register ServerRpc
        registerRpc(rpc);
    }

    public WebCamJS(String caption) {
        this();
        setCaption(caption);
    }

    public void addReadyListener(ReadyListener listener) {
        super.addListener(ReadyEvent.class, listener, ReadyListener.METHOD);
    }

    public void removeReadyListener(ReadyListener listener) {
        super.removeListener(ReadyEvent.class, listener, ReadyListener.METHOD);
    }

    public void addSnapReceivedListener(SnapReceivedListener listener) {
        super.addListener(ReceivedEvent.class, listener,
                SnapReceivedListener.METHOD);
    }

    public void removeSnapReceivedListener(SnapReceivedListener listener) {
        super.removeListener(ReceivedEvent.class, listener,
                SnapReceivedListener.METHOD);
    }

    /**
     * Freeze the current live camera frame, allowing the user to preview before
     * saving.
     */
    public void freeze() {
        getRpcProxy(WebCamJSClientRpc.class).freeze();
    }

    /**
     * Cancel the preview (discard image) and resume the live camera view.
     */
    public void unfreeze() {
        getRpcProxy(WebCamJSClientRpc.class).unfreeze();
    }

    /**
     * Take a snapshot from the camera (or frozen preview image). Pass callback
     * function to receive data.
     */
    public void snap() {
        getRpcProxy(WebCamJSClientRpc.class).snap();
    }

    public int getQuality() {
        return getState(false).quality;
    }

    public void setQuality(int quality) {
        if (quality >= 0 && quality <= 100) {
            getState().quality = quality;
        }
    }

    public int getCamWidth() {
        return getState(false).camWidth;
    }

    /**
     * WebCam width in pixels. Default value = 320
     *
     * @param camWidth
     */
    public void setCamWidth(int camWidth) {
        getState().camWidth = camWidth;
    }

    /**
     *
     * @return camera's height
     */
    public int getCamHeight() {
        return getState(false).camHeight;
    }

    /**
     *
     * WebCam height in pixel. Default value = 240
     *
     * @param camHeight
     */
    public void setCamHeight(int camHeight) {
        getState().camHeight = camHeight;
    }

    @Override
    protected WebCamJSState getState() {
        return (WebCamJSState) super.getState();
    }

    @Override
    protected WebCamJSState getState(boolean markAsDirty) {
        return (WebCamJSState) super.getState(markAsDirty);
    }
}
