package org.firstinspires.ftc.teamcode.technicaldifficulties.commands.completion;

import com.disnodeteam.dogecommander.Command;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.Shooter;

/**
 * Created by Admin on 3/5/2021.
 */

public class FlickIntoShooter implements Command {

    private ElapsedTime timer;
    private Telemetry telemetry;

    private Shooter shooter;
    private double seconds;

    private boolean finished = false;

    public FlickIntoShooter(Shooter shooter, ElapsedTime timer, Telemetry telemetry, double seconds) {
        this.shooter = shooter;
        this.timer = timer;
        this.telemetry = telemetry;
        this.seconds = seconds;
    }

    @Override
    public void start() {
        shooter.setFlick(true);
        timer.reset();
        seconds += timer.seconds();
    }

    @Override
    public void periodic() {
        finished = timer.seconds() >= seconds;
        telemetry.addData("Action", "Flick");
        telemetry.update();
    }

    @Override
    public void stop() {
        shooter.setFlick(false);
        telemetry.addData("Last Completed Action", "Flick");
        telemetry.update();
    }

    @Override
    public boolean isCompleted() {
        return finished;
    }
}