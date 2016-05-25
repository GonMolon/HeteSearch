package presentation.utilities;


import domain.GraphSearch;
import presentation.PresentationController;

import java.awt.event.FocusEvent;
import java.util.ArrayList;

public class NodeTextField extends AutoClearTextField {

    private PresentationController presentationController;

    public NodeTextField(PresentationController presentationController, String title) {
        super(title);
        this.presentationController = presentationController;
        addFocusListener(this);
    }

    public void focusGained(FocusEvent e) {
        super.focusGained(e);
    }

    public void focusLost(FocusEvent e) {
/*        ArrayList<GraphSearch.Result> result = presentationController.simpleSearch();
        if(result.size() == 1) {
            setText(result.get(0).);
        } else {
        }
*/
        super.focusLost(e);
    }
}
