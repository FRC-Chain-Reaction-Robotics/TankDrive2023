package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Elevator extends SubsystemBase{
    private CANSparkMax leftLiftMotor = new CANSparkMax(Constants.Elevator.kLeftElevatorMotor, MotorType.kBrushless);
    private CANSparkMax rightLiftMotor =  new CANSparkMax(Constants.Elevator.kRightElevatorMotor, MotorType.kBrushless);
    //private DigitalInput upLimit;
    private DigitalInput downLimit;

    ShuffleboardTab elevatorTab;
    public Elevator(/*DigitalInput up*/ DigitalInput down) {
        //upLimit = up;
        downLimit = down;

        leftLiftMotor.restoreFactoryDefaults();
        rightLiftMotor.restoreFactoryDefaults();

        resetEncoders();

        leftLiftMotor.setIdleMode(IdleMode.kCoast);
        rightLiftMotor.setIdleMode(IdleMode.kCoast);

        leftLiftMotor.setInverted(false);
        rightLiftMotor.setInverted(true);

        // leftLiftMotor.setSmartCurrentLimit(0, 0, 0)

        // elevatorTab = Shuffleboard.getTab("Elevator");
        
    }

    public void resetEncoders() {
        rightLiftMotor.getEncoder().setPosition(0);
        leftLiftMotor.getEncoder().setPosition(0);
    }
    
    public void motorsOn(double speed) {

        //boolean cantGoUp = speed > 0 && upLimit.get();
        boolean cantGoDown = speed < 0 && downLimit.get();

        if (cantGoDown /*|| cantGoUp*/)
        {
            speed = 0;
        }

        rightLiftMotor.set(speed);
        leftLiftMotor.set(speed);
    }
    
    public void motorsOff() {
        rightLiftMotor.set(0);
        leftLiftMotor.set(0);
    }

    @Override
    public void periodic() {
        // elevatorTab.getLayout("Lift Motors", BuiltInLayouts.kList)
        // .addDouble("Right Lift Motor Ticks", () -> rightLiftMotor.getEncoder().getPosition());
        // elevatorTab.getLayout("Lift Motors", BuiltInLayouts.kList)
        // .addDouble("Left Lift Motor Ticks", () -> leftLiftMotor.getEncoder().getPosition());
        
        SmartDashboard.putNumber("right liftTicks", rightLiftMotor.getEncoder().getPosition());
        SmartDashboard.putNumber("left liftTicks", leftLiftMotor.getEncoder().getPosition());
        //SmartDashboard.putBoolean("up limit switch state", upLimit.get());
        SmartDashboard.putBoolean("down limit switch state", downLimit.get());

        
    }

    public void coast()
    {
        leftLiftMotor.setIdleMode(IdleMode.kCoast);
        rightLiftMotor.setIdleMode(IdleMode.kCoast);
    }

    
    public void brake()
    {
        leftLiftMotor.setIdleMode(IdleMode.kBrake);
        rightLiftMotor.setIdleMode(IdleMode.kBrake);
    }
    


}