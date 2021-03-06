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

import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.ui.Connect;
import tm.kod.widgets.webcamjs.WebCamJS;

/**
 *
 * @author Kerim O.D.
 */
@Connect(value = WebCamJS.class)
public class WebCamJSConnector extends AbstractComponentConnector
        implements WebCamJSWidget.ReadyListener,
        WebCamJSWidget.ErrorListener,
        WebCamJSWidget.SnapListener {

    WebCamJSServerRpc rpc;
    boolean inited;
    int quality = 90;
    int camWidth = 320;
    int camHeight = 240;

    public WebCamJSConnector() {
        rpc = getRpcProxy(WebCamJSServerRpc.class);
        // To receive RPC events from server, we register ClientRpc implementation 
        registerRpc(WebCamJSClientRpc.class, new WebCamJSClientRpc() {

            @Override
            public void freeze() {
                getWidget().freeze();
            }

            @Override
            public void unfreeze() {
                getWidget().unfreeze();
            }

            @Override
            public void snap() {
                getWidget().snap(WebCamJSConnector.this);
            }

        });
    }

    @Override
    public WebCamJSWidget getWidget() {
        return (WebCamJSWidget) super.getWidget();
    }

    @Override
    public WebCamJSState getState() {
        return (WebCamJSState) super.getState();
    }

    @Override
    public void ready() {
        rpc.ready();
    }

    @Override
    public void error(String message) {
        rpc.error(message);
    }

    @Override
    public void snap(String base64String) {
        rpc.upload(base64String);
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        WebCamJSState state = getState();
            WebCamJSWidget widget = getWidget();
        boolean camStateChanged = false;
        if(camWidth != state.camWidth) {
            camWidth = state.camWidth;
            widget.setQuality(camWidth);
            camStateChanged = true;
        }
        if(camHeight != state.camHeight) {
            camHeight = state.camHeight;
            widget.setQuality(camHeight);
            camStateChanged = true;
        }
        if(quality != state.quality) {
            quality = state.quality;
            widget.setQuality(quality);
            camStateChanged = true;
        }
        if (camStateChanged) {
            widget.reset();
            widget.init();
        }
    }

    @Override
    protected void init() {
        super.init();
        WebCamJSState state = getState();
        WebCamJSWidget widget = getWidget();
        widget.setCamWidth(state.camWidth);
        widget.setCamHeight(state.camHeight);
        widget.setReadyListener(WebCamJSConnector.this);
        widget.setErrorListener(WebCamJSConnector.this);
        widget.init();
    }

    @Override
    public void onUnregister() {
        getWidget().reset();
        super.onUnregister();
    }

}
