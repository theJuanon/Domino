// Juan de Jesus Hernandez Angulo - 18170350
// Topicos Avanzados de Programacion - 12:00 - 13:00

import javax.swing.*;

public class Boton extends JButton {

    private int num1, num2, player;
    private String ruta;
    public Boton(String ruta, int num1, int num2){
        super(Rutinas.AjustarImagen(ruta, 60, 100));
        this.num1 = num1;
        this.num2 = num2;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public void redimensionar(int ancho, int alto){
        this.setIcon(Rutinas.AjustarImagen(this.ruta, ancho, alto));
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public int getNum1() {
        return num1;
    }

    public void setNum1(int num1) {
        this.num1 = num1;
    }

    public int getNum2() {
        return num2;
    }

    public void setNum2(int num2) {
        this.num2 = num2;
    }
}
