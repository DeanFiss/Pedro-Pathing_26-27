package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Constants.DriveConstants;

@TeleOp
public class FieldCentricMecanumTeleOp extends OpMode {
    DcMotorEx FrontLeft;
    DcMotorEx FrontRight;
    DcMotorEx BackRight;
    DcMotorEx BackLeft;

    poseTracker poseTracker;

    double MAX_VELOCITY = 2200; //change with new bot

    // Variable to track button state and prevent spamming resetPosAndIMU()
    private boolean lastOptionsState = false;

    @Override
    public void init() {
        FrontLeft = hardwareMap.get(DcMotorEx.class, "leftFront");
        FrontRight = hardwareMap.get(DcMotorEx.class, "rightFront");
        BackRight = hardwareMap.get(DcMotorEx.class, "rightRear");
        BackLeft = hardwareMap.get(DcMotorEx.class, "leftRear");
        poseTracker = new poseTracker(hardwareMap);

        FrontLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        FrontRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        BackLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        BackRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {
        double y = -gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x;
        double rx = gamepad1.right_stick_x;

        poseTracker.pinpoint.update();

        // Safe rising-edge check: triggers ONLY once when options is pressed down
        boolean currentOptionsState = gamepad1.options;
        if (currentOptionsState && !lastOptionsState) {
            poseTracker.pinpoint.resetPosAndIMU();
        }
        lastOptionsState = currentOptionsState;

        double botHeading = Math.toRadians(poseTracker.pinpoint.getHeading(AngleUnit.DEGREES));

        double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

        rotX = rotX * 1.1; //can change
        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
        double fl = (rotY + rotX + rx) / denominator;
        double bl = (rotY - rotX + rx) / denominator;
        double fr = (rotY - rotX - rx) / denominator;
        double br = (rotY + rotX - rx) / denominator;

        FrontLeft.setVelocity(fl * MAX_VELOCITY);
        BackLeft.setVelocity(bl * MAX_VELOCITY);
        FrontRight.setVelocity(fr * MAX_VELOCITY);
        BackRight.setVelocity(br * MAX_VELOCITY);
    }
}