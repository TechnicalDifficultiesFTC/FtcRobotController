package org.firstinspires.ftc.teamcode.technicaldifficulties.commands.continuous;

import com.disnodeteam.dogecommander.Command;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.IntakeIndexer;

/**
 * Created by Admin on 3/3/2021.
 */

public class IntakeControlCommand implements Command {

    private IntakeIndexer intakeIndexer;
    private Gamepad driverGamepad;
    private Gamepad gunnerGamepad;

    private boolean sideArmButtonPressed;

    public IntakeControlCommand(IntakeIndexer intakeIndexer, Gamepad driverGamepad, Gamepad gunnerGamepad) {
        this.intakeIndexer = intakeIndexer;
        this.driverGamepad = driverGamepad;
        this.gunnerGamepad = gunnerGamepad;
    }

    @Override
    public void start() {
        intakeIndexer.setIntakePower(0);
        sideArmButtonPressed = false;
    }

    @Override
    public void periodic() {
        if(driverGamepad.right_trigger >= 0.5 || gunnerGamepad.right_stick_y <= -0.5) intakeIndexer.setIntakePower(1);
        else if(driverGamepad.left_trigger >= 0.5 || gunnerGamepad.right_stick_y >= 0.5) intakeIndexer.setIntakePower(-1);
        else intakeIndexer.setIntakePower(0);

        if(driverGamepad.dpad_right || gunnerGamepad.dpad_right) {
            if(!sideArmButtonPressed) {
                intakeIndexer.toggleSideArmState();
                sideArmButtonPressed = true;
            }
        } else sideArmButtonPressed = false;
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