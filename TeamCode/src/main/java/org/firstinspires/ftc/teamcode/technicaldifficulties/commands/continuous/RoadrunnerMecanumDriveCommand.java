package org.firstinspires.ftc.teamcode.technicaldifficulties.commands.continuous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.disnodeteam.dogecommander.Command;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.RoadrunnerDriveBase;

public class RoadrunnerMecanumDriveCommand implements Command {

    private RoadrunnerDriveBase driveBase;
    private Gamepad gamepad;

    public RoadrunnerMecanumDriveCommand(RoadrunnerDriveBase driveBase, Gamepad driverGamepad) {
        this.driveBase = driveBase;
        this.gamepad = driverGamepad;
    }

    @Override
    public void start() {
        driveBase.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void periodic() {
        driveBase.setWeightedDrivePower(new Pose2d(-gamepad.right_stick_y, -gamepad.right_stick_x, -gamepad.left_stick_x));
    }

    @Override
    public void stop() {
        driveBase.setDrivePower(new Pose2d(0, 0, 0));
    }

    @Override
    public boolean isCompleted() {
        return false;
    }
}