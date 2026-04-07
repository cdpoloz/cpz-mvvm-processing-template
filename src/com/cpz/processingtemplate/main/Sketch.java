package com.cpz.processingtemplate.main;

import com.cpz.processing.controls.controls.button.input.ButtonInputAdapter;
import com.cpz.processing.controls.controls.button.model.ButtonModel;
import com.cpz.processing.controls.controls.button.style.ButtonDefaultStyles;
import com.cpz.processing.controls.controls.button.view.ButtonView;
import com.cpz.processing.controls.controls.button.viewmodel.ButtonViewModel;
import com.cpz.processing.controls.controls.label.model.LabelModel;
import com.cpz.processing.controls.controls.label.style.LabelDefaultStyles;
import com.cpz.processing.controls.controls.label.view.LabelView;
import com.cpz.processing.controls.controls.label.viewmodel.LabelViewModel;
import com.cpz.processing.controls.controls.slider.input.SliderInputAdapter;
import com.cpz.processing.controls.controls.slider.model.SliderModel;
import com.cpz.processing.controls.controls.slider.model.SliderOrientation;
import com.cpz.processing.controls.controls.slider.style.SliderDefaultStyles;
import com.cpz.processing.controls.controls.slider.view.SliderView;
import com.cpz.processing.controls.controls.slider.viewmodel.SliderViewModel;
import com.cpz.processing.controls.core.input.DefaultInputLayer;
import com.cpz.processing.controls.core.input.InputManager;
import com.cpz.processing.controls.core.input.KeyboardEvent;
import com.cpz.processing.controls.core.input.PointerEvent;
import com.cpz.processing.controls.core.theme.LightTheme;
import com.cpz.processing.controls.core.theme.ThemeManager;
import com.cpz.processingtemplate.input.keyboard.KeyboardState;
import com.cpz.processingtemplate.input.keyboard.ProcessingKeyboardAdapter;
import com.cpz.processingtemplate.input.pointer.PointerState;
import com.cpz.processingtemplate.input.pointer.ProcessingPointerAdapter;
import com.cpz.processingtemplate.model.AppState;
import com.cpz.processingtemplate.viewmodel.MainViewModel;
import org.jetbrains.annotations.NotNull;
import processing.core.PApplet;
import processing.event.MouseEvent;

import java.math.BigDecimal;

import static com.cpz.processingtemplate.main.Launcher.LOG;

/**
 * Processing bootstrap for the example application built on top of controls.
 */
public class Sketch extends PApplet {

    private int screenWidth;
    private int screenHeight;
    private int smoothing;
    private int fps;
    private float horizontalScreenScaleFactor;
    private float verticalScreenScaleFactor;
    private String windowTitle;
    private final MainViewModel viewModel;
    private final ThemeManager themeManager;
    private final InputManager inputManager;
    private final KeyboardState keyboardState;
    private final ProcessingKeyboardAdapter keyboardAdapter;
    private final PointerState pointerState;
    private final ProcessingPointerAdapter pointerAdapter;
    private ButtonView buttonView;
    private SliderView sliderView;
    private LabelView labelView;
    private ButtonViewModel buttonControlViewModel;
    private SliderViewModel sliderControlViewModel;
    private LabelViewModel labelControlViewModel;
    private ButtonInputAdapter buttonInputAdapter;
    private SliderInputAdapter sliderInputAdapter;

    public Sketch() {
        viewModel = new MainViewModel(new AppState());
        themeManager = new ThemeManager(new LightTheme());
        inputManager = new InputManager();
        keyboardState = new KeyboardState();
        keyboardAdapter = new ProcessingKeyboardAdapter(keyboardState, inputManager);
        pointerState = new PointerState();
        pointerAdapter = new ProcessingPointerAdapter(pointerState, keyboardState, inputManager);
    }

    @Override
    public void settings() {
        LOG.info("Starting settings");
        size((int) (screenWidth * horizontalScreenScaleFactor), (int) (screenHeight * verticalScreenScaleFactor), P2D);
        smooth(smoothing);
        LOG.info("Finished settings");
    }

    @Override
    public void setup() {
        LOG.info("Starting setup");
        frameRate(fps);
        surface.setTitle(windowTitle);
        createControls();
        inputManager.registerLayer(new DemoInputLayer());
        syncControls();
        LOG.info("Finished setup");
    }

    public void initializeSketch(int timerInterval) {
        viewModel.initialize(timerInterval);
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public void setSmoothing(int smoothing) {
        this.smoothing = smoothing;
    }

    public void setFps(int fps) {
        this.fps = fps;
    }

    public void setHorizontalScreenScaleFactor(float horizontalScreenScaleFactor) {
        this.horizontalScreenScaleFactor = horizontalScreenScaleFactor;
    }

    public void setVerticalScreenScaleFactor(float verticalScreenScaleFactor) {
        this.verticalScreenScaleFactor = verticalScreenScaleFactor;
    }

    public void setWindowTitle(String windowTitle) {
        this.windowTitle = windowTitle;
    }

    @Override
    public void draw() {
        viewModel.update(millis());
        syncControls();
        background(themeManager.getSnapshot().tokens.surface);
        buttonView.draw();
        sliderView.draw();
        labelView.draw();
    }

    @Override
    public void mouseWheel(@NotNull MouseEvent event) {
        pointerAdapter.mouseWheel(event.getCount());
    }

    @Override
    public void mouseReleased() {
        pointerAdapter.mouseReleased(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mousePressed() {
        pointerAdapter.mousePressed(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseDragged() {
        pointerAdapter.mouseDragged(mouseX, mouseY);
    }

    @Override
    public void mouseMoved() {
        pointerAdapter.mouseMoved(mouseX, mouseY);
    }

    @Override
    public void keyReleased() {
        normalizeEscape();
        keyboardAdapter.keyReleased(key, keyCode);
    }

    @Override
    public void keyPressed() {
        normalizeEscape();
        keyboardAdapter.keyPressed(key, keyCode);
    }

    @Override
    public void keyTyped() {
        normalizeEscape();
        keyboardAdapter.keyTyped(key, keyCode);
    }

    private void createControls() {
        buttonControlViewModel = new ButtonViewModel(new ButtonModel("Disable feature"));
        buttonControlViewModel.setClickListener(() -> {
            viewModel.toggleDemoEnabled();
            syncControls();
        });
        buttonView = new ButtonView(this, buttonControlViewModel, width * 0.5f, height * 0.28f, 220.0f, 54.0f);
        buttonView.setStyle(ButtonDefaultStyles.primary(themeManager));
        buttonInputAdapter = new ButtonInputAdapter(buttonView, buttonControlViewModel);

        SliderModel sliderModel = new SliderModel();
        sliderModel.setMin(BigDecimal.ZERO);
        sliderModel.setMax(new BigDecimal("100"));
        sliderModel.setStep(BigDecimal.ONE);
        sliderModel.setValue(viewModel.getSliderValue());
        sliderControlViewModel = new SliderViewModel(sliderModel);
        sliderControlViewModel.setFormatter(value -> "Value: " + value.toPlainString());
        sliderControlViewModel.setOnValueChanged(value -> {
            viewModel.setSliderValue(value);
            syncControls();
        });
        sliderView = new SliderView(this, sliderControlViewModel, width * 0.5f, height * 0.50f, 320.0f, 52.0f, SliderOrientation.HORIZONTAL);
        sliderView.setStyle(SliderDefaultStyles.standard(themeManager));
        sliderInputAdapter = new SliderInputAdapter(sliderView, sliderControlViewModel);

        labelControlViewModel = new LabelViewModel(new LabelModel());
        labelView = new LabelView(this, labelControlViewModel, 0.0f, 0.0f);
        labelView.setStyle(LabelDefaultStyles.defaultText(themeManager));
    }

    private void syncControls() {
        buttonControlViewModel.setText(viewModel.isDemoEnabled() ? "Disable feature" : "Enable feature");
        sliderControlViewModel.setEnabled(viewModel.isDemoEnabled());
        sliderControlViewModel.setValue(viewModel.getSliderValue());
        labelControlViewModel.setText(viewModel.getStatusText());
        labelView.centerBlockAround(width * 0.5f, height * 0.73f);
    }

    private void normalizeEscape() {
        if (key == ESC) {
            key = 0;
        }
    }

    private final class DemoInputLayer extends DefaultInputLayer {
        private DemoInputLayer() {
            super(0);
        }

        @Override
        public boolean handlePointerEvent(PointerEvent event) {
            switch (event.getType()) {
                case MOVE:
                    buttonInputAdapter.handleMouseMove(event.getX(), event.getY());
                    sliderInputAdapter.handleMouseMove(event.getX(), event.getY());
                    return true;
                case DRAG:
                    sliderInputAdapter.handleMouseDrag(event.getX(), event.getY());
                    buttonInputAdapter.handleMouseMove(event.getX(), event.getY());
                    return true;
                case PRESS:
                    buttonInputAdapter.handleMousePress(event.getX(), event.getY());
                    sliderInputAdapter.handleMousePress(event.getX(), event.getY());
                    return true;
                case RELEASE:
                    buttonInputAdapter.handleMouseRelease(event.getX(), event.getY());
                    sliderInputAdapter.handleMouseRelease(event.getX(), event.getY());
                    return true;
                case WHEEL:
                    sliderInputAdapter.handleMouseWheel(event.getWheelDelta(), event.isShiftDown(), event.isControlDown());
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public boolean handleKeyboardEvent(KeyboardEvent event) {
            return false;
        }
    }
}
