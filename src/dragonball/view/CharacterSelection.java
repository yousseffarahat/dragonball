package dragonball.view;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.JViewport;

import dragonball.model.character.fighter.Fighter;
import dragonball.model.exceptions.MissingFieldException;
import dragonball.model.exceptions.UnknownAttackTypeException;
import dragonball.model.game.Game;
import dragonball.model.player.Player;

public class CharacterSelection extends JFrame {
	private Game game;
	private DragonBallWorldView view;
	private StartingScreen view7;
	private String fightern;
	private String fightertype;
	private JTextArea x;
	private JButton add;
	
	
	public CharacterSelection(Game game) {
		this.setResizable(false);
		this.game = game;
		setTitle("Character Selection");
		// setDefaultCloseOperation(EXIT_ON_CLOSE);
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
		setBounds(350, 75, 700, 525);
		BufferedImage myimage = resize(createImageIcon("bk.jpg", ""), 700, 525);
		setContentPane(new ImagePanel(myimage));
		setLayout(new GridBagLayout());
		setVisible(true);
		JLabel name = new JLabel("Name");
		name.setSize(50, 50);
		JButton returning = new JButton("Back");
		returning.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				view7 = new StartingScreen();// ********************
				view7.setVisible(true);
				dispose();
			}

		});
		x = new JTextArea(2, 30);
		Font font = new Font("Times New Roman", Font.BOLD, 20);
		x.setFont(font);
		x.setLineWrap(true);
		// final JScrollPane scrollPane = new JScrollPane(x);

		add = new JButton("Create");
		add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// JViewport view2 = scrollPane.getViewport();
				// JTextArea x = (JTextArea) view2.getComponent(0);

				String fightername = x.getText();
				// System.out.println(fightername);
				fightern = fightername;
				if (fightertype != null && fightern != null
						&& !x.getText().isEmpty()) {
					char c = fightertype.charAt(0);
					Player p = game.getPlayer();
					p.createFighter(c, fightern);
					view = new DragonBallWorldView(p, game);
					view.setVisible(true);
					dispose();
				} else if (x.getText().isEmpty()) {
					JOptionPane.showMessageDialog(new JFrame(),
							"Please enter a fighter name.");
				} else {
					JOptionPane.showMessageDialog(new JFrame(),
							"Please Select a fighter .");
				}
			}

		});
		// ------------------------------------------------------------------------------------
		JToggleButton ebox = new JToggleButton();
		ebox.setBorder(null);
		ebox.setContentAreaFilled(false);
		ebox.setIcon(createImageIcon("earthling1.png", ""));
		ebox.setSelectedIcon(createImageIcon("earthling1.png", ""));
		ebox.setRolloverIcon(createImageIcon("kr.png", ""));
		ebox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fightertype = "E";
			}

		});
		// ------------------------------------------------------------------------------------
		JToggleButton sbox = new JToggleButton();
		sbox.setBorder(null);
		sbox.setContentAreaFilled(false);
		sbox.setIcon(createImageIcon("Goku.png", ""));
		sbox.setRolloverIcon(createImageIcon("Gss.png", ""));
		sbox.setSelectedIcon(createImageIcon("Gss.png", ""));
		sbox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fightertype = "S";
			}

		});
		// ------------------------------------------------------------------------------------
		JToggleButton nbox = new JToggleButton();
		nbox.setBorder(null);
		nbox.setContentAreaFilled(false);
		nbox.setIcon(createImageIcon("Namekians1.png", ""));
		nbox.setRolloverIcon(createImageIcon("pic.png", ""));
		nbox.setSelectedIcon(createImageIcon("pic.png", ""));

		nbox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fightertype = "N";
			}

		});
		// ------------------------------------------------------------------------------------
		JToggleButton fbox = new JToggleButton();
		fbox.setBorder(null);
		fbox.setContentAreaFilled(false);
		fbox.setIcon(createImageIcon("freiza1.png", ""));
		fbox.setRolloverIcon(createImageIcon("Golden_Frieza.png", ""));
		fbox.setSelectedIcon(createImageIcon("Golden_Frieza.png", ""));
		fbox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fightertype = "F";
			}

		});
		// ------------------------------------------------------------------------------------
		JToggleButton mbox = new JToggleButton();
		mbox.setBorder(null);
		mbox.setContentAreaFilled(false);
		mbox.setIcon(createImageIcon("majin.png", ""));
		mbox.setRolloverIcon(createImageIcon("ma.png", ""));
		mbox.setSelectedIcon(createImageIcon("ma.png", ""));
		mbox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fightertype = "M";
			}

		});

		GridBagConstraints c = new GridBagConstraints();
		c.ipady = 30;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 0;
		name.setFont(font);
		name.setOpaque(true);
		add(name, c);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.5;
		c.gridx = 2;
		c.gridy = 0;

		add(x, c);

		c.weightx = 0.5;
		c.gridx = 3;
		c.gridy = 0;
		add.setFont(font);
		add.setOpaque(false);
		add(add, c);

		c.weightx = 0.5;
		c.gridx = 4;
		c.gridy = 0;
		add.setFont(font);
		returning.setOpaque(false);
		add(returning, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(10, 0, 0, 0);
		c.ipady = 10;
		c.weightx = 0.05;
		c.gridx = 1;
		c.gridy = 1;
		ebox.setOpaque(false);
		add(sbox, c);
		c.fill = GridBagConstraints.HORIZONTAL;

		c.weightx = 0.5;
		c.gridx = 2;
		c.gridy = 1;
		add(fbox, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 3;
		c.gridy = 1;
		nbox.setOpaque(false);
		add(mbox, c);
		c.fill = GridBagConstraints.HORIZONTAL;

		c.weightx = 0.5;
		c.gridx = 4;
		c.gridy = 1;
		fbox.setOpaque(false);
		add(nbox, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 5;
		c.gridy = 1;
		mbox.setOpaque(false);
		add(ebox, c);
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

	public static BufferedImage resize(ImageIcon image, int width, int height) {
		BufferedImage bi = new BufferedImage(width, height,
				BufferedImage.TRANSLUCENT);
		Graphics2D g2d = (Graphics2D) bi.createGraphics();
		g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY));
		g2d.drawImage(image.getImage(), 0, 0, width, height, null);
		g2d.dispose();
		return bi;
	}

	public static void main(String[] args) throws MissingFieldException,
			UnknownAttackTypeException {
		Game x = new Game();
		new CharacterSelection(x);
	}

	public void setx(Boolean y) {
		x.setVisible(y);
	}

}
