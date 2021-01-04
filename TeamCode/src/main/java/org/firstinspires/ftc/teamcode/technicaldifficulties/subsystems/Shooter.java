package org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems;

import com.disnodeteam.dogecommander.Subsystem;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.technicaldifficulties.MiscUtils;
import org.firstinspires.ftc.teamcode.technicaldifficulties.OperationState;

public class Shooter implements Subsystem {

    // Configuration Variables
    private static final double shooterRampStartingPosition = 1;

    // Subsystem Setup
    private HardwareMap hardwareMap;
    private Telemetry telemetry;

    // Hardware Components
    private DcMotor shooterWheel;
    private Servo shooterRamp;

    // Control Variables
    private OperationState shooterWheelState = OperationState.OFF;
    private double shooterWheelPower;
    private double shooterRampPosition = shooterRampStartingPosition;
    private boolean speedLocked;

    public Shooter(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
    }

    @Override
    public void initHardware() {
        shooterWheel = hardwareMap.get(DcMotor.class, "shooterWheel");
        shooterRamp = hardwareMap.get(Servo.class, "shooterRamp");

        stopShooterWheel();
        resetShooterRampPosition();
    }

    @Override
    public void periodic() {
        switch(shooterWheelState) {
            case FORWARD:
                shooterWheel.setPower(shooterWheelPower);
                break;
            case REVERSE:
                shooterWheel.setPower(-shooterWheelPower);
                break;
            case OFF:
                shooterWheel.setPower(0);
        }
        shooterRamp.setPosition(shooterRampPosition);

        updateTelemtry();
    }

    public void setShooterRampPosition(double position) {
        shooterRampPosition = Range.clip(position, 0, 1);
    }

    public void incrementShooterRampPosition(double incrementValue) {
        setShooterRampPosition(incrementValue + shooterRampPosition);
    }

    public void resetShooterRampPosition() {
        setShooterRampPosition(shooterRampStartingPosition);
    }

    public void setShooterWheelState(OperationState state) {
        shooterWheelState = state;
    }

    public void setShooterWheelPower(double power) {
        if(speedLocked) return;
        shooterWheelPower = Range.clip(power, 0, 1);
    }

    public void incrementShooterWheelPower(double incrementValue) {
        setShooterWheelPower(incrementValue + shooterWheelPower);
    }

    public void stopShooterWheel() {
        setShooterWheelState(OperationState.OFF);
    }

    public void toggleSpeedLock() {
        speedLocked = !speedLocked;
    }

    private void updateTelemtry() {
        telemetry.addData("Shooter State", shooterWheelState);
        telemetry.addData("Shooter Power", MiscUtils.getMotorPowerAsPercentage(shooterWheelPower));
        telemetry.addData("Shooter Power Locked", speedLocked);
        telemetry.addData("Shooter Ramp Position", Math.round(shooterRampPosition * 180) + " Degrees?");
        telemetry.update();
    }
}
