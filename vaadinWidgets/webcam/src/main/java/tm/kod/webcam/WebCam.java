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

package tm.kod.webcam;

import com.google.gwt.thirdparty.guava.common.io.BaseEncoding;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Method;

import com.vaadin.annotations.JavaScript;
import com.vaadin.server.UserError;
import com.vaadin.ui.AbstractJavaScriptComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.JavaScriptFunction;
import com.vaadin.util.ReflectTools;
import elemental.json.JsonArray;
import tm.kod.webcam.client.WebCamState;

/**
 * Simple Javascript WebCam component based on https://github.com/jhuckaby/webcamjs
 * 
 * @author Kerim O.D.
 */
@JavaScript("vaadin://webcam/webcam.min.js")
public class WebCam extends AbstractJavaScriptComponent {

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

        public ReadyEvent(WebCam source) {
            super(source);
        }

        @Override
        public WebCam getSource() {
            return (WebCam) super.getSource();
        }
    }

    /**
     * On complete upload javascript function
     *
     */
    private class OnReadyJavaScriptFunction implements JavaScriptFunction {

        private static final String NAME = "onReady";
        /**
         * Generated serial version UID
         */
        private static final long serialVersionUID = 4255944043478088806L;

        @Override
        public void call(JsonArray arg0) {
            getState().ready = true;
            // fire ready event on server
            fireEvent(new ReadyEvent(WebCam.this));
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

    /**
     * On complete upload javascript function
     *
     */
    private class OnSaveJavaScriptFunction implements JavaScriptFunction {

        private static final String NAME = "onSave";
        /**
         * Generated serial version UID
         */
        private static final long serialVersionUID = 4255944043478088806L;

        @Override
        public void call(JsonArray arguments) {
            // fire upload complete event on server
            fireEvent(new CompleteEvent(WebCam.this, arguments.getString(0)));
        }
    }

    /**
     * On error in widget function
     *
     */
    private class OnErrorJavaScriptFunction implements JavaScriptFunction {

        private static final String NAME = "onError";
        /**
         * Generated serial version UID
         */
        private static final long serialVersionUID = 4255944043478088806L;

        @Override
        public void call(JsonArray arguments) {
            String message = arguments.getString(0);
            //message = getLocalizedMessage(code, message);
            setComponentError(new UserError(message));
        }
    }

    public WebCam() {
        setHeight(240, Unit.PIXELS);
        setWidth(320, Unit.PIXELS);
        setImmediate(true);
        addFunction(OnReadyJavaScriptFunction.NAME,
                new OnReadyJavaScriptFunction());
        addFunction(OnSaveJavaScriptFunction.NAME,
                new OnSaveJavaScriptFunction());
        addFunction(OnErrorJavaScriptFunction.NAME,
                new OnErrorJavaScriptFunction());
    }

    public WebCam(String caption) {
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
        callFunction("freeze", "");
    }

    public void unfreeze() {
        callFunction("unfreeze", "");
    }

    public void reset() {
        callFunction("reset", "");
    }

    public void snap() {
        callFunction("snap", "");
    }

    public boolean isReady() {
        return getState().ready;
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
        return getState().imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        getState().imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return getState().imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        getState().imageHeight = imageHeight;
    }

    @Override
    protected WebCamState getState() {
        return (WebCamState) super.getState();
    }
}
