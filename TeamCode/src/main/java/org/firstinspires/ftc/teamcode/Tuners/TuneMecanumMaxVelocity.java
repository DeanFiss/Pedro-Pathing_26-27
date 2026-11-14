package org.firstinspires.ftc.teamcode.Tuners;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Hardware;

@TeleOp
public class TuneMecanumMaxVelocity extends OpMode {

    double testVelocity = 1000;
    Hardware Hardware;

    @Override
    public void init() {
        Hardware = new Hardware(hardwareMap);
    }

    @Override
    public void loop() {
        if (gamepad1.dpad_up)   testVelocity += 5;
        if (gamepad1.dpad_down) testVelocity -= 5;
        testVelocity = Math.max(0, testVelocity);

        if (gamepad1.right_bumper) {
            Hardware.FrontLeft.setVelocity(testVelocity);
            Hardware.FrontRight.setVelocity(testVelocity);
            Hardware.BackLeft.setVelocity(testVelocity);
            Hardware.BackRight.setVelocity(testVelocity);
        } else {
            Hardware.FrontLeft.setPower(0);
            Hardware.FrontRight.setPower(0);
            Hardware.BackLeft.setPower(0);
            Hardware.BackRight.setPower(0);
        }

        telemetry.addLine("Hold RIGHT BUMPER to drive forward at test velocity");
        telemetry.addLine("DPAD UP/DOWN to increase/decrease velocity");
        telemetry.addLine("Increase until motors start skipping or stuttering");
        telemetry.addLine("Set MAX_VELOCITY to just below that point");
        telemetry.addLine("");
        telemetry.addData("Test Velocity",      "%.0f", testVelocity);
        telemetry.addData("FL Actual Velocity", "%.0f", Hardware.FrontLeft.getVelocity());
        telemetry.addData("FR Actual Velocity", "%.0f", Hardware.FrontRight.getVelocity());
        telemetry.addData("BL Actual Velocity", "%.0f", Hardware.BackLeft.getVelocity());
        telemetry.addData("BR Actual Velocity", "%.0f", Hardware.BackRight.getVelocity());
        telemetry.update();
    }
}




