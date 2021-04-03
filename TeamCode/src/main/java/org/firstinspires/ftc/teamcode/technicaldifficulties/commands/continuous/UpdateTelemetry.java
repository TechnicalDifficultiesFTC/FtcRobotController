package org.firstinspires.ftc.teamcode.technicaldifficulties.commands.continuous;

import com.disnodeteam.dogecommander.Command;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class UpdateTelemetry implements Command {

    private Telemetry telemetry;

    public UpdateTelemetry(Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    @Override
    public void start() {

    }

    @Override
    public void periodic() {
        telemetry.update();
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isCompleted() {
        return false;
    }
}
