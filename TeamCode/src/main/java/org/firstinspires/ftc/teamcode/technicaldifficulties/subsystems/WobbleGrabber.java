package org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems;

import com.disnodeteam.dogecommander.Subsystem;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.technicaldifficulties.MiscUtils;

public class WobbleGrabber implements Subsystem {

    // Configuration Variables

    // Subsystem Setup
    private HardwareMap hardwareMap;
    private Telemetry telemetry;

    // Hardware Components
    private DcMotor wobbleArmMotor;
    private Servo wobbleClawServoA;
    private Servo wobbleClawServoB;

    // Control Variables
    private double wobbleArmPower;
    private boolean wobbleClawsOpen;

    public WobbleGrabber(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
    }

    @Override
    public void initHardware() {
        wobbleArmMotor = hardwareMap.get(DcMotor.class, "wobbleArmMotor");
        wobbleClawServoA = hardwareMap.get(Servo.class, "wobbleClawServoA");
        wobbleClawServoB = hardwareMap.get(Servo.class, "wobbleClawServoB");

        stopWobbleArm();
        setWobbleClawsOpen(false);
    }

    @Override
    public void periodic() {
        wobbleArmMotor.setPower(wobbleArmPower);
        wobbleClawServoA.setPosition(wobbleClawsOpen ? 0.6 : 0);
        wobbleClawServoB.setPosition(wobbleClawsOpen ? 0 : 0.6);

        updateTelemtry();
    }

    public void setWobbleClawsOpen(boolean open) {
        this.wobbleClawsOpen = open;
    }

    public void setWobbleArmPower(double power) {
        this.wobbleArmPower = power;
    }

    public void stopWobbleArm() {
        setWobbleArmPower(0);
    }

    private void updateTelemtry() {
        telemetry.addData("Wobble Claw", wobbleClawsOpen ? "Open" : "Closed");
        telemetry.addData("Wobble Arm Power", MiscUtils.getMotorPowerAsPercentage(wobbleArmPower));
        telemetry.update();
    }

}
