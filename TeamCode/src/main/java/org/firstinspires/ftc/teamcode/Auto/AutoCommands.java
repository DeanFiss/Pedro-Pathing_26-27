package org.firstinspires.ftc.teamcode.Auto;


import com.pedropathing.ivy.Command;

import org.firstinspires.ftc.teamcode.Constants.IntakeConstants;
import org.firstinspires.ftc.teamcode.Constants.ShooterConstants;
import org.firstinspires.ftc.teamcode.Hardware;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AutoCommands {

    public static final List<Integer> Velocities = new ArrayList<>(Arrays.asList(0, 500, 1000, 2000));
    public static int currentPresetIndex = 0;
    public static double TargetVelocity;
    public static Command Idle = Command.build()
            .setExecute(() -> {
                Hardware.FlowerRampLeft.setPosition(IntakeConstants.LEFT_FLOWER_RAMP_SERVO_UP_POS);
                Hardware.FlowerRampRight.setPosition(IntakeConstants.RIGHT_FLOWER_RAMP_SERVO_UP_POS);
                Hardware.IntakeBarLeft.setPosition(IntakeConstants.LEFT_INTAKE_SERVO_UP_POS);
                Hardware.IntakeBarRight.setPosition(IntakeConstants.RIGHT_INTAKE_SERVO_UP_POS);
                TargetVelocity = AutoCommands.Velocities.get(AutoCommands.currentPresetIndex);
                Hardware.FlyWheel.setVelocity(TargetVelocity);
                Hardware.IntakeLeft.setVelocity(IntakeConstants.INTAKE_IDLE_VELOCITY);
                Hardware.IntakeRight.setVelocity(IntakeConstants.INTAKE_IDLE_VELOCITY);
            })
            .setEnd(endCondition -> {
                // executed on end
            })
            .requiring(Hardware.FlyWheel, Hardware.FlowerRampLeft, Hardware.FlowerRampRight);


    public static Command Intake = Command.build()
            .setExecute(() -> {
                Hardware.IntakeLeft.setVelocity(IntakeConstants.INTAKE_VELOCITY);
                Hardware.IntakeRight.setVelocity(IntakeConstants.INTAKE_VELOCITY);
                Hardware.IntakeBarLeft.setPosition(IntakeConstants.LEFT_INTAKE_SERVO_DOWN_POS);
                Hardware.IntakeBarRight.setPosition(IntakeConstants.RIGHT_INTAKE_SERVO_DOWN_POS);
            })
            .setEnd(endCondition -> {
                // executed on end
            })
            .requiring(Hardware.IntakeLeft, Hardware.IntakeRight);


    public static Command FlowerIntake = Command.build()
            .setExecute(() -> {
                Hardware.FlowerRampLeft.setPosition(IntakeConstants.LEFT_FLOWER_RAMP_SERVO_DOWN_POS);
                Hardware.FlowerRampRight.setPosition(IntakeConstants.RIGHT_FLOWER_RAMP_SERVO_DOWN_POS);
                Hardware.IntakeBarLeft.setPosition(IntakeConstants.LEFT_INTAKE_SERVO_DOWN_POS);
                Hardware.IntakeBarRight.setPosition(IntakeConstants.RIGHT_INTAKE_SERVO_DOWN_POS);
                Hardware.IntakeLeft.setVelocity(IntakeConstants.INTAKE_VELOCITY);
                Hardware.IntakeRight.setVelocity(IntakeConstants.INTAKE_VELOCITY);
            })
            .setEnd(endCondition -> {
                // executed on end
            })
            .requiring(/* requirements */);


    public static Command Shoot = Command.build()
            .setExecute(() -> {
                Hardware.IntakeRight.setVelocity(IntakeConstants.INTAKE_VELOCITY);
                Hardware.IntakeLeft.setVelocity(IntakeConstants.INTAKE_VELOCITY);
                Hardware.Gate.setPosition(ShooterConstants.GATE_SERVO_OPEN);
            })
            .setEnd(endCondition -> {
                currentPresetIndex = currentPresetIndex + 1;
                Hardware.Gate.setPosition(ShooterConstants.GATE_SERVO_CLOSED);
            })
            .requiring(/* requirements */);
}
