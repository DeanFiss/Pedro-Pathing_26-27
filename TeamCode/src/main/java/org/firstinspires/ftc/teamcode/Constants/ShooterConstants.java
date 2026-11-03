package org.firstinspires.ftc.teamcode.Constants;

import com.pedropathing.math.Pose;


public class ShooterConstants {
    public static Pose GOAL_POS_RED = new Pose(138, 138);
    public static double SCORE_HEIGHT = 29; //inches
    public static double SCORE_ANGLE = Math.toRadians(-30); //radians
    public static double PASS_THROUGH_POINT_RADIUS = 5; //inches
    public static double HOOD_MAX_ANGLE            = Math.toRadians(70);
    public static double HOOD_MIN_ANGLE            = Math.toRadians(30);
    public static double HOOD_SERVO_AT_MIN         = 1.0;
    public static double HOOD_SERVO_AT_MAX         = 0.325;
}
