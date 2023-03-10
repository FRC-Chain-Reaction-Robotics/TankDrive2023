package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.util.Units;

public final class Constants {
    public static final class Elevator
    {
        public static final int kLeftElevatorMotor = 2;
        public static final int kRightElevatorMotor = 6;
    }
    public static final class Drivetrain{

        public static final int kLFMotorID = 10;
        public static final int kRFMotorID = 7;
        public static final int kLBMotorID = 8;
        public static final int kRBMotorID = 3;

        public static final double kTrackwidthMeters = Units.inchesToMeters(18);
	    public static final double kWheelDiameterMeters = Units.inchesToMeters(6);
        public static final DifferentialDriveKinematics kDriveKinematics = new DifferentialDriveKinematics(
			kTrackwidthMeters);
        
        public static final int kEvilStallCurrentLimit = 45;
        public static final int kEvilFreeCurrentLimit  = 45;
        public static final int kGoodStallCurrentLimit = 35;
        public static final int kGoodFreeCurrentLimit  = 35;

        public static final double ksVolts = 0.16;
        public static final double kvVoltSecondsPerMeter = 0;
        public static final double kaVoltSecondsSquaredPerMeter = 0;
        public static final double kPDriveVel = 0;
        public static final double DTD_KP = 1.5;
        public static final double DTD_TOLERANCE = 0.1;

        public static final double kMaxSpeedMetersPerSecond = 1.0;
        public static final double kMaxAccelMetersPerSecondSquared = 1.0;
    }
}
