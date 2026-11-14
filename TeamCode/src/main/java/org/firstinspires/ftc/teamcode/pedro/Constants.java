package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {
    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set("Leftfront");
        c.frontRightName.set("Rightfront");
        c.backLeftName.set("Leftrear");
        c.backRightName.set("Rightrear");
        c.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.frontRightDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backLeftDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backRightDirection.set(DcMotorSimple.Direction.REVERSE);
    });
    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set("pinpoint");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        c.xPodOffset.set(0.1043565348377378);
        c.yPodOffset.set(2.524183979184609);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });
    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.2904683068896662);
                Controller secondaryTranslationalForward = Controller.proportional(0.1073202779396268);
                Controller primaryTranslationalLateral = Controller.proportional(0.43388847934988994);
                Controller secondaryTranslationalLateral = Controller.proportional(0.16031019940609162);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.010923350430057722));
                c.brake.set(Controller.proportionalFeedforward(0.009284847865549064));

                c.headingFeedback.set(Controller.proportional(3.9246084604625544));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.043986265028185006, 0.009110827946203756));

                c.linearBrakeCoefficients.set(Matrix.diag(0.07215205472595704, 0.032518456879997056));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.002341892672067545, 0.002530050214927819));

                c.maxAchievableForwardVelocity.set(79.77044361134122);
                c.maxAchievableStrafeVelocity.set(59.74277786193844);
                c.naturalForwardDeceleration.set(30.040628137558656);
                c.naturalStrafeDeceleration.set(72.89237997230275);
            }
    );
    public static Follower create(HardwareMap h) {
        return new Follower(
                new PinpointLocalizer(h, localizerConfig),
                new Mecanum(h, drivetrainConfig),
                new Foresight(foresightConfig)
        );
    }
}