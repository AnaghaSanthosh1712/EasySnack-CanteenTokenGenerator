import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

public class user_activity extends JFrame implements ActionListener{
   static int token=101;
   JCheckBox[] list=new JCheckBox[20];
   JComboBox[] dropdown=new JComboBox[20];
   double[] amount=new double[20];
   int count=0; 
   String outofstock;
   JPanel food = new JPanel();
   JPanel drink = new JPanel();
   JButton Back; 

   public user_activity(){
    this.setTitle("Canteen Menu and Tocken Generator");
    setSize(500,500);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    JPanel Top=new JPanel();
    Top.setBackground(Color.decode("#2C1810"));
    Top.setPreferredSize(new Dimension(500,70));
    Top.setLayout(new GridLayout(2,1));

    JLabel head=new JLabel("CANTEEN MENU",JLabel.CENTER);
    head.setFont(new Font("Arial", Font.BOLD, 22));
    head.setAlignmentX(Component.CENTER_ALIGNMENT);
    head.setForeground(Color.white);
    Top.add(head);

    JLabel subhead=new JLabel("Choose Your Order",JLabel.CENTER);
    subhead.setFont(new Font("Arial", Font.BOLD, 20));
    subhead.setAlignmentX(Component.CENTER_ALIGNMENT);
    subhead.setForeground(Color.gray);
    Top.add(subhead);
    add(Top,BorderLayout.NORTH);

    JPanel centre=new JPanel();
    centre.setLayout(new BoxLayout(centre, BoxLayout.Y_AXIS));
    centre.setBackground(Color.decode("#FDF6E3"));

    food.setLayout(new BoxLayout(food,BoxLayout.Y_AXIS));
    food.setBackground(Color.decode("#FDF6E3"));
    JLabel items=new JLabel("FOOD ITEMS");
    items.setForeground(Color.decode("#2C1810"));
    items.setFont(new Font("Arial",Font.BOLD,20));
    items.setAlignmentX(Component.CENTER_ALIGNMENT);
    food.add(items);

    drink.setLayout(new BoxLayout(drink,BoxLayout.Y_AXIS));
    drink.setBackground(Color.decode("#FDF6E3"));
    JLabel juice=new JLabel("DRINKS");
    juice.setForeground(Color.decode("#2C1810"));
    juice.setFont(new Font("Arial",Font.BOLD,20));
    juice.setAlignmentX(Component.CENTER_ALIGNMENT);
    drink.add(juice);

    centre.add(food);
    centre.add(drink);
    add(centre,BorderLayout.CENTER);

    menu();

    JPanel bottom=new JPanel();
    bottom.setBackground(Color.decode("#2C1810"));
    JButton generate=new JButton("GENERATE TOKEN");
    generate.addActionListener(this);
    bottom.add(generate);
    Back=new JButton("BACK");
    Back.addActionListener(this);
    bottom.add(Back);
    add(bottom,BorderLayout.SOUTH);

    setVisible(true);
   } 

   public void menu(){
        String url="jdbc:mysql://localhost:3306/canteen_db";
        String user="your_username";
        String pass="your-password";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection(url,user,pass);
            Statement stm=con.createStatement();
            ResultSet rs=stm.executeQuery("SELECT * FROM menu WHERE stock > 0");

            while(rs.next()){
                String itemName =rs.getString("item_name");
                double price=rs.getDouble("price");
                int stock=rs.getString("stock")!=null?rs.getInt("stock"):0;
                String Category=rs.getString("Category");

                if(stock<=0){
                    continue;
                }

                JPanel row=new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 2));
                row.setBackground(Color.decode("#FDF6E3"));

                JCheckBox check=new JCheckBox(itemName+"-₹"+price);
                check.setBackground(Color.decode("#FDF6E3"));

                String[] quantities = new String[stock];
                for (int i = 0; i <stock; i++) {
                    quantities[i] = String.valueOf(i);
                }

                JComboBox value = new JComboBox(quantities);
                value.setVisible(false);

                check.addItemListener(e -> {
                    if(e.getStateChange() == ItemEvent.SELECTED){
                    value.setVisible(true);}
                });

                JLabel qty=new JLabel();
                row.add(check);
                row.add(qty);
                row.add(value);

                if (Category != null && Category.equals("drink")) {
                    drink.add(row);
            }
            else{
                    food.add(row);
            }

            list[count] = check;
            dropdown[count] = value;
            amount[count] = price;
            count++;
        }
        con.close();
   }
   catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() ==Back) {
            canteen home = new canteen();
            home.setExtendedState(this.getExtendedState());
            home.setBounds(this.getBounds());
            this.setVisible(false);
            return;
        }
       String summary="  Ordered Items   \n";
        double grandTotal = 0.0;
        boolean selection=false;
        
        for (int i = 0; i < count; i++) {
            if (list[i].isSelected()) {
                int qty = Integer.parseInt((String)dropdown[i].getSelectedItem());
                
                if(qty==0){
                    selection=true;
                    outofstock=list[i].getText();
                }
                else{
                    double itemTotal = amount[i] * qty;
                    grandTotal += itemTotal;
                    summary += "Item: " + list[i].getText() + " | Qty: " + qty + " | Total: ₹" + itemTotal + "\n";
                }
            }
        }

        if (grandTotal==0) {
        JOptionPane.showMessageDialog(this, "Please select at least one item!", "Warning", JOptionPane.WARNING_MESSAGE);
        return;
    }


        else{
        
    try {
        Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/canteen_db",
        "root",
        "admin123"
    );

    for (int i = 0; i < count; i++) {
        if (list[i].isSelected()) {
            int qty = Integer.parseInt(
                (String) dropdown[i].getSelectedItem()
            );

            PreparedStatement ps = con.prepareStatement(
                "UPDATE menu SET stock = stock - ? WHERE item_name = ?"
            );

            ps.setInt(1, qty);
            ps.setString(2,
                list[i].getText().split("-₹")[0]);

            ps.executeUpdate();
            ps.close();
        }
    }

    con.close();

} 
    catch (Exception ex) {
             ex.printStackTrace();
    }

        JOptionPane.showMessageDialog(this,"Token Generated Successfully! Token ID: " + token+"\n"
                                        +summary+"\n"+"GRAND TOTAL: ₹" + grandTotal + "\n","TOKEN & BILL",JOptionPane.INFORMATION_MESSAGE);

        token++;
        this.setVisible(false);
        user_activity uact=new user_activity();
        uact.setExtendedState(this.getExtendedState());
        uact.setBounds(this.getBounds());

    }}

    public static void main(String[] args) {
        new user_activity();
    }
}






