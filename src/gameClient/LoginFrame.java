package gameClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LoginFrame extends JFrame implements Runnable, ActionListener
{
    private Dimension screen_size; // dimensions of the screen
    private double fractionX; // ratio of screen
    private double fractionY;
    private JButton login; // login button
    private JTextArea textArea_id; // take user id
    private JTextArea textArea_level; // take the scenario
    private JLabel label_id; // label of id text field
    private JLabel label_level; // label of level text field
    private JLabel messagebox; // a message box
    private int login_framex; // frame sizes
    private int login_framey;
    private Ex2.SharedLevelBuffer sharedLevelBuffer; // shared level buffer
    private Lock lockscreen;
    public Condition condition;

    public LoginFrame(double X,double Y,Ex2.SharedLevelBuffer sharedLevelBuffer) {
        super();
        this.sharedLevelBuffer = sharedLevelBuffer;
        this.lockscreen = new ReentrantLock();
        this.condition = this.lockscreen.newCondition();
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// ste exist on close
        //set X and Y
        fractionX = X;
        fractionY = Y;
        // get the screen size dimensions and create the frame x and frame y vars
        screen_size = Toolkit.getDefaultToolkit().getScreenSize();
        login_framex = (int) (fractionX * screen_size.width);
        login_framey = (int) (fractionY * screen_size.height);
        setBounds(0, 0, login_framex, login_framey); // set the bounds of the window
        Font f =  new Font("Arial",Font.PLAIN,20); // set the font to be
        //init label_id
        label_id = new JLabel();
        label_id.setFont(f);
        label_id.setText("ID:");
        label_id.setBounds((int)(0.25*login_framex)-40,(int)(0.5*login_framey)-61,40,24);
        //init label_level
        label_level = new JLabel();
        label_level.setFont(f);
        label_level.setText("LEVEL:");
        label_level.setBounds((int)(0.25*login_framex)-90,(int)(0.5*login_framey)-30,80,24);
        // init the text area
        textArea_id = new JTextArea("322699125");
        textArea_id.setBorder(BorderFactory.createLineBorder(Color.gray,1));
        textArea_id.setFont(f);
        textArea_id.setBounds((int)(0.25*login_framex),(int)(0.5*login_framey)-61,(int)(0.5*login_framex),30);
        //init text area level
        textArea_level = new JTextArea();
        textArea_level.setBorder(BorderFactory.createLineBorder(Color.gray,1));
        textArea_level.setFont(f);
        textArea_level.setBounds((int)(0.25*login_framex),(int)(0.5*login_framey)-30,(int)(0.5*login_framex),30);
        // init the login button
        login = new JButton("login");
        login.setFont(f);
        login.setBounds((int)(login_framex*0.25),(int)(login_framey*0.5)+1,(int)(login_framex*0.5),30);
        login.addActionListener(this);
        // create a new message box
        messagebox = new JLabel();
        messagebox.setBounds((int)(login_framex*0.25),(int)(login_framey*0.5)+31,(int)(login_framex*0.5),30);
        messagebox.setFont(f);
        messagebox.setForeground(Color.RED);
         // add all of the components to the frame
        add(textArea_id);
        add(textArea_level);
        add(label_id);
        add(label_level);
        add(login);
        add(messagebox);
    }

    /**
     * Override the run method
     */
    @Override
    public void run()
    {
        setVisible(true); // set screen visible
        this.lockscreen.lock(); // lock the lockscreen
        try {
            // waiting for a good login condition to happen
            this.condition.await(); // wait for action to happen
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            this.lockscreen.unlock(); // unlock the screen if anything goes wrong
            setVisible(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == this.login) // if the login button did the action continue
        {
            String id = this.textArea_id.getText(); // take the id
            String level = this.textArea_level.getText(); // take the level
            int l; // level var to put in the level
            if (id.equals("")) // test case id is empty
            {
                this.messagebox.setText("Can't login with no id..."); // write to message box so that the user could see
                return; // terminate function
            }
            try
            { // try convert the level into an int
                l= Integer.parseInt(level);
            }
            catch (NumberFormatException exception) // if failed
            {
                this.messagebox.setFont(new Font("Arial",Font.PLAIN,20));
                this.messagebox.setText("The level should be a number "); // present the user this message
                return; // terminate function
            }
            this.messagebox.setFont(new Font("Arial",Font.PLAIN,24)); // set font in message box back to what it should be
            this.messagebox.setText(""); // clear the message box
            // put the information in the message box
            this.sharedLevelBuffer.setId(id);
            this.sharedLevelBuffer.setLevel(l);
            // lock signal and unlock so that the frame would know that it shouldn't wait anymore. and thread of login screen can finish
            this.lockscreen.lock();
            this.condition.signal();
            this.lockscreen.unlock();
        }

    }
}
