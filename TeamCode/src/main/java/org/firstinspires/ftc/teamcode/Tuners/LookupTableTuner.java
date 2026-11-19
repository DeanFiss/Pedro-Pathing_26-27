package org.firstinspires.ftc.teamcode.Tuners;

import androidx.core.math.MathUtils;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Constants.ShooterConstants;
import org.firstinspires.ftc.teamcode.Hardware;

@TeleOp
public class LookupTableTuner extends OpMode {

    double TargetTPS = 1900;
    double HoodAngle = 0;
    Hardware Hardware;

    boolean prevDpadUp = false;
    boolean prevDpadDown = false;
    boolean prevDpadLeft = false;
    boolean prevDpadRight = false;

    @Override
    public void init() {
        Hardware = new Hardware(hardwareMap);
        HoodAngle = MathUtils.clamp(HoodAngle, ShooterConstants.HOOD_SERVO_MIN_POS, ShooterConstants.HOOD_SERVO_MAX_POS);
    }

    @Override
    public void loop() {
        boolean currentDpadUp = gamepad1.dpad_up;
        if (currentDpadUp && !prevDpadUp) {
            TargetTPS += 5;
        }
        prevDpadUp = currentDpadUp;

        boolean currentDpadDown = gamepad1.dpad_down;
        if (currentDpadDown && !prevDpadDown) {
            TargetTPS -= 5;
        }
        prevDpadDown = currentDpadDown;

        boolean currentDpadLeft = gamepad1.dpad_left;
        if (currentDpadLeft && !prevDpadLeft) {
            HoodAngle += 0.01;
        }
        prevDpadLeft = currentDpadLeft;

        boolean currentDpadRight = gamepad1.dpad_right;
        if (currentDpadRight && !prevDpadRight) {
            HoodAngle -= 0.01;
        }
        prevDpadRight = currentDpadRight;

        HoodAngle = MathUtils.clamp(HoodAngle, ShooterConstants.HOOD_SERVO_MIN_POS, ShooterConstants.HOOD_SERVO_MAX_POS);

        if (gamepad1.right_bumper) {
            org.firstinspires.ftc.teamcode.Hardware.FlyWheel.setVelocity(TargetTPS);
        } else {
            org.firstinspires.ftc.teamcode.Hardware.FlyWheel.setVelocity(0);
        }
        org.firstinspires.ftc.teamcode.Hardware.Hood.setPosition(HoodAngle);

        telemetry.addLine("Hold RIGHT BUMPER to drive forward at test velocity");
        telemetry.addLine("DPAD UP/DOWN to increase/decrease velocity");
        telemetry.addLine("DPAD LEFT/RIGHT to increase/decrease hood angle");
        telemetry.addLine("Set MAX_VELOCITY to just below that point");
        telemetry.addLine("");
        telemetry.addData("Target Velocity",      "%.0f", TargetTPS);
        telemetry.addData("FlyWheel Actual Velocity", "%.0f",
                org.firstinspires.ftc.teamcode.Hardware.FlyWheel.getVelocity());
        telemetry.addData("Target Hood Angle",      "%.0f", HoodAngle);
        telemetry.addData("Actual Position", "%.0f",
                org.firstinspires.ftc.teamcode.Hardware.Hood.getPosition());

        telemetry.update();
    }
}