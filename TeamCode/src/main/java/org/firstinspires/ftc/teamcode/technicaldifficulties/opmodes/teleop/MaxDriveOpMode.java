package org.firstinspires.ftc.teamcode.technicaldifficulties.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.technicaldifficulties.commands.continuous.MaxMecanumDriveCommand;
import org.firstinspires.ftc.teamcode.technicaldifficulties.commands.continuous.MecanumDriveCommand;
import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.DriveBase;

@TeleOp(group = "Competition", name = "Max Drive")
@Disabled
public class MaxDriveOpMode extends CompetitionOpMode {

    @Override
    protected MecanumDriveCommand getMecanumDriveCommand(DriveBase driveBase) {
        return new MaxMecanumDriveCommand(driveBase, gamepad1);
    }

}
