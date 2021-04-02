package org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.old;

import com.disnodeteam.dogecommander.Subsystem;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class DriveBaseOld implements Subsystem {

    private HardwareMap hardwareMap;
    private Telemetry telemetry;

    // Front Left, Front Right, Back Left, Back Right
    private final String[] driveMotorNames = { "frontLeft", "frontRight", "backLeft", "backRight" };
    private DcMotor[] driveMotors = new DcMotor[4];
    private double[] driveMotorPowers =  new double[] {0, 0, 0, 0};

    public DriveBaseOld(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
    }

    @Override
    public void initHardware() {
        for(int i = 0; i < 4; i++) driveMotors[i] = hardwareMap.get(DcMotor.class, driveMotorNames[i]);

        driveMotors[1].setDirection(DcMotorSimple.Direction.REVERSE);
        driveMotors[3].setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void periodic() {
        for(int i = 0; i < 4; i++) {
            driveMotors[i].setPower(driveMotorPowers[i]);
        }
    }

    public void setPowers(double[] powers) {
        if(powers.length >= 4) driveMotorPowers = powers;
    }

    public void setAllPowers(double power) {
        driveMotorPowers = new double[] {power, power, power, power};
    }
}