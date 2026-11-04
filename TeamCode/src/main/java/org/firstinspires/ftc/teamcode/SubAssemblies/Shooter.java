package org.firstinspires.ftc.teamcode.SubAssemblies;

import androidx.core.math.MathUtils;
import com.pedropathing.math.Vector;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Constants.ShooterConstants;
import org.firstinspires.ftc.teamcode.poseTracker;

public class Shooter {

    private final Vector robotToGoalVector = new Vector();
    private Vector calculateShotVectorAndUpdateTurret(double robotHeading) {
        //constants
        double g = 32.174 * 12;
        double x = robotToGoalVector.magnitude() - ShooterConstants.PASS_THROUGH_POINT_RADIUS;
        double y = ShooterConstants.SCORE_HEIGHT;
        double a = ShooterConstants.SCORE_ANGLE;

        //calculate initial launch components
        double hoodAngle = MathUtils.clamp(Math.atan(2 * y / x - Math.tan(a)), ShooterConstants.HOOD_MAX_ANGLE,
                ShooterConstants.HOOD_MIN_ANGLE);

        double flywheelSpeed = Math.sqrt(g * x * x / (2 * Math.pow(Math.cos(hoodAngle), 2) * (x * Math.tan(hoodAngle) - y)));

        //get robot velocity and convert it into parallel and perpendicular components
        Vector robotVelocity = new Vector(
                poseTracker.pinpoint.getVelX(DistanceUnit.INCH),
                poseTracker.pinpoint.getVelY(DistanceUnit.INCH)
        );

        double coordinateTheta = robotVelocity.toVector2D().theta() - robotToGoalVector.toVector2D().theta();

        double parallelComponent = -Math.cos(coordinateTheta) * robotVelocity.magnitude();
        double perpendicularComponent = Math.sin(coordinateTheta) * robotVelocity.magnitude();

        //velocity compensation variables
        double vz = flywheelSpeed * Math.sin(hoodAngle);
        double time = x / (flywheelSpeed * Math.cos(hoodAngle));
        double ivr = x / time + parallelComponent;
        double nvr = Math.sqrt(ivr * ivr + perpendicularComponent * perpendicularComponent);
        double ndr = nvr * time;

        //recalculate launch components
        hoodAngle = MathUtils.clamp(Math.atan(vz / nvr), ShooterConstants.HOOD_MAX_ANGLE,
                ShooterConstants.HOOD_MIN_ANGLE);

        flywheelSpeed = Math.sqrt(g * ndr * ndr / (2 * Math.pow(Math.cos(hoodAngle), 2) * (ndr * Math.tan(hoodAngle) - y)));

        //update turret
        double turretVelCompOffset = Math.atan(perpendicularComponent / ivr);
        double turretAngle = Math.toDegrees(robotHeading - robotToGoalVector.toVector2D().theta() + turretVelCompOffset);

        if (turretAngle > 180) {
            turretAngle -= 360;
        }

        if (turretAngle > 185) {
            turretAngle -= 360;
        } else if (turretAngle < -185) {
            turretAngle += 360;
        }



        return new Vector(flywheelSpeed, hoodAngle);
    }
}
