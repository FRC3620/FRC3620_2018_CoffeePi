// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc.team3620.robot;

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static DigitalInput exampleSubsystemDigitalInput0;
    public static SpeedController driveSubsystemLeftSpeedController;
    public static SpeedController driveSubsystemRightSpeedController;
    public static DifferentialDrive driveSubsystemPWMDifferentialDrive;
    public static WPI_TalonSRX driveSubsystemTalonSRX1;
    public static WPI_VictorSPX driveSubsystemVictorSPX2;
    public static WPI_VictorSPX driveSubsystemVictorSPX3;
    public static SpeedControllerGroup driveSubsystemLeftSpeedControllerGroup;
    public static WPI_TalonSRX driveSubsystemTalonSRX4;
    public static WPI_VictorSPX driveSubsystemVictorSPX5;
    public static WPI_VictorSPX driveSubsystemVictorSPX6;
    public static SpeedControllerGroup driveSubsystemRightSpeedControllerGroup;
    public static DifferentialDrive driveSubsystemCANDifferentialDrive;
    public static SpeedController lightSubsystemLightPWM9;
    public static DoubleSolenoid driveGearShifter;
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public static void init() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        exampleSubsystemDigitalInput0 = new DigitalInput(0);
        LiveWindow.addSensor("ExampleSubsystem", "Digital Input 0", exampleSubsystemDigitalInput0);
        
        driveSubsystemLeftSpeedController = new Spark(0);
        LiveWindow.addActuator("DriveSubsystem", "LeftSpeedController", (Spark) driveSubsystemLeftSpeedController);
        driveSubsystemLeftSpeedController.setInverted(false);
        driveSubsystemRightSpeedController = new Spark(1);
        LiveWindow.addActuator("DriveSubsystem", "RightSpeedController", (Spark) driveSubsystemRightSpeedController);
        driveSubsystemRightSpeedController.setInverted(false);
        driveSubsystemPWMDifferentialDrive = new DifferentialDrive(driveSubsystemLeftSpeedController, driveSubsystemRightSpeedController);
        LiveWindow.addActuator("DriveSubsystem", "PWMDifferentialDrive", driveSubsystemPWMDifferentialDrive);
        driveSubsystemPWMDifferentialDrive.setSafetyEnabled(true);
        driveSubsystemPWMDifferentialDrive.setExpiration(0.1);
        driveSubsystemPWMDifferentialDrive.setMaxOutput(1.0);

        driveSubsystemTalonSRX1 = new WPI_TalonSRX(1);
        
        
        driveSubsystemVictorSPX2 = new WPI_VictorSPX(2);
        
        
        driveSubsystemVictorSPX3 = new WPI_VictorSPX(3);
        
        driveGearShifter = new DoubleSolenoid(0,0,1);
        
        driveSubsystemLeftSpeedControllerGroup = new SpeedControllerGroup(driveSubsystemTalonSRX1, driveSubsystemVictorSPX2 , driveSubsystemVictorSPX3 );
        LiveWindow.addActuator("DriveSubsystem", "LeftSpeedControllerGroup", driveSubsystemLeftSpeedControllerGroup);
        
        driveSubsystemTalonSRX4 = new WPI_TalonSRX(4);
        
        
        driveSubsystemVictorSPX5 = new WPI_VictorSPX(5);
        
        
        driveSubsystemVictorSPX6 = new WPI_VictorSPX(6);
        
        
        driveSubsystemRightSpeedControllerGroup = new SpeedControllerGroup(driveSubsystemTalonSRX4, driveSubsystemVictorSPX5 , driveSubsystemVictorSPX6 );
        LiveWindow.addActuator("DriveSubsystem", "RightSpeedControllerGroup", driveSubsystemRightSpeedControllerGroup);
        
        driveSubsystemCANDifferentialDrive = new DifferentialDrive(driveSubsystemLeftSpeedControllerGroup, driveSubsystemRightSpeedControllerGroup);
        LiveWindow.addActuator("DriveSubsystem", "CANDifferentialDrive", driveSubsystemCANDifferentialDrive);
        driveSubsystemCANDifferentialDrive.setSafetyEnabled(true);
        driveSubsystemCANDifferentialDrive.setExpiration(0.1);
        driveSubsystemCANDifferentialDrive.setMaxOutput(1.0);

        lightSubsystemLightPWM9 = new Spark(9);
        LiveWindow.addActuator("LightSubsystem", "LightPWM9", (Spark) lightSubsystemLightPWM9);
        lightSubsystemLightPWM9.setInverted(false);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }
}
