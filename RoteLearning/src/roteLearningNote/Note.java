package roteLearningNote;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

*/
public class Note extends JFrame implements ActionListener{

	File inputFile;
	
	private static final long serialVersionUID = 1L;//意味をよくわかってないけどつけた
	static final int WIDTH = 60;
	static final int HEIGHTP = 10;
	static final int HEIGHTA = 18;
	protected int problemNum = 0;
	protected JLabel probNumLabel = new JLabel();
	protected boolean isAnswer = true;
	
	protected ArrayList<Problem> problemset = new ArrayList<Problem>();
	protected ArrayList<Tag> tags = new ArrayList<Tag>();
	
	protected MyJTextArea mainProblemText = new MyJTextArea(HEIGHTP,WIDTH);
	protected MyJTextArea mainAnswerText = new MyJTextArea(HEIGHTA,WIDTH);
	
	private String[][] allProblemTableText = {};;
	private String[] columnName = {"問題番号","問題","答え"};
	protected  DefaultTableModel allProblemTableModel
     = new DefaultTableModel(allProblemTableText, columnName );
	
	protected Model model;
	
	public Note() {
		super("anki-cho ver.3" );

		tags.add(new Tag("ROOT"));

		JTabbedPane tabbedPane = new JTabbedPane();
		Dimension preferredSize = new Dimension(720,660);
		tabbedPane.setPreferredSize(preferredSize);
		model = new Model(this);
		//ファイル読み込み
//		model.readFile();	
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		JPanel frontPane = new JPanel();
		frontPane.setLayout(new BoxLayout(frontPane,BoxLayout.X_AXIS));
		{
			mainProblemText.setFocusable(false);
			mainAnswerText.setFocusable(false);
			JPanel mainPane = new JPanel();
			mainPane.add(probNumLabel);
			mainPane.setLayout(new BoxLayout(mainPane,BoxLayout.Y_AXIS));
			mainPane.add(mainProblemText.getJScrollPane());
			mainPane.add(mainAnswerText.getJScrollPane());
			
			JButton nextButton = new JButton("Next");
			nextButton.addActionListener(this);
			mainPane.add(nextButton);

			tabbedPane.addTab("MainNote",mainPane);
		}
		{
			//addPanel
			final MyJTextArea addProblemText = new MyJTextArea(HEIGHTP,WIDTH);
			final MyJTextArea addAnswerText = new MyJTextArea(HEIGHTA,WIDTH);
			JPanel addPane = new JPanel();
			addPane.setLayout(new BoxLayout(addPane,BoxLayout.Y_AXIS));
			
			addPane.add(addProblemText.getJScrollPane());
			addPane.add(addAnswerText.getJScrollPane());
			
			JButton addButton = new JButton("Add");
			addButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					String statement = addProblemText.getText();
					String answer = addAnswerText.getText();
					Problem prob = new Problem(statement, answer, problemset.size()+1);
					problemset.add(prob);
					System.out.println("num" + prob.getOriginalNumber());
					
					addProblemText.setText("正しく問題がセットされました。問題番号は"+problemset.size()+"になります");
					addAnswerText.setText("");
					model.setJTable();
				}
			});
			addPane.add(addButton);
			
			tabbedPane.addTab("AddPage",addPane);
			
		}
		{
			//editPanel.
			final JLabel editLabel = new JLabel();
			final MyJTextArea editAreaProb = new MyJTextArea(HEIGHTP,WIDTH);
			final MyJTextArea editAreaAns = new MyJTextArea(HEIGHTA,WIDTH);
			{
				JPanel editPane = new JPanel();
				editPane.setLayout(new BoxLayout(editPane,BoxLayout.Y_AXIS));
				
				editPane.add(editLabel);
				editPane.add(editAreaProb.getJScrollPane());
				editPane.add(editAreaAns.getJScrollPane());
				JButton editButton = new JButton("Edit");
				editButton.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						String statement = editAreaProb.getText();
						String answer = editAreaAns.getText();
						int index = Integer.parseInt(editLabel.getText());
						if(index<0 ) return;
						{
							Problem problem = problemset.get(index);
							problem.setProblem(statement);
							problem.setAnswer(answer);
							problemset.set(index, problem);
							
						}
						editAreaProb.setText("");
						editAreaAns.setText("");
						editLabel.setText("-1");
						model.setJTable();
					}
					
				});

				editPane.add(editButton);
				
				tabbedPane.addTab("editpage",editPane);
			}
			{
				JPanel allViewPane = new JPanel();
				allViewPane.setLayout(new BoxLayout(allViewPane,BoxLayout.Y_AXIS));
				final JTable table = new JTable(allProblemTableModel);	
				table.setAutoResizeMode(table.AUTO_RESIZE_OFF);
				table.setFocusable(false);
				table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
					@Override 
				
					public void valueChanged(ListSelectionEvent e) {
						if(e.getValueIsAdjusting()) return;
						DefaultListSelectionModel k = (DefaultListSelectionModel) e.getSource();

						int pos = k.getAnchorSelectionIndex();
						if(pos>k.getMaxSelectionIndex())return;
						System.out.println("pos"+pos);
						editLabel.setText(String.valueOf(pos));
						editAreaProb.setText(problemset.get(pos).getProblem());
						editAreaAns.setText(problemset.get(pos).getAnswer());
						
					}
				});
			
				DefaultTableColumnModel columnModel= (DefaultTableColumnModel) table.getColumnModel();
				columnModel.getColumn(0).setPreferredWidth(50);
				columnModel.getColumn(1).setPreferredWidth(330);
				columnModel.getColumn(2).setPreferredWidth(330);
				
				JScrollPane sp = new JScrollPane(table);
				allViewPane.add(sp);
				
				JButton changeButton = new JButton("ChangePos");
				changeButton.addActionListener(this);

				allViewPane.add(changeButton);
				tabbedPane.addTab("allProblems", allViewPane);
			}	
		}
		/*Tag機能を作ろうとした残骸
		{//EditTagPanel
			JPanel editTagPane = new JPanel();
			DefaultMutableTreeNode top = new DefaultMutableTreeNode(tags.get(0));
			JTree tree = new JTree(top);
//			JScrollPane treeView = new JScrollPane(tree);

			DynamicTreePane treeD = new DynamicTreePane(this);
			JScrollPane treeView = new JScrollPane(treeD);
			
			treeView.setPreferredSize(new Dimension(500,500));
			
				
			editTagPane.add(treeView);
			//editTagPane.add(editButton);
			tabbedPane.addTab("EditTag" , editTagPane);
		}
		*/
		frontPane.add(tabbedPane);
		JPanel sidePane = new JPanel();
		sidePane.setLayout(new BoxLayout(sidePane, BoxLayout.Y_AXIS));
		{
			
			//sidePaneの設定。Saveとfileのロード
			JButton save = new JButton("Save");
			save.addActionListener(this);
			sidePane.add(save);

			final JButton fileButton = new  JButton("File") ;
			fileButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {

					JFileChooser filechooser = new JFileChooser("/home/shogo/ドキュメント/roteLearning/");
					int selected = filechooser.showOpenDialog(fileButton);
					if (selected == JFileChooser.APPROVE_OPTION){
						inputFile = filechooser.getSelectedFile();
						model.readFile();
						model.setJTable();
					}
				}
			});
			sidePane.add(fileButton);
		}
		frontPane.add(sidePane);
		getContentPane().add(frontPane);
		pack();
		setVisible(true);
		
		model.setNote(this);
	}
	
	public void actionPerformed(ActionEvent e){
		System.out.println(e.getActionCommand() );
		
		String com = e.getActionCommand();
		
		if(com.equals("Next")) model.printNext();
		if(com.equals("Save")) model.saveState();
		if(com.equals("ChangePos")) {
			String fromS = JOptionPane.showInputDialog(this, "from?");
			String toS   = JOptionPane.showInputDialog(this, "to?");	
			int fromI = Integer.parseInt(fromS);
			int toI   = Integer.parseInt(toS);
			
			if(fromI<toI) {
				
				Problem problemTmp = problemset.get(fromI);

				for(int i=fromI; i<toI; i++) {
					problemset.set(i, problemset.get(i+1));
				}
				problemset.set(toI, problemTmp);
			}
			if(fromI>toI) {
				Problem problemTmp = problemset.get(fromI);

				for(int i=fromI; i>toI; i--) {
					problemset.set(i, problemset.get(i-1));
				}
				problemset.set(toI, problemTmp);
			}
			model.setJTable();
		}
	}
	public void run() {
		System.out.println("start");
	}	
	
	public static void main(String[] args) {
		Note e = new Note();
		e.run();
	}
	
}
