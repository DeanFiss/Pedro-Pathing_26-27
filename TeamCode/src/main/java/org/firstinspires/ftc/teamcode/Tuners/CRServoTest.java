package org.firstinspires.ftc.teamcode.Tuners;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp
public class CRServoTest extends OpMode {

    private CRServo parallaxServo;
    private AnalogInput feedbackAnalog;

    // Set minVoltage to 0.0 to remove the dead zone trapping low voltages at zero
    private final double minVoltage = 0.01;
    private final double maxVoltage = 0.45;

    private double speed = 0;
    private double zeroOffset = 0.0;
    private boolean lastXState = false; // Using X button to zero (since A decreases speed)

    // Smoothing variables for software noise reduction

    @Override
    public void init() {
        parallaxServo = hardwareMap.get(CRServo.class, "crservo");
        feedbackAnalog = hardwareMap.get(AnalogInput.class, "crsencoder");

        telemetry.addData("Status", "Initialized. Ready to start.");
        telemetry.update();
    }

    @Override
    public void loop() {
        double rawVoltage = feedbackAnalog.getVoltage();

        double clampedVoltage = Math.max(minVoltage, Math.min(maxVoltage, rawVoltage));

        double rawAngle = ((clampedVoltage - minVoltage) / (maxVoltage - minVoltage)) * 360.0;

        if (gamepad1.x && !lastXState) {
            zeroOffset = rawAngle;
        }
        lastXState = gamepad1.x;


        double adjustedAngle = rawAngle - zeroOffset;
        if (adjustedAngle < 0) {
            adjustedAngle += 360.0;
        }
        if (adjustedAngle >= 360.0) {
            adjustedAngle -= 360.0;
        }

        if (gamepad1.y) {
            speed += 0.01;
        } else if (gamepad1.a) {
            speed -= 0.01;
        }

        speed = Math.max(-1.0, Math.min(1.0, speed));

        if (gamepad1.left_bumper) {
            parallaxServo.setPower(-speed);
        } else if (gamepad1.right_bumper) {
            parallaxServo.setPower(speed);
        } else {
            parallaxServo.setPower(0.0);
        }

        telemetry.addData("Controls", "Y: Speed+, A: Speed-, X: Zero, Bumpers: Run");
        telemetry.addData("Voltage", "%.3f", rawVoltage);
        telemetry.addData("Zero Offset", "%.1f°", zeroOffset);
        telemetry.addData("Position", "%.1f°", adjustedAngle);
        telemetry.addData("Power", "%.2f", speed);
        telemetry.update();
    }
}