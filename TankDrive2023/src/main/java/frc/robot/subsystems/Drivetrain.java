package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
//import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drivetrain extends SubsystemBase{
    CANSparkMax leftFront = new CANSparkMax(Constants.Drivetrain.kLFMotorID, MotorType.kBrushless);
	CANSparkMax leftBack = new CANSparkMax(Constants.Drivetrain.kLBMotorID, MotorType.kBrushless);
	CANSparkMax rightFront = new CANSparkMax(Constants.Drivetrain.kRFMotorID, MotorType.kBrushless);
	CANSparkMax rightBack = new CANSparkMax(Constants.Drivetrain.kRBMotorID, MotorType.kBrushless);

	RelativeEncoder lfEncoder = leftFront.getEncoder();
	RelativeEncoder lbEncoder = leftBack.getEncoder();
	RelativeEncoder rfEncoder = rightFront.getEncoder();
	RelativeEncoder rbEncoder = rightBack.getEncoder();

	MotorControllerGroup left = new MotorControllerGroup(leftFront, leftBack);
	MotorControllerGroup right = new MotorControllerGroup(rightFront, rightBack);;

	DifferentialDrive dt = new DifferentialDrive(left, right);

	Field2d field = new Field2d();

	// Gyro gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
	AHRS gyro = new AHRS(SPI.Port.kMXP);

	private static final double output = 1.0;

	
	
	

	private DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(	new Rotation2d(), 0, 0, new Pose2d());

	public Drivetrain()
	{

		
		lfEncoder.setPositionConversionFactor(0.4788 / 10.71);
		rfEncoder.setPositionConversionFactor(0.4788 / 10.71);
		lfEncoder.setVelocityConversionFactor(0.4788 / 10.71);
		rfEncoder.setVelocityConversionFactor(0.4788 / 10.71);

		lfEncoder.setPosition(0);
		lbEncoder.setPosition(0);
		rfEncoder.setPosition(0);
		rbEncoder.setPosition(0);
		

		goodMode();
			
		leftFront.setSmartCurrentLimit(Constants.Drivetrain.kGoodStallCurrentLimit, Constants.Drivetrain.kGoodFreeCurrentLimit);
		rightFront.setSmartCurrentLimit(Constants.Drivetrain.kGoodStallCurrentLimit, Constants.Drivetrain.kGoodFreeCurrentLimit);
		rightBack.setSmartCurrentLimit(Constants.Drivetrain.kGoodStallCurrentLimit, Constants.Drivetrain.kGoodFreeCurrentLimit);
		leftBack.setSmartCurrentLimit(Constants.Drivetrain.kGoodStallCurrentLimit, Constants.Drivetrain.kGoodFreeCurrentLimit);


		leftFront.setInverted(false);
		leftBack.setInverted(false);
		rightFront.setInverted(true);
		rightBack.setInverted(true);

		leftFront.burnFlash();
		leftBack.burnFlash();
		rightFront.burnFlash();
		rightBack.burnFlash();

		SmartDashboard.putData(field);
		setOutput(1);
		
		// evilMode();
	}

	public void arcadeDrive(double xSpeed, double zRotation)
	{
		dt.arcadeDrive(output * xSpeed, output * zRotation);
	}

	public double getAngle()
	{
		return gyro.getAngle();
	}

	public void resetSensors()
	{
		resetEncoders();
		resetGyro();
	}


	public void setOutput(double x)
	{
		dt.setMaxOutput(x);
	}

	public void resetEncoders()
	{
		lfEncoder.setPosition(0);
		rfEncoder.setPosition(0);
	}

	public void resetGyro()
	{
		gyro.reset();
		gyro.calibrate();
	}

	@Override
	public void periodic()
	{
		// This method will be called once per scheduler run'

		
		SmartDashboard.putNumber("lTicks", lfEncoder.getPosition());
		SmartDashboard.putNumber("rTicks", rfEncoder.getPosition());
		// Print out the odometry to smartdashboard
		var odometryPose = odometry.getPoseMeters();
		SmartDashboard.putNumber("odom x", odometryPose.getTranslation().getX());
		SmartDashboard.putNumber("odom y", odometryPose.getTranslation().getY());
		SmartDashboard.putNumber("odom heading", odometryPose.getRotation().getDegrees());
		SmartDashboard.putNumber("gyro raw angle", gyro.getAngle());
		SmartDashboard.putNumber("dtd dist", getDistanceMeters());
		SmartDashboard.putNumber("left speeds", getWheelSpeeds().leftMetersPerSecond);
		SmartDashboard.putNumber("right speeds", getWheelSpeeds().rightMetersPerSecond);
		field.setRobotPose(getPose());
		odometry.update(gyro.getRotation2d(), lfEncoder.getPosition(), rfEncoder.getPosition());
		
	}

	public DifferentialDriveWheelSpeeds getWheelSpeeds()
	{
		return new DifferentialDriveWheelSpeeds();
		//return new DifferentialDriveWheelSpeeds(lfEncoder.getVelocity(), rfEncoder.getVelocity());
	}

	public Pose2d getPose()
	{
		return odometry.getPoseMeters();
	}

	/**
	 * Controls the left and right sides of the drive directly with voltages.
	 *
	 * @param leftVolts
	 *            the commanded left output
	 * @param rightVolts
	 *            the commanded right output
	 */
	public void tankDriveVolts(double leftVolts, double rightVolts)
	{
		left.setVoltage(-leftVolts);
		right.setVoltage(rightVolts);
		dt.feed();
	}

	public void resetOdometry(Pose2d initialPose)
	{
		odometry.resetPosition(new Rotation2d(gyro.getAngle()), 0, 0, initialPose);
	}

	public double getDistanceMeters()
	{
		// return 0.0;
		return Math.max(lfEncoder.getPosition(), rfEncoder.getPosition());
	}

	public void evilMode()
	{
		
		leftFront.setSmartCurrentLimit(Constants.Drivetrain.kEvilStallCurrentLimit, Constants.Drivetrain.kEvilFreeCurrentLimit);
		rightFront.setSmartCurrentLimit(Constants.Drivetrain.kEvilStallCurrentLimit, Constants.Drivetrain.kEvilFreeCurrentLimit);
		leftBack.setSmartCurrentLimit(Constants.Drivetrain.kEvilStallCurrentLimit, Constants.Drivetrain.kEvilFreeCurrentLimit);
		rightBack.setSmartCurrentLimit(Constants.Drivetrain.kEvilStallCurrentLimit, Constants.Drivetrain.kEvilFreeCurrentLimit);

	}

	public void goodMode()
	{
		
		leftFront.setSmartCurrentLimit(Constants.Drivetrain.kGoodStallCurrentLimit, Constants.Drivetrain.kGoodFreeCurrentLimit);
		rightFront.setSmartCurrentLimit(Constants.Drivetrain.kGoodStallCurrentLimit, Constants.Drivetrain.kGoodFreeCurrentLimit);
		leftBack.setSmartCurrentLimit(Constants.Drivetrain.kGoodStallCurrentLimit, Constants.Drivetrain.kGoodFreeCurrentLimit);
		rightBack.setSmartCurrentLimit(Constants.Drivetrain.kGoodStallCurrentLimit, Constants.Drivetrain.kGoodFreeCurrentLimit);
		
	
	}

	public void slowMode()
	{
		leftFront.setSmartCurrentLimit(Constants.Drivetrain.kEvilStallCurrentLimit, Constants.Drivetrain.kEvilFreeCurrentLimit);
		rightFront.setSmartCurrentLimit(Constants.Drivetrain.kEvilStallCurrentLimit, Constants.Drivetrain.kEvilFreeCurrentLimit);
		leftBack.setSmartCurrentLimit(Constants.Drivetrain.kEvilStallCurrentLimit, Constants.Drivetrain.kEvilFreeCurrentLimit);
		rightBack.setSmartCurrentLimit(Constants.Drivetrain.kEvilStallCurrentLimit, Constants.Drivetrain.kEvilFreeCurrentLimit);

		setOutput(0.3);
	}

	SimpleMotorFeedforward feedForward = new SimpleMotorFeedforward(Constants.Drivetrain.ksVolts, Constants.Drivetrain.kvVoltSecondsPerMeter, Constants.Drivetrain.kaVoltSecondsSquaredPerMeter);
  	PIDController lPid = new PIDController(Constants.Drivetrain.kPDriveVel, 0, 0);
  	PIDController rPid = new PIDController(Constants.Drivetrain.kPDriveVel, 0, 0);
  	
	public void setWheelSpeeds(double left, double right)
  	{
		DifferentialDriveWheelSpeeds wheelSpeedNow = getWheelSpeeds();
    	double leftVolt  =	lPid.calculate(wheelSpeedNow.leftMetersPerSecond, left) +
							feedForward.calculate(left);
    	double rightVolt =  rPid.calculate(wheelSpeedNow.leftMetersPerSecond, right) +
							feedForward.calculate(right);
		tankDriveVolts(-leftVolt, rightVolt);
  	}
}
