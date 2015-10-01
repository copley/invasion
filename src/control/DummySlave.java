package control;

import gamelogic.ClientFrame;
import gamelogic.Server;
import gamelogic.events.PlayerDropEvent;
import gamelogic.events.PlayerEvent;
import gamelogic.events.PlayerMoveDown;
import gamelogic.events.PlayerMoveLeft;
import gamelogic.events.PlayerMoveRight;
import gamelogic.events.PlayerMoveUp;
import gamelogic.events.PlayerPickupEvent;
import gamelogic.events.PlayerSelectInvSlot1;
import gamelogic.events.PlayerSelectInvSlot2;
import gamelogic.events.PlayerSelectInvSlot3;
import gamelogic.events.PlayerSelectInvSlot4;
import gamelogic.events.PlayerSelectInvSlot5;

import java.util.Scanner;

import ui.GameGui;

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
			PlayerMoveDown downEvent = new PlayerMoveDown(this.getPlayerUid());
			PlayerMoveUp upEvent = new PlayerMoveUp(this.getPlayerUid());
			PlayerMoveRight rightEvent = new PlayerMoveRight(this.getPlayerUid());
			PlayerMoveLeft leftEvent = new PlayerMoveLeft(this.getPlayerUid());
			PlayerPickupEvent grabEvent = new PlayerPickupEvent(this.getPlayerUid());
			PlayerDropEvent dropEvent = new PlayerDropEvent(this.getPlayerUid());
			PlayerSelectInvSlot1 pick1 = new PlayerSelectInvSlot1(this.getPlayerUid());
			PlayerSelectInvSlot2 pick2 = new PlayerSelectInvSlot2(this.getPlayerUid());
			PlayerSelectInvSlot3 pick3 = new PlayerSelectInvSlot3(this.getPlayerUid());
			PlayerSelectInvSlot4 pick4 = new PlayerSelectInvSlot4(this.getPlayerUid());
			PlayerSelectInvSlot5 pick5 = new PlayerSelectInvSlot5(this.getPlayerUid());

			//hacky loop that lets user move
			Scanner sc = new Scanner(System.in);
			String temp = "nulltbh";
			while(true){
					temp = sc.next();
				switch (temp){
					case "w":
						this.master.sendEventSlaveToMaster(upEvent);
						temp = " ";
						break;
					case "s":
						this.master.sendEventSlaveToMaster(downEvent);
						temp = " ";
						break;
					case "a":
						this.master.sendEventSlaveToMaster(leftEvent);
						temp = " ";
						break;
					case "d":
						this.master.sendEventSlaveToMaster(rightEvent);
						temp = " ";
						break;
					case "pickup":
						this.master.sendEventSlaveToMaster(grabEvent);
						temp = " ";
						break;
					case "drop":
						this.master.sendEventSlaveToMaster(dropEvent);
						temp = " ";
						break;
					case "pick1":
						this.master.sendEventSlaveToMaster(pick1);
						temp = " ";
						break;
					case "pick2":
						this.master.sendEventSlaveToMaster(pick2);
						temp = " ";
						break;
					case "pick3":
						this.master.sendEventSlaveToMaster(pick3);
						temp = " ";
						break;
					case "pick4":
						this.master.sendEventSlaveToMaster(pick4);
						temp = " ";
						break;
					case "pick5":
						this.master.sendEventSlaveToMaster(pick5);
						temp = " ";
						break;
						
				}
			}




		}

	}

	//HACKY METHOD FOR MAXC TO SEND PlayerEvents GENERATED BY THE Listener
	//THROUGH TO THE SERVER
	public void sendEventClientToServer(PlayerEvent eventToSend){
		this.master.sendEventSlaveToMaster(eventToSend);
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

		 this.myGui.getPlayerCanvas().setDrawableState(stateToDraw.getPlayerInfoToDraw());
		 this.myGui.getPlayerCanvas().repaint();

	}

	///UTIL///

	 public int getPlayerUid() {
		return playerUid;
	}




}
