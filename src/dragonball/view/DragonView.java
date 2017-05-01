package dragonball.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
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
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SpringLayout.Constraints;

import dragonball.model.dragon.*;
import dragonball.model.game.Game;
import dragonball.model.player.Player;
import dragonball.model.player.PlayerListener;

public class DragonView extends JFrame implements PlayerListener {
	private Game game;
	private JButton item1;
	private JButton item2;
	private JButton item3;
	private JButton item4;
	private Dragon dragon;

	public DragonView(Game game, Dragon dragon,DragonBallWorldView view) throws IOException {
		super();
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
		this.setResizable(false);
		this.game = game;
		this.dragon=dragon;
		setVisible(true);
		setBounds(350, 70, 800, 525);
		BufferedImage myimage = resize(createImageIcon("db.png", ""), 800, 525);
		setContentPane(new ImagePanel(myimage));
		setLayout(new GridBagLayout());
		JLabel text = new JLabel("You have summoned "+dragon.getName());
		text.setForeground(Color.white);
		GridBagConstraints z = new GridBagConstraints();
		z.weightx = 1.0;
		z.weighty = 1.0;
		z.gridx = 0;
		z.gridy = 0;
		z.anchor = GridBagConstraints.NORTH;
		getContentPane().add(text, z);
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		DragonWish[] x = dragon.getWishes();
		c.insets = new Insets(10, 10, 10, 10);
		DragonWish y1= x[0];
		item1 = new JButton("1).Update your Senzu Beans by "+y1.getSenzuBeans());
		c.gridx = 4;
		c.gridy = 2;
		panel.add(item1, c);
		DragonWish y2 = x[1];
		item2 = new JButton("2).Update your Ability Points by "+y2.getAbilityPoints());
		c.gridx = 4;
		c.gridy = 3;
		panel.add(item2, c);
		DragonWish y3 = x[2];
		item3 = new JButton("3).Unlock "+y3.getSuperAttack().getName()+" with damage "+y3.getSuperAttack().getDamage());
		c.gridx = 4;
		c.gridy = 4;
		panel.add(item3, c);
		DragonWish y4 = x[3];
		item4 = new JButton("4).Unlock "+y4.getUltimateAttack().getName()+" with damage "+y4.getUltimateAttack().getDamage());
		c.gridx = 4;
		c.gridy = 5;
		panel.add(item4, c);
		panel.setOpaque(false);
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = c.gridy = 0;
		getContentPane().add(panel, c);
		item1.addActionListener(new ActionListener() {
			 
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				game.getPlayer().chooseWish(y1);
				JFrame x = new JFrame("Congratulations "
						+ "You have added" +y1.getSenzuBeans()+" Senzu Beans.");
				x.setVisible(true);
				x.setBounds(400, 150, 500, 375);
				x.getContentPane().add(
						new ImagePanel(createImageIcon("senzubeans.jpg", "")
								.getImage()));
				x.getContentPane().setLayout(new BorderLayout());
				JPanel buttonpanel = new JPanel();
				buttonpanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
				JButton back = new JButton("Back to World Mode");
				buttonpanel.add(back);
				x.getContentPane().add(buttonpanel, BorderLayout.NORTH);
				back.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						x.setVisible(false);
						view.setVisible(true);
						view.updatesb();
						view.updatedb();
						//new DragonBallWorldView(game.getPlayer(), game);
					}
				});
			}
		});
		item2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				game.getPlayer().chooseWish(y2);
				JFrame x = new JFrame("Congratulations "
						+ "You have added "+y2.getAbilityPoints()+" ability points.");
				x.setVisible(true);
				x.setBounds(400, 150, 500, 375);
				x.getContentPane().add(
						new ImagePanel(
								createImageIcon("ability points.jpg", "")
										.getImage()));
				x.getContentPane().setLayout(new BorderLayout());
				JPanel buttonpanel = new JPanel();
				buttonpanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
				JButton back = new JButton("Back to World Mode");
				buttonpanel.add(back);
				x.getContentPane().add(buttonpanel, BorderLayout.NORTH);
				back.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						x.setVisible(false);
						view.setVisible(true);
						view.updatedb();
						view.updateap();
						//new DragonBallWorldView(game.getPlayer(), game);
					}
				});
			}
		});
		item3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				game.getPlayer().chooseWish(y3);
				JFrame x = new JFrame("Congratulations, "
						+ "you have unlocked "+y3.getSuperAttack().getName()+".");
				x.setVisible(true);
				x.setBounds(400, 150, 500, 280);
				x.getContentPane().add(
						new ImagePanel(createImageIcon("superattack.jpg", "")
								.getImage()));
				x.getContentPane().setLayout(new BorderLayout());
				JPanel buttonpanel = new JPanel();
				buttonpanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
				JButton back = new JButton("Back to World Mode");
				buttonpanel.add(back);
				x.getContentPane().add(buttonpanel, BorderLayout.NORTH);
				back.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						x.setVisible(false);
						view.setVisible(true);
						view.updatedb();
						//new DragonBallWorldView(game.getPlayer(), game);
					}
				});
			}
		});
		item4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				game.getPlayer().chooseWish(y4);
				JFrame x = new JFrame("Congratulations, "
						+ "you have unlocked "+y4.getUltimateAttack().getName()+".");
				x.setVisible(true);
				x.setBounds(400, 150, 500, 280);
				x.getContentPane().add(
						new ImagePanel(createImageIcon("ulltive.jpg", "")
								.getImage()));
				x.getContentPane().setLayout(new BorderLayout());
				JPanel buttonpanel = new JPanel();
				buttonpanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
				JButton back = new JButton("Back to World Mode");
				buttonpanel.add(back);
				x.getContentPane().add(buttonpanel, BorderLayout.NORTH);
				back.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						x.setVisible(false);
						view.setVisible(true);
						view.updatedb();
						//new DragonBallWorldView(game.getPlayer(), game);
					}
				});
			}
		});
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

	public static void main(String[] args) {
	//	try {
		//	new DragonView(new Game());
		//} catch (IOException e) {
		//	e.printStackTrace();
		//}
	}

	@Override
	public void onDragonCalled() {
		game.onDragonCalled();

	}

	@Override
	public void onWishChosen(DragonWish wish) {
		game.getPlayer().chooseWish(wish);
		game.onWishChosen(wish);

	}
}
