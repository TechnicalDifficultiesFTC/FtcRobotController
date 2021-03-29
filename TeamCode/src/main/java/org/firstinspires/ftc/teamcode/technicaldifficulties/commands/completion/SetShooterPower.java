package org.firstinspires.ftc.teamcode.technicaldifficulties.commands.completion;

import com.disnodeteam.dogecommander.Command;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.Shooter;

/**
 * Created by Admin on 3/5/2021.
 */

public class SetShooterPower implements Command {

    private ElapsedTime timer;
    private Telemetry telemetry;

    private Shooter shooter;
    private double power;
    private double seconds;

    private boolean finished;

    public SetShooterPower(Shooter shooter, ElapsedTime timer, Telemetry telemetry, double power, double seconds) {
        this.shooter = shooter;
        this.timer = timer;
        this.telemetry = telemetry;
        this.power = power;
        this.seconds = seconds;
    }

    @Override
    public void start() {
        timer.reset();
        seconds += timer.seconds();
        shooter.setShooterPower(power);
    }

    @Override
    public void periodic() {
        finished = timer.seconds() >= seconds;
        telemetry.addData("Action", "Shooter Power");
        telemetry.update();
    }

    @Override
    public void stop() {
        telemetry.addData("Last Completed Action", "Shooter Power");
        telemetry.update();
    }

    @Override
    public boolean isCompleted() {
        return finished;
    }
}