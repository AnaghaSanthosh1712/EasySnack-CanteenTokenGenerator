import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class admin_login extends JFrame implements ActionListener {
    JLabel admin_id, name;
    JTextField t1, t2;
    JButton login, back;

    public admin_login() {
        setTitle("EasySnack - Admin Login");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.decode("#2C1810"));
        topPanel.setPreferredSize(new Dimension(500, 60));
        topPanel.setLayout(new GridLayout(1, 1)); 

        JLabel title = new JLabel("Admin Login", JLabel.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(title);
        add(topPanel, BorderLayout.NORTH);

        JPanel centrepanel = new JPanel();
        centrepanel.setBackground(Color.decode("#FDF6E3"));
        centrepanel.setLayout(new GridLayout(3, 1));

        JPanel topSpacer = new JPanel();
        topSpacer.setBackground(Color.decode("#FDF6E3"));

        JPanel contentBox = new JPanel();
        contentBox.setBackground(Color.decode("#FDF6E3"));
        contentBox.setLayout(new GridLayout(5, 1, 0, 8));

        JLabel name = new JLabel("NAME");
        name.setFont(new Font("Arial", Font.BOLD, 13));
        name.setForeground(Color.decode("#2C1810"));
        t1 = new JTextField();

        JLabel adm_no = new JLabel("ADMIN ID");
        adm_no.setFont(new Font("Arial", Font.BOLD, 13));
        adm_no.setForeground(Color.decode("#2C1810"));
        t2 = new JTextField();

        login = new JButton("LOGIN");
        back = new JButton("GO BACK");

        login.addActionListener(this);
        back.addActionListener(this);

        contentBox.add(name);
        contentBox.add(t1);
        contentBox.add(adm_no);
        contentBox.add(t2);
        contentBox.add(login);
        contentBox.add(back);

        JPanel bottomspacer = new JPanel();
        bottomspacer.setBackground(Color.decode("#FDF6E3"));

        centrepanel.add(topSpacer);
        centrepanel.add(contentBox);
        centrepanel.add(bottomspacer);
        add(centrepanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == login) {
            admin_activity activity = new admin_activity();
            activity.setExtendedState(this.getExtendedState());
            activity.setBounds(this.getBounds());
            this.dispose();
        } else {
            canteen home = new canteen();
            home.setExtendedState(this.getExtendedState());
            home.setBounds(this.getBounds());
            this.dispose();
        }
    }

    public static void main(String[] args) {
        new admin_login();
    }
}