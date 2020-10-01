public class Lista <T>{

    private Nodo<T> Frente, Fin;
    private T Dr;

    public Lista(){
        Frente = Fin = null;
        Dr = null;
    }

    public boolean insertarFin(T Dato){
        Nodo<T> Nuevo;
        try{
            Nuevo = new Nodo(Dato);
        }catch(Exception e){
            return false;
        }
        if(Frente == null)// Insertando primer nodo
            Frente = Fin = Nuevo;
        else{// Insertando nuevo nodo al final
            Fin.setSig(Nuevo);
            Fin = Nuevo;
        }
        return true;
    }

    public boolean insertarFrente(T Dato){
        Nodo<T> Nuevo;
        try{
            Nuevo = new Nodo(Dato);
        }catch (Exception e){
            return false;
        }

        if(Frente == null)
            Frente = Fin = Nuevo;
        else{
            Nuevo.setSig(Frente);
            Frente = Nuevo;
        }
        return true;
    }

    public boolean insertarOrdenada(T Dato){
        if(Frente == null){
            try{
                Frente = new Nodo(Dato);
            }catch (Exception e){
                return false;
            }
            Fin = Frente;
            return true;
        }
        // Si inserta al inicio
        String idDato = Dato.toString();
        String idFrente = Frente.getInfo().toString();

        if(idDato.compareTo(idFrente) <= 0){// insertar al inicio
            return insertarFrente(Dato);
        }

        String idFin = Fin.getInfo().toString();
        if(idDato.compareTo(idFin) >= 0){// insertar al final
            return insertarFin(Dato);
        }

        Nodo<T> Nuevo;
        try{
            Nuevo = new Nodo(Dato);
        }catch (Exception e){
            return false;
        }

        //entre dos nodos
        Nodo<T> Aux = Frente;
        Nodo<T> Ant = null;
        while(idDato.compareTo(Aux.getInfo().toString()) > 0){
            Ant = Aux;
            Aux = Aux.getSig();
        }
        Ant.setSig(Nuevo);
        Nuevo.setSig(Aux);
        return true;
    }

    public boolean retirar(int posicion){
        if(Frente == null || posicion > length() || posicion <= 0)
            return false;
        Nodo<T> Aux = Frente;
        Nodo<T> Ant = null;
        for(int i = 1; i < posicion; i++){
            Ant = Aux;
            Aux = Aux.getSig();
        }
        Dr = Aux.getInfo();
        // Aux posicionado listo para eliminar

        //Primer caso, Unico nodo de la lista
        if(Frente == Fin){
            Frente = Fin = null;
            return true;
        }

        //Como saber si Aux esta en el primer nodo
        if(Frente == Aux){
            Frente = Frente.getSig();
            return true;
        }

        //Como saber si Aux esta en el ultimo nodo
        if(Fin == Aux){
            Fin = Ant;
            Fin.setSig(null);
            return true;
        }

        //Aux entre dos nodos
        Ant.setSig(Aux.getSig());
        return true;
    }

    public boolean retirar(T Dato){
        if(Frente == null)
            return false;

        String idBorrar = Dato.toString();
        int posicion = 0;
        Nodo<T> Aux = Frente;

        while(Aux != null){
            posicion++;
            String idAux = Aux.getInfo().toString();
            if(idAux.compareToIgnoreCase(idBorrar) == 0)
                break;
            Aux = Aux.getSig();
        }

        if(Aux == null)//no lo encuentra
            return false;
        return retirar(posicion);//encontrado
    }

    public boolean buscar(T Dato){
        if(Frente == null)
            return false;
        String idBuscado = Dato.toString();
        Nodo<T> Aux = Frente;
        while(Aux != null){
            String idAux = Aux.getInfo().toString();
            if(idAux.compareToIgnoreCase(idBuscado) == 0)
                break;
            Aux = Aux.getSig();
        }

        if(Aux == null)//No encontrado
            return false;
        Dr = Aux.getInfo();
        return true;
    }

    public int length(){
        Nodo<T> Aux = Frente;
        int cont = 0;
        while(Aux!= null){
            cont++;
            Aux = Aux.getSig();
        }
        return cont;
    }

    public Nodo<T> getFrente() {
        return Frente;
    }

    public Nodo<T> getFin() {
        return Fin;
    }

    public T getDr() {
        return Dr;
    }
}
