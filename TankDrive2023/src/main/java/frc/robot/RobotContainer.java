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

public class RobotContainer {
    private final Drivetrain dt = new Drivetrain();

    CommandXboxController driverController = new CommandXboxController(0);

    public RobotContainer()
    {
        dt.setDefaultCommand(new RunCommand(() -> dt.arcadeDrive(-driverController.getLeftY(),
        driverController.getRightX()), dt));

        driverController.rightBumper().onTrue(new InstantCommand(dt::slowMode, dt))
        .onFalse(new InstantCommand(() -> dt.setOutput(1), dt));

        
    }

    public void disabledInit()
    {
        dt.resetEncoders();
    }
}
