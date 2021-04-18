package org.firstinspires.ftc.teamcode.technicaldifficulties.opmodes.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.disnodeteam.dogecommander.Subsystem;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

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

        // Shoot powershots
        Pose2d powershotsEndPose = runPowershots(timer, new Pose2d());

        // Place first wobble
        Trajectory placeWobbleTrajectory = driveBase.trajectoryBuilder(powershotsEndPose)
                .addTemporalMarker(1, () -> shooter.setShooterPower(0))
                .addDisplacementMarker(15, () -> wobbleGrabber.setArmPower(0.7))
                .splineToLinearHeading(new Pose2d(powershotsEndPose.getX() - 4, powershotsEndPose.getY() + 52, 0), 0)
                .build();
        followTrajectory(placeWobbleTrajectory);

        wobbleGrabber.setClawOpen(true);
        waitForTime(timer, 0.25, wobbleGrabber);

        // Move to parking line
        Trajectory parkingLineTrajectory = driveBase.trajectoryBuilder(placeWobbleTrajectory.end())
                .splineToLinearHeading(new Pose2d(placeWobbleTrajectory.end().getX() - 15, placeWobbleTrajectory.end().getY(), 0), 0)
                .splineToLinearHeading(new Pose2d(placeWobbleTrajectory.end().getX() - 15, placeWobbleTrajectory.end().getY() - 18, 0), 0)
                .splineToLinearHeading(new Pose2d(placeWobbleTrajectory.end().getX() + 14, placeWobbleTrajectory.end().getY() - 18, 0), 0)
                .build();
        followTrajectory(parkingLineTrajectory);
    }

    private void oneRing(ElapsedTime timer) {
        autoVersion = "One Ring";

        // Shoot powershots
        Pose2d powershotsEndPose = runPowershots(timer, new Pose2d());

        // Place first wobble
        Trajectory placeWobbleTrajectory = driveBase.trajectoryBuilder(powershotsEndPose)
                .addTemporalMarker(1, () -> shooter.setShooterPower(0))
                .addDisplacementMarker(15, () -> wobbleGrabber.setArmPower(0.7))
                .splineToLinearHeading(new Pose2d(powershotsEndPose.getX() + 22, powershotsEndPose.getY() + 33, 0), 0)
                .build();
        followTrajectory(placeWobbleTrajectory);

        wobbleGrabber.setClawOpen(true);
        waitForTime(timer, 0.25, wobbleGrabber);

        // Move to parking line
        Trajectory parkingLineTrajectory = driveBase.trajectoryBuilder(placeWobbleTrajectory.end())
                .back(10)
                .build();
        followTrajectory(parkingLineTrajectory);
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
                .addDisplacementMarker(10, () -> shooter.setShooterPower(0.65))
                .splineToLinearHeading(new Pose2d(20, -13, 0), 0)
                .splineToLinearHeading(new Pose2d(58, -13, 0), 0)
                .build();
        followTrajectory(fromSpawnTrajectory);

        // Shoot once
        shoot(timer);

        // Strafe for the second powershot
        Trajectory toSecondTrajectory = driveBase.trajectoryBuilder(fromSpawnTrajectory.end())
                .strafeRight(8)
                .build();
        followTrajectory(toSecondTrajectory);

        // Shoot once
        waitForTime(timer, 1);
        shoot(timer);

        // Strafe for the third powershot
        Trajectory toThirdTrajectory = driveBase.trajectoryBuilder(toSecondTrajectory.end())
                .strafeRight(8 )
                .build();
        followTrajectory(toThirdTrajectory);

        // Shoot once
        waitForTime(timer, 1);
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
            driveBase.periodic();
            wobbleGrabber.periodic();
            intakeIndexer.periodic();
            shooter.periodic();
            updateTelemetry();
        }
    }

    private void turn(double angle) {
        driveBase.turnAsync(angle);
        while(driveBase.getOperationMode() == DriveBase.Mode.TURN && continueRunning()) {
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