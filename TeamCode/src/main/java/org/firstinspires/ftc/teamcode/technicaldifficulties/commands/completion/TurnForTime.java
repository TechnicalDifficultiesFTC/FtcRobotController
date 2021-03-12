package org.firstinspires.ftc.teamcode.technicaldifficulties.commands.completion;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.DriveBase;

/**
 * Created by Admin on 3/5/2021.
 */

public class TurnForTime extends DriveForTime {

    public TurnForTime(DriveBase driveBase, ElapsedTime timer, Telemetry telemetry, double power, double seconds) {
        super(driveBase, timer, telemetry, new double[] {-power, power, -power, power}, seconds);
    }
}
