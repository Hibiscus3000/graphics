package ru.nsu.fit.icg.lab2.dialog;

import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import ru.nsu.fit.icg.lab2.imageBox.ImageBox;
import ru.nsu.fit.icg.lab2.imageBox.ScalingType;

import java.util.HashMap;
import java.util.Map;

public class ScalingDialog extends InstrumentDialog {

    private final ImageBox imageBox;

    private final ToggleGroup scalingTypeGroup = new ToggleGroup();

    public ScalingDialog(ImageBox imageBox) {
        this.imageBox = imageBox;
        setTitle("Интерполяция");
        VBox scalingBox = new VBox();
        scalingBox.setSpacing(4);
        for (ScalingType scalingType : ScalingType.values()) {
            ScalingRadioButton scalingRadioButton = new ScalingRadioButton(scalingType);
            scalingRadioButton.setToggleGroup(scalingTypeGroup);
            scalingBox.getChildren().add(scalingRadioButton);
            radioButtonMap.put(scalingType, scalingRadioButton);
        }
        radioButtonMap.get(selectedScalingType = ScalingType.BILINEAR).setSelected(true);
        scalingBox.getChildren().add(getButtonBox());
        getDialogPane().setContent(scalingBox);
    }

    private final Map<ScalingType, ScalingRadioButton> radioButtonMap = new HashMap<>();
    private ScalingType selectedScalingType;

    @Override
    protected void ok() {
        selectedScalingType = ((ScalingRadioButton) scalingTypeGroup.getSelectedToggle()).getScalingType();
        imageBox.interpolate(selectedScalingType);
    }

    @Override
    protected void cancel() {
        radioButtonMap.get(selectedScalingType).setSelected(true);
    }

    private class ScalingRadioButton extends RadioButton {

        private final ScalingType scalingType;

        public ScalingRadioButton(ScalingType scalingType) {
            super(scalingType.getName());
            this.scalingType = scalingType;
        }

        public ScalingType getScalingType() {
            return scalingType;
        }
    }
}
