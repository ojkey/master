/*
 * Copyright 2015 Kerim.
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
package tm.kod.widgets.webcam;

import com.google.gwt.thirdparty.guava.common.io.BaseEncoding;
import com.vaadin.annotations.JavaScript;
import com.vaadin.server.UserError;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Component;
import com.vaadin.util.ReflectTools;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import tm.kod.widgets.webcam.client.WebCamJSClientRpc;
import tm.kod.widgets.webcam.client.WebCamJSServerRpc;
import tm.kod.widgets.webcam.client.WebCamJSState;

/**
 * Simple Javascript WebCam component based on https://github.com/jhuckaby/webcamjs
 *
 * @author Kerim
 */
@JavaScript("vaadin://webcam/webcam.js")
public class WebCamJS extends AbstractComponent {

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
            fireEvent(new CompleteEvent(WebCamJS.this, base64String));
        }

    };
    /**
     * Generated serial version UID
     */
    private static final long serialVersionUID = -3703246954643612374L;

    /**
     * Upload complete event class
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
        public WebCam getSource() {
            return (WebCam) super.getSource();
        }
    }

    /**
     * Webcam ready listener
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
     * Upload complete event class
     *
     */
    public class CompleteEvent extends Event {

        /**
         * Generated serial version UID
         */
        private static final long serialVersionUID = -6630357998893872551L;
        private final byte[] bytes;

        public CompleteEvent(Component source, String base64String) {
            super(source);
            bytes = BaseEncoding.base64().decode(base64String);
        }

        public InputStream getStream() {
            return new ByteArrayInputStream(bytes);
        }
    }

    /**
     * Upload complete listener
     *
     */
    public interface UploadCompleteListener extends Serializable {

        static Method METHOD = ReflectTools.findMethod(
                UploadCompleteListener.class, "complete", CompleteEvent.class);

        /**
         *
         * @param e event object
         */
        void complete(CompleteEvent e);
    }

    public WebCamJS() {
        setHeight(240, Unit.PIXELS);
        setWidth(320, Unit.PIXELS);
        setImmediate(true);
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

    public void addUploadCompleteListener(UploadCompleteListener listener) {
        super.addListener(CompleteEvent.class, listener,
                UploadCompleteListener.METHOD);
    }

    public void removeUploadCompleteListener(UploadCompleteListener listener) {
        super.removeListener(CompleteEvent.class, listener,
                UploadCompleteListener.METHOD);
    }

    public void freeze() {
        getRpcProxy(WebCamJSClientRpc.class).freeze();
    }

    public void unfreeze() {
        getRpcProxy(WebCamJSClientRpc.class).unfreeze();
    }

    public void reset() {
        getRpcProxy(WebCamJSClientRpc.class).reset();
    }

    public void snap() {
        getRpcProxy(WebCamJSClientRpc.class).snap();
    }

    public int getQuality() {
        return getState().quality;
    }

    public void setQuality(int quality) {
        if (quality > 0 && quality <= 100) {
            getState().quality = quality;
        }
    }

    public int getImageWidth() {
        return getState().camWidth;
    }

    public void setImageWidth(int camWidth) {
        getState().camWidth = camWidth;
    }

    public int getCamHeight() {
        return getState().camHeight;
    }

    public void setCamHeight(int camHeight) {
        getState().camHeight = camHeight;
    }

    @Override
    protected WebCamJSState getState() {
        return (WebCamJSState) super.getState();
    }
}
