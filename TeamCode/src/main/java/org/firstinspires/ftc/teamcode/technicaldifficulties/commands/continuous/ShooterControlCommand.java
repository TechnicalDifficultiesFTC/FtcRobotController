package org.firstinspires.ftc.teamcode.technicaldifficulties.commands.continuous;

import com.disnodeteam.dogecommander.Command;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.Shooter;

/**
 * Created by Admin on 3/3/2021.
 */

public class ShooterControlCommand implements Command {

    private Shooter shooter;
    private Gamepad gamepad;
    private Telemetry telemetry;

    private boolean highPower = true;

    private boolean buttonPressed = false;

    public ShooterControlCommand(Shooter shooter, Gamepad gamepad, Telemetry telemetry) {
        this.shooter = shooter;
        this.gamepad = gamepad;
        this.telemetry = telemetry;
    }

    @Override
    public void start() {
        shooter.setShooterPower(0);
    }

    @Override
    public void periodic() {

        if(gamepad.y) {
            if(!buttonPressed) {
                buttonPressed = true;
                highPower = !highPower;
            }
        } else buttonPressed = false;

        telemetry.addData("Power", highPower ? "HIGH" : "LOW");
        telemetry.update();

        shooter.setShooterPower(gamepad.left_trigger * (highPower ? 1 : 0.8));
        shooter.setFlick(gamepad.right_trigger >= 0.5);
    }

    @Override
    public void stop() {
        shooter.setShooterPower(0);
    }

    @Override
    public boolean isCompleted() {
        return false;
    }
}