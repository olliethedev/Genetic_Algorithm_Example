
package other;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author Oleksiy
 */
public class Log 
{
    //private static String text="";
    private static JFrame frame ;
    private static JTextArea textBox;
    private static boolean isWindow=false;
    public static void print(String str)
    {
        if(!isWindow)
        {
            System.out.print(str);
        }
        else
        {           
           Log.textBox.setText(Log.textBox.getText()+str);            
        }
    }
    public static void println(String str)
    {
        if(!isWindow)
        {
            System.out.print(str);
        }
        else
        {            
            Log.textBox.setText(Log.textBox.getText()+"\n"+str);            
        }
    }
    public static void isWindow(boolean status)
    {
        Log.isWindow=status;
        if(status)
        {
            if(Log.frame==null)
            {                
                JTextArea ta = new JTextArea();  
                ta.setLineWrap(true);  
                JFrame f = new JFrame();  
                f.getContentPane().add(new JScrollPane(ta));  
                f.setSize(400,300);  
                f.setLocationRelativeTo(null);  
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
                f.setVisible(true);  
                Log.frame =f;
                Log.textBox=ta;
                DefaultCaret caret = (DefaultCaret)Log.textBox.getCaret();
                caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
            }
        }
        else
        {
            if(Log.frame!=null)
            {
                Log.frame.dispatchEvent(new WindowEvent(Log.frame, WindowEvent.WINDOW_CLOSING));
            }
        }
    }
}
