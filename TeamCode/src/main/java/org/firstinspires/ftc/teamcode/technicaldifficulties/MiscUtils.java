package org.firstinspires.ftc.teamcode.technicaldifficulties;

public class MiscUtils {

    public static String getMotorPowerAsPercentage(double power) {
        return Math.round(power * 100) + "%";
    }

    public static double mmToInches(double mm) {
        return mm * 25.4;
    }

}
