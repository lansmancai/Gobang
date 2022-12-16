import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
/**
 * Date:16/12/2022
 * @author jinniu
 * @version 1.0
 */
public class Gobang
{
	//下面三个位图分别代表棋盘、黑子、白子
	BufferedImage table;
	BufferedImage black;
	BufferedImage white;
	// 当鼠标移动时候的选择框
	BufferedImage selected;
	// 定义棋盘的大小
	private static int BOARD_SIZE = 15;
	// 定义棋盘宽、高多少个像素
	private final int TABLE_WIDTH = 535;
	private final int TABLE_HETGHT = 536;
	// 定义棋盘坐标的像素值和棋盘数组之间的比率。
	private final int RATE = TABLE_WIDTH / BOARD_SIZE;
	// 定义棋盘坐标的像素值和棋盘数组之间的偏移距。
	private final int X_OFFSET = 5;
	private final int Y_OFFSET = 6;
	// 定义一个二维数组来充当棋盘
	private String[][] board = new String[BOARD_SIZE][BOARD_SIZE];
	// 五子棋游戏的窗口
	JFrame f = new JFrame("五子棋游戏");
	// 五子棋游戏棋盘对应的Canvas组件
	ChessBoard chessBoard = new ChessBoard();
	// 当前选中点的坐标
	private int selectedX = -1;
	private int selectedY = -1;
	//标记电脑赢了
	private boolean pcwin = false;
	//标记用户赢了
	private boolean userwin = false;
	//标记用户的前前一个下点
	private int prex = -1;
	private int prey = -1;
	//标记用户前一个下点
	private int x = -1;
	private int y = -1;
	
	
	
	//此函数计算用户是否五子连珠，是则用户胜出
	public void countuserwin(int x ,int y)
	{
		int[] index00 = {-4, -3, -2, -1, 0 , 1, 2, 3, 4};
		int[] index01 = {0, 0, 0, 0, 0, 0, 0, 0, 0};
		int[] index10 = {0, 0, 0, 0, 0, 0, 0, 0, 0};
		int[] index11 = {-4, -3, -2, -1, 0 , 1, 2, 3, 4};
		int[] index20 = {-4, -3, -2, -1, 0 , 1, 2, 3, 4};
		int[] index21 = {-4, -3, -2, -1, 0 , 1, 2, 3, 4};
		int[] index30 = {-4, -3, -2, -1, 0 , 1, 2, 3, 4};
		int[] index31 = {4, 3, 2, 1, 0, -1, -2, -3, -4};
		int sum0 = 0;
		int sum1 = 0;
		int sum2 = 0;
		int sum3 = 0;
		
		for (int i = 0; i < 5; i++)
		{   
			//横竖斜对角共四个方向，最多选包含X,Y坐标的9个点，计算是否存在连续5个子都是一致的，是则五子连珠
			for (int j = i; j < i + 5; j++)
			{
				if (0 <= x + index00[j]&& x + index00[j] < BOARD_SIZE &&0 <= y + index01[j]&&y + index01[j] < BOARD_SIZE&&board[x + index00[j]][y + index01[j]] == "1")
				{
					sum0++;
				}
				if (0 <= x + index10[j]&& x + index10[j] < BOARD_SIZE &&0 <= y + index11[j]&&y + index11[j] < BOARD_SIZE&&board[x + index10[j]][y + index11[j]] == "1")
				{
					sum1++;
				}
			    if (0 <= x + index20[j]&& x + index20[j] < BOARD_SIZE &&0 <= y + index21[j]&&y + index21[j] < BOARD_SIZE&&board[x + index20[j]][y + index21[j]] == "1")
				{
					sum2++;
				}
				if (0 <= x + index30[j]&& x + index30[j] < BOARD_SIZE &&0 <= y + index31[j]&&y + index31[j] < BOARD_SIZE&&board[x + index30[j]][y + index31[j]] == "1")
				{
					sum3++;
				}
			}
			//sum0,sum1,sum2,sum3 4个数有一个为5则五子连珠，打印出you win;
			if (sum0 == 5 || sum1 == 5 || sum2 == 5 || sum3 ==5)
			{
					userwin = true;
			}
			//没有五子连珠则计数全部归零进入下一轮
			sum0 = 0;
			sum1 = 0;
			sum2 = 0;
			sum3 = 0;
		}
	}
	//此函数计算电脑是否五子连珠，是则电脑赢了
	public void countpcwin(int x ,int y)
	{
		int[] index00 = {-4, -3, -2, -1, 0 , 1, 2, 3, 4};
		int[] index01 = {0, 0, 0, 0, 0, 0, 0, 0, 0};
		int[] index10 = {0, 0, 0, 0, 0, 0, 0, 0, 0};
		int[] index11 = {-4, -3, -2, -1, 0 , 1, 2, 3, 4};
		int[] index20 = {-4, -3, -2, -1, 0 , 1, 2, 3, 4};
		int[] index21 = {-4, -3, -2, -1, 0 , 1, 2, 3, 4};
		int[] index30 = {-4, -3, -2, -1, 0 , 1, 2, 3, 4};
		int[] index31 = {4, 3, 2, 1, 0, -1, -2, -3, -4};
		int sum0 = 0;
		int sum1 = 0;
		int sum2 = 0;
		int sum3 = 0;
		
		for (int i = 0; i < 5; i++)
		{   
			//横竖斜对角共四个方向，最多选包含X,Y坐标的9个点，计算是否存在连续5个子都是一致的，是则五子连珠
			for (int j = i; j < i + 5; j++)
			{
				if (0 <= x + index00[j]&& x + index00[j] < BOARD_SIZE &&0 <= y + index01[j]&&y + index01[j] < BOARD_SIZE&&board[x + index00[j]][y + index01[j]] == "2")
				{
					sum0++;
				}
				if (0 <= x + index10[j]&& x + index10[j] < BOARD_SIZE &&0 <= y + index11[j]&&y + index11[j] < BOARD_SIZE&&board[x + index10[j]][y + index11[j]] == "2")
				{
					sum1++;
				}
			    if (0 <= x + index20[j]&& x + index20[j] < BOARD_SIZE &&0 <= y + index21[j]&&y + index21[j] < BOARD_SIZE&&board[x + index20[j]][y + index21[j]] == "2")
				{
					sum2++;
				}
				if (0 <= x + index30[j]&& x + index30[j] < BOARD_SIZE &&0 <= y + index31[j]&&y + index31[j] < BOARD_SIZE&&board[x + index30[j]][y + index31[j]] == "2")
				{
					sum3++;
				}
			}
			//sum0,sum1,sum2,sum3 4个数有一个为5则五子连珠，打印出you win;
			if (sum0 == 5 || sum1 == 5 || sum2 == 5 || sum3 ==5)
			{
					pcwin = true;
			}
			//没有五子连珠则计数全部归零进入下一轮
			sum0 = 0;
			sum1 = 0;
			sum2 = 0;
			sum3 = 0;
		}
	}
	public void init() throws Exception
	{
		//读取位图
		table = ImageIO.read(new File("image/board.jpg"));
		black = ImageIO.read(new File("image/black.gif"));
		white = ImageIO.read(new File("image/white.gif"));
		selected = ImageIO.read(new File("image/selected.gif"));
		//初始棋盘数组全部复制为0，代表没有黑子也没有白子
		for (var i = 0; i < BOARD_SIZE; i++)
		{
			for (var j = 0; j < BOARD_SIZE; j++)
			{
				board[i][j] = "0";
			}
		}
		chessBoard.setPreferredSize(new Dimension(TABLE_WIDTH, TABLE_HETGHT));
		chessBoard.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				if (pcwin||userwin) return ;
				var userx = (int)((e.getX() - X_OFFSET) / RATE);
				var usery = (int)((e.getY() - Y_OFFSET ) / RATE);
				//代表一个棋子从正北方向顺时针开始的8个临近节点
				int[] xplus = {0, 1, 1, 1, 0, -1, -1, -1};
				int[] yplus = {-1, -1,0, 1, 1, 1, 0 , -1};
				//如果坐标上面已经有黑子或者白子了，用return;直接返回，不执行下面的内容
				if (board[userx][usery].equals("1")||board[userx][usery].equals("2"))
				{
					return ;
				}
				//用户下黑子
				board[userx][usery] = "1";
				//如果是第一个黑子则，prex,prey和x,y是一致的，不是第一个则进行前前位置和前位置的更新。
				if(prex == -1)
				{
					x = userx;
					y = usery;
					prex = x;
					prey = y;
				}
				else
				{
					prex = x;
					prey = y;
					x = userx;
					y = usery;
				}
				countuserwin(userx, usery);
				//电脑下棋
				var cpx = -1;
				var cpy = -1;
				var cpover = 0;
				//用cpover保证下棋完成
				while ( cpover < 1)
				{
					//cpx = (int)(Math.random() * BOARD_SIZE);
					//cpy = (int)(Math.random() * BOARD_SIZE);
					//棋盘就一个黑子，则从正北方向顺时针围堵黑子，哪个位置先有效先下哪个。
					if ((x == prex)&&(y == prey))
					{
						for (int i = 0; i< 7; i++)
						{
							cpx = x + xplus[i];
							cpy = y + yplus[i];
							if ((cpx > -1)&&(cpx < BOARD_SIZE)&&(cpy > -1)&&(cpy < BOARD_SIZE))
							{
								break;
							}
						}
					}
					//棋盘上不止一个黑子
					if ((x != prex)||(y != prey))
					{
						//判断是否前两个黑子是二子连珠的，是则进行堵截
						for (int i = 0; i < 8; i++)
						{
							if ((prex + xplus[i] == x)&&(prey + yplus[i] == y))
							{
									cpx = x + xplus[i];
									cpy = y + yplus[i];
									//二子连珠则顺势堵截
									if ((cpx > -1)&&(cpx < BOARD_SIZE)&&(cpy > -1)&&(cpy < BOARD_SIZE)&&board[cpx][cpy] != "1" && board[cpx][cpy] != "2")
									{
										break;
									}
									cpx = prex + -1 * xplus[i];
									cpy = prey + -1 * yplus[i];
									//没法顺势堵截则尝试反方向堵截下
									if ((cpx > -1)&&(cpx < BOARD_SIZE)&&(cpy > -1)&&(cpy < BOARD_SIZE)&&board[cpx][cpy] != "1" && board[cpx][cpy] != "2")
									{
										break;
									}
									//还是不行则生成个随机的下棋坐标
									else 
									{
										cpx = (int)(Math.random() * cpx);
										cpy = (int)(Math.random() * cpy);
									}
							}
						}
						//没有二子连珠则随机生成个下棋坐标
						if (cpx == -1)
						{
							cpx = (int)(Math.random() * x);
							cpy = (int)(Math.random() * y);
						}
					}
					if(board[cpx][cpy] != "1" && board[cpx][cpy] != "2")
					{
						board[cpx][cpy] = "2";
						cpover++;	
					}
				}
				//计算电脑是否赢了
				countpcwin(cpx, cpy);
				chessBoard.repaint();
				
			}
		
			public void mouseExited(MouseEvent e)
			{
				selectedX = -1;
				selectedY = -1;
				chessBoard.repaint();
			}
		});
		chessBoard.addMouseMotionListener(new MouseMotionAdapter()
		{
			//鼠标移动时显示选中
			public void mouseMoved(MouseEvent e)
			{
				if (userwin||pcwin) return ;
				selectedX = (e.getX() - X_OFFSET) / RATE;
				selectedY = (e.getY() - Y_OFFSET) / RATE;
				chessBoard.repaint();
			}
		});
		f.add(chessBoard);
		f.pack();
		f.setVisible(true);
	}
	public static void main(String[] args) throws Exception
	{
		var gb = new Gobang();
		gb.init();
	}
	class ChessBoard extends JPanel
	{
		// 重写JPanel的paint方法，实现绘画
		public void paint(Graphics g)
		{
			// 将绘制五子棋棋盘
			g.drawImage(table, 0, 0, null);
			// 绘制选中点的红框
			if (selectedX >= 0 && selectedY >= 0)
			{
				g.drawImage(selected, selectedX * RATE + X_OFFSET, selectedY * RATE + Y_OFFSET, null);
			}
			// 遍历数组，绘制棋子。
			for (var i = 0; i < BOARD_SIZE; i++)
			{
				for (var j = 0; j < BOARD_SIZE; j++)
				{
					// 绘制黑棋
					if (board[i][j].equals("1"))
					{
						g.drawImage(black, i * RATE + X_OFFSET, j * RATE + Y_OFFSET, null);
					}
					// 绘制白棋
					if (board[i][j].equals("2"))
					{
						g.drawImage(white, i * RATE + X_OFFSET, j * RATE + Y_OFFSET, null);
					}
				}
			}
			//如果用户赢了则显示用户获胜
			if (userwin)
			{
				BufferedImage imageuserwin = new BufferedImage(535,535, BufferedImage.TYPE_INT_ARGB);
				Graphics guserwin = imageuserwin.getGraphics();
				guserwin.setColor(new Color(255, 0, 0));
				guserwin.setFont(new Font("Times", Font.BOLD, 60));
				guserwin.drawString("用户获胜", 100, 200);
				g.drawImage(imageuserwin, 0, 0, null);
			}
			//如果电脑赢了则显示电脑获胜
			if (pcwin)
			{
				BufferedImage imageuserwin = new BufferedImage(535,535, BufferedImage.TYPE_INT_ARGB);
				Graphics guserwin = imageuserwin.getGraphics();
				guserwin.setColor(new Color(255, 0, 0));
				guserwin.setFont(new Font("Times", Font.BOLD, 60));
				guserwin.drawString("电脑获胜", 100, 200);
				g.drawImage(imageuserwin, 0, 0, null);
			}
		}
	}
}
