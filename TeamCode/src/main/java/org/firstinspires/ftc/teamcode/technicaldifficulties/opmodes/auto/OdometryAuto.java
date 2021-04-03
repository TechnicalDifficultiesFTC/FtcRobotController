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
        vision = new Vision(hardwareMap, telemetry);

        driveBase.initHardware();
        wobbleGrabber.initHardware();
        intakeIndexer.initHardware();
        shooter.initHardware();
        vision.initHardware();

        ElapsedTime timer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

        waitForStart();

        driveBase.setPoseEstimate(new Pose2d());

        waitForTime(timer, 1, vision);

        switch(vision.getRingCount()) {
            case ZERO:
                zeroRings(timer);
                break;
            case ONE:
                oneRing(timer);
                break;
            case FOUR:
                fourRings(timer);
                break;
        }
    }

    private void zeroRings(ElapsedTime timer) {
        autoVersion = "Zero Rings";

        Trajectory trajectory1 = driveBase.trajectoryBuilder(new Pose2d())
                .addDisplacementMarker(10, () -> shooter.setShooterPower(0.7))
                .splineToLinearHeading(new Pose2d(20, -13, 0), 0)
                .splineToLinearHeading(new Pose2d(58, -13, 0), 0)
                .build();
        followTrajectory(trajectory1);


        shoot(timer);

        Trajectory rightEight = driveBase.trajectoryBuilder(trajectory1.end())
                .strafeRight(8)
                .build();

        followTrajectory(rightEight);
        waitForTime(timer, 1);
        shoot(timer);

        Trajectory rightTwo = driveBase.trajectoryBuilder(rightEight.end())
                .strafeRight(8)
                .build();

        followTrajectory(rightTwo);
        waitForTime(timer, 1);
        shoot(timer);

        Trajectory trajectory2 = driveBase.trajectoryBuilder(rightTwo.end())
                .addTemporalMarker(1, () -> shooter.setShooterPower(0))
                .strafeLeft(58)
                .build();

        wobbleGrabber.setArmPower(0.7);

        followTrajectory(trajectory2);

        wobbleGrabber.setClawOpen(true);
        waitForTime(timer, 0.25, wobbleGrabber);

        Trajectory trajectory3 = driveBase.trajectoryBuilder(trajectory2.end())
                .splineToLinearHeading(new Pose2d(trajectory2.end().getX() - 15, trajectory2.end().getY(), 0), 0)
                .splineToLinearHeading(new Pose2d(trajectory2.end().getX() - 15, trajectory2.end().getY() - 18, 0), 0)
                .splineToLinearHeading(new Pose2d(trajectory2.end().getX() + 10, trajectory2.end().getY() - 18, 0), 0)
                .build();

        followTrajectory(trajectory3);

        /*
        Trajectory trajectory4 = driveBase.trajectoryBuilder(trajectory2.end())
                .splineToConstantHeading(new Vector2d(trajectory2.end().getX() - 10, trajectory2.end().getY()), 0)
                .splineToSplineHeading(new Pose2d(trajectory2.end().getX() - 15, trajectory2.end().getY(), Math.toRadians(-90)), 0)
                .splineToSplineHeading(new Pose2d(trajectory2.end().getX() - 20, trajectory2.end().getY(), Math.toRadians(-180)), 0)
                .splineToSplineHeading(new Pose2d(trajectory2.end().getX() - 30, trajectory2.end().getY() - 5, Math.toRadians(-180)), 0)
                .build();

        followTrajectory(trajectory4);

        wobbleGrabber.setClawOpen(false);
        waitForTime(timer, 0.25, wobbleGrabber);
        wobbleGrabber.setArmPower(-0.7);

        Trajectory trajectory5 = driveBase.trajectoryBuilder(trajectory4.end())
                .splineToLinearHeading(new Pose2d(trajectory4.end().getX() + 24, trajectory4.end().getY() + 2, 0), 0)
                .addDisplacementMarker(15, () -> wobbleGrabber.setArmPower(0.7))
                .build();

        followTrajectory(trajectory5);

        wobbleGrabber.setClawOpen(true);
        waitForTime(timer, 0.25, wobbleGrabber);

        Trajectory trajectory7 = driveBase.trajectoryBuilder(trajectory5.end())
                .splineToLinearHeading(new Pose2d(trajectory5.end().getX() - 5, trajectory5.end().getY(), 0), 0)
                .addDisplacementMarker(5, () -> wobbleGrabber.setArmPower(-0.7))
                .splineToLinearHeading(new Pose2d(trajectory5.end().getX() - 5, trajectory5.end().getY() - 10, 0), 0)
                .splineToLinearHeading(new Pose2d(trajectory5.end().getX() + 23, trajectory5.end().getY() - 18, 0), 0)
                .build();

        followTrajectory(trajectory7);
         */
    }

    private void oneRing(ElapsedTime timer) {
        autoVersion = "One Ring";

        Trajectory trajectory1 = driveBase.trajectoryBuilder(new Pose2d())
                .addDisplacementMarker(10, () -> shooter.setShooterPower(0.7))
                .splineToLinearHeading(new Pose2d(20, -13, 0), 0)
                .splineToLinearHeading(new Pose2d(58, -13, 0), 0)
                .build();
        followTrajectory(trajectory1);


        shoot(timer);

        Trajectory rightEight = driveBase.trajectoryBuilder(trajectory1.end())
                .strafeRight(8)
                .build();

        followTrajectory(rightEight);
        waitForTime(timer, 1);
        shoot(timer);

        Trajectory rightTwo = driveBase.trajectoryBuilder(rightEight.end())
                .strafeRight(8)
                .build();

        followTrajectory(rightTwo);
        waitForTime(timer, 1);
        shoot(timer);

        Trajectory trajectory2 = driveBase.trajectoryBuilder(rightTwo.end())
                .addTemporalMarker(1, () -> shooter.setShooterPower(0))
                .splineToLinearHeading(new Pose2d(rightTwo.end().getX() + 22, rightTwo.end().getY() + 35, 0), 0)
                .build();

        wobbleGrabber.setArmPower(0.7);

        followTrajectory(trajectory2);

        wobbleGrabber.setClawOpen(true);
        waitForTime(timer, 0.25, wobbleGrabber);

        Trajectory trajectory3 = driveBase.trajectoryBuilder(trajectory2.end())
                .back(10)
                .build();

        followTrajectory(trajectory3);
    }

    private void fourRings(ElapsedTime timer) {
        autoVersion = "Four Rings";

        Trajectory trajectory1 = driveBase.trajectoryBuilder(new Pose2d())
                .addDisplacementMarker(10, () -> shooter.setShooterPower(0.7))
                .splineToLinearHeading(new Pose2d(20, -13, 0), 0)
                .splineToLinearHeading(new Pose2d(58, -13, 0), 0)
                .build();
        followTrajectory(trajectory1);


        shoot(timer);

        Trajectory rightEight = driveBase.trajectoryBuilder(trajectory1.end())
                .strafeRight(8)
                .build();

        followTrajectory(rightEight);
        waitForTime(timer, 1);
        shoot(timer);

        Trajectory rightTwo = driveBase.trajectoryBuilder(rightEight.end())
                .strafeRight(8)
                .build();

        followTrajectory(rightTwo);
        waitForTime(timer, 1);
        shoot(timer);

        Trajectory trajectory2 = driveBase.trajectoryBuilder(rightTwo.end())
                .addTemporalMarker(1, () -> shooter.setShooterPower(0))
                .splineToLinearHeading(new Pose2d(rightTwo.end().getX() + 44, rightTwo.end().getY() + 57, 0), 0)
                .build();

        wobbleGrabber.setArmPower(0.7);

        followTrajectory(trajectory2);

        wobbleGrabber.setClawOpen(true);
        waitForTime(timer, 0.25, wobbleGrabber);

        Trajectory trajectory3 = driveBase.trajectoryBuilder(trajectory2.end())
                .back(34)
                .build();

        followTrajectory(trajectory3);
    }

    private void updateTelemetry() {
        telemetry.addData("Version", autoVersion);
        telemetry.update();
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
        waitForTime(timer, 0.5, shooter, intakeIndexer);
    }

    private void waitForTime(ElapsedTime timer, double seconds) {
        waitForTime(timer, seconds, new Subsystem[]{});
    }

    private void waitForTime(ElapsedTime timer, double seconds, Subsystem... subsystems) { double startTime = timer.milliseconds();
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