package Coords;

import Geom.Point3D;

/**
 * This class converts radians coordinates to cartesian ones, add coordinates together, find the distance between two points 
 * @author Shalhevet and Naomi
 *
 */
public class MyCoords implements coords_converter
{
	/*radius of planet earth*/
	
	static public int EARTH_RADIUS = 6371000;//---
	final double lon_norm = 0.847091174;
//----------------------------------------------------------------------------------------------------------
	/*add*/
	
	/**
	 * 	 * This method takes two points, add one to the other and returns the new point.
	 * @param point with gps coordinates
	 * @param point with radians coordinates
	 * @return the new point
	 */
	@Override
	public Point3D add(Point3D gps, Point3D local_vector_in_meter){
		if(isValid_GPS_Point(gps)== false) {
			throw new RuntimeException("gps is invalid point");
			}
		else {
			double Norm = Math.cos(Math.toRadians(gps.x()));
			double px = Math.toDegrees( Math.asin (local_vector_in_meter.x() /( EARTH_RADIUS))) + gps.x();
			double py = Math.toDegrees(Math.asin(local_vector_in_meter.y()/ ( EARTH_RADIUS * Norm))) + gps.y();
			double pz = local_vector_in_meter.z() + gps.z();
			Point3D point = new Point3D ( px , py , pz );
			if(isValid_GPS_Point(point)== false){
				throw new RuntimeException("gps is invalid point");
					}
				else {	
				return point;  }
			}}
//------------------------------------------------------------------------------
	/*distance3d*/
	/**
	 * This method calculates the distance between  two gps points.
	 * @param gps0 first gps point.
	 * @param gps1 second gps point.
	 * @return the 3d distance of these two points.	 
	 */
	@Override
	public double distance3d(Point3D gps0, Point3D gps1)
	{
		
		if (isValid_GPS_Point(gps0)== false  || isValid_GPS_Point(gps1)==false) { 
			throw new RuntimeException("gps is invalid point");
		}
		 else { 
			double dx = gps1.x()-gps0.x(); 
			double dy = gps1.y()-gps0.y();
			dx = (dx*Math.PI)/180; 
			dy = (dy*Math.PI)/180; 
			dx = Math.sin(dx)*EARTH_RADIUS; 
			dy = Math.sin(dy)*EARTH_RADIUS*lon_norm; 
			double distance = Math.sqrt(dx*dx + dy*dy); 
		if (distance>100000000) {
				throw new RuntimeException("The distance is too large"); 
		}
		return distance;
		}}
//-----------------------------------------------------------------------------------------------------------------
	
	/*isValid*/
	/**
	 * return true if this point is a valid lat, lon , lat coordinate: [-180,+180],[-90,+90],[-450, +inf]
	 * @param p - the point
	 * @return - true if the point is valid, otherwise false
	 */
	@Override
	public boolean isValid_GPS_Point(Point3D p)
	{
	
				if (p.x()<-180 || p.x()>180 ||p.y()<-90 || p.y()>90 || p.z() < -450)
				
					return false;
				
			return true;
	}
//-----------------------------------------------------------------------------------------------------------------
	/**
	 * This method calculates the 3D vector (in meters) between two gps points 
	 * @param the first point
	 * @param the second point 
	 * @return the 3d vector
	 */
	public Point3D vector3D(Point3D gps0, Point3D gps1){
		if (isValid_GPS_Point(gps0) == false || isValid_GPS_Point(gps1) == false) { 
			throw new RuntimeException("gps is invalid point");
		}
			 else { 
			double dx = gps1.x() - gps0.x();
			double dy = gps1.y() - gps0.y(); 
			dx = (dx*Math.PI) / 180; 
			dy = (dy*Math.PI) / 180; 
			dx = Math.sin(dx) * EARTH_RADIUS;
			dy = Math.sin(dy) * EARTH_RADIUS * lon_norm; 
			double dz = gps1.z() - gps0.z(); 
			Point3D vector3D = new Point3D(dx, dy, dz);
			return vector3D;
		}
	}
//-----------------------------------------------------------------------------------------------------------------
	 /**
	  * 	This method computes the polar representation of the 3D vector between two gps points
	  *		@param the first point
	  *    @param the second point
	  *    @return the polar representation of the 3D vector between the two points
	  */
//	@Override
//	public double[] azimuth_elevation_dist(Point3D gps0, Point3D gps1) {
//		
//		double[] arr = new double[3];
//		
//		Point3D p = vector3D( gps0 , gps1 );
//		
//		if (isValid_GPS_Point(gps0) == false || isValid_GPS_Point(gps1) == false) {
//			throw new RuntimeException("gps is invalid point");
//		}
//		
//		else {
//			
//		
//			
//			if ((p.x()<0) && (p.y()<0) ) {
//				arr[0] = 180 + arr[0];
//			}
//			if (arr[0]<0) {
//				arr[0]= arr[0]+ 360;
//				}
//			if (arr[0]>360) {
//				arr[0] = arr[0] - 360;
//			}
//			
//			arr[2] = distance3d(gps0,gps1);
//			
//			arr[0] = Math.toDegrees( Math.atan( Math.abs( ( p.y()) / (p.x() ) ) ) );
//			
//			if ((p.x()<0)&&(p.y()>0)) {
//				arr[0] = 180 - arr[0];
//			}
//			if ((p.y()<0)&&(p.x()>0)) {
//				arr[0] = 360 - arr[0];
//			}
//		
//			
//			arr [1] = Math.toDegrees(Math.asin(p.z()/arr[2]));
//			
//			return arr;
//		}

		
		private double  azimuth(Point3D p0, Point3D p1) {
			double longDiff= Point3D.d2r((p1.y()-p0.y()));
			double p0_x_r=Point3D.d2r(p0.x());
			double p1_x_r=Point3D.d2r(p1.x());
			double y = Math.sin(longDiff)*Math.cos(p1_x_r);
			double x = Math.cos(p0_x_r)*Math.sin(p1_x_r)-Math.sin(p0_x_r)*Math.cos(p1_x_r)*Math.cos(longDiff);
			return Point3D.r2d(Math.atan2(y, x))+360%360;
		}

		private double  elevation(Point3D p0, Point3D p1) {
			return Point3D.r2d(Math.asin((p1.z()-p0.z())/(distance3d(p0, p1))));
	}
	
		 public double[] azimuth_elevation_dist(Point3D gps0, Point3D gps1) {
				double[] azimuth_elevation_dist=new double [3];
				azimuth_elevation_dist[0]=azimuth(gps0,gps1);
				azimuth_elevation_dist[1]=elevation(gps0,gps1);
				azimuth_elevation_dist[2]=distance3d(gps0, gps1);
				return azimuth_elevation_dist;


		 }



}
