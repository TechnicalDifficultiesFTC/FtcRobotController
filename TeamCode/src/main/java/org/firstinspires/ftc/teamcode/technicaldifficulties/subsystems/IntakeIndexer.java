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

    private Servo sideArmServo;
    private boolean sideArmState;

    private DcMotor intakeMotor;
    private CRServo intakeServo;
    private double intakePower = 0;

    public IntakeIndexer(HardwareMap hardwareMap, Telemetry telemetry, Gamepad driverGamepad, Gamepad gunnerGamepad, boolean sideArmStartState) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        this.driverGamepad = driverGamepad;
        this.gunnerGamepad = gunnerGamepad;
        this.sideArmState = sideArmStartState;
    }

    @Override
    public void initHardware() {
        colorSensor = hardwareMap.get(ColorSensor.class, "indexerColorSensor");
        flickerServo = hardwareMap.get(Servo.class, "upperFlickerServo");
        intakeServo = hardwareMap.get(CRServo.class, "intakeServo");
        sideArmServo = hardwareMap.get(Servo.class, "sideArmServo");
        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
    }

    @Override
    public void periodic() {
        boolean position = colorSensor.green() >= 1000;
        if(gunnerGamepad.x) position = true;
        if(gunnerGamepad.b) position = false;

        flickerServo.setPosition(position ? 0.6 : 0.39);
        intakeMotor.setPower(intakePower);
        if(intakePower > 0) intakeServo.setPower(-1);
        else if(intakePower < 0) intakeServo.setPower(1);
        else intakeServo.setPower(0);

        //sideArmServo.setPosition(sideArmState ? 0.22 : 0.7);
        sideArmServo.setPosition(sideArmState ? 0.42 : 0.92);

        telemetry.addData("Green", colorSensor.green());
        telemetry.addData("Side Arm State", sideArmState);
    }

    public void setColorSensorLEDEnabled(boolean enabled) {
        colorSensorLED = enabled;
    }

    public void setIntakePower(double intakePower) {
        this.intakePower = intakePower;
    }

    public void setSideArmState(boolean state) {
        this.sideArmState = state;
    }

    public void toggleSideArmState() {
        sideArmState = !sideArmState;
    }
}