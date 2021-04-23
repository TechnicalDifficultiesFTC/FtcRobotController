package org.firstinspires.ftc.teamcode.technicaldifficulties.commands.continuous;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.DriveBase;

public class MaxMecanumDriveCommand extends MecanumDriveCommand {

    public MaxMecanumDriveCommand(DriveBase driveBase, Gamepad driverGamepad) {
        super(driveBase, driverGamepad);
    }

    @Override
    protected double getForwardStick() {
        return gamepad.right_stick_y;
    }

    @Override
    protected double getStrafeStick() {
        if(gamepad.dpad_up) return 0.4;
        if(gamepad.dpad_down) return -0.4;
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
