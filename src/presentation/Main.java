package presentation;

public class Main {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        DriverPresentation driverPresentation = new DriverPresentation();
                        driverPresentation.runPresentation();
                    }
                }
        );
    }
}