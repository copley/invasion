package gamelogic;

import java.util.HashMap;

import gamelogic.entities.GameEntity;
import gamelogic.entities.NullEntity;
import gamelogic.tiles.GameRoomTile;
//A ROOMSTATE THAT CONTAINS A PYLON AND MUST BE ABLE TO SPAWN PYLON ATTACKERS
public class PylonRoomState extends RoomState
{
	
	//SPAWN LOCATIONS FOR PYLON ATTACKERS
	private final int topSpawnX = 11; //TODO: if these are hardcoded need to specify that room is of certain size
	private final int topSpawnY = 1;
	
	private final int bottomSpawnX = 11;
	private final int bottomSpawnY = 21;
	
	private final int rightSpawnX = 21;
	private final int rightSpawnY = 11;
	
	private final int leftSpawnX = 1;
	private final int leftSpawnY = 11;
	
	
	
	
	
	public PylonRoomState(GameRoomTile[][] tiles, GameEntity[][] entities,int width, int height, int roomId, String roomName) {
		super(tiles, entities, width, height, roomId, roomName);
		assert(tiles[0].length == 23 && tiles.length == 23):"if the  pylon room's size does not follow the guidelines, the spawning system etc cannot work";
	}
	

	///ATTEMPTs TO SPAWN PYLON ATTACKER WAVE IN ROOM
	protected HashMap<Integer, IndependentActor> spawnPylonAttackerWave(HashMap<Integer, IndependentActor> waveMap){
		assert(waveMap.size() == 4): "why were we not passed 4 pylon attackers?";
		
		//we need a map to return the attackers that were spawned successfully
		HashMap<Integer, IndependentActor> successfullyPlacedAttackers = new HashMap<Integer, IndependentActor>();
		
		//spawn each pylon attacker at the appropriate location (determined by which directin it faces)
		for(int eachKey: waveMap.keySet()){
			//put each of the four attackers in the right position in the room
			//if we successfully place an attacker in their spawn, add them to the map of
			//successfully added attackers
			switch(waveMap.get(eachKey).getFacingCardinalDirection()){
				case NORTH:
					if(this.attemptToPlaceEntityInRoom(waveMap.get(eachKey), bottomSpawnX, bottomSpawnY)){
						successfullyPlacedAttackers.put(eachKey, waveMap.get(eachKey));
					}
					break;
				case EAST:
					if(this.attemptToPlaceEntityInRoom(waveMap.get(eachKey), leftSpawnX, leftSpawnY)){
						successfullyPlacedAttackers.put(eachKey, waveMap.get(eachKey));
					}
					break;
				case SOUTH:
					if(this.attemptToPlaceEntityInRoom(waveMap.get(eachKey), topSpawnX, topSpawnY)){
						successfullyPlacedAttackers.put(eachKey, waveMap.get(eachKey));
					}
					break;
					
				case WEST:
					if(this.attemptToPlaceEntityInRoom(waveMap.get(eachKey), rightSpawnX, rightSpawnY)){
						successfullyPlacedAttackers.put(eachKey, waveMap.get(eachKey));
					}
					break;
			}
		}
		
		//we added as many attackers as we could to the room, so return the map we made so that they are added
		//to the id -> entity maps
		
		return successfullyPlacedAttackers;
		
		
	}
	
//<<<<<<< Updated upstream
	//JOSH ADDED THIS
	
	public PylonRoomState(GameRoomTile[][] tiles, int width, int height, int roomId, boolean isDark, String roomName){
		super(tiles, width, height, roomId, isDark,roomName);
	}
	
	public void setEntities(GameEntity[][] entities){
		super.setEntities(entities);
	}
//=======
	//TODO: these two methods can probably be consolidated by just making zombie spawn at different times or maybe dont even spawn them in waves
	
	///ATTEMPTs TO SPAWN PYLON ATTACKER WAVE IN ROOM
	protected HashMap<Integer, IndependentActor> spawnZombieAttackerWave(HashMap<Integer, IndependentActor> waveMap){
		//assert(waveMap.size() == 4): "why were we not passed 4 zombie attackers?";
		
		//we need a map to return the attackers that were spawned successfully
		HashMap<Integer, IndependentActor> successfullyPlacedAttackers = new HashMap<Integer, IndependentActor>();
		
		//spawn each pylon attacker at the appropriate location (determined by which directin it faces)
		for(int eachKey: waveMap.keySet()){
			//put each of the four attackers in the right position in the room
			//if we successfully place an attacker in their spawn, add them to the map of
			//successfully added attackers
			switch(waveMap.get(eachKey).getFacingCardinalDirection()){
				case NORTH:
					if(this.attemptToPlaceEntityInRoom(waveMap.get(eachKey), bottomSpawnX, bottomSpawnY - 5)){
						successfullyPlacedAttackers.put(eachKey, waveMap.get(eachKey));
					}
					break;
				case EAST:
					if(this.attemptToPlaceEntityInRoom(waveMap.get(eachKey), leftSpawnX + 5, leftSpawnY)){
						successfullyPlacedAttackers.put(eachKey, waveMap.get(eachKey));
					}
					break;
				case SOUTH:
					if(this.attemptToPlaceEntityInRoom(waveMap.get(eachKey), topSpawnX, topSpawnY + 5)){
						successfullyPlacedAttackers.put(eachKey, waveMap.get(eachKey));
					}
					break;
					
				case WEST:
					if(this.attemptToPlaceEntityInRoom(waveMap.get(eachKey), rightSpawnX - 5, rightSpawnY)){
						successfullyPlacedAttackers.put(eachKey, waveMap.get(eachKey));
					}
					break;
			}
		}
		
		//we added as many attackers as we could to the room, so return the map we made so that they are added
		//to the id -> entity maps
		
		return successfullyPlacedAttackers;
		
		
//>>>>>>> Stashed changes
	}
	


}
