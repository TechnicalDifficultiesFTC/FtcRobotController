package org.firstinspires.ftc.teamcode.technicaldifficulties.opmodes.teleop.old;

import com.disnodeteam.dogecommander.DogeCommander;
import com.disnodeteam.dogecommander.DogeOpMode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.technicaldifficulties.commands.continuous.old.WobbleControlCommand;
import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.old.WobbleGrabber;

// OLD
@TeleOp(group = "Testing", name = "Wobble Op Mode")
public class WobbleOpMode extends LinearOpMode implements DogeOpMode {

    @Override
    public void runOpMode() {
        DogeCommander commander = new DogeCommander(this);

        WobbleGrabber wobbleGrabber = new WobbleGrabber(hardwareMap, telemetry);

        commander.registerSubsystem(wobbleGrabber);

        commander.init();

        waitForStart();

        commander.runCommandsParallel(
            new WobbleControlCommand(wobbleGrabber, gamepad1)
        );

        commander.stop();
    }

}
