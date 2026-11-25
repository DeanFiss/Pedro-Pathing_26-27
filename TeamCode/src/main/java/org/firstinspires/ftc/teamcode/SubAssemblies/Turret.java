package org.firstinspires.ftc.teamcode.SubAssemblies;

import static org.firstinspires.ftc.teamcode.Hardware.limelight;
import static org.firstinspires.ftc.teamcode.Hardware.Turret;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Constants.TurretConstants;

public class Turret {
    public static double tx;
    public static double headingError;
    public static double turretPower;
    private static boolean bPrev = false;
    private static boolean isZeroing = false;

    public void GetTurretAngle(Gamepad gamepad2) {
        boolean bPressed = gamepad2.b && !bPrev;
        bPrev = gamepad2.b;

        if (bPressed) {
            isZeroing = true;
        }

        if (isZeroing) {
            int currentPosition = Turret.getCurrentPosition();
            if (currentPosition > 5) {
                turretPower = -0.3;
            } else if (currentPosition < -5) {
                turretPower = 0.3;
            } else {
                turretPower = 0;
                isZeroing = false;
            }
        } else {
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
                } else {
                    turretPower = 0;
                }
            } else {
                turretPower = 0;
            }

            double currentPosition = Turret.getCurrentPosition();
            double maxTicks = TurretConstants.MAX_TICKS;

            if (currentPosition >= maxTicks && turretPower > 0) {
                turretPower = 0;
            } else if (currentPosition <= -maxTicks && turretPower < 0) {
                turretPower = 0;
            }
        }

        Turret.setPower(turretPower);
    }
}