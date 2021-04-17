package org.firstinspires.ftc.teamcode.technicaldifficulties.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(group = "Tuning", name = "Flicker Tuning")
public class FlickerTuningOpMode extends OpMode {

    private ColorSensor colorSensor;
    private Servo servo;
    private boolean buttonPressed;
    private double servoPosition;

    @Override
    public void init() {
        servoPosition = 0;
        servo = hardwareMap.get(Servo.class, "clawRightServo");
        colorSensor = hardwareMap.get(ColorSensor.class, "indexerColorSensor");
    }

    @Override
    public void loop() {

        if(gamepad1.a) {
            if(!buttonPressed) {
                servoPosition += 0.1;
                buttonPressed = true;
            }
        } else if(gamepad1.b) {
            if(!buttonPressed) {
                servoPosition -= 0.1;
                buttonPressed = true;
            }
        } else if(gamepad1.x) {
            if(!buttonPressed) {
                servoPosition += 0.01;
                buttonPressed = true;
            }
        } else if(gamepad1.y) {
            if(!buttonPressed) {
                servoPosition -= 0.01;
                buttonPressed = true;
            }
        } else {
            buttonPressed = false;
        }

        if(servoPosition < 0) servoPosition = 0;
        if(servoPosition > 1) servoPosition = 1;

        servo.setPosition(servoPosition);

        telemetry.addData("Color Sensor Green", colorSensor.green());
        telemetry.addData("Servo Position", servoPosition);
        telemetry.addData("Button Pressed", buttonPressed);
        telemetry.update();
    }
}
