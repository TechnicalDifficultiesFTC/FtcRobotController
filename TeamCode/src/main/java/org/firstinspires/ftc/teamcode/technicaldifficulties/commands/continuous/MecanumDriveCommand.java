package org.firstinspires.ftc.teamcode.technicaldifficulties.commands.continuous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.disnodeteam.dogecommander.Command;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.DriveBase;

public abstract class MecanumDriveCommand implements Command {

    private DriveBase driveBase;
    protected Gamepad gamepad;

    protected boolean autoLineup = false;

    public MecanumDriveCommand(DriveBase driveBase, Gamepad driverGamepad) {
        this.driveBase = driveBase;
        this.gamepad = driverGamepad;
    }

    @Override
    public void start() {
        driveBase.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void periodic() {
        if(gamepad.x) {
            if(!autoLineup) {
                autoLineup = true;
                Trajectory trajectory = driveBase.trajectoryBuilder(driveBase.getPoseEstimate())
                        .splineToLinearHeading(new Pose2d(57, 0, 0), 0)
                        .build();
                driveBase.followTrajectoryAsync(trajectory);
            }
            return;
        } else if(gamepad.y) {
            if(autoLineup) {
                autoLineup = false;
                driveBase.setOperationMode(DriveBase.Mode.IDLE);
            }
        }

        if(autoLineup) return;

        double multiplier = getMultiplier();
        driveBase.setWeightedDrivePower(new Pose2d(getForwardStick() * multiplier, getStrafeStick() * multiplier, getTurnStick() * multiplier));
    }

    @Override
    public void stop() {
        driveBase.setDrivePower(new Pose2d(0, 0, 0));
    }

    @Override
    public boolean isCompleted() {
        return false;
    }

    protected abstract double getForwardStick();
    protected abstract double getStrafeStick();
    protected abstract double getTurnStick();
    protected abstract double getMultiplier();
}
