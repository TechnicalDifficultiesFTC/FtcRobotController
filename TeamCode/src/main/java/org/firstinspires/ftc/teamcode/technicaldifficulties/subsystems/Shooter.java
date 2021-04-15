package org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems;

import com.disnodeteam.dogecommander.Subsystem;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Shooter implements Subsystem {

    private HardwareMap hardwareMap;
    private Telemetry telemetry;
    private Gamepad gamepad;

    private Servo flickerServo;
    private boolean flick = false;

    private DcMotor leftMotor;
    private DcMotor rightMotor;
    private double shooterPower;

    public Shooter(HardwareMap hardwareMap, Telemetry telemetry, Gamepad gamepad) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        this.gamepad = gamepad;
    }

    @Override
    public void initHardware() {
        flickerServo = hardwareMap.get(Servo.class, "lowerFlickerServo");
        leftMotor = hardwareMap.get(DcMotor.class, "leftShooterMotor");
        rightMotor = hardwareMap.get(DcMotor.class, "rightShooterMotor");
    }

    @Override
    public void periodic() {
        flickerServo.setPosition(flick ? 0.65 : 0.48);

        leftMotor.setPower(-shooterPower);
        rightMotor.setPower(-shooterPower);

        telemetry.addData("Shooter Power", shooterPower);
    }

    public void setShooterPower(double power) {
        shooterPower = power;
    }

    public void setFlick(boolean flick) {
        this.flick = flick;
    }

}