package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Admin on 11/13/2020.
 */

@TeleOp(name = "Crab")
public class CrabOpMode extends OpMode {

    DcMotor armMotor;
    Servo clawServoOne;
    Servo clawServoTwo;

    @Override
    public void init() {
        armMotor = hardwareMap.get(DcMotor.class, "armMotor");
        clawServoOne = hardwareMap.get(Servo.class, "clawServoOne");
        clawServoTwo = hardwareMap.get(Servo.class, "clawServoTwo");
    }

    @Override
    public void loop() {
        double armPower = 0;
        if(gamepad1.left_stick_y >= 0.5) armPower = 0.4;
        else if(gamepad1.left_stick_y <= -0.5) armPower = -0.4;

        armMotor.setPower(armPower);

        // 80 degrees (out of 180)
        double rotationNum = 0.44;

        if(gamepad1.right_trigger >= 0.5) {
            clawServoOne.setPosition(1);
            clawServoTwo.setPosition(0);
        } else {
            clawServoOne.setPosition(gamepad1.a ? 1 : 1 - rotationNum);
            clawServoTwo.setPosition(gamepad1.b ? 0 : rotationNum);
        }
    }
}
