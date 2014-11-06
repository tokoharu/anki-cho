package roteLearningNote;

import java.awt.Insets;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

public class MyJTextArea extends JTextArea {
	
	/**
	 * よくわからんがつけてみた
	 */
	private static final long serialVersionUID = 1L;
	private JScrollPane scrollpane;
	
	public MyJTextArea(int width , int height) {
		super(width,height);
		setMargin(new Insets(5,10,5,10));
		setBorder(new EtchedBorder(EtchedBorder.RAISED));
		scrollpane = new JScrollPane(this);
		setLineWrap(true);
	}
	public JScrollPane getJScrollPane() {
		return scrollpane;
	}
	public void setTexts(String[] texts) {
		for(int i=0; i<texts.length; i++) 
			setText(texts[i]);
	}
}
