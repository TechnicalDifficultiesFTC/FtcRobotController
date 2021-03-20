package org.firstinspires.ftc.teamcode.technicaldifficulties.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Admin on 3/20/2021.
 */

@TeleOp(group = "Monke", name = "Monke Workey")
public class MotorTestMode extends OpMode {

    private DcMotor motor;

    @Override
    public void init() {
        motor = hardwareMap.get(DcMotor.class, "intakeMotor");
    }

    // 6 holes over

    @Override
    public void loop() {
        if(gamepad1.a) motor.setPower(1);
        else if(gamepad1.b) motor.setPower(-1);
        else motor.setPower(0);
    }
}
