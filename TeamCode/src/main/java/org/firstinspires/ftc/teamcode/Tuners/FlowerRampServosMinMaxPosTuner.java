package org.firstinspires.ftc.teamcode.Tuners;

import static org.firstinspires.ftc.teamcode.Hardware.FlowerRampLeft;
import static org.firstinspires.ftc.teamcode.Hardware.FlowerRampRight;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware;
@TeleOp
public class FlowerRampServosMinMaxPosTuner extends OpMode {

    private static double LeftservoPosition = 0;
    private static double RightservoPosition = 1;
    private static double Pos;
    Hardware Hardware;


    @Override
    public void init() {
        Hardware = new Hardware(hardwareMap);

    }

    @Override
    public void loop() {
        RightservoPosition = 1;
        if (gamepad1.dpad_up) {
            Pos += 0.001;
        } if (gamepad1.dpad_down) {
            Pos -= 0.001;
        }
        Pos = Math.max(0.0, Math.min(1.0, Pos));
        FlowerRampLeft.setPosition(Pos);
        FlowerRampRight.setPosition(1-Pos);

        telemetry.addLine("Use DPAD UP and DPAD DOWN to move the Left Flower Servo");
        telemetry.addLine("Use DPAD LEFT and DPAD RIGHT to move the Right Flower Servo");
        telemetry.addLine("");
        telemetry.addData("Pos", "%.4f", Pos);
        telemetry.update();
    }
}
