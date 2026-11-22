package org.firstinspires.ftc.teamcode.Tuners;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Constants.TurretConstants;
import org.firstinspires.ftc.teamcode.Hardware;

@TeleOp
public class LimelightTurretTuner extends OpMode {

    private boolean dpadUpPrev = false;
    private boolean dpadDownPrev = false;
    private boolean dpadLeftPrev = false;
    private boolean dpadRightPrev = false;
    private boolean leftBumperPrev = false;
    private boolean rightBumperPrev = false;
    Hardware Hardware;

    @Override
    public void init() {
        Hardware = new Hardware(hardwareMap);
    }

    @Override
    public void start() {
        org.firstinspires.ftc.teamcode.Hardware.limelight.start();
    }

    @Override
    public void loop() {
        if (gamepad1.dpad_up && !dpadUpPrev) {
            TurretConstants.Kp -= 0.002;
        }
        if (gamepad1.dpad_down && !dpadDownPrev) {
            TurretConstants.Kp += 0.002;
        }
        if (gamepad1.dpad_right && !dpadRightPrev) {
            TurretConstants.MIN_COMMAND += 0.005;
        }
        if (gamepad1.dpad_left && !dpadLeftPrev) {
            TurretConstants.MIN_COMMAND = Math.max(0.0, TurretConstants.MIN_COMMAND - 0.005);
        }
        if (gamepad1.right_bumper && !rightBumperPrev) {
            TurretConstants.DEADBAND += 0.25;
        }
        if (gamepad1.left_bumper && !leftBumperPrev) {
            TurretConstants.DEADBAND = Math.max(0.0, TurretConstants.DEADBAND - 0.25);
        }

        dpadUpPrev = gamepad1.dpad_up;
        dpadDownPrev = gamepad1.dpad_down;
        dpadLeftPrev = gamepad1.dpad_left;
        dpadRightPrev = gamepad1.dpad_right;
        leftBumperPrev = gamepad1.left_bumper;
        rightBumperPrev = gamepad1.right_bumper;

        double turretPower = 0.0;
        double tx = 0.0;
        boolean hasTarget = false;

        if (gamepad1.a) {
            LLResult result = org.firstinspires.ftc.teamcode.Hardware.limelight.getLatestResult();
            if (result != null && result.isValid()) {
                hasTarget = true;
                tx = result.getTx();
                double headingError = -tx;

                if (Math.abs(headingError) > TurretConstants.DEADBAND) {
                    if (headingError < 0) {
                        turretPower = (TurretConstants.Kp * headingError) + TurretConstants.MIN_COMMAND;
                    } else {
                        turretPower = (TurretConstants.Kp * headingError) - TurretConstants.MIN_COMMAND;
                    }
                }
            }
        } else {
            turretPower = gamepad1.right_stick_x * 0.5;
        }

        Hardware.Turret.setPower(turretPower);

        telemetry.addData("Target Valid", hasTarget);
        telemetry.addData("tx", "%.2f", tx);
        telemetry.addData("Turret Power", "%.3f", turretPower);
        telemetry.addData("Kp (D-Pad Up/Down)", "%.4f", TurretConstants.Kp);
        telemetry.addData("MIN_COMMAND (D-Pad Left/Right)", "%.3f", TurretConstants.MIN_COMMAND);
        telemetry.addData("DEADBAND (Bumpers)", "%.2f", TurretConstants.DEADBAND);
        telemetry.update();
    }

    @Override
    public void stop() {
        org.firstinspires.ftc.teamcode.Hardware.limelight.stop();
        Hardware.Turret.setPower(0);
    }
}