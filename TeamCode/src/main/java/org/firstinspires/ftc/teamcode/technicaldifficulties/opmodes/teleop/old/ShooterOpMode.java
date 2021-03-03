package org.firstinspires.ftc.teamcode.technicaldifficulties.opmodes.teleop.old;

import com.disnodeteam.dogecommander.DogeCommander;
import com.disnodeteam.dogecommander.DogeOpMode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.technicaldifficulties.commands.continuous.old.ShooterControlCommand;
import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.old.Shooter;

// OLD
@TeleOp(group = "Testing", name = "Shooter Op Mode")
public class ShooterOpMode extends LinearOpMode implements DogeOpMode {

    @Override
    public void runOpMode() {
        DogeCommander commander = new DogeCommander(this);

        Shooter shooter = new Shooter(hardwareMap, telemetry);

        commander.registerSubsystem(shooter);

        commander.init();

        waitForStart();

        commander.runCommandsParallel(
            new ShooterControlCommand(shooter, gamepad1)
        );

        commander.stop();
    }

}
