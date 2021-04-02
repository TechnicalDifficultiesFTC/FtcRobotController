package org.firstinspires.ftc.teamcode.technicaldifficulties.opmodes.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.disnodeteam.dogecommander.DogeCommander;
import com.disnodeteam.dogecommander.DogeOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.technicaldifficulties.commands.completion.DriveForTime;
import org.firstinspires.ftc.teamcode.technicaldifficulties.commands.completion.FollowTrajectory;
import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.DriveBase;
import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.IntakeIndexer;
import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.RoadrunnerDriveBase;
import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.WobbleGrabber;

@Autonomous(group = "10332", name = "Odometry Auto")
public class OdometryAuto extends LinearOpMode implements DogeOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        //DogeCommander commander = new DogeCommander(this);

        //RoadrunnerDriveBase driveBase = new RoadrunnerDriveBase(hardwareMap, telemetry);
        SampleMecanumDrive driveBase = new SampleMecanumDrive(hardwareMap);
        //WobbleGrabber wobbleGrabber = new WobbleGrabber(hardwareMap, telemetry);
        //IntakeIndexer intakeIndexer = new IntakeIndexer(hardwareMap, telemetry, gamepad1, gamepad2);
        //Shooter shooter = new Shooter(hardwareMap, telemetry, gamepad1);

        //commander.registerSubsystem(driveBase);
        //commander.registerSubsystem(wobbleGrabber);
        //commander.registerSubsystem(intakeIndexer);
        //commander.registerSubsystem(shooter);

        //commander.init();

        Trajectory trajectory = driveBase.trajectoryBuilder(new Pose2d())
                .forward(50)
                .build();
        Trajectory trajectory1 = driveBase.trajectoryBuilder(trajectory.end())
                .back(50)
                .build();

        //ElapsedTime timer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        //DriveForTime.test = 0;

        waitForStart();

        // Double Forwards
        driveBase.followTrajectoryAsync(trajectory);
        while(driveBase.isBusy() && opModeIsActive()) {
            driveBase.update();
        }

        driveBase.followTrajectoryAsync(trajectory1);
        while(driveBase.isBusy() && opModeIsActive()) {
            driveBase.update();
        }

        /*
        commander.runCommand(new FollowTrajectory(driveBase, telemetry, trajectory));
        commander.runCommand(new FollowTrajectory(driveBase, telemetry, trajectory1));
        */

        //commander.stop();
    }
}