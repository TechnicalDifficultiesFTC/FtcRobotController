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
    private Servo servoDos;
    private boolean buttonPressed;
    private double servoPosition;

    private boolean togglePressed;
    private boolean useDos;

    @Override
    public void init() {
        servoPosition = 0;
        servo = hardwareMap.get(Servo.class, "lowerFlickerServo");
        servoDos = hardwareMap.get(Servo.class, "clawLeftServo");
        colorSensor = hardwareMap.get(ColorSensor.class, "indexerColorSensor");
    }

    @Override
    public void loop() {

        if(gamepad1.right_bumper) {
            if(!buttonPressed) {
                useDos = !useDos;
                togglePressed = true;
            }
        } else togglePressed = false;

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

        if(useDos) servoDos.setPosition(servoPosition);
        else servo.setPosition(servoPosition);

        telemetry.addData("Color Sensor Green", colorSensor.green());
        telemetry.addData("Servo Position", servoPosition);
        telemetry.addData("Button Pressed", buttonPressed);
        telemetry.addData("Servo Used", useDos ? "One" : "Two");
        telemetry.addData("Toggle Pressed", togglePressed);
        telemetry.update();
    }
}
