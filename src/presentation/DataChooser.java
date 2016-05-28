package presentation;

import presentation.utils.DataChooserException;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class DataChooser extends JFileChooser{

    String path;
    boolean correct_directory;
    boolean found;
    final String[] default_files = {"author.txt", "author_label.txt", "conf.txt", "conf_label.txt", "paper_label.txt", "paper.txt",
                                    "paper_author.txt", "paper_conf.txt", "paper_term.txt", "term.txt"};

    public DataChooser(Component c, boolean export) throws DataChooserException {
        final JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(getCurrentDirectory());
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setAcceptAllFileFilterUsed(false);
        fc.setDialogTitle("Select directory");
        int result;
        File folder = new File("null");
        if(export) result = fc.showSaveDialog(c);
        else result = fc.showOpenDialog(c);
        if(result == JFileChooser.APPROVE_OPTION){
            folder = fc.getSelectedFile();
            path = folder.getPath();
        } else {
            throw new DataChooserException(DataChooserException.Error.NOT_SELECTED);
        }
        // CONTROL DE ERRORES, QUE EN EL DIRECTORIO HAYA LOS ARCHIVOS POR DEFECTO (AUTHOR, PAPER, ...)
        //LISTFILES desde FILE
        correct_directory = true;
        String[] files = folder.list();
        int i = 0;
        while(i < 10 && correct_directory){
            found = false;
            for (int j = 0; j < files.length && !found; ++j) {
                if (files[j].equals(default_files[i])){
                    found = true;
                }
            }
            if(!found) correct_directory = false;
            ++i;
        }
        if(!correct_directory) throw new DataChooserException(DataChooserException.Error.DIRECTORY_INVALID);
    }
    public String getDirectory(){
        return path.toString()+"/";
    }
}
