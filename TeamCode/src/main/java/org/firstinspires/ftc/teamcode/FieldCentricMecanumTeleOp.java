package org.firstinspires.ftc.teamcode;


import static org.firstinspires.ftc.teamcode.Hardware.pinpoint;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp
public class FieldCentricMecanumTeleOp extends OpMode {
    double MAX_VELOCITY = 2200; //change with new bot
    private boolean lastOptionsState = false;
    Hardware Hardware;

    @Override
    public void init() {
        Hardware = new Hardware(hardwareMap);
    }

    @Override
    public void loop() {
        double y = -gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x;
        double rx = gamepad1.right_stick_x;

        pinpoint.update();

        // Safe rising-edge check: triggers ONLY once when options is pressed down
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
        Hardware.FrontLeft.setVelocity(fl * MAX_VELOCITY);
        Hardware.BackLeft.setVelocity(bl * MAX_VELOCITY);
        Hardware.FrontRight.setVelocity(fr * MAX_VELOCITY);
        Hardware.BackRight.setVelocity(br * MAX_VELOCITY);
    }
}