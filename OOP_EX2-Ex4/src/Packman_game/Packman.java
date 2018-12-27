package Packman_game;

import Geom.Point3D;

public class Packman {

	private double radius;
	private double speed_weight;
	private Point3D Pacman_Points;
	private Point3D gps_p;
	Path path=new Path();


	/**
	 * The constructor receives radius and speed_weight, pac
	 * @param radius
	 * @param speed_weight
	 * @param Pac
	 */

	public Packman(double radius,double speed_weight,Point3D Pac) {
		this.radius = radius;
		this.speed_weight = speed_weight;
		this.gps_p=Pac;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	/**
	 * The constructor receive Point3D
	 * @param Pac
	 */
	public Packman(Point3D Pac) {
		this.Pacman_Points = Pac;


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
	public Path getPath() {
		return path;
	}
	
	/**
	 * Set function of path
	 * @param path
	 */
	public void setPath(Path path) {
		this.path = path;
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
	public void setGps_f(Point3D gps_p) {
		this.gps_p = gps_p;
	}
	
}