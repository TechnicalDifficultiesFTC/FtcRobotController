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
    }

    @Override
    public void stop() {
        wobbleGrabber.setClawOpen(false);
    }

    @Override
    public boolean isCompleted() {
        return false;
    }
}
