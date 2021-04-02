package org.firstinspires.ftc.teamcode.technicaldifficulties.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.technicaldifficulties.commands.continuous.AidanMecanumDriveCommand;
import org.firstinspires.ftc.teamcode.technicaldifficulties.commands.continuous.MecanumDriveCommand;
import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.DriveBase;

@TeleOp(group = "Competition", name = "Aidan Drive")
public class AidanDriveOpMode extends CompetitionOpMode{

    @Override
    protected MecanumDriveCommand getMecanumDriveCommand(DriveBase driveBase) {
        return new AidanMecanumDriveCommand(driveBase, gamepad1);
    }

}
