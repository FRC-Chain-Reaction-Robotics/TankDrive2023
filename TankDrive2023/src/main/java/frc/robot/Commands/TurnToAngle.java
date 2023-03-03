package frc.robot.Commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.Drivetrain;

public class TurnToAngle extends PIDCommand
{
    Drivetrain dt;

    public TurnToAngle(double angleDegrees, Drivetrain dt)
    {
        super(new PIDController(0.015, 0, 0),
            dt::getAngle,   //  PID's process variable getter method
            angleDegrees,   //  PID setpoint
            output -> dt.arcadeDrive(0, -output),    //  PID output method as a lambda, this will turn the robot to the desired angle
            dt);    //  every command must have its subsystems passed in as "requirements"

        getController().setTolerance(3);    //  the tolerance with which the isFinished() method checks if the PV is within the setpoint

        this.dt = dt;
    } 

    @Override
    public void initialize()
    {
        dt.resetGyro(); //  upon initialization, reset the gyro
    }

    @Override
    public boolean isFinished()
    {
        return getController().atSetpoint();    //  This command will terminate once the desired angle has been reached.
    
    }
    @Override
    public void end(boolean interrupted) 
    {
        dt.arcadeDrive(0, 0);
    }
}
