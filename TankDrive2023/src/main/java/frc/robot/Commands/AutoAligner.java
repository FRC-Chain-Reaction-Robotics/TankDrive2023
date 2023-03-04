// package frc.robot.Commands;

// import com.ctre.phoenix.sensors.Pigeon2;
// import com.kauailabs.navx.frc.AHRS;

// import edu.wpi.first.math.controller.PIDController;
// import edu.wpi.first.math.kinematics.ChassisSpeeds;
// import edu.wpi.first.wpilibj2.command.CommandBase;
// import frc.robot.Drivetrain;

// public class AutoAligner extends CommandBase {
//     public final AHRS pigy;
//     public final Drivetrain drivebase;
//     public PIDController pid = new PIDController(0.01, 0, 0);
//     public ChassisSpeeds cs = new ChassisSpeeds(0, 0, 0);

//     public AutoAligner(frc.robot.subsystems.Drivetrain drivebase) {
//         this.drivebase = drivebase;
//         this.pigy = drivebase.getGyro();
//         pid.setTolerance(2.5, 0.1);
//     }

//     @Override
//     public void execute() {
//         cs.vxMetersPerSecond = pid.calculate(pigy.getPitch(), 0);
//         drivebase.arcadeDrive();
//     }

//     @Override
//     public boolean isFinished() {
//         return pid.atSetpoint();
//     }

// }