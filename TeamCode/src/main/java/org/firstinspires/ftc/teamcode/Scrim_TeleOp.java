package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.Constants.DriveConstants.MAX_VELOCITY;
import static org.firstinspires.ftc.teamcode.Hardware.limelight;
import static org.firstinspires.ftc.teamcode.Hardware.pinpoint;
import static org.firstinspires.ftc.teamcode.SubAssemblies.Turret.turretPower;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Constants.IntakeConstants;
import org.firstinspires.ftc.teamcode.Constants.ShooterConstants;
import org.firstinspires.ftc.teamcode.SubAssemblies.Intake;
import org.firstinspires.ftc.teamcode.SubAssemblies.Intake;
import org.firstinspires.ftc.teamcode.SubAssemblies.Shooter;
import org.firstinspires.ftc.teamcode.SubAssemblies.Turret;

@TeleOp
public class Scrim_TeleOp extends OpMode {
    private boolean lastOptionsState = false;
    private int Inatkeon = 0;
    private double Target = 1700;
    private double HoodPos = 0.0;
    private boolean isRumbleActive = false;
    private boolean shouldRumble = false;
    private Intake intake;



    @Override
    public void init() {
        Hardware hardware = new Hardware(hardwareMap);
        Shooter shooter = new Shooter();
        intake = new Intake();
        org.firstinspires.ftc.teamcode.Hardware.FlowerRampLeft.setPosition(IntakeConstants.LEFT_FLOWER_RAMP_SERVO_UP_POS);
        org.firstinspires.ftc.teamcode.Hardware.FlowerRampRight.setPosition(IntakeConstants.RIGHT_FLOWER_RAMP_SERVO_UP_POS);
    }

    @Override
    public void start() {
        limelight.start();
        intake.Init(hardwareMap);
    }

    @Override
    public void loop() {



        if (Inatkeon == 1) {
            org.firstinspires.ftc.teamcode.Hardware.IntakeLeft.setVelocity(IntakeConstants.INTAKE_VELOCITY);
            org.firstinspires.ftc.teamcode.Hardware.IntakeRight.setVelocity(IntakeConstants.INTAKE_VELOCITY);
        } else if (Inatkeon == 2) {
            org.firstinspires.ftc.teamcode.Hardware.IntakeLeft.setVelocity(-IntakeConstants.INTAKE_VELOCITY);
            org.firstinspires.ftc.teamcode.Hardware.IntakeRight.setVelocity(-IntakeConstants.INTAKE_VELOCITY);
        } else if (Inatkeon == 0) {
            org.firstinspires.ftc.teamcode.Hardware.IntakeLeft.setPower(0);
            org.firstinspires.ftc.teamcode.Hardware.IntakeRight.setPower(0);
        }if (gamepad2.right_bumper) {
            Inatkeon = 1;

        } else if (gamepad2.left_bumper) {
            Inatkeon = 2;
        } else if (gamepad1.left_bumper) {
            Inatkeon = 1;
        } else {
            Inatkeon = 0;
        }

        if (gamepad1.left_bumper) {
            org.firstinspires.ftc.teamcode.Hardware.Gate.setPosition(ShooterConstants.GATE_SERVO_OPEN);
        } else {
            org.firstinspires.ftc.teamcode.Hardware.Gate.setPosition(ShooterConstants.GATE_SERVO_CLOSED);

        } if (Intake.hasfour) {
            shouldRumble = true;
            org.firstinspires.ftc.teamcode.Hardware.IntakeBarLeft.setPosition(IntakeConstants.LEFT_INTAKE_SERVO_UP_POS);
            org.firstinspires.ftc.teamcode.Hardware.IntakeBarRight.setPosition(IntakeConstants.RIGHT_INTAKE_SERVO_UP_POS);
            if (shouldRumble && !isRumbleActive) {
                gamepad1.rumble(1.0, 1.0, Gamepad.RUMBLE_DURATION_CONTINUOUS);
                gamepad2.rumble(1.0, 1.0, Gamepad.RUMBLE_DURATION_CONTINUOUS);
                isRumbleActive = true;
            }
        } else {
            shouldRumble = false;
            gamepad1.stopRumble();
            gamepad2.stopRumble();
            isRumbleActive = false;
            org.firstinspires.ftc.teamcode.Hardware.IntakeBarLeft.setPosition(IntakeConstants.LEFT_INTAKE_SERVO_DOWN_POS);
            org.firstinspires.ftc.teamcode.Hardware.IntakeBarRight.setPosition(IntakeConstants.RIGHT_INTAKE_SERVO_DOWN_POS);
        }

        Intake.isGamePieceDetected();
        Intake.ishasFour();
        Shooter.GetDistance();
        if (org.firstinspires.ftc.teamcode.SubAssemblies.Shooter.CanSee) {
            Hardware.FlyWheel.setVelocity(Target);
            Hardware.Hood.setPosition(HoodPos);
        }
        Target = 8 * org.firstinspires.ftc.teamcode.SubAssemblies.Shooter.distanceFromLimelightToGoalInches + 1160;
        HoodPos = 0.012 * org.firstinspires.ftc.teamcode.SubAssemblies.Shooter.distanceFromLimelightToGoalInches -0.3;




        double y = -gamepad1.left_stick_y;


        double x = gamepad1.left_stick_x;
        double rx = gamepad1.right_stick_x;

        pinpoint.update();

        boolean currentOptionsState = gamepad1.options;
        if (currentOptionsState && !lastOptionsState) {
            pinpoint.resetPosAndIMU();
        }
        lastOptionsState = currentOptionsState;

        double botHeading = Math.toRadians(pinpoint.getHeading(AngleUnit.DEGREES));

        double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

        rotX = rotX * 1.1; //can change
        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
        double fl = (rotY + rotX + rx) / denominator;
        double bl = (rotY - rotX + rx) / denominator;
        double fr = (rotY - rotX - rx) / denominator;
        double br = (rotY + rotX - rx) / denominator;
        org.firstinspires.ftc.teamcode.Hardware.FrontLeft.setVelocity(fl * MAX_VELOCITY);
        org.firstinspires.ftc.teamcode.Hardware.BackLeft.setVelocity(bl * MAX_VELOCITY);
        org.firstinspires.ftc.teamcode.Hardware.FrontRight.setVelocity(fr * MAX_VELOCITY);
        org.firstinspires.ftc.teamcode.Hardware.BackRight.setVelocity(br * MAX_VELOCITY);



        telemetry.addData("Flywheel's current Velocity", "%.4f", org.firstinspires.ftc.teamcode.Hardware.FlyWheel.getVelocity());
        telemetry.addData("Left Intake's current Velocity", "%.4f", org.firstinspires.ftc.teamcode.Hardware.IntakeLeft.getVelocity());
        telemetry.addData("Right Intake's current Velocity", "%.4f", org.firstinspires.ftc.teamcode.Hardware.IntakeRight.getVelocity());
        /*telemetry.addData("Back Right Motor's current Velocity", "%.4f", org.firstinspires.ftc.teamcode.Hardware.BackRight.getVelocity());
        telemetry.addData("Back Left Motor's current Velocity", "%.4f", org.firstinspires.ftc.teamcode.Hardware.BackLeft.getVelocity());
        telemetry.addData("Front Right Motor's current Velocity", "%.4f", org.firstinspires.ftc.teamcode.Hardware.FrontRight.getVelocity());
        telemetry.addData("Front Left Motor's current Velocity", "%.4f", org.firstinspires.ftc.teamcode.Hardware.FrontLeft.getVelocity());*/
        telemetry.addData("FlyWheel Target", "%.4f", Target);
        telemetry.addData("Hood Target", "%.4f", HoodPos);
        telemetry.addData("Has Four?", Intake.hasfour);


        if (org.firstinspires.ftc.teamcode.SubAssemblies.Shooter.CanSee) {
            telemetry.addData("Target Valid", true);
            telemetry.addData("ty (deg)", "%.2f", org.firstinspires.ftc.teamcode.SubAssemblies.Shooter.targetOffsetAngle_Vertical);
            telemetry.addData("Distance (in)", "%.2f", org.firstinspires.ftc.teamcode.SubAssemblies.Shooter.distanceFromLimelightToGoalInches);
        } else {
            telemetry.addData("Target Valid", false);
        }
    }
}