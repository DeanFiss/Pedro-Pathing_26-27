package org.firstinspires.ftc.teamcode.Auto;

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

import org.firstinspires.ftc.teamcode.Constants.IntakeConstants;
import org.firstinspires.ftc.teamcode.Constants.ShooterConstants;
import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous
public class UpSide_Solo_Back_Park extends LinearOpMode {

    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(56, 8, 270);
    private final Pose point1 = poseFactory.of(56, 10, 270);
    private final Pose path2Start = poseFactory.of(56, 10, 270);
    private final Pose path2 = poseFactory.of(8, 8, 180);
    private final Pose path2Control1 = poseFactory.of(36.6138, 16.6474, 0);
    private final Pose path2Control2 = poseFactory.of(27.2163, 8.1715, 0);
    private final Pose point3Start = poseFactory.of(8, 8, 180);
    private final Pose point3 = poseFactory.of(58, 124, 90);
    private final Pose point3Control1 = poseFactory.of(94.6519, 14.0913, 0);
    private final Pose point3Control2 = poseFactory.of(50.7391, 37.7592, 0);
    private final Pose point3Control3 = poseFactory.of(64.9347, 126.1237, 0);
    private final Pose point3Control4 = poseFactory.of(59.8323, 84.1588, 0);
    private final Pose point3Control5 = poseFactory.of(60.128, 105.7795, 0);
    private final Pose point4Start = poseFactory.of(58, 124, 90);
    private final Pose point4 = poseFactory.of(47, 130, 90);
    private final Pose point4Control1 = poseFactory.of(45.1785, 124.5556, 0);
    private final Pose point5Start = poseFactory.of(47, 130, 90);
    private final Pose point5 = poseFactory.of(47, 134, 90);
    private final Pose point6Start = poseFactory.of(47, 134, 90);
    private final Pose point6 = poseFactory.of(47, 130, 90);
    private final Pose point7Start = poseFactory.of(47, 130, 90);
    private final Pose point7 = poseFactory.of(58, 124, 90);
    private final Pose point7Control1 = poseFactory.of(47.6525, 123.2773, 0);
    private final Pose point8Start = poseFactory.of(58, 124, 90);
    private final Pose point8 = poseFactory.of(12, 47, 180);
    private final Pose point8Control1 = poseFactory.of(73.4389, 5.1076, 0);
    private final Pose point8Control2 = poseFactory.of(54.9813, 31.8061, 0);
    private final Pose point8Control3 = poseFactory.of(24.4916, 50.1248, 0);
    private final Pose point9Start = poseFactory.of(12, 47, 180);
    private final Pose point9 = poseFactory.of(8, 47, 180);
    private final Pose point10Start = poseFactory.of(8, 47, 180);
    private final Pose point10 = poseFactory.of(12, 47, 180);
    private final Pose point11Start = poseFactory.of(12, 47, 180);
    private final Pose point11 = poseFactory.of(58, 10, 270);
    private final Pose point11Control1 = poseFactory.of(42.0043, 48.3034, 0);
    private final Pose point12Start = poseFactory.of(58, 10, 270);
    private final Pose point12 = poseFactory.of(12, 124, 180);
    private final Pose point12Control1 = poseFactory.of(44.7102, 42.7932, 0);
    private final Pose point12Control2 = poseFactory.of(112.3338, 108.4022, 0);
    private final Pose point12Control3 = poseFactory.of(38.2687, 135.6866, 0);
    private final Pose point12Control4 = poseFactory.of(9.8373, 135.9674, 0);

    // Autonomous routine
    public Command autoRoutine() {
        return sequential(
                AutoCommands.Idle,
                follow(follower, path1()),
                AutoCommands.Shoot,
                waitMs(ShooterConstants.AUTO_SHOOTER_TIME),
                AutoCommands.Intake,
                follow(follower, path2()),
                AutoCommands.Idle,
                follow(follower, path3()),
                AutoCommands.Shoot,
                waitMs(ShooterConstants.AUTO_SHOOTER_TIME),
                AutoCommands.Idle,
                follow(follower, path4()),
                AutoCommands.FlowerIntake,
                follow(follower, path5()),
                waitMs(IntakeConstants.AUTO_FLOWER_INTAKE_TIME),
                follow(follower, path6()),
                AutoCommands.Idle,
                follow(follower, path7()),
                AutoCommands.Shoot,
                waitMs(ShooterConstants.AUTO_SHOOTER_TIME),
                AutoCommands.Idle,
                follow(follower, path8()),
                AutoCommands.FlowerIntake,
                follow(follower, path9()),
                waitMs(IntakeConstants.AUTO_FLOWER_INTAKE_TIME),
                follow(follower, path10()),
                AutoCommands.Idle,
                follow(follower, path11()),
                AutoCommands.Shoot,
                waitMs(ShooterConstants.AUTO_SHOOTER_TIME),
                AutoCommands.Idle,
                follow(follower, path12()),
                AutoCommands.Off
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
        return Paths.curve(path2Start, path2Control1, path2Control2, path2).linear(path2Start, path2);
    }

    public Path path3() {
        return Paths.curve(point3Start, point3Control1, point3Control2, point3Control3, point3Control4, point3Control5, point3).linear(point3Start, point3);
    }

    public Path path4() {
        return Paths.curve(point4Start, point4Control1, point4).linear(point4Start, point4);
    }

    public Path path5() {
        return Paths.line(point5Start, point5).linear(point5Start, point5);
    }

    public Path path6() {
        return Paths.line(point6Start, point6).linear(point6Start, point6);
    }

    public Path path7() {
        return Paths.curve(point7Start, point7Control1, point7).linear(point7Start, point7);
    }

    public Path path8() {
        return Paths.curve(point8Start, point8Control1, point8Control2, point8Control3, point8).linear(point8Start, point8);
    }

    public Path path9() {
        return Paths.line(point9Start, point9).linear(point9Start, point9);
    }

    public Path path10() {
        return Paths.line(point10Start, point10).linear(point10Start, point10);
    }

    public Path path11() {
        return Paths.curve(point11Start, point11Control1, point11).linear(point11Start, point11);
    }

    public Path path12() {
        return Paths.curve(point12Start, point12Control1, point12Control2, point12Control3, point12Control4, point12).linear(point12Start, point12);
    }
}