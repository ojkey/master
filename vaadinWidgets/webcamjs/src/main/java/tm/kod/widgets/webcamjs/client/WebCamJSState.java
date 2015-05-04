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
package tm.kod.widgets.webcamjs.client;

import com.vaadin.shared.AbstractComponentState;

/**
 *
 * @author Kerim
 */
public class WebCamJSState extends AbstractComponentState {

    /**
     * Generated serial version UID
     */
    private static final long serialVersionUID = -2800170770211846223L;
    /**
     * Image capture quality [1-100]. Default value = 90 %
     */
    public int quality = 90;
    /**
     * WebCam width. Default value = 320
     */
    public int camWidth = 320;
    /**
     * WebCam height. Default value = 240
     */
    public int camHeight = 240;
    /**
     * Location of SWF movie
     */
    public String swfUrl;
    
}
