import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
class Product {
int id;
String name;
double price;
Product(int id, String name, double price) {
this.id = id;
this.name = name;
this.price = price;
}
public String toString() {
return id + ". " + name + " - Rs." + price;
}}
class Cart {
Product[] items;
int count;
Cart(int size) {
items = new Product[size];
count = 0;
}
void addProduct(Product p) {
if (count < items.length) {
items[count++] = p; // actually add the product to the array
} else {
JOptionPane.showMessageDialog(null, "Cart is full!");
}
}
void removeProduct(Product p) {
for (int i = 0; i < count; i++) {
if (items[i].id == p.id) {
for (int j = i; j < count - 1; j++) {
items[j] = items[j + 1];
}
count--;
break;
}
}
}
String displayCart(){
if (count == 0) {
return "Your cart is empty!";
}
String result = "\n--- Your Cart ---\n";
double total = 0;
for (int i = 0; i < count; i++) {
result += items[i] + "\n";
total += items[i].price;
}
result += "Total: Rs." + total;
return result;
}}
public class ShoppingCartArrayGUI extends JFrame {
private DefaultListModel productModel; 
private JList productList;           
private JTextArea cartArea;
private Cart cart;
public ShoppingCartArrayGUI() {
setTitle("Online Shopping Cart (Array)");
setSize(600, 400);
setDefaultCloseOperation(EXIT_ON_CLOSE);
setLayout(new BorderLayout());
cart = new Cart(10);  
productModel = new DefaultListModel();
productModel.addElement(new Product(1, "Laptop", 55000));
productModel.addElement(new Product(2, "Headphones", 1500));
productModel.addElement(new Product(3, "Keyboard", 1200));
productModel.addElement(new Product(4, "Mouse", 800));
productModel.addElement(new Product(5, "Smartphone", 30000));
productList = new JList(productModel); // no <Product>
productList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
add(new JScrollPane(productList), BorderLayout.WEST);
cartArea = new JTextArea();
cartArea.setEditable(false);
add(new JScrollPane(cartArea), BorderLayout.CENTER);
JPanel buttonPanel = new JPanel();
JButton addBtn = new JButton("Add to Cart");
JButton removeBtn = new JButton("Remove from Cart");
JButton viewBtn = new JButton("View Cart");
buttonPanel.add(addBtn);
buttonPanel.add(removeBtn);
buttonPanel.add(viewBtn);
add(buttonPanel, BorderLayout.SOUTH);
addBtn.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
Object selected = productList.getSelectedValue(); // Object instead of Product
if (selected instanceof Product) {
Product p = (Product) selected;
cart.addProduct(p);
JOptionPane.showMessageDialog(null, p.name + " added to cart!");
} else {
JOptionPane.showMessageDialog(null, "Please select a product!");
}
}
});
removeBtn.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
Object selected = productList.getSelectedValue();
if (selected instanceof Product) {
Product p = (Product) selected;
cart.removeProduct(p);
JOptionPane.showMessageDialog(null, p.name + " removed from cart!");
} else {
JOptionPane.showMessageDialog(null, "Product not in cart!");
}
}
});
viewBtn.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e){
cartArea.setText(cart.displayCart());
}
});
}
public static void main(String[] args) {
SwingUtilities.invokeLater(new Runnable() {
public void run() {
new ShoppingCartArrayGUI().setVisible(true);
}});}}