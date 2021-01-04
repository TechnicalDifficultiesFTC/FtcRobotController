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
    private boolean speedLockPressed;

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

        // Shooter Speed Lock
        if(gunnerGamepad.x) {
            if(!speedLockPressed) {
                shooter.toggleSpeedLock();
                speedLockPressed = true;
            }
        } else speedLockPressed = false;

        // Shooter Speed
        if(gunnerGamepad.dpad_up) {
            if(!shooterSpeedAdjusted) {
                shooter.incrementShooterWheelPower(0.05);
                shooterSpeedAdjusted = true;
            }
        } else if(gunnerGamepad.dpad_down) {
            if (!shooterSpeedAdjusted) {
                shooter.incrementShooterWheelPower(-0.05);
                shooterSpeedAdjusted = true;
            }
        } else shooterSpeedAdjusted = false;

        if(gunnerGamepad.y) {
            if(!shooterRampAdjusted) {
                shooter.incrementShooterRampPosition(-9.0 / 180.0);
                shooterRampAdjusted = true;
            }
        }
        else if(gunnerGamepad.a) {
            if(!shooterRampAdjusted) {
                shooter.incrementShooterRampPosition(9.0 / 180.0);
                shooterRampAdjusted = true;
            }
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
