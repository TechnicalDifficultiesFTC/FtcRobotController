package org.firstinspires.ftc.teamcode.technicaldifficulties.commands.continuous;

import com.disnodeteam.dogecommander.Command;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.technicaldifficulties.OperationState;
import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.Shooter;

public class ShooterControlCommand implements Command {

    private Shooter shooter;
    private Gamepad gunnerGamepad;

    // REDO THIS SYSTEM LATER
    private boolean shooterSpeedAdjusted;
    private boolean shooterRampAdjusted;

    public ShooterControlCommand(Shooter shooter, Gamepad gunnerGamepad) {
        this.shooter = shooter;
        this.gunnerGamepad = gunnerGamepad;
    }

    @Override
    public void start() {
        resetRequiredSubsystems();
        shooter.setShooterWheelPower(0.7);
    }

    @Override
    public void periodic() {
        // Shooter Toggle
        if(gunnerGamepad.dpad_right) shooter.setShooterWheelState(OperationState.FORWARD);
        else if(gunnerGamepad.dpad_left) shooter.setShooterWheelState(OperationState.REVERSE);
        else shooter.setShooterWheelState(OperationState.OFF);

        // Shooter Speed
        if(gunnerGamepad.dpad_up) {
            if(!shooterSpeedAdjusted) shooter.incrementShooterWheelPower(0.05);
        } else if(gunnerGamepad.dpad_down) {
            if (!shooterSpeedAdjusted) shooter.incrementShooterWheelPower(-0.05);
        } else shooterSpeedAdjusted = false;

        // Shooter Ramp (REDO TO MOVE DEGREES)
        if(gunnerGamepad.y) {
            if(!shooterRampAdjusted) shooter.incrementShooterRampPosition(-0.055);
        }
        else if(gunnerGamepad.a) {
            if(!shooterRampAdjusted) shooter.incrementShooterRampPosition(0.055);
        } else shooterRampAdjusted = false;
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
        shooter.stopShooterWheel();
        shooter.resetShooterRampPosition();
    }
}
