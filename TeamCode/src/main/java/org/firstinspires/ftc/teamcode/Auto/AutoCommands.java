package org.firstinspires.ftc.teamcode.Auto;



import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.behaviors.InterruptedBehavior;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Constants.IntakeConstants;
import org.firstinspires.ftc.teamcode.Constants.ShooterConstants;
import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.SubAssemblies.Intake;

public class AutoCommands {

    public static final double velocity =  1900;//Tune
    //public static final ArrayList<Double> Hood_Angles = new ArrayList<>(Arrays.asList(0.5, 0.55, 0.4, 0.2)); //Tune
    public static int currentPresetIndex = 0;
    public static double TargetVelocity;
    public static double TargetHoodAngle;
    private Hardware Hardware;
    public static ElapsedTime timer = new ElapsedTime();



    public static Command Idle = Command.build()
            .setExecute(() -> {
                org.firstinspires.ftc.teamcode.Hardware.FlowerRampLeft.setPosition(IntakeConstants.LEFT_FLOWER_RAMP_SERVO_UP_POS);
                org.firstinspires.ftc.teamcode.Hardware.FlowerRampRight.setPosition(IntakeConstants.RIGHT_FLOWER_RAMP_SERVO_UP_POS);
                org.firstinspires.ftc.teamcode.Hardware.IntakeBarLeft.setPosition(IntakeConstants.LEFT_INTAKE_SERVO_UP_POS);
                org.firstinspires.ftc.teamcode.Hardware.IntakeBarRight.setPosition(IntakeConstants.RIGHT_INTAKE_SERVO_UP_POS);
                TargetVelocity = velocity;
                org.firstinspires.ftc.teamcode.Hardware.FlyWheel.setVelocity(TargetVelocity);
                //org.firstinspires.ftc.teamcode.Hardware.Hood.setPosition(TargetHoodAngle);
                org.firstinspires.ftc.teamcode.Hardware.IntakeLeft.setVelocity(IntakeConstants.INTAKE_IDLE_VELOCITY);
                org.firstinspires.ftc.teamcode.Hardware.IntakeRight.setVelocity(IntakeConstants.INTAKE_IDLE_VELOCITY);
            })
            .setPriority(-1)
            .setInterruptedBehavior(InterruptedBehavior.SUSPEND)
            .requiring(
                    org.firstinspires.ftc.teamcode.Hardware.FlyWheel,
                    org.firstinspires.ftc.teamcode.Hardware.FlowerRampLeft,
                    org.firstinspires.ftc.teamcode.Hardware.FlowerRampRight,
                    org.firstinspires.ftc.teamcode.Hardware.IntakeBarLeft,
                    org.firstinspires.ftc.teamcode.Hardware.IntakeBarRight,
                    org.firstinspires.ftc.teamcode.Hardware.IntakeLeft,
                    org.firstinspires.ftc.teamcode.Hardware.IntakeRight,
                    org.firstinspires.ftc.teamcode.Hardware.Hood);


    
    public static Command Intake = Command.build()
            .setStart(() -> {
                timer.reset();
            })
            .setExecute(() -> {
                org.firstinspires.ftc.teamcode.Hardware.IntakeLeft.setVelocity(IntakeConstants.INTAKE_VELOCITY);
                org.firstinspires.ftc.teamcode.Hardware.IntakeRight.setVelocity(IntakeConstants.INTAKE_VELOCITY);
                org.firstinspires.ftc.teamcode.Hardware.IntakeBarLeft.setPosition(IntakeConstants.LEFT_INTAKE_SERVO_DOWN_POS);
                org.firstinspires.ftc.teamcode.Hardware.IntakeBarRight.setPosition(IntakeConstants.RIGHT_INTAKE_SERVO_DOWN_POS);
            })
            .setDone(()-> timer.seconds() >= IntakeConstants.AUTO_INTAKE_TIME || org.firstinspires.ftc.teamcode.SubAssemblies.Intake.isGamePieceDetected())
            .requiring(
                    org.firstinspires.ftc.teamcode.Hardware.IntakeLeft,
                    org.firstinspires.ftc.teamcode.Hardware.IntakeRight,
                    org.firstinspires.ftc.teamcode.Hardware.IntakeBarLeft,
                    org.firstinspires.ftc.teamcode.Hardware.IntakeBarRight);


    public static Command FlowerIntake = Command.build()
            .setStart(() -> {
                timer.reset();
            })
            .setExecute(() -> {
                org.firstinspires.ftc.teamcode.Hardware.FlowerRampLeft.setPosition(IntakeConstants.LEFT_FLOWER_RAMP_SERVO_DOWN_POS);
                org.firstinspires.ftc.teamcode.Hardware.FlowerRampRight.setPosition(IntakeConstants.RIGHT_FLOWER_RAMP_SERVO_DOWN_POS);
                org.firstinspires.ftc.teamcode.Hardware.IntakeBarLeft.setPosition(IntakeConstants.LEFT_INTAKE_SERVO_DOWN_POS);
                org.firstinspires.ftc.teamcode.Hardware.IntakeBarRight.setPosition(IntakeConstants.RIGHT_INTAKE_SERVO_DOWN_POS);
                org.firstinspires.ftc.teamcode.Hardware.IntakeLeft.setVelocity(IntakeConstants.INTAKE_VELOCITY);
                org.firstinspires.ftc.teamcode.Hardware.IntakeRight.setVelocity(IntakeConstants.INTAKE_VELOCITY);
            })
            .setDone(()-> timer.seconds() >= IntakeConstants.AUTO_FLOWER_INTAKE_TIME)
            .requiring(
                    org.firstinspires.ftc.teamcode.Hardware.FlowerRampLeft,
                    org.firstinspires.ftc.teamcode.Hardware.FlowerRampRight,
                    org.firstinspires.ftc.teamcode.Hardware.IntakeBarLeft,
                    org.firstinspires.ftc.teamcode.Hardware.IntakeBarRight,
                    org.firstinspires.ftc.teamcode.Hardware.IntakeLeft,
                    org.firstinspires.ftc.teamcode.Hardware.IntakeRight
            );


    public static Command Shoot = Command.build()
            .setStart(() -> {
                timer.reset();
            })
            .setExecute(() -> {
                org.firstinspires.ftc.teamcode.Hardware.FlyWheel.setVelocity(TargetVelocity);
                org.firstinspires.ftc.teamcode.Hardware.IntakeRight.setVelocity(IntakeConstants.INTAKE_VELOCITY);
                org.firstinspires.ftc.teamcode.Hardware.IntakeLeft.setVelocity(IntakeConstants.INTAKE_VELOCITY);
                org.firstinspires.ftc.teamcode.Hardware.Gate.setPosition(ShooterConstants.GATE_SERVO_OPEN);
            })
            .setDone(() -> org.firstinspires.ftc.teamcode.Hardware.FlyWheel.getVelocity() <= 1400 || timer.seconds() >= ShooterConstants.AUTO_SHOOTER_TIME)
            .setEnd(endCondition -> {
                currentPresetIndex += 1;
                org.firstinspires.ftc.teamcode.Hardware.Gate.setPosition(ShooterConstants.GATE_SERVO_CLOSED);
            })
            .requiring(
                    org.firstinspires.ftc.teamcode.Hardware.IntakeRight,
                    org.firstinspires.ftc.teamcode.Hardware.IntakeLeft,
                    org.firstinspires.ftc.teamcode.Hardware.Gate,
                    org.firstinspires.ftc.teamcode.Hardware.FlyWheel
                    );
    public static Command Off = Command.build()
            .setExecute(() -> {
                org.firstinspires.ftc.teamcode.Hardware.FlowerRampLeft.setPosition(IntakeConstants.LEFT_FLOWER_RAMP_SERVO_UP_POS);
                org.firstinspires.ftc.teamcode.Hardware.FlowerRampRight.setPosition(IntakeConstants.RIGHT_FLOWER_RAMP_SERVO_UP_POS);
                org.firstinspires.ftc.teamcode.Hardware.IntakeBarLeft.setPosition(IntakeConstants.LEFT_INTAKE_SERVO_UP_POS);
                org.firstinspires.ftc.teamcode.Hardware.IntakeBarRight.setPosition(IntakeConstants.RIGHT_INTAKE_SERVO_UP_POS);
                org.firstinspires.ftc.teamcode.Hardware.FlyWheel.setVelocity(0);
                org.firstinspires.ftc.teamcode.Hardware.IntakeLeft.setVelocity(0);
                org.firstinspires.ftc.teamcode.Hardware.IntakeRight.setVelocity(0);
            })
            .setEnd(endCondition -> {
                // executed on end
            })
            .requiring(
                    org.firstinspires.ftc.teamcode.Hardware.FlyWheel,
                    org.firstinspires.ftc.teamcode.Hardware.FlowerRampLeft,
                    org.firstinspires.ftc.teamcode.Hardware.FlowerRampRight,
                    org.firstinspires.ftc.teamcode.Hardware.IntakeBarLeft,
                    org.firstinspires.ftc.teamcode.Hardware.IntakeBarRight,
                    org.firstinspires.ftc.teamcode.Hardware.IntakeLeft,
                    org.firstinspires.ftc.teamcode.Hardware.IntakeRight
            );
}
