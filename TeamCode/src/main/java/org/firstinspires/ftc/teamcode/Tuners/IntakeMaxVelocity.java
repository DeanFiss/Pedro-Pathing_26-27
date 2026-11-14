package org.firstinspires.ftc.teamcode.Tuners;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Hardware;

@TeleOp
public class IntakeMaxVelocity extends OpMode {

    double testVelocity = 20;
    Hardware Hardware;

    @Override
    public void init() {
        Hardware = new Hardware(hardwareMap);
    }

    @Override
    public void loop() {
        if (gamepad1.dpad_up)   testVelocity += 5;
        if (gamepad1.dpad_down) testVelocity -= 5;



        if (gamepad1.right_bumper) {
            Hardware.IntakeLeft.setVelocity(testVelocity);
            Hardware.IntakeRight.setVelocity(testVelocity);
        } else {
            Hardware.IntakeLeft.setPower(0);
            Hardware.IntakeRight.setPower(0);
        }

        telemetry.addLine("Hold RIGHT BUMPER to drive forward at test velocity");
        telemetry.addLine("DPAD UP/DOWN to increase/decrease velocity");
        telemetry.addLine("Set MAX_VELOCITY to just below that point");
        telemetry.addLine("");
        telemetry.addData("Target Velocity",      "%.0f", testVelocity);
        telemetry.addData("FL Actual Velocity", "%.0f", Hardware.IntakeLeft.getVelocity());
        telemetry.addData("FR Actual Velocity", "%.0f", Hardware.IntakeRight.getVelocity());
        telemetry.update();
    }
}




