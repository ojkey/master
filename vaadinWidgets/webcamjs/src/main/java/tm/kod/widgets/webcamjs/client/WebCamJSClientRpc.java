package tm.kod.widgets.webcamjs.client;

import com.vaadin.shared.communication.ClientRpc;

// ClientRpc is used to pass events from server to client
// For sending information about the changes to component state, use State instead
public interface WebCamJSClientRpc extends ClientRpc {

    public void freeze();

    public void unfreeze();

    public void reset();

    public void snap();
}
