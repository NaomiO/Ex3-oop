package Packman_game;

import Packman_game.fruits;
import Geom.Point3D;

public class Packman {

	private double radius;
	private double Lat;
	private double Lon;
	private double Alt;
	private double ID;
	private double Wt;
	private double speed_weight;
	private Point3D Pacman_Points;
	private Point3D gps_p;
    private double score = 0;
    private fruits closeFruit;
    private double time=0;
    private double timeToFruit;
	private Path mypath;


	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	
	/**
	 * The constructor receives radius, speed_weight,pac, gps_f
	 * @param radius
	 * @param speed_weight
	 * @param pac
	 * @param gps_p
	 */

	public Packman(int radius, int speed_weight, Point3D pac, Point3D gps_p) {
		this.radius = radius;
		this.speed_weight = speed_weight;
		this.Pacman_Points=pac;
		this.gps_p= gps_p;
		mypath=new Path();
		mypath.add(gps_p);
	}
	
	/**
	 * Return function of radius
	 * @return
	 */
	public double getRadius() {
		return radius;
	}


/**
 * Set function of radius
 * @param radius
 */
	public void setRadius(double radius) {
		this.radius = radius;
	}


/**
 * Return function of speed_weight
 * @return speed_weight
 */
	public double getSpeed_weight() {
		return speed_weight;
	}

/**
 * Set function of speed_weight
 * @param speed_weight
 */

	public void setSpeed_weight(double speed_weight) {
		this.speed_weight = speed_weight;
	}
	
	
	/**
	 * Return function of path
	 * @return path
	 */
	public Path getMypath() {
		return this.mypath;
	}
	/**
	 * Set function of path
	 * @param path
	 */
	public void setMypath(Path path1) {
		mypath = path1;
	}
	
	/**
	 * Return function of Pacman_Points
	 * @return Pacman_Points
	 */
	public Point3D getPacman_Points() {
		return Pacman_Points;
	}

	/**
	 * Set function of Pacman_Points
	 * @param Pacman_Points
	 */
	public void setPacman_Points(Point3D Pacman_Points) {
		this.Pacman_Points = Pacman_Points;
	}
	
	/**
	 * Return function of gps_p
	 * @return
	 */
	public Point3D getGps_p() {
		return gps_p;
	}
	
	/**
	 * Set function of gps_p
	 * @param gps_p
	 */
	public void setGps_p(Point3D gps_p) {
		this.gps_p = gps_p;
	}
	public double getLat() {
		return Lat;
	}

	public double getLon() {
		return Lon;
	}
	public double getAlt() {
		return Alt;
	}
	
	public double getScore() {
        return score;
    }

    public void addScore(double d) {
        score += d;
    }
	
	public void setScore(double d) {
        this.score= d;
    }
    
    public fruits getCloseFruit() {
    	return this.closeFruit;
    }
    
    public void setCloseFruit(fruits f) {
    	this.closeFruit= f;
    }
    
    public double getTime() {
    	return this.time;       // זמן שהוא כבר עבר
    }
    
    public void setTime(double t) {
    	this.time+= t;       // זמן שהוא כבר עבר
    }
    
    public double getTimeToFruit() {
    	return this.timeToFruit;
    }
    
    public void setTimeToFruit(double t) {
    	this.timeToFruit= t;
    }
	
}