package org.firstinspires.ftc.teamcode.technicaldifficulties.opmodes.teleop;

import com.disnodeteam.dogecommander.DogeCommander;
import com.disnodeteam.dogecommander.DogeOpMode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.technicaldifficulties.commands.continuous.ShooterControlCommand;
import org.firstinspires.ftc.teamcode.technicaldifficulties.commands.continuous.WobbleControlCommand;
import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.WobbleGrabber;

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
