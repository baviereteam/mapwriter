package mapwriter.gui;

import mapwriter.Mw;
import mapwriter.map.Marker;
import mapwriter.map.MarkerManager;
import mapwriter.serverconnector.WebConnector;
import net.minecraft.client.gui.GuiScreen;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MwGuiServerDialog extends MwGuiTextDialog {
	private static final int STATE_HOST = 0;
	private static final int STATE_PORT = 1;
	private static final int STATE_NUMBER = 2;
	private static final int STATE_KEY = 3;
	
    private int state = STATE_HOST;
    
    private String host = "";
    private int port = 0;
    private int server = 0;
    private String key = "";
    
    public MwGuiServerDialog(GuiScreen parentScreen, String host, int port, int server, String key) {
        super(parentScreen, "Server host:", host, "server must have a host");
        this.host = host;
        this.port = port;
        this.server = server;
        this.key = key;
    }
    
    public MwGuiServerDialog(GuiScreen parentScreen) {
        super(parentScreen, "Server host:", "", "server must have a host");
    }
    	
	@Override
	public boolean submit() {
		boolean done = false;
		switch(this.state) {
		case STATE_HOST:
			this.host = this.getInputAsString();
			if (this.inputValid) {
				this.title = "Server port:";
				this.setText("" + this.port);
				this.error = "server must have a port";
				this.state++;
			}
			break;
			
		case STATE_PORT:
			this.port = this.getInputAsInt();
			if (this.inputValid) {
				this.title = "Server ID:";
				this.setText("" + this.server);
				this.error = "invalid value (must be a number)";
				this.state++;
			}
			break;
			
		case STATE_NUMBER:
			this.server = this.getInputAsInt();
			if (this.inputValid) {
				this.title = "Server access key:";
				this.setText(this.key);
				this.error = "invalid value";
				this.state++;
			}
			break;
			
		case STATE_KEY:
			this.key = this.getInputAsString();
			if (this.inputValid) {
				done = true;
				WebConnector.getInstance().setHost(host);
				WebConnector.getInstance().setPort(port);
				Mw.instance.serverId = server;
				Mw.instance.serverKey = key;
			}
			break;

		}
		return done;
	}
}
