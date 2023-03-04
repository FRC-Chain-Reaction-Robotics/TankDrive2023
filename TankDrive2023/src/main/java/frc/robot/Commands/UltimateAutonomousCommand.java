package frc.robot.Commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;

public class UltimateAutonomousCommand extends SequentialCommandGroup 
{
    public UltimateAutonomousCommand(Drivetrain dt, BooleanSupplier inFrontOfChargingStation)
    {

        if (inFrontOfChargingStation.getAsBoolean())
            addCommands(new DriveToDistance(Units.inchesToMeters(62.0625 + 24), dt));
        else
            addCommands(
                new TurnToAngle(90, dt),
                new DriveToDistance(Units.feetToMeters(5), dt),
                new TurnToAngle(0, dt),
                new DriveToDistance(Units.inchesToMeters(62.0625 + 24), dt)
            );
    }
}
