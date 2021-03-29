package org.firstinspires.ftc.teamcode.technicaldifficulties.commands.completion;

import com.disnodeteam.dogecommander.Command;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems.Shooter;

/**
 * Created by Admin on 3/5/2021.
 */

public class ShootThreeRings implements Command {

    private ElapsedTime timer;
    private Telemetry telemetry;

    private Shooter shooter;
    private double power;

    private int ringsShot = 0;
    private boolean flick = false;
    private boolean flickReturned = true;

    private boolean finished = false;

    public ShootThreeRings(Shooter shooter, ElapsedTime timer, Telemetry telemetry, double power) {
        this.shooter = shooter;
        this.timer = timer;
        this.telemetry = telemetry;
        this.power = power;
    }

    @Override
    public void start() {
        shooter.setShooterPower(power);
        timer.reset();
    }

    @Override
    public void periodic() {
        telemetry.addData("Action", "Shoot 3");
        telemetry.update();
        if(flick) {
            if(timer.seconds() < 0.5) return;
            shooter.setFlick(false);
            flick = false;
            timer.reset();
            return;
        } else if(!flickReturned) {
            if(timer.seconds() < 0.5) return;
            flickReturned = true;
            timer.reset();
            return;
        }
        switch(ringsShot) {
            case 0:
                if(timer.seconds() < 2.5) return;
                shoot();
                return;
            case 1:
                if(timer.seconds() < 1) return;
                shoot();
                return;
            case 2:
                if(timer.seconds() < 1) return;
                shoot();
                return;
            case 3:
                if(timer.seconds() < 1) return;
                finished = true;
                return;
        }
    }

    @Override
    public void stop() {
        shooter.setShooterPower(0);
        shooter.setFlick(false);
    }

    @Override
    public boolean isCompleted() {
        return finished;
    }

    private void shoot() {
        shooter.setFlick(true);
        flick = true;
        flickReturned = false;
        ringsShot++;
        timer.reset();
    }
}