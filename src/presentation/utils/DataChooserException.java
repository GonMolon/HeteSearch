package presentation.utils;


public class DataChooserException extends Exception {
    private Error error;

    public DataChooserException(Error error){
        super();
        this.error = error;
    }

    public void printStackTrace(){
        if(error == Error.DIRECTORY_INVALID)
            System.out.println("Invalid directory");
        if(error == Error.NOT_SELECTED)
            System.out.println("Directory not selected");
    }

    public enum Error {DIRECTORY_INVALID, NOT_SELECTED}
}
