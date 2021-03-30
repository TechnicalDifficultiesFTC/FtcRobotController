package org.firstinspires.ftc.teamcode.technicaldifficulties.opmodes.teleop;

import com.disnodeteam.dogecommander.DogeCommander;
import com.disnodeteam.dogecommander.DogeOpMode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.technicaldifficulties.commands.continuous.RoadrunnerMecanumDriveCommand;
import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.RoadrunnerDriveBase;
import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.Vision;

@TeleOp(group = "Testing", name = "X OpMode")
public class XOpMode extends LinearOpMode implements DogeOpMode {

    @Override
    public void runOpMode() {
        DogeCommander commander = new DogeCommander(this);

        RoadrunnerDriveBase driveBase = new RoadrunnerDriveBase(hardwareMap);
        Vision vision = new Vision(hardwareMap, telemetry);

        commander.registerSubsystem(driveBase);
        commander.registerSubsystem(vision);

        commander.init();

        waitForStart();

        commander.runCommandsParallel(
                new RoadrunnerMecanumDriveCommand(driveBase, gamepad1)
        );

        commander.stop();
    }
}
