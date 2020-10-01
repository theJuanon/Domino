public class Nodo <T>{

    private T Info;
    private Nodo<T> Sig;

    public Nodo(T Dato){
        Info = Dato;
        Sig = null;
    }

    public Nodo<T> getSig(){
        return Sig;
    }

    public void setSig(Nodo<T> Sig){
        this.Sig = Sig;
    }

    public T getInfo(){
        return Info;
    }

}
