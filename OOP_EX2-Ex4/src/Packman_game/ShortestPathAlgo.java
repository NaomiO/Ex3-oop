
package Packman_game;

import java.util.ArrayList;
import java.util.Iterator;

import Coords.MyCoords;

public class ShortestPathAlgo {
	
	/**
	 * This class represents an algorithm that calculates the shortest path for each pacman to the closest fruits.
	 */
	public game game;
	
	/**
	 * Constructor that receives a game and uses the algorithm on this game using the informations it contains.
	 * @param game
	 */
	public  ShortestPathAlgo (game game){
		this.game = game;
	}
	
	/**
	 * This function is an algorithm that receives a game; pacmans and fruits points and calculates the shortest path between the pacmans and the fruits
	 * @param game
	 * @return the path in an arraylist of fruits points
	 */
	public ArrayList<Packman> ShortestPath (game game)
	{
		this.game = game;
		ArrayList<Packman>path=new ArrayList<>();
		MyCoords mycoords = new MyCoords();

		ArrayList<Packman> pacList = new ArrayList<>();
		pacList.addAll(game.getPackman());         //adds all the pacman to the array list
		ArrayList<fruits> fruitList = new ArrayList<>();
		fruitList.addAll(game.getfruits());  
		
		Iterator<fruits> itF = fruitList.iterator();
		int index = 0;
		int indexPath = 0;
		
		while(itF.hasNext())
		{
			fruits fruit = itF.next();
			Iterator<Packman> itP = pacList.iterator();
			Packman fastest = itP.next();
			double bestTime =  (mycoords.distance3d(fastest.getGps_p(),fruit.getGps_f()) - fastest.getRadius())
					/ fastest.getSpeed_weight();
			
			while(itP.hasNext())
			{
				Packman packman = itP.next();
				double time = (mycoords.distance3d(packman.getGps_p(),fruit.getGps_f()) - packman.getRadius())
						/ packman.getSpeed_weight();
				if(time<bestTime)
				{
					bestTime = time;
					fastest = packman;
					if(pacList.size()-1 != index)
						index++;

				}
			}

			//change the place of the fastest packman to the place of fruit
			pacList.set(index, new Packman(fastest.getRadius(),fastest.getSpeed_weight(),fruit.getGps_f()));
			path.add(indexPath, new Packman(fastest.getRadius(),fastest.getSpeed_weight(),fruit.getGps_f()));
			indexPath++;
		}
		return path;
		
	}
}
	