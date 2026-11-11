package org.firstinspires.ftc.teamcode.Tuners;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.ManualDrive;

import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.pedro.Constants;
@TeleOp
public class AutoVariableTuner extends OpMode {
    private double SHOTINDEX = 1;
    private double HOODINDEX = 1;
    private double CURRENTINDEX = 1;
    private double TARGETVELOCITY;
    private double HOODDEG;


    private Follower follower;

    @Override
    public void init() {
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
        if (gamepad1.dpadLeftWasReleased()) {
            CURRENTINDEX += 1;
        } if (gamepad1.dpadRightWasReleased()) {
            CURRENTINDEX -= 1;
        }
        if (gamepad1.rightBumperWasReleased()) {
            if (CURRENTINDEX == 1) {
                TARGETVELOCITY += 25;
            } if (CURRENTINDEX == 2) {
                HOODDEG += 0.02;
            }

        } if (gamepad1.startWasPressed()) {
            Pose cornerPose = new Pose(
                    7.04234,
                    6.1811,
                    Math.toRadians(90)); //tune //when robot is in bottom left corner facing to the right


            follower.setPose(cornerPose); // overrides our pose
        }

        follower.update();
        Pose robotPose = follower.pose(); // returns a Pose object
        telemetry.addLine("Use Bumbers to increase and decrease selected variable ");
        telemetry.addLine("Use DPAD LEFT and DPAD RIGHT to switch between variable ");
        telemetry.addLine("Use Start to zero");
        telemetry.addLine("");
        telemetry.addData("Robot X", robotPose.x());
        telemetry.addData("Robot Y", robotPose.y());
        telemetry.addData("Target Velocity", TARGETVELOCITY);
        telemetry.addData("Current Velocity", Hardware.FlyWheel.getVelocity());
        telemetry.addData("Hood DEG", HOODDEG);
        telemetry.addData("Robot Heading", Math.toDegrees(robotPose.heading()));

    }
}


