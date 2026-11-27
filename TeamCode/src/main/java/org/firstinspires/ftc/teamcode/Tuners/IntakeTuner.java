package org.firstinspires.ftc.teamcode.Tuners;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.SubAssemblies.Intake;

@TeleOp
public class IntakeTuner extends LinearOpMode {

    private Intake intake = new Intake();

    @Override
    public void runOpMode() {
        intake.Init(hardwareMap);

        telemetry.addData("Status", "Initialized. Waiting for Start.");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            double currentDistance = intake.getDistanceCm(telemetry);

            Intake.ishasFour();

            telemetry.addData("Distance (cm)", "%.2f", currentDistance);
            telemetry.addData("Game Piece Detected", Intake.isGamePieceDetected());
            telemetry.addData("Has Four (Triggered)", Intake.hasfour);
            telemetry.update();
        }
    }
}