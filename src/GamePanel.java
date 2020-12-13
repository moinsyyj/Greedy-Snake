import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

//@SuppressWarnings("serial")
public class GamePanel extends JPanel implements KeyListener, ActionListener{
	//矩阵宽度
	public static final int GRAD_PX =25;
	public static final int GRAD_X =20;
	public static final int GRAD_Y =20;
	//游戏速率
	public int speed;//速度控制，0为慢，1为正常，2为快
	private final Timer[] timer=new Timer[]{
			new Timer(200, this),
			new Timer(100, this),
			new Timer(30, this)
	};
	//计分控制
	private int len;//身体长度
	private int score;//分数
	//蛇和食物的位置
	private int[] snakeX = new int[GRAD_X*GRAD_Y];
	private int[] snakeY = new int[GRAD_X*GRAD_Y];
	private int[] map = new int[GRAD_X*GRAD_Y];
	private int foodX;
	private int foodY;//食物坐标
	private Random rand = new Random();//随机生成食物
	//运动方向控制
	private char dir;
	private char current_dir;
	//游戏状态控制
	public boolean isStarted;//是否开始游戏
	private boolean isFailed;//是否失败

	public GamePanel(){
		speed = 1;
		this.setFocusable(true);
		this.addKeyListener(this);
		init();
	}
	//游戏初始化
	public void init() {
		isFailed=false;
		isStarted = false;
		len = 3;
		score = 0;
		dir = 'R';
		current_dir = 'R';
		snakeX[0] = GRAD_PX*4;		snakeY[0] = GRAD_PX*4;
		snakeX[1] = GRAD_PX*3;		snakeY[1] = GRAD_PX*4;
		snakeX[2] = GRAD_PX*2;		snakeY[2] = GRAD_PX*4;
		for(int i=0;i<GRAD_X*GRAD_Y;i++) map[i]=0;//
		map[GRAD_X*4+4]=map[GRAD_X*4+3]=map[GRAD_X*4+3]=1;//
		foodX = GRAD_PX*rand.nextInt(GRAD_X);
		foodY = GRAD_PX*rand.nextInt(GRAD_Y);
		timer[speed].start();
		repaint();
	}
	//引入图片素材
	private static final ImageIcon BODY = new ImageIcon("img/body0.png");
	private static final ImageIcon HEAD_UP = new ImageIcon("img/head_up.png");
	private static final ImageIcon HEAD_DOWN = new ImageIcon("img/head_down.png");
	private static final ImageIcon HEAD_LEFT = new ImageIcon("img/head_left.png");
	private static final ImageIcon HEAD_RIGHT = new ImageIcon("img/head_right.png");
	private static final ImageIcon FOOD = new ImageIcon("img/food0.png");
	//绘图
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.LIGHT_GRAY);
		//画背景
		g.fillRect(0, 0, GRAD_PX*GRAD_X, GRAD_PX*GRAD_Y);
		//画头
		switch(dir){
			case 'R':
				HEAD_RIGHT.paintIcon(this, g, snakeX[0], snakeY[0]);
				break;
			case 'L':
				HEAD_LEFT.paintIcon(this, g, snakeX[0], snakeY[0]);
				break;
			case 'U':
				HEAD_UP.paintIcon(this, g, snakeX[0], snakeY[0]);
				break;
			case 'D':
				HEAD_DOWN.paintIcon(this, g, snakeX[0], snakeY[0]);
				break;
		}
		current_dir=dir;//延后设置current_dir是为了避免 屏幕刷新事件频率 跟不上 按键事件频率
		//画身体
		for(int i=1;i<len;i++) {
			BODY.paintIcon(this, g, snakeX[i], snakeY[i]);
		}
		//画食物
		FOOD.paintIcon(this, g, foodX, foodY);//绘制食物
		//画分数
		g.setFont(new Font("arial",Font.PLAIN,15));
		g.setColor(Color.WHITE);
		g.drawString("Code Length:"+len,15*GRAD_PX,15);
		g.drawString("Code Score :"+score,15*GRAD_PX,30);
		//继续游戏字幕
		g.setFont(new Font("arial",Font.BOLD,20));
		if(isStarted == false) {
			g.setColor(Color.LIGHT_GRAY);
			g.drawString("Take a break... press SPACE to keep working",GRAD_PX,10*GRAD_PX);
		}
		//游戏结束字幕
		if(isFailed) {
			g.setColor(Color.RED);
			g.drawString("Detect BUG blackhole... press SPACE to rewrite",GRAD_PX,10*GRAD_PX);
		}

	}
	//键盘事件
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_SPACE) {
			if(isFailed){
				isFailed = false;
				init();
			}else{
				isStarted = !isStarted;
			}
			repaint();
		}else if(isStarted){//如果暂停了则忽略按键
			//上下左右控制蛇身体移动
			switch (keyCode){
				case KeyEvent.VK_LEFT:
					if(current_dir != 'R') dir='L';
					break;
				case KeyEvent.VK_RIGHT:
					if(current_dir != 'L') dir='R';
					break;
				case KeyEvent.VK_UP:
					if(current_dir != 'D') dir='U';
					break;
				case KeyEvent.VK_DOWN:
					if(current_dir != 'U') dir='D';
					break;
			}
		}
	}
	public void keyTyped(KeyEvent e) { }
	public void keyReleased(KeyEvent e) { }
	@Override
	public void actionPerformed(ActionEvent e) {
		if(isStarted && !isFailed) {
			map[GRAD_X*snakeY[len-1]/GRAD_PX+snakeX[len-1]/GRAD_PX]=0;//
			for(int i=len;i>0;i--) {
				snakeX[i]= snakeX[i-1];
				snakeY[i]= snakeY[i-1];
			}
			//头部移动
			if(dir == 'U') {
				snakeY[0]-=GRAD_PX;
				if(snakeY[0]<0) snakeY[0]=GRAD_PX*(GRAD_Y-1);
			}else if(dir == 'D') {
				snakeY[0]+=GRAD_PX;
				if(snakeY[0]>=GRAD_PX*GRAD_Y) snakeY[0] = 0;
			}else if(dir == 'L') {
				snakeX[0]-=GRAD_PX;
				if(snakeX[0]<0) snakeX[0]=GRAD_PX*(GRAD_X-1);
			}else if(dir == 'R') {
				snakeX[0]+=GRAD_PX;
				if(snakeX[0]>=GRAD_PX*GRAD_X) snakeX[0] = 0;
			}
			map[GRAD_X*snakeY[0]/GRAD_PX+snakeX[0]/GRAD_PX]=1;//
			if(snakeX[0] == foodX && snakeY[0] == foodY) {
				map[GRAD_X*snakeY[len-1]/GRAD_PX+snakeX[len-1]/GRAD_PX]=1;//
				MusicPlayer.play("sound/github.wav",false);
				len++;
				score+=5;
				//避免食物出现在身体上
				do{
					foodX = GRAD_PX*rand.nextInt(GRAD_X-1);
					foodY = GRAD_PX*rand.nextInt(GRAD_Y-1);
				}while(map[GRAD_X*foodY/GRAD_PX+foodX/GRAD_PX]==1);
			}
			//是否出BUG
			if(len==GRAD_X*GRAD_Y){
				isStarted=false;
			}
			for(int i=1;i<len;i++) {
				if(snakeX[i]== snakeX[0] && snakeY[i]== snakeY[0]) {
					MusicPlayer.play("sound/gameover.wav",false);
					isFailed = true;
				}
			}
			repaint();
		}
		if(speed == 1) {
			timer[0].stop();
			timer[1].start();
			timer[2].stop();
		}else if(speed == 0) {
			timer[0].start();
			timer[1].stop();
			timer[2].stop();
		}else {
			timer[0].stop();
			timer[1].stop();
			timer[2].start();
		}
		
	}}