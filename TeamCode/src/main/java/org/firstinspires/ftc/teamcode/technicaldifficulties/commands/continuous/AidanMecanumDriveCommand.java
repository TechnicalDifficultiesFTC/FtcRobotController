package org.firstinspires.ftc.teamcode.technicaldifficulties.commands.continuous;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.DriveBase;

public class AidanMecanumDriveCommand extends MecanumDriveCommand {

    public AidanMecanumDriveCommand(DriveBase driveBase, Gamepad driverGamepad) {
        super(driveBase, driverGamepad);
    }

    @Override
    protected double getForwardStick() {
        if(Math.abs(gamepad.right_stick_y) <= 0.2) return 0;
        return gamepad.right_stick_y;
    }

    @Override
    protected double getStrafeStick() {
        return gamepad.right_stick_x;
    }

    @Override
    protected double getTurnStick() {
        return gamepad.left_stick_x;
    }

    @Override
    protected double getMultiplier() {
        return gamepad.left_bumper ? -0.5 : -1;
    }

}
