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
    DoubleSolenoid ds;
    public Grabber()
    {
        ds = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 2, 3);
        ds.set(Value.kForward);
        comp.enableDigital();    
    }
    
    public void togglePneumatics()
    {
        ds.toggle();   
    }

    public void disable()
    {
        ds.set(Value.kOff);
        comp.disable();
    }

    public void enable(DoubleSolenoid.Value value)
    {
        ds.set(value);
        comp.enableDigital();
    }

    @Override
    public void periodic()
    {
        SmartDashboard.putBoolean("Pressure", comp.getPressureSwitchValue());
        SmartDashboard.putString("Solenoid Direction", getSolenoidValue());
    }

    private String getSolenoidValue()
    {
        if (ds.get().equals(Value.kForward))
            return "Forward";
        return "Reverse";
    }
}
