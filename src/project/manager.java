package project;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

class exitt extends manager{
	public JFrame f3=new JFrame();
	
	exitt(){
		f3.setTitle("종료확인");
		f3.setSize(300,150);
		f3.setLocation(500,300);
		
		JPanel p4 = new JPanel();
		p4.setLayout(null);
		
		JLabel l = new JLabel("정말 종료하시겠습니까?");
		l.setBounds(60,15,200,32);
		p4.add(l);
		
		JButton c=new JButton("취 소");
		c.setFont(new Font("HY나무L",Font.BOLD,13));
		c.setBounds(50,65,90,32);
		p4.add(c);
		
		JButton e=new JButton("종 료");
		e.setFont(new Font("HY나무L",Font.BOLD,13));
		e.setBounds(130,65,90,32);
		p4.add(e);
		
		f3.add(p4);
		f3.setVisible(true);
		
		c.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f3.dispose();
			}
		});
		e.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manager.dispose();
				f3.dispose();
			}
		});
	}
}
public class manager extends JFrame implements ActionListener{
	private CardLayout cards = new CardLayout();
	
	private static JPanel contentPane;
	public static JFrame manager=new JFrame();
	public JPanel menu_p=new JPanel(); //메뉴관리
	public JPanel money_p=new JPanel(); //매출관리
	public JPanel inven_p=new JPanel(); //재고관리
	public JComboBox comboBox; //일매출

	String kind=null;
	
	String header[]= {"     메뉴","      종류","     재고"}; //재고확인
	DefaultTableModel model = new DefaultTableModel(header,0);
	JTable inventable = new JTable(model);
	
	String headerm[]= {"날짜","금액"}; //매출확인
	DefaultTableModel mod = new DefaultTableModel(headerm,0);
	JTable money_table = new JTable(mod);
	
	String headerr[]= {"     메뉴","     가격","     종류","     재고"};//메뉴테이블
	DefaultTableModel modell = new DefaultTableModel(headerr,0);
	JTable menu_table = new JTable(modell);
	
	int sale_[]=new int[100];
	String [] sale_m=new String[100];
	String[] date=new String[1000];
	int mon[]=new int[1000];
	String col_y=null;
	String col_m=null;
	String col_d=null;
	String ch=null;
	int tmoney=0;
	int x=0;
	int check=0;
	
	public void money() { 
		money_p.setBounds(12, 43, 809, 426);
		money_p.setLayout(null);
		money_p.setVisible(true);
		
		String[] index_d=new String[40];
		String[] index_m=new String[40];
		String[] index_y=new String[40];
		
		for(int z=0;z<31;z++) {
			if(z<9) {
				index_d[z]="0"+Integer.toString(z+1);
			}
			else {
			index_d[z]=Integer.toString(z+1);
			}
		}
		for(int z=0;z<12;z++) {
			if(z<9) {
				index_m[z]="0"+Integer.toString(z+1);
			}
			else {
				index_m[z]=Integer.toString(z+1);
			}
		}
		for(int z=0;z<10;z++) {
			index_y[z]=Integer.toString(2015+z);
		}
		
		Connection con=null;
		PreparedStatement ps=null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/soyeon?serverTimezone=UTC","root","1234");
			System.out.println("sales 테이블 연결 완료");
			Statement stmt=conn.createStatement();
			String sql="SELECT * FROM sales";
			ResultSet rs=stmt.executeQuery(sql);
		
			while(rs.next()) {
				String n=rs.getString(1);
				date[x]=rs.getString(2);
				String m=rs.getString(3);
				mon[x]=rs.getInt(4);
				x++;
			}
			conn.close();
		}catch(ClassNotFoundException e1) {
			System.out.println("JDBC 드라이버가 존재하지 않습니다"+e1);
		} catch(java.sql.SQLException e1) {
			System.out.println("DB SQL문 오류:"+e1);
		}catch(Exception e1) {
			e1.printStackTrace(); 
		}
	
		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setFont(new Font("HY나무L", Font.PLAIN, 18));
		lblNewLabel.setBounds(160, 111, 32, 29);
		money_p.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("총 매출 : ");
		lblNewLabel_1.setFont(new Font("HY나무L", Font.BOLD, 18));
		lblNewLabel_1.setBounds(56, 216, 81, 34);
		money_p.add(lblNewLabel_1);

		JLabel lbltotal = new JLabel();
		lbltotal.setFont(new Font("HY나무L", Font.PLAIN, 18));
		lbltotal.setBounds(149, 217, 134, 34);
		money_p.add(lbltotal);
		money_p.setVisible(true);
		
		money_table.setFillsViewportHeight(true);
		money_table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scroll=new JScrollPane(money_table);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setViewportView(money_table);
		scroll.setBounds(326, 54, 447, 329);
		money_p.add(scroll);
		
		comboBox=new JComboBox(index_d);
		comboBox.setBounds(24, 112, 124, 29);
		money_p.add(comboBox);
		
		JButton btnyear = new JButton("년매출");
		btnyear.setBounds(223, 62, 91, 23);
		money_p.add(btnyear);
		btnyear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ch="year";
				mod.setNumRows(0);
				tmoney=0;
				lbltotal.setText(Integer.toString(tmoney));
				lblNewLabel.setText("년");
				DefaultComboBoxModel model3=new DefaultComboBoxModel(index_y);
				comboBox.setModel(model3);
				comboBox.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JComboBox jcb=(JComboBox)e.getSource();
						int indexx = jcb.getSelectedIndex();	
						col_y=index_y[indexx];
					}
				});
			}
		});
		
		JButton btnmonth = new JButton("월매출");
		btnmonth.setBounds(120, 62, 91, 23);
		money_p.add(btnmonth);
		btnmonth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ch="month";
				mod.setNumRows(0);
				tmoney=0;
				lbltotal.setText(Integer.toString(tmoney));
				lblNewLabel.setText("월");
				DefaultComboBoxModel model1=new DefaultComboBoxModel(index_m);
				comboBox.setModel(model1);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-");
		        Calendar c1 = Calendar.getInstance();
		        String strToday = sdf.format(c1.getTime());
				
				comboBox.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JComboBox jcb=(JComboBox)e.getSource();
						int indexx = jcb.getSelectedIndex();
						col_m=strToday+index_m[indexx];
					}
				});
			}
		});
		
		JButton btnday = new JButton("일매출");
		btnday.setBounds(17, 62, 91, 23);
		money_p.add(btnday);
		btnday.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ch="day";
				mod.setNumRows(0);
				tmoney=0;
				lbltotal.setText(Integer.toString(tmoney));
				lblNewLabel.setText("일");
				DefaultComboBoxModel model2=new DefaultComboBoxModel(index_d);
				comboBox.setModel(model2);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-");
		        Calendar c1 = Calendar.getInstance();
		        String strToday = sdf.format(c1.getTime());
				comboBox.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JComboBox jcb=(JComboBox)e.getSource();
						int indexx = jcb.getSelectedIndex();
						col_d=strToday+index_d[indexx];
					}
				});
			}
		});
	
		JButton btnload = new JButton("조회");
		btnload.setFont(new Font("HY나무L", Font.PLAIN, 12));
		btnload.setBounds(212, 112, 91, 29);
		btnload.addActionListener(this);
		money_p.add(btnload);
		String[] rr=new String[3];
		btnload.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e){
			check=0;
			if(ch.equals("year")) {
				for(int i=0;i<x;i++) {
					if(date[i].contains(col_y)) {
						rr[0]=date[i];
						rr[1]=Integer.toString(mon[i]);
						tmoney+=mon[i];
						mod.addRow(rr);
						money_table.setModel(mod);
						lbltotal.setText(Integer.toString(tmoney));
						check+=1;
					}
					else {
					}
				}
			}else if(ch.equals("month")){
				for(int i=0;i<x;i++) {
					if(date[i].contains(col_m)) {
						rr[0]=date[i];
						rr[1]=Integer.toString(mon[i]);
						tmoney+=mon[i];
						mod.addRow(rr);
						money_table.setModel(mod);
						lbltotal.setText(Integer.toString(tmoney));
						check+=1;
					}
					else {
					}
				}
			}
			else if(ch.equals("day")) {
				for(int i=0;i<x;i++) {
					if(date[i].contains(col_d)) {
						rr[0]=date[i];
						rr[1]=Integer.toString(mon[i]);
						tmoney+=mon[i];
						mod.addRow(rr);
						money_table.setModel(mod);
						lbltotal.setText(Integer.toString(tmoney));
						check+=1;
					}
					else {
					}
				}
			}
			if(check==0) {
				mod.setNumRows(0);
				JOptionPane.showMessageDialog(null, "내역이 없습니다.");
			}
		}
		});
	}

	public void menup() {
		menu_p.setBounds(12, 43, 790, 377);
		menu_p.setLayout(null);
		menu_p.setVisible(true);
		/*String[] rows=new String[3];*/
		JLabel lblNewLabel = new JLabel("\uBA54\uB274\uBA85 : ");
		lblNewLabel.setFont(new Font("HY나무L", Font.PLAIN, 15));
		lblNewLabel.setBounds(22, 101, 70, 39);
		menu_p.add(lblNewLabel);
			
		JTextField menuname = new JTextField();
		menuname.setBounds(91, 107, 157, 30);
		menu_p.add(menuname);
		menuname.setColumns(10);
			
		JLabel lblprice = new JLabel("\uAC00   \uACA9 : ");
		lblprice.setFont(new Font("HY나무L", Font.PLAIN, 15));
		lblprice.setBounds(22, 167, 57, 39);
		menu_p.add(lblprice);
			
		JTextField menu_price = new JTextField();
		menu_price.setColumns(10);
		menu_price.setBounds(91, 173, 157, 30);
		menu_p.add(menu_price);
			
		JLabel lblNewLabel_1 = new JLabel("\uBD84    \uB958");
		lblNewLabel_1.setFont(new Font("HY나무L", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(22, 44, 47, 27);
		menu_p.add(lblNewLabel_1);
			
		String[] index= {"HOT coffee","ICE coffee","HOT Beverage","ICE Beverage","Tea","Shake","Add"};
		JComboBox comboBox=new JComboBox(index);
		comboBox.setBounds(91, 43, 157, 30);
		menu_p.add(comboBox);
			
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox j=(JComboBox)e.getSource();
				int in = j.getSelectedIndex();
				if(in==0) {
					kind="hotcoffee";
				}
				else if(in==1) {
					kind="icecoffee";
				}
				else if(in==2) {
					kind="hotbeverage";
				}
				else if(in==3) {
					kind="icebeverage";
				}
				else if(in==4) {
					kind="tea";
				}
				else if(in==4) {
					kind="shake";
				}
				else if(in==5) {
					kind="add";
				}
			}
		});
		
		menu_table.setFillsViewportHeight(true);
		menu_table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane=new JScrollPane(menu_table);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setViewportView(menu_table);
		scrollPane.setBounds(270, 23, 510, 350);
		menu_p.add(scrollPane);
			
		JButton plus = new JButton("등록");
		plus.setFont(new Font("HY나무L", Font.PLAIN, 15));
		plus.setBounds(22, 230, 102, 39);
		menu_p.add(plus);
		plus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {  
				modell.setNumRows(0);
				menu_table.setModel(modell);
				String menu=menuname.getText();
				String m=menu_price.getText();
				String[] rows=new String[5];
				int modify_price=Integer.parseInt(m);
				int a=0;
				String m1=null;
		 
				Connection con=null;
				PreparedStatement ps=null;
				

				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/soyeon?serverTimezone=UTC","root","1234");
					System.out.println("menu 테이블 연결 완료");
					Statement stmt=conn.createStatement();
					String sql="SELECT menu FROM menu WHERE menu='"+menu+"'";
					ResultSet rs=stmt.executeQuery(sql);
				
					while(rs.next()) {
						m1=rs.getString(1);
					}
					if(menu.equals(m1)) {
						JOptionPane.showMessageDialog(null, "이미 있는 메뉴입니다.");
					}
					else {
						String s="INSERT INTO menu (menu, price, kinds, num) VALUES('"+menu+"',"+modify_price+",'"+kind+"',"+a+")";
						int r=stmt.executeUpdate(s);
						rows[0]=menu;
						rows[1]=m;
						rows[2]=kind;
						rows[3]="0";
						modell.addRow(rows);
						menu_table.setModel(modell);
						menuname.setText(null);
						menu_price.setText(null);
						JOptionPane.showMessageDialog(null, "등록완료");
					}
					conn.close();
				}catch(ClassNotFoundException e1) {
					System.out.println("JDBC 드라이버가 존재하지 않습니다"+e1);
					menuname.setText(null);
					menu_price.setText(null);
					JOptionPane.showMessageDialog(null, "등록실패");
				} catch(java.sql.SQLException e1) {
					System.out.println("DB SQL문 오류:"+e1);
					menuname.setText(null);
					menu_price.setText(null);
					JOptionPane.showMessageDialog(null, "등록실패");
				}catch(Exception e1) {
					e1.printStackTrace(); 
				}
			}	
		});
		
		JButton modify = new JButton("수정");
		modify.setFont(new Font("HY나무L", Font.PLAIN, 15));
		modify.setBounds(146, 283, 102, 39);
		menu_p.add(modify);
		modify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modell.setNumRows(0);
				menu_table.setModel(modell);
				String menu=menuname.getText();
				String m=menu_price.getText();
				int modify_price=Integer.parseInt(m);
				String modi_menu = null;
				String[] rows=new String[4];
				String kin=null;
				int num=0;
				int n=0;
				
				Connection con=null;
				PreparedStatement ps=null;

				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/soyeon?serverTimezone=UTC","root","1234");
					System.out.println("menu 테이블 연결 완료");
					Statement stmt=conn.createStatement();
					String sql="SELECT * FROM menu WHERE menu='"+menu+"'";
					ResultSet rs=stmt.executeQuery(sql);
					
					while(rs.next()) {
						modi_menu=rs.getString(1);
						n=rs.getInt(2);
						kin=rs.getString(3);
						num=rs.getInt(4);
					}
					if(menu.equals(modi_menu)) {
						String s="UPDATE menu set price="+modify_price+" where menu='"+menu+"'";
						int r=stmt.executeUpdate(s);
						rows[0]=menu;
						rows[1]=m;
						rows[2]=kin;
						rows[3]=Integer.toString(num);
						modell.addRow(rows);
						menu_table.setModel(modell);
						menuname.setText(null);
						menu_price.setText(null);
						JOptionPane.showMessageDialog(null, "수정완료");
					}
					else {
						JOptionPane.showMessageDialog(null, "없는 메뉴입니다.");
					}
					conn.close();
				}catch(ClassNotFoundException e1) {
					System.out.println("JDBC 드라이버가 존재하지 않습니다"+e1);
					JOptionPane.showMessageDialog(null, "수정실패");
				} catch(java.sql.SQLException e1) {
					System.out.println("DB SQL문 오류:"+e1);
					JOptionPane.showMessageDialog(null, "수정실패");
				}catch(Exception e1) {
					e1.printStackTrace(); 
				}
			}
		});
		
		JButton delete = new JButton("삭제");
		delete.setFont(new Font("HY나무L", Font.PLAIN, 15));
		delete.setBounds(22, 283, 102, 39);
		menu_p.add(delete);
		delete.addActionListener(new ActionListener() { //삭제이벤트
			public void actionPerformed(ActionEvent e) { 
				modell.setNumRows(0);
				menu_table.setModel(modell);
				String del=menuname.getText();
				String del_menu = null;
				Connection con=null;
				PreparedStatement ps=null;

				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/soyeon?serverTimezone=UTC","root","1234");
					System.out.println("menu 테이블 연결 완료");
					Statement stmt=conn.createStatement();
					String sql="delete menu from menu where menu='"+del+"'";
					
					String s="SELECT menu FROM menu WHERE menu='"+del+"'";
					ResultSet r=stmt.executeQuery(s);
					
					while(r.next()) {
						del_menu=r.getString(1);
					}
					if(del.equals(del_menu)) {
						int rs=stmt.executeUpdate(sql);
						JOptionPane.showMessageDialog(null, "삭제완료");
					}
					else {
						JOptionPane.showMessageDialog(null, "없는 메뉴입니다.");
					}
					conn.close();
				}catch(ClassNotFoundException e1) {
					System.out.println("JDBC 드라이버가 존재하지 않습니다"+e1);
					JOptionPane.showMessageDialog(null, "삭제실패");
				} catch(java.sql.SQLException e1) {
					System.out.println("DB SQL문 오류:"+e1);
					JOptionPane.showMessageDialog(null, "삭제실패");
				}catch(Exception e1) {
					e1.printStackTrace(); 
				}
			}
		});
		
		JButton check = new JButton("확인");
		check.setFont(new Font("HY나무L", Font.PLAIN, 15));
		check.setBounds(146, 230, 102, 39);
		menu_p.add(check);
		check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modell.setNumRows(0);
				menu_table.setModel(modell);
				Connection con=null;
				PreparedStatement ps=null;
				
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/soyeon?serverTimezone=UTC","root","1234");
					System.out.println("menu 테이블 연결 완료");
					Statement stmt=conn.createStatement();
					String sql="SELECT * FROM menu";
					ResultSet rs=stmt.executeQuery(sql);
					
					while(rs.next()) {
						String menu=rs.getString(1);
						int price=rs.getInt(2);
						String kinds=rs.getString(3);
						int num=rs.getInt(4);
						
						Object data[]= {menu,price,kinds,num};
						modell.addRow(data);
						menu_table.setModel(modell);
					}
					conn.close();
				}catch(ClassNotFoundException e1) {
					System.out.println("JDBC 드라이버가 존재하지 않습니다"+e1);
					JOptionPane.showMessageDialog(null, "업로드실패");
				} catch(java.sql.SQLException e1) {
					System.out.println("DB SQL문 오류:"+e1);
					JOptionPane.showMessageDialog(null, "업로드실패");
				}catch(Exception e1) {
					e1.printStackTrace(); 
				}
			}
		});
	}
	public void DB_con() {
		Connection con=null;
		PreparedStatement ps=null;
		int t=0;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/soyeon?serverTimezone=UTC","root","1234");
			System.out.println("menu 테이블 연결 완료");
			Statement stmt=conn.createStatement();
			String sql="SELECT * FROM menu";
			ResultSet rs=stmt.executeQuery(sql);
			
			while(rs.next()) {
				String menu=rs.getString(1);
				int price=rs.getInt(2);
				String kinds=rs.getString(3);
				int num=rs.getInt(4);
				sale_m[t]=rs.getString(1);
				sale_[t]=rs.getInt(5);
				t++;
				Object data[]= {menu,kinds,num};
				model.addRow(data);
				inventable.setModel(model);
			}
			conn.close();
		}catch(ClassNotFoundException e1) {
			System.out.println("JDBC 드라이버가 존재하지 않습니다"+e1);
			JOptionPane.showMessageDialog(null, "업로드실패");
		} catch(java.sql.SQLException e1) {
			System.out.println("DB SQL문 오류:"+e1);
			JOptionPane.showMessageDialog(null, "업로드실패");
		}catch(Exception e1) {
			e1.printStackTrace(); 
		}
	}
	public void inventory() {
		inven_p.setBounds(12, 43, 809, 426);
		inven_p.setLayout(null);
		inven_p.setVisible(true);
		
		inventable.setFillsViewportHeight(true);
		inventable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		JScrollPane sc=new JScrollPane(inventable);
		sc.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		sc.setViewportView(inventable);
		sc.setBounds(321, 36, 463, 350);
		DefaultTableCellRenderer dtct=new DefaultTableCellRenderer();
		dtct.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcm=inventable.getColumnModel();
		for(int i=0;i<tcm.getColumnCount();i++) {
			tcm.getColumn(i).setCellRenderer(dtct);
		}
		inven_p.add(sc);
		
		DB_con();
		
		JLabel lblmenu = new JLabel("\uBA54 \uB274 \uBA85");
		lblmenu.setFont(new Font("HY나무L", Font.PLAIN, 14));
		lblmenu.setBounds(30, 52, 68, 27);
		inven_p.add(lblmenu);
		
		JLabel lblmenu_num = new JLabel("\uC218     \uB7C9");
		lblmenu_num.setFont(new Font("HY나무L", Font.PLAIN, 14));
		lblmenu_num.setBounds(30, 103, 68, 27);
		inven_p.add(lblmenu_num);
		
		JTextField menu_name = new JTextField();
		menu_name.setBounds(110, 52, 149, 24);
		inven_p.add(menu_name);
		menu_name.setColumns(10);
		
		JTextField menu_num = new JTextField();
		menu_num.setColumns(10);
		menu_num.setBounds(110, 106, 149, 24);
		inven_p.add(menu_num);
		
		JButton btninput = new JButton("입고");
		btninput.setBounds(168, 160, 91, 23);
		inven_p.add(btninput);
		btninput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setNumRows(0);
				String in_menu=menu_name.getText();
				String in_num =menu_num.getText();
				int m_n=Integer.parseInt(in_num);
				
				String im=null; //메뉴명
				String k=null; //종류
				int count=0; //재고수
				Connection con=null;
				PreparedStatement ps=null;
				
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/soyeon?serverTimezone=UTC","root","1234");
					System.out.println("menu 테이블 연결 완료");
					Statement stmt=conn.createStatement();
					String sql="SELECT * FROM menu WHERE menu='"+in_menu+"'";
					ResultSet rs=stmt.executeQuery(sql);
					
					while(rs.next()) {
						im=rs.getString(1);
						int n=rs.getInt(2);
						k=rs.getString(3);
						count=rs.getInt(4);
					}
					if(in_menu.equals(im)) {
						int total=0;
						total=count+m_n;
						String s="UPDATE menu set num="+total+" where menu='"+in_menu+"'";
						int r=stmt.executeUpdate(s);

						String a="SELECT * FROM menu";
						ResultSet a2=stmt.executeQuery(a);
						
						menu_name.setText(null);
						menu_num.setText(null);
						JOptionPane.showMessageDialog(null, "입고완료");
						
						while(a2.next()) {
							String m=a2.getString(1);
							int n=a2.getInt(2);
							String ki=a2.getString(3);
							int cn=a2.getInt(4);
							
							Object data[]= {m,ki,Integer.toString(cn)};
							model.addRow(data);
							inventable.setModel(model);
						}
						
					}
					else {
						menu_name.setText(null);
						menu_num.setText(null);
						JOptionPane.showMessageDialog(null, "없는 메뉴입니다.");
						DB_con();
					}
					conn.close();
				}catch(ClassNotFoundException e1) {
					menu_name.setText(null);
					menu_num.setText(null);
					System.out.println("JDBC 드라이버가 존재하지 않습니다"+e1);
					JOptionPane.showMessageDialog(null, "입고실패");
					DB_con();
				} catch(java.sql.SQLException e1) {
					menu_name.setText(null);
					menu_num.setText(null);
					System.out.println("DB SQL문 오류:"+e1);
					JOptionPane.showMessageDialog(null, "입고실패");
					DB_con();
				}catch(Exception e1) {
					e1.printStackTrace(); 
				}
			}
		});
		
		JButton btnoutput = new JButton("출고");
		btnoutput.setBounds(40, 160, 91, 23);
		inven_p.add(btnoutput);
		btnoutput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setNumRows(0);
				String in_menu=menu_name.getText();
				String in_num =menu_num.getText();
				int m_n=Integer.parseInt(in_num);
				
				String im=null; //메뉴명
				String k=null; //종류
				int count=0; //재고수
				Connection con=null;
				PreparedStatement ps=null;
				
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/soyeon?serverTimezone=UTC","root","1234");
					System.out.println("menu 테이블 연결 완료");
					Statement stmt=conn.createStatement();
					String sql="SELECT * FROM menu WHERE menu='"+in_menu+"'";
					ResultSet rs=stmt.executeQuery(sql);
					
					while(rs.next()) {
						im=rs.getString(1);
						int n=rs.getInt(2);
						k=rs.getString(3);
						count=rs.getInt(4);
					}
					if(in_menu.equals(im)) {
						int total=0;
						total=count-m_n;
						String s="UPDATE menu set num="+total+" where menu='"+in_menu+"'";
						int r=stmt.executeUpdate(s);

						String a="SELECT * FROM menu";
						ResultSet a2=stmt.executeQuery(a);
						
						menu_name.setText(null);
						menu_num.setText(null);
						JOptionPane.showMessageDialog(null, "출고완료");
						
						while(a2.next()) {
							String m=a2.getString(1);
							int n=a2.getInt(2);
							String ki=a2.getString(3);
							int cn=a2.getInt(4);
							
							Object data[]= {m,ki,Integer.toString(cn)};
							model.addRow(data);
							inventable.setModel(model);
						}
					}
					else {
						menu_name.setText(null);
						menu_num.setText(null);
						JOptionPane.showMessageDialog(null, "없는 메뉴입니다.");
						DB_con();
					}
					conn.close();
				}catch(ClassNotFoundException e1) {
					menu_name.setText(null);
					menu_num.setText(null);
					System.out.println("JDBC 드라이버가 존재하지 않습니다"+e1);
					JOptionPane.showMessageDialog(null, "출고실패");
					DB_con();
				} catch(java.sql.SQLException e1) {
					menu_name.setText(null);
					menu_num.setText(null);
					System.out.println("DB SQL문 오류:"+e1);
					JOptionPane.showMessageDialog(null, "출고실패");
					DB_con();
				}catch(Exception e1) {
					e1.printStackTrace(); 
				}
			}
		});
		
		JPanel best=new JPanel();
		best.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 255), 5), "\uC74C\uB8CC\uD310\uB9E4BEST5 ", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		best.setBounds(30, 220, 250, 166);
		best.setVisible(true);
		inven_p.add(best);
		best.setLayout(null);
		
		JLabel lbl1 = new JLabel("BEST 1 : ");
		lbl1.setFont(new Font("HY나무L", Font.BOLD, 12));
		lbl1.setBounds(33, 30, 63, 15);
		best.add(lbl1);
		
		JLabel lbl1_m = new JLabel();
		lbl1_m.setBounds(108, 30, 135, 15);
		lbl1_m.setFont(new Font("HY나무L", Font.PLAIN, 12));
		best.add(lbl1_m);
		
		JLabel lbl2 = new JLabel("BEST 2 : ");
		lbl2.setFont(new Font("HY나무L", Font.BOLD, 12));
		lbl2.setBounds(33, 58, 63, 15);
		best.add(lbl2);
		
		JLabel lbl2_m = new JLabel();
		lbl2_m.setBounds(108, 58, 135, 15);
		lbl2_m.setFont(new Font("HY나무L", Font.PLAIN, 12));
		best.add(lbl2_m);
		
		JLabel lbl3 = new JLabel("BEST 3 : ");
		lbl3.setFont(new Font("HY나무L", Font.BOLD, 12));
		lbl3.setBounds(33, 83, 63, 15);
		best.add(lbl3);
		
		JLabel lbl3_m = new JLabel();
		lbl3_m.setBounds(108, 83, 135, 15);
		lbl3_m.setFont(new Font("HY나무L", Font.PLAIN, 12));
		best.add(lbl3_m);
		
		JLabel lbl4 = new JLabel("BEST 4 : ");
		lbl4.setFont(new Font("HY나무L", Font.BOLD, 12));
		lbl4.setBounds(33, 108, 63, 15);
		best.add(lbl4);
		
		JLabel lbl4_m = new JLabel();
		lbl4_m.setBounds(108, 108, 135, 15);
		lbl4_m.setFont(new Font("HY나무L", Font.PLAIN, 12));
		best.add(lbl4_m);
		
		JLabel lbl5 = new JLabel("BEST 5 : ");
		lbl5.setFont(new Font("HY나무L", Font.BOLD, 12));
		lbl5.setBounds(33, 133, 63, 15);
		best.add(lbl5);
		
		JLabel lbl5_m = new JLabel();
		lbl5_m.setBounds(108, 133, 135, 15);
		lbl5_m.setFont(new Font("HY나무L", Font.PLAIN, 12));
		best.add(lbl5_m);
		
		for(int i=0; i<sale_.length;i++) {
			for(int j=i+1;j<sale_.length;j++) {
				if(sale_[i]<sale_[j]) {
					int temp=sale_[i];
					String tem=sale_m[i];
					sale_[i]=sale_[j];
					sale_m[i]=sale_m[j];
					sale_[j]=temp;
					sale_m[j]=tem;
				}
			}
		}
		
		lbl1_m.setText(sale_m[0]);
		lbl2_m.setText(sale_m[1]);
		lbl3_m.setText(sale_m[2]);
		lbl4_m.setText(sale_m[3]);
		lbl5_m.setText(sale_m[4]);
	}
		
	manager() {
		manager.setResizable(false);
		manager.setTitle("이디야 pos기_관리자모드");
		manager.getContentPane().setLayout(cards);
		manager.setResizable(false);
		manager.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		manager.setBounds(100, 100, 847, 516);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		manager.setContentPane(contentPane);
		contentPane.setLayout(null);
		manager.setVisible(true);
			
		JButton btnmoney = new JButton("\uB9E4\uCD9C\uD655\uC778");
		btnmoney.setBounds(12, 10, 91, 23);
		contentPane.add(btnmoney);
		
		btnmoney.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getContentPane().removeAll();
				contentPane.add(money_p);
				inven_p.setVisible(false);
				menu_p.setVisible(false);
				money_p.setVisible(true);
				money();
			}
		});
		
		JButton btnmenu = new JButton("\uBA54\uB274\uAD00\uB9AC");
		btnmenu.setBounds(115, 10, 91, 23);
		contentPane.add(btnmenu);
		
		btnmenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getContentPane().removeAll();
				contentPane.add(menu_p);
				money_p.setVisible(false);
				inven_p.setVisible(false);
				menu_p.setVisible(true);
				menup();
			}
		});
		
		JButton btninventory = new JButton("\uC7AC\uACE0\uD655\uC778");
		btninventory.setBounds(218, 10, 91, 23);
		contentPane.add(btninventory);
		
		btninventory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getContentPane().removeAll();
				contentPane.add(inven_p);
				money_p.setVisible(false);
				inven_p.setVisible(true);
				menu_p.setVisible(false);
				inventory();
			}
		});
		
		JButton btnexit = new JButton("\uC885 \uB8CC");
		btnexit.setBounds(700, 10, 91, 23);
		contentPane.add(btnexit);
		
		btnexit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new exitt();
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
