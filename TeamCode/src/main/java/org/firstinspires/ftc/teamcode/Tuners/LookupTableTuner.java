package org.firstinspires.ftc.teamcode.Tuners;

import static org.firstinspires.ftc.teamcode.Hardware.limelight;

import androidx.core.math.MathUtils;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Constants.IntakeConstants;
import org.firstinspires.ftc.teamcode.Constants.ShooterConstants;
import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.SubAssemblies.Shooter;

@TeleOp
public class LookupTableTuner extends OpMode {

    double TargetTPS = 1700;

    private Hardware Hardware;
    private Shooter Shooter;
    double HoodAngle = 0;

    boolean prevDpadUp = false;
    boolean prevDpadDown = false;
    boolean prevDpadLeft = false;
    boolean prevDpadRight = false;


    @Override
    public void init() {
        Hardware = new Hardware(hardwareMap);
        Shooter = new Shooter();

    }
    @Override
    public void start() {
        limelight.start();
    }

    @Override
    public void loop() {
        if (gamepad1.dpadUpWasReleased()) {
            TargetTPS += 5;
        }

        if (gamepad1.dpadDownWasReleased()) {
            TargetTPS -= 5;
        }

        if (gamepad1.dpad_left) {
            HoodAngle += 0.01;
        }

        if (gamepad1.dpad_right) {
            HoodAngle -= 0.01;
        }



        if (gamepad1.right_bumper) {
            org.firstinspires.ftc.teamcode.Hardware.FlyWheel.setVelocity(TargetTPS);
        } else {
            org.firstinspires.ftc.teamcode.Hardware.FlyWheel.setVelocity(0);
        }
        org.firstinspires.ftc.teamcode.Hardware.Hood.setPosition(MathUtils.clamp(HoodAngle, 0,0.35));
        if (gamepad1.a) {
            org.firstinspires.ftc.teamcode.Hardware.IntakeRight.setVelocity(IntakeConstants.INTAKE_VELOCITY);
            org.firstinspires.ftc.teamcode.Hardware.IntakeLeft.setVelocity(IntakeConstants.INTAKE_VELOCITY);
        } else {
            org.firstinspires.ftc.teamcode.Hardware.IntakeRight.setVelocity(0);
            org.firstinspires.ftc.teamcode.Hardware.IntakeLeft.setVelocity(0

            );
        }

        Shooter.GetDistance();




        telemetry.addLine("Hold RIGHT BUMPER to drive forward at test velocity");
        telemetry.addLine("DPAD UP/DOWN to increase/decrease velocity");
        telemetry.addLine("DPAD LEFT/RIGHT to increase/decrease hood angle");
        telemetry.addLine("Set MAX_VELOCITY to just below that point");
        telemetry.addLine("");
        telemetry.addData("Target Velocity",      "%.0f", TargetTPS);
        telemetry.addData("FlyWheel Actual Velocity", "%.0f",
                org.firstinspires.ftc.teamcode.Hardware.FlyWheel.getVelocity());
        telemetry.addData("Target Hood Angle", "%.2f", HoodAngle);
        telemetry.addData("Actual Position", "%.2f",
                org.firstinspires.ftc.teamcode.Hardware.Hood.getPosition());
        if (org.firstinspires.ftc.teamcode.SubAssemblies.Shooter.CanSee) {
            telemetry.addData("Target Valid", true);
            telemetry.addData("ty (deg)", "%.2f", org.firstinspires.ftc.teamcode.SubAssemblies.Shooter.targetOffsetAngle_Vertical);
            telemetry.addData("Distance (in)", "%.2f", org.firstinspires.ftc.teamcode.SubAssemblies.Shooter.distanceFromLimelightToGoalInches);
        } else {
            telemetry.addData("Target Valid", false);
        }
        telemetry.update();
    }
}