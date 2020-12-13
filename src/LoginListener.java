import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class LoginListener implements ActionListener{
	private JFrame login;
	public LoginListener(JFrame login){
		this.login=login;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFrame frame = new JFrame();
		frame.setTitle("Cloning Code...");
		GamePanel panel=new GamePanel();
		frame.add(panel);

		ActionListener l0 = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {panel.speed = 0;}
		};
		ActionListener l1 = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {panel.speed = 1;}
		};
		ActionListener l2 = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {panel.speed = 2;}
		};
		ActionListener l3 = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.init();
			}
		};

		JMenuBar menuBar = new JMenuBar();
		JMenu m0 = new JMenu("选项");
		JMenuItem m0_0 = new JMenuItem("简单");
		m0_0.addActionListener(l0);
		m0.add(m0_0);
		JMenuItem m0_1 = new JMenuItem("普通");
		m0_1.addActionListener(l1);
		m0.add(m0_1);
		JMenuItem m0_2 = new JMenuItem("鬼畜");
		m0_2.addActionListener(l2);
		m0.add(m0_2);
		JMenuItem m0_3 = new JMenuItem("重新开始");
		m0_3.addActionListener(l3);
		m0.add(m0_3);
		menuBar.add(m0);
		frame.setJMenuBar(menuBar);

		//frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setSize(panel.GRAD_PX*panel.GRAD_X+15,panel.GRAD_PX*panel.GRAD_Y+64);//窗口大小900*700
		//frame.setResizable(false);//禁用调整窗口大小
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		login.dispose();
	}

}
