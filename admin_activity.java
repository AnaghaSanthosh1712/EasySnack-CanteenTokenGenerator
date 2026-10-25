import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class admin_activity extends JFrame implements ActionListener {
    JTextField ItemName, Price, Stock, txtDeleteName;
    JComboBox<String> Category;
    JButton Save, Delete, Back;

    public admin_activity() {
        setTitle("Canteen Admin");
        setSize(500,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.decode("#2C1810"));
        JLabel title = new JLabel("ADMIN PANEL");
        title.setForeground(Color.white);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        topPanel.add(title);
        add(topPanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBackground(Color.decode("#FDF6E3"));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        formPanel.add(new JLabel("Item Name:"));
        ItemName = new JTextField();
        formPanel.add(ItemName);

        formPanel.add(new JLabel("Price (₹):"));
        Price = new JTextField();
        formPanel.add(Price);

        formPanel.add(new JLabel("Stock Quantity:"));
        Stock = new JTextField();
        formPanel.add(Stock);

        formPanel.add(new JLabel("Category:"));
        Category = new JComboBox<>(new String[]{"food", "drink"});
        formPanel.add(Category);

        formPanel.add(new JLabel("Item to Delete:"));
        txtDeleteName = new JTextField();
        formPanel.add(txtDeleteName);

        add(formPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.decode("#2C1810"));
        
        Save = new JButton("Save / Update");
        Save.addActionListener(this);
        bottomPanel.add(Save);

        Delete = new JButton("Delete Item");
        Delete.addActionListener(this);
        bottomPanel.add(Delete);

        Back = new JButton("Back");
        Back.addActionListener(this);
        bottomPanel.add(Back);

        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Back) {
            canteen home = new canteen();
            home.setExtendedState(this.getExtendedState());
            home.setBounds(this.getBounds());
            this.setVisible(false);
            return ;
        }

        if (e.getSource() == Delete) {
            String deleteName = txtDeleteName.getText().trim();
            if (deleteName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter item name to delete!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                delete_item(deleteName);
            }
            return;
        }

        String itemName = ItemName.getText().trim();
        String priceStr = Price.getText().trim();
        String stockStr = Stock.getText().trim();
        String category = (String) Category.getSelectedItem();

        if (itemName.isEmpty() || priceStr.isEmpty() || stockStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            update_menu(itemName, priceStr, stockStr, category);
        }
    }

    public void update_menu(String itemName, String priceStr, String stockStr, String category) {
        String url = "jdbc:mysql://localhost:3306/canteen_db";
        String user = "your_username";
        String pass = "your_password";

        try {
            double price = Double.parseDouble(priceStr);
            int stock = Integer.parseInt(stockStr);
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, pass);

            String query = "INSERT INTO menu (item_name, price, stock, Category) VALUES (?, ?, ?, ?) " +
                           "ON DUPLICATE KEY UPDATE price = VALUES(price), stock = stock + VALUES(stock), Category = VALUES(Category)";
            
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, itemName);
            pst.setDouble(2, price);
            pst.setInt(3, stock);
            pst.setString(4, category);

            pst.executeUpdate();
            con.close();

            JOptionPane.showMessageDialog(this, "Item saved!");

            ItemName.setText("");
            Price.setText("");
            Stock.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Price must be a number and Stock must be an integer!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void delete_item(String itemName) {
        String url = "jdbc:mysql://localhost:3306/canteen_db";
        String user = "root";
        String pass = "admin123";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, pass);

            String query = "DELETE FROM menu WHERE item_name = ?";
            
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, itemName);

            int rowsAffected = pst.executeUpdate();
            con.close();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Item deleted successfully!");
                txtDeleteName.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Item not found in database!", "Warning", JOptionPane.WARNING_MESSAGE);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new admin_activity();
    }
}