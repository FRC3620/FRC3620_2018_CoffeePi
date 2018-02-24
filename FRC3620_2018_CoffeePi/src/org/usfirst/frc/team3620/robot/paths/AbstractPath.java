package org.usfirst.frc.team3620.robot.paths;

import org.usfirst.frc.team3620.robot.Robot;
import org.usfirst.frc.team3620.robot.RobotMap;
import org.usfirst.frc.team3620.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

/**
 *
 */

// TODO add reverse modifier
public abstract class AbstractPath extends Command {

	boolean finishedFlag = false;

	EncoderFollower left;
	EncoderFollower right;

	// Remove these and initialize down in execute() if things don't work
	int encoderPosLeft;
	int encoderPosRight;
	double maxOutput = 0;

	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
	public AbstractPath() {

		requires(Robot.driveSubsystem);

		// Fit Method: HERMITE_CUBIC or HERMITE_QUINTIC
		// Sample Count: SAMPLES_HIGH (100 000)
		// SAMPLES_LOW (10 000)
		// SAMPLES_FAST (1 000)
		// Time Step: 0.05 Seconds
		// Max Velocity: 1.7 m/s
		// Max Acceleration: 2.0 m/s/s
		// Max Jerk: 60.0 m/s/s/s
		// **Now in feet**
		// Check on the 0.75 vmax multiplier and tune as needed
		Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC,
				Trajectory.Config.SAMPLES_LOW, 0.05, (0.75 * getPathfinderV_MAX()), 4.5, 10.0);
		Waypoint[] points = getMyWaypoints();
		Trajectory trajectory = Pathfinder.generate(points, config);

		TankModifier modifier = new TankModifier(trajectory).modify(2.0); // 2 feet.

		left = new EncoderFollower(modifier.getLeftTrajectory());
		right = new EncoderFollower(modifier.getRightTrajectory());

	}

	abstract Waypoint[] getMyWaypoints();

	// Override any one of us if necessary!
	/**
	 * This method gets called to provide the 'P' value for the Pathfinder PID. It
	 * provides a default value that subcommands can override if they need a
	 * different value.
	 * 
	 * @return P value for Pathfinder
	 */
	double getPathfinderP() {
		// This is the proportional gain. Usually this will be quite high
		return 0.01;
	}

	/**
	 * This method gets called to provide the 'I' value for the Pathfinder PID. It
	 * provides a default value that subcommands can override if they need a
	 * different value.
	 * 
	 * @return I value for Pathfinder
	 */
	double getPathfinderI() {
		// This is the integral gain. This is unused for motion profiling
		return 0.0;
	}

	/**
	 * This method gets called to provide the 'D' value for the Pathfinder PID. It
	 * provides a default value that subcommands can override if they need a
	 * different value.
	 * 
	 * @return D value for Pathfinder
	 */
	double getPathfinderD() {
		// This is the derivative gain. Tweak this if you are unhappy with the tracking
		// of the trajectory
		return 0.0;
	}

	/**
	 * This method gets called to provide the max velocity value for the Pathfinder
	 * PID. It provides a default value that subcommands can override if they need a
	 * different value.
	 * 
	 * @return Max velocity value for Pathfinder
	 */
	double getPathfinderV_MAX() {
		// The fourth argument is the velocity ratio. This is 1 over the maximum
		// velocity you provided in the
		// trajectory configuration (it translates m/s to a -1 to 1 scale that your
		// motors can read)
		// (In feet)
		return 3.5;
	}

	/**
	 * This method gets called to provide the acceleration gain value for the
	 * Pathfinder PID. It provides a default value that subcommands can override if
	 * they need a different value.
	 * 
	 * @return Acceleration gain value for Pathfinder
	 */
	double getPathfinderA_GAIN() {
		return 0;
	}

	/**
	 * This method gets called to drive the generated path in reverse. It defaults
	 * to false, with which the robot will drive forward as usual. Setting to true
	 * will make the back of the robot the new "front."
	 * 
	 * @return Reverse direction modifier setting for Pathfinder
	 */
	boolean getPathfinderReverseMode() {
		return false;
	}

	double lastLeftEncoder = 0;
	double lastRightEncoder = 0;
	
	@Override
	protected void initialize() {

		Robot.driveSubsystem.resetEncoders();
		Robot.driveSubsystem.resetNavX();

		left.configurePIDVA(getPathfinderP(), getPathfinderI(), getPathfinderD(), 1 / getPathfinderV_MAX(),
				getPathfinderA_GAIN());
		right.configurePIDVA(getPathfinderP(), getPathfinderI(), getPathfinderD(), 1 / getPathfinderV_MAX(),
				getPathfinderA_GAIN());

		System.out.println("PIDVAs configured.");

		lastLeftEncoder = encoderPosLeft = Robot.driveSubsystem.readLeftEncRaw();
		//lastLeftEncoder = encoderPosLeft = RobotMap.driveSubsystemLeftEncoder.getRaw();
		lastRightEncoder = encoderPosRight = Robot.driveSubsystem.readRightEncRaw();
		//lastRightEncoder = encoderPosRight = RobotMap.driveSubsystemRightEncoder.getRaw();
		
		System.out.println("Encoders L,R initial = " + encoderPosLeft + " " + encoderPosRight);

		// TODO test this since moving to here from execute()
		left.configureEncoder(encoderPosLeft, 512, (0.33)); // (raw encoder position, ticks per wheel rotation, wheel
															// diameter in feet)
		right.configureEncoder(encoderPosRight, 512, (0.33));

		t0 = System.currentTimeMillis();
	}
	
	double t0;

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.wpi.first.wpilibj.command.Command#execute()
	 */
	@Override
	protected void execute() {
		if (getPathfinderReverseMode()) {
			encoderPosLeft = -1 * Robot.driveSubsystem.readLeftEncRaw();
			encoderPosRight = -1 * Robot.driveSubsystem.readRightEncRaw();
		} else {
			encoderPosLeft = Robot.driveSubsystem.readLeftEncRaw();
			encoderPosRight = Robot.driveSubsystem.readRightEncRaw();
		}
		
		double leftEncoderDelta = lastLeftEncoder - encoderPosLeft;
		double rightEncoderDelta = lastRightEncoder - encoderPosRight;
		
		lastLeftEncoder = encoderPosLeft;
		lastRightEncoder = encoderPosRight;

		double outputLeft = left.calculate(encoderPosLeft);
		double outputRight = right.calculate(encoderPosRight);

		double navx_heading = Robot.driveSubsystem.getAngle();
		double desired_heading = Pathfinder.r2d(left.getHeading()); // seems to be outputting a range of 0-360 degrees.
																	// Hmm...
		// double desired_heading = Pathfinder.r2d(left.getHeading()) - 180;

		// "Pathfinder.boundHalfDegrees() binds a degree angle to -180..180, preventing
		// absurdly large turn value."
		double angleDifference = Pathfinder.boundHalfDegrees(desired_heading + navx_heading); //maybe "+" is counter-intuitive??
		// Included example angle calculation:
		double turn = 0.8 * (-1.0 / 80.0) * angleDifference; // tune this to tune turn rate??
		// Custom angle calculation:
		// double turn = -0.03 * angleDifference; //TODO Determine the appropriate turn
		// value multiplier.

		// Restrict turn value
		if (turn > 0) {
			turn = Math.min(turn, 0.4);
		} else if (turn < 0) {
			turn = Math.max(turn, -0.4);
		} else {
			turn = 0;
		}
		
//		turn = 0;  //for testing

		// Factor in the turn value and run the motors.
		double leftMotorSet = outputLeft + turn;
		double rightMotorSet = outputRight - turn;
		if (getPathfinderReverseMode()) {
			Robot.driveSubsystem.autoDriveTank((-rightMotorSet), (-leftMotorSet));  //TODO fix multiplier/values greater than 1
		} else {
			Robot.driveSubsystem.autoDriveTank((leftMotorSet), (rightMotorSet));
		}

		// records the max output value for tuning the v_max scaler up there
		if (leftMotorSet > maxOutput) {
			maxOutput = leftMotorSet;
		}
		if (rightMotorSet > maxOutput) {
			maxOutput = rightMotorSet;
		}
		
		double t1 = System.currentTimeMillis();
		

		// Console prints for debugging. Comment and uncomment as needed.
		System.out.println("\noutputLeft = " + outputLeft);
		System.out.println("outputRight = " + outputRight);
		System.out.println("Encoders delta L,R = " + leftEncoderDelta + " " + rightEncoderDelta);
		System.out.println("Encoders L,R = " + encoderPosLeft + " " + encoderPosRight);
//		System.out.println("left.getHeading() = " + left.getHeading());
//		System.out.println("r2d-left.getHeading() = " + Pathfinder.r2d(left.getHeading()));
		System.out.println("desired_heading = " + desired_heading);
		System.out.println("navx_heading = " + navx_heading);
		System.out.println("angleDifference = " + angleDifference);
		System.out.println("tdelta = " + (t1 - t0));
		System.out.println("turn = " + turn);
		System.out.println("Motor output L/R: " + leftMotorSet + ", " + rightMotorSet);
		System.out.println("Max motor output: " + maxOutput);

		// Ends the command when forward trajectory is finished.

		if (outputLeft == 0.0 && outputRight == 0.0) {
//			double endTime = timeSinceInitialized();
//			while (timeSinceInitialized() < endTime + 1000) {
//			}
			finishedFlag = true;
		}
		
		t0 = t1;
		
//		if (maxOutput > 0.1) {
//			if (Math.abs(leftMotorSet) < 0.1 && Math.abs(rightMotorSet) < 0.1) {
//				finishedFlag = true;
//			}
//		}

	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		if (finishedFlag) {
			return true;
		} else {
			return false;
		}
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		System.out.println("Autonomous command ended.");
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		System.out.println("Autonomous command interrupted.");
	}
}