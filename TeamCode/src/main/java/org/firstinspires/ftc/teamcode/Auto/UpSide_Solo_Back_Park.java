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

import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous
public class UpSide_Solo_Back_Park extends LinearOpMode {

    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(56, 8, 270);
    private final Pose path1 = poseFactory.of(8, 8, 180);
    private final Pose path1Control1 = poseFactory.of(36.6138, 16.6474, 0);
    private final Pose path1Control2 = poseFactory.of(27.2163, 8.1715, 0);
    private final Pose point2Start = poseFactory.of(8, 8, 180);
    private final Pose point2 = poseFactory.of(58, 124, 90);
    private final Pose point2Control1 = poseFactory.of(94.6519, 14.0913, 0);
    private final Pose point2Control2 = poseFactory.of(50.7391, 37.7592, 0);
    private final Pose point2Control3 = poseFactory.of(64.9347, 126.1237, 0);
    private final Pose point2Control4 = poseFactory.of(59.8323, 84.1588, 0);
    private final Pose point2Control5 = poseFactory.of(60.128, 105.7795, 0);
    private final Pose point3Start = poseFactory.of(58, 124, 90);
    private final Pose point3 = poseFactory.of(47, 133, 90);
    private final Pose point3Control1 = poseFactory.of(45.1785, 124.5556, 0);
    private final Pose point4Start = poseFactory.of(47, 133, 90);
    private final Pose point4 = poseFactory.of(58, 124, 90);
    private final Pose point4Control1 = poseFactory.of(47.6525, 123.2773, 0);
    private final Pose point5Start = poseFactory.of(58, 124, 90);
    private final Pose point5 = poseFactory.of(8, 47, 180);
    private final Pose point5Control1 = poseFactory.of(73.4389, 5.1076, 0);
    private final Pose point5Control2 = poseFactory.of(54.9813, 31.8061, 0);
    private final Pose point5Control3 = poseFactory.of(24.4916, 50.1248, 0);
    private final Pose point6Start = poseFactory.of(8, 47, 180);
    private final Pose point6 = poseFactory.of(58, 10, 270);
    private final Pose point6Control1 = poseFactory.of(42.0043, 48.3034, 0);
    private final Pose point7Start = poseFactory.of(58, 10, 270);
    private final Pose point7 = poseFactory.of(10, 124, 180);
    private final Pose point7Control1 = poseFactory.of(84.0484, 105.9104, 0);
    private final Pose point7Control2 = poseFactory.of(47.8201, 137.4655, 0);
    private final Pose point7Control3 = poseFactory.of(3.9348, 138.6025, 0);
    private final Pose point7Control4 = poseFactory.of(10.024, 130.0555, 0);

    // Autonomous routine
    public Command autoRoutine() {
        return sequential(
                follow(follower, path1()),
                follow(follower, path2()),
                follow(follower, path3()),
                follow(follower, path4()),
                follow(follower, path5()),
                follow(follower, path6()),
                follow(follower, path7())
        );
    }

    @Override
    public void runOpMode() {
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(start);
        follower.update();

        waitForStart();
        schedule(autoRoutine());

        while (opModeIsActive()) {
            follower.update();
            Scheduler.execute();

            telemetry.addData("x", follower.pose().x());
            telemetry.addData("y", follower.pose().y());
            telemetry.addData("heading", follower.pose().heading());

            if (follower.currentPath() != null) {
                telemetry.addData("Current path distance remaining", follower.distanceToEndpoint());
                telemetry.addData("Path number", follower.pathIndex());
            }

            telemetry.update();
        }
    }

    public Path path1() {
        return Paths.curve(start, path1Control1, path1Control2, path1).linear(start, path1);
    }

    public Path path2() {
        return Paths.curve(point2Start, point2Control1, point2Control2, point2Control3, point2Control4, point2Control5, point2).linear(point2Start, point2);
    }

    public Path path3() {
        return Paths.curve(point3Start, point3Control1, point3).linear(point3Start, point3);
    }

    public Path path4() {
        return Paths.curve(point4Start, point4Control1, point4).linear(point4Start, point4);
    }

    public Path path5() {
        return Paths.curve(point5Start, point5Control1, point5Control2, point5Control3, point5).linear(point5Start, point5);
    }

    public Path path6() {
        return Paths.curve(point6Start, point6Control1, point6).linear(point6Start, point6);
    }

    public Path path7() {
        return Paths.curve(point7Start, point7Control1, point7Control2, point7Control3, point7Control4, point7).linear(point7Start, point7);
    }
}