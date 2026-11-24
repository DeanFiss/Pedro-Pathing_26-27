package org.firstinspires.ftc.teamcode.Tuners;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Constants.TurretConstants;
import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.SubAssemblies.Shooter;
import org.firstinspires.ftc.teamcode.SubAssemblies.Turret;

@TeleOp
public class LimelightTurretTuner extends OpMode {

    private boolean dpadUpPrev = false;
    private boolean dpadDownPrev = false;
    private boolean dpadLeftPrev = false;
    private boolean dpadRightPrev = false;
    private boolean leftBumperPrev = false;
    private boolean rightBumperPrev = false;
    private Hardware Hardware;
    private Turret Turret;
    private Shooter Shooter;
    private double turretPower;
    private double tx;

    @Override
    public void init() {
        Hardware = new Hardware(hardwareMap);
        Turret = new Turret();
        Shooter = new Shooter();
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
        Turret.GetTurretAngle();

        dpadUpPrev = gamepad1.dpad_up;
        dpadDownPrev = gamepad1.dpad_down;
        dpadLeftPrev = gamepad1.dpad_left;
        dpadRightPrev = gamepad1.dpad_right;
        leftBumperPrev = gamepad1.left_bumper;
        rightBumperPrev = gamepad1.right_bumper;
        if(org.firstinspires.ftc.teamcode.SubAssemblies.Shooter.CanSee) {
            turretPower = org.firstinspires.ftc.teamcode.SubAssemblies.Turret.turretPower;
            tx = org.firstinspires.ftc.teamcode.SubAssemblies.Turret.tx;
        } else {
            turretPower = 0.0;
            tx = 0.0;
        }
        Shooter.GetDistance();
        Turret.GetTurretAngle();

        Hardware.Turret.setPower(turretPower);

        if (org.firstinspires.ftc.teamcode.SubAssemblies.Shooter.CanSee) {
            telemetry.addData("Target Valid", true);
            telemetry.addData("ty (deg)", "%.2f", org.firstinspires.ftc.teamcode.SubAssemblies.Shooter.targetOffsetAngle_Vertical);
            telemetry.addData("Distance (in)", "%.2f", org.firstinspires.ftc.teamcode.SubAssemblies.Shooter.distanceFromLimelightToGoalInches);
        } else {
            telemetry.addData("Target Valid", false);
        }
        telemetry.addData("tx", "%.2f", tx);
        telemetry.addData("Turret Power", "%.3f", turretPower);
        telemetry.addData("Kp (D-Pad Up/Down)", "%.4f", TurretConstants.Kp);
        telemetry.addData("MIN_COMMAND (D-Pad Left/Right)", "%.3f", TurretConstants.MIN_COMMAND);
        telemetry.addData("DEADBAND (Bumpers)", "%.2f", TurretConstants.DEADBAND);
        telemetry.update();
    }
}