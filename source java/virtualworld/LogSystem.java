package virtualworld;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTextArea;

public class LogSystem 
{
    private int logsLength_ = 0;
    private int logsMaxLength_ = 0;
    private String[] logs_;
    
    public LogSystem(int logsLength)
    {
        logsMaxLength_ = logsLength;
        logs_ = new String[logsMaxLength_];
    }
    
    public void Log(String message)
    {
        if (logsLength_ == logsMaxLength_)
	{
		for (int i = 1; i < logsLength_; i++)
			logs_[i - 1] = logs_[i];
		logs_[logsMaxLength_ - 1] = message;
	}
	else
		logs_[logsLength_++] = message;
    } 
    
    public void Clear()
    {
        logsLength_ = 0;
    }
    
    private String GetLogsAsString()
    {
        String result = "";
        for(int i = 0; i<logsLength_; i++)
            result += logs_[i] + '\n';
        return result;
    }
    
    public JComponent GetJComponent()
    {    
        JTextArea textArea = new JTextArea()
        {
            @Override
            protected void paintComponent(Graphics g)
            {
                setText(GetLogsAsString());
                super.paintComponent(g);
            }
        };
        
        textArea.setColumns(30);
        textArea.setRows(logsMaxLength_);
        textArea.setEditable(false);
        textArea.setMargin(new Insets(20, 20, 20, 20));
        textArea.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        textArea.setPreferredSize(new Dimension(300, 300));
         
        return textArea;
    }

}
