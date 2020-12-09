package gameClient;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LoginFrame extends JFrame implements Runnable, ActionListener
{
    private Dimension screen_size;
    private double fractionX;
    private double fractionY;
    private JButton login;
    private JTextArea textArea_id; // take user id
    private JTextArea textArea_level; // take the scenario
    private JLabel label_id;
    private JLabel label_level;
    private JLabel messagebox;
    private int login_framex;
    private int login_framey;
    private Ex2.SharedLevelBuffer sharedLevelBuffer;
    private Lock lockscreen;
    public Condition condition;

    public LoginFrame(double X,double Y,Ex2.SharedLevelBuffer sharedLevelBuffer) {
        super();
        this.sharedLevelBuffer = sharedLevelBuffer;
        this.lockscreen = new ReentrantLock();
        this.condition = this.lockscreen.newCondition();
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fractionX = X;
        fractionY = Y;
        screen_size = Toolkit.getDefaultToolkit().getScreenSize();
        login_framex = (int) (fractionX * screen_size.width);
        login_framey = (int) (fractionY * screen_size.height);
        setBounds(0, 0, login_framex, login_framey);
        Font f =  new Font("Arial",Font.PLAIN,24);
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
        textArea_id = new JTextArea();
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
        //
        messagebox = new JLabel();
        messagebox.setBounds((int)(login_framex*0.25),(int)(login_framey*0.5)+31,(int)(login_framex*0.5),30);
        messagebox.setFont(f);
        messagebox.setForeground(Color.RED);

        add(textArea_id);
        add(textArea_level);
        add(label_id);
        add(label_level);
        add(login);
        add(messagebox);


    }

    @Override
    public void run()
    {
        setVisible(true);
        this.lockscreen.lock();
        try {
            this.condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            this.lockscreen.unlock();
            setVisible(false);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == this.login)
        {
            String id = this.textArea_id.getText();
            String level = this.textArea_level.getText();
            int l;
            if (id.equals("")) // test case id is empty
            {
                this.messagebox.setText("Can't login with no id...");
                return;
            }else
            {
                this.messagebox.setText("");
            }
            try {
                l= Integer.parseInt(level);
            }
            catch (NumberFormatException exception)
            {
                this.messagebox.setFont(new Font("Arial",Font.PLAIN,20));
                this.messagebox.setText("The level should be a number between 0 to 23");
                return;
            }
            this.messagebox.setFont(new Font("Arial",Font.PLAIN,24));
            this.messagebox.setText("");
            this.sharedLevelBuffer.setId(id);
            this.sharedLevelBuffer.setLevel(l);
            this.lockscreen.lock();
            this.condition.signal();
            this.lockscreen.unlock();
        }

    }
}
