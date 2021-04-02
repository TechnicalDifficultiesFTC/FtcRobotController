package org.firstinspires.ftc.teamcode.technicaldifficulties.opmodes.auto;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.DriveBase;

@Config
@Autonomous(name = "HerbertAuto")
public class HerbertAuto extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        DriveBase drive = new DriveBase(hardwareMap, telemetry);
        drive.initHardware();

        waitForStart();

        Trajectory trajectoryForward = drive.trajectoryBuilder(new Pose2d())
                .forward(50)
                .build();

        drive.followTrajectoryAsync(trajectoryForward);

        while(opModeIsActive()) {
            drive.periodic();
            telemetry.addData("BUSY", drive.getOperationMode());
            telemetry.update();
        }
    }
}