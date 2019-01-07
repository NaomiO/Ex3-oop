package Packman_game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import Packman_game.Path;
import Geom.Point3D;

/**
 * 
 * @author  Shalhevet and Naomi
 *
 */

public class game extends ArrayList<Object> {
	/**
	 * A class that includes a collection of fruit and a collection of robots, the department has the
	 * ability to be built From csv file and save its information to such file.
	 */

	public ArrayList<Packman> p = new ArrayList<>();
	public ArrayList<fruits> f = new ArrayList<>();
	public String file_directory;
	public map Map1 = new map();

	/**
	 *  constructor
	 */
	public game() {
	
	}

	/**
	 *  constractor who gets two ArrayLists
	 * @param f
	 * @param p
	 */
	public game(ArrayList<fruits> f, ArrayList<Packman> p) {
		Iterator<fruits> iter1 = f.iterator();
		Iterator<Packman> iter2 = p.iterator();
		while (iter1.hasNext()) {
			fruits fruit = iter1.next();
			this.f.add(fruit);
		}
		while (iter2.hasNext()) {
			Packman packman1 = iter2.next();
			this.p.add(packman1);
		}
	}

	/**
	 * Return function of file_directory
	 * @return
	 */

	public String getDiractroy() {
		return this.file_directory;
	}
	/**
	 *  Set function of file_directory
	 * @param file_directory
	 */

	public void setFile_directory(String file_directory) {
		this.file_directory = file_directory;
	}

	/**
	 * Return function of
	 * @return ArrayList<fruits>
	 */

	public ArrayList<fruits> getfruits() {
		return f;
	}
	/**
	 *  Set function of ArrayList<fruits>
	 * @param fruits
	 */

	public void setFruits(ArrayList<fruits> fruits) {
		this.f = fruits;
	}
	/**
	 * Return function of ArrayList<Packman>
	 * @return
	 */
	public ArrayList<Packman> getPackman() {
		return p;
	}
	/**
	 *  Set function of ArrayList<Packman
	 * @param packman
	 */

	public void setPackmans(ArrayList<Packman> packman) {
		this.p = packman;
	}

	//	public String toString() {
	//		return "game [p=" + p + ", f=" + f + ", file_directory=" + file_directory + ", Map1=" + Map1 + "]";
	//	}
	//----------------------------------------------------------------------------------------
	public void CsvSave() {
		/**
		 * The function accepts fruits and packman and saves the data in a csv file
		 */

		//https://stackoverflow.com/questions/30073980/java-writing-strings-to-a-csv-file

		PrintWriter pw = null;
		StringBuilder builder = new StringBuilder();

		try {
			pw = new PrintWriter(new File(getDiractroy()+".csv"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		builder.append("Type,");
		builder.append("Id,");
		builder.append("Lat,");
		builder.append("Lon,");
		builder.append("Alt,");
		builder.append("Speed_weight,");
		builder.append("Radius,\n");


		for (int i = 0; i < this.p.size(); i++) {

			builder.append("P");
			builder.append(',');
			builder.append(i);
			builder.append(',');
			builder.append(p.get(i).getGps_p().x());
			builder.append(',');
			builder.append(p.get(i).getGps_p().y());
			builder.append(',');
			builder.append(p.get(i).getGps_p().z());
			builder.append(',');
			builder.append(p.get(i).getSpeed_weight());
			builder.append(',');
			builder.append(p.get(i).getRadius());
			builder.append('\n');
		}

		for (int i = 0; i < this.f.size(); i++) {
			builder.append("F");
			builder.append(',');
			builder.append(i);
			builder.append(',');
			builder.append(f.get(i).getGps_f().x());
			builder.append(',');
			builder.append(f.get(i).getGps_f().y());;
			builder.append(',');
			builder.append(f.get(i).getGps_f().z());
			builder.append(',');	
			builder.append(f.get(i).getSpeed_weight());
			builder.append('\n');

		}

		builder.append('\n');

		pw.write(builder.toString());

		pw.close();

	}
	//----------------------------------------------------------------------------------------
	public  void CsvRead(String csvFile){
		/**
		 * The function receives a csv file and reads it
		 */

		String line = "";
		String cvsSplitBy = ",";
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) 
		{	
			line = br.readLine();

			while ((line = br.readLine()) != null) 
			{
				String[] AllData = line.split(cvsSplitBy);
				if (AllData[0].equals("P")) {
					double Lat = Double.parseDouble(AllData[2]);
					double Lon = Double.parseDouble(AllData[3]);
					double Alt = Double.parseDouble(AllData[4]);
					map test=new map();
					Point3D return_pac=	test.GPStoPixels(new Point3D(Lat,Lon,Alt));
					double speed_weight = Double.parseDouble(AllData[5]);
					double radius = Double.parseDouble(AllData[6]);
					Packman p_new = new Packman((int)radius, (int)speed_weight, return_pac ,return_pac) ;
					p.add(p_new);



				}
				if (AllData[0].equals("F")) {
					double Lat = Double.parseDouble(AllData[2]);
					double Lon = Double.parseDouble(AllData[3]);
					double Alt = Double.parseDouble(AllData[4]);
					map test=new map();
					Point3D return_fruit=	test.GPStoPixels(new Point3D(Lat,Lon,Alt));

					double speed_weight = Double.parseDouble(AllData[5]);
					fruits fruits_new =	new fruits(speed_weight,return_fruit) ;
					f.add(fruits_new);
				}
			}
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return;
	}

	//----------------------------------------------------------------------------------------



	public void saveTokml(String out ,ArrayList<Path> paths) throws FileNotFoundException {
		PrintWriter writer = null;
		
		ArrayList<String> color= new ArrayList<>();
		String [] tag = {"Red" , "Yellow", "Blue","Green","Purple","Orange", "Brown", "Pink"};
		
		try {
			writer = new PrintWriter(new File(out+".kml"));
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		writer.println("<kml xmlns=\"http://www.opengis.net/kml/2.2\">");
		writer.println("<Document>");
		writer.println("<Folder>");
		int i=0 ,j=1 , pacmanIndex = 0;
		writer.println("<name>Paths</name>");

		for (Path current : paths) {
			writer.println("<name>Paths["+(i++)+"]</name>");
			writer.println("<Folder>");
			writer.println("<Style id=\"getcolor\">");
			writer.println("<LineStyle>");
			String s =get_color();
			color.add(s);
			writer.println("<color>"+s+"</color>");
			writer.println("<width>4</width>");
			writer.println( "</LineStyle>");
			writer.println("</Style>");
			writer.println("<Placemark>");
			writer.println("<name>Absolute Extruded</name>");
			writer.println("<styleUrl>#getcolor</styleUrl>");
			writer.println("<LineString>");
			writer.println("<coordinates>");

			for(int pl = 0; pl < current.size();pl++) {
				Point3D point = current.get(pl);
				writer.println(""+point.y()+","+point.x()+","+point.z());

			}
			writer.println("</coordinates>");
			writer.println("</LineString>");
			writer.println("</Placemark>");
			writer.println("</Folder>");
		}
		i=0;
		int c=0;
		int po=1;
		for (Path current : paths) {
			writer.println("<Folder>");
			writer.println("<name>Path["+(i++)+"]</name>");
			String 	C2L =color.get(c++);
			double[] times = current.getTime();
			for(int placemark = 0; placemark < current.size(); placemark++) {
				Point3D point = current.get(placemark);
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				double sum =times[placemark];
				Date date = new Date((long) (sum * 1000000) + System.currentTimeMillis());
				System.out.println(date);
				System.out.println(times[placemark]);
				writer.println("<Placemark>");
				if (placemark==0) {
					writer.println("<name>Pack["+(po++)+"]</name>");

				}
				else {
				writer.println("<name>Fruit["+(j++)+"]</name>");
				}
				writer.println("<description><![CDATA[location: <b>"+point.y() +","+point.x()+"</b><br/>Date: <b>"+ df.format(date)+ "</b>]]></description>");

				writer.println("<Point>");
				writer.println("<coordinates>"+point.y() +","+point.x()+"</coordinates>");
				writer.println("</Point>");
				writer.println("<styleUrl>#"+C2L+"</styleUrl>");
				if (placemark!=0) {
				writer.println("<TimeStamp>");
				writer.println("<when>"+df.format(date).toString().replace(" ", "T")+"</when>");
				writer.println("</TimeStamp>");}
				writer.println("</Placemark>");
				
				}
			writer.println("</Folder>");
			}
		for(int m = 0 ; m<8 ; m++) {
			String link =linkcolor(tag[m]);
			writer.println("<Style id=\""+tag[m]+"\">");
			writer.println("<IconStyle>");
			writer.println("<Icon>");
			writer.println("<href>"+link+"</href>");
			writer.println( "</Icon>");
			writer.println("</IconStyle>");
			writer.println("</Style>");
			}
		writer.println();
		writer.println("</Folder>");
		writer.println("</Document>");
		writer.println("</kml>");
		writer.close();
	}
	
	public  String get_color(){
		double f = Math.random();
		f=f*7;
		String [] color= {"ff0000ff","ff00ffff","ffff0000","ff00ff00","ff800080","ff0080ff","ff336699","ffff00ff"};
		return color[(int)f];
	}
	
	public  String linkcolor (String color) {
		int i =0;
		String linkincolor="";
		String Red="ff0000ff";
		String Yellow  ="ff00ffff";;
		String Blue ="ffff0000";
		String Green ="ff00ff00";
		String Purple ="ff800080";
		String Orange ="ff0080ff";
		String Brown ="ff336699";
		String Pink ="ffff00ff";
		if(color.equals(Red))i=0;
		if(color.equals(Yellow))i=1;
		if(color.equals(Blue))i=2;
		if(color.equals(Green))i=3;
		if(color.equals(Purple))i=4;
		if(color.equals(Orange))i=5;
		if(color.equals(Brown))i=6;
		if(color.equals(Pink))i=7;
		switch (i) {
		case 0: linkincolor= "http://maps.google.com/mapfiles/kml/paddle/red-circle.png";
		case 1: linkincolor="http://maps.google.com/mapfiles/kml/paddle/ylw-circle.png";
		case 2: linkincolor="http://maps.google.com/mapfiles/kml/paddle/blu-circle.png";
		case 3: linkincolor="http://maps.google.com/mapfiles/kml/paddle/grn-circle.png";
		case 4: linkincolor="http://maps.google.com/mapfiles/kml/paddle/purple-square.png";
		case 5: linkincolor="http://maps.google.com/mapfiles/kml/paddle/wht-blank.png";
		case 6: linkincolor="http://maps.google.com/mapfiles/kml/paddle/wht-blank.png";
		case 7: linkincolor="http://maps.google.com/mapfiles/kml/paddle/pink-circle.png";
		}
		return linkincolor;
	}
	
	
	}