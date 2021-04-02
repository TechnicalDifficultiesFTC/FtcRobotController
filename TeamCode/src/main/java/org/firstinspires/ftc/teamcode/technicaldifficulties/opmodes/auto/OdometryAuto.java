package org.firstinspires.ftc.teamcode.technicaldifficulties.opmodes.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.disnodeteam.dogecommander.Subsystem;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.IntakeIndexer;
import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.DriveBase;
import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.Vision;
import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.WobbleGrabber;

@Autonomous(group = "10332", name = "Odometry Auto")
public class OdometryAuto extends LinearOpMode {

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
        vision = new Vision(hardwareMap);

        driveBase.initHardware();
        wobbleGrabber.initHardware();
        intakeIndexer.initHardware();
        shooter.initHardware();
        vision.initHardware();

        ElapsedTime timer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

        driveBase.setPoseEstimate(new Pose2d());

        waitForStart();

        zeroRings(timer);
    }

    private void zeroRings(ElapsedTime timer) {
        autoVersion = "Zero Rings";

        Trajectory trajectory1 = driveBase.trajectoryBuilder(new Pose2d())
                .splineTo(new Vector2d(58, 16), Math.toRadians(0))
                .build();
        followTrajectory(trajectory1);

        shooter.setShooterPower(0.7);
        waitForTime(timer, 1, shooter);

        shoot(timer);

        Trajectory rightEight = driveBase.trajectoryBuilder(trajectory1.end())
                .strafeRight(8)
                .build();

        followTrajectory(rightEight);
        shoot(timer);

        followTrajectory(rightEight);
        shoot(timer);

        shooter.setShooterPower(0);
        waitForTime(timer, 0.5, shooter);

        Trajectory trajectory2 = driveBase.trajectoryBuilder(trajectory1.end().plus(new Pose2d(16, 0, 0)))
                .strafeLeft(61)
                .build();

        wobbleGrabber.setArmPower(0.7);

        followTrajectory(trajectory2);

        wobbleGrabber.setClawOpen(true);
        waitForTime(timer, 0.25, wobbleGrabber);

        Trajectory trajectory3 = driveBase.trajectoryBuilder(trajectory2.end())
                .back(5)
                .build();

        followTrajectory(trajectory3);

        Trajectory trajectory4 = driveBase.trajectoryBuilder(trajectory3.end())
                .splineTo(new Vector2d(trajectory3.end().getX() - 25, trajectory3.end().getY() + 8), Math.toRadians(180))
                .build();

        followTrajectory(trajectory4);

        wobbleGrabber.setClawOpen(false);
        waitForTime(timer, 0.25, wobbleGrabber);

        Trajectory trajectory5 = driveBase.trajectoryBuilder(trajectory4.end())
                .splineTo(new Vector2d(trajectory4.end().getX() + 24, trajectory4.end().getY()), 0)
                .build();

        followTrajectory(trajectory5);

        wobbleGrabber.setClawOpen(true);
        waitForTime(timer, 0.25, wobbleGrabber);

        Trajectory trajectory6 = driveBase.trajectoryBuilder(trajectory5.end())
                .back(5)
                .build();

        followTrajectory(trajectory6);

        wobbleGrabber.setArmPower(-0.7);
        wobbleGrabber.setClawOpen(false);

        Trajectory trajectory7 = driveBase.trajectoryBuilder(trajectory6.end())
                .splineTo(new Vector2d(trajectory6.end().getX() + 23, trajectory6.end().getY() + 18), 0)
                .build();

        followTrajectory(trajectory7);
    }

    private void oneRing(ElapsedTime timer) {
        autoVersion = "One Ring";
    }

    private void fourRings(ElapsedTime timer) {
        autoVersion = "Four Rings";
    }

    private void updateTelemetry() {
        telemetry.addData("Version", autoVersion);
    }

    private void followTrajectory(Trajectory trajectory) {
        driveBase.followTrajectoryAsync(trajectory);
        while(driveBase.getOperationMode() == DriveBase.Mode.FOLLOW_TRAJECTORY && continueRunning()) {
            driveBase.periodic();
            wobbleGrabber.periodic();
            intakeIndexer.periodic();
            shooter.periodic();
            updateTelemetry();
        }
    }

    private void shoot(ElapsedTime timer) {
        shooter.setFlick(true);
        waitForTime(timer, 0.5, shooter);
        shooter.setFlick(false);
        waitForTime(timer, 0.5, shooter);
    }

    private void waitForTime(ElapsedTime timer, double seconds) {
        waitForTime(timer, seconds, new Subsystem[]{});
    }

    private void waitForTime(ElapsedTime timer, double seconds, Subsystem... subsystems) {
        double startTime = timer.milliseconds();
        double endTime = startTime + (seconds * 1000);
        while(timer.milliseconds() < endTime && continueRunning()) {
            for(Subsystem subsystem : subsystems) subsystem.periodic();
            updateTelemetry();
        }
    }

    private boolean continueRunning() {
        return opModeIsActive() && !isStopRequested();
    }
}