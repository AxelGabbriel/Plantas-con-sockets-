package invernaderoAxel;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class IUplantas extends JFrame{

	public JPanel panel;
	public JLabel Amapola;
	public JLabel tulipan;
	public JLabel rosa;
	public JLabel Girasol;
	public JLabel captus;
	public JLabel orquidea;
	public JLabel dalias;
	public JLabel margaritas;
	public JLabel arbol;
	public JLabel sedro;
	public JLabel arbusto;
	public JLabel helecho;
	public JLabel coladecaballo;
	public JLabel briofitos;
	public JLabel hongos;
	public JTextField Ingresarplanta;
	public JTextField Claveplanta;
	public JLabel plantaIntroducido;
	public JLabel cicadas;
	public JLabel hepaticas;
	public String dbName = "Register";
	public String URL = "jdbc:postgresql://localhost:5432/ZOOLOGICO";
	public String USER = "postgres";
	public String PASSWORD = "princho4";
	PreparedStatement ps;
	ResultSet res;
	//
	public JTextField ingresoMensaje;
    public JTextArea pantallaChat;
    public JMenuItem adjuntar;
    private static ServerSocket servidor;
    private static Socket cliente;
    private static String ipServidor;// = "127.0.0.1";
    public static Cliente ventanaCliente;
    public static String usuario;
    public boolean recibir;
	



public IUplantas() {
		this.setTitle("plantas");
		this.setSize(500, 500);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		Iniciarplantaes();
		this.setLocationRelativeTo(null);

	}
public Connection getConection() {
	Connection con = null;
	try {
		
		Class.forName("org.postgresql.Driver");
		con = (Connection) DriverManager.getConnection(URL,USER,PASSWORD);
		System.out.println("conexion exitosa");
		JOptionPane.showMessageDialog(null,"conexion exitosa");
		
	}catch(Exception e){
		System.out.println(e.getMessage());
	}return con;
 }
	private void LimpiarCajas() {
		Claveplanta.setText(null);
		Ingresarplanta.setText(null);
		
	}
	public void Iniciarplantaes(){
		Colocarplantaes();
		panel.setBackground(Color.YELLOW);
		ColocarCosas();
		TodosLosplantaes();
		plantaNuevo();
		Colocarplanta();
		Boton2();
	
		BotonEliminar();
		BotonModificar();
		BotonBuscar();
		Claveplanta();
		Listaplantaes();
	}
	public void Colocarplantaes()  {
	panel = new JPanel();
	panel.setLayout(null);
	this.getContentPane().add(panel);
	}
	public void ColocarCosas() {
	JLabel plantaes = new JLabel();
	plantaes.setText("INVERNADERO");
	plantaes.setBounds(440,20,1000,50);
	plantaes.setForeground(Color.GREEN);
	plantaes.setFont(new Font("arial",1,20));
	panel.add(plantaes);
	}
	public void TodosLosplantaes() {
		JButton boton1 = new JButton();
		boton1.setText("Lista de plantas");
		boton1.setEnabled(false);
		boton1.setBounds(100, 100, 200, 40);
		
		panel.add(boton1);
		JLabel plantaNuevoRepetido = new JLabel();
		plantaNuevoRepetido.setBounds(100, 215, 100, 200);
		panel.add(plantaNuevoRepetido);
		
		ActionListener Boton01 = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
					plantaNuevoRepetido.setText(""+Ingresarplanta.getText());
					try {
					Connection con =null;
					con =  getConection();
					
					ps= con.prepareStatement("Select * FROM plantas");
					res= ps.executeQuery();
					if(res.next()) {
						JOptionPane.showMessageDialog(null, res.getString("planta"));
					}else {
						JOptionPane.showMessageDialog(null,"NO EXISTEN DATOS");
						}
					con.close();
					}catch(Exception a) {
						System.out.println(a);
					}
					}
			
		};
		boton1.addActionListener(Boton01);
	}
	public void plantaNuevo() {
		JLabel	plantaN = new JLabel();
		plantaN.setBounds(450, 60, 100, 30);
		plantaN.setText("Ingrese planta");
		panel.add(plantaN);
	}		
	public void Boton2() {
		JButton Boton02 = new JButton();
		Boton02.setBounds(640, 180, 200, 40);
		Boton02.setText("Guardar");
		
		plantaIntroducido = new JLabel();
		plantaIntroducido.setBounds(400, 200, 250, 30);
		panel.add(plantaIntroducido);
		panel.add(Boton02);
		JLabel plantaRepetido2 = new JLabel();
		plantaRepetido2.setBounds(100, 215, 100, 200);
		panel.add(plantaRepetido2);
		
		
		
		ActionListener Agregarplanta = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Connection con = null;
				try {
					con = getConection();
					ps= con.prepareStatement("INSERT INTO plantaes (planta,Id) VALUES(?,?)");
					ps.setString(1,Ingresarplanta.getText());
					ps.setString(2, Claveplanta.getText());
					int res= ps.executeUpdate();
					if(res>0) {
						JOptionPane.showMessageDialog(null, "planta Guardado");
						LimpiarCajas();
					}else {
						JOptionPane.showMessageDialog(null, "Error al Guardar");
						LimpiarCajas();
					}
					con.close();
				}catch(Exception c) {
					System.err.print(c);
					
				}
				
			}
			
		};
		Boton02.addActionListener(Agregarplanta);
	}
	public void Colocarplanta() {
		Ingresarplanta = new JTextField();
		Ingresarplanta.setBounds(400, 100, 200, 40);
		panel.add(Ingresarplanta);
		
	}


	public void BotonEliminar() {
		JButton BotonEliminar = new JButton();
		BotonEliminar.setBounds(850, 180, 200, 40);
		BotonEliminar.setText("Eliminar");
		panel.add(BotonEliminar);
		
		ActionListener BotonEliminarAcc = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Connection con = null;
				try {
					con = getConection();
					ps= con.prepareStatement("DELETE FROM plantaes WHERE Id=?");
					ps.setString(1,Claveplanta.getText());
					int res= ps.executeUpdate();
					if(res>0) {
						JOptionPane.showMessageDialog(null, "planta Borrado");
						LimpiarCajas();
					}else {
						JOptionPane.showMessageDialog(null, "Error Al Borrar");
						LimpiarCajas();
					}
					con.close();
				}catch(Exception c) {
					System.err.print(c);
					
				}
				
			}
			
		};
		BotonEliminar.addActionListener(BotonEliminarAcc);
		
	}
	public void BotonModificar() {
		JButton BotonModificar = new JButton();
		BotonModificar.setBounds(210, 350, 200, 40);
		BotonModificar.setText("Modificar");
		panel.add(BotonModificar);
		ActionListener BotonModificarAcc = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Connection con = null;
				try {
					con = getConection();
					ps= con.prepareStatement("UPDATE plantaes SET planta=? WHERE Id=?");
					ps.setString(1,Ingresarplanta.getText());
					ps.setString(2,Claveplanta.getText());
					int res= ps.executeUpdate();
					if(res>0) {
						JOptionPane.showMessageDialog(null, "planta Modificado");
						LimpiarCajas();
					}else {
						JOptionPane.showMessageDialog(null, "Error al Modificar");
						LimpiarCajas();
					}
					con.close();
				}catch(Exception c) {
					System.err.print(c);
					
				}
				
			}
			
		};
		BotonModificar.addActionListener(BotonModificarAcc);
	}
	public void BotonBuscar() {
		JButton BotonBuscar = new JButton();
		BotonBuscar.setBounds(10, 350, 200, 40);
		BotonBuscar.setText("Buscar");
		panel.add(BotonBuscar);
		
		ActionListener BotonBuscarAcc = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection con = null;
				try {
					con=getConection();
					ps= con.prepareStatement("Select * From plantaes WHERE Id = ?");
					ps.setString(1,Claveplanta.getText() );
					res = ps.executeQuery();
					if(res.next()) {
						Claveplanta.setText(res.getString("id"));
						Ingresarplanta.setText(res.getString("planta"));
					}else {
						JOptionPane.showMessageDialog(null, "No existe ese planta en este zoologico");
					}
				}catch(Exception d){
					System.err.print(d);
				}
				
				
			}
			
		};
		BotonBuscar.addActionListener(BotonBuscarAcc);
		
	}
	public void Claveplanta() {
		Claveplanta = new JTextField();
		JLabel ID = new JLabel();
		Claveplanta.setBounds(400,175, 200, 40);
		panel.add(Claveplanta);
		ID.setBounds(500, 110, 100, 100);
		ID.setText("ID");
		panel.add(ID);
	}

	public void Listaplantaes() {
		JLabel Lista = new JLabel();
		Lista.setBounds(175, 105, 100, 100);
		Lista.setText("ID  /  plantas ");
		panel.add(Lista);
		Amapola = new JLabel();
		Amapola.setBounds(100, 110, 100, 200);
		Amapola.setText("1.Amapola");
		tulipan = new JLabel();
		tulipan.setBounds(100, 125, 100, 200);
		tulipan.setText("2.tulipan");
		rosa = new JLabel();
		rosa.setBounds(100, 140, 100, 200);
		rosa.setText("3.rosa");
		Girasol = new JLabel();
		Girasol.setBounds(100, 155, 100, 200);
		Girasol.setText("4.Girasol");
		captus = new JLabel();
		captus.setBounds(100, 170, 100, 200);
		captus.setText("5.captus");
		orquidea = new JLabel();
		orquidea.setText("6.orquidea");
		orquidea.setBounds(100, 185, 100, 200);
		dalias = new JLabel();
		dalias.setText("7.dalias");
		dalias.setBounds(100, 200, 100, 200);
		margaritas = new JLabel();
		margaritas.setText("8.margaritas");
		margaritas.setBounds(250, 110, 100, 200);
		arbol = new JLabel();
		arbol.setText("9.arbol");
		arbol.setBounds(250, 125, 100, 200);
		sedro = new JLabel();
		sedro.setText("10.sedro");
		sedro.setBounds(250, 140, 100, 200);
		arbusto = new JLabel();
		arbusto.setText("11.arbusto");
		arbusto.setBounds(250, 155, 100, 200);
		helecho = new JLabel();
		helecho.setText("12.helecho");
		helecho.setBounds(250, 170, 100, 200);
		coladecaballo = new JLabel();
		coladecaballo.setText("13.coladecaballo");
		coladecaballo.setBounds(250, 185, 100, 200);
		briofitos = new JLabel();
		briofitos.setText("14.briofitos");
		briofitos.setBounds(250, 200, 100, 200);
		hongos = new JLabel();
		hongos.setText("15.hongos");
		hongos.setBounds(250, 215, 100, 200);
		panel.add(hongos);
		panel.add(tulipan);
		panel.add(briofitos);
		panel.add(coladecaballo);
		panel.add(helecho);
		panel.add(arbusto);
		panel.add(sedro);
		panel.add(arbol);
		panel.add(margaritas);
		panel.add(dalias);
		panel.add(rosa);
		panel.add(captus);
		panel.add(tulipan);
		panel.add(Amapola);
		panel.add(Girasol);
		panel.add(orquidea);
	
	}	
}
