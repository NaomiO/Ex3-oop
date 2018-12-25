package Packman_game;

import Geom.Point3D;
/**
 * 
 * @author Shalhevet and Naomi
 *
 */

public class fruits {
	
/**
 * A class that represents a "target" in a known geographic location
 */
	private double speed_weight;

	Point3D fruit_Points;
	private Point3D gps_f;

/**
 * The constructor receives speed_weight and fruit_Points
 * @param speed_weight
 * @param fruit_Points
 */
	public fruits(double speed_weight,Point3D fruit_Points) {
		this.speed_weight = speed_weight;
		this.fruit_Points = fruit_Points;

	}
	
	/**
	 * The constructor receives fruit_Points
	 * @param fruit_Points
	 */

	public fruits(Point3D fruit_Points) {
		this.speed_weight = 1;
		this.fruit_Points = fruit_Points;
	}
	/**
	 * The constructor receives speed_weight ,fruit_Points and gps_f
	 * @param speed_weight
	 * @param fruit_Points
	 * @param gps_f
	 */
	public fruits(int speed_weight, Point3D fruit_Points ,Point3D gps_f) {
		this.speed_weight = speed_weight;
		this.fruit_Points = fruit_Points;
		this.gps_f= gps_f;

	}

	/**
	 * Return function of fruit_Points
	 * @return fruit_Points
	 */
	public Point3D getfruit_Points() {
		return fruit_Points;
	}

	/**
	 * Set function of Point3D
	 * @param points
	 */
	public void setfruit_Points(Point3D points) {
		fruit_Points = points;
	}

/**
 * Return function of speed_weight
 * @return speed_weight
 */
	public double getSpeed_weight() {
		return speed_weight;
	}

/**
 *  Set function of speed_weight
 * @param speed_weight
 */
	public void setSpeed_weight(double speed_weight) {
		this.speed_weight = speed_weight;
	}
	public String toString() {
		return "fruits [speed_weight=" + speed_weight + ", fruit_Points=" + fruit_Points + ", gps_f=" + gps_f
				+ ", getfruit_Points()=" + getfruit_Points() + ", getSpeed_weight()=" + getSpeed_weight() + ", getGps_f()="
				+ getGps_f() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	/**
	 * Return function of gps
	 * @return gps_f
	 */
	public Point3D getGps_f() {
		return gps_f;
	}
	/**
	 * Set function of Point3D gps
	 * @param gps_f
	 */
	public void setGps_f(Point3D gps_f) {
		this.gps_f = gps_f;
	}
	
}