package frc.robot;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.util.Units;

public final class Constants {
    public static final class Elevator
    {
        public static final int kLeftElevatorMotor = 3;
        public static final int kRightElevatorMotor = 4;
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
        
        public static final int kEvilStallCurrentLimit = 35;
        public static final int kEvilFreeCurrentLimit  = 35;
        public static final int kGoodStallCurrentLimit = 25;
        public static final int kGoodFreeCurrentLimit  = 25;

        public static final double ksVolts = 0.16;
        public static final double kvVoltSecondsPerMeter = 0;
        public static final double kaVoltSecondsSquaredPerMeter = 0;
        public static final double kPDriveVel = 0;
    }
}
