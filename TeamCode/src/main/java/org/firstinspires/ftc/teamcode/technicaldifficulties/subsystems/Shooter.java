package org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems;

import com.disnodeteam.dogecommander.Subsystem;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Shooter implements Subsystem {

    private HardwareMap hardwareMap;
    private Telemetry telemetry;
    private Gamepad gamepad;

    private Servo flickerServo;

    public Shooter(HardwareMap hardwareMap, Telemetry telemetry, Gamepad gamepad) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        this.gamepad = gamepad;
    }

    @Override
    public void initHardware() {
        flickerServo = hardwareMap.get(Servo.class, "lowerFlickerServo");
    }

    @Override
    public void periodic() {
        if(gamepad.x) flickerServo.setPosition(0);
        else if(gamepad.y) flickerServo.setPosition(0.25);
        else if(gamepad.a) flickerServo.setPosition(0.5);
        else if(gamepad.b) flickerServo.setPosition(0.75);
        else flickerServo.setPosition(1);
    }

}
