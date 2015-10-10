package gamelogic;

import gamelogic.entities.Portal;
import gamelogic.events.*;

/**
 * the character that users can play as who can use magic to move around quickly and move other players around
 * @author brownmax1
 *
 */
public class SorcererPlayerStrategy implements CharacterStrategy {

	private final int WARP_DISTANCE = 4;
	private final Portal telegunPortal = new Portal(CardinalDirection.NORTH); // we give these values
	
	//USED TO CONVERT THE CLIENT INPUT EVENTS GENERATED BY KEY PRESSES AND CLICKS INTO THE GAME EVENTS RELEVANT TO THIS STRATEGY
	@Override
	public PlayerEvent createCharacterEvent(ClientGeneratedEvent tempClientEvent) {
	
		//we need to modify movement keys because some characters might move more/less
		if(tempClientEvent instanceof DownPushedEvent){
			return new PlayerMoveDown(tempClientEvent.getUid());
		}else if(tempClientEvent instanceof UpPushedEvent){
			return new PlayerMoveUp(tempClientEvent.getUid());
		}else if(tempClientEvent instanceof LeftPushedEvent){
			return new PlayerMoveLeft(tempClientEvent.getUid());
		}else if(tempClientEvent instanceof RightPushedEvent){
			return new PlayerMoveRight(tempClientEvent.getUid());
		}
		
		//we need to modify action/ability/attack keys because different characters do this differently
		else if(tempClientEvent instanceof Action1PushedEvent){//action 1 for sorcerer is attempt to warp
			return new WarpMoveEvent(tempClientEvent.getUid(), this.WARP_DISTANCE);
		}else if(tempClientEvent instanceof Action2PushedEvent){//action 2 for sorcerer is attempt to place teleporter
			return new useTeleGunEvent(tempClientEvent.getUid(), this.telegunPortal);
		}
		//in the case that it is some other kind of event that is consistent between different characters, return it as is. (e.g. PlayerSelectInvSlot1 is a ClientGeneratedEvent and a PlayerEvent
		//sanity checkkk
		assert(tempClientEvent instanceof PlayerDropEvent || tempClientEvent instanceof PlayerPickupEvent || tempClientEvent instanceof InventorySelectionEvent || tempClientEvent instanceof CarrierOpenCloseEvent || tempClientEvent instanceof RotateMapClockwise):"this kind of event not supported atm in the strategy converter";
		return (PlayerEvent) tempClientEvent;
	}
//TODO: implement getMeleeHit() method etc
	//need to think about how damagin system will work etc actually


	//JOSH ADDED THIS
	@Override
	public String toString(){
		return "Tank_Strategy";
	}
}