package gamelogic.events;

import gamelogic.MovementEvent;

/**
 * this is the version of the event that will be submitted to the serverside true game state maintenance class.
 * has the requested event AND the id of the player who made that event
 * @author brownmax1
 *
 */
public class IDedPlayerMoveUp  implements IDedPlayerEvent, MovementEvent{


	private final int Uid;//the unique id of the player who sent this event to the server

	public IDedPlayerMoveUp(int Uid){
		this.Uid = Uid;
	}

	/* (non-Javadoc)
	 * @see gamelogic.IDedEvent#getUid()
	 */
	@Override
	public int getUid(){
		return this.Uid;
	}

}