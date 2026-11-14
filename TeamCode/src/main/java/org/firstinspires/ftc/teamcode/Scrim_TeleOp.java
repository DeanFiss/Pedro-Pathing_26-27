package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.Constants.DriveConstants.MAX_VELOCITY;
import static org.firstinspires.ftc.teamcode.Hardware.pinpoint;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Constants.IntakeConstants;
import org.firstinspires.ftc.teamcode.Constants.ShooterConstants;
@TeleOp
public class Scrim_TeleOp extends OpMode {



    private boolean lastOptionsState = false;
    Hardware Hardware;

    @Override
    public void init() {
        Hardware = new Hardware(hardwareMap);

    }


    @Override
    public void loop() {

        if (gamepad1.right_bumper) {
            org.firstinspires.ftc.teamcode.Hardware.IntakeLeft.setVelocity(IntakeConstants.INTAKE_VELOCITY);
            org.firstinspires.ftc.teamcode.Hardware.IntakeRight.setVelocity(IntakeConstants.INTAKE_VELOCITY);

        } else {
            org.firstinspires.ftc.teamcode.Hardware.IntakeLeft.setVelocity(IntakeConstants.INTAKE_IDLE_VELOCITY);
            org.firstinspires.ftc.teamcode.Hardware.IntakeRight.setVelocity(IntakeConstants.INTAKE_IDLE_VELOCITY);

        } if (gamepad1.left_bumper) {
            org.firstinspires.ftc.teamcode.Hardware.IntakeLeft.setVelocity(-IntakeConstants.INTAKE_VELOCITY);
            org.firstinspires.ftc.teamcode.Hardware.IntakeLeft.setVelocity(-IntakeConstants.INTAKE_VELOCITY);
        }

        if (gamepad2.y) {
            org.firstinspires.ftc.teamcode.Hardware.IntakeBarLeft.setPosition(IntakeConstants.LEFT_INTAKE_SERVO_UP_POS);
            org.firstinspires.ftc.teamcode.Hardware.IntakeBarRight.setPosition(IntakeConstants.RIGHT_INTAKE_SERVO_UP_POS);

        } if (gamepad2.a) {
            org.firstinspires.ftc.teamcode.Hardware.IntakeBarLeft.setPosition(IntakeConstants.LEFT_INTAKE_SERVO_DOWN_POS);
            org.firstinspires.ftc.teamcode.Hardware.IntakeBarRight.setPosition(IntakeConstants.RIGHT_INTAKE_SERVO_DOWN_POS);
        } if (gamepad1.b) {
            org.firstinspires.ftc.teamcode.Hardware.Gate.setPosition(ShooterConstants.GATE_SERVO_OPEN);
        } else {
            org.firstinspires.ftc.teamcode.Hardware.Gate.setPosition(ShooterConstants.GATE_SERVO_CLOSED);

        }
        org.firstinspires.ftc.teamcode.Hardware.FlyWheel.setVelocity(1600);


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
        telemetry.addData("Back Right Motor's current Velocity", "%.4f", org.firstinspires.ftc.teamcode.Hardware.BackRight.getVelocity());
        telemetry.addData("Back Left Motor's current Velocity", "%.4f", org.firstinspires.ftc.teamcode.Hardware.BackLeft.getVelocity());
        telemetry.addData("Front Right Motor's current Velocity", "%.4f", org.firstinspires.ftc.teamcode.Hardware.FrontRight.getVelocity());
        telemetry.addData("Front Left Motor's current Velocity", "%.4f", org.firstinspires.ftc.teamcode.Hardware.FrontLeft.getVelocity());

    }
}
