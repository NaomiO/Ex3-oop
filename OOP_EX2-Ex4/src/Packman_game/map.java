package Packman_game;

import Geom.Point3D;
import Coords.MyCoords;


public class map
{

	private Point3D LeftUpCorner;
	private Point3D RightDownCorner;
	private double mapLon;
	private double mapLat;
	private double width = 1378; //=mapWidth
	private double height = 642;//=mapHeight

/**
 * constructor default
 */
	public map()
	{
			   
		this.LeftUpCorner = new Point3D(32.101869,  35.202303);
		this.RightDownCorner = new Point3D(32.105731,  35.212402);
		this.mapLon = this.RightDownCorner.y()-this.LeftUpCorner.y();
		this.mapLat = this.RightDownCorner.x()-this.LeftUpCorner.x();
	}

/**
 * The function convert pointGps to point in Pixel
 * @param pt
 * @return Point in pixel
 */
	public Point3D GPStoPixels(Point3D pt )
	{
		
		double x,y;
		
	    x=pt.y() - LeftUpCorner.y();
	    y = RightDownCorner.x()-pt.x();
	
	    int x1 = (int) (width*(x/mapLon));
	    int y1 = (int) (height*(y/mapLat));
	    return new Point3D(x1, y1);

	}


	/**
	 * The function convert pointPixel to pointGps
	 * @param pt
	 * @return point in gps
	 */
	public Point3D PixelstoGPS(Point3D pt)
    {    	
		double latX = ((pt.x()/width)*mapLon);
		double lonY = ((pt.y()/height)*mapLat);
		
		double CoordsX = RightDownCorner.x() -lonY; 
		double CoordsY = LeftUpCorner.y()+ latX;
		
		Point3D gpsPoint= new Point3D(CoordsX,CoordsY);
		return gpsPoint;
    }
	
	
    /**
     * The function calculates distance between two pointPixel
     * @param p1
     * @param p2
     * @return distance
     */
    public double distancePixels(Point3D p1, Point3D p2) {
    	
    	MyCoords coords = new MyCoords();
    	
    	Point3D p2gpsX =  PixelstoGPS(p1);
    	Point3D p2gpsY =  PixelstoGPS(p2);
    	
    	double distance = coords.distance3d(p2gpsX, p2gpsY);
    	return distance;

    }

    /**
     * The function calculates angle between twp point
     * @param p1
     * @param p2
     * @return angle 
     */

    public double Angle(Point3D p1, Point3D p2) {

		Point3D pix1= new Point3D(this.PixelstoGPS( p1));
		Point3D pix2= new Point3D(this.PixelstoGPS( p2));

		double x=Math.cos(pix2.x())*Math.sin(pix2.y()-pix1.y());
		double y=(Math.cos(pix1.x())*Math.sin(pix2.x())) - (Math.sin(pix1.x())*Math.cos(pix2.x()*Math.cos(pix2.y()-pix1.y())));

		double angle=Math.atan2(x, y);
		return angle;
	}
}