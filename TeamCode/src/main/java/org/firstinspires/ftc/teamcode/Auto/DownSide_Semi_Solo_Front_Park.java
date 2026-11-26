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
public class DownSide_Semi_Solo_Front_Park extends LinearOpMode {

    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(63, 133, 90);
    private final Pose point1 = poseFactory.of(69.5135, 126.1495, 70);
    private final Pose point2Start = poseFactory.of(69.5135, 126.1495, 70);
    private final Pose point2 = poseFactory.of(46.9055, 126.6156, 90);
    private final Pose point2Control1 = poseFactory.of(45.7808, 112.8748, 0);
    private final Pose point3Start = poseFactory.of(46.9055, 126.6156, 90);
    private final Pose point3 = poseFactory.of(46.8585, 132.3646, 90);
    private final Pose point4 = poseFactory.of(46.8985, 125.9559, 90.3584);
    private final Pose point5Start = poseFactory.of(46.8985, 125.9559, 90);
    private final Pose point5 = poseFactory.of(69.3301, 125.3915, 70);
    private final Pose point5Control1 = poseFactory.of(46.0323, 114.1563, 0);
    private final Pose point6Start = poseFactory.of(69.3301, 125.3915, 70);
    private final Pose point6 = poseFactory.of(17.0877, 46.5875, 180);
    private final Pose point6Control1 = poseFactory.of(71.2706, 0, 0);
    private final Pose point6Control2 = poseFactory.of(44.138, 46, 0);
    private final Pose point7Start = poseFactory.of(17.0877, 46.5875, 180);
    private final Pose point7 = poseFactory.of(9.3528, 46.6149, 180);
    private final Pose point8Start = poseFactory.of(9.3528, 46.6149, 180);
    private final Pose point8 = poseFactory.of(17.4583, 46.637, 180);
    private final Pose point9Start = poseFactory.of(17.4583, 46.637, 180);
    private final Pose point9 = poseFactory.of(51.0183, 25.6561, -110);
    private final Pose point9Control1 = poseFactory.of(34.5005, 47.0777, 0);
    private final Pose point10Start = poseFactory.of(51.0183, 25.6561, -110);
    private final Pose point10 = poseFactory.of(10.1351, 8.0787, 180);
    private final Pose point10Control1 = poseFactory.of(46.0458, 12.0108, 0);
    private final Pose point11Start = poseFactory.of(10.1351, 8.0787, 180);
    private final Pose point11 = poseFactory.of(51.3121, 25.5092, -110);
    private final Pose point12Start = poseFactory.of(51.3121, 25.5092, -110);
    private final Pose point12 = poseFactory.of(9.6945, 94.6924, 0);
    private final Pose point12Control1 = poseFactory.of(24.5053, 40.9513, 0);

    // Autonomous routine
    public Command autoRoutine() {
        return sequential(
                follow(follower, path1()),
                AutoCommands.Shoot,
                waitMs(ShooterConstants.AUTO_SHOOTER_TIME),
                follow(follower, path2()),
                AutoCommands.Idle,
                follow(follower, path3()),
                AutoCommands.FlowerIntake,
                waitMs(IntakeConstants.AUTO_FLOWER_INTAKE_TIME),
                follow(follower, path4()),
                AutoCommands.FlowerIntake,
                follow(follower, path5()),
                AutoCommands.Shoot,
                waitMs(ShooterConstants.AUTO_SHOOTER_TIME),
                follow(follower, path6()),
                AutoCommands.Idle,
                follow(follower, path7()),
                AutoCommands.FlowerIntake,
                waitMs(IntakeConstants.AUTO_FLOWER_INTAKE_TIME),
                follow(follower, path8()),
                AutoCommands.FlowerIntake,
                follow(follower, path9()),
                AutoCommands.Shoot,
                waitMs(ShooterConstants.AUTO_SHOOTER_TIME),
                follow(follower, path10()),
                AutoCommands.Intake,
                follow(follower, path11()),
                AutoCommands.Shoot,
                waitMs(ShooterConstants.AUTO_SHOOTER_TIME),
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
        return Paths.curve(point2Start, point2Control1, point2).linear(point2Start, point2);
    }

    public Path path3() {
        return Paths.line(point3Start, point3).linear(point3Start, point3);
    }

    public Path path4() {
        return Paths.line(point3, point4).reverseTangent();
    }

    public Path path5() {
        return Paths.curve(point5Start, point5Control1, point5).linear(point5Start, point5);
    }

    public Path path6() {
        return Paths.curve(point6Start, point6Control1, point6Control2, point6).linear(point6Start, point6);
    }

    public Path path7() {
        return Paths.line(point7Start, point7).linear(point7Start, point7);
    }

    public Path path8() {
        return Paths.line(point8Start, point8).linear(point8Start, point8);
    }

    public Path path9() {
        return Paths.curve(point9Start, point9Control1, point9).linear(point9Start, point9);
    }

    public Path path10() {
        return Paths.curve(point10Start, point10Control1, point10).linear(point10Start, point10);
    }

    public Path path11() {
        return Paths.line(point11Start, point11).linear(point11Start, point11);
    }

    public Path path12() {
        return Paths.curve(point12Start, point12Control1, point12).linear(point12Start, point12);
    }
}