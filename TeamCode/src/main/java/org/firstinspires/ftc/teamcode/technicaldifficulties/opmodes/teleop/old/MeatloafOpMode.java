package org.firstinspires.ftc.teamcode.technicaldifficulties.opmodes.teleop.old;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Admin on 2/7/2021.
 */

// OLD
@TeleOp(group = "Testing", name = "Meatloaf Op Mode")
public class MeatloafOpMode extends OpMode {
// Hasan is a meatloaf
    private DcMotor shooterMotorOne;
    private DcMotor shooterMotorTwo;
    private Servo lowerFlickServo;
    private Servo upperFlickServo;

    @Override
    public void init() {
        shooterMotorOne = hardwareMap.get(DcMotor.class, "shooterMotorOne");
        shooterMotorTwo = hardwareMap.get(DcMotor.class, "shooterMotorTwo");
        lowerFlickServo = hardwareMap.get(Servo.class, "lowerFlickServo");
        upperFlickServo = hardwareMap.get(Servo.class, "upperFlickServo");
    }

    @Override
    public void loop() {
        shooterMotorOne.setPower(gamepad1.right_trigger * -1);
        shooterMotorTwo.setPower(gamepad1.right_trigger * -1);
        lowerFlickServo.setPosition(gamepad1.a ? 0.5 : 0.3);
        upperFlickServo.setPosition(gamepad1.b ? 1 : 0.8);
    }
}
