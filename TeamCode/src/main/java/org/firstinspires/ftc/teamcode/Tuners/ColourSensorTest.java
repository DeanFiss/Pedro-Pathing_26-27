package org.firstinspires.ftc.teamcode.Tuners;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.SubAssemblies.Intake;

@TeleOp
public class ColourSensorTest extends OpMode {

    Intake intake = new Intake();

    @Override
    public void init() {
        intake.Init(hardwareMap);
    }

    @Override
    public void loop() {
        intake.getDistanceCm(telemetry);


    }
}