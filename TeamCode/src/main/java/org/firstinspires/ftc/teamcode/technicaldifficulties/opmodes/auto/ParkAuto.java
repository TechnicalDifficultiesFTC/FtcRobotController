package org.firstinspires.ftc.teamcode.technicaldifficulties.opmodes.auto;

import com.disnodeteam.dogecommander.DogeCommander;
import com.disnodeteam.dogecommander.DogeOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.technicaldifficulties.commands.completion.DriveForTime;
import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.old.DriveBaseOld;
import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.IntakeIndexer;
import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.WobbleGrabber;

/**
 * Created by Admin on 3/5/2021.
 */

@Autonomous(group = "Competition", name = "Park Auto")
@Disabled
public class ParkAuto extends LinearOpMode implements DogeOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DogeCommander commander = new DogeCommander(this);

        DriveBaseOld driveBase = new DriveBaseOld(hardwareMap, telemetry);
        WobbleGrabber wobbleGrabber = new WobbleGrabber(hardwareMap, telemetry);
        IntakeIndexer intakeIndexer = new IntakeIndexer(hardwareMap, telemetry, gamepad1, gamepad2);
        Shooter shooter = new Shooter(hardwareMap, telemetry, gamepad1);

        commander.registerSubsystem(driveBase);
        commander.registerSubsystem(wobbleGrabber);
        commander.registerSubsystem(intakeIndexer);
        commander.registerSubsystem(shooter);

        commander.init();

        ElapsedTime timer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        DriveForTime.test = 0;

        waitForStart();

        commander.runCommand(new DriveForTime(driveBase, timer, telemetry, 0.7, 2));

        commander.stop();
    }
}