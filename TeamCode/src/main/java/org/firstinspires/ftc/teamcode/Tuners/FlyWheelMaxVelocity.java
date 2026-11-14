package org.firstinspires.ftc.teamcode.Tuners;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware;

@TeleOp
public class FlyWheelMaxVelocity extends OpMode {

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
            org.firstinspires.ftc.teamcode.Hardware.FlyWheel.setVelocity(testVelocity);
        } else {
            org.firstinspires.ftc.teamcode.Hardware.FlyWheel.setVelocity(0);

        }

        telemetry.addLine("Hold RIGHT BUMPER to drive forward at test velocity");
        telemetry.addLine("DPAD UP/DOWN to increase/decrease velocity");
        telemetry.addLine("Set MAX_VELOCITY to just below that point");
        telemetry.addLine("");
        telemetry.addData("Target Velocity",      "%.0f", testVelocity);
        telemetry.addData("FlyWheel Actual Velocity", "%.0f",
                org.firstinspires.ftc.teamcode.Hardware.FlyWheel.getVelocity());

        telemetry.update();
    }
}




