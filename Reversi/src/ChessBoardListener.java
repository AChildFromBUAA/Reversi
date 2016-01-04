import java.awt.event.*;

public class ChessBoardListener extends MouseAdapter {
	
	private ChessboardView cbv;
	
	private Boolean isInboard(int x, int y) {
		if(x <= ChessboardView.boardX + ChessboardView.boardWidth) {
			if(x >= ChessboardView.boardX) {
				if(y <= ChessboardView.boardY + ChessboardView.boardHeight) {
					if(y >= ChessboardView.boardY) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	private void changeColor(int x, int y) {
		if(cbv.searchN(x, y)) {
			for(int i=x-1; i >= 0; i--) {
				if(ChessboardView.chess[i][y] == -ChessboardView.sideState) {
					ChessboardView.chess[i][y] = ChessboardView.sideState;
				} else {
					break;
				}
			}
		}
		if(cbv.searchNE(x, y)) {
			for(int i=x-1, j=y+1; i >=0 && j < 8; i--, j++) {
				if(ChessboardView.chess[i][j] == -ChessboardView.sideState) {
					ChessboardView.chess[i][j] = ChessboardView.sideState;
				} else {
					break;
				}
			}
		}
		if(cbv.searchE(x, y)) {
			for(int i=y+1; i < 8; i++) {
				if(ChessboardView.chess[x][i] == -ChessboardView.sideState) {
					ChessboardView.chess[x][i] = ChessboardView.sideState;
				} else {
					break;
				}
			}
		}
		if(cbv.searchSE(x, y)) {
			for(int i=x+1, j=y+1; i < 8 && j < 8; i++, j++) {
				if(ChessboardView.chess[i][j] == -ChessboardView.sideState) {
					ChessboardView.chess[i][j] = ChessboardView.sideState;
				} else {
					break;
				}
			}
		}
		if(cbv.searchS(x, y)) {
			for(int i=x+1; i < 8; i++) {
				if(ChessboardView.chess[i][y] == -ChessboardView.sideState) {
					ChessboardView.chess[i][y] = ChessboardView.sideState;
				} else {
					break;
				}
			}
		}
		if(cbv.searchSW(x, y)) {
			for(int i=x+1, j=y-1; i < 8 && j >=0; i++, j--) {
				if(ChessboardView.chess[i][j] == -ChessboardView.sideState) {
					ChessboardView.chess[i][j] = ChessboardView.sideState;
				} else {
					break;
				}
			}
		}
		if(cbv.searchW(x, y)) {
			for(int j=y-1; j >=0; j--) {
				if(ChessboardView.chess[x][j] == -ChessboardView.sideState) {
					ChessboardView.chess[x][j] = ChessboardView.sideState;
				} else {
					break;
				}
			}
		}
		if(cbv.searchNW(x, y)) {
			for(int i=x-1, j=y-1; i>=0 && j >=0; i--, j--) {
				if(ChessboardView.chess[i][j] == -ChessboardView.sideState) {
					ChessboardView.chess[i][j] = ChessboardView.sideState;
				} else {
					break;
				}
			}
		}
	}
	
	public ChessBoardListener(ChessboardView cbv) {
		// TODO Auto-generated constructor stub
		this.cbv = cbv;
	}
	
	public void mouseClicked(MouseEvent e) {
		int xpos = e.getX();
		int ypos = e.getY() - ChessboardView.reviseY;
		if(isInboard(xpos, ypos)) {
			int j = (xpos - ChessboardView.boardX)*8 / ChessboardView.boardWidth;
			int i = (ypos - ChessboardView.boardY)*8 / ChessboardView.boardHeight;
			if(ChessboardView.chess[i][j] == 0 && cbv.canDown(i, j)) {
				ChessboardView.chess[i][j] = ChessboardView.sideState;
				changeColor(i,j);
				cbv.update();
			}
		}
	}
	
}
