package org.usfirst.frc.team3620.robot.paths;

import org.slf4j.Logger;
import org.usfirst.frc.team3620.robot.Robot;
import org.usfirst.frc.team3620.robot.RobotMap;
import org.usfirst.frc.team3620.robot.subsystems.DriveSubsystem;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.EventLogging.Level;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

/**
 *
 */

public abstract class AbstractPath extends Command {
	Logger logger = EventLogging.getLogger(getClass(), Level.INFO);

	boolean finishedFlag = false;
	boolean debugMode = false;
	int competitionMultiplier;
	EncoderFollower left;
	EncoderFollower right;

	// Remove these and initialize down in execute() if things don't work
	int encoderPosLeft;
	int encoderPosRight;
	double maxOutput = 0;
	double maxTurn = 0;

	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
	public AbstractPath() {
		requires(Robot.driveSubsystem);

	}

	void setup() {
		double t0 = System.currentTimeMillis();
		logger.info("setup() start");
		// Fit Method: HERMITE_CUBIC or HERMITE_QUINTIC
		// Sample Count: SAMPLES_HIGH (100 000)
		// SAMPLES_LOW (10 000)
		// SAMPLES_FAST (1 000)

		// Time Step: 0.05 Seconds
		// Max Velocity Multiplier: <getPathfinderGenVelocityMultiplier()>
		// Max Velocity: <getPathfinderV_MAX()> m/s
		// Max Acceleration: <getPathfinderGenAcceleration()> m/s/s
		// Max Jerk: 10.0 m/s/s/s
		// **Now in feet**
		// Check on the 0.75 vmax multiplier and tune as needed
		Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC,
				Trajectory.Config.SAMPLES_FAST, 0.05, (getPathfinderGenVelocityMultiplier() * getPathfinderV_MAX()), getPathfinderGenAcceleration(), 10.0); 
		Waypoint[] points = getMyWaypoints();
		Trajectory trajectory = Pathfinder.generate(points, config);

		TankModifier modifier = new TankModifier(trajectory).modify(2.0); // 2 feet.

		left = new EncoderFollower(modifier.getLeftTrajectory());
		right = new EncoderFollower(modifier.getRightTrajectory());

		logger.info("setup() done in " + (System.currentTimeMillis() - t0) + "ms");
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
		return 0.001;
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
		// This is the max velocity of the path/divetrain (?)
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

	/**
	 * This method gets called to multiply the final motor outputs by a reducer constant.
	 * It provides a default value that subcommands can override if they need a
	 * different value.
	 * 
	 * @return Output multiplier constant for Pathfinder
	 */
	double getPathfinderOutputMultiplier() {
		// Used to scale down final output to motors by a multiplier less than 1.0
		// Added because we think vMax needs to be set to drivetrain speed, and wasn't having an effect on our following velocity.
		return 0.5;
	}

	double getPathfinderGenVelocityMultiplier() {
		return 0.6;
	}

	double getPathfinderGenAcceleration() {
		return 3.0;
	}
	
	double getHeadingCorrectionFactor() {
		return 0.8 / 80.0;
	}

	double lastLeftEncoder = 0;
	double lastRightEncoder = 0;
	double startingAbsoluteHeading; 
	
	@Override
	protected void initialize() {
		EventLogging.commandMessage(logger);

		setup();

		finishedFlag = false;

		Robot.driveSubsystem.resetEncoders();
		//Robot.driveSubsystem.resetNavX();
		startingAbsoluteHeading = Robot.driveSubsystem.getAngle();
		
		logger.info("Navx initial 1 = {}", Robot.driveSubsystem.getAngle());
		left.configurePIDVA(getPathfinderP(), getPathfinderI(), getPathfinderD(), 1 / getPathfinderV_MAX(),
				getPathfinderA_GAIN());
		logger.info("Navx initial 2 = {}", Robot.driveSubsystem.getAngle());
		right.configurePIDVA(getPathfinderP(), getPathfinderI(), getPathfinderD(), 1 / getPathfinderV_MAX(),
				getPathfinderA_GAIN());
		
		
		
		logger.info("PIDVAs configured.");
		logger.info("Navx initial 3 = {}", Robot.driveSubsystem.getAngle());
		lastLeftEncoder = encoderPosLeft = Robot.driveSubsystem.readLeftEncRaw();
		//lastLeftEncoder = encoderPosLeft = RobotMap.driveSubsystemLeftEncoder.getRaw();
		lastRightEncoder = encoderPosRight = Robot.driveSubsystem.readRightEncRaw();
		//lastRightEncoder = encoderPosRight = RobotMap.driveSubsystemRightEncoder.getRaw();

		logger.info("Encoders L,R initial = {}, {}", encoderPosLeft, encoderPosRight);

		logger.info("Reverse mode = {}", getPathfinderReverseMode());
		logger.info("Navx initial = {}", Robot.driveSubsystem.getAngle());
		//CHANGED TICK PER REVOLUTION TO 1024 from 512
		if(Robot.driveSubsystem.gotCompBot()) {
			competitionMultiplier = 2;
		} else {
			competitionMultiplier = 1;
		}
		if (getPathfinderReverseMode()) {
			left.configureEncoder(-encoderPosRight, 512*competitionMultiplier, (0.3333)); // (raw encoder position, ticks per wheel rotation, wheel
			// diameter in feet)
            right.configureEncoder(-encoderPosLeft, 512*competitionMultiplier, (0.3333));
		} else {
			left.configureEncoder(encoderPosLeft, 512, (0.3333)); // (raw encoder position, ticks per wheel rotation, wheel
			// diameter in feet)
			right.configureEncoder(encoderPosRight, 512, (0.3333));
		}
			

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
			encoderPosLeft = -1 * Robot.driveSubsystem.readRightEncRaw();
			encoderPosRight = -1 * Robot.driveSubsystem.readLeftEncRaw();
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

		double currentAbsoluteHeading = Robot.driveSubsystem.getAngle();
		double navx_heading = currentAbsoluteHeading - startingAbsoluteHeading;

		// change desired heading to positive right
		double desired_heading = - Pathfinder.r2d(left.getHeading()); // seems to be outputting a range of 0-360 degrees.
																	// Hmm...

		// double desired_heading = Pathfinder.r2d(left.getHeading()) - 180;

		// "Pathfinder.boundHalfDegrees() binds a degree angle to -180..180, preventing
		// absurdly large turn value."
		// positive angle difference means we are pointed too far to the right
		double angleDifference = Pathfinder.boundHalfDegrees(navx_heading - desired_heading);
		// Included example angle calculation:
		// positive angle difference means we are pointed too far to the rigvht
		double turn = -getHeadingCorrectionFactor() * angleDifference; // tune this to tune turn rate??
		// negative turn means we are pointed too far right, and need to boost RH power
		
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
		// negative turn means we are pointed too far right, and need to boost on RH power
		double leftMotorSet = outputLeft + turn;
		double rightMotorSet = outputRight - turn;
		if (getPathfinderReverseMode()) {
			Robot.driveSubsystem.autoDriveTank((getPathfinderOutputMultiplier() * -rightMotorSet), (getPathfinderOutputMultiplier() * -leftMotorSet));  //TODO fix multiplier/values greater than 1
		} else {
			Robot.driveSubsystem.autoDriveTank((getPathfinderOutputMultiplier() * leftMotorSet), (getPathfinderOutputMultiplier() * rightMotorSet));
		}

		// records the max output value for tuning the v_max scaler up there
		if (leftMotorSet > maxOutput) {
			maxOutput = leftMotorSet;
		}
		if (rightMotorSet > maxOutput) {
			maxOutput = rightMotorSet;
		}

		if (Math.abs(turn) > maxTurn) {
			maxTurn = turn;
		}

		double t1 = System.currentTimeMillis();


		// TODO log with logger.debug() instead.
		if (debugMode) {
			// Console prints for debugging. Comment and uncomment as needed.
			if(false) {
				System.out.println("\noutputLeft = " + outputLeft);
				System.out.println("outputRight = " + outputRight);
				System.out.println("Encoders delta L,R = " + leftEncoderDelta + " " + rightEncoderDelta);
				System.out.println("Encoders L,R = " + encoderPosLeft + " " + encoderPosRight);
				//	System.out.println("left.getHeading() = " + left.getHeading());
				//	System.out.println("r2d-left.getHeading() = " + Pathfinder.r2d(left.getHeading()));
				System.out.println("desired_heading = " + desired_heading);
				System.out.println("navx_heading = " + navx_heading);
				System.out.println("angleDifference = " + angleDifference);
				System.out.println("tdelta = " + (t1 - t0));
				System.out.println("turn = " + turn);
				System.out.println("Motor output L/R: " + leftMotorSet + ", " + rightMotorSet);
				System.out.println("Max motor output: " + maxOutput);
				System.out.println("Max turn:" + maxTurn);
			}
		}
	/*	System.out.println("\noutputLeft = " + outputLeft);
		System.out.println("outputRight = " + outputRight);
		System.out.println("desired_heading = " + desired_heading);
		System.out.println("navx_heading = " + navx_heading); */


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
			return finishedFlag;
		}

		// Called once after isFinished returns true
		@Override
		protected void end() {
			EventLogging.commandMessage(logger);
			System.out.println("Max motor output: " + maxOutput);
		}

		// Called when another command which requires one or more of the same
		// subsystems is scheduled to run
		@Override
		protected void interrupted() {
			EventLogging.commandMessage(logger);
		}
	}

