package tm.kod.widgets.webcamjs.client;

import com.vaadin.shared.communication.ServerRpc;

// ServerRpc is used to pass events from client to server
public interface WebCamJSServerRpc extends ServerRpc {

    public void ready();
    
    public void error(String message);
    
    public void upload(String base64String);

}
