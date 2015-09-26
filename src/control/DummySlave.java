package control;

import ui.GameGui;
import gamelogic.ClientFrame;
import gamelogic.IDedPlayerMoveDown;
import gamelogic.IDedPlayerMoveRight;
import gamelogic.IDedPlayerMoveUp;
import gamelogic.Server;

/**
 *
 * @author brownmax1 -THIS IS A DUMMY IMPLEMENTATION FOR TESTING THE INFORMATION
 *         FLOW OF THE PROGRAM WITH ONE PLAYER. NOT ACTUALLY NETWORKED THIS
 *         SHOULD IMPLEMENT A KEY LISTENER BUT *SENDING * in reality the slave
 *         will - intercept key presses, - encode them into a "cheap data"
 *         version (e.g. 0001 for moving north or someshit) -place that "event"
 *         in the socket to be received by the Master RECEIVING -receive an
 *         encoded event with player or enemy id to identify who/what made that
 *         action -turn those encoded bits into the appropriate IDed event -
 *         give that IDed event to this slave's Player so that the player can
 *         update its version of the WorldGameState appropriately
 */
// NOTE that for now we will just deal with player events so we don't need to
// worry about. how the zombies will work.
// will probably be a system where the zombie ids are registered from the server
// to the player so that the players can just receive
// zombie ids along with their events

public class DummySlave extends Thread {

	private DummyMaster master;
	private final int playerUid;
	private final GameGui myGui;

	public DummySlave(int uid, GameGui topLevelGui) {
		this.myGui = topLevelGui;
		this.playerUid = uid;
	}

	///ESTABLISHING CONNECTION///

	public void connectToServer(Server server) {
		// connect this slave to its master on the server side.
		//!!! THIS WILL ACTUALLY BE DONE WITH SOCKETS AND addreesess to initiate the communivation SHIT OBVS
		this.master = server.createMasterForSlave(this);
		// the master needs to be able to talk back
		this.master.giveSlave(this);
		// the slave has the master now so we can start sending our dummy events
		// to it
		this.start();
	}


	// /OUT TO THE SERVER ///

	// DUMMY SENDING SIGNALS THING
	/**
	 * this method generates fake/random events to give to the master. in
	 * realilty these events will be generated by a key listener and encoded
	 * into bytes those bytes will then be sent by the master along with the
	 * player id when they rach the master, the master will decode them into the appropriate event
	 */
	public void run() {

		while (true) {
			IDedPlayerMoveDown downEvent = new IDedPlayerMoveDown(this.getPlayerUid());
			IDedPlayerMoveUp upEvent = new IDedPlayerMoveUp(this.getPlayerUid());
			try {
				//go down the boardddd
				this.master.sendEventSlaveToMaster(downEvent);
				Thread.sleep( (long) (1000) );
				this.master.sendEventSlaveToMaster(downEvent);
				Thread.sleep( (long) (1000) );
				this.master.sendEventSlaveToMaster(downEvent);
				Thread.sleep( (long) (1000) );
				this.master.sendEventSlaveToMaster(downEvent);
				Thread.sleep( (long) (1000) );
				this.master.sendEventSlaveToMaster(downEvent);
				Thread.sleep( (long) (1000) );
				this.master.sendEventSlaveToMaster(downEvent);
				Thread.sleep( (long) (1000) );
				this.master.sendEventSlaveToMaster(downEvent);
				Thread.sleep( (long) (1000) );
				this.master.sendEventSlaveToMaster(downEvent);
				Thread.sleep( (long) (1000) );
				this.master.sendEventSlaveToMaster(downEvent);
				Thread.sleep( (long) (1000) );
				//go back upp
				this.master.sendEventSlaveToMaster(upEvent);
				Thread.sleep( (long) (1000) );
				this.master.sendEventSlaveToMaster(upEvent);
				Thread.sleep( (long) (1000) );
				this.master.sendEventSlaveToMaster(upEvent);
				Thread.sleep( (long) (1000) );
				this.master.sendEventSlaveToMaster(upEvent);
				Thread.sleep( (long) (1000) );
				this.master.sendEventSlaveToMaster(upEvent);
				Thread.sleep( (long) (1000) );
				this.master.sendEventSlaveToMaster(upEvent);
				Thread.sleep( (long) (1000) );
				this.master.sendEventSlaveToMaster(upEvent);
				Thread.sleep( (long) (1000) );
				this.master.sendEventSlaveToMaster(upEvent);
				Thread.sleep( (long) (1000) );
				this.master.sendEventSlaveToMaster(upEvent);
				Thread.sleep( (long) (1000) );

			} catch (InterruptedException e) {
				e.printStackTrace();
			}


		}

	}


	// / IN TO GUI/CLIENT ///

	/**
	 * sends the update to the true game state from teh server back to the
	 * player
	 *
	 * @param event
	 *            the event that has changed the true game state
	 */
	public void sendGameStateMasterToSlave(ClientFrame stateToDraw) {
		//System.out.println("drawing updated game state then fam");//TODO: obv this needs to be linked to the renderer shits
		 this.myGui.getInvasionCanvas().setDrawableState(stateToDraw.getRoomToDraw());
		 this.myGui.getInvasionCanvas().repaint();

	}

	///UTIL///

	 public int getPlayerUid() {
		return playerUid;
	}




}
