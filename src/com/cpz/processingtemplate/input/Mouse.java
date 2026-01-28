package com.cpz.processingtemplate.input;

import com.cpz.processingtemplate.viewmodel.MainViewModel;
import com.cpz.processingtemplate.interfaces.Input;
import com.cpz.processingtemplate.util.MouseButton;

/**
 * @author CPZ
 */
public class Mouse implements Input {

    // <editor-fold defaultstate="collapsed" desc="*** variables ***">
    private final MainViewModel vm;
    // </editor-fold>

    public Mouse(MainViewModel vm) {
        this.vm = vm;
    }

    // <editor-fold defaultstate="collapsed" desc="*** wheel ***">
    public void mouseWheel(int d, Object... o) {
        vm.onMouseWheel(d, o);
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="*** released ***">
    public void mouseReleasedLeft(Object... o) {
        vm.onMouseReleased(MouseButton.LEFT, o);
    }

    public void mouseReleasedCenter(Object... o) {
        vm.onMouseReleased(MouseButton.CENTER, o);
    }

    public void mouseReleasedRight(Object... o) {
        vm.onMouseReleased(MouseButton.RIGHT, o);
    }

    public void mouseReleasedOther(Object... o) {
        vm.onMouseReleased(MouseButton.OTHER, o);
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="*** pressed ***">
    public void mousePressedLeft(Object... o) {
        //vm.onMousePressed(MouseButton.LEFT, o);
    }

    public void mousePressedCenter(Object... o) {
        //vm.onMousePressed(MouseButton.CENTER, o);
    }

    public void mousePressedRight(Object... o) {
        //vm.onMousePressed(MouseButton.RIGHT, o);
    }

    public void mousePressedOther(Object... o) {
        //vm.onMousePressed(MouseButton.OTHER, o);
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="*** dragged ***">
    public void mouseDraggedLeft(Object... o) {
        //vm.onMouseDragged(MouseButton.LEFT, o);
    }

    public void mouseDraggedCenter(Object... o) {
        //vm.onMouseDragged(MouseButton.CENTER, o);
    }

    public void mouseDraggedRight(Object... o) {
        //vm.onMouseDragged(MouseButton.RIGHT, o);
    }

    public void mouseDraggedOther(Object... o) {
        //vm.onMouseDragged(MouseButton.OTHER, o);
    }
    // </editor-fold>

}
