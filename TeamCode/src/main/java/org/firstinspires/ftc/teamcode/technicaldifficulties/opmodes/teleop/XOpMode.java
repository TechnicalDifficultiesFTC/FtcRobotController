package org.firstinspires.ftc.teamcode.technicaldifficulties.opmodes.teleop;

import com.disnodeteam.dogecommander.DogeCommander;
import com.disnodeteam.dogecommander.DogeOpMode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.technicaldifficulties.commands.continuous.AidanMecanumDriveCommand;
import org.firstinspires.ftc.teamcode.technicaldifficulties.commands.continuous.MecanumDriveCommand;
import org.firstinspires.ftc.teamcode.technicaldifficulties.commands.continuous.UpdateTelemetry;
import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.DriveBase;
import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.Vision;

@TeleOp(group = "Testing", name = "X OpMode")
public class XOpMode extends LinearOpMode implements DogeOpMode {

    @Override
    public void runOpMode() {
        DogeCommander commander = new DogeCommander(this);

        DriveBase driveBase = new DriveBase(hardwareMap, telemetry);
        Vision vision = new Vision(hardwareMap, telemetry);

        commander.registerSubsystem(driveBase);
        commander.registerSubsystem(vision);

        commander.init();

        waitForStart();

        commander.runCommandsParallel(
                new AidanMecanumDriveCommand(driveBase, gamepad1),
                new UpdateTelemetry(telemetry)
        );

        commander.stop();
    }
}
