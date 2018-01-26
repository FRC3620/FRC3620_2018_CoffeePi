package org.usfirst.frc.team3620.robot.subsystems;



import org.usfirst.frc.team3620.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class LiftSubsystem extends Subsystem {
	private final WPI_TalonSRX talonSRX7 = RobotMap.liftSubsystemTalonSRX7;
    private final WPI_VictorSPX victorSPX8 = RobotMap.liftSubsystemVictorSPX8;
    private final WPI_VictorSPX victorSPX9 = RobotMap.liftSubsystemVictorSPX9;
    private final SpeedControllerGroup climbSpeedControllerGroup = RobotMap.liftSubsystemClimbSpeedControllerGroup;
    private final DigitalInput elevatorHomeSwitch = RobotMap.liftSubsystemElevatorHomeSwitch;
    private final DigitalInput intakeInPos = RobotMap.liftSubsystemIntakeInPos;
    private final DigitalInput intakeFacingBack = RobotMap.liftSubsystemIntakeFacingBack;


    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

