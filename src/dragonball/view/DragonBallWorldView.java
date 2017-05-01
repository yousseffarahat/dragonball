package dragonball.view;

import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

import dragonball.model.attack.SuperAttack;
import dragonball.model.attack.UltimateAttack;
import dragonball.model.battle.Battle;
import dragonball.model.battle.BattleEvent;
import dragonball.model.battle.BattleEventType;
import dragonball.model.battle.BattleListener;
import dragonball.model.cell.Collectible;
import dragonball.model.character.fighter.NonPlayableFighter;
import dragonball.model.dragon.Dragon;
import dragonball.model.exceptions.DuplicateAttackException;
import dragonball.model.exceptions.MaximumAttacksLearnedException;
import dragonball.model.exceptions.MissingFieldException;
import dragonball.model.exceptions.NotASaiyanException;
import dragonball.model.exceptions.NotEnoughAbilityPointsException;
import dragonball.model.exceptions.UnknownAttackTypeException;
import dragonball.model.game.Game;
import dragonball.model.game.GameListener;
import dragonball.model.player.Player;

@SuppressWarnings("serial")
public class DragonBallWorldView extends JFrame implements KeyListener,
		ActionListener, GameListener/* BattleListener*/ {

	private JLayeredPane panel;
	private KeyListener klistener;
	private boolean loadgame;
	private BattleView view;
	private Game game;
	private DragonView dragonview;
	private JLabel pic1;
	private JLabel name;
	private JButton nofsb;
	private JButton nofdb;
	private String fname;
	private int flevel, xp, targetxp, abilitypoints;
	private String playername;

	public DragonBallWorldView(Player player, Game game) {
		setTitle("World Mode");
		this.setResizable(false);
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
		addKeyListener(this);
		game.setListener(this);
		setBounds(300, 10, 900, 715);
		setVisible(true);
		BufferedImage myimage = resize(createImageIcon("desert_bkg x.png", ""),
				900, 715);
		setContentPane(new ImagePanel(myimage));
		setLayout(new GridBagLayout());
		Container x = getContentPane();
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		// if (loadgame) {
		// try {
		// game.load(game.getLastSavedFile());
		// } catch (ClassNotFoundException | IOException e) {
		// e.printStackTrace();
		// }
		// } else {
		// try {
		// this.game = new Game();
		// } catch (MissingFieldException | UnknownAttackTypeException e) {
		// e.printStackTrace();
		// }
		// }
		this.game = game;
		if (!(player.equals(null))) {
			game.getPlayer().setName(player.getName());
			game.getPlayer().setActiveFighter(player.getActiveFighter());
			game.getPlayer().setDragonBalls(player.getDragonBalls());
			game.getPlayer().setExploredMaps(player.getExploredMaps());
			game.getPlayer().setFighters(player.getFighters());
			game.getPlayer().setSenzuBeans(player.getSenzuBeans());
			game.getPlayer().setSuperAttacks(player.getSuperAttacks());
			game.getPlayer().setUltimateAttacks(player.getUltimateAttacks());
		}

		panel = new JLayeredPane();
		panel.setLayout(new GridBagLayout());
		panel.setVisible(true);
		panel.setBounds(50, 50, 800, 600);

		JButton box = new JButton("CELL");
		GridBagConstraints c = new GridBagConstraints();

		ImageIcon icon = createImageIcon("cell.jpg", "");
		// adding Stronge Foe
		ImageIcon icon2 = new ImageIcon(resize(icon, 60, 60));
		ImageIcon icon3 = createImageIcon("veg1.png", "");
		c.gridx = 0;
		c.gridy = 0;
		ImageIcon icon4 = new ImageIcon(resize(icon3, 60, 60));
		// adding Player cell(9,9)
		ImageIcon icon5 = createImageIcon("cell1.png", "");
		ImageIcon icon6 = new ImageIcon(resize(icon5, 60, 60));
		pic1 = new JLabel(icon6);
		c.gridx = game.getWorld().getPlayerColumn();
		c.gridy = game.getWorld().getPlayerRow();
		panel.add(pic1, c, 0);
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				c.weightx = 10;
				c.weighty = 10;
				c.gridx = i;
				c.gridy = j;
				box = new JButton("CELL");
				box.setFont(getFont());
				JLabel pic2 = new JLabel(icon2);
				pic2.setBorder(BorderFactory.createBevelBorder(1));
				panel.add(pic2, c, 1);
			}
		}
		JLabel pic3 = new JLabel(icon4);
		c.gridx = 0;
		c.gridy = 0;
		panel.add(pic3, c, 0);
		// int flevel, xp, targetxp, abilitypoints;
		playername = game.getPlayer().getName();
		if (game.getPlayer().getActiveFighter() == null) {
			fname = "~";
			xp = targetxp = abilitypoints = flevel = 0;

		} else {
			fname = game.getPlayer().getActiveFighter().getName();
			flevel = game.getPlayer().getActiveFighter().getLevel();
			xp = game.getPlayer().getActiveFighter().getXp();
			targetxp = game.getPlayer().getActiveFighter().getTargetXp();
			abilitypoints = game.getPlayer().getActiveFighter()
					.getAbilityPoints();
		}
		name = new JLabel("Player Name : " + "" + playername
				+ "     Figher Name : " + fname + "     Fighter Level : "
				+ flevel + "     Current Xp : " + xp + "     TargetXp : "
				+ targetxp + "     Abiltiy Points : " + abilitypoints);
		name.setFont(getFont());
		nofsb = new JButton("" + game.getPlayer().getSenzuBeans());
		nofsb.setEnabled(false);
		nofdb = new JButton("" + game.getPlayer().getDragonBalls());
		nofdb.setEnabled(false);
		ImageIcon senzubeans = new ImageIcon(resize(
				createImageIcon("SenzuBean.png", ""), 60, 60));
		ImageIcon dragonball = new ImageIcon(resize(
				createImageIcon("dragonball.png", ""), 60, 60));
		JLabel sb = new JLabel(senzubeans);
		JLabel db = new JLabel(dragonball);
		GridBagConstraints z = new GridBagConstraints();
		z.weightx = 1.0;
		z.weighty = 1.0;
		z.gridheight = 2;
		z.gridx = 0;
		z.gridy = 0;
		z.anchor = GridBagConstraints.NORTHWEST;
		x.add(name, z, 0);
		z.gridx = 0;
		z.gridy = 0;
		z.anchor = GridBagConstraints.WEST;
		x.add(panel, z, 0);
		JPanel rightup = new JPanel(new GridBagLayout());
		GridBagConstraints t = new GridBagConstraints();
		t.weightx = 1.0;
		t.weighty = 1.0;
		t.gridx = 0;
		t.gridy = 0;
		t.anchor = GridBagConstraints.NORTH;
		rightup.add(sb, t, 0);
		t.gridx = 1;
		t.gridy = 0;
		rightup.add(nofsb, t, 0);
		t.anchor = GridBagConstraints.SOUTH;
		t.gridx = 0;
		t.gridy = 1;
		rightup.add(db, t, 0);
		t.gridx = 1;
		t.gridy = 1;
		rightup.add(nofdb, t, 0);
		z.anchor = GridBagConstraints.NORTHEAST;
		rightup.setBackground(new java.awt.Color(0, true));
		x.add(rightup, z, 0);
		JPanel rightdown = new JPanel(new GridBagLayout());
		GridBagConstraints xy = new GridBagConstraints();
		xy.weightx = 1.0;
		xy.weighty = 1.0;
		xy.gridx = 1;
		xy.gridy = 0;
		xy.anchor = GridBagConstraints.CENTER;
		JMenuBar menu = new JMenuBar();
		JMenu op = new JMenu("Options");
		JMenuItem cf = new JMenuItem("1).Create new Fighter"); // Should create
																// new
		cf.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String[] list = { "Saiyan", "Frieza", "Majin", "Namekian",
						"Earthling" };
				JComboBox jcb = new JComboBox(list);
				jcb.setEditable(true);
				JTextArea x = new JTextArea(1, 10);
				String name = JOptionPane.showInputDialog(x,
						"Enter Fighter name");
				JOptionPane.showMessageDialog(null, jcb, "Choose Fighter Race",
						JOptionPane.QUESTION_MESSAGE);
				game.getPlayer().createFighter(
						list[jcb.getSelectedIndex()].charAt(0), name);
				refresh();
			}

		});

		JMenu aa = new JMenu("2).Assign an attack"); // Should assign new attack
		JMenuItem sa = new JMenuItem("1).Assign SuperAttack");
		sa.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String[] aroldattacks = new String[4];
				int i;
				for (i = 0; i < game.getPlayer().getActiveFighter()
						.getSuperAttacks().size(); i++) {
					aroldattacks[i] = game.getPlayer().getActiveFighter()
							.getSuperAttacks().get(i).getName();
				}
				if (i < 4)
					aroldattacks[i] = new String(
							"Add a new Super Attack Directly");
				JComboBox usereoa = new JComboBox(aroldattacks);
				JOptionPane
						.showMessageDialog(null, usereoa,
								"Repalce your old Attack",
								JOptionPane.QUESTION_MESSAGE);
				String[] arnewattacks = new String[game.getPlayer()
						.getSuperAttacks().size()];
				for (int j = 0; j < arnewattacks.length; j++) {
					arnewattacks[j] = game.getPlayer().getSuperAttacks().get(j)
							.getName();
				}
				JComboBox userna = new JComboBox(arnewattacks);
				JOptionPane
						.showMessageDialog(null, userna,
								"Your avaliable Attacks.",
								JOptionPane.QUESTION_MESSAGE);
				String tmpoldattack = aroldattacks[usereoa.getSelectedIndex()];
				String tmpnewattack = arnewattacks[userna.getSelectedIndex()];
				if (tmpoldattack.equals("Add a new Super Attack Directly")) {
					for (int j = 0; j < game.getPlayer().getSuperAttacks()
							.size(); j++) {
						if (game.getPlayer().getSuperAttacks().get(i).getName()
								.equals(tmpnewattack)) {
							try {
								game.getPlayer().assignAttack(
										game.getPlayer().getActiveFighter(),
										game.getPlayer().getSuperAttacks()
												.get(i), null);
							} catch (MaximumAttacksLearnedException
									| DuplicateAttackException
									| NotASaiyanException e) {
								JOptionPane.showMessageDialog(new JFrame(),
										e.getMessage());
							}
						}
					}
				} else {
					SuperAttack oldtmp = null;
					for (int j = 0; j < game.getPlayer().getActiveFighter()
							.getSuperAttacks().size(); j++) {
						if (game.getPlayer().getActiveFighter()
								.getSuperAttacks().get(j).getName()
								.equals(tmpoldattack)) {
							oldtmp = game.getPlayer().getActiveFighter()
									.getSuperAttacks().get(j);
						}
					}
					for (int j = 0; j < game.getPlayer().getSuperAttacks()
							.size(); j++) {
						if (game.getPlayer().getSuperAttacks().get(j).getName()
								.equals(tmpnewattack)) {
							try {
								game.getPlayer().assignAttack(
										game.getPlayer().getActiveFighter(),
										game.getPlayer().getSuperAttacks()
												.get(j), oldtmp);
							} catch (MaximumAttacksLearnedException
									| DuplicateAttackException
									| NotASaiyanException e) {
								JOptionPane.showMessageDialog(new JFrame(),
										e.getMessage());
							}
						}
					}
				}
				refresh();
			}
		});

		aa.add(sa);
		JMenuItem ua = new JMenuItem("2).Assign UltimateAttack");
		ua.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String[] aroldattacks = new String[2];
				int i;
				for (i = 0; i < game.getPlayer().getActiveFighter()
						.getUltimateAttacks().size(); i++) {
					aroldattacks[i] = game.getPlayer().getActiveFighter()
							.getUltimateAttacks().get(i).getName();
				}
				if (i < 2)
					aroldattacks[i] = new String(
							"Add a new Ultimate Attack Directly");
				JComboBox usereoa = new JComboBox(aroldattacks);
				JOptionPane
						.showMessageDialog(null, usereoa,
								"Repalce your old Attack",
								JOptionPane.QUESTION_MESSAGE);
				String[] arnewattacks = new String[game.getPlayer()
						.getUltimateAttacks().size()];
				for (int j = 0; j < arnewattacks.length; j++) {
					arnewattacks[j] = game.getPlayer().getUltimateAttacks()
							.get(j).getName();
				}
				JComboBox userna = new JComboBox(arnewattacks);
				JOptionPane
						.showMessageDialog(null, userna,
								"Your avaliable Attacks.",
								JOptionPane.QUESTION_MESSAGE);
				String tmpoldattack = aroldattacks[usereoa.getSelectedIndex()];
				String tmpnewattack = arnewattacks[userna.getSelectedIndex()];
				if (tmpoldattack.equals("Add a new Ultimate Attack Directly")) {
					for (int j = 0; j < game.getPlayer().getUltimateAttacks()
							.size(); j++) {
						if (game.getPlayer().getUltimateAttacks().get(i)
								.getName().equals(tmpnewattack)) {
							try {
								game.getPlayer().assignAttack(
										game.getPlayer().getActiveFighter(),
										game.getPlayer().getUltimateAttacks()
												.get(i), null);
							} catch (MaximumAttacksLearnedException
									| DuplicateAttackException
									| NotASaiyanException e1) {
								JOptionPane.showMessageDialog(new JFrame(),
										e1.getMessage());
							}
						}
					}
				} else {
					UltimateAttack oldtmp = null;
					for (int j = 0; j < game.getPlayer().getActiveFighter()
							.getUltimateAttacks().size(); j++) {
						if (game.getPlayer().getActiveFighter()
								.getUltimateAttacks().get(j).getName()
								.equals(tmpoldattack)) {
							oldtmp = game.getPlayer().getActiveFighter()
									.getUltimateAttacks().get(j);
						}
					}
					for (int j = 0; j < game.getPlayer().getUltimateAttacks()
							.size(); j++) {
						if (game.getPlayer().getUltimateAttacks().get(j)
								.getName().equals(tmpnewattack)) {
							try {
								game.getPlayer().assignAttack(
										game.getPlayer().getActiveFighter(),
										game.getPlayer().getUltimateAttacks()
												.get(j), oldtmp);
							} catch (MaximumAttacksLearnedException
									| DuplicateAttackException
									| NotASaiyanException e2) {
								JOptionPane.showMessageDialog(new JFrame(),
										e2.getMessage());
							}
						}
					}
				}
refresh();
			}
		});

		aa.add(ua);
		JMenu up = new JMenu("3).Upgrade your ActiveFighter");

		JMenuItem item1 = new JMenuItem(
				"1).Increase Max Health Points by 50 points.");
		item1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (game.getPlayer().getActiveFighter().getAbilityPoints() == 0) {
					JOptionPane.showMessageDialog(new JFrame(),
							"Sorry you dont have enough Ability Points");
				} else {
					Object[] options = { "OK", "CANCEL" };
					int x = game.getPlayer().getActiveFighter()
							.getMaxHealthPoints();
					int z = JOptionPane.showOptionDialog(null,
							"Would you like to incease your  Max.Health points from "
									+ x + " to " + (x + 50), "Warning",
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.WARNING_MESSAGE, null, options,
							options[0]);
					try {
						if (z == 0) {
							game.getPlayer().upgradeFighter(
									game.getPlayer().getActiveFighter(), 'H');
							JOptionPane.showMessageDialog(new JFrame(),
									"Your Current ActiveFighter Max.Health points is. "
											+ (x + 50));
							refresh();
						}
					} catch (NotEnoughAbilityPointsException e1) {
						e1.printStackTrace();
					}
				}
			}

		});
		up.add(item1);
		JMenuItem item2 = new JMenuItem(
				"2).Increase Physical Damage by 50 points.");
		item2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (game.getPlayer().getActiveFighter().getAbilityPoints() == 0) {
					JOptionPane.showMessageDialog(new JFrame(),
							"Sorry you dont have enough Ability Points");
				} else {
					Object[] options = { "OK", "CANCEL" };
					int x = game.getPlayer().getActiveFighter()
							.getPhysicalDamage();
					int z = JOptionPane.showOptionDialog(null,
							"Would you like to incease your  Physical Damage from "
									+ x + " to " + (x + 50), "Warning",
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.WARNING_MESSAGE, null, options,
							options[0]);
					try {
						if (z == 0) {
							game.getPlayer().upgradeFighter(
									game.getPlayer().getActiveFighter(), 'P');
							JOptionPane.showMessageDialog(new JFrame(),
									"Your Current Activefighter Physical Damage is. "
											+ (x + 50));
							refresh();
						}
					} catch (NotEnoughAbilityPointsException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		up.add(item2);
		JMenuItem item3 = new JMenuItem(
				"3).Increase Blast Damage by 50 points.");
		item3.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (game.getPlayer().getActiveFighter().getAbilityPoints() == 0) {
					JOptionPane.showMessageDialog(new JFrame(),
							"Sorry you dont have enough Ability Points");
				} else {
					Object[] options = { "OK", "CANCEL" };
					int x = game.getPlayer().getActiveFighter()
							.getBlastDamage();
					int z = JOptionPane.showOptionDialog(null,
							"Would you like to incease your Blast Damage from "
									+ x + " to " + (x + 50), "Warning",
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.WARNING_MESSAGE, null, options,
							options[0]);
					try {
						if (z == 0) {
							game.getPlayer().upgradeFighter(
									game.getPlayer().getActiveFighter(), 'B');
							JOptionPane.showMessageDialog(new JFrame(),
									"Your Current Blast Damage is . "
											+ (x + 50));
							refresh();
						}
					} catch (NotEnoughAbilityPointsException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		up.add(item3);
		JMenuItem item4 = new JMenuItem("4).Increase Max Ki by one bar.");
		item4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (game.getPlayer().getActiveFighter().getAbilityPoints() == 0) {
					JOptionPane.showMessageDialog(new JFrame(),
							"Sorry you dont have enough Ability Points");
				} else {
					Object[] options = { "OK", "CANCEL" };
					int x = game.getPlayer().getActiveFighter().getMaxKi();
					int z = JOptionPane.showOptionDialog(null,
							"Would you like to incease your  Max.Ki from " + x
									+ " to " + (x + 1), "Warning",
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.WARNING_MESSAGE, null, options,
							options[0]);
					try {
						if (z == 0) {
							game.getPlayer().upgradeFighter(
									game.getPlayer().getActiveFighter(), 'K');
							JOptionPane.showMessageDialog(new JFrame(),
									"Your current Activefighter Max Ki is . "
											+ (x + 1));
							refresh();
						}
					} catch (NotEnoughAbilityPointsException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		up.add(item4);
		JMenuItem item5 = new JMenuItem("5).Increase Max Stamina by one bar.");
		item5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (game.getPlayer().getActiveFighter().getAbilityPoints() == 0) {
					JOptionPane.showMessageDialog(new JFrame(),
							"Sorry you dont have enough Ability Points");
				} else {
					Object[] options = { "OK", "CANCEL" };
					int x = game.getPlayer().getActiveFighter()
							.getMaxHealthPoints();
					int z = JOptionPane.showOptionDialog(null,
							"Would you like to incease your  Max.Stamina from "
									+ x + " to " + (x + 1), "Warning",
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.WARNING_MESSAGE, null, options,
							options[0]);
					try {
						if (z == 0) {
							game.getPlayer().upgradeFighter(
									game.getPlayer().getActiveFighter(), 'S');
							JOptionPane.showMessageDialog(new JFrame(),
									"Your Current Activefighter Max Stamina is . "
											+ (x + 1));
							refresh();
						}
					} catch (NotEnoughAbilityPointsException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		up.add(item5);
		op.add(cf);
		op.add(aa);
		op.add(up);
		menu.add(op);
		rightdown.add(menu, xy, 0);
		JButton switchb = new JButton(new ImageIcon(resize(
				createImageIcon("re.png", ""), 60, 60)));
		switchb.setBorder(null);
		switchb.setRolloverEnabled(false);
		switchb.setContentAreaFilled(false);
		xy.gridx = 0;
		xy.gridy = 0;
		rightdown.add(switchb, xy, 0);
		switchb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (game.getPlayer().getFighters().size() > 1){
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						String[] list = new String[game.getPlayer()
								.getFighters().size()];
						for (int i = 0; i < game.getPlayer().getFighters()
								.size(); i++) {
							list[i] = game.getPlayer().getFighters().get(i)
									.getName();
						}
						JComboBox jcb = new JComboBox(list);
						jcb.setEditable(true);
						JOptionPane.showMessageDialog(null, jcb,
								"Switch your Active Fighter",
								JOptionPane.QUESTION_MESSAGE);
						for (int i = 0; i < game.getPlayer().getFighters()
								.size(); i++) {
							if ((game.getPlayer().getFighters().get(i)
									.getName()).equals(list[jcb
									.getSelectedIndex()])) {
								game.getPlayer().setActiveFighter(
										game.getPlayer().getFighters().get(i));
								fname = game.getPlayer().getActiveFighter()
										.getName();
								// System.out.println(fname);
							}
						}
						// setFocusableWindowState(false);
						// setFocusable(false);
						refresh();
					}
				});
				}
			}
		});
		z.anchor = GridBagConstraints.SOUTHEAST;
		x.add(rightdown, z, 0);
		rightdown.setBackground(new java.awt.Color(0, true));
		JToggleButton afinfo = new JToggleButton();
		afinfo.setBorder(null);
		afinfo.setContentAreaFilled(false);
		afinfo.setEnabled(true);
		afinfo.setIcon(createImageIcon("Gss.png", ""));
		z.anchor = GridBagConstraints.EAST;
		z.gridx = 0;
		z.gridy = 0;
		x.add(afinfo, z, 0);
		//System.out.println(game.getWorld().toString());
		GridBagConstraints zbutton = new GridBagConstraints();
		  zbutton.weightx = 1.0;
		  zbutton.weighty = 1.0;
		  zbutton.gridheight =1;
		  zbutton.gridx = 0;
		  zbutton.gridy = 0;
		  zbutton.anchor = GridBagConstraints.SOUTHWEST;
		  //JButton save=new JButton("Save");
		  //final JFileChooser fc = new JFileChooser();
		//  fc.setDialogTitle("Please choose your save destination");
		/*  save.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (game.getPlayer().getFighters().size() > 1){
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							try {
								
								game.save(fc.getSelectedFile().getAbsolutePath());														// selection
								//view7.setVisible(true);
								//view7.setFlag(true);
								dispose();
							} catch (IOException f) {
								// TODO Auto-generated catch block
								f.printStackTrace();
							}
						}
					});
					}
				}
			});*/
		 // x.add(save,zbutton,0);
		revalidate();
		repaint();
	}

	public void setFlag(boolean flag) {
		this.loadgame = flag;
	}

	public KeyListener getKlistener() {
		return klistener;
	}

	public void setKlistener(KeyListener klistener) {
		this.klistener = klistener;
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

	@Override
	public void keyPressed(KeyEvent e) {
		game.setListener(this);
		int keycode = e.getKeyCode();
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = game.getWorld().getPlayerColumn();
		c.gridy = game.getWorld().getPlayerRow();

		if (keycode == KeyEvent.VK_LEFT) {
			if (c.gridx > 0) {
				game.getWorld().moveLeft();
				c.gridx--;
				panel.add(pic1, c, 1);
				panel.revalidate();
				panel.repaint();
				//System.out.println(game.getWorld().toString());
			}
		} else if (keycode == KeyEvent.VK_UP) {
			if (c.gridy > 0) {
				game.getWorld().moveUp();
				c.gridy--;
				panel.add(pic1, c, 1);
				panel.revalidate();
				panel.repaint();
				//System.out.println(game.getWorld().toString());
			}
		} else if (keycode == KeyEvent.VK_DOWN) {
			if (c.gridy < 9) {
				game.getWorld().moveDown();
				c.gridy++;
				panel.add(pic1, c, 1);
				panel.revalidate();
				panel.repaint();
				//System.out.println(game.getWorld().toString());
			}
		} else if (keycode == KeyEvent.VK_RIGHT) {
			if (c.gridx < 9) {
				game.getWorld().moveRight();
				c.gridx++;
				panel.add(pic1, c, 1);
				panel.revalidate();
				panel.repaint();
				//System.out.println(game.getWorld().toString());
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// if (game.getPlayer().getDragonBalls() == 1)
		// try {
		// dragonview = new DragonView(game);
		// dragonview.setVisible(true);
		// this.setVisible(false);
		// game.getPlayer().callDragon();
		// dragonview.revalidate();
		// dragonview.repaint();
		// } catch (IOException e1) {
		// e1.printStackTrace();
		// }
		// this.revalidate();
		// this.repaint();
		// e.consume();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		e.consume();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDragonCalled(Dragon dragon) {
			try {
				dragonview = new DragonView(game, dragon, this);
				dragonview.setVisible(true);
				this.setVisible(false);
				game.getPlayer().callDragon();
				dragonview.revalidate();
				dragonview.repaint();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		this.revalidate();
		this.repaint();
		// e.consume();
	}

	@Override
	public void onBattleEvent(BattleEvent e) {
		game.setListener(this);
		if (e.getType() == BattleEventType.STARTED) {
			NonPlayableFighter foe = (NonPlayableFighter) ((Battle) e
					.getSource()).getFoe();
			Battle battle = (Battle) e.getSource();
			view = new BattleView(game, battle, foe, this);
			view.setVisible(true);
			revalidate();
			repaint();
		} else if (e.getType() == BattleEventType.ENDED) {
			view.dispose();
			refresh();
			revalidate();
			repaint();
		}
	}

	@Override
	public void onCollectibleFound(Collectible collectible) {
		switch (collectible) {
		case SENZU_BEAN:
			JOptionPane.showMessageDialog(new JFrame(),
					"Wow. You Stepped on a Senzu Bean");
			nofsb.setText("" + game.getPlayer().getSenzuBeans());
			revalidate();
			repaint();
			break;
		case DRAGON_BALL:
			JOptionPane.showMessageDialog(new JFrame(),
					"Wow. You got a DragonBall");
			nofdb.setText("" + game.getPlayer().getDragonBalls());
			//if(game.getPlayer().getDragonBalls()==1){
				//game.getPlayer().setDragonBalls(7);
			//}
			//if(game.getPlayer().getDragonBalls()==7){
				//game.onDragonCalled();
			//}
			// // should only be after 7
			revalidate();
			repaint();
			break;
		}
	}

	public void updatesb() {
		nofsb.setText("" + game.getPlayer().getSenzuBeans());
		revalidate();
		repaint();
	}

	public void updatedb() {
		nofdb.setText("" + game.getPlayer().getDragonBalls());
		revalidate();
		repaint();
	}

	public void updateap() {
		abilitypoints = game.getPlayer().getActiveFighter().getAbilityPoints();
		name.setText("Player Name : " + "" + playername + "     Figher Name : "
				+ fname + "     Fighter Level : " + flevel
				+ "     Current Xp : " + xp + "     TargetXp : " + targetxp
				+ "     Abiltiy Points : " + abilitypoints);
		revalidate();
		repaint();
	}

	public void updatename() {
		fname = game.getPlayer().getActiveFighter().getName();
		name.setText("Player Name : " + "" + playername + "     Figher Name : "
				+ fname + "     Fighter Level : " + flevel
				+ "     Current Xp : " + xp + "     TargetXp : " + targetxp
				+ "     Abiltiy Points : " + abilitypoints);
		revalidate();
		repaint();
	}

	public void refresh() {
		playername = game.getPlayer().getName();
		fname = game.getPlayer().getActiveFighter().getName();
		flevel = game.getPlayer().getActiveFighter().getLevel();
		xp = game.getPlayer().getActiveFighter().getXp();
		targetxp = game.getPlayer().getActiveFighter().getTargetXp();
		abilitypoints = game.getPlayer().getActiveFighter().getAbilityPoints();
		name.setText("Player Name : " + "" + playername + "     Figher Name : "
				+ fname + "     Fighter Level : " + flevel
				+ "     Current Xp : " + xp + "     TargetXp : " + targetxp
				+ "     Abiltiy Points : " + abilitypoints);
		updatesb();
		updatedb();
		updateap();
		revalidate();
		repaint();
		// this.setFocusable(true);
		// this.setFocusTraversalKeysEnabled(true);
		this.requestFocus();
	}
	public void foewon(){
		game.foewon();
		resetposition();
		refresh();
		revalidate();
		repaint();
	}
	public void foewonstrong(){
		game.getPlayer().setExploredMaps(game.getPlayer().getExploredMaps() + 1);

		// reload foes in case range changed
		int foesRange = (game.getPlayer().getMaxFighterLevel() - 1) / 10 + 1;
		try {
			game.loadFoes("." + File.separator + "Database-Foes-Range"
					+ foesRange + ".csv");
		} catch (MissingFieldException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// regenerate map
		game.getWorld().generateMap(game.getWeakFoes(), game.getStrongFoes());
		resetposition();
		refresh();
		revalidate();
		repaint();
		
	}
	public void resetposition(){
		game.getWorld().resetPlayerPosition();
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = game.getWorld().getPlayerColumn();
		c.gridy = game.getWorld().getPlayerRow();
		panel.add(pic1, c, 1);
		panel.revalidate();
		panel.repaint();
	}
	

	public static void main(String[] args) throws MissingFieldException,
			UnknownAttackTypeException {
		Game x = new Game();
		new DragonBallWorldView(new Player("Slim"), x);
	}
}
