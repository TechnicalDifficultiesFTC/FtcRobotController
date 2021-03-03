package org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems;

import com.disnodeteam.dogecommander.Subsystem;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class WobbleGrabber implements Subsystem {

    private HardwareMap hardwareMap;
    private Telemetry telemetry;
    private Gamepad test;

    private boolean clawOpen = false;

    private Servo leftServo;
    private Servo rightServo;

    private DcMotor motor;

    public WobbleGrabber(HardwareMap hardwareMap, Telemetry telemetry, Gamepad test) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        this.test = test;
    }

    @Override
    public void initHardware() {
        leftServo = hardwareMap.get(Servo.class, "clawLeftServo");
        rightServo = hardwareMap.get(Servo.class, "clawRightServo");

        motor = hardwareMap.get(DcMotor.class, "wobbleArmMotor");

        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void periodic() {
        leftServo.setPosition(clawOpen ? 0.6 : 0.3);
        rightServo.setPosition(clawOpen ? 0.6 : 0.3);

        if(test.x) motor.setPower(0.2);
        else if(test.y) motor.setPower(-0.2);
        else motor.setPower(0);

        telemetry.addData("Claw State", clawOpen ? "Open" : "Closed");
        telemetry.addData("Motor Debug", motor.getCurrentPosition() + " | " + motor.isBusy());
        telemetry.update();
    }

    public void setClawOpen(boolean open) {
        clawOpen = open;
    }
}
