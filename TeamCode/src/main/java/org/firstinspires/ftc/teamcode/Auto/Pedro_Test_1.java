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
public class Pedro_Test_1 extends LinearOpMode {

    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(59, 8, 180);
    private final Pose path1Start = poseFactory.of(59, 8, 180);
    private final Pose path1 = poseFactory.of(8, 8, 180);
    private final Pose point2 = poseFactory.of(61, 132, 90);
    private final Pose point2Control1 = poseFactory.of(29.7703, 48.2261, 0);
    private final Pose point2Control2 = poseFactory.of(1.663, 99.1705, 0);
    private final Pose point2Control3 = poseFactory.of(65.0876, 103.9674, 0);
    private final Pose point3 = poseFactory.of(47, 130, 90);
    private final Pose point3Control1 = poseFactory.of(56, 118, 0);
    private final Pose point4 = poseFactory.of(61, 132, 90);
    private final Pose point4Control1 = poseFactory.of(56, 118, 0);
    private final Pose point5 = poseFactory.of(13, 120, 200);
    private final Pose point5Control1 = poseFactory.of(56.6816, 101.0635, 0);
    private final Pose point6 = poseFactory.of(13, 90, 220);
    private final Pose point6Control1 = poseFactory.of(10.5, 105, 0);
    private final Pose point7 = poseFactory.of(59, 8, 270);
    private final Pose point7Control1 = poseFactory.of(26, 45, 0);
    private final Pose point8 = poseFactory.of(14, 47, 180);
    private final Pose point8Control1 = poseFactory.of(44, 50, 0);
    private final Pose point9 = poseFactory.of(59, 8, 270);
    private final Pose point10 = poseFactory.of(12, 95, 0);
    private final Pose point10Control1 = poseFactory.of(26, 45, 0);

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
                follow(follower, path8()),
                follow(follower, path9()),
                follow(follower, path10())
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
        return Paths.line(path1Start, path1).linear(path1Start, path1);
    }

    public Path path2() {
        return Paths.curve(path1, point2Control1, point2Control2, point2Control3, point2).linear(path1, point2);
    }

    public Path path3() {
        return Paths.curve(point2, point3Control1, point3).linear(point2, point3);
    }

    public Path path4() {
        return Paths.curve(point3, point4Control1, point4).linear(point3, point4);
    }

    public Path path5() {
        return Paths.curve(point4, point5Control1, point5).linear(point4, point5);
    }

    public Path path6() {
        return Paths.curve(point5, point6Control1, point6).linear(point5, point6);
    }

    public Path path7() {
        return Paths.curve(point6, point7Control1, point7).linear(point6, point7);
    }

    public Path path8() {
        return Paths.curve(point7, point8Control1, point8).linear(point7, point8);
    }

    public Path path9() {
        return Paths.line(point8, point9).linear(point8, point9);
    }

    public Path path10() {
        return Paths.curve(point9, point10Control1, point10).linear(point9, point10);
    }
}