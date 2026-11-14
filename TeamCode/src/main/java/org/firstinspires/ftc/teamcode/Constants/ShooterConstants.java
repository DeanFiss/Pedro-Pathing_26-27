package org.firstinspires.ftc.teamcode.Constants;

import com.pedropathing.math.Pose;


public class ShooterConstants {
    public static Pose GOAL_POS_RED                 = new Pose(138, 138); //Change
    public static double SCORE_HEIGHT               = 29; //inches //Change
    public static double SCORE_ANGLE                = Math.toRadians(-30); //radians //Change
    public static double PASS_THROUGH_POINT_RADIUS  = 5; //inches //Change
    public static double HOOD_MAX_ANGLE             = Math.toRadians(70); //Tune
    public static double HOOD_MIN_ANGLE             = Math.toRadians(30); //Tune
    public static double HOOD_SERVO_MIN_POS         = 1.0; //Tune
    public static double HOOD_SERVO_MAX_POS         = 0.325; //Tune
    public static double GATE_SERVO_CLOSED          = 0;
    public static double GATE_SERVO_OPEN            = 0.2;
    public static double AUTO_SHOOTER_TIME          = 2000; //Tune

}
