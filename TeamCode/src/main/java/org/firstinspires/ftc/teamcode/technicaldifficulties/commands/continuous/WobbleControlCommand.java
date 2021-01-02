package org.firstinspires.ftc.teamcode.technicaldifficulties.commands.continuous;

import com.disnodeteam.dogecommander.Command;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.technicaldifficulties.OperationState;
import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.WobbleGrabber;

public class WobbleControlCommand implements Command {

    private WobbleGrabber wobbleGrabber;
    private Gamepad gunnerGamepad;

    public WobbleControlCommand(WobbleGrabber wobbleGrabber, Gamepad gunnerGamepad) {
        this.wobbleGrabber = wobbleGrabber;
        this.gunnerGamepad = gunnerGamepad;
    }

    @Override
    public void start() {
        resetRequiredSubsystems();
    }

    @Override
    public void periodic() {
        // Claws Toggle
        if(gunnerGamepad.right_trigger >= 0.5) wobbleGrabber.setWobbleClawsOpen(true);
        else if(gunnerGamepad.right_bumper) wobbleGrabber.setWobbleClawsOpen(false);

        // Wobble Arm
        wobbleGrabber.setWobbleArmPower(gunnerGamepad.left_stick_y * 0.3);
    }

    @Override
    public void stop() {
        resetRequiredSubsystems();
    }

    @Override
    public boolean isCompleted() {
        return false;
    }

    private void resetRequiredSubsystems() {
        wobbleGrabber.stopWobbleArm();
        wobbleGrabber.setWobbleClawsOpen(false);
    }
}
