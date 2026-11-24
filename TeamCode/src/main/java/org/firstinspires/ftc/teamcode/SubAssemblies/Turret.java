package org.firstinspires.ftc.teamcode.SubAssemblies;

import static org.firstinspires.ftc.teamcode.Hardware.limelight;

import com.qualcomm.hardware.limelightvision.LLResult;

import org.firstinspires.ftc.teamcode.Constants.TurretConstants;

public class Turret {
    public static double tx;
    public static double headingError;

    public static double turretPower;
    public void GetTurretAngle(){
        LLResult result = limelight.getLatestResult();
        if (result != null && result.isValid()) {
            tx = result.getTx();
            headingError = -tx;

            if (Math.abs(headingError) > TurretConstants.DEADBAND) {
                if (headingError < 0) {
                    turretPower = (TurretConstants.Kp * headingError) + TurretConstants.MIN_COMMAND;
                } else {
                    turretPower = (TurretConstants.Kp * headingError) - TurretConstants.MIN_COMMAND;
                }
            }
        }



    }
}
