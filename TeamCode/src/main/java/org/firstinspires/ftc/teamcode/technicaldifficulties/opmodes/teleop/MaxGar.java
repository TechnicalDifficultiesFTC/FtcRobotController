package org.firstinspires.ftc.teamcode.technicaldifficulties.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Admin on 3/10/2021.
 */

@TeleOp(name ="Max Gar", group = "Testing")
public class MaxGar extends OpMode {

    private DcMotor motorOne;
    private DcMotor motorTwo;

    @Override
    public void init() {
        motorOne = hardwareMap.get(DcMotor.class, "motorOne");
        motorTwo = hardwareMap.get(DcMotor.class, "motorTwo");
    }

    @Override
    public void loop() {
        motorOne.setPower(gamepad1.right_trigger);
        motorTwo.setPower(gamepad1.left_trigger);
    }
}
