package gym;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.sun.awt.*;

import com.mysql.cj.xdevapi.Result;

public class gym extends Frame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		//创建窗口
		Frame f=new Frame();	
		f.setSize(500,500);
		f.setLocation(500,500);
		
		//设置关闭按钮
		f.addWindowListener(new WindowAdapter(){
	           @Override
	           public void windowClosing(WindowEvent arg0) {
	              System.exit(0);
	           }
	       });
		
		//连接数据库
		Connection con;
		String driver="com.mysql.cj.jdbc.Driver";
		String url="jdbc:mysql://localhost:3306/mydb?serverTimezone=UTC";
		String user="ruby";
		String password="ruby";
		try {
			Class.forName(driver);
			con=DriverManager.getConnection(url,user,password);
			if(!con.isClosed())
				System.out.println("Succeed connecting!");

			Log_in_frame l=new Log_in_frame(con,null);
			l.setVisible(true);		
			Toolkit kit=Toolkit.getDefaultToolkit();		
			Dimension c=kit.getScreenSize();	
			l.setLocation(c.width/2-500,c.height/2-500);
			
		}catch(ClassNotFoundException e) {
			System.out.println("not found!");
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			System.out.println("succeed!");
		}
		
		
		
		
		
	}
		
}

class Warning extends Frame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String User;
	Connection con;
	String message;
	Warning(Connection c,String u,Frame p_f,String m){
		message=m;
		
		Frame f=this;
		f.setTitle("警告");
		f.addWindowListener(new WindowAdapter(){
	           @Override
	           public void windowClosing(WindowEvent arg0) {
	              System.exit(0);
	           }
	       });
		
		f.setSize(300,150);
		f.setLocation(p_f.getX()+75,p_f.getY()+325);
		f.setVisible(true);
		
		Font font1=new Font("华文隶书",Font.PLAIN,25);
		Font font2=new Font("华文隶书",Font.PLAIN,15);
		
		Label word=new Label(message);
		word.setFont(font1);
		word.setSize(200,50);
		word.setLocation(90-(message.length()-5)*10, 50);
		f.add(word);
		
		JButton yes=new JButton("确定");
		yes.setFont(font2);
		yes.setSize(80,30);
		yes.setLocation(120,100);
		f.add(yes);
		
		f.add(new Label());
		
		yes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.setVisible(false);
				p_f.setVisible(true);
			}
		});
	}
}

class Reminder extends Frame{
	String User;
	Connection con;
	String message;
	Reminder(Connection c,String u,Frame p_f,String m){
		message=m;
		con=c;
		User=u;
		
		Frame f=this;
		f.setTitle("提示");
		f.addWindowListener(new WindowAdapter(){
	           @Override
	           public void windowClosing(WindowEvent arg0) {
	              System.exit(0);
	           }
	       });
		
		f.setSize(300,150);
		f.setLocation(p_f.getX()+75,p_f.getY()+325);
		f.setVisible(true);
		
		Font font1=new Font("华文隶书",Font.PLAIN,25);
		Font font2=new Font("华文隶书",Font.PLAIN,15);
		
		Label word=new Label("您已经"+message);
		word.setFont(font1);
		word.setSize(200,50);
		word.setLocation(90-(message.length()-5)*10, 50);
		f.add(word);
		
		JButton yes=new JButton("是");
		yes.setFont(font2);
		yes.setSize(80,30);
		yes.setLocation(120,100);
		f.add(yes);
		
		f.add(new Label());
		
		yes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.setVisible(false);
				p_f.setVisible(true);
			}
		});
	}
}

//登陆界面
class Log_in_frame extends Frame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String User;
	Connection con;
	Log_in_frame(Connection c,String s){
		User=s;
		con=c;
		Frame f=this;
		f.setSize(450,800);
		f.setResizable(false);

		this.setVisible(true);
		this.addWindowListener(new WindowAdapter(){
	           @Override
	           public void windowClosing(WindowEvent arg0) {
	              System.exit(0);
	           }
	       });
		
		Font font1=new Font("华文琥珀",Font.PLAIN,40);
		Font font2=new Font("华文隶书",Font.PLAIN,25);


		JButton log_in=new JButton("登陆");
		log_in.setFont(font2);
		log_in.setSize(f.getWidth()*21/125, f.getHeight()*45/1000);
		log_in.setLocation(f.getWidth()*127/500, f.getHeight()*101/125);
		log_in.setBorder(BorderFactory.createRaisedBevelBorder()); 
		log_in.setBorderPainted(false);
		log_in.setOpaque(false);
		
		JButton sign_in=new JButton("注册");
		sign_in.setFont(font2);
		sign_in.setSize(f.getWidth()*21/125,f.getHeight()*45/1000);
		sign_in.setLocation(f.getWidth()*577/1000,f.getHeight()*101/125);
		sign_in.setBorder(BorderFactory.createRaisedBevelBorder()); 
		
		JLabel words=new JLabel("XX大学体育馆租借系统");
		words.setFont(font1);
		words.setForeground(Color.LIGHT_GRAY);
		words.setSize(f.getWidth(),f.getHeight()*100/500);
		words.setLocation(f.getWidth()*30/500, f.getHeight()*47/500);
		
		JLabel user=new JLabel("学号:");
		user.setFont(font2);
		user.setForeground(Color.white);
		user.setSize(f.getWidth()*200/1000, f.getHeight()*63/1000);
		user.setLocation(f.getWidth()*270/1000, f.getHeight()*385/1000);
		
		JLabel password=new JLabel("密码:");
		password.setFont(font2);
		password.setForeground(Color.white);
		password.setSize(f.getWidth()*200/1000, f.getHeight()*63/1000);
		password.setLocation(f.getWidth()*270/1000, f.getHeight()*535/1000);
		
		JTextField user_name=new JTextField();
		user_name.setText(User);
		user_name.setBorder(BorderFactory.createRaisedBevelBorder());
		user_name.setSize(f.getWidth()*250/1000, f.getHeight()*30/1000);
		user_name.setLocation(f.getWidth()*410/1000, f.getHeight()*400/1000);
		
		JPasswordField pass_word=new JPasswordField();
		pass_word.setBorder(BorderFactory.createRaisedBevelBorder()); 
		pass_word.setSize(f.getWidth()*250/1000, f.getHeight()*30/1000);
		pass_word.setLocation(f.getWidth()*410/1000, f.getHeight()*550/1000);
		
		f.add(words);
		f.add(user);
		f.add(user_name);
		f.add(password);
		f.add(pass_word);
		f.add(log_in);
		f.add(sign_in);
		
		JLabel background=new JLabel();
		background.setSize(450,800);
		background.setLocation(0,0);
		background.setIcon(new ImageIcon("/Users/limingxia/Downloads/图片/gym.jpg"));
		
		f.add(background);
		f.setVisible(true);
		
		
		
		log_in.addActionListener(new ActionListener() {	
			String p=new String();
			public void actionPerformed(ActionEvent e) {
				System.out.println(user_name.getText());
				StringBuffer sql=new StringBuffer();
				try {
					Statement statement=con.createStatement();				
					sql.append("select password from user where user_id='");				
					sql.append(user_name.getText()+"';");				
					ResultSet rs=statement.executeQuery(sql.toString());
					
					if(!rs.next()) {
						Warning w=new Warning(con,User,f,"用户不存在");
						System.out.println("用户不存在");
					}
					else {
						p=rs.getString("password");
						if(!pass_word.getText().equals(p)) {
							//密码错误警告
							Warning w=new Warning(con,User,f,"密码错误");
							System.out.println("密码错误");
						}				
						else {
							//登陆成功，进入下级界面
							Function_choose_frame c_f=new Function_choose_frame(con,user_name.getText());
							c_f.setLocation(f.getLocation());
							c_f.setVisible(true);
							f.setVisible(false);
						}
					}	
				}catch(SQLException e1) {
					e1.printStackTrace();
				}catch(Exception e2) {
					e2.printStackTrace();
				}finally {
					System.out.println("succeed!");
				}	
				}
							
		});
		//登陆验证
		//密码错误||进入下一级界面
		sign_in.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sign_in_frame s_f=new Sign_in_frame(con,User);
				s_f.setLocation(f.getLocation());
				s_f.setVisible(true);
				f.setVisible(false);
			}
			
		});
	}
}

//注册界面
class Sign_in_frame extends Frame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String User;
	Connection con;
	Sign_in_frame(Connection c,String s){
		User=s;
		con=c;
		Frame f=this;
		f.setSize(450, 800);
		f.setResizable(false);
		f.addWindowListener(new WindowAdapter(){
	           @Override
	           public void windowClosing(WindowEvent arg0) {
	              System.exit(0);
	           }
	       });
		
		Font font1=new Font("华文隶书",Font.PLAIN,30);
		Font font2=new Font("华文隶书",Font.PLAIN,25);
		Font font3=new Font("华文隶书",Font.PLAIN,20);

		
		JButton confirm=new JButton("确认学号");
		confirm.setFont(font3);
		confirm.setSize(f.getWidth()/5,f.getHeight()/12);
		confirm.setLocation(f.getWidth()*89/120, f.getHeight()/15);
		f.add(confirm);

		JButton sign_in=new JButton("注册");
		sign_in.setFont(font2);
		sign_in.setSize(f.getWidth()/5, f.getHeight()/12);
		sign_in.setLocation(f.getWidth()*7/120, f.getHeight()*23/30);
		f.add(sign_in);

		JButton back=new JButton("返回");
		back.setFont(font2);
		back.setSize(f.getWidth()/5, f.getHeight()/12);
		back.setLocation(f.getWidth()/3, f.getHeight()*23/30);
		f.add(back);	

		JLabel userid=new JLabel("学号:");
		userid.setFont(font1);
		userid.setSize(f.getWidth()/5, f.getHeight()/12);
		userid.setLocation(f.getWidth()*7/120, f.getHeight()/20);
		f.add(userid);

		JLabel username=new JLabel("用户名:");
		username.setFont(font1);
		username.setSize(f.getWidth()/5, f.getHeight()/12);
		username.setLocation(f.getWidth()*7/120,f.getHeight()/11);
		f.add(username);
		
		JLabel password=new JLabel("密码:");
		password.setFont(font1);
		password.setSize(f.getWidth()/5, f.getHeight()/12);
		password.setLocation(f.getWidth()*7/120, f.getHeight()*7/24);
		f.add(password);

		JLabel confirm_p=new JLabel("确认密码:");
		confirm_p.setFont(font1);
		confirm_p.setSize(f.getWidth()/5, f.getHeight()/12);
		confirm_p.setLocation(f.getWidth()*7/120, f.getHeight()*9/20);
		f.add(confirm_p);

		JLabel phone=new JLabel("联系电话:");
		phone.setFont(font1);
		phone.setSize(f.getWidth()/5, f.getHeight()/12);
		phone.setLocation(f.getWidth()*7/120, f.getHeight()*73/120);
		f.add(phone);

		JTextField user_id=new JTextField();
		user_id.setSize(f.getWidth()/3, f.getHeight()/24);
		user_id.setLocation(f.getWidth()/3, f.getHeight()/15);
		f.add(user_id);

		JTextField user_name=new JTextField();
		user_name.setSize(f.getWidth()/3, f.getHeight()/24);
		user_name.setLocation(f.getWidth()/3, f.getHeight()/2);
		f.add(user_name);
		
		JPasswordField pass_word=new JPasswordField();
		pass_word.setSize(f.getWidth()/3, f.getHeight()/24);
		pass_word.setLocation(f.getWidth()/3, f.getHeight()*7/24);
		f.add(pass_word);

		JPasswordField confirm_pass_word=new JPasswordField();
		confirm_pass_word.setSize(f.getWidth()/3, f.getHeight()/12);
		confirm_pass_word.setLocation(f.getWidth()/3, f.getHeight()*9/20);
		f.add(confirm_pass_word);

		JTextField phone_number=new JTextField();
		phone_number.setSize(f.getWidth()/3, f.getHeight()/12);
		phone_number.setLocation(f.getWidth()/3, f.getHeight()*73/120);
		f.add(phone_number);

		JLabel background=new JLabel();
		background.setIcon(new ImageIcon("/Users/limingxia/Downloads/图片/sign_in.png"));
		f.add(background);
		
		f.setVisible(true);
		
		f.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				update();
			}
			private void update() {
				confirm.setSize(f.getWidth()*8/30,f.getHeight()/24);
				confirm.setLocation(f.getWidth()*85/120, f.getHeight()*3/20);
				sign_in.setSize(f.getWidth()/5, f.getHeight()/24);
				sign_in.setLocation(f.getWidth()/5, f.getHeight()*23/30);
				back.setSize(f.getWidth()/5, f.getHeight()/24);
				back.setLocation(f.getWidth()*3/5, f.getHeight()*23/30);
				
				userid.setSize(f.getWidth()/5, f.getHeight()/12);
				userid.setLocation(f.getWidth()*25/120, f.getHeight()*15/120);
				user_id.setSize(f.getWidth()/3, f.getHeight()/24);
				user_id.setLocation(f.getWidth()*11/30, f.getHeight()*3/20);
				
				username.setSize(f.getWidth()/3,f.getHeight()/12);
				username.setLocation(f.getWidth()*17/120,f.getHeight()*9/40);
				user_name.setSize(f.getWidth()/3, f.getHeight()/24);
				user_name.setLocation(f.getWidth()*11/30, f.getHeight()*15/60);
				
				password.setSize(f.getWidth()/5, f.getHeight()/12);
				password.setLocation(f.getWidth()*25/120, f.getHeight()*39/120);
				pass_word.setSize(f.getWidth()/3, f.getHeight()/24);
				pass_word.setLocation(f.getWidth()*11/30, f.getHeight()*42/120);
				
				confirm_p.setSize(f.getWidth()/3, f.getHeight()/12);
				confirm_p.setLocation(f.getWidth()*6/120, f.getHeight()*51/120);
				confirm_pass_word.setSize(f.getWidth()/3, f.getHeight()/24);
				confirm_pass_word.setLocation(f.getWidth()*11/30, f.getHeight()*54/120);
				
				phone.setSize(f.getWidth()/3, f.getHeight()/12);
				phone.setLocation(f.getWidth()*6/120, f.getHeight()*63/120);
				phone_number.setSize(f.getWidth()/3, f.getHeight()/24);
				phone_number.setLocation(f.getWidth()*11/30, f.getHeight()*66/120);
			}
		});
		
		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//检测是否存在重复学号
				if(user_id.getText().length()!=7) {
					Warning w=new Warning(con,User,f,"学号格式错误");
					System.out.println("学号格式错误");
				}
				else {
					StringBuffer sql=new StringBuffer();
					String num=new String();
					try {
						Statement statement=con.createStatement();				
						sql.append("select * from user where user_id='");				
						sql.append(user_id.getText()+"';");
						ResultSet rs=statement.executeQuery(sql.toString());
						if(rs.next()) {
							Warning w=new Warning(con,User,f,"学号已存在");
							System.out.println("学号已存在");
						}
						else {
							Warning w=new Warning(con,User,f,"学号可用");
							System.out.println("学号可用");
						}
					}catch(SQLException e1) {
						e1.printStackTrace();
					}catch(Exception e2) {
						e2.printStackTrace();
					}finally {
						System.out.println("succeed!");
					}
				}
			}
		});
		sign_in.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StringBuffer sql=new StringBuffer();
				try {
					Statement statement=con.createStatement();				
					sql.append("select * from user where user_id='");				
					sql.append(user_id.getText()+"';");
					ResultSet rs=statement.executeQuery(sql.toString());
					if(rs.next()) {
						Reminder r=new Reminder(con,User,f,"学号已存在");
					}			
					else {
						if(pass_word.getText().length()==0){
							//密码为空
							Warning w=new Warning(con,User,f,"密码不能为空");
							System.out.println("密码不能为空");
							}
						else if(!pass_word.getText().equals(confirm_pass_word.getText())) {
							//两次密码不一致
							Warning w=new Warning(con,User,f,"两次密码不一致");
							System.out.println("两次密码不一致");
						}
						else if(phone_number.getText().length()!=11) {
							//电话格式不对
							Warning w=new Warning(con,User,f,"电话格式错误");
							System.out.println("电话格式错误");
						}
						else {
							
							for(int i=0;i<phone_number.getText().length();i++) {
								if(phone_number.getText().charAt(i)<'0'||phone_number.getText().charAt(i)>'9') {
									//电话包含非法字符
									Warning w=new Warning(con,User,f,"电话号码应该全为数字");
									System.out.println("电话号码应该全为数字");
									return;
								}
							}
							//向数据库中插入新信息
							StringBuffer sql1=new StringBuffer();
							sql1.append("insert into user value('"+user_id.getText()+"','"+user_name.getText()+"','"+phone_number.getText()+"','"+pass_word.getText()+"');");
							statement.execute(sql1.toString());							
							
							Log_in_frame l_f=new Log_in_frame(con,user_id.getText());
							l_f.setName("登陆");
							l_f.setLocation(f.getLocation());
							l_f.setVisible(true);
							f.setVisible(false);
						}
					}
				}catch(SQLException e1) {
					e1.printStackTrace();
				}catch(Exception e2) {
					e2.printStackTrace();
				}finally {
					System.out.println("succeed!");
				}
			}
		});
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Log_in_frame l_f=new Log_in_frame(con,null);
				l_f.setName("登陆");
				l_f.setLocation(f.getLocation());
				l_f.setVisible(true);
				f.setVisible(false);
			}
		});
		
	}	
}

//功能选择界面
class Function_choose_frame extends Frame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String User;
	Connection con;
	Function_choose_frame(Connection c,String s){
		User=s;
		con=c;
		Frame f=this;
		f.setResizable(false);
		this.setSize(450,800);
		this.setVisible(true);
		this.addWindowListener(new WindowAdapter(){
	           @Override
	           public void windowClosing(WindowEvent arg0) {
	              System.exit(0);
	           }
	       });
		JButton user_info=new JButton("查看个人信息");
		user_info.setOpaque(false);
		user_info.setSize(f.getWidth()*2/3, f.getHeight()/10);
		user_info.setLocation(f.getWidth()/6, f.getHeight()/4);
		
		JButton reserve=new JButton("预定场地");
		reserve.setOpaque(true);
		reserve.setSize(f.getWidth()*2/3, f.getHeight()/10);
		reserve.setLocation(f.getWidth()/6, f.getHeight()*9/20);
		
		JButton log_out=new JButton("注销");
		log_out.setSize(f.getWidth()*2/3, f.getHeight()/10);
		log_out.setLocation(f.getWidth()/6, f.getHeight()*13/20);

		f.add(user_info);
		f.add(log_out);
		f.add(reserve);
		
		JLabel background=new JLabel();
		background.setIcon(new ImageIcon("/Users/limingxia/Downloads/图片/f_c.jpg"));
		f.add(background);
		
		f.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				update();
			}
			private void update() {
				user_info.setSize(f.getWidth()*2/3, f.getHeight()/10);
				user_info.setLocation(f.getWidth()/6, f.getHeight()/4);
				reserve.setSize(f.getWidth()*2/3, f.getHeight()/10);
				reserve.setLocation(f.getWidth()/6, f.getHeight()*9/20);
				log_out.setSize(f.getWidth()*2/3, f.getHeight()/10);
				log_out.setLocation(f.getWidth()/6, f.getHeight()*13/20);
				
			}
		});
		
		log_out.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Log_in_frame l_f=new Log_in_frame(con,null);
				
				l_f.setVisible(true);
				l_f.setLocation(f.getLocation());
				f.setVisible(false);
			}
		});
		user_info.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User_info_frame u_i=new User_info_frame(con,User);
				u_i.setName("登陆");
				
				u_i.setVisible(true);
				u_i.setLocation(f.getLocation());
				f.setVisible(false);
			}
		});
		reserve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Place_Choose_frame r_p=new Place_Choose_frame(con,User);
				r_p.setLocation(f.getLocation());
				r_p.setVisible(true);
				f.setVisible(false);
			}
		});
	}

}

//个人信息界面
class User_info_frame extends Frame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String User;
	Connection con;
	User_info_frame(Connection c,String s){
		User=s;
		con=c;
		Frame f=this;
		f.setResizable(false);

		this.setSize(450,800);
		this.addWindowListener(new WindowAdapter(){
	           @Override
	           public void windowClosing(WindowEvent arg0) {
	              System.exit(0);
	           }
	       });		
		Font font1=new Font("华文隶书",Font.PLAIN,20);
		
		JPanel button=new JPanel();
		button.setLayout(new GridLayout());
		JButton back=new JButton("返回");
		JButton log_out=new JButton("注销");
		button.add(back);
		button.add(log_out);
		button.setSize(f.getWidth()*300/1000,f.getHeight()*30/1000);
		button.setLocation(f.getWidth()*600/1000,f.getHeight()*129/1000);
		
		JLabel user_id=new JLabel("学号:"+User);
		user_id.setFont(font1);
		user_id.setSize(f.getWidth()*300/1000,f.getHeight()*80/1000);
		user_id.setLocation(f.getWidth()*120/1000,f.getHeight()*129/1000);
		user_id.setForeground(Color.orange);
		
		JLabel logo=new JLabel("图标");
		logo.setBackground(new Color(1));
		logo.setSize(f.getWidth()*200/1000,f.getHeight()*148/1000);
		logo.setLocation(f.getWidth()*400/1000,f.getHeight()*87/1000);
		logo.setIcon(new ImageIcon("/Users/limingxia/Downloads/图片/logo.png"));
		
		JLabel history=new JLabel("预定信息");	
		history.setFont(font1);
		history.setForeground(Color.GRAY);
		history.setSize(f.getWidth()*730/1000,f.getHeight()*50/1000);
		history.setLocation(f.getWidth()*135/1000,f.getHeight()*360/1000);
			
		TextArea message=new TextArea();
		message.setSize(f.getWidth()*730/1000,f.getHeight()*480/1000);
		message.setLocation(f.getWidth()*135/1000,f.getHeight()*445/1000);
		message.setEditable(false);
		
		JComboBox<String> info_d=new JComboBox<String>();
		info_d.setSize(250,30);
		info_d.setLocation(20, 210);
		f.add(info_d);
		
		JButton cancel=new JButton("取消预定");
		cancel.setSize(100,30);
		cancel.setLocation(290,210);
		f.add(cancel);
		
		f.add(button);
		f.add(user_id);
		f.add(logo);
		f.add(history);
		f.add(message);
		
		StringBuffer sql=new StringBuffer();
		try {
			Statement statement=con.createStatement();				
			sql.append("select * from rent where u_id='");
			sql.append(User+"' order by date;");
			ResultSet rs=statement.executeQuery(sql.toString());
			
			String[] info=new String[4];
			String date;
			String time;
			String place=new String();
			
			message.append("\n   日期\t\t   场地\t\t   时间段\t\t 状态\n\n");
			while(rs.next()) {
				info[0]=rs.getString("date");
				if(rs.getString("v_id").equals("1"))
					info[1]=rs.getString("s_id")+"号篮球场\t";
				else if(rs.getString("v_id").equals("2"))
					info[1]=rs.getString("s_id")+"号排球场\t";
				else if(rs.getString("v_id").equals("3"))
					info[1]=rs.getString("s_id")+"号乒乓球台";
				else if(rs.getString("v_id").equals("4"))
					info[1]=rs.getString("s_id")+"号羽毛球场";
				info[2]=(Integer.valueOf(rs.getString("t_id"))+8)+":00-"+(Integer.valueOf(rs.getString("t_id"))+9)+":00";
				Date now=new Date();
				if(now.compareTo(new SimpleDateFormat("yyyy-MM-dd").parse(info[0]))==1)
					info[3]="已生效";
				else {
					info[3]="可取消";
					info_d.addItem(info[0].substring(0,10)+" "+info[2]+" "+info[1]);
				}
				message.append(info[0].substring(0,10)+"\t"+info[1]+"\t"+info[2]+"\t"+info[3]+"\n\n");
				
			}
			
			
			
		}catch(SQLException e1) {
			e1.printStackTrace();
		}catch(Exception e2) {
			e2.printStackTrace();
		}finally {
			System.out.println("succeed!");
		}
		
		f.add(new Label());
		
		f.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				update();
			}
			private void update() {
				button.setSize(f.getWidth()*300/1000,f.getHeight()*30/1000);
				button.setLocation(f.getWidth()*640/1000,f.getHeight()*140/1000);
				user_id.setSize(f.getWidth()*300/1000,f.getHeight()*200/1000);
				user_id.setLocation(f.getWidth()*80/1000,f.getHeight()*60/1000);
				logo.setSize(f.getWidth()*218/1000,f.getHeight()*148/1000);
				logo.setLocation(f.getWidth()*360/1000,f.getHeight()*87/1000);
				history.setSize(f.getWidth()*730/1000,f.getHeight()*63/1000);
				history.setLocation(f.getWidth()*135/1000,f.getHeight()*387/1000);				
				//message.setSize(f.getWidth()*730/1000,f.getHeight()*480/1000);
				//message.setLocation(f.getWidth()*135/1000,f.getHeight()*445/1000);
			
			}
		});
		
		JLabel background=new JLabel();
		background.setIcon(new ImageIcon("/Users/limingxia/Downloads/图片/u_i.jpg"));
		f.add(background);
	
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Function_choose_frame c_f=new Function_choose_frame(con,User);
				c_f.setLocation(f.getLocation());
				c_f.setVisible(true);
				f.setVisible(false);
				}	
			});
		log_out.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Log_in_frame l_f=new Log_in_frame(con,null);
				
				l_f.setVisible(true);
				l_f.setLocation(f.getLocation());
				f.setVisible(false);
				}
			});
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StringBuffer sql=new StringBuffer();
				try {
					sql.append("call cancel_book(");
					int v=1;
					int t=1;
					String d=new String();
					int n=1;
					String str=(String) info_d.getSelectedItem();
					for(int i=0;i<str.length();i++) {
						if(str.charAt(i)==' ') {
							if(n==1) {
								d=str.substring(0,i);
								System.out.println(d);
								for(int j=i+1;j<str.length();j++) {
									if(str.charAt(j)==':') {
										t=Integer.parseInt(str.substring(i+1,j));
										break;
									}
								}
								n++;
							}
							else if(n==2) {
								int j=i+3;
								if(str.substring(j, str.length()).equals("篮球场"))
									v=1;
								else if(str.substring(j, str.length()).equals("排球场"))
									v=2;
								else if(str.substring(j, str.length()).equals("乒乓球台"))
									v=3;
								else if(str.substring(j, str.length()).equals("羽毛球场"))
									v=4;	
								break;
							}
							
						}
					}
					sql.append(v+",'"+d+"',"+(t-8)+",'"+User+"');");
					System.out.println(sql.toString());
					Statement statement=con.createStatement();				
					ResultSet rs=statement.executeQuery(sql.toString());
				}catch(SQLException e1) {
					e1.printStackTrace();
				}catch(Exception e2) {
					e2.printStackTrace();
				}finally {
					System.out.println("succeed!");
				}


				User_info_frame u_i=new User_info_frame(con,User);
				u_i.setLocation(f.getLocation());
				f.setVisible(false);
				u_i.setVisible(true);
			}
		});

	}
}

//场地选择界面
class Place_Choose_frame extends Frame{
	String User;
	Connection con;
	Place_Choose_frame(Connection c,String s){
		Frame f=this;
		f.setResizable(false);
		f.setSize(450,800);
		f.setVisible(true);
		this.addWindowListener(new WindowAdapter(){
	           @Override
	           public void windowClosing(WindowEvent arg0) {
	              System.exit(0);
	           }
	       });
		User=s;
		con=c;
		
		Font font=new Font("华文隶书",Font.PLAIN,22);
		
		JPanel button=new JPanel();
		button.setLayout(new GridLayout());
		JButton back=new JButton("返回");
		JButton log_out=new JButton("注销");
		button.add(back);
		button.add(log_out);
		button.setSize(f.getWidth()*300/1000,f.getHeight()*30/1000);
		button.setLocation(f.getWidth()*600/1000,f.getHeight()*900/1000);
		
		JButton basketball=new JButton("篮球 100元/场·h");
		basketball.setFont(font);
		basketball.setSize(f.getWidth()*491/1000,f.getHeight()*106/1000);
		basketball.setLocation(f.getWidth()*400/1000,f.getHeight()*91/1000);
		JLabel basketball_p=new JLabel();
		basketball_p.setSize(85,85);
		basketball_p.setLocation(f.getWidth()*400/1000-120,f.getHeight()*91/1000);
		basketball_p.setIcon(new ImageIcon("/Users/limingxia/Downloads/图片/b1.png"));
		f.add(basketball_p);
		
		JButton volleyball=new JButton("排球 80元/场·h");
		volleyball.setFont(font);
		volleyball.setSize(f.getWidth()*491/1000,f.getHeight()*106/1000);
		volleyball.setLocation(f.getWidth()*400/1000,f.getHeight()*319/1000);
		JLabel volleyball_p=new JLabel();
		volleyball_p.setSize(85,85);
		volleyball_p.setLocation(f.getWidth()*400/1000-120,f.getHeight()*319/1000);
		volleyball_p.setIcon(new ImageIcon("/Users/limingxia/Downloads/图片/v.png"));

		f.add(volleyball_p);
		
		JButton table_tennis=new JButton("乒乓球 20元/台·h");
		table_tennis.setFont(font);
		table_tennis.setSize(f.getWidth()*491/1000,f.getHeight()*106/1000);
		table_tennis.setLocation(f.getWidth()*400/1000,f.getHeight()*538/1000);
		JLabel table_tennis_p=new JLabel();
		table_tennis_p.setSize(85,85);
		table_tennis_p.setLocation(f.getWidth()*400/1000-120,f.getHeight()*538/1000);
		table_tennis_p.setIcon(new ImageIcon("/Users/limingxia/Downloads/图片/t.png"));
		f.add(table_tennis_p);
		
		JButton badminton=new JButton("羽毛球 20元/场·h");
		badminton.setFont(font);
		badminton.setSize(f.getWidth()*491/1000,f.getHeight()*106/1000);
		badminton.setLocation(f.getWidth()*400/1000,f.getHeight()*769/1000);
		JLabel badminton_p=new JLabel();
		badminton_p.setSize(85,85);
		badminton_p.setLocation(f.getWidth()*400/1000-120,f.getHeight()*769/1000);
		badminton_p.setIcon(new ImageIcon("/Users/limingxia/Downloads/图片/b2.png"));
		f.add(badminton_p);
		
		f.add(button);
		f.add(basketball);
		f.add(volleyball);
		f.add(table_tennis);
		f.add(badminton);
		
		JLabel background=new JLabel();
		background.setIcon(new ImageIcon("/Users/limingxia/Downloads/图片/p_c.jpg"));
		f.add(background);
		
		basketball.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Palace_reserve_frame r=new Palace_reserve_frame(con,User,1);
				r.setLocation(f.getLocation());
				r.setVisible(true);
				f.setVisible(false);
			}
		});
		volleyball.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Palace_reserve_frame r=new Palace_reserve_frame(con,User,2);
				r.setLocation(f.getLocation());
				r.setVisible(true);
				f.setVisible(false);
			}
		});
		table_tennis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Palace_reserve_frame r=new Palace_reserve_frame(con,User,3);
				r.setLocation(f.getLocation());
				r.setVisible(true);
				f.setVisible(false);
			}
		});
		badminton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Palace_reserve_frame r=new Palace_reserve_frame(con,User,4);
				r.setLocation(f.getLocation());
				r.setVisible(true);
				f.setVisible(false);
			}
		});
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Function_choose_frame c_f=new Function_choose_frame(con,User);
				c_f.setLocation(f.getLocation());
				c_f.setVisible(true);
				f.setVisible(false);
				}	
			});
		log_out.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Log_in_frame l_f=new Log_in_frame(con,null);
				
				l_f.setVisible(true);
				l_f.setLocation(f.getLocation());
				f.setVisible(false);
				}
			});
		
		}
	}

//场地预定
class Palace_reserve_frame extends Frame{
	String User;
	Connection con;
	String venue;
	int[] rest_num=new int[13];
	Palace_reserve_frame(Connection c,String s,int v){
		User=s;
		con=c;
		if(v==1)
			venue="basketball";
		else if(v==2)
			venue="volleyball";
		else if(v==3)
			venue="table tennis";
		else if(v==4)
			venue="badminton";
		
		Frame f=this;
		f.setResizable(false);
		this.setSize(450,800);
		this.addWindowListener(new WindowAdapter(){
	           @Override
	           public void windowClosing(WindowEvent arg0) {
	              System.exit(0);
	           }
	       });
		Font font1=new Font("华文隶书",Font.PLAIN,15);
		Font font2=new Font("华文隶书",Font.PLAIN,20);
		
		JPanel button=new JPanel();
		button.setLayout(new GridLayout());
		JButton back=new JButton("返回");
		JButton log_out=new JButton("注销");
		back.setBorder(BorderFactory.createRaisedBevelBorder()); 
		log_out.setBorder(BorderFactory.createRaisedBevelBorder()); 
		button.add(back);
		button.add(log_out);
		button.setSize(f.getWidth()*300/1000,f.getHeight()*30/1000);
		button.setLocation(280,f.getHeight()*900/1000);
		f.add(button);
		button.setBorder(BorderFactory.createRaisedBevelBorder()); 

		JPanel p=new JPanel();
		p.setLayout(null);
		p.setSize(380, 600);
		p.setLocation(35, 100);
		p.setBackground(Color.WHITE);
		
		f.add(p);
		
		JLabel date_l=new JLabel("日期:");
		date_l.setFont(font2);
		date_l.setSize(50,50);
		date_l.setLocation(100,20);
		p.add(date_l);
		
		JComboBox<String> date=new JComboBox<String>();	
		SimpleDateFormat s1=new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar=new GregorianCalendar();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, +0);
		date.addItem(s1.format(calendar.getTime()));
		calendar.add(Calendar.DATE, +1);
		date.addItem(s1.format(calendar.getTime()));
		calendar.add(Calendar.DATE, +1);
		date.addItem(s1.format(calendar.getTime()));
		calendar.add(Calendar.DATE, +1);
		date.addItem(s1.format(calendar.getTime()));
		calendar.add(Calendar.DATE, +1);
		date.addItem(s1.format(calendar.getTime()));
		calendar.add(Calendar.DATE, +1);
		date.addItem(s1.format(calendar.getTime()));
		calendar.add(Calendar.DATE, +1);
		date.addItem(s1.format(calendar.getTime()));
		date.setSize(100, 20);
		date.setLocation(150, 33);		
		p.add(date);
		
		JLabel[] rest=new JLabel[13];
		JButton[] reserve=new JButton[13];
		
		
		StringBuffer sql=new StringBuffer();
		try {
			Statement statement=con.createStatement();				
			sql.append("call site_info('"+venue+"','"+date.getSelectedItem()+"');");				
			ResultSet rs=statement.executeQuery(sql.toString());
			int i=1;
			while(rs.next()) {
				if(i==1)
					rest[i]=new JLabel("  "+(i+7)+":00-"+(i+8)+":00   剩余场地:"+rs.getString("cur_site_count"));
				else if(i==2)
					rest[i]=new JLabel(" "+(i+7)+":00-"+(i+8)+":00   剩余场地:"+rs.getString("cur_site_count"));
				else
					rest[i]=new JLabel((i+7)+":00-"+(i+8)+":00   剩余场地:"+rs.getString("cur_site_count"));
				
				rest[i].setSize(200,30);
				
				rest[i].setLocation(50, 70+40*i);
				p.add(rest[i]);
								
				reserve[i]=new JButton("预定");
				reserve[i].setFont(font1);
				reserve[i].setSize(100,30);
				reserve[i].setLocation(200,72+40*i);
				p.add(reserve[i]);
				
				rest_num[i]=rs.getInt("cur_site_count");
				
				i++;			
			}
		}catch(SQLException e1) {
			e1.printStackTrace();
		}catch(Exception e2) {
			e2.printStackTrace();
		}finally {
			System.out.println("succeed!");
		}
		
		date.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StringBuffer sql=new StringBuffer();
				try {

					Statement statement=con.createStatement();				
					sql.append("call site_info('"+venue+"','"+date.getSelectedItem()+"');");				
					ResultSet rs=statement.executeQuery(sql.toString());
					int i=1;
					while(rs.next()) {
						rest[i].setText((i+7)+":00-"+(i+8)+":00   剩余场地:"+rs.getString("cur_site_count"));						
						rest[i].setSize(200,30);
						rest[i].setLocation(50, 70+40*i);
										
						reserve[i]=new JButton("预定");
						reserve[i].setFont(font1);
						reserve[i].setSize(100,30);
						reserve[i].setLocation(200,72+40*i);
						p.add(reserve[i]);
						
						rest_num[i]=rs.getInt("cur_site_count");
						
						
						rest_num[i]=rs.getInt("cur_site_count");

						i++;				
					}
				}catch(SQLException e1) {
					e1.printStackTrace();
				}catch(Exception e2) {
					e2.printStackTrace();
				}finally {
					System.out.println("succeed!");
				}
			}
		});
		
		for(int i=1;i<=12;i++) {
			final int n=i;
			reserve[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(rest_num[n]==0) {
						f.setVisible(false);
						Frame warning=new Frame("警告");
						warning.setSize(150,75);
						warning.setLocation(f.getLocation());
						Panel p=new Panel();
						p.setLayout(new GridLayout());
						Label warn=new Label("当前无空闲场地，不可预订");
						Button back=new Button("返回");
						p.add(warn);
						p.add(back);
						warning.add(p);
						warning.setVisible(true);
						
						back.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								warning.setVisible(false);
								f.setVisible(true);
							}
						});
					}
					else {
						StringBuffer sql=new StringBuffer();
						try {

							Statement statement=con.createStatement();				
							sql.append("select book_site("+v+","+n+","+User+",'"+date.getSelectedItem().toString()+"');");				
							System.out.println(sql.toString());
							ResultSet rs=statement.executeQuery(sql.toString());
							int site=1;
							while(rs.next()) {
								site=rs.getInt(1);
							}
							reminder(date.getSelectedItem().toString(),n,site);
							
						}catch(SQLException e1) {
							e1.printStackTrace();
						}catch(Exception e2) {
							e2.printStackTrace();
						}finally {
							System.out.println("succeed!");
						}
					}
				}
			});
			
		}
		
		

		JLabel background=new JLabel();
		background.setSize(450,800);
		background.setLocation(0,0);
		if(v==1)
			background.setIcon(new ImageIcon("/Users/limingxia/Downloads/图片/basketball.jpeg"));
		else if(v==2)
			background.setIcon(new ImageIcon("/Users/limingxia/Downloads/图片/volleyball.jpeg"));
		else if(v==3)
			background.setIcon(new ImageIcon("/Users/limingxia/Downloads/图片/table tennis.jpg"));
		else if(v==4)
			background.setIcon(new ImageIcon("/Users/limingxia/Downloads/图片/badminton.jpg"));
		
		f.add(background);
		
		
		
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Place_Choose_frame r_p=new Place_Choose_frame(con,User);
				r_p.setLocation(f.getLocation());
				r_p.setVisible(true);
				f.setVisible(false);
				}	
			});
		log_out.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Log_in_frame l_f=new Log_in_frame(con,null);
				
				l_f.setVisible(true);
				l_f.setLocation(f.getLocation());
				f.setVisible(false);
				}
			});
	}

	void reminder(String date,int time,int site) {
		Frame f=this;
		
		this.setVisible(false);
		Frame remind=new Frame("提示");
		remind.setSize(200,100);
		remind.setLocation(this.getX()+125,this.getY()+350);
		remind.setVisible(true);
		remind.addWindowListener(new WindowAdapter(){
	           @Override
	           public void windowClosing(WindowEvent arg0) {
	              System.exit(0);
	           }
	       });
		
		Panel p=new Panel();
		p.setLayout(new GridLayout());
		p.setVisible(true);
		String str=new String();
		if(venue.equals("basketball")) {
			str=site+"号篮球场，是否继续";
		}
		else if(venue.equals("volleyball")) {
			str=site+"号排球场，是否继续";
		}
		else if(venue.equals("table tennis")) {
			str=site+"号乒乓球台，是否继续";
		}
		else if(venue.equals("badminton")) {
			str=site+"号羽毛球场，是否继续";
		}
		Label words=new Label("您已预定"+str);
		Button yes=new Button("是");
		Button no=new Button("否");
		p.add(words);
		p.add(yes);
		p.add(no);
		remind.add(p);
		
		
		yes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remind.setVisible(false);
				int v=0;
				if(venue.equals("basketball"))
					v=1;
				else if(venue.equals("volleytball"))
					v=2;
				else if(venue.equals("table tennis"))
					v=3;
				else if(venue.equals("badminton"))
					v=4;
				Palace_reserve_frame p_r=new Palace_reserve_frame(con,User,v);
				p_r.setLocation(f.getLocation());
				p_r.setVisible(true);
			}
		});
		no.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User_info_frame u_i=new User_info_frame(con,User);
				u_i.setLocation(f.getLocation());
				u_i.setVisible(true);
				remind.setVisible(false);
			}
		});
	}
}