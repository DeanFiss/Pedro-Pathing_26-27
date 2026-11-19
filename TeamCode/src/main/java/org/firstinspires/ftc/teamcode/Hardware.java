package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Constants.ShooterConstants;


public class Hardware {
    public static DcMotorEx FrontLeft;
    public static DcMotorEx FrontRight;
    public static DcMotorEx BackRight;
    public static DcMotorEx BackLeft;
    public static DcMotorEx FlyWheel;
    public static DcMotorEx Turret;
    public static DcMotorEx IntakeRight;
    public static DcMotorEx IntakeLeft;
    public static Servo FlowerRampRight;
    public static Servo FlowerRampLeft;
    public static Servo IntakeBarRight;
    public static Servo IntakeBarLeft;
    public static Servo Gate;
    public static Servo Hood;

    public static GoBildaPinpointDriver pinpoint;
    public Hardware (HardwareMap hardwareMap) {

        //Motors
        FrontLeft = hardwareMap.get(DcMotorEx.class, "Leftfront");
        FrontRight = hardwareMap.get(DcMotorEx.class, "Rightfront");
        BackRight = hardwareMap.get(DcMotorEx.class, "Rightrear");
        BackLeft = hardwareMap.get(DcMotorEx.class, "Leftrear");
        Turret = hardwareMap.get(DcMotorEx.class, "Turret");
        FlyWheel = hardwareMap.get(DcMotorEx.class, "Flywheel");
        IntakeLeft = hardwareMap.get(DcMotorEx.class, "Intakeleft");
        IntakeRight = hardwareMap.get(DcMotorEx.class, "Intakeright");

        //Drive Motors
        FrontLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        FrontRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        BackLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        BackRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        BackRight.setDirection(DcMotorSimple.Direction.REVERSE);
        FrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        FrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        IntakeLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        IntakeRight.setDirection(DcMotorSimple.Direction.REVERSE);

        Turret.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        FlyWheel.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        IntakeLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        IntakeRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(100, 0, 0, 22);
        FlyWheel.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);

        Turret.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FlyWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        IntakeRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        IntakeLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //Servos
        FlowerRampLeft = hardwareMap.get(Servo.class, "Flowerleft");
        FlowerRampRight = hardwareMap.get(Servo.class, "Flowerright");
        IntakeBarLeft = hardwareMap.get(Servo.class, "Intakesleft");
        IntakeBarRight = hardwareMap.get(Servo.class, "Intakesright");
        Hood = hardwareMap.get(Servo.class, "Hood");
        Gate = hardwareMap.get(Servo.class, "Gate");

        //Gate.scaleRange(ShooterConstants.GATE_SERVO_CLOSED, ShooterConstants.GATE_SERVO_OPEN);
        // Hood.scaleRange(ShooterConstants.HOOD_SERVO_MIN_POS, ShooterConstants.HOOD_SERVO_MAX_POS);

        //Pinpont
        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");//tune mm
        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_SWINGARM_POD);
        pinpoint.setEncoderDirections(
                GoBildaPinpointDriver.EncoderDirection.FORWARD,                          //tune
                GoBildaPinpointDriver.EncoderDirection.FORWARD                           //tune
        );
        pinpoint.resetPosAndIMU();







    }
}