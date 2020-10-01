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


	JPanel centro, botones;
	Boton [] fichas, original, jugadas, norte, sur, este, oeste;
	Boton aux;
	JButton btnRevolver,btnRepartirJuego,btnOriginal, nPaso, sPaso, ePaso, oPaso;
	String [] vi;
	Box jugadorNorte = Box.createHorizontalBox();
	Box jugadorSur = Box.createHorizontalBox();
	Box jugadorEste = Box.createVerticalBox();
	Box jugadorOeste = Box.createVerticalBox();
	int sub = 0;

	public Domino() {
		super("Juego de domino");
		HazInterfaz();
		HazEscuchas();
	}

	private void HazInterfaz() {
		setSize(800,700);
		setLocationRelativeTo(null);
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		centro = new JPanel();
		centro.setLayout(new GridLayout(0,7, 5, 5));
		
		fichas = new Boton[28];
		vi     = new String[28];
		jugadas = new Boton[28];
		int aux = 0;

		for(int i = 0; i < 7; i++) {
		    for (int j = i; j < 7; j++){
                vi[aux] = (aux+1) + ".jpg";
                fichas[aux] = new Boton(vi[aux], i, j);
                fichas[aux].setMinimumSize(new Dimension(50, 70));
                fichas[aux].setPreferredSize(new Dimension(50, 70));
                fichas[aux].setMaximumSize(new Dimension(50, 70));
                centro.add(fichas[aux]);
                aux++;
            }
		}

        original = fichas.clone();

        botones = new JPanel();
        botones.setLayout(new FlowLayout());
		btnOriginal = new JButton("Original");
		botones.add(btnOriginal);
		btnRevolver = new JButton("Revolver");
		botones.add(btnRevolver);
		btnRepartirJuego = new JButton("Repartir");
		botones.add(btnRepartirJuego);

		nPaso = new JButton("Paso");
		sPaso = new JButton("Paso");
		ePaso = new JButton("Paso");
		oPaso = new JButton("Paso");
		add(centro);
		add(botones, BorderLayout.SOUTH);

		setVisible(true);
		
	}

	private void HazEscuchas() {
		btnRevolver.addActionListener(this);
		btnOriginal.addActionListener(this);
		btnRepartirJuego.addActionListener(this);
		nPaso.addActionListener(this);
		sPaso.addActionListener(this);
		ePaso.addActionListener(this);
		oPaso.addActionListener(this);
	}
	
	public static void main(String [] a) {
		new Domino();
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if(evt.getSource() == btnOriginal) {
            centro.removeAll();
			for(int i = 0; i < fichas.length; i++) {
                centro.add(original[i]);
			}
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
			centro.removeAll();
			for (int i = 0; i < fichas.length; i++)
			    centro.add(fichas[i]);
			centro.revalidate();
			centro.repaint();
			return;
		}
		if (evt.getSource() == btnRepartirJuego){
		    centro.removeAll();
			this.remove(botones);
			centro.setLayout(new FlowLayout());
			add(jugadorNorte,BorderLayout.NORTH);
			add(jugadorSur,BorderLayout.SOUTH);
			add(jugadorEste,BorderLayout.EAST);
			add(jugadorOeste,BorderLayout.WEST);
			int v = 0;
		    for (int i = 0; i < 28; i++){
				jugadorNorte.add(fichas[i]);
				fichas[i].setIcon(Rutinas.AjustarImagen(vi[i], 40, 50));
				fichas[i].setEnabled(false);
				//norte[v] = fichas[i];
				i++;
				jugadorSur.add(fichas[i]);
				fichas[i].setIcon(Rutinas.AjustarImagen(vi[i], 40, 50));
				fichas[i].setEnabled(false);
				//sur[v] = fichas[i];
				i++;
				jugadorEste.add(fichas[i]);
                fichas[i].setIcon(Rutinas.AjustarImagen(vi[i], 40, 50));
				fichas[i].setEnabled(false);
				//este[v] = fichas[i];
				i++;
                jugadorOeste.add(fichas[i]);
                fichas[i].setIcon(Rutinas.AjustarImagen(vi[i], 40, 50));
				fichas[i].setEnabled(false);
				//oeste[v] = fichas[i];
				v++;
			}

			for(int i=0 ; i<fichas.length ; i++) {
				if (fichas[i].getNum1() == 6 && fichas[i].getNum2() == 6){
					fichas[i].setEnabled(true);
					aux = fichas[i];
					break;
				}
			}
			jugadorNorte.add(nPaso);
			jugadorSur.add(sPaso);
			jugadorEste.add(ePaso);
			jugadorOeste.add(oPaso);
            for (int i = 0; i<fichas.length; i++)
                fichas[i].addActionListener(this);

            this.revalidate();
            this.repaint();
            return;
        }

		for(int i=0 ; i<fichas.length ; i++) {
			fichas[i].setEnabled(false);
			if (fichas[i].getNum1() == aux.getNum1() || fichas[i].getNum1() == aux.getNum2() || fichas[i].getNum2() == aux.getNum1() || fichas[i].getNum2() == aux.getNum2()) {
				fichas[i].setEnabled(true);
				if(evt.getSource()==fichas[i]) {
					jugar(i);
					this.revalidate();
					this.repaint();
				}
			}
		}

		/*
		for(int i=0 ; i<fichas.length ; i++) {
			if(evt.getSource()==fichas[i]) {
				centro.add(fichas[i]);
			}
		}

		 */
		this.revalidate();
		this.repaint();
		//fichas[Sub].setEnabled(true);
		//fichas[Sub].setDisabledIcon(Rutinas.AjustarImagen(vi[Sub],100,40));

		if (evt.getSource() == nPaso){

		}
		if (evt.getSource() == sPaso){

		}
		if (evt.getSource() == ePaso){

		}
		if (evt.getSource() == oPaso){

		}

		//Boton aux = (Boton)evt.getSource();
		/*int pos = 0;
		if (evt.getSource() == aux){
			int i;
			for (i = 0; i < 7; i++){
				switch ()
				if (aux == norte[i]){
					pos = 1;
					break;
				}
				if (aux == sur[i]){
					pos = 2;
					break;
				}
				if (aux == este[i]){
					pos = 3;
					break;
				}
				if (aux == oeste[i]){
					pos = 4;
					break;
				}
			}
			for (int j = 0; j<7; j++){
				norte[j].setEnabled(false);
				sur[j].setEnabled(false);
				este[j].setEnabled(false);
				oeste[j].setEnabled(false);
			}

		}*/
		/*Boton aux2;
		int tir = 0;
		if (tir != 1 && aux.getNum1() == 6 && aux.getNum2() == 6){
			centro.add(aux);
			aux.removeActionListener(this);
			tir = 1;
			this.revalidate();
			this.repaint();
			return;
		}
		if (aux.getNum1() == 6 || aux.getNum2() == 6){
			centro.add(aux);
			aux.removeActionListener(this);
			this.revalidate();
			this.repaint();
			return;
		}*/
		//aux.setEnabled(false);
		//aux.setDisabledIcon(aux.getIcon());
	}

	public void jugar(int pos){
		centro.add(fichas[pos]);
		fichas[pos].removeActionListener(this);
		jugadas[sub] = fichas[pos];
		sub++;
		aux = fichas[pos];
		for (int i = 0; i < sub-1; i++){
			jugadas[i].setEnabled(true);
		}
		this.update(this.getGraphics());
		this.revalidate();
		this.repaint();
	}

}