package presentation;
/**
 * Created by Magi on 12/05/2016.
 */
public class DriverPresentation {

    private MainView mainView;

    public DriverPresentation(){
        //Crear controlador de dominio
        mainView = new MainView();
    }

    public void runPresentation() {
        //Inicializar controlador de dominio
        mainView.setEnable();
    }
}
