package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Grabber extends SubsystemBase
{
    Compressor comp = new Compressor(PneumaticsModuleType.CTREPCM);
    DoubleSolenoid ds = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 1, 2);
    public Grabber()
    {
        ds.set(Value.kReverse);    
    }
    
    public void togglePneumatics()
    {
        ds.toggle();   
    }

    public void grabGamePiece()
    {
        ds.set(Value.kForward);
    }

    public void releaseGamePiece()
    {
        ds.set(Value.kReverse);
    }

    @Override
    public void periodic()
    {
        SmartDashboard.putBoolean("Pressure", comp.getPressureSwitchValue());
    }

    
}
