package org.firstinspires.ftc.teamcode.technicaldifficulties.opmodes.teleop;

import com.disnodeteam.dogecommander.DogeCommander;
import com.disnodeteam.dogecommander.DogeOpMode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.technicaldifficulties.commands.continuous.IntakeControlCommand;
import org.firstinspires.ftc.teamcode.technicaldifficulties.commands.continuous.MecanumDriveCommand;
import org.firstinspires.ftc.teamcode.technicaldifficulties.commands.continuous.ShooterControlCommand;
import org.firstinspires.ftc.teamcode.technicaldifficulties.commands.continuous.WobbleControlCommand;
import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.DriveBase;
import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.IntakeIndexer;
import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.WobbleGrabber;

@TeleOp(group = "Testing", name = "Testing OpMode")
public class TestingOpMode extends LinearOpMode implements DogeOpMode {

    @Override
    public void runOpMode() {
        DogeCommander commander = new DogeCommander(this);

        DriveBase driveBase = new DriveBase(hardwareMap, telemetry);
        WobbleGrabber wobbleGrabber = new WobbleGrabber(hardwareMap, telemetry);
        IntakeIndexer intakeIndexer = new IntakeIndexer(hardwareMap, telemetry, gamepad1, gamepad2);
        Shooter shooter = new Shooter(hardwareMap, telemetry, gamepad1);

        commander.registerSubsystem(driveBase);
        commander.registerSubsystem(wobbleGrabber);
        commander.registerSubsystem(intakeIndexer);
        commander.registerSubsystem(shooter);

        commander.init();

        waitForStart();

        commander.runCommandsParallel(
                new MecanumDriveCommand(driveBase, gamepad1),
                new WobbleControlCommand(wobbleGrabber, gamepad2),
                new ShooterControlCommand(shooter, gamepad2, telemetry),
                new IntakeControlCommand(intakeIndexer, gamepad1)
        );

        commander.stop();
    }
}
