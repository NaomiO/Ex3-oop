package GUI;

import java.awt.Graphics;
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

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import Geom.Point3D;
import Packman_game.Packman;
import Packman_game.fruits;
import Packman_game.game;
import Packman_game.map;

public class MainWindow extends JFrame implements MouseListener
{
	private static final long serialVersionUID = 1L;
	public BufferedImage myImage;
	public BufferedImage Fruit;
	public BufferedImage Pacman;
	public game Mygame=new game();
	public int Mygame_run=0;
	public map map = new map();
	public ArrayList<Packman>Pac_array=new ArrayList<>();
	public ArrayList<fruits>Fruits_array=new ArrayList<>();

	MainWindow() 
	{
		initGUI();		
		this.addMouseListener(this); 
	}

	private void initGUI() 
	{
		MenuBar menuBar = new MenuBar();
		Menu menu = new Menu("Menu"); 
		MenuItem run = new MenuItem("Run Game");
		MenuItem exit = new MenuItem("Exit");

		menuBar.add(menu);
		menu.add(run);
		menu.add(exit);
		this.setMenuBar(menuBar);


		Menu add = new Menu("Add"); 
		MenuItem Add_Packman = new MenuItem("Add Packman");
		MenuItem Add_Fruit = new MenuItem("Add Fruit");


		menuBar.add(add);
		add.add(Add_Packman);
		add.add(Add_Fruit);
		this.setMenuBar(menuBar);

		Menu csv = new Menu("Csv"); 
		MenuItem readCsv = new MenuItem("read Csv");
		MenuItem saveCsv = new MenuItem("save Csv");

		menuBar.add(csv);
		csv.add(readCsv);
		csv.add(saveCsv);
		this.setMenuBar(menuBar);

		Menu Kml = new Menu("Kml"); 
		MenuItem saveToKml = new MenuItem("saveToKml");


		menuBar.add(Kml);
		Kml.add(saveToKml);
		this.setMenuBar(menuBar);




		Add_Packman.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Mygame_run=-1;
				repaint();
			}
		});




		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});


		Add_Fruit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Mygame_run=1;
				repaint();
			}
		});

		try {
			myImage = ImageIO.read(new File("PNGFiles/Ariel1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}		


		try {
			Pacman = ImageIO.read(new File("PNGFiles/pacman.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}		

		try {
			Fruit = ImageIO.read(new File("PNGFiles/fruit.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}		


		


		readCsv.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				JFileChooser file = new JFileChooser();
				file.setCurrentDirectory(new File(System.getProperty("user.home")));
				file.setDialogTitle("Choose a file");
				file.setAcceptAllFileFilterUsed(false);
				int val = file.showOpenDialog(null);
				FileNameExtensionFilter csv = new FileNameExtensionFilter("csv","CSV");
				file.addChoosableFileFilter(csv);


				if (val == JFileChooser.APPROVE_OPTION) {

					System.out.println(file.getSelectedFile().getPath());

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
					Mygame.p = temp_game.p;
					Mygame.f = temp_game.f;

					Mygame.file_directory = temp_game.file_directory;
					Mygame_run=2;

					repaint();


				}
			}
		});


		saveCsv.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Mygame.CsvSave();
				repaint();

			}
		});




		saveToKml.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					Mygame.saveTokml();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				repaint();

			}
		});



	}

	public void paint(Graphics g)
	{

		Image temp=myImage.getScaledInstance(this.getWidth(), this.getHeight(),myImage.SCALE_SMOOTH);
		g.drawImage(temp, 0, 0,this.getWidth(),this.getHeight(),null);

		double x1=0;
		double y1=0;
		if(Mygame_run!=0) {

			for (int i = 0; i < Mygame.getPackman().size(); i++) {

				x1=(Mygame.getPackman().get(i).getPacman_Points()).x()*getWidth();
				y1=(Mygame.getPackman().get(i).getPacman_Points()).y()*getHeight();


				g.drawImage(Pacman,(int)x1,(int)y1,30,30,null);



			}

			for (int i = 0; i < Mygame.getfruits().size(); i++) {

				x1=( Mygame.getfruits().get(i).getfruit_Points().x())*getWidth();
				y1=(Mygame.getfruits().get(i).getfruit_Points().y())*getHeight();


				g.drawImage(Fruit,(int)x1,(int)y1,30,30,null);



			}
		}}



	@Override
	public void mouseClicked(MouseEvent arg) {


		double x=arg.getX();

		double y=arg.getY();

		Point3D gps_add= map.PixelstoGPS(new Point3D(x,y));
		x=x/getWidth();
		y=y/getHeight();


		Point3D add_point=new Point3D(x, y);
		if (Mygame_run==1)
		{

			Mygame.getfruits().add(new fruits(1,add_point,gps_add));
			repaint();
		}
		if (Mygame_run==-1)
		{
			Mygame.getPackman().add(new Packman(1, 1, add_point,gps_add));
			repaint();
		}


		x = arg.getX();

		y = arg.getY();

		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		System.out.println("mouse entered");

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}