import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Vector;

import javax.help.CSH;
import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.time.SimpleTimePeriod;

import com.orsoncharts.Chart3DHints.Key;

class CpuFrame extends JFrame{
	private Timer timer;
	boolean input_boolean,technique_boolean;
	Boolean[] table_boolean;
	DefaultTableModel model;
	String test;
	Task room[];
	JPanel p1=new JPanel();
	JPanel p2=new JPanel();
	JPanel p3=new JPanel();
	JPanel p4=new JPanel();
	JLabel lb_result=new JLabel("스케쥴링 결과:");
	JLabel lb_result1=new JLabel();
	JLabel lb_avg_blocked_time=new JLabel("평균 대기 시간:");
	JTextField tf_avg_blocked_time=new JTextField();
	JButton bt_avg_blocked_time=new JButton("자세히");
	JLabel lb_return_time=new JLabel("평균 반환 시간:");
	JTextField tf_return_time=new JTextField();
	JButton bt_return_time=new JButton("자세히");
	TaskSeriesCollection dataset = new TaskSeriesCollection();
	TaskSeries unavailable = new TaskSeries("Used Cpu Time");   
	JFreeChart chart = ChartFactory.createGanttChart("", "Process name", "Time", dataset, true, true, false);
	JLabel lb_cpu_Create ;  //프로세스 생성방법 라벨
	JLabel lb_process_count; //프로세스 수 라벨
	JRadioButton rb_Cpu_auto;  //자동 버튼
	JRadioButton rb_Cpu_passive;  //수동 버튼
	ButtonGroup bg_Cpu_choice; 
	JPanel pl_Interface;
	JLabel lb_explanation=new JLabel();
	JLabel lb_explanation1=new JLabel();
	JTextField    tf_input_process_count=new JTextField("      입력란          ");
	JButton bt_input_process_count;
	JTable tb_process=new JTable();;
	JScrollPane pl_table;
	JLabel lb_technique=new JLabel("스케쥴링 기법");
	JCheckBox rb_fcfs=new JCheckBox("First-Come.First-Serviced");
	JCheckBox rb_HRN=new JCheckBox("Highest Response Radio Next");
	JCheckBox rb_SJF=new JCheckBox("Shortest Job First");
	JCheckBox rb_SRT=new JCheckBox("Shortest Remaining Time");
	JCheckBox rb_Priority=new JCheckBox("Priority");
	JCheckBox rb_Round_Robin=new JCheckBox("Round-Robin");
	JCheckBox rb_null=new JCheckBox();
	ButtonGroup bg_rb=new ButtonGroup();
	JCheckBox rb_list[]=new JCheckBox[6];
	JLabel lb_comparison=new JLabel("성능비교");
	JLabel lb_text=new JLabel("기법");
	JLabel lb_text1=new JLabel("평균대기시간");
	JLabel lb_text2=new JLabel("평균반환시간");
	JLabel lb_FCFS=new JLabel("FCFS:");
	JLabel lb_quantum_count=new JLabel("퀀텀 :");
	JLabel lb_FCFS_Avg_blocked_time=new JLabel("0");
	JLabel lb_FCFS_Avg_return_time=new JLabel("0");
	JLabel lb_HRN=new JLabel("HRN:");
	JLabel lb_HRN_Avg_blocked_time=new JLabel("0");
	JLabel lb_HRN_Avg_return_time=new JLabel("0");
	JLabel lb_SJF=new JLabel("SJF:");
	JLabel lb_SJF_Avg_blocked_time=new JLabel("0");
	JLabel lb_SJF_Avg_return_time=new JLabel("0");
	JLabel lb_SRT=new JLabel("SRT:");
	JLabel lb_SRT_Avg_blocked_time=new JLabel("0");
	JLabel lb_SRT_Avg_return_time=new JLabel("0");
	JLabel lb_Priority=new JLabel("Priority:");
	JLabel lb_Priority_Avg_blocked_time=new JLabel("0");
	JLabel lb_Priority_Avg_return_time=new JLabel("0");
	JLabel lb_RR=new JLabel("RR:");
	JLabel lb_RR_Avg_blocked_time=new JLabel("0");
	JLabel lb_RR_Avg_return_time=new JLabel("0");
	JButton bt_start=new JButton("실행");
	JButton bt_reset=new JButton("재실행");
	JButton bt_exit=new JButton("나가기");
	JComboBox cb_quantum ;
	static int n;
	static String S[][];
	DefaultTableModel model1=new DefaultTableModel();
	Vector col=new Vector();
	Vector list_qt;
	String result_1="";
	String result_2="";
	JMenuBar menuBar = new JMenuBar();
	JMenu helpMenu = new JMenu();
	JMenuItem helpItem = new JMenuItem();
	HelpSet helpSet;
	HelpBroker helpBroker;

	void reset(){
		this.setVisible(false);
		new CpuFrame();
	}

	@SuppressWarnings("serial")
	class CpuCreatemethod extends JPanel implements FocusListener,ActionListener,ItemListener,MouseListener,KeyListener{


		protected JPanel p1=new JPanel();
		protected JPanel p2=new JPanel();
		JLabel lb_no=new JLabel("No:프로세스의 Pid 값");
		JLabel lb_prcess=new JLabel("프로세스:프로세스의 이름");
		JLabel lb_Priority=new JLabel("우선 순위:프로세스의 가장 중요한 일을 먼저 실행 할 수 있도록 결정");
		JLabel lb_Priority_time_principle=new JLabel(":Priority기법일때만 제공되고 우선순위는 낮은숫자부터 높은우선순위");
		JLabel lb_arrival_time=new JLabel("도착 시간:프로세스의 도착지점");
		JLabel lb_service_time=new JLabel("서비스 시간:프로세스가 일하는 시간");
		JLabel lb_service_time_principle=new JLabel(":서비스 시간은 0을 줄수 없다.");

		String str=tf_input_process_count.getText();
		Vector data=new Vector();
		Vector col=new Vector();

		boolean change;

		public CpuCreatemethod() {
			// TODO Auto-generated constructor stub
			//      this.setBorder(new LineBorder(Color.BLACK, 3));
			this.setLayout(new GridLayout(1, 2,5,5));
			init();  //인터페이스 구현부
			start(); // 이벤트 처리 부

		}

		@SuppressWarnings("unchecked")
		private void init() {
			// TODO Auto-generated method stub

			col.add("No");
			col.add("프로세스");
			col.add("우선순위");
			col.add("도착시간");
			col.add("서비스시간");

			model1=new DefaultTableModel(col, 0);
			tb_process.getTableHeader().setReorderingAllowed(false);
			tb_process.setModel(model1);


			p1.setLayout(new GridLayout(1,2,5,5));

			pl_Interface=new JPanel();
			pl_Interface.setLayout(null);


			bt_input_process_count=new JButton("입력");


			lb_cpu_Create=new JLabel("프로세스 생성방법");
			frameAdd(lb_cpu_Create, 10, -40, 110, 110);
			lb_cpu_Create.setForeground(Color.BLUE);

			rb_Cpu_auto=new JRadioButton("자동");
			frameAdd(rb_Cpu_auto, 20, 20, 60, 60);

			rb_Cpu_passive=new JRadioButton("수동");
			frameAdd(rb_Cpu_passive, 90, 20, 60, 60);

			bg_Cpu_choice=new ButtonGroup();
			bg_Cpu_choice.add(rb_Cpu_auto);
			bg_Cpu_choice.add(rb_Cpu_passive);

			pl_Interface.add(lb_cpu_Create);
			pl_Interface.add(rb_Cpu_auto);
			pl_Interface.add(rb_Cpu_passive);

			frameAdd(lb_no, 5, 150, 160, 30);
			pl_Interface.add(lb_no);

			frameAdd(lb_prcess, 5, 200, 160, 30);
			pl_Interface.add(lb_prcess);

			frameAdd(lb_Priority, 5, 250, 450, 30);
			pl_Interface.add(lb_Priority);

			lb_Priority_time_principle.setForeground(Color.RED);
			frameAdd(lb_Priority_time_principle,60, 270, 550, 30);
			pl_Interface.add(lb_Priority_time_principle);

			frameAdd(lb_arrival_time, 5, 300, 210, 30);
			pl_Interface.add(lb_arrival_time);

			frameAdd(lb_service_time, 5, 350, 210, 30);
			pl_Interface.add(lb_service_time);



			lb_service_time_principle.setForeground(Color.RED);
			frameAdd(lb_service_time_principle,70, 370, 550, 30);
			pl_Interface.add(lb_service_time_principle);

			pl_table = new JScrollPane(tb_process);   
			p1.add(pl_Interface);
			p1.add(pl_table);
			p2.add(p1);
			p2.setBorder(new LineBorder(Color.BLACK,3));
			this.add(p2);

		}
		private void start() {
			// TODO Auto-generated method stub
			tf_input_process_count.addFocusListener(this);
			bt_input_process_count.addActionListener(this);
			tb_process.addMouseListener(this);
			tb_process.addKeyListener(this);
			rb_Cpu_auto.addItemListener(this);
			rb_Cpu_passive.addItemListener(this);
			tb_process.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		private void frameAdd(JComponent c, int x, int y, int w, int h) {
			// TODO Auto-generated method stub
			c.setBounds(x, y, w, h);
			this.add(c);
		}
		@Override
		public void focusGained(FocusEvent e) { //입력문이 사라짐
			// TODO Auto-generated method stub
			if(e.getSource()==tf_input_process_count){
				if(tf_input_process_count.getText().equals(str)){
					System.out.println("확인");
					tf_input_process_count.setText(null);
					tf_input_process_count.requestFocus();
				}
				else{
					tf_input_process_count.setForeground(Color.BLACK);
				}
			}
		}
		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==tf_input_process_count){
				int x=tf_input_process_count.getText().trim().length();
				if(x==0){
					tf_input_process_count.setText("      입력란          ");
					tf_input_process_count.setForeground(Color.GRAY);
				}
			}
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==bt_input_process_count){
				unavailable.removeAll();

				if(rb_Cpu_passive.isSelected()){
					try {

						n=Integer.parseInt(tf_input_process_count.getText());
						if(7<=n){
							int s=JOptionPane.showConfirmDialog(this, n+"개 프로세스 입력시 그래프가 복잡해집니다 그래도 실행하시겠습니까?","확인",
									JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
							System.out.println("s"+s);
							if(s==0){
								input_boolean=true;
							}
							else{
								input_boolean=false;

							}
						}
						else  if(0>=n){
							JOptionPane.showMessageDialog(null, "마이너스 및 0개 값은 입력 못합니다.","Message",JOptionPane.ERROR_MESSAGE);
							input_boolean=false;
						}
						else  if(1<=n&&n<=6){
							input_boolean=true;
						}

						System.out.println("input_boolean:"+input_boolean);
						if(input_boolean==true){
							rb_Round_Robin.setEnabled(true);
							rb_Priority.setEnabled(true); 
							rb_SRT.setEnabled(true);
							rb_SJF.setEnabled(true); 
							rb_HRN.setEnabled(true);  
							rb_fcfs.setEnabled(true);
							model=new DefaultTableModel(col, n);

						}
						else if(input_boolean==false){
							rb_Round_Robin.setEnabled(false);
							rb_Priority.setEnabled(false); 
							rb_SRT.setEnabled(false);
							rb_SJF.setEnabled(false); 
							rb_HRN.setEnabled(false);  
							rb_fcfs.setEnabled(false);
							bt_start.setEnabled(false);

							model=new DefaultTableModel(col, 0);
						}


						if(technique_boolean==true){
							unavailable.removeAll();
							tb_process.setModel(model);
							for(int i=0;i<n;i++){

								tb_process.setValueAt(i, i,0);
								tb_process.setValueAt("P"+(i+1), i,1);
							}
							model1=(DefaultTableModel)tb_process.getModel();

						}
						tf_avg_blocked_time.setText(null);
						tf_return_time.setText(null);
						lb_FCFS_Avg_blocked_time.setText("0");
						lb_FCFS_Avg_return_time.setText("0");
						lb_HRN_Avg_blocked_time.setText("0");
						lb_HRN_Avg_return_time.setText("0");
						lb_SJF_Avg_blocked_time.setText("0");
						lb_SJF_Avg_return_time.setText("0");
						lb_SRT_Avg_blocked_time.setText("0");
						lb_SRT_Avg_return_time.setText("0");
						lb_Priority_Avg_blocked_time.setText("0");
						lb_Priority_Avg_return_time.setText("0");
						lb_RR_Avg_blocked_time.setText("0");
						lb_RR_Avg_return_time.setText("0");
						result_1="";
						result_2="";


					}catch (NumberFormatException e1) {
						// TODO: handle exception

						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "문자열을 입력하셨습니다.","Message",JOptionPane.ERROR_MESSAGE);
						rb_Round_Robin.setEnabled(false);
						rb_Priority.setEnabled(false); 
						rb_SRT.setEnabled(false);
						rb_SJF.setEnabled(false); 
						rb_HRN.setEnabled(false);  
						rb_fcfs.setEnabled(false);

					}catch(ArrayIndexOutOfBoundsException e2){
						//						e2.printStackTrace();
						rb_Round_Robin.setEnabled(false);
						rb_Priority.setEnabled(false); 
						rb_SRT.setEnabled(false);
						rb_SJF.setEnabled(false); 
						rb_HRN.setEnabled(false);  
						rb_fcfs.setEnabled(false);

					}

					System.out.println("수동버튼클릭 후 : "+input_boolean);
				}


				if(rb_Cpu_auto.isSelected()){
					try {
						n=Integer.parseInt(tf_input_process_count.getText());
						if(7<=n){
							System.out.println("입력시완료");
							int s=JOptionPane.showConfirmDialog(this, n+"개 프로세스 입력시 그래프가 복잡해집니다 그래도 실행하시겠습니까?","확인",
									JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
							System.out.println("s"+s);
							if(s==0){
								input_boolean=true;
							}
							else{
								input_boolean=false;
							}
						}
						else  if(0>=n){
							JOptionPane.showMessageDialog(null, "마이너스 및 0개 값은 입력 못합니다.","Message",JOptionPane.ERROR_MESSAGE);
							input_boolean=false;
						}
						else  if(1<=n&&n<=6){
							input_boolean=true;
						}
						if(input_boolean==true){
							unavailable.removeAll();
							rb_Round_Robin.setEnabled(true);
							rb_Priority.setEnabled(true); 
							rb_SRT.setEnabled(true);
							rb_SJF.setEnabled(true); 
							rb_HRN.setEnabled(true);  
							rb_fcfs.setEnabled(true);

							model=new DefaultTableModel(col, n){
								@Override
								public boolean isCellEditable(int row, int column) {
									// TODO Auto-generated method stub
									return false;
								}
							};
							tb_process.setModel(model);


							S=new String[n][tb_process.getColumnCount()];
							//                  S1=new String[n][tb_process.getColumnCount()];
							for(int i=0;i<n;i++){
								S[i][0]=""+(i+1);
								S[i][1]="P"+(i+1);
								tb_process.setValueAt(S[i][0], i,0);
								tb_process.setValueAt(S[i][1], i,1);
							}

							if(rb_Priority.isSelected()==true){
								for(int i=0;i<n;i++){
									S[i][2]=""+(int)(Math.random()*n+1);
									tb_process.setValueAt(S[i][2], i,2);
								}   
							}

							for(int i=0;i<n;i++){
								S[i][3]=""+(int)(Math.random()*20);
								tb_process.setValueAt(S[i][3], i,3);
							}

							for(int i=0;i<n;i++){
								S[i][4]=""+(int)(Math.random()*20+1);
								tb_process.setValueAt(S[i][4], i,4);

							}
							model1=(DefaultTableModel)tb_process.getModel();
						}
						else if(input_boolean==false){
							rb_Round_Robin.setEnabled(false);
							rb_Priority.setEnabled(false); 
							rb_SRT.setEnabled(false);
							rb_SJF.setEnabled(false); 
							rb_HRN.setEnabled(false);  
							rb_fcfs.setEnabled(false);
							bt_start.setEnabled(false);
						}

						tf_avg_blocked_time.setText(null);
						tf_return_time.setText(null);
						lb_FCFS_Avg_blocked_time.setText("0");
						lb_FCFS_Avg_return_time.setText("0");
						lb_HRN_Avg_blocked_time.setText("0");
						lb_HRN_Avg_return_time.setText("0");
						lb_SJF_Avg_blocked_time.setText("0");
						lb_SJF_Avg_return_time.setText("0");
						lb_SRT_Avg_blocked_time.setText("0");
						lb_SRT_Avg_return_time.setText("0");
						lb_Priority_Avg_blocked_time.setText("0");
						lb_Priority_Avg_return_time.setText("0");
						lb_RR_Avg_blocked_time.setText("0");
						lb_RR_Avg_return_time.setText("0");
						result_1="";
						result_2="";
					} catch (Exception e2) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(null, "문자열을 입력하셨습니다.","Message",JOptionPane.ERROR_MESSAGE);  

						rb_Round_Robin.setEnabled(false);
						rb_Priority.setEnabled(false); 
						rb_SRT.setEnabled(false);
						rb_SJF.setEnabled(false); 
						rb_HRN.setEnabled(false);  
						rb_fcfs.setEnabled(false);

						System.out.println("오류");
					}  
					input_boolean=true;

					System.out.println("자동버튼클릭 후 : "+input_boolean);
				}


			}
			tf_input_process_count.setText("      입력란          ");

		}
		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub

			//         System.out.println("선택됨");
			model1=new DefaultTableModel(col, 0);
			tb_process.setModel(model1);
			lb_process_count=new JLabel("프로세스 수(1~6개가 그래프는 깔끔해서 보기좋음)");
			frameAdd(lb_process_count, 10, 50, 300, 80);
			tf_input_process_count.setForeground(Color.GRAY);
			frameAdd(tf_input_process_count, 30, 110, 80, 20);
			frameAdd(bt_input_process_count, 120, 110, 80, 20);      
			pl_Interface.add(lb_process_count);
			pl_Interface.add(tf_input_process_count);
			pl_Interface.add(bt_input_process_count);
			rb_Round_Robin.setEnabled(false);
			rb_Priority.setEnabled(false); 
			rb_SRT.setEnabled(false);
			rb_SJF.setEnabled(false); 
			rb_HRN.setEnabled(false);  
			rb_fcfs.setEnabled(false);
			rb_null.setSelected(true);
			input_boolean=false;
			technique_boolean=false;
			bt_start.setEnabled(false);

			for(int i=0;i<rb_list.length;i++){
				rb_list[i].setForeground(Color.BLACK);
			}
			lb_result1.setText("");
			lb_explanation.setText("");
			lb_explanation1.setText("");
			repaint();
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if(rb_Cpu_auto.isSelected()){
				System.out.println("클릭함");
				JOptionPane.showMessageDialog(null, "프로세스 생성방법이 자동 선택시 값을 변경하지 못합니다. 직접 값을 넣고 싶으면 수동으로 선택해주세요","Message",JOptionPane.ERROR_MESSAGE);   
			}
			if(rb_Cpu_passive.isSelected()&&rb_Priority.isSelected()==false){
				@SuppressWarnings("unused")
				int row = tb_process.getSelectedRow();
				int column = tb_process.getSelectedColumn();
				if(column==2){
					JOptionPane.showMessageDialog(null, "우선순위는 Priority기법에서만 제공됩니다","Message",JOptionPane.ERROR_MESSAGE); 
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			if(rb_Cpu_passive.isSelected()&&rb_Priority.isSelected()==false){
				@SuppressWarnings("unused")
				int row = tb_process.getSelectedRow();
				int column = tb_process.getSelectedColumn();
				if(column==2){
					JOptionPane.showMessageDialog(null, "우선순위는 Priority기법에서만 제공됩니다","Message",JOptionPane.ERROR_MESSAGE);
					requestFocus();
				}
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			@SuppressWarnings("unused")
			int a=0;
			int row = tb_process.getSelectedRow();
			int column = tb_process.getSelectedColumn();
			int code = e.getKeyCode();

			if(rb_Cpu_passive.isSelected()&&rb_Priority.isSelected()==false){
				//				System.out.println("포커스");
				if(column==2){
					JOptionPane.showMessageDialog(null, "우선순위는 Priority기법에서만 제공됩니다","Message",JOptionPane.ERROR_MESSAGE);
					requestFocus();
				}

			}



		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			int row = tb_process.getSelectedRow();
			int column = tb_process.getSelectedColumn();
			int code = e.getKeyCode();
			if(code==KeyEvent.VK_ENTER){
				System.out.println("엔터확인");
				System.out.println("row:"+row);
				System.out.println("column:"+column);
				//					System.out.println("a:"+a);
				for(int i=0;i<n;i++){
					try{


						if(Integer.parseInt(String.valueOf((tb_process.getValueAt(i, 3))))<0){

							JOptionPane.showMessageDialog(null, "도착시간은  마이너스값이 제공하지않습니다","Message",JOptionPane.ERROR_MESSAGE);
							tb_process.setValueAt(null, i, 3);

						}


					}catch (NumberFormatException e5) {
						// TODO: handle exception

					}
				}
				for(int i=0;i<n;i++){
					try{


						if(Integer.parseInt((String) (tb_process.getValueAt(i, 4)))<=0){
							JOptionPane.showMessageDialog(null, "서비스시간은 0 및 마이너스값은 제공하지않습니다","Message",JOptionPane.ERROR_MESSAGE);
							tb_process.setValueAt(null, i, 4);

						}


					}catch (NumberFormatException e5) {
						// TODO: handle exception

					}
				}
			}


		}

		@Override
		public void keyTyped(KeyEvent e) {
		}
	}


	@SuppressWarnings("serial")
	public class CpuResult extends JPanel  {


		JPanel p1=new JPanel();
		JPanel p3=new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel p2=new JPanel();
		public CpuResult() {
			// TODO Auto-generated constructor stub
			init();
			start();

		}
		private void init() {
			// TODO Auto-generated method stub
			lb_result.setForeground(Color.BLUE);
			setLayout(new GridLayout(2, 1,10,10));
			p1.setBorder(new LineBorder(Color.BLACK,3));
			p2.setBorder(new LineBorder(Color.BLACK,3));
			dataset.add(unavailable);
			ChartPanel chartPanel = new ChartPanel(chart);
			p1.setLayout(null);
			p2.setLayout(null);
			p3.add(lb_result);
			p3.add(lb_result1);
			p1.setLayout(new BorderLayout());
			p1.add(p3,BorderLayout.NORTH);
			p1.add(chartPanel, BorderLayout.CENTER);
			p2.add(lb_avg_blocked_time);
			lb_avg_blocked_time.setBounds(20, 70, 100, 30);
			p2.add(bt_avg_blocked_time);
			bt_avg_blocked_time.setToolTipText("프로세스의 대기시간을 자세히 볼 수 있습니다.");
			bt_avg_blocked_time.setBounds(300, 75, 80, 20);
			p2.add(tf_avg_blocked_time);
			tf_avg_blocked_time.setBounds(30, 100, 640, 50);
			p2.add(lb_return_time);
			lb_return_time.setBounds(20, 200, 100, 30);
			p2.add(bt_return_time);
			bt_return_time.setBounds(300, 205, 80, 20);
			bt_return_time.setToolTipText("프로세스의 반환시간을 자세히 볼 수 있습니다.");
			p2.add(tf_return_time);
			tf_return_time.setBounds(30, 230, 640, 50);
			add(p1);
			add(p2);
		}
		private void start() {
			// TODO Auto-generated method stub
			tf_avg_blocked_time.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mouseClicked(e);
					JOptionPane.showMessageDialog(null, "결과 값은 수정 할 수 없습니다.","확인",JOptionPane.WARNING_MESSAGE);
					requestFocus();

				}
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mouseReleased(e);
					JOptionPane.showMessageDialog(null, "결과 값은 수정 할 수 없습니다.","확인",JOptionPane.WARNING_MESSAGE);
					requestFocus();
				}
			});;
			tf_return_time.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mouseClicked(e);
					JOptionPane.showMessageDialog(null, "결과 값은 수정 할 수 없습니다.","확인",JOptionPane.WARNING_MESSAGE);
					requestFocus();

				}
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mouseReleased(e);
					JOptionPane.showMessageDialog(null, "결과 값은 수정 할 수 없습니다.","확인",JOptionPane.WARNING_MESSAGE);
					requestFocus();
				}
			});;

			bt_avg_blocked_time.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.out.println("클릭");
					new Avgtime();

				}
			});
			bt_return_time.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.out.println("클릭");
					new Avgtime1();

				}
			});
		}

	}

	class Avgtime extends JDialog  {
		private JTextArea result;
		public Avgtime() {
			// TODO Auto-generated constructor stub
			Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
			System.out.println(result_1);

			result=new JTextArea(result_1);
			this.add(result);
			result.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					// TODO Auto-generated method stub
					super.mouseClicked(arg0);
					JOptionPane.showMessageDialog(null, "결과 값은 수정 할 수 없습니다.","확인",JOptionPane.WARNING_MESSAGE);
					requestFocus();
				}
				@Override
				public void mouseReleased(MouseEvent arg0) {
					// TODO Auto-generated method stub
					super.mouseReleased(arg0);
					JOptionPane.showMessageDialog(null, "결과 값은 수정 할 수 없습니다.","확인",JOptionPane.WARNING_MESSAGE);
					requestFocus();
				}
			});
			this.setSize(dim.width/5,dim.height/5); 
			this.setLocationRelativeTo(null);
			setModal(true);
			setVisible(true);
		}


	}
	class Avgtime1 extends JDialog {
		private JTextArea result;
		public Avgtime1() {
			// TODO Auto-generated constructor stub
			Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
			System.out.println(result_2);

			result=new JTextArea(result_2);
			result.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					// TODO Auto-generated method stub
					super.mouseClicked(arg0);
					JOptionPane.showMessageDialog(null, "결과 값은 수정 할 수 없습니다.","확인",JOptionPane.WARNING_MESSAGE);
					requestFocus();
				}
				@Override
				public void mouseReleased(MouseEvent arg0) {
					// TODO Auto-generated method stub
					super.mouseReleased(arg0);
					JOptionPane.showMessageDialog(null, "결과 값은 수정 할 수 없습니다.","확인",JOptionPane.WARNING_MESSAGE);
					requestFocus();
				}
			});
			this.add(result);
			this.setSize(dim.width/5,dim.height/5); 
			this.setLocationRelativeTo(null);
			setModal(true);
			setVisible(true);
		}

	}
	@SuppressWarnings("serial")
	class Cpu_schedule_technique extends JPanel{

		JPanel p1=new JPanel();

		String explanation_technique[]={"프로세스는 준비 큐에 도착한  ",
				"가장 높은 응답률을 가진 프로세스를 우선",
				"잔여 실행 시간이 가장 짧은 프로세스를 ",
				"실행시간이 가장 짧은 작업을 우선적으로 ",
				"프로세스의 우선순위를 스케줄링에 활용함",
				"FCFS기법으로 프로세스를 처리하되"
		};
		String explanation_technique2[]={"순서대로 CPU를 할당받음",
				"적으로 처리",
				"우선적으로 스케줄링",
				"스케줄링",
				"",
				"프로세스에게 동일한 CPU 시간을 할당"
		};




		public Cpu_schedule_technique() {
			// TODO Auto-generated constructor stub
			//      this.setBorder(new LineBorder(Color.BLUE));
			this.setLayout(new GridLayout(1, 2,5,5));
			init();

		}
		private void init() {
			// TODO Auto-generated method stub
			add(new technique());
			add(new comparison());
		}

		class technique extends JPanel implements ItemListener{
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public technique() {
				// TODO Auto-generated constructor stub
				init();
				start();

			}
			private void init() {
				// TODO Auto-generated method stub
				this.setLayout(null);
				this.setBorder(new LineBorder(Color.BLACK, 3));
				this.add(lb_technique);
				lb_technique.setForeground(Color.BLUE);
				lb_technique.setBounds(10, 10, 90, 20);

				lb_explanation.setBounds(10, 200, 300, 20);
				lb_explanation1.setBounds(10,220,300,20);
				this.add(lb_explanation);
				this.add(lb_explanation1);
				this.add(rb_fcfs);
				rb_fcfs.setBounds(250, 90, 200, 20);
				rb_fcfs.setEnabled(false);
				this.add(rb_HRN);
				rb_HRN.setBounds(250, 140, 200, 20);
				rb_HRN.setEnabled(false);  
				this.add(rb_SJF);
				rb_SJF.setBounds(250, 190, 200, 20);
				rb_SJF.setEnabled(false);  
				this.add(rb_SRT);
				rb_SRT.setBounds(250, 240, 200, 20);
				rb_SRT.setEnabled(false);  

				this.add(rb_Priority);
				rb_Priority.setBounds(250, 290, 200, 20);
				rb_Priority.setEnabled(false);  

				this.add(rb_Round_Robin);
				rb_Round_Robin.setBounds(250, 340, 200, 20);
				rb_Round_Robin.setEnabled(false);

				list_qt=new Vector();
				for(int i=1;i<=10;i++){
					list_qt.add(i);
				}
				cb_quantum = new JComboBox(list_qt);
				cb_quantum.setBounds(370, 380, 50, 20);
				lb_quantum_count.setBounds(310,380,70,20);

				//				repaint();

				bg_rb.add(rb_fcfs);
				bg_rb.add(rb_HRN);
				bg_rb.add(rb_SJF);
				bg_rb.add(rb_SRT);
				bg_rb.add(rb_Priority);
				bg_rb.add(rb_Round_Robin);
				bg_rb.add(rb_null);

				rb_list[0]=rb_fcfs;
				rb_list[1]=rb_HRN;
				rb_list[2]=rb_SJF;
				rb_list[3]=rb_SRT;
				rb_list[4]=rb_Priority;
				rb_list[5]=rb_Round_Robin;
			}
			private void start() {
				// TODO Auto-generated method stub
				for(int i=0;i<rb_list.length;i++){
					rb_list[i].addItemListener(this);

				}

				rb_fcfs.addItemListener(this);
				rb_HRN.addItemListener(this);
				rb_SJF.addItemListener(this);
				rb_SRT.addItemListener(this);
				rb_Round_Robin.addItemListener(this);
				rb_Priority.addItemListener(this);
			}
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				Object ob1=e.getSource();
				//            S1=new String[n][tb_process.getColumnCount()];

				if(rb_Cpu_auto.isSelected()==true&&rb_Priority.isSelected()==true){
					for(int i=0;i<n;i++){
						S[i][2]=""+(int)(Math.random()*n+1);
						tb_process.setValueAt(S[i][2], i,2);
					}
				}
				else if(rb_Cpu_auto.isSelected()==true&&rb_Priority.isSelected()==false){
					for(int i=0;i<n;i++){
						S[i][2]="";
						tb_process.setValueAt(S[i][2], i,2);
					}
					for(int i=0;i<n;i++){
						tb_process.setValueAt(S[i][3], i, 3);
					}
				}



				if(rb_Round_Robin.isSelected()==true){
					//					System.out.println("들어옴");
					this.add(cb_quantum);
					this.add(lb_quantum_count);
					updateUI();
				}
				else{
					this.remove(cb_quantum);
					this.remove(lb_quantum_count);
					updateUI();
				}
				if(rb_Cpu_passive.isSelected()){
					for(int i=0;i<rb_list.length;i++){
						if(rb_list[i].isSelected()==true){
							technique_boolean=true;
						}
					}



					if(technique_boolean==true){
						//						System.out.println("확인");
						tb_process.setModel(model);
						model1=(DefaultTableModel)tb_process.getModel();
						bt_start.setEnabled(true);
						if(rb_Priority.isSelected()==false){
							for(int i=0;i<n;i++){
								tb_process.setValueAt(null, i, 2);
							}
						}
						for(int i=0;i<n;i++){
							tb_process.setValueAt(i+1, i, 0);
							tb_process.setValueAt("P"+(i+1), i, 1);
						}
					}
				}
				if(rb_Cpu_auto.isSelected()){
					bt_start.setEnabled(true);
				}

				for(int i=0;i<rb_list.length;i++){
					if(ob1==rb_list[i]){
						rb_list[i].setForeground(Color.RED);
						lb_explanation.setText(explanation_technique[i]);
						lb_explanation1.setText(explanation_technique2[i]);
						test=rb_list[i].getActionCommand();
						//               System.out.println(""+rb_list[i]+""+rb_list[i].isSelected());
					}
					else{
						rb_list[i].setForeground(Color.BLACK);
					}
				}
			}
		}

		class comparison extends JPanel implements ActionListener{
			int k ;

			public comparison() {
				// TODO Auto-generated constructor stub
				this.setLayout(null);
				//         this.setBackground(Color.BLACK);
				this.add(lb_comparison);
				lb_comparison.setForeground(Color.BLUE);
				lb_comparison.setBounds(10, 5, 60, 30);
				this.add(lb_text);
				lb_text.setBounds(30,75, 30, 30);         
				this.add(lb_text1);
				lb_text1.setBounds(150,75,110, 30);
				this.add(lb_text2);      
				lb_text2.setBounds(320,75, 110, 30);         
				this.add(lb_FCFS);
				lb_FCFS.setFont(new Font("BOLD", Font.BOLD, 20));
				lb_FCFS.setBounds(10,115,110,40);
				this.add(lb_FCFS_Avg_blocked_time);
				lb_FCFS_Avg_blocked_time.setFont(new Font("BOLD", Font.BOLD, 20));
				lb_FCFS_Avg_blocked_time.setBounds(180,115,110,40);
				this.add(lb_FCFS_Avg_return_time);
				lb_FCFS_Avg_return_time.setFont(new Font("BOLD", Font.BOLD, 20));
				lb_FCFS_Avg_return_time.setBounds(350,115,110,40);

				this.add(lb_HRN);
				lb_HRN.setFont(new Font("BOLD", Font.BOLD, 20));
				lb_HRN.setBounds(10,145,110,40);
				this.add(lb_HRN_Avg_blocked_time);
				lb_HRN_Avg_blocked_time.setFont(new Font("BOLD", Font.BOLD, 20));
				lb_HRN_Avg_blocked_time.setBounds(180,145,110,40);
				this.add(lb_HRN_Avg_return_time);
				lb_HRN_Avg_return_time.setFont(new Font("BOLD", Font.BOLD, 20));
				lb_HRN_Avg_return_time.setBounds(350,145,110,40);

				this.add(lb_SJF);
				lb_SJF.setFont(new Font("BOLD", Font.BOLD, 20));
				lb_SJF.setBounds(10,175,110,40);
				this.add(lb_SJF_Avg_blocked_time);
				lb_SJF_Avg_blocked_time.setFont(new Font("BOLD", Font.BOLD, 20));
				lb_SJF_Avg_blocked_time.setBounds(180,175,110,40);
				this.add(lb_SJF_Avg_return_time);
				lb_SJF_Avg_return_time.setFont(new Font("BOLD", Font.BOLD, 20));
				lb_SJF_Avg_return_time.setBounds(350,175,110,40);

				this.add(lb_SRT);
				lb_SRT.setFont(new Font("BOLD", Font.BOLD, 20));
				lb_SRT.setBounds(10,205,110,40);
				this.add(lb_SRT_Avg_blocked_time);
				lb_SRT_Avg_blocked_time.setFont(new Font("BOLD", Font.BOLD, 20));
				lb_SRT_Avg_blocked_time.setBounds(180,205,110,40);
				this.add(lb_SRT_Avg_return_time);
				lb_SRT_Avg_return_time.setFont(new Font("BOLD", Font.BOLD, 20));
				lb_SRT_Avg_return_time.setBounds(350,205,110,40);

				this.add(lb_Priority);
				lb_Priority.setFont(new Font("BOLD", Font.BOLD, 20));
				lb_Priority.setBounds(10,235,110,40);
				this.add(lb_Priority_Avg_blocked_time);
				lb_Priority_Avg_blocked_time.setFont(new Font("BOLD", Font.BOLD, 20));
				lb_Priority_Avg_blocked_time.setBounds(180,235,110,40);
				this.add(lb_Priority_Avg_return_time);
				lb_Priority_Avg_return_time.setFont(new Font("BOLD", Font.BOLD, 20));
				lb_Priority_Avg_return_time.setBounds(350,235,110,40);

				this.add(lb_RR);
				lb_RR.setFont(new Font("BOLD", Font.BOLD, 20));
				lb_RR.setBounds(10,265,110,40);
				this.add(lb_RR_Avg_blocked_time);
				lb_RR_Avg_blocked_time.setFont(new Font("BOLD", Font.BOLD, 20));
				lb_RR_Avg_blocked_time.setBounds(180,265,110,40);
				this.add(lb_RR_Avg_return_time);
				lb_RR_Avg_return_time.setFont(new Font("BOLD", Font.BOLD, 20));
				lb_RR_Avg_return_time.setBounds(350,265,110,40);

				bt_start.setEnabled(false);
				this.add(bt_start);
				bt_start.setBounds(10, 375, 110, 40);


				this.add(bt_reset);
				bt_reset.setBounds(150, 375, 110, 40);



				this.add(bt_exit);
				bt_exit.setBounds(290, 375, 110, 40);


				bt_start.addActionListener(this);
				bt_reset.addActionListener(this);
				bt_exit.addActionListener(this);


			}

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(e.getSource()==bt_start){
					int time=0;
					int sum =0;
					int sum1= 0;
					int count=1;
					@SuppressWarnings("unused")
					int c=0;
					int min=99999;
					boolean table_boolean_result= false;
					ArrayList <Integer>list=new ArrayList<Integer>();
					ArrayList <Integer>list1=new ArrayList<Integer>();
					ArrayList <Integer>list2=new ArrayList<Integer>();
					ArrayList <Integer>queue=new ArrayList<Integer>();
					ArrayList<Integer> Blocktime=new ArrayList<Integer>();
					ArrayList<Integer>sevicetime=new ArrayList<Integer>();
					String blocktime[]=new String[n];
					String blockresult;
					@SuppressWarnings("unused")
					String returetime[]=new String[n];
					@SuppressWarnings("unused")
					String retureresult;
					double blocktime_result_sum=0;
					@SuppressWarnings("unused")
					double reture_result_sum=0;
					result_1="";
					result_2="";


					int p[][]=new int[n][tb_process.getColumnCount()];
					room=new Task[n];
					table_boolean=new Boolean[tb_process.getColumnCount()];

					if(rb_Cpu_passive.isSelected()){

						for(int i=0;i<model1.getRowCount();i++){
							try{
								p[i][3]=Integer.parseInt((String) model1.getValueAt(i, 3));
								table_boolean[3]=true;
							}catch (Exception e1) {
								// TODO: handle exception
								System.out.println(e1);
								JOptionPane.showMessageDialog(null, model1.getColumnName(3)+"값을 재대로 입력하지않았습니다.","Message",JOptionPane.ERROR_MESSAGE); 
								table_boolean[3]=false;
							}	
							if(table_boolean[3]==false){
								break;
							}
						}
						for(int i=0;i<model1.getRowCount();i++){
							try{
								p[i][4]=Integer.parseInt((String) model1.getValueAt(i, 4));
								table_boolean[4]=true;
							}catch (Exception e1) {

								System.out.println(e1);
								JOptionPane.showMessageDialog(null, model1.getColumnName(4)+"값을 재대로 입력하지않았습니다.","Message",JOptionPane.ERROR_MESSAGE); 
								table_boolean[4]=false;
							}
							if(table_boolean[4]==false){
								break;
							}
						}
						for(int i=0;i<model1.getRowCount();i++){
							try{

								if(model1.getValueAt(i, 1)==null){
									JOptionPane.showMessageDialog(null, model1.getColumnName(1)+"에 값을 입력하세요","Message",JOptionPane.ERROR_MESSAGE); 
									table_boolean[1]=false;
									break;
								}
								else{
									table_boolean[1]=true;
								}

							}catch (Exception e1) {
								// TODO: handle exception
							}	
						}

						for(int i=0;i<model1.getRowCount();i++){
							try{

								if(model1.getValueAt(i, 0)==null){
									JOptionPane.showMessageDialog(null, model1.getColumnName(0)+"에 값을 입력하세요","Message",JOptionPane.ERROR_MESSAGE); 
									table_boolean[0]=false;
									break;
								}
								else{
									table_boolean[0]=true;
								}

							}catch (Exception e1) {
								// TODO: handle exception
							}	
						}
						if(rb_Cpu_passive.isSelected()&&rb_Priority.isSelected()){
							for(int i=0;i<model1.getRowCount();i++){
								try{
									p[i][2]=Integer.parseInt((String) model1.getValueAt(i, 2));
									table_boolean[2]=true;
								}catch (Exception e1) {
									// TODO: handle exception
									System.out.println(e1);
									JOptionPane.showMessageDialog(null, model1.getColumnName(2)+"값을 재대로 입력하지않았습니다.","Message",JOptionPane.ERROR_MESSAGE); 

									table_boolean[2]=false;

								}	
								if(table_boolean[2]==false){
									break;
								}
							}
						}
						else{
							table_boolean[2]=true;
						}
						for(int i=0;i<table_boolean.length;i++){
							if(table_boolean[i]==false){

								table_boolean_result=false;
								break;
							}
							else{
								table_boolean_result=true;
							}
						}
					}		
					if(rb_Cpu_auto.isSelected()){
						table_boolean_result=true;
					}

					if(table_boolean_result==true){

						unavailable.removeAll();
						lb_result1.setText(test);               
						final CategoryPlot plot = (CategoryPlot) chart.getPlot();
						DateAxis range = (DateAxis) plot.getRangeAxis();
						range.setDateFormatOverride(new SimpleDateFormat("SSS"));
						for(int i=0;i<n;i++){
							room[i]= new Task((String)model1.getValueAt(i, 1),new Date(0),new Date(0));
							unavailable.add(room[i]);
						}
						if(rb_Cpu_auto.isSelected()){
							for(int i=0;i<n;i++){
								System.out.println(" "+S[i][3]);
								p[i][3]=Integer.parseInt(S[i][3]);
								p[i][4]=Integer.parseInt(S[i][4]);

							}
						}
						if(rb_fcfs.isSelected()==true){
							for(int i=0;i<n;i++){
								list1.add(-1);
								list2.add(0);
								Blocktime.add(0);
								list.add(p[i][3]);
								sum+=p[i][4];
							}
							Collections.sort(list);

							while(time<=sum){        

								for(int i=0;i<n;i++){
									if(p[i][3]==time){
										list1.set(i, p[i][3]);
										queue.add(p[i][3]);
									}
								}

								if(queue.size()!=0){
									System.out.println("time1: "+time);
									for(int i=0;i<n;i++){
										if(queue.get(0)==p[i][3]&&list2.get(i)!=-1){
											k=i;
											if(sum1==0){
												sum1=p[k][3]+p[k][4];
											}
											break;
										}
									}
									System.out.println("queue: "+queue);
									System.out.println("k"+k);


									if(sum1==time){
										System.out.println("time2: "+time);
										list2.set(k, -1);
										queue.remove(0);
										list.remove(0);
										list1.set(k, -1);
										for(int i=0;i<n;i++){
											if(queue.size()!=0&&list.size()!=0){
												if(list.get(0)==p[i][3]&&list2.get(i)!=-1){
													k=i;
													sum1+=p[k][4];
													System.out.println("sum1: "+sum1);
													break;
												}
											}
											if(queue.size()==0&&list.size()!=0){
												if(list.get(0)==p[i][3]&&list2.get(i)!=-1){
													k=i;
													sum1=p[k][3]+p[k][4];
													System.out.println("sum2: "+sum1);
													break;
												}
											}
										}
									}   
									if(queue.size()!=0){
										room[k].addSubtask(new Task((String)model1.getValueAt(k, 1),new SimpleTimePeriod(time, time+1)));
										for(int j=0;j<n;j++){
											for(int i=0;i<queue.size();i++){
												if(queue.get(i)==p[j][3]&&j!=k&&list1.get(j)!=-1){
													Blocktime.set(j,Blocktime.get(j)+1);
													break;
												}  
											}
										}
									}
								}
								else if(queue.size()==0){
									sum++;
								}
								time++;
							}
							System.out.println(sum);
							blockresult="(";
							retureresult="(";
							for(int i=0;i<Blocktime.size();i++){
								blocktime_result_sum+=Blocktime.get(i);
								reture_result_sum+=(Blocktime.get(i)+p[i][4]);
								blocktime[i]=String.valueOf(Blocktime.get(i));
								returetime[i]=String.valueOf(Blocktime.get(i)+p[i][4]);
								if(i==Blocktime.size()-1){
									blockresult+=blocktime[i]+")";
									retureresult+=returetime[i]+")";
								}
								else{
									blockresult+=blocktime[i]+"+";
									retureresult+=returetime[i]+"+";
								}
							}
							blocktime_result_sum/=n;   
							reture_result_sum/=n;   
							blockresult+="/"+n+"="+blocktime_result_sum+"ms";
							retureresult+="/"+n+"="+reture_result_sum+"ms";
							tf_avg_blocked_time.setText(blockresult);
							tf_return_time.setText(retureresult);
							lb_FCFS_Avg_blocked_time.setText(String.valueOf(blocktime_result_sum));
							lb_FCFS_Avg_return_time.setText(String.valueOf(reture_result_sum));
						}
						
						if(rb_SJF.isSelected()==true){   //SJF기법
							boolean sw1=false;
						
							for(int i=0;i<n;i++){
								list1.add(-1);
								list2.add(0);
								Blocktime.add(0);
								sum+=p[i][4];
							}

							while(time<sum){
								for(int i=0;i<n;i++){
									if(time==p[i][3]){
										queue.add(i);
										sevicetime.add(p[i][4]);
									}
								}
								if(sevicetime.size()!=0){
									
									System.out.println("sevicetime: "+sevicetime);
									if(sw1==false){
										Collections.sort(sevicetime);
									}
									for(int j=0;j<queue.size();j++){
										for(int i=0;i<n;i++){
											if(p[i][4]==sevicetime.get(0)&&sw1==false&&list1.get(i)!=0&&queue.get(j)==i){
												k=i;
												sw1=true;
												break;
											}
										}
									}
									System.out.println("k");
									room[k].addSubtask(new Task((String)model1.getValueAt(k, 1),new SimpleTimePeriod(time, time+1)));
								
								    sevicetime.set(0, sevicetime.get(0)-1);
									if(sevicetime.get(0)==0){
										sevicetime.remove(0);
										for(int i=0;i<queue.size();i++){
											if(queue.get(i)==k){
												queue.remove(i);
											}
										}
										list1.set(k,0);
										sw1=false;
									}
									for(int j=0;j<queue.size();j++){
										for(int i=0;i<n;i++){
											if(queue.get(j)==i&&k!=i){
												Blocktime.set(i, Blocktime.get(i)+1);
											}
										}
									}
								}
								else if(sevicetime.size()==0){
									sum++;
								}
								time++;
							}
							blockresult="(";
							retureresult="(";
							for(int i=0;i<Blocktime.size();i++){
								blocktime_result_sum+=Blocktime.get(i);
								reture_result_sum+=(Blocktime.get(i)+p[i][4]);
								blocktime[i]=String.valueOf(Blocktime.get(i));
								returetime[i]=String.valueOf(Blocktime.get(i)+p[i][4]);
								if(i==Blocktime.size()-1){
									blockresult+=blocktime[i]+")";
									retureresult+=returetime[i]+")";
								}
								else{
									blockresult+=blocktime[i]+"+";
									retureresult+=returetime[i]+"+";
								}
							}
							blocktime_result_sum/=n;	
							reture_result_sum/=n;	
							blockresult+="/"+n+"="+blocktime_result_sum+"ms";
							retureresult+="/"+n+"="+reture_result_sum+"ms";
							tf_avg_blocked_time.setText(blockresult);
							tf_return_time.setText(retureresult);
							lb_SJF_Avg_blocked_time.setText(String.valueOf(blocktime_result_sum));
							lb_SJF_Avg_return_time.setText(String.valueOf(reture_result_sum));
							//							range.setMaximumDate(new Date(sum+10));
						}

						if(rb_HRN.isSelected()==true){

							ArrayList<Double>response=new ArrayList<Double>();
							ArrayList<Integer>Broketime1=new ArrayList<Integer>();
							boolean sw=true;
							for(int i=0;i<n;i++){
								list1.add(-1);
								list2.add(0);
								Blocktime.add(0);
								Broketime1.add(0);
								response.add(0.);
								sevicetime.add(p[i][4]);
								sum+=p[i][4];
							}

							while(time!=sum){        

								for(int i=0;i<n;i++){
									if(p[i][3]==time){
										list1.set(i, p[i][3]);
										queue.add(p[i][3]);
									}
								}

								if(queue.size()!=0){
									System.out.println("time1: "+time);
									//                           for(int i=0;i<n;i++){
									//                              if(queue.get(0)==p[i][3]&&list2.get(i)!=-1&&sw==true){
									//                                 k=i;
									//                                 break;
									//                              }
									//                           }
									Double max=-0.5;
									for(int i=0;i<response.size();i++){
										if(queue.size()!=0){
											if(response.get(i)!=-1.&&response.get(i)>max&&list1.get(i)!=-1&&sw==true){
												max=response.get(i);
												k=i;
											}
										}
									}

									sw=false;

									room[k].addSubtask(new Task((String)model1.getValueAt(k, 1),new SimpleTimePeriod(time, time+1)));
									sevicetime.set(k,sevicetime.get(k)-1);
									System.out.println("sevicetime:"+sevicetime);
									for(int j=0;j<n;j++){
										for(int i=0;i<queue.size();i++){
											if(queue.get(i)==p[j][3]&&j!=k&&list1.get(j)!=-1){
												Broketime1.set(j,Broketime1.get(j)+1);
												break;
											}  
										}
									}
									System.out.println("queue: "+queue);
									System.out.println("k1: "+k);

									if(sevicetime.get(k)==0){
										System.out.println("time2: "+time);
										list1.set(k, -1);
										for(int i=0;i<queue.size();i++){
											if(queue.get(i)==p[k][3]){
												queue.remove(i);
												break;
											}
										}

										sw=true;
										for(int j=0;j<queue.size();j++){
											for(int i=0;i<response.size();i++){
												if(i==k){
													response.set(k, -1.);
												}
												else if(i!=k&&response.get(i)!=-1&&queue.get(j)==p[i][3]){
													response.set(i, 1+((double)Broketime1.get(i)/p[i][4]));
												}

											}
										}
										System.out.println("Bt:"+Blocktime);
										System.out.println("response:"+response);
									}
									for(int j=0;j<n;j++){
										for(int i=0;i<queue.size();i++){
											if(queue.get(i)==p[j][3]&&j!=k&&list1.get(j)!=-1){
												Blocktime.set(j,Blocktime.get(j)+1);
												break;
											}  
										}
									}
								}
								else if(queue.size()==0){
									sum++;
								}
								time++;
							}
							for(int i=0;i<Blocktime.size();i++){
								if(Blocktime.get(i)!=0){
									Blocktime.set(i, Blocktime.get(i));
								}
							}
							System.out.println("End Blocked time: "+Blocktime);
							System.out.println(sum);
							blockresult="(";
							retureresult="(";
							for(int i=0;i<Blocktime.size();i++){
								blocktime_result_sum+=Blocktime.get(i);
								reture_result_sum+=(Blocktime.get(i)+p[i][4]);
								blocktime[i]=String.valueOf(Blocktime.get(i));
								returetime[i]=String.valueOf(Blocktime.get(i)+p[i][4]);
								if(i==Blocktime.size()-1){
									blockresult+=blocktime[i]+")";
									retureresult+=returetime[i]+")";
								}
								else{
									blockresult+=blocktime[i]+"+";
									retureresult+=returetime[i]+"+";
								}
							}
							blocktime_result_sum/=n;   
							reture_result_sum/=n;   
							blockresult+="/"+n+"="+blocktime_result_sum+"ms";
							retureresult+="/"+n+"="+reture_result_sum+"ms";
							tf_avg_blocked_time.setText(blockresult);
							tf_return_time.setText(retureresult);
							lb_HRN_Avg_blocked_time.setText(String.valueOf(blocktime_result_sum));
							lb_HRN_Avg_return_time.setText(String.valueOf(reture_result_sum));
							//							range.setMaximumDate(new Date(sum+10));

						} 
						if(rb_Priority.isSelected()==true){  //우선순위 기법
							ArrayList<Integer>priority=new ArrayList<Integer>();
							if(rb_Cpu_auto.isSelected()){
								for(int i=0;i<n;i++){
									p[i][2]=Integer.parseInt(S[i][2]);
								}
							}
							for(int i=0;i<n;i++){
								list.add(p[i][3]);
								list1.add(0);
								sevicetime.add(0);
								Blocktime.add(0);
								priority.add(-1);
								sum+=p[i][4];
							}
							System.out.println("sum: "+sum);

							while(time<sum){
								for(int i=0;i<n;i++){
									if(time==p[i][3]){
										queue.add(p[i][3]);
										sevicetime.set(i,p[i][4]);
										priority.set(i,p[i][2]);
									}
								}
								for(int i=0;i<priority.size();i++){
									if(min>priority.get(i)&&priority.get(i)!=-1&&list1.get(i)!=-1){
										min=priority.get(i);
										k=i;
									}
								}

								if(queue.size()!=0){
									room[k].addSubtask(new Task((String)model1.getValueAt(k, 1),new SimpleTimePeriod(time, time+1)));
									System.out.println("Blocked time: "+Blocktime);
									sevicetime.set(k,sevicetime.get(k)-1);
									for(int j=0;j<n;j++){
										for(int i=0;i<queue.size();i++){
											if(queue.get(i)==p[j][3]&&j!=k&&list1.get(j)!=-1){
												Blocktime.set(j,Blocktime.get(j)+1);
												break;
											}  
										}
									}
									if(sevicetime.get(k)==0){
										for(int i=0;i<queue.size();i++){
											if(queue.get(i)==list.get(k)){
												queue.remove(i);
												list1.set(k, -1);
												break;
											}
										}
										min=99999;
									}


								}
								else if(queue.size()==0){
									sum=sum+1;
								}
								time++;
							}
							System.out.println("End Blocked time: "+Blocktime);
							System.out.println(sum);
							blockresult="(";
							retureresult="(";
							for(int i=0;i<Blocktime.size();i++){
								blocktime_result_sum+=Blocktime.get(i);
								reture_result_sum+=(Blocktime.get(i)+p[i][4]);
								blocktime[i]=String.valueOf(Blocktime.get(i));
								returetime[i]=String.valueOf(Blocktime.get(i)+p[i][4]);
								if(i==Blocktime.size()-1){
									blockresult+=blocktime[i]+")";
									retureresult+=returetime[i]+")";
								}
								else{
									blockresult+=blocktime[i]+"+";
									retureresult+=returetime[i]+"+";
								}
							}
							blocktime_result_sum/=n;	
							reture_result_sum/=n;	
							blockresult+="/"+n+"="+blocktime_result_sum+"ms";
							retureresult+="/"+n+"="+reture_result_sum+"ms";
							tf_avg_blocked_time.setText(blockresult);
							tf_return_time.setText(retureresult);
							lb_Priority_Avg_blocked_time.setText(String.valueOf(blocktime_result_sum));
							lb_Priority_Avg_return_time.setText(String.valueOf(reture_result_sum));
							//							range.setMaximumDate(new Date(sum+10));
						}
						if(rb_SRT.isSelected()==true){
							for(int i=0;i<n;i++){
								list1.add(0);
								list2.add(0);
								list.add(p[i][3]);
								sevicetime.add(0);
								Blocktime.add(0);
								sum+=p[i][4];
							}
							System.out.println("sum: "+sum);

							while(time<sum){
								for(int i=0;i<n;i++){
									if(time==p[i][3]){
										queue.add(p[i][3]);
										sevicetime.set(i,p[i][4]);
									}
								}
								for(int i=0;i<sevicetime.size();i++){
									if(min>sevicetime.get(i)&&sevicetime.get(i)!=0&&list1.get(i)!=-1){
										min=sevicetime.get(i);
										k=i;
									}
								}

								if(queue.size()!=0){
									room[k].addSubtask(new Task((String)model1.getValueAt(k, 1),new SimpleTimePeriod(time, time+1)));
									System.out.println("Blocked time: "+Blocktime);
									for(int j=0;j<n;j++){
										for(int i=0;i<queue.size();i++){
											if(queue.get(i)==p[j][3]&&j!=k&&list1.get(j)!=-1){
												System.out.println("queue:"+queue);
												Blocktime.set(j,Blocktime.get(j)+1);
												break;
											}  
										}
									}
									sevicetime.set(k,sevicetime.get(k)-1);
									System.out.println("sevicetime:"+sevicetime);
									if(sevicetime.get(k)==0){
										min=99999;
										for(int i=0;i<queue.size();i++){
											if(queue.get(i)==list.get(k)&&list1.get(k)!=-1){
												queue.remove(i);
												list1.set(k, -1);
											}
										}
									}
								}
								else if(queue.size()==0){
									sum=sum+1;
								}
								time++;
							}
							System.out.println("End Blocked time: "+Blocktime);
							System.out.println(sum);
							blockresult="(";
							retureresult="(";
							for(int i=0;i<Blocktime.size();i++){
								blocktime_result_sum+=Blocktime.get(i);
								reture_result_sum+=(Blocktime.get(i)+p[i][4]);
								blocktime[i]=String.valueOf(Blocktime.get(i));
								returetime[i]=String.valueOf(Blocktime.get(i)+p[i][4]);
								if(i==Blocktime.size()-1){
									blockresult+=blocktime[i]+")";
									retureresult+=returetime[i]+")";
								}
								else{
									blockresult+=blocktime[i]+"+";
									retureresult+=returetime[i]+"+";
								}
							}
							blocktime_result_sum/=n;	
							reture_result_sum/=n;	
							blockresult+="/"+n+"="+blocktime_result_sum+"ms";
							retureresult+="/"+n+"="+reture_result_sum+"ms";
							tf_avg_blocked_time.setText(blockresult);
							tf_return_time.setText(retureresult);
							lb_SRT_Avg_blocked_time.setText(String.valueOf(blocktime_result_sum));
							lb_SRT_Avg_return_time.setText(String.valueOf(reture_result_sum));
							//							range.setMaximumDate(new Date(sum+10));

						}
						if(rb_Round_Robin.isSelected()==true){
							System.out.println(cb_quantum.getSelectedItem().toString());
							int quantum=Integer.parseInt(cb_quantum.getSelectedItem().toString());
							int quantumcount=0;

							for(int i=0;i<n;i++){
								list1.add(-1);
								Blocktime.add(0);
								sevicetime.add(-1);
								sum+=p[i][4];
							}
							System.out.println("sum: "+sum);

							while(time<sum){
								for(int i=0;i<n;i++){
									if(time==p[i][3]){
										queue.add(i);
										sevicetime.set(i,p[i][4]);
									}
								}



								if(queue.size()!=0){
									System.out.println("queue: "+queue);

									if(quantum==quantumcount){
										int move=queue.get(0);
										queue.remove(0);
										queue.add(move);
										quantumcount=0;
									}
									k=queue.get(0);
									room[k].addSubtask(new Task((String)model1.getValueAt(k, 1),new SimpleTimePeriod(time, time+1)));
									sevicetime.set(k,sevicetime.get(k)-1);
									quantumcount++;
									if(sevicetime.get(k)==0){
										queue.remove(0);
										quantumcount=0;
									}

									for(int j=0;j<n;j++){
										for(int i=0;i<queue.size();i++){
											if(queue.get(i)==j&&j!=k){
												Blocktime.set(j,Blocktime.get(j)+1);
												break;
											}  
										}
									}


								}
								else if(queue.size()==0){
									sum++;
								}

								time++;
							}
							System.out.println("End Blocked time: "+Blocktime);
							System.out.println(sum);
							blockresult="(";
							retureresult="(";
							for(int i=0;i<Blocktime.size();i++){
								blocktime_result_sum+=Blocktime.get(i);
								reture_result_sum+=(Blocktime.get(i)+p[i][4]);
								blocktime[i]=String.valueOf(Blocktime.get(i));
								returetime[i]=String.valueOf(Blocktime.get(i)+p[i][4]);
								if(i==Blocktime.size()-1){
									blockresult+=blocktime[i]+")";
									retureresult+=returetime[i]+")";
								}
								else{
									blockresult+=blocktime[i]+"+";
									retureresult+=returetime[i]+"+";
								}
							}
							blocktime_result_sum/=n;	
							reture_result_sum/=n;	
							blockresult+="/"+n+"="+blocktime_result_sum+"ms";
							retureresult+="/"+n+"="+reture_result_sum+"ms";
							tf_avg_blocked_time.setText(blockresult);
							tf_return_time.setText(retureresult);
							lb_RR_Avg_blocked_time.setText(String.valueOf(blocktime_result_sum));
							lb_RR_Avg_return_time.setText(String.valueOf(reture_result_sum));
							//							range.setMaximumDate(new Date(sum+10));
						}
						for(int i=0;i<n;i++){
							result_1+=(String)model1.getValueAt(i, 1)+": ";
							result_1+=blocktime[i]+"\n";
							result_2+=(String)model1.getValueAt(i, 1)+": ";
							result_2+=returetime[i]+"\n";
						}
						range.setMaximumDate(new Date(sum+1	));

						sum=0;
						min=99999;

					}
				}
				else if(e.getSource()==bt_reset){
					System.out.println("reset");
					//               System.out.println("선택됨");
					reset();
				}
				else if(e.getSource()==bt_exit){
					System.exit(0);
				}
			}
		}
	}

	public CpuFrame() {
		// TODO Auto-generated constructor stub
		Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
		this.setTitle("CPU Time");
		//      this.setLocationRelativeTo(null);  //중앙 위치로

		this.setSize(dim.width/2,dim.height/2); //화면크기의 반만큼 사이즈를줌
		init(); //프레임의 인터페이스 구현
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	private void init() {
		// TODO Auto-generated method stub
		//      p1.setBorder(new LineBorder(Color.RED,5));
		this.setJMenuBar(menuBar);
		helpMenu.setText("Help");
		helpItem.setText("도움말");
		menuBar.add(helpMenu);
		helpMenu.add(helpItem);
		initHelp();

		helpItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CSH.DisplayHelpFromSource(helpBroker);
				// 반드시 true로 호출해야만 보인다
				helpBroker.setDisplayed(true);
			}
		});
		p1.setLayout(new GridLayout(2,1,20,20));
		p1.add(new CpuCreatemethod());
		p1.add(new Cpu_schedule_technique());
		p2.add(p1);
		p3.add(p2);
		//      p4.setLayout(new BorderLayout());
		this.setResizable(false);
		this.add(new CpuResult(),BorderLayout.CENTER);
		this.add(p3,BorderLayout.WEST);
	}
	private void initHelp() {
		final String helpsetName = "helpdemo1";
		try {
			// 헬프셋 파일을 지정한다
			//	         File file = new File(".\\src\\my_sample\\helpdemo2.hs");
			ClassLoader cl = this.getClass().getClassLoader();
			URL hsURL = HelpSet.findHelpSet(cl, helpsetName);
			helpSet = new HelpSet(cl,hsURL);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Cannot find helpset file");
		}
		helpBroker = helpSet.createHelpBroker();
		helpBroker.setSize(new Dimension(700, 700));
		// 기본값 id를 지정
		helpBroker.enableHelpKey(getRootPane(), "overview", helpSet);
	}
}