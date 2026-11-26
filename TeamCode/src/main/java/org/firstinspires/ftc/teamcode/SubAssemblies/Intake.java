package org.firstinspires.ftc.teamcode.SubAssemblies;

import static org.firstinspires.ftc.teamcode.Hardware.coloursensor;

import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Hardware;

public class Intake {
    private static Hardware hardware;
    private static final double DETECTION_THRESHOLD_CM = 5.0;

    public void Init(HardwareMap ahwMap) {
        hardware = new Hardware(ahwMap);
    }

    public double getDistanceCm(Telemetry telemetry) {
        if (hardware == null || coloursensor == null) {
            telemetry.addData("Intake Sensor", "Not Initialized");
            return Double.NaN;
        }

        // Cast coloursensor to DistanceSensor (works if declared as NormalizedColorSensor or ColorRangeSensor in Hardware)
        double distanceCm = ((DistanceSensor) coloursensor).getDistance(DistanceUnit.CM);

        telemetry.addData("Distance (cm)", "%.2f", distanceCm);
        telemetry.addData("Piece Detected", isGamePieceDetected());

        return distanceCm;
    }

    // Helper method you can call in your OpModes to check if an item is inside the intake
    public static boolean isGamePieceDetected() {
        if (hardware == null || coloursensor == null) {
            return false;
        }
        double distanceCm = ((DistanceSensor) coloursensor).getDistance(DistanceUnit.CM);
        return !Double.isNaN(distanceCm) && distanceCm < DETECTION_THRESHOLD_CM;
    }


}
