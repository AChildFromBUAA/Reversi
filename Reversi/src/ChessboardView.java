import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChessboardView extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// game info
	private boolean canPlayerDown;
	private int whiteCount;
	private int blackCount;
	//-- game start
	private void startState() {
		for(int i=0; i < 8; i++) {
			for(int j=0; j < 8; j++) {
				chess[i][j] = 0;
			}
		}
		chess[3][3] = -1;
		chess[4][4] = -1;
		chess[3][4] = 1;
		chess[4][3] = 1;
		blackCount = 2;
		whiteCount = 2;
		sideState = 1;
		canPlayerDown = true;
	}
	//-- game process
	
	public boolean searchN(int x, int y) {
		int cnt = 0;
		for(int i=x-1; i >= 0; i--) {
			if(chess[i][y] == -sideState) {
				cnt++;
			} else if(chess[i][y] == sideState && cnt!= 0 && cnt == x-i-1) {
				return true;
			}
		}
		return false;
	}
	
	public boolean searchNE(int x, int y) {
		int cnt = 0;
		for(int i=x-1, j=y+1; i >=0 && j < 8; i--, j++) {
			if(chess[i][j] == -sideState) {
				cnt++;
			} else if(chess[i][j] == sideState && cnt != 0 && cnt == x-i-1) {
				return true;
			}
		}
		return false;
	}
	
	public boolean searchE(int x,int y) {
		int cnt = 0;
		for(int i=y+1; i < 8; i++) {
			if(chess[x][i] == -sideState) {
				cnt++;
			} else if(chess[x][i] == sideState && cnt != 0 && cnt == i-y-1) {
				return true;
			}
		}
		return false;
	}
	
	public boolean searchSE(int x, int y) {
		int cnt = 0;
		for(int i=x+1, j=y+1; i < 8 && j < 8; i++, j++) {
			if(chess[i][j] == -sideState) {
				cnt++;
			} else if(chess[i][j] == sideState && cnt != 0 && cnt == i-x-1) {
				return true;
			}
		}
		return false;
	}
	
	public boolean searchS(int x, int y) {
		int cnt = 0;
		for(int i=x+1; i < 8; i++) {
			if(chess[i][y] == -sideState) {
				cnt++;
			} else if(chess[i][y] == sideState && cnt != 0 && cnt == i-x-1) {
				return true;
			}
		}
		return false;
	}
	
	public boolean searchSW(int x, int y) {
		int cnt = 0;
		for(int i=x+1, j=y-1; i < 8 && j >=0; i++, j--) {
			if(chess[i][j] == -sideState) {
				cnt++;
			} else if(chess[i][j] == sideState && cnt != 0 && cnt == i-x-1) {
				return true;
			}
		}
		return false;
	}
	
	public boolean searchW(int x, int y) {
		int cnt = 0;
		for(int j=y-1; j >=0; j--) {
			if(chess[x][j] == -sideState) {
				cnt++;
			} else if(chess[x][j] == sideState && cnt != 0 && cnt == y-j-1) {
				return true;
			}
		}
		return false;
	}
	
	public boolean searchNW(int x, int y) {
		int cnt = 0;
		for(int i=x-1, j=y-1; i>=0 && j >=0; i--, j--) {
			if(chess[i][j] == -sideState) {
				cnt++;
			} else if(chess[i][j] == sideState && cnt != 0 && cnt == x-i-1) {
				return true;
			}
		}
		return false;
	}
	
	public boolean canDown(int x, int y) {
		if(searchN(x,y) || searchNE(x,y) || searchE(x, y) || searchSE(x, y) || searchS(x, y) || searchSW(x, y) || searchW(x, y) || searchNW(x, y)) {
			canPlayerDown = true;
			return true;
		} else {
			return false;
		}
	}
	
	//-- game over
	private boolean isFull() {
		if(whiteCount + blackCount == 64) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean isOver() {
		if(!isFull()) {
			if(canPlayerDown) {
				return false;
			}
		}
		
		return true;
	}
	
	private int whoWin() {
		// -1 for black win, 1 for white win, 0 for equal
		if(blackCount > whiteCount) {
			return -1;
		} else if(blackCount < whiteCount) {
			return 1;
		} else {
			return 0;
		}
	}
	
	private void clear() {
		for(int i=0; i < 8; i++) {
			for(int j=0; j < 8; j++) {
				chess[i][j] = 0;
			}
		}
	}
	
	public static int chess[][] = new int[8][8];	// 1 for white, -1 for black, 0 for nil
	public static int sideState = 0;	// 1 for white, -1 for black, 0 for nil
	
	//view component
	private JPanel pTop;
	private JPanel pCenter;
	private JPanel pLeft;
	private JPanel blocks[][] = new JPanel[8][8];
	private JLabel blackLabel;
	private JLabel whiteLabel;
	private JLabel countLabelForBlack;
	private JLabel countLabelForWhite;
	private JLabel whichSideLabel;
	private JLabel winLabel;
	private JButton restartButton;
	
	public ChessboardView() {
		
		this.setBounds(initX, initY, initWidth, initHeight);
		this.setLayout(null);
		
		//top rol
		pTop = new JPanel();
		pTop.setBounds(300, 0, initWidth-300, boardY-10);
		pTop.setBackground(Color.white);
		Font winLabelFont = new Font("Arial", Font.BOLD, 30);
		winLabel = new JLabel("", JLabel.CENTER);
		winLabel.setBounds(initWidth/2-100, boardY/2-30, 200, 60);
		winLabel.setFont(winLabelFont);
		pTop.add(winLabel);
		restartButton = new JButton("restart");
		restartButton.addActionListener(
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					clear();
					winLabel.setText("");
					gameStart();
				}
		});
		restartButton.setBounds(initWidth-200, boardY/2-30, 100, 30);
		pTop.add(restartButton);
		this.add(pTop);
		
		//center board
		pCenter = new JPanel();
		pCenter.setBounds(boardX, boardY, boardWidth, boardHeight);
		pCenter.setBackground(Color.black);
		pCenter.setLayout(new GridLayout(8,8));
		for(int i=0; i < 8; i++) {
			for(int j=0; j<8; j++) {
				blocks[i][j] = new JPanel();
				blocks[i][j].setBackground(boardColor);
				blocks[i][j].setBorder(BorderFactory.createLineBorder(boardLineColor));
				pCenter.add(blocks[i][j]);
			}
		}
		this.add(pCenter);
		
		//left col
		pLeft = new JPanel();
		pLeft.setBounds(0, 0, 300, initHeight);
		pLeft.setBackground(Color.white);
		pLeft.setLayout(null);
		//--whichSideLabel
		Font sideLabelFont = new Font("Arial", Font.BOLD, 20);
		whichSideLabel = new JLabel("", JLabel.CENTER);
		whichSideLabel.setBounds(10, 50, 100, 100);
		whichSideLabel.setFont(sideLabelFont);
		pLeft.add(whichSideLabel);
		//--countLabel
		Font countLabelFont = new Font("Arial", Font.PLAIN, 20);
		blackLabel = new JLabel("BLACK", JLabel.CENTER);
		blackLabel.setFont(countLabelFont);
		blackLabel.setBounds(10, initHeight/2-100, 100, 100);
		pLeft.add(blackLabel);
		countLabelForBlack = new JLabel("0", JLabel.CENTER);
		countLabelForBlack.setBounds(10, initHeight/2, 100, 100);
		countLabelForBlack.setFont(countLabelFont);
		pLeft.add(countLabelForBlack);
		whiteLabel = new JLabel("WHITE", JLabel.CENTER);
		whiteLabel.setFont(countLabelFont);
		whiteLabel.setBounds(10+100+10, initHeight/2-100, 100, 100);
		pLeft.add(whiteLabel);
		countLabelForWhite = new JLabel("0", JLabel.CENTER);
		countLabelForWhite.setBounds(10+100+10, initHeight/2, 100, 100);
		countLabelForWhite.setFont(countLabelFont);
		pLeft.add(countLabelForWhite);
		this.add(pLeft);
	
		this.setVisible(true);
		
		this.addMouseListener(new ChessBoardListener(this));
	}
	
	public void gameStart() {
		startState();
		update();
	}
	
	public void update() {
		blackCount = 0;
		whiteCount = 0;
		canPlayerDown = false;
		sideState = -sideState;
		for(int i=0; i < 8; i++) {
			for(int j=0; j < 8; j++) {
				Graphics g = blocks[i][j].getGraphics();
				g.setColor(boardColor);
				g.fillRect(10, 10, blocks[i][j].getWidth()-20, blocks[i][j].getHeight()-20);
				if(chess[i][j] == 1) {
					//blocks[i][j].setBackground(Color.white);
					g.setColor(Color.white);
					g.drawOval(15, 15, 50, 50);
					g.fillOval(15, 15, 50, 50);
					whiteCount++;
				} else if(chess[i][j] == -1) {
					//blocks[i][j].setBackground(Color.black);
					g.setColor(Color.black);
					g.drawOval(15, 15, 50, 50);
					g.fillOval(15, 15, 50, 50);
					blackCount++;
				} else {
					if(canDown(i, j)) {
						//blocks[i][j].setBackground(Color.red);
						g.setColor(canDownBlockColor);
						g.drawOval(15, 15, 50, 50);
					}
				}
				try{
                    Thread.sleep(2);
                }
                catch(Exception ex){
                }
			}
		}

		if(sideState == 1) {
			whichSideLabel.setText("White");
		} else {
			whichSideLabel.setText("Black");
		}
		countLabelForWhite.setText(whiteCount+ " ");
		countLabelForBlack.setText(blackCount+ " ");
		//over
		if(isOver()) {
			switch(whoWin()) {
			case 1:
				//JOptionPane.showInternalMessageDialog(null, "","White Win", JOptionPane.INFORMATION_MESSAGE);
				this.winLabel.setText("White Win");
				break;
			case -1:
				//JOptionPane.showInternalMessageDialog(null, "","Black Win", JOptionPane.INFORMATION_MESSAGE);
				this.winLabel.setText("Black Win");
				break;
			case 0:
				//JOptionPane.showInternalMessageDialog(null, "","Tie", JOptionPane.INFORMATION_MESSAGE);
				this.winLabel.setText("Tie");
				break;
			default:
				break;
			}
		}
	}

	public final static int initX = 100;
	public final static int initY = 100;
	public final static int initWidth = 1200;
	public final static int initHeight = 800;
	public static int boardX = 400;
	public static int boardY = 100;
	public static int boardWidth = 640;
	public static int boardHeight = 640;
	public static int reviseY = 30;
	public static Color boardColor = new Color(216, 138, 49);
	public static Color boardLineColor = new Color(136, 78, 65);
	public static Color canDownBlockColor = new Color(253, 255, 197);
}
