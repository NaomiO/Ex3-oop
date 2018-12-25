package Packman_game;

import java.util.ArrayList;
import java.util.Iterator;

import Coords.MyCoords;

public class Path {
	
private ArrayList<fruits> path_fruit;

/**
 * Return function of path_fruit
 * @return path_fruit
 */
public ArrayList<fruits> getGPSPoints() {
	return path_fruit;
}

/**
 * Set function of gPSPoints
 * @param gPSPoints
 */
public void setGPSPoints(ArrayList<fruits> gPSPoints) {
	path_fruit = gPSPoints;
}

double time;

public Path() {
	
	/**
	 * constractor default
	 */
	path_fruit=new ArrayList<fruits>();
	time = 0;
}

/**
 * The function add fruits to path
 * @param p
 */
public void add(fruits p) {
	path_fruit.add(p);
}

/**
 * The function calculates the size of the path
 * @return dis
 */
public double distance() {
	double dis = 0;
	Iterator<fruits> itr = path_fruit.iterator();
	MyCoords c = new MyCoords();
	fruits p1 = itr.next();
	while(itr.hasNext()) {
		
		fruits p2 = itr.next();
		dis += c.distance3d(p1.getfruit_Points(), p2.getfruit_Points());
		p1=p2;
		
	}
	
	return dis;
}

/**
 * The function add time to total time
 * @param temp
 */
public void addTime(double temp) {
	time+=temp;
}

/**
 * Return function of time 
 * @return
 */
public double getTime() {
	return time;
     }
}