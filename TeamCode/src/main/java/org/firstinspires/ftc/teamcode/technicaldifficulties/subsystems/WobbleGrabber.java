package org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems;

import com.disnodeteam.dogecommander.Subsystem;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class WobbleGrabber implements Subsystem {

    private HardwareMap hardwareMap;
    private Telemetry telemetry;

    private boolean clawOpen = false;

    private TouchSensor outTouchSensor;
    private TouchSensor inTouchSensor;
    private Servo leftServo;
    private Servo rightServo;

    private DcMotor motor;
    private double armPower = 0;

    public WobbleGrabber(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
    }

    @Override
    public void initHardware() {
        outTouchSensor = hardwareMap.get(TouchSensor.class, "outTouchSensor");
        inTouchSensor = hardwareMap.get(TouchSensor.class, "inTouchServo");
        leftServo = hardwareMap.get(Servo.class, "clawLeftServo");
        rightServo = hardwareMap.get(Servo.class, "clawRightServo");

        motor = hardwareMap.get(DcMotor.class, "wobbleArmMotor");
        motor.setDirection(DcMotorSimple.Direction.REVERSE);

        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void periodic() {
        leftServo.setPosition(clawOpen ? 0.8 : 0.3);
        rightServo.setPosition(clawOpen ? 0.8 : 0.3);

        double adjustedArmPower = armPower;
        if(outTouchSensor.isPressed() && adjustedArmPower > 0) adjustedArmPower = 0;
        if(inTouchSensor.isPressed() && adjustedArmPower < 0) adjustedArmPower = 0;

        motor.setPower(adjustedArmPower);

        //telemetry.addData("Claw State", clawOpen ? "Open" : "Closed");
        //telemetry.addData("Motor Debug", motor.getCurrentPosition() + " | " + motor.isBusy());
        //telemetry.update();
    }

    public void setClawOpen(boolean open) {
        clawOpen = open;
    }

    public void setArmPower(double power) {
        armPower = power;
    }
}