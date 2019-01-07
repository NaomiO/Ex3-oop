package Packman_game;

import java.util.ArrayList;
import java.util.Iterator;


import Packman_game.game;
import Packman_game.Packman;
import Packman_game.Path;
import Packman_game.fruits;


public class ShortestPathAlgo extends ArrayList<Path> {
	
	static ArrayList<Path> Paths = new ArrayList<Path>();
	ArrayList<fruits> tempFruits;
	ArrayList<Packman> tempPackmans;
	
	public ShortestPathAlgo(game game) {
		
		tempFruits = new ArrayList<fruits>(game.getfruits()); 
		tempPackmans = new ArrayList<Packman>(game.getPackman());
		
		while(!tempFruits.isEmpty()) {
			
			Iterator<Packman> itP= game.getPackman().iterator();
			Iterator<Packman> temp_itP= tempPackmans.iterator();

			double min= 100000000;
			Packman bestPac= itP.next();
			
			while(temp_itP.hasNext())
			{
				Iterator<fruits> itF= tempFruits.iterator();	
				
				Packman p= temp_itP.next();
				double minTimeToFruit= 1000000000;
				
				while(itF.hasNext()) {
					fruits runner= itF.next();
					double time= p.getGps_p().distance2D(runner.getGps_f());
					time= time/p.getSpeed_weight();
					if(time<= minTimeToFruit) {
						minTimeToFruit= time;
						p.setTimeToFruit(time);
						p.setCloseFruit(runner);
					}
				}	
				
				if(p.getTimeToFruit()<= min) {
					min= p.getTimeToFruit();
					bestPac= p;
				}
			}
			
			double time= bestPac.getTimeToFruit();
			bestPac.setGps_p(bestPac.getCloseFruit().getGps_f());
			bestPac.setTime(time);
			bestPac.getMypath().add(bestPac.getCloseFruit().getGps_f());
			bestPac.getMypath().addPointTime(time);
			int i= tempFruits.indexOf(bestPac.getCloseFruit());
			tempFruits.remove(i);

			bestPac.addScore(bestPac.getCloseFruit().getSpeed_weight());
		}
		
		Iterator<Packman> temp= tempPackmans.iterator();
		while(temp.hasNext()) {
			Packman p= temp.next();
			Paths.add(p.getMypath());
		}
		addScores(game, tempPackmans);
	}
	
	public ArrayList<Path> getSolution(){
		return Paths;
	}

	public void addScores(game game, ArrayList<Packman> tempPackmans) {
		
		for(int i=0; i<tempPackmans.size(); i++) {
			game.getPackman().get(i).setScore(tempPackmans.get(i).getScore());
			
		}
	}
	

}
