package org.firstinspires.ftc.teamcode.Tuners;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.teamcode.Hardware;

@TeleOp
public class FlyWheelPIDFTuner extends OpMode{
    Hardware Hardware;
    public double  highVelocity = 1500;
    public double lowVelocity = 0;
    double curTargetVelocity = highVelocity;
    double F = 0;
    double P = 0;
    double[] stepSizes = {10.0, 1.0, 0.1, 0.001, 0.001};
    int stepIndex = 1;

    @Override
    public void init() {
        Hardware = new Hardware(hardwareMap);
        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(P, 0, 0, F);
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
       }

        // set new PIDF coefficients
        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(P, 0, 0, F);
        org.firstinspires.ftc.teamcode.Hardware.FlyWheel.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);

        // set Velocity
        org.firstinspires.ftc.teamcode.Hardware.FlyWheel.setVelocity(curTargetVelocity);

        double curVelocity = org.firstinspires.ftc.teamcode.Hardware.FlyWheel.getVelocity();
        double error = curTargetVelocity - curVelocity;

        telemetry.addData("Target Velocity", curTargetVelocity);
        telemetry.addData("Current Velocity", "%.2f", curVelocity);
        telemetry.addData("Error","%.2f", error);
        telemetry.addLine("----------------------------");
        telemetry.addData("Tuning P", "%.4f (D-pad U/D)", P);
        telemetry.addData("Tuning F", "%.4f (D-pad L/R)", F);
        telemetry.addData("Step Size", "%.4f (B Button)", stepSizes[stepIndex]);

    }
}
