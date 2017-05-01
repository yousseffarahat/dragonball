package dragonball.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SpringLayout.Constraints;

import dragonball.model.attack.PhysicalAttack;
import dragonball.model.attack.SuperAttack;
import dragonball.model.attack.UltimateAttack;
import dragonball.model.battle.Battle;
import dragonball.model.battle.BattleEvent;
import dragonball.model.battle.BattleEventType;
import dragonball.model.battle.BattleListener;
import dragonball.model.cell.Collectible;
import dragonball.model.character.fighter.Fighter;
import dragonball.model.character.fighter.NonPlayableFighter;
import dragonball.model.character.fighter.PlayableFighter;
import dragonball.model.character.fighter.Saiyan;
import dragonball.model.exceptions.MissingFieldException;
import dragonball.model.exceptions.NotEnoughKiException;
import dragonball.model.exceptions.NotEnoughResourcesException;
import dragonball.model.exceptions.NotEnoughSenzuBeansException;
import dragonball.model.exceptions.UnknownAttackTypeException;
import dragonball.model.game.Game;
import dragonball.model.game.GameState;
import dragonball.model.world.World;

public class BattleView extends JFrame implements BattleListener {

	private Game game;
	private Battle battle;
	private JButton[] arrayofstamina;
	private JButton[] arrayofki;
	private JButton[] arrayofstaminafoe;
	private JButton[] arrayofkifoe;
	private JMenuItem[] arrayofsu;
	private JMenuItem[] arrayofua;
	private JLabel me;
	private Fighter meFighter;
	private Fighter foeFighter;
	private JLabel foe;
	private JProgressBar x1;
	private JProgressBar x2;
	private NonPlayableFighter foeF;
	private DragonBallWorldView view;
	private final int px = 190, py = 370, fx = 600, fy = 400, pfx = 370,
			ffx = 420;
	private JLabel text1, text2, text3, text4, text5;
	private boolean bool = true;
	private JLabel mehpbar;
	private JLabel foehpbar;
	private JLabel nubofsb;
	private JLabel endturn;
	private JLabel pname;
	private JLabel foname;

	public BattleView(Game game, Battle battle, NonPlayableFighter foeF,
			DragonBallWorldView view) {
		super("Battle Mode");
		this.battle = battle;
		this.game = game;
		this.foeF = foeF;
		this.view = view;
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowListener() {
		    @Override
		    public void windowClosing(WindowEvent we)
		    { 
		        String ObjButtons[] = {"Yes","No"};
		        int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to exit? \n All unsaved data will be lost","Exit Confirmation",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
		        if(PromptResult==JOptionPane.YES_OPTION)
		        {
		            System.exit(0);
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
		battle.setListener(this);
		game.setListener(view);
		this.setResizable(false);
		setBounds(250, 30, 900, 715);
		setVisible(true);
		setLayout(null);
		arrayofstamina = new JButton[game.getPlayer().getActiveFighter()
				.getMaxStamina()];
		arrayofki = new JButton[game.getPlayer().getActiveFighter().getMaxKi()];
		arrayofstaminafoe = new JButton[foeF.getMaxStamina()];
		arrayofkifoe = new JButton[foeF.getMaxKi()];
		x1 = new JProgressBar();
		meFighter = (Fighter) battle.getMe();
		int mehp = meFighter.getHealthPoints() / meFighter.getMaxHealthPoints()
				* 100;
		//System.out.println(mehp);
		//System.out.println(meFighter.getHealthPoints());
		x1.setValue(mehp);
		x1.setForeground(Color.red);
		pname = new JLabel("Player's name : "
				+ game.getPlayer().getActiveFighter().getName() +"                                             UNTRANSFORMED");
		pname.setBounds(3, 0, 1000, 20);
		add(pname);
		foname = new JLabel("UNTRANSFORMED   " + foeF.getName());
		foname.setBounds(720, 0, 200, 20);
		add(foname);
		x1.setBounds(78, 20, 180, 15);
		add(x1);
		x2 = new JProgressBar();
		foeFighter = (Fighter) battle.getFoe();
		int foehp = foeFighter.getHealthPoints()
				/ foeFighter.getMaxHealthPoints() * 100;
		//System.out.println(foehp);
		//System.out.println(foeFighter.getHealthPoints());
		x2.setValue(foehp);
		x2.setForeground(Color.blue);
		x2.setBounds(635, 20, 180, 15);
		add(x2);
		JLabel pic = new JLabel(new ImageIcon(resize(
				createImageIcon("gofa.jpg", ""), 70, 65)));
		pic.setBounds(0, 15, 78, 70);
		add(pic);
		JLabel meculevel = new JLabel("Level : "
				+ game.getPlayer().getActiveFighter().getLevel());
		meculevel.setForeground(Color.white);
		meculevel.setBounds(5, 85, 170, 20);
		add(meculevel);
		JLabel foeculevel = new JLabel("Level : " + foeF.getLevel());
		foeculevel.setForeground(Color.white);
		foeculevel.setBounds(815, 85, 170, 20);
		add(foeculevel);
		JLabel pic1 = new JLabel(new ImageIcon(resize(
				createImageIcon("fre.jpg", ""), 70, 65)));
		pic1.setBounds(815, 15, 78, 70);
		add(pic1);
		mehpbar = new JLabel(""
				+ game.getPlayer().getActiveFighter().getHealthPoints() + "/"
				+ game.getPlayer().getActiveFighter().getMaxHealthPoints());
		mehpbar.setBounds(200, 10, 500, 10);
		add(mehpbar);
		foehpbar = new JLabel("" + foeF.getHealthPoints() + "/"
				+ foeF.getMaxHealthPoints());
		foehpbar.setBounds(635, 10, 500, 10);
		add(foehpbar);
		JLabel maxkilab = new JLabel("(MaxKi)");
		maxkilab.setBounds(135, 75, 90, 15);
		add(maxkilab);
		JLabel Currentkilabel = new JLabel("(Cur.Ki)");
		Currentkilabel.setBounds(135, 60, 90, 15);
		add(Currentkilabel);
		JButton maxk = new JButton();
		maxk.setEnabled(false);
		maxk.setBackground(Color.yellow);
		maxk.setBounds(180, 62, 10, 10);
		// add(maxk);
		JLabel Currentstalabel = new JLabel("(Cur.Stamina)");
		Currentstalabel.setBounds(135, 35, 90, 15);
		add(Currentstalabel);
		JLabel maxstalab = new JLabel("(MaxStamina)");
		maxstalab.setBounds(135, 48, 90, 15);
		add(maxstalab);
		int cordx = 80;
		for (int i = 0; i < arrayofstamina.length; i++) {
			arrayofstamina[i] = new JButton();
			arrayofstamina[i].setBackground(Color.yellow);
			arrayofstamina[i].setSize(10, 10);
			arrayofstamina[i].setEnabled(false);
			arrayofstamina[i].setLocation(cordx, 37);
			add(arrayofstamina[i]);
			JButton max = new JButton();
			max.setBackground(Color.white);
			max.setSize(10, 10);
			max.setEnabled(false);
			max.setLocation(cordx, 50);
			add(max);
			cordx += 10;
		}
		cordx = 805;
		for (int i = 0; i < arrayofstaminafoe.length; i++) {
			arrayofstaminafoe[i] = new JButton();
			arrayofstaminafoe[i].setBackground(Color.yellow);
			arrayofstaminafoe[i].setSize(10, 10);
			arrayofstaminafoe[i].setEnabled(false);
			arrayofstaminafoe[i].setLocation(cordx, 37);
			add(arrayofstaminafoe[i]);
			JButton max = new JButton();
			max.setBackground(Color.white);
			max.setSize(10, 10);
			max.setEnabled(false);
			max.setLocation(cordx, 50);
			add(max);
			cordx -= 10;
		}
		cordx = 80;
		for (int i = 0; i < arrayofki.length; i++) {
			arrayofki[i] = new JButton();
			arrayofki[i].setBackground(Color.yellow);
			arrayofki[i].setVisible(false);
			arrayofki[i].setSize(10, 10);
			arrayofki[i].setEnabled(false);
			arrayofki[i].setLocation(cordx, 62);

			add(arrayofki[i]);
			JButton max = new JButton();
			max.setBackground(Color.white);
			max.setSize(10, 10);
			max.setEnabled(false);
			max.setLocation(cordx, 75);
			add(max);
			cordx += 10;
		}
		cordx = 805;
		for (int i = 0; i < arrayofkifoe.length; i++) {
			arrayofkifoe[i] = new JButton();
			arrayofkifoe[i].setBackground(Color.yellow);
			arrayofkifoe[i].setVisible(false);
			arrayofkifoe[i].setSize(10, 10);
			arrayofkifoe[i].setEnabled(false);
			arrayofkifoe[i].setLocation(cordx, 62);

			add(arrayofkifoe[i]);
			JButton max = new JButton();
			max.setBackground(Color.white);
			max.setSize(10, 10);
			max.setEnabled(false);
			max.setLocation(cordx, 75);
			add(max);
			cordx -= 10;
		}
		me = new JLabel(new ImageIcon(resize(createImageIcon("2.png", ""), 72,
				144)));
		me.setBounds(70, 150, 72, 144);
		add(me);
		foe = new JLabel(new ImageIcon(resize(createImageIcon("3.png", ""),
				100, 125)));
		foe.setBounds(700, 350, 100, 125);
		add(foe);
		arrayofsu = new JMenuItem[4];
		for (int i = 0; i < 4; i++) {
			arrayofsu[i] = new JMenuItem("");
		}
		for (int i = 0; i < 4
				&& i < game.getPlayer().getActiveFighter().getSuperAttacks()
						.size(); i++) {

			if (game.getPlayer().getActiveFighter().getSuperAttacks().get(i) != null) {
				arrayofsu[i].setText(game.getPlayer().getActiveFighter()
						.getSuperAttacks().get(i).getName());
				SuperAttack attack = game.getPlayer().getActiveFighter()
						.getSuperAttacks().get(i);
				arrayofsu[i].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {

						try {
							battle.attack(attack);
							endturn.setText("Enemy Turn");
							// updatetext(meFighter.getName()+" has used a "+attack.getName()+" for "+attack.getAppliedDamage(foeFighter)+" damage.");
						} catch (NotEnoughKiException e1) {
							JOptionPane.showMessageDialog(new JFrame(),
									e1.getMessage());
						}
						refresh();
						try {
							battle.play();
							endturn.setText("Your Turn");
							// updatetext("tmp");
						} catch (NotEnoughKiException e) {
							JOptionPane.showMessageDialog(new JFrame(),
									e.getMessage());
						}
						refresh();
						// me.setIcon((new
						// ImageIcon(resize(createImageIcon("6.png", ""),
						// 48, 96))));
						foe.setLocation(ffx, fy);
						for (int i = 0; i < 2000; i++)
							;
						me.setLocation(pfx, fy);

					}
				});
			}
		}
		arrayofua = new JMenuItem[2];
		for (int i = 0; i < 2; i++) {
			arrayofua[i] = new JMenuItem("");
		}
		for (int i = 0; i < 2
				&& i < game.getPlayer().getActiveFighter().getUltimateAttacks()
						.size(); i++) {
			if (game.getPlayer().getActiveFighter().getUltimateAttacks().get(i) != null) {
				arrayofua[i].setText(game.getPlayer().getActiveFighter()
						.getUltimateAttacks().get(i).getName());
				UltimateAttack attack = game.getPlayer().getActiveFighter()
						.getUltimateAttacks().get(i);
				arrayofua[i].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {

						try {
							battle.attack(attack);
							endturn.setText("Enemy Turn");
							// updatetext(meFighter.getName()+" has used a "+attack.getName()+" for "+attack.getAppliedDamage(foeFighter)+" damage.");
						} catch (NotEnoughKiException e1) {
							JOptionPane.showMessageDialog(new JFrame(),
									e1.getMessage());
						}
						refresh();
						try {
							battle.play();
							endturn.setText("Your Turn");
							// updatetext("tmp");
						} catch (NotEnoughKiException e) {
							JOptionPane.showMessageDialog(new JFrame(),
									e.getMessage());
						}
						refresh();
						// me.setIcon((new
						// ImageIcon(resize(createImageIcon("6.png", ""),
						// 48, 96))));
						foe.setLocation(ffx, fy);
						for (int i = 0; i < 2000; i++)
							;
						me.setLocation(pfx, fy);

					}
				});
			}
		}
		JMenuBar bar = new JMenuBar();
		JMenu attack = new JMenu("      Attack");
		JMenuItem phpa = new JMenuItem("1).PhysicalAttack");
		phpa.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				PhysicalAttack pa = new PhysicalAttack();
				try {
					battle.attack(pa);
					endturn.setText("Enemy Turn");
					// updatetext(meFighter.getName()+" has used a physical attack for "+pa.getAppliedDamage(foeFighter)+" damage.");
				} catch (NotEnoughKiException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(new JFrame(), e.getMessage());
				}
				refresh();
				try {
					
					battle.play();
					endturn.setText("Your Turn");
					// updatetext("tmp");
				} catch (NotEnoughKiException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(new JFrame(), e.getMessage());
				}
				refresh();
				foe.setLocation(ffx, fy);
				for (int i = 0; i < 2000; i++)
					;
				me.setLocation(pfx, fy);

			}
		});
		attack.add(phpa);
		JMenu supa = new JMenu("2).SuperAttack");
		supa.add(arrayofsu[0]);
		supa.add(arrayofsu[1]);
		supa.add(arrayofsu[2]);
		supa.add(arrayofsu[3]);
		attack.add(supa);
		JMenu ulpa = new JMenu("3).UltimateAttack");
		ulpa.add(arrayofua[0]);
		ulpa.add(arrayofua[1]);
		attack.add(ulpa);
		attack.setHorizontalTextPosition(JMenu.CENTER);
		bar.add(attack);
		bar.setBounds(0, 540, 80, 30);
		JButton block = new JButton("Block");
		block.setBounds(0, 570, 80, 30);
		block.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				// PhysicalAttack pa = new PhysicalAttack();
				
				battle.block();
				endturn.setText("Enemy Turn");
				refresh();
				revalidate();
				repaint();
				
			//	tr/y {
				//	Thread.sleep(500);
				///} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
				//	e1.printStackTrace();
			//	}
				
				// for(int i=0;i<arrayofstamina.length;i++)
				// {
				// if(i>game.getPlayer().getActiveFighter().getStamina())
				// {
				// arrayofstamina[i].setVisible(false);
				// }
				// }
				// updatetext(meFighter.getName()+" has blocked");
				//System.out.println(">>"
						//+ game.getPlayer().getActiveFighter().getStamina());
				refresh();
				try {
					
					battle.play();
					endturn.setText("Your Turn");
					// updatetext("tmp");
				} catch (NotEnoughResourcesException  e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(new JFrame(), e.getMessage());
				}
				refresh();
				// me.setIcon((new ImageIcon(resize(createImageIcon("6.png",
				// ""),
				// 48, 96))));
				foe.setLocation(ffx, fy);
				for (int i = 0; i < 2000; i++)
					;
				me.setLocation(pfx, fy);

			}
		});
		JButton use = new JButton("Use");
		use.setBounds(0, 600, 80, 30);
		use.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				try {
					battle.use(game.getPlayer(), Collectible.SENZU_BEAN);
					endturn.setText("Enemy Turn");
					// updatetext(meFighter.getName()+" has used a Senzu Bean.");
					x1.setValue(100);
				} catch (NotEnoughSenzuBeansException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(new JFrame(), e1.getMessage());
				}
				refresh();
				try {
					battle.play();
					endturn.setText("Your Turn");
					x2.setValue(100);
					// updatetext("tmp");
				} catch (NotEnoughKiException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(new JFrame(), e.getMessage());
				}
				refresh();
				// me.setIcon((new ImageIcon(resize(createImageIcon("6.png",
				// ""),
				// 48, 96))));
				foe.setLocation(ffx, fy);
				for (int i = 0; i < 2000; i++)
					;
				me.setLocation(pfx, fy);

			}
		});
		add(block);
		add(use);
		add(bar);
		JLabel Currentfoestalabel = new JLabel("(Curr.Stamina)");
		Currentfoestalabel.setBounds(680, 35, 90, 15);
		add(Currentfoestalabel);
		JLabel maxfoestalab = new JLabel("(MaxStamina)");
		maxfoestalab.setBounds(680, 50, 90, 15);
		add(maxfoestalab);
		JLabel Currentfoekilabel = new JLabel("(Curr.Ki)");
		Currentfoekilabel.setBounds(680, 63, 90, 15);
		add(Currentfoekilabel);
		JLabel maxfoekilab = new JLabel("(MaxKi)");
		maxfoekilab.setBounds(680, 75, 90, 15);
		add(maxfoekilab);
		JLabel avsb = new JLabel(new ImageIcon(resize(
				createImageIcon("SenzuBean.png", ""), 60, 60)));
		avsb.setBounds(0, 400, 60, 60);
		add(avsb);
		nubofsb = new JLabel("" + game.getPlayer().getSenzuBeans());
		nubofsb.setBounds(65, 450, 10, 10);
		nubofsb.setForeground(Color.white);
		add(nubofsb);
		JPanel foruser = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.ipady = 15;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.NORTHWEST;
		text1 = new JLabel("Battle History:");
		text1.setFont(new Font("Courier New", Font.BOLD, 24));
		text1.setForeground(Color.white);
		foruser.add(text1, c);
		c.gridy = 1;
		text2 = new JLabel("");
		text2.setForeground(Color.white);
		foruser.add(text2, c);
		c.gridy = 2;
		text3 = new JLabel("");
		text3.setForeground(Color.white);
		foruser.add(text3, c);
		c.gridy = 3;
		text4 = new JLabel("");
		text4.setForeground(Color.white);
		foruser.add(text4, c);
		c.gridy = 4;
		text5 = new JLabel("");
		text5.setForeground(Color.white);
		foruser.add(text5, c);
		foruser.setBounds(560, 470, 400, 215);
		foruser.setBackground(new java.awt.Color(0, true));
		add(foruser);
		endturn = new JLabel("Your Turn.");
		endturn.setForeground(Color.white);
		endturn.setBounds(400, 100, 150, 50);
		add(endturn);	
		getContentPane().add(
				new ImagePanel(createImageIcon("bm1.jpg", "").getImage()));
		revalidate();
		repaint();
	}

	public void onBattleEvent(BattleEvent e) {

		if (e.getType() == BattleEventType.ATTACK) {
			
			if (e.getCurrentOpponent() == meFighter) {
				if(e.getAttack() instanceof UltimateAttack)
				  {
				   int i;
				   for( i=0;i<=game.getPlayer().getActiveFighter().getKi();i++)
				   {
				    
				   }
				   arrayofki[i].setVisible(false);
				   arrayofki[i-1].setVisible(false);
				   //arrayofki[i-2].setVisible(false);
				  }
				  
				  if(e.getAttack() instanceof SuperAttack)
				  {
				   int i;
				   for(i=0;i<game.getPlayer().getActiveFighter().getKi();i++)
				   {
				    
				   }
				   arrayofki[i].setVisible(false);
				  }
				  refresh();
				updatetext(meFighter.getName() + " has used "
						+ e.getAttack().getName() + " for "
						+ e.getAttack().getDamage() + " damage!");
			} else {
				if(e.getAttack() instanceof UltimateAttack)
				  {
				   int i;
				   for( i=0;i<=foeFighter.getKi();i++)
				   {
				    
				   }
				   arrayofkifoe[i].setVisible(false);
				   arrayofkifoe[i-1].setVisible(false);
				  // arrayofkifoe[i-2].setVisible(false);
				  }
				  
				  if(e.getAttack() instanceof SuperAttack)
				  {
				   int i;
				   for(i=0;i<foeFighter.getKi();i++)
				   {
				    
				   }
				   arrayofkifoe[i].setVisible(false);
				  }
				  refresh();
				updatetext(foeFighter.getName() + " has used "
						+ e.getAttack().getName() + " for "
						+ e.getAttack().getDamage() + " damage!");
			}
		} else if (e.getType() == BattleEventType.BLOCK) {
			if (e.getCurrentOpponent() == meFighter) {
				updatetext(meFighter.getName() + " has Blocked!");
			} else {
				updatetext(foeFighter.getName() + " has Blocked!");
			}
		} else if (e.getType() == BattleEventType.USE) {
			updatetext(meFighter.getName() + " has used a Senzu Bean!");
		} else if (e.getType() == BattleEventType.ENDED) {

			// if(((NonPlayableFighter) foeFighter).isStrong()){
			int xpgained = 0;
			if (bool == true) {
				NonPlayableFighter foe = (NonPlayableFighter) ((Battle) e
						.getSource()).getFoe();

				PlayableFighter me = game.getPlayer().getActiveFighter();
				// if i won
				if (e.getWinner() == me) {
					// gain xp
					//System.out.println("inside");
					//System.out.println(game.getPlayer().getActiveFighter()
					//		.getXp());
					//System.out.println(foe.getLevel());
					//System.out.println(game.getPlayer().getActiveFighter()
					//		.getXp()
					//		+ foe.getLevel() * 5);
					xpgained = foe.getLevel() * 5;
					me.setXp(me.getXp() + foe.getLevel() * 5);

					// learn opponents super and ultimate attacks
					for (SuperAttack superAttack : foe.getSuperAttacks()) {
						if (!game.getPlayer().getSuperAttacks()
								.contains(superAttack)) {
							game.getPlayer().getSuperAttacks().add(superAttack);
						}
					}
					for (UltimateAttack ultimateAttack : foe
							.getUltimateAttacks()) {
						if (!game.getPlayer().getUltimateAttacks()
								.contains(ultimateAttack)) {
							game.getPlayer().getUltimateAttacks()
									.add(ultimateAttack);
						}
					}

					// if opponent is boss
					if (foe.isStrong()) {
						// increment explored maps by 1
						view.foewonstrong();
					}
					// game.getState() = GameState.WORLD;
					// if my opponent won
				} else if (e.getWinner() == foe) {
					JOptionPane.showMessageDialog(new JFrame(),
							"You lost, regenerating map");
					view.foewon();
					bool = false;

				}

				// System.out.println("outside");
				// System.out.println(foeFighter.getLevel());
				// System.out.println(game.getPlayer().getActiveFighter().getXp());
				// System.out.println(game.getPlayer().getActiveFighter().getXp()
				// + foeFighter.getLevel()*5);
				// int xpgained = game.getPlayer().getActiveFighter().getXp() +
				// foeFighter.getLevel()*5;
				if (bool == true) {
					if(((NonPlayableFighter) foeFighter).isStrong()){
						String sattacksgained = "You have gained these Super attacks:\n";
						for (int i = 0; i < foeFighter.getSuperAttacks().size(); i++) {
							sattacksgained = sattacksgained
									+ foeFighter.getSuperAttacks().get(i).getName()
									+ " with damage "
									+ foeFighter.getSuperAttacks().get(i)
											.getDamage() + "\n";
						}
						String uattacksgained = "You have gained these Ultimate attacks:\n";
						for (int i = 0; i < foeFighter.getUltimateAttacks().size(); i++) {
							uattacksgained = uattacksgained
									+ foeFighter.getUltimateAttacks().get(i)
											.getName()
									+ " with damage "
									+ foeFighter.getUltimateAttacks().get(i)
											.getDamage() + "\n";
						}
						JOptionPane.showMessageDialog(new JFrame(),
								"Congratulations you have defeated the boss gaining you " + xpgained
										+ " XP \n \n And " + sattacksgained
										+ "\n \n And" + uattacksgained);
						view.refresh();
						bool = false;
					}else{
					String sattacksgained = "You have gained these Super attacks:\n";
					for (int i = 0; i < foeFighter.getSuperAttacks().size(); i++) {
						sattacksgained = sattacksgained
								+ foeFighter.getSuperAttacks().get(i).getName()
								+ " with damage "
								+ foeFighter.getSuperAttacks().get(i)
										.getDamage() + "\n";
					}
					String uattacksgained = "You have gained these Ultimate attacks:\n";
					for (int i = 0; i < foeFighter.getUltimateAttacks().size(); i++) {
						uattacksgained = uattacksgained
								+ foeFighter.getUltimateAttacks().get(i)
										.getName()
								+ " with damage "
								+ foeFighter.getUltimateAttacks().get(i)
										.getDamage() + "\n";
					}
					JOptionPane.showMessageDialog(new JFrame(),
							"Congratulations you have gained " + xpgained
									+ " XP \n \n And " + sattacksgained
									+ "\n \n And" + uattacksgained);
					view.refresh();
					bool = false;
				}
				}

			}
			// }

			dispose();

		}
		// TODO Auto-generated method stub
		// game.setListener(this);
		// if (e.getType() == BattleEventType.ENDED) {
		// dispose();
		// view.refresh();
		// revalidate();
		// repaint();
		// }
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

	public void refresh() {
		if(meFighter instanceof Saiyan){
			if(((Saiyan) meFighter).isTransformed()){
				pname = new JLabel("Player's name : "
						+ game.getPlayer().getActiveFighter().getName() +"                                             TRANSFORMED");
			}
		}
		if(foeFighter instanceof Saiyan){
			foname = new JLabel("TRANSFORMED   " + foeF.getName());
		}
		nubofsb.setText("" + game.getPlayer().getSenzuBeans());
		foehpbar.setText("" + foeF.getHealthPoints() + "/"
				+ foeF.getMaxHealthPoints());
		mehpbar.setText(""
				+ game.getPlayer().getActiveFighter().getHealthPoints() + "/"
				+ game.getPlayer().getActiveFighter().getMaxHealthPoints());
		// System.out.println(game.getPlayer().getActiveFighter().getKi());
		// System.out.println(game.getPlayer().getActiveFighter().getStamina());
		// Fighter meFighter = (Fighter) battle.getMe();
		double mehp = (double) meFighter.getHealthPoints()
				/ (double) meFighter.getMaxHealthPoints();
		mehp = mehp * 100;
		x1.setValue((int) mehp);
		x1.setForeground(Color.red);
		// Fighter foeFighter = (Fighter) battle.getFoe();
		double foehp = (double) foeFighter.getHealthPoints()
				/ (double) foeFighter.getMaxHealthPoints();
		foehp = foehp * 100;
		x2.setValue((int) foehp);
		x2.setForeground(Color.blue);
		// System.out.println(mehp);
		// System.out.println(meFighter.getHealthPoints());
		// System.out.println(meFighter.getMaxHealthPoints());
		// System.out.println(foehp);
		// System.out.println(foeFighter.getHealthPoints());
		// System.out.println(foeFighter.getMaxHealthPoints());
		for (int i = 0; i < game.getPlayer().getActiveFighter().getKi(); i++) {
			arrayofki[i].setVisible(true);
		}
		for (int i = 0; i < foeF.getKi(); i++) {
			arrayofkifoe[i].setVisible(true);
		}
		for (int i = 0; i < game.getPlayer().getActiveFighter().getStamina(); i++) {
			arrayofstamina[i].setVisible(true);
		}
		for (int i = 0; i < foeF.getStamina(); i++) {
			arrayofstaminafoe[i].setVisible(true);
		}
		revalidate();
		repaint();
	}

	public void updatetext(String message) {
		text2.setText(text3.getText());
		text3.setText(text4.getText());
		text4.setText(text5.getText());
		text5.setText(message);
		revalidate();
		repaint();

	}

}
