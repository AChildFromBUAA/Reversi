
public class ReversiAI {
    private int searchDep;
    private int Color;
    private int[] dx = { -1, -1, -1, 0, 0, 1, 1, 1};
    private int[] dy = { -1, 0, 1, -1, 1, -1, 0, 1};
    private int[][] boardPts = {
        {1 << 24  , 1      , 1 << 20  , 1 << 16  , 1 << 16  , 1 << 20  , 1      , 1 << 24  },
        {1      , 1      , 1 << 16  , 1 << 4   , 1 << 4   , 1 << 16  , 1      , 1      },
        {1 << 20  , 1 << 16  , 1 << 12  , 1 << 8   , 1 << 8   , 1 << 12  , 1 << 16  , 1 << 20  },
        {1 << 16  , 1 << 4   , 1 << 8   , 0      , 0      , 1 << 8   , 1 << 4   , 1 << 16  },
        {1 << 16  , 1 << 4   , 1 << 8   , 0      , 0      , 1 << 8   , 1 << 4   , 1 << 16  },
        {1 << 20  , 1 << 16  , 1 << 12  , 1 << 8   , 1 << 8   , 1 << 12  , 1 << 16  , 1 << 20  },
        {1      , 1      , 1 << 16  , 1 << 4   , 1 << 4   , 1 << 16  , 1      , 1      },
        {1 << 24  , 1      , 1 << 20  , 1 << 16  , 1 << 16  , 1 << 20  , 1      , 1 << 24  }
    };//棋盘的估值分数

    public ReversiAI() {
    	
    }
    
    public ReversiAI(int searchDep, int Color) { //构造函数，参数输入思考步数（决定AI搜索深度）和AI的落子颜色
        this.Color = Color;
        this.searchDep = searchDep;
    }//Constructor

    public void setSearchDep(int searchDep) {
    	this.searchDep = searchDep;
    }
    
    public int getSearchDep() {
    	return this.searchDep;
    }
    
    public void setColor(int color) {
    	this.Color = color;
    }
    
    public int getSide() {
    	return this.Color;
    }
    
    public Boolean checkMovable(int[][] chessBoard, int Color, int x, int y) { //参数输入当前棋盘信息和落子方颜色，以及落子位置（已拆成x,y），返回该位置是否可落子
        Boolean ret = false;
        if (chessBoard[x][y]!=0) return false;
        for (int i = 0; i < 8; i++)
            if (chessBoard[x + dx[i]][y + dy[i]] == -Color) {
                Boolean flag = true;
                for (int j = 1; j <= 8; j++)
                    if (chessBoard[x + dx[i] * j][y + dy[i] * j] != -Color) {
                        if (chessBoard[x + dx[i] * j][y + dy[i] * j] == 0) flag = false;
                        break;
                    }//if
                if (flag) {
                    ret = true;
                    break;
                }//if flag
            }//if
        return ret;
    }//checkMovable

    public int[][] Move(int[][] chessBoard, int pos, int Color) { //参数输入当前棋盘信息和落子位置及落子方颜色，返回落子后的棋盘信息
        int[][] ret = new int[10][10];
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++) ret[i][j] = chessBoard[i][j];
        int x = (pos - 1) / 8 + 1, y = (pos - 1) % 8 + 1;
        ret[x][y] = Color;
        for (int i = 0; i < 8; i++)
            if (ret[x + dx[i]][y + dy[i]] == -Color) {
                Boolean flag = true;
                for (int j = 1; j < 8; j++)
                    if (ret[x + dx[i] * j][y + dy[i] * j] != -Color) {
                        if (ret[x + dx[i] * j][y + dy[i] * j] == 0) flag = false;
                        break;
                    }//if
                if (flag) {
                    for (int j = 1; j < 8; j++)
                        if (ret[x + dx[i] * j][y + dy[i] * j] == -Color) ret[x + dx[i] * j][y + dy[i] * j] = Color;
                        else break;
                }//if flag
            }//if
        return ret;
    }

    public int[] moveQueue(int[][] chessBoard, int Color) { //参数输入当前棋盘信息和落子方颜色，返回一个落子方可走位置的数组
        int[] Q = new int[70];
        for (int i = 0; i < 70; i++) Q[i] = 0;
        int cnt = 0;
        for (int i = 1; i <= 64; i++) {
            int nx = (i - 1) / 8 + 1, ny = (i - 1) % 8 + 1;
            if (checkMovable(chessBoard, Color, nx, ny)) {
                Q[cnt] = i;
                cnt++;
            }//if
        }//for i
        return Q;
    }//moveQueue

    public int calcPts(int[][] chessBoard, int Color) { //计算当前棋盘分数
        int ret = 0;
        for (int i = 1; i <= 8; i++)
            for (int j = 1; j <= 8; j++) ret += chessBoard[i][j] * boardPts[i - 1][j - 1];
        ret *= Color;
        return ret;
    }//calcPts

    public int[] getPtsQueue(int[][] chessBoard, int cnt, int Color, int[] Q, int depth) { //参数输入当前棋盘和可走位置数组的相关信息，返回一个对应可走位置数组的得分数组
        int[] ret = new int[70];
        for (int i = 0; i < 70; i++) ret[i] = 0;
        for (int i = 0; i < cnt; i++) {
            //System.out.println("getPtsQueue: i="+i+" pos="+Q[i]+" depth="+depth);
            //printChessBoard(chessBoard);
            int[][] tempBoard = Move(chessBoard, Q[i], Color); //在Q[i]位置落子，得到改变后的棋盘信息
            //printChessBoard(tempBoard);
            int[] Q1=moveQueue(tempBoard,Color),Q2=moveQueue(tempBoard,-Color);
            int cnt1=0,cnt2=0;//cnt1和cnt2分别储存双方的行动力
            for (int j=0;j<70;j++)
            	if (Q1[j]==0)
            	{
            		cnt1=j;
            		break;
            	}//if
            for (int j=0;j<70;j++)
            	if (Q2[j]==0)
            	{
            		cnt2=j;
            		break;
            	}//if
            int tempPts = calcPts(tempBoard, Color)+cnt1-cnt2; //计算改变后的棋盘分数以及行动力对棋盘分数的影响
            if (depth < this.searchDep) ret[i] = tempPts - opponentMove(tempBoard, -Color, depth); //若未达到搜索层数searchDep，则计算对手行动，减去对手行动的最高分
            else ret[i] = tempPts; //否则（即达到搜索层数）直接返回棋盘分数
        }//for i
        return ret;
    }//getPtsQueue

    public int[][] transChessBoard(int[][] chessBoard)
	{
		int[][] ret=new int[10][10];
		for (int i=0;i<10;i++)
			for (int j=0;j<10;j++) ret[i][j]=0;
		for (int i=1;i<=8;i++)
			for (int j=1;j<=8;j++) ret[i][j]=chessBoard[i-1][j-1];
		return ret;
	}//transChessBoard
    
    public int[][] transChessBoard2(int[][] chessBoard)
    {
    	int[][] ret=new int[8][8];
    	for (int i=0;i<8;i++)
    		for (int j=0;j<8;j++) ret[i][j]=chessBoard[i+1][j+1];
    	return ret;
    }//transChessBoard2
    
    public int getMaxNum(int[] pts, int cnt) {
        int ret = 0;
        for (int i = 0; i < cnt; i++)
            if (pts[i] > pts[ret]) ret = i;
        return ret;
    }//getMaxNum

    public int opponentMove(int[][] chessBoard, int Color, int depth) { //对手行动，参数为当前棋盘，对手颜色，和搜索深度，返回为对手在当前棋盘下获得的最高分
        int ret = 0, cnt = 0;
        int[] Q = moveQueue(chessBoard, Color); //得到可以走的位置的序列
        //System.out.println("ChessBoard 1:");
        //printChessBoard(chessBoard);
        for (int i = 0; i < 70; i++) //得到可以走的位置的总数
            if (Q[i] == 0) {
                cnt = i;
                break;
            }//if
        //System.out.println("ChessBoard 2:");
        //printChessBoard(chessBoard);
        int[] pts = getPtsQueue(chessBoard, cnt, Color, Q, depth + 1); //得到每一个可以走的位置的分数
        ret = getMaxNum(pts, cnt); //从中挑选最大分数返回
        return pts[ret];
    }//opponentMove

    public int finalReturn(int[][] nowBoard) { //AI读入玩家落子后的棋盘信息nowBoard，并开始进行落子位置的决策，返回为AI落子的位置
        //int[][] ret;
        int ret;
        int[] Q = moveQueue(nowBoard, Color); //得到可以走的位置的序列
        int cnt = 0;
        for (int i = 0; i < 70; i++)
            if (Q[i] == 0) {
                cnt = i;
                break;
            }//if
        //System.out.println("NowBoard 1:");
        //printChessBoard(nowBoard);
        int[] pts = getPtsQueue(nowBoard, cnt, Color, Q, 0); //开始计算每个可走位置的分数，最后的0表示目前搜索深度为0,达到searchDep后即不再往下搜索

        //System.out.println("NowBoard 2:");
        //printChessBoard(nowBoard);
        int which = getMaxNum(pts, cnt);
        //System.out.println("which="+which+" pos="+Q[which]);
        //ret=Move(nowBoard,Q[which],Color);//对nowBoard棋盘信息，AI选择落子在Q[which]处，并得到落子并改变后的棋盘信息
        ret = Q[which]; //返回位置=（行－1）*8+列
        //printChessBoard(ret);
        return ret;
    }

    public void printChessBoard(int[][] chessBoard) {
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) System.out.print(chessBoard[i][j] + " ");
            System.out.println();
        }//for i
        System.out.println();
    }//printChessBoard

    public int getColor() {
        return this.Color;
    }
    /*
    public static void main(String[] args) {
        int[][] chessBoard = new int[10][10];
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++) chessBoard[i][j] = 0;
        chessBoard[4][4] = -1;
        chessBoard[4][5] = chessBoard[5][4] = chessBoard[5][5] = chessBoard[5][6] = 1;
        ReversiAI R = new ReversiAI(6, -1);
        //R.printChessBoard(chessBoard);
        //int[][] newBoard=R.finalReturn(chessBoard);
        int pos = R.finalReturn(chessBoard);
        int[][] newBoard = R.Move(chessBoard, pos, R.getColor());
        R.printChessBoard(newBoard);
    }//main
    */ //example
}