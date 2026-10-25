import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class canteen extends JFrame implements ActionListener{
    JButton user, admin;

    public canteen(){
        this.setTitle("EasySnack-Canteen Token Generator");
        setSize(500,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        JPanel topPanel=new JPanel();
        topPanel.setBackground(Color.decode("#2C1810"));
        topPanel.setPreferredSize(new Dimension(500, 70));
        topPanel.setLayout(new GridLayout(2,1));

        JLabel title=new JLabel("EasySnack",JLabel.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(title);

        JLabel subtitle=new JLabel("Canteen Token Generator",JLabel.CENTER);
        subtitle.setForeground(Color.LIGHT_GRAY);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 12));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(subtitle);

        JPanel centrePanel = new JPanel();
        centrePanel.setBackground(Color.decode("#896f52"));
        centrePanel.setLayout(new GridLayout(3, 1));

        JPanel topSpacer = new JPanel();
        topSpacer.setBackground(Color.decode("#896f52"));

        JLabel intro = new JLabel("Welcome to EasySnack",JLabel.CENTER);
        intro.setFont(new Font("Arial", Font.BOLD, 20));
        intro.setForeground(Color.white);
        
        user = new JButton("LOGIN AS USER");
        admin = new JButton("LOGIN AS ADMIN");
        
        Dimension buttonSize = new Dimension(220, 40);
        user.setPreferredSize(buttonSize);
        admin.setPreferredSize(buttonSize);

        JPanel userPanel = new JPanel();
        userPanel.setBackground(Color.decode("#896f52"));
        userPanel.add(user);

        JPanel adminPanel = new JPanel();
        adminPanel.setBackground(Color.decode("#896f52"));
        adminPanel.add(admin);
        
        JPanel contentBox = new JPanel();
        contentBox.setBackground(Color.decode("#896f52"));
        contentBox.setLayout(new GridLayout(3, 1, 0, 15));
        contentBox.add(intro);
        contentBox.add(userPanel);
        contentBox.add(adminPanel);

        JPanel bottomSpacer = new JPanel();
        bottomSpacer.setBackground(Color.decode("#896f52"));
        
        centrePanel.add(topSpacer);
        centrePanel.add(contentBox);
        centrePanel.add(bottomSpacer);

        JLabel bottomPanel=new JLabel();
        bottomPanel.setBackground(Color.decode("#2C1810"));
        
        user.addActionListener(this);
        admin.addActionListener(this);

        setLayout(new BorderLayout());
        add(topPanel,BorderLayout.NORTH);
        add(centrePanel,BorderLayout.CENTER);
        add(bottomPanel,BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource()==user){
            user_login ulogin=new user_login();
            ulogin.setExtendedState(this.getExtendedState());
            ulogin.setBounds(this.getBounds());
            this.setVisible(false);
        }
        else{
            admin_login alogin=new admin_login();
            alogin.setExtendedState(this.getExtendedState());
            alogin.setBounds(this.getBounds());
            this.setVisible(false);
        }
    }

    public static void main(String[] args) {
        new canteen();
    }
}