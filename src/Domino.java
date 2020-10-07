// Juan de Jesus Hernandez Angulo - 18170350
// Topicos Avanzados de Programacion - 12:00 - 13:00

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
	Boton [] fichas, original, jugadas;
	JButton btnRevolver,btnRepartirJuego,btnOriginal, btnPaso;
	String [] vi;
	Box jugadorNorte = Box.createHorizontalBox();
	Box jugadorSur = Box.createHorizontalBox();
	Box jugadorEste = Box.createVerticalBox();
	Box jugadorOeste = Box.createVerticalBox();
	int sub = 0, turno, skips = 0;
	int [] pts = new int[4];
	int [] mano = new int[4];
	Lista<Boton> mesa;
            
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

		btnPaso = new JButton("Paso");
		add(centro);
		add(botones, BorderLayout.SOUTH);

		setVisible(true);
		
	}

	private void HazEscuchas() {
		btnRevolver.addActionListener(this);
		btnOriginal.addActionListener(this);
		btnRepartirJuego.addActionListener(this);
		btnPaso.addActionListener(this);
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
			jugadorNorte.add(Box.createHorizontalStrut(200));
			add(jugadorSur,BorderLayout.SOUTH);
			jugadorSur.add(Box.createHorizontalStrut(200));
			add(jugadorEste,BorderLayout.EAST);
			add(jugadorOeste,BorderLayout.WEST);

			int ficha = 0, player = 0;
			while (ficha < 28){
				pts[player] += fichas[ficha].getNum1() + fichas[ficha].getNum2();
				fichas[ficha].setPlayer(player+1);
				fichas[ficha].setIcon(Rutinas.AjustarImagen(vi[ficha],40,50));
				fichas[ficha].setEnabled(false);
				switch (player){
					case 0:
						jugadorNorte.add(fichas[ficha]);
						player++;
						break;
					case 1:
						jugadorOeste.add(fichas[ficha]);
						player++;
						break;
					case 2:
						jugadorSur.add(fichas[ficha]);
						player++;
						break;
					case 3:
						jugadorEste.add(fichas[ficha]);
						player = 0;
						break;
				}
				ficha++;
			}

			mesa = new Lista<>();
            for(int i=0 ; i<fichas.length ; i++) {
                if (fichas[i].getNum1() == 6 && fichas[i].getNum2() == 6){
                    fichas[i].setEnabled(true);
                    turno = fichas[i].getPlayer();
                    mesa.insertarFin(fichas[i]);
                    break;
                }
            }

            for (int i = 0; i<fichas.length; i++)
                fichas[i].addActionListener(this);

            Arrays.fill(mano, 7);
            switch (turno){
                case 1:
                    jugadorNorte.add(btnPaso); break;
                case 2:
                    jugadorOeste.add(btnPaso); break;
                case 3:
                    jugadorSur.add(btnPaso); break;
                case 4:
                    jugadorEste.add(btnPaso); break;
            }
            this.revalidate();
            this.repaint();
            return;
        }

		for(int i = 0 ; i<fichas.length ; i++) {
            if(evt.getSource() == fichas[i]) {
				if (mesa.getFrente().getInfo().getNum2() == fichas[i].getNum1()
						|| mesa.getFrente().getInfo().getNum2() == fichas[i].getNum2()
						|| mesa.getFin().getInfo().getNum1() == fichas[i].getNum1()
						|| mesa.getFin().getInfo().getNum1() == fichas[i].getNum2()) {
					fichas[i].setEnabled(true);
					if(fichas[i].getPlayer() != turno)
				    	fichas[i].setEnabled(false);
					jugar(i);
                    for (int j = 0; j < fichas.length; j++){
                        if ((mesa.getFrente().getInfo().getNum2() == fichas[j].getNum1()
								|| mesa.getFrente().getInfo().getNum2() == fichas[j].getNum2()
								|| mesa.getFin().getInfo().getNum1() == fichas[j].getNum1()
								|| mesa.getFin().getInfo().getNum1() == fichas[j].getNum2())
								&& (fichas[j].getPlayer() == turno)) {
                        	fichas[j].setEnabled(true);
                        }else
                            fichas[j].setEnabled(false);
                    }
                    for (int j = 0; j < sub; j++){
                        jugadas[j].setEnabled(true);
                    }
				}
				this.revalidate();
				this.repaint();
			}
		}

		if (evt.getSource() == btnPaso){
            pasar();
            skips++;
            if (skips == 4){
                int menor = pts[0], j = 0;
                for (int i = 0; i < pts.length; i++){
                    if (pts[i] < menor){
                        menor = pts[i];
                        j = i;
                    }
                }
                JOptionPane.showMessageDialog(null,
						"Ganó judagor "+(j+1),
						"Juego Terminado",
						JOptionPane.INFORMATION_MESSAGE
				);
                System.exit(0);
            }
		}
	}

	public void jugar(int pos){
	    skips = 0;
		fichas[pos].removeActionListener(this);
		jugadas[sub] = fichas[pos];
		sub++;
		int ori;
		if (fichas[pos].getNum2() == mesa.getFin().getInfo().getNum1()){
			mesa.insertarFin(fichas[pos]);
			centro.add(mesa.getFin().getInfo());
		}else if (fichas[pos].getNum1() == mesa.getFrente().getInfo().getNum2()){
			mesa.insertarFrente(fichas[pos]);
			centro.add(mesa.getFrente().getInfo());
		}else if (fichas[pos].getNum1() == mesa.getFin().getInfo().getNum1()){
			ori = fichas[pos].getNum1();
			fichas[pos].setNum1(fichas[pos].getNum2());
			fichas[pos].setNum2(ori);
			mesa.insertarFin(fichas[pos]);
			centro.add(mesa.getFin().getInfo());
		}else if (fichas[pos].getNum2() == mesa.getFrente().getInfo().getNum2()){
			ori = fichas[pos].getNum1();
			fichas[pos].setNum1(fichas[pos].getNum2());
			fichas[pos].setNum2(ori);
			mesa.insertarFrente(fichas[pos]);
			centro.add(mesa.getFrente().getInfo());
		}

		centro.removeAll();
		Nodo aux = mesa.getFrente();
		centro.add(mesa.getFrente().getInfo());
		while(aux.getSig() != null){
			centro.add((Boton)aux.getSig().getInfo());
			aux = aux.getSig();
		}


		for (int i = 0; i < sub-1; i++){
			jugadas[i].setEnabled(true);
		}

		switch (turno){
            case 1:
                mano[0]--;
                pts[0] -= fichas[pos].getNum1() + fichas[pos].getNum2();
                turno++;
                jugadorOeste.add(btnPaso);
                break;
            case 2:
                mano[1]--;
                pts[1] -= fichas[pos].getNum1() + fichas[pos].getNum2();
                turno++;
                jugadorSur.add(btnPaso);
                break;
            case 3:
                mano[2]--;
                pts[2] -= fichas[pos].getNum1() + fichas[pos].getNum2();
                turno++;
                jugadorEste.add(btnPaso);
                break;
            case 4:
                mano[3]--;
                pts[3] -= fichas[pos].getNum1() + fichas[pos].getNum2();
                turno = 1;
                jugadorNorte.add(btnPaso);
                break;
        }
        this.revalidate();
		this.repaint();

        for (int i = 0; i<pts.length; i++){
            if (mano[i] == 0){
                JOptionPane.showMessageDialog(
                		null,
						"Ganó judagor "+(i+1),
						"Juego Terminado",
						JOptionPane.INFORMATION_MESSAGE
				);
                System.exit(0);
            }
        }
	}

	public void pasar(){
        switch (turno){
            case 1:
                turno++; jugadorOeste.add(btnPaso); break;
            case 2:
                turno++; jugadorSur.add(btnPaso); break;
            case 3:
                turno++; jugadorEste.add(btnPaso); break;
            case 4:
                turno = 1; jugadorNorte.add(btnPaso); break;
        }

	    for (int i = 0; i < fichas.length; i++){
            if (fichas[i].getPlayer() != turno)
                fichas[i].setEnabled(false);
            else
                if (mesa.getFrente().getInfo().getNum2() == fichas[i].getNum1()
						|| mesa.getFrente().getInfo().getNum2() == fichas[i].getNum2()
						|| mesa.getFin().getInfo().getNum1() == fichas[i].getNum1()
						|| mesa.getFin().getInfo().getNum1() == fichas[i].getNum2()){
                    fichas[i].setEnabled(true);
                }
        }
        for (int i = 0; i < sub; i++){
            jugadas[i].setEnabled(true);
        }
        this.revalidate();
        this.repaint();
    }

}