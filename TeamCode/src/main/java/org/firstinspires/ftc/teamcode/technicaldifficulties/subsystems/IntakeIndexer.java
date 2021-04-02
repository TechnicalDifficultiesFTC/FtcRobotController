package org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems;

import com.disnodeteam.dogecommander.Subsystem;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class IntakeIndexer implements Subsystem {

    private HardwareMap hardwareMap;
    private Telemetry telemetry;
    private Gamepad driverGamepad;
    private Gamepad gunnerGamepad;

    private ColorSensor colorSensor;
    private boolean colorSensorLED = false;

    private Servo flickerServo;

    private DcMotor intakeMotor;
    private CRServo intakeServo;
    private double intakePower = 0;

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
        intakeServo = hardwareMap.get(CRServo.class, "intakeServo");
        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        intakeServo.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void periodic() {
        boolean position = colorSensor.green() >= 1000;
        if(gunnerGamepad.x) position = true;
        if(gunnerGamepad.b) position = false;

        flickerServo.setPosition(position ? 0.8 : 0.38);
        intakeMotor.setPower(intakePower);
        if(intakePower > 0) intakeServo.setPower(1);
        else if(intakePower < 0) intakeServo.setPower(-1);
        else intakeServo.setPower(0);

        telemetry.addData("Green", colorSensor.green());
    }

    public void setColorSensorLEDEnabled(boolean enabled) {
        colorSensorLED = enabled;
    }

    public void setIntakePower(double intakePower) {
        this.intakePower = intakePower;
    }
}