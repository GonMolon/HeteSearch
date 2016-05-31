import presentation.CommandLineInterface;
import presentation.PresentationController;

import javax.swing.*;
import java.io.Console;

public class Main {

    public static void main(String[] args) {
        if (System.console() == null) {
            javax.swing.SwingUtilities.invokeLater(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            } catch (InstantiationException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (UnsupportedLookAndFeelException e) {
                                e.printStackTrace();
                            }
                            PresentationController presentationController = new PresentationController();
                        }
                    }
            );
        } else {
            CommandLineInterface cli = new CommandLineInterface();
            cli.run();
        }
    }
}