package org.firstinspires.ftc.teamcode.technicaldifficulties.commands.completion;

import com.disnodeteam.dogecommander.Command;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by Admin on 3/5/2021.
 */

public class Wait implements Command {

    private ElapsedTime timer;
    private Telemetry telemetry;

    private double seconds;

    private boolean finished = false;

    public Wait(ElapsedTime timer, Telemetry telemetry, double seconds) {
        this.timer = timer;
        this.telemetry  = telemetry;
        this.seconds = seconds;
    }

    @Override
    public void start() {
        timer.reset();
        seconds += timer.seconds();
    }

    @Override
    public void periodic() {
        finished = timer.seconds() >= seconds;
        telemetry.addData("Action", "Wait");
        telemetry.update();
    }

    @Override
    public void stop() {
        telemetry.addData("Last Completed Action", "Wait");
        telemetry.update();
    }

    @Override
    public boolean isCompleted() {
        return finished;
    }
}
