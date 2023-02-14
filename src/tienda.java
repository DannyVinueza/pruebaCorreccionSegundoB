import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class tienda {
    PreparedStatement ps;
    private JPanel ptienda;
    private JLabel text1;
    private JTextField id;
    private JLabel text2;
    private JLabel text3;
    private JComboBox clasificacion;
    private JTextField nombre;
    private JLabel text4;
    private JTextField cantidad;
    private JLabel text5;
    private JTextField precio;
    private JLabel text6;
    private JComboBox seccion;
    private JButton crear;
    private JButton buscar;
    private JButton actualizar;
    private JButton borrar;
    private JLabel salida;


    public tienda() {//Boton Buscar

        Connection con;
        try{
            con = getConection();
            String queryC = "select * from clasificacion;";
            Statement s = con.createStatement();
            ResultSet cs = s.executeQuery(queryC);
            clasificacion.addItem("Legumbres");
            clasificacion.addItem("Verduras");
            clasificacion.addItem("Frutas");

            seccion.addItem("L1");
            seccion.addItem("L2");
            seccion.addItem("L3");
        }catch(HeadlessException | SQLException f){
            System.err.println(f);
        }

        buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection con;
                try{
                    String idp;
                    idp = id.getText();
                    System.out.println(id);
                    System.out.println("\n");
                    con = getConection();
                    String query = "select * from productos;";// WHERE Id = " + cedulaIn + ";";
                    Statement s = con.createStatement();
                    ResultSet rs = s.executeQuery(query);
                    System.out.println(rs);

                    while(rs.next()){
                        if(idp.equals(rs.getString(1))){
                            clasificacion.getSelectedItem();
                            nombre.setText(rs.getString(3));
                            cantidad.setText(rs.getString(4));
                            precio.setText(rs.getString(5));
                            seccion.getSelectedItem();

                            System.out.println(rs.getString(1) + " " +
                                    rs.getString(2) + " " +
                                    rs.getString(3) + " " +
                                    rs.getString(4) + " " +
                                    rs.getString(5) + " " +
                                    rs.getString(6));
                            /*nombre.setEnabled(true);
                            celular.setEnabled(true);
                            email.setEnabled(true);
                            actualizarB.setEnabled(true);*/
                            //break;
                        }else{
                            salida.setText("Producto no encontrado");
                            break;
                        }
                    }
                    con.close();
                }catch(HeadlessException | SQLException f){
                    System.err.println(f);
                }
            }
        });
        actualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection con;

                try{
                    con = getConection();
                    ps = con.prepareStatement("UPDATE productos SET idproductos = ?, clasificacion = ?, nombre = ?, cantidad = ?, precio = ?, seccion = ? WHERE id ="+ id.getText() );
                    //ps.setString(1, id.getText());
                    ps.setString(2, (String)(clasificacion.getSelectedItem()));
                    ps.setString(3, nombre.getText());
                    ps.setString(4, cantidad.getText());
                    ps.setString(5, precio.getText());
                    ps.setString(6, (String)(seccion.getSelectedItem()));
                    System.out.println(ps);//Imprime para ver los datos ingresados por consola
                    int res = ps.executeUpdate();
                    if(res > 0){
                        JOptionPane.showMessageDialog(null, "Actualizacion exitosa ");
                    }else{
                        JOptionPane.showMessageDialog(null,"Error al actualizar");
                    }
                    con.close();
                }catch(HeadlessException | SQLException f){
                    System.err.println(f);
                }
            }
        });
        crear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection con;
                try{

                    con = getConection();
                    ps = con.prepareStatement("INSERT INTO registro (idproductos, clasificacion, nombre, cantidad, precio, seccion) VALUES (?, ?, ?, ?, ?, ?);");
                    ps.setString(1, id.getText());
                    ps.setString(2, (String)clasificacion.getSelectedItem());
                    ps.setString(3, nombre.getText());
                    ps.setString(4, cantidad.getText());
                    ps.setString(5,precio.getText());
                    ps.setString(6, (String)seccion.getSelectedItem());
                    System.out.println(ps);//Imprime para ver los datos ingresados por consola

                    int res = ps.executeUpdate();
                    if(res > 0){
                        JOptionPane.showMessageDialog(null, "Registro Guardado satisfactoriament");
                    }else{
                        JOptionPane.showMessageDialog(null,"Error al guardar el registro");
                    }
                    con.close();
                }catch(HeadlessException | SQLException f){
                    System.err.println(f);
                }
            }
        });
    }

    public static Connection getConection(){
        Connection con = null;
        String base = "mercado";
        String url = "jdbc:mysql://localhost/" + base;
        String user = "root";
        String password = "1234";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url,user,password);
        }catch(ClassNotFoundException | SQLException es){
            System.err.println(es);
        }
        return con;

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Danny Vinueza Tienda de Abarrotes");
        frame.setContentPane(new tienda().ptienda);
        frame.setSize(1000,1000);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
