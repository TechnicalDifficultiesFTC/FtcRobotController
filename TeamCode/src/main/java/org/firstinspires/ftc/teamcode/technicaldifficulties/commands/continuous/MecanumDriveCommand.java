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

        /*
        double r = Math.hypot(gamepad.left_stick_x, gamepad.left_stick_y);
        double robotAngle = Math.atan2(gamepad.left_stick_y, gamepad.left_stick_x) - Math.PI / 4;
        double rightX = gamepad.right_stick_x;
        final double frontLeft = r * Math.cos(robotAngle) + rightX;
        final double frontRight = r * Math.sin(robotAngle) - rightX;
        final double backLeft = r * Math.sin(robotAngle) + rightX;
        final double backRight = r * Math.sin(robotAngle) - rightX;
        */

        double y = -gamepad.right_stick_y;
        double x = gamepad.right_stick_x;
        double lx = gamepad.left_stick_x;

        double frontLeft = y + x + lx;
        double frontRight = y + x - lx;
        double backLeft = y - x + lx;
        double backRight = y - x - lx;

        if(Math.abs(frontLeft) > 1 || Math.abs(frontRight) > 1 || Math.abs(backLeft) > 1|| Math.abs(backRight) > 1) {
            double max = 0;
            max = Math.max(Math.abs(frontLeft), Math.abs(backLeft));
            max = Math.max(Math.abs(frontRight), max);
            max = Math.max(Math.abs(backRight), max);

            frontLeft /= max;
            frontRight /= max;
            backLeft /= max;
            backRight /= max;
        }

        double multiplier = gamepad.left_bumper ? -0.5 : -1;

        driveBase.setPowers(new double[] { frontLeft * multiplier, frontRight * multiplier, backLeft * multiplier, backRight * multiplier });
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