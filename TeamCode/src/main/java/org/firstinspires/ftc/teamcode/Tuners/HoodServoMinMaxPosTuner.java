package org.firstinspires.ftc.teamcode.Tuners;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware;
@TeleOp
public class HoodServoMinMaxPosTuner extends OpMode {

    private double servoPosition;
    private double servoPositionOpen = 0;
    private double servoPositionClosed = 0;

    Hardware Hardware;


    @Override
    public void init() {
        Hardware = new Hardware(hardwareMap);
    }

    @Override
    public void loop() {
        if (gamepad1.dpad_up) {
            servoPosition += 0.001;
        } if (gamepad1.dpad_down) {
            servoPosition -= 0.001;
        }

        if (gamepad1.a) {
            servoPositionOpen = servoPosition;
        } if (gamepad1.b){
            servoPositionClosed = servoPosition;
        }
        servoPosition = Math.max(0.0, Math.min(1.0, servoPosition));

        org.firstinspires.ftc.teamcode.Hardware.Hood.setPosition(servoPosition);

        telemetry.addLine("Use DPAD UP and DPAD DOWN to move the gate");
        telemetry.addLine("Move gate to fully OPEN position");
        telemetry.addLine("Press A to record position as GATE_OPEN");
        telemetry.addLine("Move gate to fully CLOSED position");
        telemetry.addLine("Press B to record position as GATE_CLOSED");
        telemetry.addLine("");
        telemetry.addData("Current Servo Position", "%.4f", servoPosition);
        telemetry.addData("Open Servo Position", "%.4f", servoPositionOpen);
        telemetry.addData("Closed Servo Position", "%.4f", servoPositionClosed);
        telemetry.update();
    }
}
