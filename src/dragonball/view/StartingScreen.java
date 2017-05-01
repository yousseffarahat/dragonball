package dragonball.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import dragonball.model.exceptions.MissingFieldException;
import dragonball.model.exceptions.UnknownAttackTypeException;
import dragonball.model.game.Game;

public class StartingScreen extends JFrame {

	private Game game;
	private JTextArea name;
	private CharacterSelection view;
	private DragonBallWorldView view7;

	public StartingScreen() {
		this.setResizable(false);
		setTitle("DragonBall Z");
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowListener() {
		    @Override
		    public void windowClosing(WindowEvent we)
		    { 
		        String ObjButtons[] = {"Yes","No"};
		        int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to exit? \n All unsaved data will be lost","Exit Confirmation",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
		        if(PromptResult==JOptionPane.YES_OPTION)
		        {
		            dispose();
		        }
		    }

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		setBounds(250, 10, 800, 450);
		setVisible(true);
		setLayout(null);

		JButton box = new JButton("New Game");
		box.setBounds(30, 310, 160, 40);
		getContentPane().add(box);
		JButton box2 = new JButton("Load Game");
		JButton go = new JButton(createImageIcon("go1.png", ""));
		go.setBorder(null);
		go.setContentAreaFilled(false);
		go.setVisible(false);
		go.setBounds(330, 310, 50, 40);
		getContentPane().add(go);
		box2.setBounds(30, 355, 160, 40);
		add(box2);
		name = new JTextArea(1, 30);
		name.setBounds(200, 310, 120, 40);
		name.setVisible(false);
		add(name);
		final JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Please load your game");
		box.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				box.setText("Enter Player's name :");
				box.setEnabled(false);
				box2.setEnabled(false);
				go.setVisible(true);
				name.setVisible(true);
				try {
					game = new Game();
				} catch (MissingFieldException | UnknownAttackTypeException e1) {
					e1.printStackTrace();
				}
				go.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if (!name.getText().isEmpty()) {
							game.getPlayer().setName(name.getText());
							view = new CharacterSelection(game);// ***********************
							view.setVisible(true);
							dispose();
						}
					}
				});
			}

		});
		box2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (fc.showOpenDialog(box2) == JFileChooser.APPROVE_OPTION) {

				}
				try {
					if(fc.getSelectedFile().getAbsolutePath()!=null){
					game.load(fc.getSelectedFile().getAbsolutePath());
					view7 = new DragonBallWorldView(game.getPlayer(), game);// should
																			// be
																			// charcter
																			// selection
					view7.setVisible(true);
					view7.setFlag(true);
					dispose();
					}
				} catch (ClassNotFoundException | IOException f) {
					// TODO Auto-generated catch block
					f.printStackTrace();
				}
			}

		});
		getContentPane().add(
				new ImagePanel(createImageIcon("login.jpg", "").getImage()));
		revalidate();
		repaint();
	}

	public ImageIcon createImageIcon(String path, String description) {
		java.net.URL imgURL = getClass().getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	public static void main(String[] args) {
		new StartingScreen();
	}
}
