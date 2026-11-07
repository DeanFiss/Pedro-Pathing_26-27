package org.firstinspires.ftc.teamcode.Tuners;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class TuneMecanumMaxVelocity extends OpMode {

    DcMotorEx frontLeft, frontRight, backLeft, backRight;
    double testVelocity = 1000;

    @Override
    public void init() {
        frontLeft  = hardwareMap.get(DcMotorEx.class, "leftFront");
        frontRight = hardwareMap.get(DcMotorEx.class, "rightFront");
        backLeft   = hardwareMap.get(DcMotorEx.class, "leftRear");
        backRight  = hardwareMap.get(DcMotorEx.class, "rightRear");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void loop() {
        if (gamepad1.dpad_up)   testVelocity += 5;
        if (gamepad1.dpad_down) testVelocity -= 5;
        testVelocity = Math.max(0, testVelocity);

        if (gamepad1.right_bumper) {
            frontLeft.setVelocity(testVelocity);
            frontRight.setVelocity(testVelocity);
            backLeft.setVelocity(testVelocity);
            backRight.setVelocity(testVelocity);
        } else {
            frontLeft.setPower(0);
            frontRight.setPower(0);
            backLeft.setPower(0);
            backRight.setPower(0);
        }

        telemetry.addLine("Hold RIGHT BUMPER to drive forward at test velocity");
        telemetry.addLine("DPAD UP/DOWN to increase/decrease velocity");
        telemetry.addLine("Increase until motors start skipping or stuttering");
        telemetry.addLine("Set MAX_VELOCITY to just below that point");
        telemetry.addLine("");
        telemetry.addData("Test Velocity",      "%.0f", testVelocity);
        telemetry.addData("FL Actual Velocity", "%.0f", frontLeft.getVelocity());
        telemetry.addData("FR Actual Velocity", "%.0f", frontRight.getVelocity());
        telemetry.addData("BL Actual Velocity", "%.0f", backLeft.getVelocity());
        telemetry.addData("BR Actual Velocity", "%.0f", backRight.getVelocity());
        telemetry.update();
    }
}

