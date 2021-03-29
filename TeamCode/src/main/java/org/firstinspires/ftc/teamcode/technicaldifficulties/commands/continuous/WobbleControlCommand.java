package org.firstinspires.ftc.teamcode.technicaldifficulties.commands.continuous;

import com.disnodeteam.dogecommander.Command;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.WobbleGrabber;

public class WobbleControlCommand implements Command {

    private WobbleGrabber wobbleGrabber;
    private Gamepad gamepad;

    public WobbleControlCommand(WobbleGrabber wobbleGrabber, Gamepad gamepad) {
        this.wobbleGrabber = wobbleGrabber;
        this.gamepad = gamepad;
    }

    @Override
    public void start() {
        wobbleGrabber.setClawOpen(false);
    }

    @Override
    public void periodic() {
        wobbleGrabber.setClawOpen(gamepad.right_bumper);
        wobbleGrabber.setArmPower(gamepad.left_stick_y * 0.7);
    }

    @Override
    public void stop() {
        wobbleGrabber.setClawOpen(false);
        wobbleGrabber.setArmPower(0);
    }

    @Override
    public boolean isCompleted() {
        return false;
    }
}