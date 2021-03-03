package org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems;

import com.disnodeteam.dogecommander.Subsystem;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class IntakeIndexer implements Subsystem {

    private HardwareMap hardwareMap;
    private Telemetry telemetry;
    private Gamepad driverGamepad;
    private Gamepad gunnerGamepad;

    private ColorSensor colorSensor;
    private boolean colorSensorLED = false;

    private Servo flickerServo;

    public IntakeIndexer(HardwareMap hardwareMap, Telemetry telemetry, Gamepad driverGamepad, Gamepad gunnerGamepad) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        this.driverGamepad = driverGamepad;
        this.gunnerGamepad = gunnerGamepad;
    }

    @Override
    public void initHardware() {
        colorSensor = hardwareMap.get(ColorSensor.class, "indexerColorSensor");
        flickerServo = hardwareMap.get(Servo.class, "upperFlickerServo");
    }

    @Override
    public void periodic() {
        colorSensor.enableLed(colorSensorLED);
        flickerServo.setPosition(colorSensor.argb() >= 0 ? 0.38 : 0.6);
    }

    public void setColorSensorLEDEnabled(boolean enabled) {
        colorSensorLED = enabled;
    }
}
