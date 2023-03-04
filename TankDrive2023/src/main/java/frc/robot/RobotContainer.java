package frc.robot;

import java.util.function.BooleanSupplier;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj2.command.button.*;
import frc.robot.Commands.DriveToDistance;
import frc.robot.Commands.TurnToAngle;
import frc.robot.Commands.UltimateAutonomousCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Grabber;

public class RobotContainer {
    
    
    //private DigitalInput upLimitSwitch = new DigitalInput(1);
    private DigitalInput downLimitSwitch = new DigitalInput(2);
    
    private final SendableChooser<Command> chooser = new SendableChooser<Command>();
    private final Drivetrain dt = new Drivetrain();
    private Elevator elevator = new Elevator(downLimitSwitch);
    private Grabber grabber = new Grabber();

    CommandXboxController driverController = new CommandXboxController(0);
    CommandXboxController operatorController = new CommandXboxController(1);

    public RobotContainer()
    {
        SendableChooser<BooleanSupplier> option = new SendableChooser<BooleanSupplier>();
        option.setDefaultOption("not in front of charging station", () -> false);
        option.addOption("in front of charging station", () -> true);
        

        dt.setDefaultCommand(new RunCommand(() -> dt.arcadeDrive(-driverController.getLeftY(),
        -driverController.getRightX()), dt));

        driverController.rightBumper().whileTrue(new InstantCommand(dt::slowMode, dt))
        .onFalse(new InstantCommand(() -> dt.setOutput(1), dt));

        elevator.setDefaultCommand(new RunCommand(() -> elevator.motorsOff(), elevator));

        
        configureButtonBindings();
        
        chooser.setDefaultOption("Drive To Distance", new DriveToDistance(Units.feetToMeters(7), dt));
        chooser.addOption("Turn To Angle", new TurnToAngle(90, dt));

        chooser.addOption("SetWheelSpeeds", new RunCommand(() -> dt.setWheelSpeeds(1, 1), dt));
        chooser.addOption("AutonCommand", new UltimateAutonomousCommand(dt, option.getSelected()));
        
        SmartDashboard.putData(option);
        SmartDashboard.putData(chooser);
        
        
    }

    public Command getAutonomousCommand()
    {
        return chooser.getSelected();
    }
    
    private void configureButtonBindings()
    {
        operatorController.povUp().whileTrue(new RunCommand(() -> elevator.motorsOn(0.5) , elevator))
        .or(operatorController.povDown().whileTrue(new RunCommand(() -> elevator.motorsOn(-0.5), elevator)))
        .whileFalse(new RunCommand(elevator::motorsOff, elevator));
        

        operatorController.a().onTrue(new InstantCommand(grabber::togglePneumatics, grabber));

        driverController.leftBumper().onTrue(new InstantCommand(dt::evilMode, dt)).onFalse(new InstantCommand(dt::goodMode));
        
    }
    
    public void disabledInit()
    {
        dt.resetEncoders();
    }
}
