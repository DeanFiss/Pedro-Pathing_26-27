package org.firstinspires.ftc.teamcode.Tuners;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.ManualDrive;

import org.firstinspires.ftc.teamcode.pedro.Constants;
@TeleOp
public class AutoVariableTuner extends OpMode {

    private double FIRSTSHOTVELOCITY;
    private double FIRSTSHOTHOODDEG;
    private double FIRSTSHOTTURRETDEG;
    private double SECONDSHOTVELOCITY;
    private double SECONDSHOTHOODDEG;
    private double SECONDSHOTTURRETDEG;
    private double THIRDSHOTVELOCITY;
    private double THIRDSHOTHOODDEG;
    private double THIRDSHOTTURRETDEG;
    private double FOURTHSHOTVELOCITY;
    private double FOURTHSHOTHOODDEG;
    private double FOURTHSHOTTURRETDEG;
    private double FIFTHSHOTVELOCITY;
    private double FIFTHSHOTHOODDEG;
    private double FIFTHSHOTTURRETDEG;
    private double SHOTINDEX = 1;
    private double HOODINDEX = 1;
    private double TURRETINDEX = 1;
    private double CURRENTINDEX = 1;


    private Follower follower;
    @Override
    public void init(){
        follower = Constants.create(hardwareMap);

    }

    @Override
    public void loop() {
        DrivePowers powers = ManualDrive.fieldCentric(
                -gamepad1.left_stick_y,
                gamepad1.left_stick_x,
                gamepad1.right_stick_x,
                follower.pose().heading());
        follower.manual(powers);



        if (gamepad1.dpadUpWasReleased()) {
            if (CURRENTINDEX == 1) {
                SHOTINDEX += 1;
            } if (CURRENTINDEX == 2) {
                HOODINDEX += 1;
            } if (CURRENTINDEX == 3) {
                TURRETINDEX += 1;
            }

        } if (gamepad1.dpadDownWasReleased()) {
            if (CURRENTINDEX == 1) {
                SHOTINDEX -= 1;
            } if (CURRENTINDEX == 2) {
                HOODINDEX -= 1;
            } if (CURRENTINDEX == 3) {
                TURRETINDEX -= 1;
            }
        } if (gamepad1.dpadLeftWasReleased()) {
             CURRENTINDEX += 1;
        } if (gamepad1.dpadRightWasReleased()) {
             CURRENTINDEX -= 1;
        }

        if (gamepad1.startWasPressed()) {
            Pose cornerPose = new Pose(
                    7.04234,
                    6.1811,
                    Math.toRadians(90)); //tune //when robot is in bottom left corner facing to the right

            follower.setPose(cornerPose); // overrides our pose
        }

        follower.update();
        Pose robotPose = follower.pose(); // returns a Pose object
        telemetry.addData("Robot X", robotPose.x());
        telemetry.addData("Robot Y", robotPose.y());
        telemetry.addData("Robot Heading", Math.toDegrees(robotPose.heading()));
    }
}


