package org.firstinspires.ftc.teamcode.technicaldifficulties.opmodes.auto;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.DriveBase;
import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.IntakeIndexer;
import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.Vision;
import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.WobbleGrabber;

@Config
@Autonomous(name = "HerbertAuto")
public class HerbertAuto extends LinearOpMode {

    private String autoVersion = "Unknown";

    private DriveBase driveBase;
    private WobbleGrabber wobbleGrabber;
    private IntakeIndexer intakeIndexer;
    private Shooter shooter;
    private Vision vision;

    @Override
    public void runOpMode() throws InterruptedException {
        autoVersion = "Unknown";

        driveBase = new DriveBase(hardwareMap, telemetry);
        wobbleGrabber = new WobbleGrabber(hardwareMap, telemetry);
        intakeIndexer = new IntakeIndexer(hardwareMap, telemetry, gamepad1, gamepad2);
        shooter = new Shooter(hardwareMap, telemetry, gamepad1);
        //vision = new Vision(hardwareMap);

        driveBase.initHardware();
        wobbleGrabber.initHardware();
        intakeIndexer.initHardware();
        shooter.initHardware();
        //vision.initHardware();

        ElapsedTime timer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

        waitForStart();

        driveBase.setPoseEstimate(new Pose2d());

        zeroRings(timer);
    }

    private void zeroRings(ElapsedTime timer) {
        autoVersion = "Zero Rings";

        Trajectory trajectoryForward = driveBase.trajectoryBuilder(new Pose2d())
                .splineTo(new Vector2d(58, -16), 0)
                .build();

        followTrajectory(trajectoryForward);

        Trajectory traj2 = driveBase.trajectoryBuilder(trajectoryForward.end())
                .strafeRight(5)
                .build();

        followTrajectory(traj2);
    }

    private void followTrajectory(Trajectory trajectory) {
        driveBase.followTrajectoryAsync(trajectory);
        while(driveBase.getOperationMode() == DriveBase.Mode.FOLLOW_TRAJECTORY  && continueRunning()) {
            driveBase.periodic();
            updateTelemtry();
        }
    }

    private void updateTelemtry() {
        telemetry.addData("Version", autoVersion);
        telemetry.update();
    }

    private boolean continueRunning() {
        return opModeIsActive() && !isStopRequested();
    }
}