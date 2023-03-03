package frc.robot.Commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;

public class DriveToDistance extends PIDCommand{
    Drivetrain dt;

    public DriveToDistance(double distMeters, Drivetrain dt)
    {

        super(new PIDController(Constants.Drivetrain.DTD_KP, 0, 0),
            dt::getDistanceMeters,    //  PID's process variable getter method
            distMeters + dt.getDistanceMeters(), //  PID setpoint
            output -> dt.arcadeDrive(output, 0),
            dt);    //  PID output method as a lambda, this w>?>,
        getController().setTolerance(Constants.Drivetrain.DTD_TOLERANCE);  //  the tolerance with which the isFinished() method checks if the PV is within the setpoint
        
        this.dt = dt;
    }

    
    @Override
    public boolean isFinished()
    {
        return getController().atSetpoint();    //  This command will terminate once the desired distance has been reached.
    }
    
    @Override
    public void end(boolean interrupted) {
        dt.arcadeDrive(0, 0);
        
    }
}
