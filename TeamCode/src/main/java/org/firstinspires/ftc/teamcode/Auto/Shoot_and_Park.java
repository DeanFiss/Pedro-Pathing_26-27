package org.firstinspires.ftc.teamcode.Auto;

import static com.pedropathing.api.Paths.*;
import com.pedropathing.api.Paths;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.commands.Commands.*;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Auto.AutoCommands;
import org.firstinspires.ftc.teamcode.Constants.ShooterConstants;
import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous
public class Shoot_and_Park extends LinearOpMode {

    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(56, 8, 270);
    private final Pose point1 = poseFactory.of(56, 10, 270);
    private final Pose path2Start = poseFactory.of(56, 10, 270);
    private final Pose path2 = poseFactory.of(13, 90, 180);
    private final Pose path2Control1 = poseFactory.of(8.5277, 72.2844, 0);
    Hardware Hardware;

    // Autonomous routine
    public Command autoRoutine() {
        return sequential(
                AutoCommands.Idle,
                waitUntil(() -> Hardware.FlyWheel.getVelocity() >= 1500),
                follow(follower, path1()),
                AutoCommands.Shoot,
                waitMs(ShooterConstants.AUTO_SHOOTER_TIME),
                AutoCommands.Idle,
                follow(follower, path2()),
                AutoCommands.Off
                );
    }


    @Override
    public void runOpMode() {
        Hardware = new Hardware(hardwareMap);




        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(start);
        follower.update();

        waitForStart();
        schedule(AutoCommands.Idle);
        schedule(autoRoutine());

        while (opModeIsActive()) {
            follower.update();
            Scheduler.execute();

            telemetry.addData("x", follower.pose().x());
            telemetry.addData("y", follower.pose().y());
            telemetry.addData("heading", follower.pose().heading());
            telemetry.addData("Shooting Active?", AutoCommands.Shoot.isScheduled());
            telemetry.addData("System Idle?", AutoCommands.Idle.isScheduled());
            telemetry.addData("Flywheel vel", org.firstinspires.ftc.teamcode.Hardware.FlyWheel.getVelocity());

            if (follower.currentPath() != null) {
                telemetry.addData("Current path distance remaining", follower.distanceToEndpoint());
                telemetry.addData("Path number", follower.pathIndex());
            }

            telemetry.update();
        }
    }

    public Path path1() {
        return Paths.line(start, point1).linear(start, point1);
    }

    public Path path2() {
        return Paths.curve(path2Start, path2Control1, path2).linear(path2Start, path2);
    }
}