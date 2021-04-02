package org.firstinspires.ftc.teamcode.technicaldifficulties.commands.completion;

import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.disnodeteam.dogecommander.Command;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.DriveBase;

public class FollowTrajectory implements Command {

    private DriveBase driveBase;
    private Telemetry telemetry;
    private Trajectory trajectory;

    public FollowTrajectory(DriveBase driveBase, Telemetry telemetry, Trajectory trajectory) {
        this.driveBase = driveBase;
        this.telemetry = telemetry;
        this.trajectory = trajectory;
    }

    @Override
    public void start() {
        driveBase.followTrajectoryAsync(trajectory);
    }

    @Override
    public void periodic() {
        telemetry.addData("Mode", driveBase.getOperationMode());
        telemetry.update();
        //driveBase.update();
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isCompleted() {
        return driveBase.getOperationMode() == DriveBase.Mode.IDLE;
        //return !driveBase.isBusy();
    }
}
