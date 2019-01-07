package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import Coords.MyCoords;
import Packman_game.Path;
import Geom.Point3D;
import Packman_game.fruits;
import Packman_game.Packman;
import Packman_game.ShortestPathAlgo;
import Packman_game.game;
import Packman_game.map;
 


public class MainWindow extends JFrame implements MouseListener
{
	private boolean ans=false;
	game game=new game();
	map map;
	ArrayList<Path> arr;

	public ArrayList<Packman>Pac_array=new ArrayList<>();
	public ArrayList<fruits>Fruits_array=new ArrayList<>();
	public int Mygame_run=0;
	public BufferedImage myImage;
	public BufferedImage Fruit;
	public BufferedImage Pacman;



	public MainWindow() 
	{
		initGUI();		
		this.addMouseListener(this); 
	}

	private ArrayList<Path> copyArray(ArrayList a){
		ArrayList<Path> arrM = new ArrayList<Path>();
		for(int i=0; i<a.size(); i++) {
			arrM.add((Path) a.get(i));
		}

		return arrM; 
	}

	private void initGUI()
	{
		
		MenuBar menuBar = new MenuBar();
		Menu menu = new Menu("game"); 
		Menu menu1 = new Menu("file"); 
		MenuItem pacman = new MenuItem("add a pacman");
		MenuItem fruit = new MenuItem("add a fruit");
		MenuItem run = new MenuItem("run the game");
		MenuItem load = new MenuItem("load a file");
		MenuItem csv = new MenuItem("save csv");
		MenuItem clear = new MenuItem("clear the screen");
		MenuItem exit = new MenuItem("exit");
		MenuItem kml = new MenuItem("save to kml");


		menuBar.add(menu);
		menuBar.add(menu1);

		menu.add(pacman);
		menu.add(fruit);
		menu.add(run);
		menu1.add(load);
		menu1.add(csv);
		menu.add(clear);
		menu.add(exit);
		menu1.add(kml);


		this.setMenuBar(menuBar);
	

	pacman.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Mygame_run=-1;
			repaint();
		}
	});



	fruit.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Mygame_run=1;
			repaint();
		}
	});
	
	
	
	run.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent listnerPlay) {
			if (listnerPlay.getActionCommand().equals("run the game")) {
				Mygame_run = 3; 
				if (ans) {
				for (int i = 0; i < game.p.size(); i++) {
					Point3D temp_point=map.PixelstoGPS(new Point3D(game.p.get(i).getPacman_Points().x()/getWidth() , game.p.get(i).getPacman_Points().y()/getHeight(), 0));
					game.p.get(i).setPacman_Points(temp_point);
				} 
				for (int i = 0; i < game.f.size(); i++) {
					Point3D temp_point=map.PixelstoGPS(new Point3D(game.f.get(i).getfruit_Points().x()/getWidth() , game.f.get(i).getfruit_Points().y()/getHeight(), 0));
					game.f.get(i).setfruit_Points(temp_point);
				}
				}
				arr= new ShortestPathAlgo(game).getSolution();      ///////////////
				repaint();
			}
		}
	});
	
	
	load.addActionListener(new ActionListener() {

		public void actionPerformed(ActionEvent e) {

			JFileChooser file = new JFileChooser();
			file.setCurrentDirectory(new File(System.getProperty("user.home")));
			file.setDialogTitle("Choose a file");
			file.setAcceptAllFileFilterUsed(false);
			int val = file.showOpenDialog(null);
			FileNameExtensionFilter csv = new FileNameExtensionFilter("csv","CSV");
			file.addChoosableFileFilter(csv);


			if (val == JFileChooser.APPROVE_OPTION) {
				game temp_game = new game(Fruits_array,Pac_array);


				temp_game.CsvRead(file.getSelectedFile().getPath());
				

				for (int i = 0; i < temp_game.p.size(); i++) {
					Point3D temp_point=new Point3D(temp_game.p.get(i).getPacman_Points().x()/getWidth() , temp_game.p.get(i).getPacman_Points().y()/getHeight(), 0);
					temp_game.p.get(i).setPacman_Points(temp_point);
				} 
				for (int i = 0; i < temp_game.f.size(); i++) {
					Point3D temp_point=new Point3D(temp_game.f.get(i).getfruit_Points().x()/getWidth() , temp_game.f.get(i).getfruit_Points().y()/getHeight(), 0);
					temp_game.f.get(i).setfruit_Points(temp_point);
				
				}
				game.p = temp_game.p;
				game.f = temp_game.f;

				game.file_directory = temp_game.file_directory;
				Mygame_run=2;
				 ans = true;
				repaint();


			}
		}
	});
	
	
	csv.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent saveGame) {
			if (saveGame.getActionCommand().equals("save csv")) {
					game.CsvSave();
			}
		}});
	
	

	
	clear.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent listenClear) {
			if (listenClear.getActionCommand().equals("Clear")) {
				Mygame_run = 4; 
				game.f.clear();
				game.p.clear();
				repaint();
				if(arr!=null)
					arr.clear();
			}
		}});
	
	
	exit.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent saveGame) {
			if (saveGame.getActionCommand().equals("exit")) {
				System.exit(0);
			}
		}});
	
	
	kml.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent saveGame) {
			if (saveGame.getActionCommand().equals("save to kml")) {
				ArrayList<Path> a= new ShortestPathAlgo(game).getSolution();    //////
				try {
					game.saveTokml("kmlFile", a);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}});
	

	try {
		myImage = ImageIO.read(new File("PNGFiles/Ariel1.png"));
		Pacman = ImageIO.read(new File("PNGFiles/pacman.png"));
		Fruit =  ImageIO.read(new File("PNGFiles/fruit.png"));
	} catch (IOException e) {
		e.printStackTrace();
	}	
	
	}
	
	
	public void paint(Graphics g)
	{
		Image temp=myImage.getScaledInstance(this.getWidth(), this.getHeight(),myImage.SCALE_SMOOTH);
		g.drawImage(temp, 0, 0,this.getWidth(),this.getHeight(),null);

		double x1=0;
		double y1=0;
		Color line = new Color(255, 255, 255); //color white

		if(Mygame_run!=4) {

			for (int i = 0; i < game.getPackman().size(); i++) {

				x1=(game.getPackman().get(i).getPacman_Points()).x()*getWidth();
				y1=(game.getPackman().get(i).getPacman_Points()).y()*getHeight();


				g.drawImage(Pacman,(int)x1,(int)y1,40,40,null);



			}

			for (int i = 0; i < game.getfruits().size(); i++) {

				x1=( game.getfruits().get(i).getfruit_Points().x())*getWidth();
				y1=(game.getfruits().get(i).getfruit_Points().y())*getHeight();


				g.drawImage(Fruit,(int)x1,(int)y1,35,35,null);



			}
		}
		
		
		if(Mygame_run==3 && ans != true) {
			Iterator<Path> it1= arr.iterator();          
			while(it1.hasNext()) { 
				Path path = it1.next();
				for(int i=1; i<path.size();i++) {
					Point3D sec = path.get(i);
					sec= map.GPStoPixels(sec);
					Point3D first = path.get(i-1);
					first = map.GPStoPixels(first);
					g.setColor(line);
					g.drawLine((int)first.x(),(int) first.y(),(int) sec.x(), (int)sec.y());

			}	
		}
	}
	}

	
	@Override
	public void mouseClicked(MouseEvent arg) {

		double x=arg.getX();

		double y=arg.getY();
		map map = new map();
		Point3D gps_add= map.PixelstoGPS(new Point3D(x,y));
		x=x/getWidth();
		y=y/getHeight();


		Point3D add_point=new Point3D(x, y);
		if (Mygame_run==1)
		{

			game.getfruits().add(new fruits(1,add_point,gps_add));
			repaint();
		}
		if (Mygame_run==-1)
		{
			game.getPackman().add(new Packman(1, 1, add_point,gps_add));
			repaint();
		}


		x = arg.getX();

		y = arg.getY();

		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
}