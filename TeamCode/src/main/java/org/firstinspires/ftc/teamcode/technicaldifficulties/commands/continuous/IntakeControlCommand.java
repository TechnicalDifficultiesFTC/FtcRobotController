package org.firstinspires.ftc.teamcode.technicaldifficulties.commands.continuous;

import com.disnodeteam.dogecommander.Command;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.IntakeIndexer;

/**
 * Created by Admin on 3/3/2021.
 */

public class IntakeControlCommand implements Command {

    private IntakeIndexer intakeIndexer;
    private Gamepad gamepad;

    public IntakeControlCommand(IntakeIndexer intakeIndexer, Gamepad gamepad) {
        this.intakeIndexer = intakeIndexer;
        this.gamepad = gamepad;
    }

    @Override
    public void start() {
        intakeIndexer.setIntakePower(0);
    }

    @Override
    public void periodic() {
        if(gamepad.right_trigger >= 0.5) intakeIndexer.setIntakePower(1);
        else if(gamepad.left_trigger >= 0.5) intakeIndexer.setIntakePower(-1);
        else intakeIndexer.setIntakePower(0);
    }

    @Override
    public void stop() {
        intakeIndexer.setIntakePower(0);
    }

    @Override
    public boolean isCompleted() {
        return false;
    }
}
