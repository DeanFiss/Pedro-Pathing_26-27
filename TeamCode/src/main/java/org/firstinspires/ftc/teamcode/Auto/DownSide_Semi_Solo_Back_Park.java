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
public class DownSide_Semi_Solo_Back_Park extends LinearOpMode {

    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(63, 133, 90);
    private final Pose point1 = poseFactory.of(69.5135, 126.1495, 70);
    private final Pose point2Start = poseFactory.of(69.5135, 126.1495, 70);
    private final Pose point2 = poseFactory.of(45.3388, 131.5118, 90);
    private final Pose point2Control1 = poseFactory.of(55.5732, 116.9876, 0);
    private final Pose point3Start = poseFactory.of(45.3388, 131.5118, 90);
    private final Pose point3 = poseFactory.of(69.3301, 125.3915, 70);
    private final Pose point3Control1 = poseFactory.of(56.804, 120.4234, 0);
    private final Pose point4Start = poseFactory.of(69.3301, 125.3915, 70);
    private final Pose point4 = poseFactory.of(9.8413, 46, 180);
    private final Pose point4Control1 = poseFactory.of(71.2706, 0, 0);
    private final Pose point4Control2 = poseFactory.of(44.138, 46, 0);
    private final Pose point5Start = poseFactory.of(9.8413, 46, 180);
    private final Pose point5 = poseFactory.of(51.0183, 25.6561, -110);
    private final Pose point5Control1 = poseFactory.of(34.5005, 47.0777, 0);
    private final Pose point6Start = poseFactory.of(51.0183, 25.6561, -110);
    private final Pose point6 = poseFactory.of(10.1351, 8.0787, 180);
    private final Pose point6Control1 = poseFactory.of(46.0458, 12.0108, 0);
    private final Pose point7Start = poseFactory.of(10.1351, 8.0787, 180);
    private final Pose point7 = poseFactory.of(51.3121, 25.5092, -110);
    private final Pose point8Start = poseFactory.of(51.3121, 25.5092, -110);
    private final Pose point8 = poseFactory.of(9.6455, 123.2861, 0);
    private final Pose point8Control1 = poseFactory.of(75.3049, 49.3285, 0);
    private final Pose point8Control2 = poseFactory.of(66.673, 131.5378, 0);

    // Autonomous routine
    public Command autoRoutine() {
        return sequential(
                follow(follower, path1()),
                follow(follower, path2()),
                follow(follower, path3()),
                follow(follower, path4()),
                follow(follower, path5()),
                follow(follower, path6()),
                follow(follower, path7()),
                follow(follower, path8())
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
        return Paths.line(start, point1).linear(start, point1);
    }

    public Path path2() {
        return Paths.curve(point2Start, point2Control1, point2).linear(point2Start, point2);
    }

    public Path path3() {
        return Paths.curve(point3Start, point3Control1, point3).linear(point3Start, point3);
    }

    public Path path4() {
        return Paths.curve(point4Start, point4Control1, point4Control2, point4).linear(point4Start, point4);
    }

    public Path path5() {
        return Paths.curve(point5Start, point5Control1, point5).linear(point5Start, point5);
    }

    public Path path6() {
        return Paths.curve(point6Start, point6Control1, point6).linear(point6Start, point6);
    }

    public Path path7() {
        return Paths.line(point7Start, point7).linear(point7Start, point7);
    }

    public Path path8() {
        return Paths.curve(point8Start, point8Control1, point8Control2, point8).linear(point8Start, point8);
    }
}