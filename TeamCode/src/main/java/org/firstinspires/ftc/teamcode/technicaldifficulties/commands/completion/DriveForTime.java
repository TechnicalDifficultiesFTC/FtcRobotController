package org.firstinspires.ftc.teamcode.technicaldifficulties.commands.completion;

import com.disnodeteam.dogecommander.Command;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.old.DriveBaseOld;

/**
 * Created by Admin on 3/5/2021.
 */

public class DriveForTime implements Command {

    public static int test = 0;

    private ElapsedTime timer;
    private Telemetry telemetry;

    private DriveBaseOld driveBase;
    private double[] powers;
    private double seconds;

    private boolean finished = false;

    public DriveForTime(DriveBaseOld driveBase, ElapsedTime timer, Telemetry telemetry, double powers[], double seconds) {
        this.driveBase = driveBase;
        this.timer = timer;
        this.telemetry = telemetry;
        this.powers = powers;
        this.seconds = seconds;
    }

    public DriveForTime(DriveBaseOld driveBase, ElapsedTime timer, Telemetry telemetry, double power, double seconds) {
        this(driveBase, timer, telemetry, new double[] { -power, -power, -power, -power}, seconds);
    }

    @Override
    public void start() {
        timer.reset();
        seconds += timer.seconds();
        driveBase.setPowers(powers);
    }

    @Override
    public void periodic() {
        finished = timer.seconds() > seconds;
        telemetry.addData("Timer Value " + test, timer.seconds());
        telemetry.addData("Action", "Drive for Time");
        telemetry.update();
    }

    @Override
    public void stop() {
        driveBase.setAllPowers(0);
        telemetry.addData("Last Completed Action", "Drive for Time");
        telemetry.update();
        test++;
    }

    @Override
    public boolean isCompleted() {
        return finished;
    }
}