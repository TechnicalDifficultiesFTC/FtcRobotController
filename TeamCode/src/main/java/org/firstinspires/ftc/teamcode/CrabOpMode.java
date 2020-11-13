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

    DcMotor launcherWheel;
    Servo launchRamp;

    double servoPosition = 0;

    @Override
    public void init() {
        launcherWheel = hardwareMap.get(DcMotor.class, "launcherWheel");
        launchRamp = hardwareMap.get(Servo.class, "launchRamp");
    }

    @Override
    public void loop() {
        double launcherWheelPower = gamepad1.x ? 0.75 : 0;
        launcherWheel.setPower(launcherWheelPower);

        if(gamepad1.left_bumper) servoPosition += 0.01;
        else if(gamepad1.right_bumper) servoPosition -= 0.01;

        // Bad code but fast to write code
        if(servoPosition > 1) servoPosition = 1;
        if(servoPosition < 0) servoPosition = 0;

        launchRamp.setPosition(servoPosition);

        telemetry.addData("Launcher Power", launcherWheelPower);
        telemetry.addData("Launch Ramp Position", servoPosition);
    }
}
