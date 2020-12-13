import javax.swing.*;

public class Login {
	public void showUI() {
		ImageIcon icon = new ImageIcon("img/main_bk.jpg");//�����汳��
		JFrame frame = new JFrame();
		frame.setTitle("Code Collector - A Game");
		frame.setSize(icon.getIconWidth(),icon.getIconHeight());

		JButton button = new JButton();
		button.setText("��ʼ��Ϸ");
		button.setBounds(icon.getIconWidth()/2-60,icon.getIconHeight()/2-60,100,40);
		frame.add(button);//������Ϸ
		button.addActionListener(new LoginListener(frame));

		JLabel background = new JLabel(icon);
		background.setBounds(0,0,icon.getIconWidth(),icon.getIconWidth());
		frame.add(background);

		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
