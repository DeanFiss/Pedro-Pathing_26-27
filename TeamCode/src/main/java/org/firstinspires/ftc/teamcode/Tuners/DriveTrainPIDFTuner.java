package org.firstinspires.ftc.teamcode.Tuners;

import static org.firstinspires.ftc.teamcode.Constants.DriveConstants.MAX_VELOCITY;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.teamcode.Hardware;

@TeleOp
public class DriveTrainPIDFTuner extends OpMode{
    Hardware Hardware;
    public double  highVelocity = 1500;
    public double lowVelocity = 0;
    double curTargetVelocity = highVelocity;
    double F = 0;
    double P = 0;
    double D = 0;
    double[] stepSizes = {10.0, 1.0, 0.1, 0.01, 0.001, 0.0001};
    int stepIndex = 1;

    @Override
    public void init() {
        Hardware = new Hardware(hardwareMap);
        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(P, 0, D, F);
        org.firstinspires.ftc.teamcode.Hardware.FlyWheel.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);
        telemetry.addLine("Init complete");

    }

    @Override
    public void loop() {
        // get all our gamepad commands
        // set target velocity
        // update telemetry

        if (gamepad1.yWasPressed()) {
            if (curTargetVelocity == highVelocity) {
                curTargetVelocity = lowVelocity;
            } else { curTargetVelocity = highVelocity;}
        }

        if (gamepad1.bWasPressed()){
            stepIndex = (stepIndex + 1) % stepSizes.length;
        }

        if (gamepad1.dpadLeftWasPressed()){
            F -= stepSizes[stepIndex];
        }
        if (gamepad1.dpadRightWasPressed()) {
            F += stepSizes[stepIndex];
        }
        if (gamepad1.dpadUpWasPressed()) {
            P += stepSizes[stepIndex];
        }
        if (gamepad1.dpadDownWasPressed()) {
            P -= stepSizes[stepIndex];
        } if (gamepad1.rightBumperWasReleased()) {
            D += stepSizes[stepIndex];
        }
        if (gamepad1.leftBumperWasReleased()) {
            D -= stepSizes[stepIndex];
        }

        // set new PIDF coefficients
        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(P, 0, D, F);
        org.firstinspires.ftc.teamcode.Hardware.FrontLeft.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);
        org.firstinspires.ftc.teamcode.Hardware.FrontRight.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);
        org.firstinspires.ftc.teamcode.Hardware.BackLeft.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);
        org.firstinspires.ftc.teamcode.Hardware.BackRight.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);

        // set Velocity
        org.firstinspires.ftc.teamcode.Hardware.FrontLeft.setVelocity(curTargetVelocity);
        org.firstinspires.ftc.teamcode.Hardware.BackLeft.setVelocity(curTargetVelocity);
        org.firstinspires.ftc.teamcode.Hardware.FrontRight.setVelocity(curTargetVelocity);
        org.firstinspires.ftc.teamcode.Hardware.BackRight.setVelocity(curTargetVelocity);

        double curVelocity = (org.firstinspires.ftc.teamcode.Hardware.FrontLeft.getVelocity()   +
                org.firstinspires.ftc.teamcode.Hardware.FrontRight.getVelocity()                +
                org.firstinspires.ftc.teamcode.Hardware.BackRight.getVelocity()                 +
                org.firstinspires.ftc.teamcode.Hardware.BackLeft.getVelocity())                 / 4;

        double error = curTargetVelocity - curVelocity;

        telemetry.addData("Target Velocity", curTargetVelocity);
        telemetry.addData("Current Velocity", "%.2f", curVelocity);
        telemetry.addData("Error","%.2f", error);
        telemetry.addLine("----------------------------");
        telemetry.addData("Tuning P", "%.4f (D-pad U/D)", P);
        telemetry.addData("Tuning F", "%.4f (D-pad L/R)", F);
        telemetry.addData("Tuning D", "%.4f (D-pad L/R)", D);
        telemetry.addData("Step Size", "%.4f (B Button)", stepSizes[stepIndex]);

    }
}
