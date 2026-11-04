package org.firstinspires.ftc.teamcode.Tuners;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware;
@TeleOp
public class IntakeServosMinMaxPosTuner extends OpMode {

    private double LeftservoPosition;
    private double RightservoPosition;
    private double LeftservoPositionOpen = 0;
    private double LeftservoPositionClosed = 0;
    private double RightservoPositionOpen = 0;
    private double RightservoPositionClosed = 0;


    @Override
    public void init() {

    }

    @Override
    public void loop() {
        if (gamepad1.dpad_up) {
            LeftservoPosition += 0.001;
        } if (gamepad1.dpad_down) {
            LeftservoPosition -= 0.001;
        } if (gamepad1.dpad_left) {
            RightservoPosition += 0.001;
        } if (gamepad1.dpad_right) {
            RightservoPosition -= 0.001;
        }

        if (gamepad1.a) {
            LeftservoPositionOpen = LeftservoPosition;
        } if (gamepad1.b) {
            LeftservoPositionClosed = LeftservoPosition;
        } if (gamepad1.x) {
            RightservoPositionOpen = RightservoPosition;
        } if (gamepad1.y) {
            RightservoPositionClosed = RightservoPosition;
        }
        LeftservoPosition = Math.max(0.0, Math.min(1.0, LeftservoPosition));

        Hardware.IntakeBarLeft.setPosition(LeftservoPosition);
        Hardware.IntakeBarRight.setPosition(RightservoPosition);

        telemetry.addLine("Use DPAD UP and DPAD DOWN to move the Left Intake Servo");
        telemetry.addLine("Use DPAD LEFT and DPAD RIGHT to move the Right Intake Servo");
        telemetry.addLine("Press A to record Left Intake Servo's Up position");
        telemetry.addLine("Press B to record Left Intake Servo's Down position");
        telemetry.addLine("Press x to record Right Intake Servo's Up position");
        telemetry.addLine("Press y to record Right Intake Servo's Down position");
        telemetry.addLine("");
        telemetry.addData("Left Servo's current Position", "%.4f", LeftservoPosition);
        telemetry.addData("Left Servo's Up Position", "%.4f", LeftservoPositionOpen);
        telemetry.addData("Left Servo's Down Position", "%.4f", LeftservoPositionClosed);
        telemetry.addData("Right Servo's current Position", "%.4f", RightservoPosition);
        telemetry.addData("Right Servo's Up Position", "%.4f", RightservoPositionOpen);
        telemetry.addData("Right Servo's Down Position", "%.4f", RightservoPositionClosed);
        telemetry.update();
    }
}
