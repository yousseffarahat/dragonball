package dragonball.controller;

import javax.swing.JFrame;

import dragonball.model.game.Game;
import dragonball.view.CharacterSelection;
import dragonball.view.DragonBallWorldView;
import dragonball.view.StartingScreen;

public class DragonballGUI extends JFrame{

	private DragonBallWorldView view;
	private StartingScreen screen;
	private CharacterSelection Cselection;
	private Game game;
	public DragonballGUI(){
		//view = new DragonBallWorldView();
		//view.setVisible(true);
		screen=new StartingScreen();
		screen.setVisible(true);
		//Cselection = new CharacterSelection();
		//Cselection.setVisible(true);
	}
	public static void main(String[]args){
		new DragonballGUI();
		
	}
	

}
