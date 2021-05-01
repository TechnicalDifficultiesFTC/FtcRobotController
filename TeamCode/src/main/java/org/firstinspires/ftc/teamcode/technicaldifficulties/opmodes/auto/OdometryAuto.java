package org.firstinspires.ftc.teamcode.technicaldifficulties.opmodes.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.profile.VelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.disnodeteam.dogecommander.Subsystem;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.roadrunner.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.IntakeIndexer;
import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.DriveBase;
import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.Vision;
import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.WobbleGrabber;

@Autonomous(group = "Competition", name = "Odometry Auto")
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

        driveBase = new DriveBase(hardwareMap, telemetry, true);
        wobbleGrabber = new WobbleGrabber(hardwareMap, telemetry);
        intakeIndexer = new IntakeIndexer(hardwareMap, telemetry, gamepad1, gamepad2, false);
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

        waitForTime(timer, 0.25, vision);

        /*
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
         */

        fourRings(timer);
    }

    private void zeroRings(ElapsedTime timer) {
        autoVersion = "Zero Rings";

        // Shoot powershots
        Pose2d powershotsEndPose = runPowershots(timer, new Pose2d());

        // Place first wobble
        turn(Math.toRadians(90));

        Trajectory placeWobbleTrajectory = driveBase.trajectoryBuilder(powershotsEndPose.plus(new Pose2d(0, 0, Math.toRadians(90))))
                .addTemporalMarker(1, () -> shooter.setShooterPower(0))
                .addDisplacementMarker(15, () -> wobbleGrabber.setArmPower(0.7))
                .splineToConstantHeading(new Vector2d(powershotsEndPose.getX() + 24, powershotsEndPose.getY() + 43), 0)
                .build();
        followTrajectory(placeWobbleTrajectory);

        wobbleGrabber.setClawOpen(true);
        waitForTime(timer, 0.25, wobbleGrabber);

        // Get the second wobble
        Trajectory getSecondWobbleBack = driveBase.trajectoryBuilder(placeWobbleTrajectory.end())
                .back(16.5)
                .build();
        followTrajectory(getSecondWobbleBack);

        Trajectory getSecondWobbleStrafe = driveBase.trajectoryBuilder(getSecondWobbleBack.end())
                .strafeLeft(65)
                .build();
        followTrajectory(getSecondWobbleStrafe);

        Trajectory getSecondWobbleForward = driveBase.trajectoryBuilder(getSecondWobbleStrafe.end())
                .forward(14.5)
                .build();
        followTrajectory(getSecondWobbleForward);

        wobbleGrabber.setClawOpen(false);
        waitForTime(timer, 0.5, wobbleGrabber);

        // Place second wobble
        Trajectory placeSecondWobble = driveBase.trajectoryBuilder(getSecondWobbleForward.end())
                .addTemporalMarker(0.25, () -> wobbleGrabber.setArmPower(-0.7))
                .addTemporalMarker(1.5, () -> wobbleGrabber.setArmPower(0))
                .addDisplacementMarker(85, () -> wobbleGrabber.setArmPower(0.7))
                .splineToConstantHeading(new Vector2d(getSecondWobbleForward.end().getX() + 56, getSecondWobbleForward.end().getY()), 0)
                .splineToConstantHeading(new Vector2d(getSecondWobbleForward.end().getX() + 56, getSecondWobbleForward.end().getY() + 3), 0)
                .build();
        followTrajectory(placeSecondWobble);

        wobbleGrabber.setClawOpen(true);
        waitForTime(timer, 0.25, wobbleGrabber);

        Trajectory parkingTrajectory = driveBase.trajectoryBuilder(placeSecondWobble.end())
                .splineToConstantHeading(new Vector2d(placeSecondWobble.end().getX() - 5, placeSecondWobble.end().getY() - 10), 0)
                .build();
        followTrajectory(parkingTrajectory);
    }

    private void oneRing(ElapsedTime timer) {
        autoVersion = "One Ring";

        // Shoot powershots
        Pose2d powershotsEndPose = runPowershots(timer, new Pose2d());

        // Place first wobble
        turn(Math.toRadians(90));

        Trajectory placeWobbleTrajectory = driveBase.trajectoryBuilder(powershotsEndPose.plus(new Pose2d(0, 0, Math.toRadians(90))))
                .addTemporalMarker(1, () -> shooter.setShooterPower(0))
                .addDisplacementMarker(15, () -> wobbleGrabber.setArmPower(0.7))
                .splineToConstantHeading(new Vector2d(powershotsEndPose.getX() + 43, powershotsEndPose.getY() + 23), 0)
                .build();
        followTrajectory(placeWobbleTrajectory);

        wobbleGrabber.setClawOpen(true);
        waitForTime(timer, 0.25, wobbleGrabber);

        // Get the second wobble
        Trajectory getSecondWobbleBack = driveBase.trajectoryBuilder(placeWobbleTrajectory.end())
                .back(9)
                .build();
        followTrajectory(getSecondWobbleBack);

        Trajectory getSecondWobbleStrafe = driveBase.trajectoryBuilder(getSecondWobbleBack.end())
                .strafeLeft(85)
                .build();
        followTrajectory(getSecondWobbleStrafe);

        Trajectory getSecondWobbleForward = driveBase.trajectoryBuilder(getSecondWobbleStrafe.end())
                .forward(24)
                .build();
        followTrajectory(getSecondWobbleForward);

        wobbleGrabber.setClawOpen(false);
        waitForTime(timer, 0.25, wobbleGrabber);

        // Place second wobble
        Trajectory placeSecondWobble = driveBase.trajectoryBuilder(getSecondWobbleForward.end())
                .addTemporalMarker(0.25, () -> wobbleGrabber.setArmPower(-0.7))
                .addTemporalMarker(1.5, () -> wobbleGrabber.setArmPower(0))
                .addTemporalMarker(4, () -> wobbleGrabber.setArmPower(1))
                .splineToConstantHeading(new Vector2d(getSecondWobbleForward.end().getX() + 78, getSecondWobbleForward.end().getY() - 25), 0, DriveBase.getVelocityConstraint(40), DriveBase.getAccelerationConstraint(40))
                .build();
        followTrajectory(placeSecondWobble);

        wobbleGrabber.setClawOpen(true);
        waitForTime(timer, 0.25, wobbleGrabber);

        Trajectory parkingTrajectory = driveBase.trajectoryBuilder(placeSecondWobble.end())
                .splineToConstantHeading(new Vector2d(placeSecondWobble.end().getX() - 19, placeSecondWobble.end().getY() - 10), 0)
                .build();
        followTrajectory(parkingTrajectory);
    }

    private void fourRings(ElapsedTime timer) {
        autoVersion = "Four Rings";

        // Shoot powershots
        Pose2d powershotsEndPose = runPowershots(timer, new Pose2d());

        // Place first wobble
        Trajectory placeWobbleTrajectory = driveBase.trajectoryBuilder(powershotsEndPose)
                .addTemporalMarker(1, () -> shooter.setShooterPower(0))
                .addDisplacementMarker(35, () -> wobbleGrabber.setArmPower(0.7))
                .splineToLinearHeading(new Pose2d(powershotsEndPose.getX() + 44, powershotsEndPose.getY() + 52, 0), 0)
                .build();
        followTrajectory(placeWobbleTrajectory);

        wobbleGrabber.setClawOpen(true);
        waitForTime(timer, 0.25, wobbleGrabber);

        // Move to parking line
        Trajectory parkingLineTrajectory = driveBase.trajectoryBuilder(placeWobbleTrajectory.end())
                .back(34)
                .build();
        followTrajectory(parkingLineTrajectory);
    }

    private Pose2d runPowershots(ElapsedTime timer, Pose2d startPose) {
        
        // From spawn to the first powershot
        Trajectory fromSpawnTrajectory = driveBase.trajectoryBuilder(startPose)
                .addDisplacementMarker(10, () -> shooter.setShooterPower(0.63))
                .splineToLinearHeading(new Pose2d(20, -24, 0), 0)
                .splineToLinearHeading(new Pose2d(58, -24, 0), 0)
                .build();
        followTrajectory(fromSpawnTrajectory);

        // Shoot once
        shoot(timer);

        // Strafe for the second powershot
        Trajectory toSecondTrajectory = driveBase.trajectoryBuilder(fromSpawnTrajectory.end())
                .strafeRight(7.5)
                .build();
        followTrajectory(toSecondTrajectory);

        // Shoot once
        waitForTime(timer, 0.5);
        shoot(timer);

        // Strafe for the third powershot
        Trajectory toThirdTrajectory = driveBase.trajectoryBuilder(toSecondTrajectory.end())
                .strafeRight(7.5)
                .build();
        followTrajectory(toThirdTrajectory);

        // Shoot once
        waitForTime(timer, 0.5);
        shoot(timer);

        return toThirdTrajectory.end();
    }

    private void updateTelemetry() {
        telemetry.addData("Version", autoVersion);
        telemetry.update();
    }

    private void followTrajectory(Trajectory trajectory) {
        driveBase.followTrajectoryAsync(trajectory);
        while(driveBase.getOperationMode() == DriveBase.Mode.FOLLOW_TRAJECTORY && continueRunning()) {
            drivingUpdateCycle();
        }
    }

    private void turn(double angle) {
        driveBase.turnAsync(angle);
        while(driveBase.getOperationMode() == DriveBase.Mode.TURN && continueRunning()) {
            drivingUpdateCycle();
        }
    }

    private void drivingUpdateCycle() {
        driveBase.periodic();
        wobbleGrabber.periodic();
        intakeIndexer.periodic();
        shooter.periodic();
        updateTelemetry();
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