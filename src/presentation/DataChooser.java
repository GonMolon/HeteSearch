package presentation;

import domain.GraphException;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class DataChooser extends JFileChooser{

    String path;

    public DataChooser(Component c, boolean export) throws DataChooserException{
        final JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(getCurrentDirectory());
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setAcceptAllFileFilterUsed(false);
        fc.setDialogTitle("Select directory");
        int result;
        if(export) result = fc.showSaveDialog(c);
        else result = fc.showOpenDialog(c);
        if(result == JFileChooser.APPROVE_OPTION){
            File fileSelected = fc.getSelectedFile();
            path = fileSelected.getPath();
            System.out.print(path.toString());
        } else {
            throw new DataChooserException(DataChooserException.Error.DIRECTORY_INVALID);
        }
    }
    public String getDirectory(){
        return path.toString()+"/";
    }
}
