package org.firstinspires.ftc.teamcode.technicaldifficulties;

public class MiscUtils {

    public static String getMotorPowerAsPercentage(double power) {
        return Math.round(power * 100) + "%";
    }

}
