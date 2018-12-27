package Packman_game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import Geom.Point3D;

/**
 * 
 * @author  Shalhevet and Naomi
 *
 */

public class game {
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
		this.f = new ArrayList<fruits>();
		this.p = new ArrayList<Packman>();
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
					Packman p_new = new Packman(radius, speed_weight, return_pac) ;
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



	public void saveTokml() throws FileNotFoundException {
		/**
		 * The function accepts fruits and packman and saves the data in a kml file
		 */

		ShortestPathAlgo test = new ShortestPathAlgo (this);
		PrintWriter Print_kml_end = new PrintWriter(new File(getDiractroy()+".kml"));
		p = test.ShortestPath(this);
//		p.addAll(test.ShortestPath(this));

		ArrayList<String> content = new ArrayList<String>();
		String kmlstart = 
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
						"<kml xmlns=\"http://www.opengis.net/kml/2.2\"><Document>\r\n<name> Points with TimeStamps</name>\r\n <Style id=\"red\">\r\n" + 
						"<IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/red-dot.png</href></Icon></IconStyle>\r\n" + 
						"</Style><Style id=\"Packman\"><IconStyle><Icon><href>http://www.iconhot.com/icon/png/quiet/256/pac-man.png</href></Icon></IconStyle>\r\n" + 
						"</Style><Style id=\"Fruit\"><IconStyle><Icon><href>http://www.stickpng.com/assets/images/580b57fcd9996e24bc43c316.png</href></Icon></IconStyle></Style>\r\n" + 
						"\r\n" + 
						"    <Style id=\"check-hide-children\">\r\n" + 
						"      <ListStyle>\r\n" + 
						"        <listItemType>checkHideChildren</listItemType>\r\n" + 
						"      </ListStyle>\r\n" + 
						"    </Style>\r\n" + 
						"    <styleUrl>#check-hide-children</styleUrl>"+
						"\r\n"+"<Folder><name>GAME PACKMAN</name>\n\n";

		content.add(kmlstart);
		String[] Name_of_data_reader = {"Type","id","Lat","Lon","Speed/Weight"	,"Radius"};

		//content.add(kmlstart);

		String kmlend = "</Folder>\n" + 
				"</Document>\n</kml>";

		
		for (int i = 0; i < p.size(); i++) {
			map testm=new map();
			Point3D temp_point = testm.PixelstoGPS(p.get(i).getGps_p());
			p.get(i).setPacman_Points(temp_point);
		}
		
		for (int i = 0; i < f.size(); i++) {
			map testm=new map();
			Point3D temp_point = testm.PixelstoGPS(f.get(i).getfruit_Points());
			f.get(i).setfruit_Points(temp_point);
		}
		
		String kmlelement="";
		for (int i = 0; i < p.size(); i++) {

			kmlelement = kmlelement+"<Placemark>\n" +
					"<name><![CDATA["+"Packman"+"]]></name>\n" +
					"<description>"+
					"<![CDATA[B"
					+Name_of_data_reader[0]+": <b>"+"P"+" </b><br/>"
					+Name_of_data_reader[1]+": <b>"+i+" </b><br/>"
					+Name_of_data_reader[2]+": <b>"+p.get(i).getGps_p().x()+" </b><br/>" 
					+Name_of_data_reader[3]+": <b>"+p.get(i).getGps_p().y()+" </b><br/>"
					+Name_of_data_reader[4]+": <b>"+p.get(i).getSpeed_weight()+" </b><br/>" 
					+Name_of_data_reader[5]+": <b>"+p.get(i).getRadius()+" </b><br/>" 


						+"]]></description>\n" 
						+"<styleUrl>#Packman</styleUrl>"+
						"<Point>\n" +
						"<coordinates>"+p.get(i).getGps_p().y()+","+p.get(i).getGps_p().x()+"</coordinates>" +
						"</Point>\n" +
						"</Placemark>\n";





			for (int j = 0; j < p.get(i).getPath().getGPSPoints().size(); j++) {

				kmlelement =kmlelement+"<Placemark>\n" +
						"<name><![CDATA["+"fruits"+"]]></name>\n" +
						"<description>"+
						"<![CDATA[B"
						+Name_of_data_reader[0]+": <b>"+"F"+" </b><br/>"
						+Name_of_data_reader[1]+": <b>"+j+" </b><br/>"
						+Name_of_data_reader[2]+": <b>"+p.get(i).getPath().getGPSPoints().get(j).fruit_Points.x()+" </b><br/>"
						+Name_of_data_reader[3]+": <b>"+p.get(i).getPath().getGPSPoints().get(j).fruit_Points.y()+" </b><br/>"
						+Name_of_data_reader[4]+": <b>"+p.get(i).getPath().getGPSPoints().get(j).getSpeed_weight()+" </b><br/>" 


							+"]]></description>\n" +"<styleUrl>#Fruit</styleUrl>"+
							"<Point>\n" +
							"<coordinates>"+p.get(i).getPath().getGPSPoints().get(j).fruit_Points.y()+","+p.get(i).getPath().getGPSPoints().get(j).fruit_Points.x()+"</coordinates>" +
							"</Point>\n" +
							"</Placemark>\n";





			}
			content.add(kmlelement);

			content.add(kmlend);
			Print_kml_end.write(String.join("\n", content));
			Print_kml_end.close();
		}}


}