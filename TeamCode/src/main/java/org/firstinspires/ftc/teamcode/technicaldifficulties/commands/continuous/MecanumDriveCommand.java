package org.firstinspires.ftc.teamcode.technicaldifficulties.commands.continuous;

import com.disnodeteam.dogecommander.Command;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.DriveBase;

public class MecanumDriveCommand implements Command {

    private DriveBase driveBase;
    private Gamepad gamepad;

    public MecanumDriveCommand(DriveBase driveBase, Gamepad driverGamepad) {
        this.driveBase = driveBase;
        gamepad = driverGamepad;
    }

    @Override
    public void start() {

    }

    @Override
    public void periodic() {
        // https://ftcforum.firstinspires.org/forum/ftc-technology/android-studio/6361-mecanum-wheels-drive-code-example

        double r = Math.hypot(gamepad.left_stick_x, gamepad.left_stick_y);
        double robotAngle = Math.atan2(gamepad.left_stick_y, gamepad.left_stick_x) - Math.PI / 4;
        double rightX = gamepad.right_stick_x;
        final double frontLeft = r * Math.cos(robotAngle) + rightX;
        final double frontRight = r * Math.sin(robotAngle) - rightX;
        final double backLeft = r * Math.sin(robotAngle) + rightX;
        final double backRight = r * Math.sin(robotAngle) - rightX;

        driveBase.setPowers(new double[] { frontLeft, frontRight, backLeft, backRight });
    }

    @Override
    public void stop() {
        driveBase.setAllPowers(0);
    }

    @Override
    public boolean isCompleted() {
        return false;
    }
}
