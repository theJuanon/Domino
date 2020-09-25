import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Domino extends JFrame implements ActionListener {

/*
 * 1. LAS FICHAS DEL DOMINO DEBEN MOSTRARSE AL CENTRO DE LA PANTALLA
 * 2. LA FICHAS SE PUEDEN REVOLVE O MOSTRARSE EN SU ESTADO ORIGINAL
 * 3. EL PROGRAMA DE ASIGNAR 7 FICHAS A CADA UNO DE LOS CUATRO JUGADORES
 * 4. EL JUGADOR QUE TENGA LA FICHA QUE REPRENTA LA MULA DEL 6 DEBE INICIAR EL JUEGO
 *    Y DE MANERA ALTERNA CADA JUGADOR TENDRÁ DERECHO A REALIZAR EL TIRO DE UNA FICHA.
 * 5. UN JUGADOR SÓLO PODRÁ PASAR SIN TIRAR FICHA SI NO TIENE FICHA VÁLIDA
 * 6. EL JUGADOR QUE GANA SERÁ AQUEL QUE HAYA TIRADO TODAS SUS FICHAS EN PRIMER LUGAR.
 * 7. SI NINGÚN JUGADOR PUEDE REALIZAR TIRO SE DICE QUE EL JUEGO SE CERRÓ Y EL GANADOR DEL JUEGO
 *    SERÁ AQUEL QUE TENGA MENOS PUNTOS.
 *
 */


	JPanel centro;
	Boton [] fichas, original;
	JButton btnRevolver,btnRepartirJuego,btnOriginal;
	String [] vi;
	Box jugadorNorte = Box.createHorizontalBox();
	Box jugadorSur = Box.createHorizontalBox();
	Box jugadorEste = Box.createVerticalBox();
	Box jugadorOeste = Box.createVerticalBox();

	public Domino() {
		super("Juego de domino");
		HazInterfaz();
		HazEscuchas();
	}

	private void HazInterfaz() {
		setSize(800,600);
		setLocationRelativeTo(null);
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		centro = new JPanel();
		centro.setLayout(new GridLayout(0,7));
		
		fichas = new Boton[28];
		vi     = new String[28];

		int aux = 0;

		for(int i = 0; i < 7; i++) {
		    for (int j = i; j < 7; j++){
                vi[aux] = (aux+1) + ".jpg";
                fichas[aux] = new Boton(vi[aux], i, j);
                fichas[aux].setMinimumSize(new Dimension(40, 50));
                fichas[aux].setPreferredSize(new Dimension(40, 50));
                fichas[aux].setMaximumSize(new Dimension(40, 50));
                centro.add(fichas[aux]);
                aux++;
            }
		}
        System.out.println(aux);
        for (int i = 0; i < fichas.length; i++)
            System.out.println(fichas[i].getNum1() +" "+ fichas[i].getNum2());
        original = fichas.clone();

		btnOriginal = new JButton("Original");
		centro.add(btnOriginal);
		btnRevolver = new JButton("Revolver");
		centro.add(btnRevolver);
		btnRepartirJuego = new JButton("Repartir");
		centro.add(btnRepartirJuego);
		add(centro);
		jugadorNorte.add(new JButton("Npaso"));
		jugadorSur.add(new JButton("Spaso"));
		jugadorEste.add(new JButton("Epaso"));
		jugadorOeste.add(new JButton("Opaso"));

		add(jugadorNorte,BorderLayout.NORTH);
		add(jugadorSur,BorderLayout.SOUTH);
		add(jugadorEste,BorderLayout.EAST);
		add(jugadorOeste,BorderLayout.WEST);
		
		setVisible(true);
		
	}

	private void HazEscuchas() {
		btnRevolver.addActionListener(this);
		btnOriginal.addActionListener(this);
		btnRepartirJuego.addActionListener(this);
	}
	
	public static void main(String [] a) {
		new Domino();
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if(evt.getSource() == btnOriginal) {
			// Actualizar la imagen de los botones
            centro.removeAll();
			for(int i = 0; i < fichas.length; i++) {
				//fichas[i].setIcon(Rutinas.AjustarImagen(original[i], 60, 100));
				//fichas[i].update(fichas[i].getGraphics());
                centro.add(original[i]);
			}
           // centro.update(centro.getGraphics());
            centro.add(btnOriginal);
			centro.add(btnRevolver);
			centro.add(btnRepartirJuego);
            centro.revalidate();
			centro.repaint();
            return;
		}
		if(evt.getSource() == btnRevolver) {
			for(int i = 0; i < 20; i++) {
				int p1 = new Random().nextInt(28);
				int p2 = new Random().nextInt(28);
				if(p1 == p2) {
					i--;
					continue;
				}
				Boton aux = fichas[p1];
				fichas[p1] = fichas[p2];
				fichas[p2] = aux;
				String auxI = vi[p1];
				vi[p1] = vi[p2];
				vi[p2] = auxI;
			}
			// Actualizar la imagen de los botones
			centro.removeAll();
			for (int i = 0; i < fichas.length; i++)
			    centro.add(fichas[i]);
			centro.add(btnOriginal);
			centro.add(btnRevolver);
			centro.add(btnRepartirJuego);
			centro.revalidate();
			centro.repaint();
			return;
		}
		if (evt.getSource() == btnRepartirJuego){
		    centro.removeAll();
		    for (int i = 0; i< fichas.length; i++)
                System.out.println(fichas[i].getNum1() +" " + fichas[i].getNum2()+" "+vi[i]);
		    for (int i = 0; i < 28; i++){
                jugadorEste.add(fichas[i]);
                fichas[i].setIcon(Rutinas.AjustarImagen(vi[i], 40, 50));
                fichas[i].setEnabled(false);
                i++;
                jugadorNorte.add(fichas[i]);
                fichas[i].setIcon(Rutinas.AjustarImagen(vi[i], 40, 50));
                fichas[i].setEnabled(false);
                i++;
                jugadorOeste.add(fichas[i]);
                fichas[i].setIcon(Rutinas.AjustarImagen(vi[i], 40, 50));
                fichas[i].setEnabled(false);
                i++;
                jugadorSur.add(fichas[i]);
                fichas[i].setIcon(Rutinas.AjustarImagen(vi[i], 40, 50));
                fichas[i].setEnabled(false);
            }
		    fichas[27].setEnabled(true);
            this.revalidate();
            this.repaint();
            return;
        }
		if (evt.getSource() == fichas[27]){
		    centro.add(fichas[27]);
        }
	}
}