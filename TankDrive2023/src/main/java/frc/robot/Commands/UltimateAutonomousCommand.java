package frc.robot.Commands;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;

public class UltimateAutonomousCommand extends SequentialCommandGroup 
{
    SendableChooser<Boolean> option;
    public UltimateAutonomousCommand(Drivetrain dt, Boolean inFrontOfChargingStation)
    {

        if (inFrontOfChargingStation)
            new DriveToDistance(Units.inchesToMeters(62.0625 + 24), dt);
        else
            addCommands(
                new TurnToAngle(90, dt),
                new DriveToDistance(Units.feetToMeters(2), dt),
                new TurnToAngle(0, dt),
                new DriveToDistance(Units.inchesToMeters(62.0625 + 24), dt)
            );
    }
}
