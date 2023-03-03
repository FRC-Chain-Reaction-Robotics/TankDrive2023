package frc.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj2.command.button.*;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Grabber;

public class RobotContainer {
    private final Drivetrain dt = new Drivetrain();
    private Elevator elevate = new Elevator();
    private Grabber grabber = new Grabber();

    CommandXboxController driverController = new CommandXboxController(0);
    CommandXboxController operatorController = new CommandXboxController(1);

    public RobotContainer()
    {
        dt.setDefaultCommand(new RunCommand(() -> dt.arcadeDrive(-driverController.getLeftY(),
        -driverController.getRightX()), dt));

        driverController.rightBumper().onTrue(new InstantCommand(dt::slowMode, dt))
        .onFalse(new InstantCommand(() -> dt.setOutput(1), dt));

        elevate.setDefaultCommand(new RunCommand(() -> elevate.motorsOff(), elevate));

        
    }
    
    private void configureButtonBindings()
    {
        driverController.povUp().whileTrue(new RunCommand(() -> elevate.motorsOn(0.5), elevate))
        .or(driverController.povUp().whileTrue(new RunCommand(() -> elevate.motorsOn(-0.5), elevate)))
        .whileFalse(new RunCommand(elevate::motorsOff, elevate));

        operatorController.x().onTrue(new InstantCommand(grabber::togglePneumatics, grabber));
    }
    
    public void disabledInit()
    {
        dt.resetEncoders();
    }
}
