package org.firstinspires.ftc.teamcode.technicaldifficulties.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.roadrunner.util.Encoder;

@TeleOp(group = "Testing", name = "Big Wang OpMode")
public class BigWangOpMode extends OpMode {

    private Encoder leftEncoder;
    private Encoder rightEncoder;
    private Encoder frontEncoder;

    @Override
    public void init() {
        leftEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "rightShooterMotor"));
        rightEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "wobbleArmMotor"));
        frontEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "leftShooterMotor"));
    }

    @Override
    public void loop() {
        telemetry.addData("Left Encoder Pos", leftEncoder.getCurrentPosition());
        telemetry.addData("Left Encoder Vel", leftEncoder.getCorrectedVelocity());

        telemetry.addData("Right Encoder Pos", rightEncoder.getCurrentPosition());
        telemetry.addData("Right Encoder Vel", rightEncoder.getCorrectedVelocity());

        telemetry.addData("Front Encoder Pos", frontEncoder.getCurrentPosition());
        telemetry.addData("Front Encoder Vel", frontEncoder.getCorrectedVelocity());
    }
}
